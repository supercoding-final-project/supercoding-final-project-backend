package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.event.OrderEvent;
import com.github.supercodingfinalprojectbackend.dto.OrderSheetDto;
import com.github.supercodingfinalprojectbackend.dto.PaymentDto;
import com.github.supercodingfinalprojectbackend.entity.*;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MentorRepository;
import com.github.supercodingfinalprojectbackend.repository.OrderSheetRepository;
import com.github.supercodingfinalprojectbackend.repository.PaymentRepository;
import com.github.supercodingfinalprojectbackend.repository.SelectedClassTimeRepository;
import com.github.supercodingfinalprojectbackend.util.sse.EventKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final MentorRepository mentorRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final PaymentRepository paymentRepository;
    private final SelectedClassTimeRepository selectedClassTimeRepository;
    private final EventService eventService;

    @Transactional
    public PaymentDto.PaymentIdResponse approveOrder(Long userId, OrderSheetDto.OrderSheetIdRequest request) {
        Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_MENTOR::exception);
        Long orderSheetId = request.getOrderSheetId();

        OrderSheet orderSheet = orderSheetRepository.findByPostMentorAndOrderSheetIdAndIsDeletedIsFalseAndIsCompletedIsFalse(mentor, orderSheetId)
                .orElseThrow(ApiErrorCode.NOT_FOUND_ORDERSHEET::exception);

        Payment payment = paymentRepository.save(orderSheet.approvedBy(mentor));

        try {
            orderSheetRepository.save(orderSheet);
        } catch (OptimisticLockingFailureException e) {
            throw new ApiException(409, "이미 취소되었거나 반려되었습니다.");
        }

        EventKey key = EventKey.aboutOrder(orderSheet.getMentee().getUser());
        OrderEvent orderEvent = OrderEvent.approved(orderSheet);
        eventService.pushEvent(key, orderEvent);

        return PaymentDto.PaymentIdResponse.from(payment);
    }

    @Transactional
    public OrderSheetDto.OrderSheetIdResponse refuseOrder(Long userId, OrderSheetDto.OrderSheetIdRequest orderDtoRequest) {
        Long orderSheetId = orderDtoRequest.getOrderSheetId();

        OrderSheet orderSheet = orderSheetRepository.findByPostMentorUserUserIdAndOrderSheetIdAndIsDeletedIsFalseAndIsCompletedIsFalse(userId, orderSheetId)
                .orElseThrow(ApiErrorCode.NOT_FOUND_ORDERSHEET::exception);

        orderSheet.beRejected();

        selectedClassTimeRepository.deleteAllByMentorUserUserIdAndOrderSheet(userId, orderSheet);

        try {
            orderSheetRepository.save(orderSheet);
        } catch (OptimisticLockingFailureException e) {
            throw new ApiException(409, "이미 취소되었거나 승인되었습니다.");
        }

        EventKey key = EventKey.aboutOrder(orderSheet.getMentee().getUser());
        OrderEvent event = OrderEvent.rejected(orderSheet);
        eventService.pushEvent(key, event);

        return OrderSheetDto.OrderSheetIdResponse.from(orderSheet);
    }

    @Transactional
    public OrderSheetDto.OrderSheetIdSetResponse cancelOrders(Long userId, Set<Long> orderSheetIdSet) {
        List<OrderSheet> orderSheets = orderSheetRepository.findAllByMenteeUserUserIdAndOrderSheetIdIsInAndIsCompletedIsFalseAndIsDeletedIsFalse(userId, orderSheetIdSet);
        orderSheets.forEach(OrderSheet::canceled);

        try {
            orderSheets.forEach(orderSheetRepository::save);
        } catch (OptimisticLockingFailureException e) {
            throw new ApiException(409, "이미 승인되었거나 반려되었습니다.");
        }

        selectedClassTimeRepository.deleteAllByMenteeUserUserIdAndOrderSheetIsIn(userId, orderSheets);

        orderSheets.forEach(o->{
            EventKey key = EventKey.aboutOrder(o.getPost().getMentor().getUser());
            OrderEvent event = OrderEvent.canceled(o);
            eventService.pushEvent(key, event);
        });

        return OrderSheetDto.OrderSheetIdSetResponse.from(orderSheets);
    }
}
