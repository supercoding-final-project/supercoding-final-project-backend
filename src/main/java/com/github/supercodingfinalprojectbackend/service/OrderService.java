package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.OrderSheetDto;
import com.github.supercodingfinalprojectbackend.dto.PaymentDto;
import com.github.supercodingfinalprojectbackend.entity.*;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MentorRepository;
import com.github.supercodingfinalprojectbackend.repository.OrderSheetRepository;
import com.github.supercodingfinalprojectbackend.repository.PaymentRepository;
import com.github.supercodingfinalprojectbackend.repository.SelectedClassTimeRepository;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final MentorRepository mentorRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final PaymentRepository paymentRepository;
    private final SelectedClassTimeRepository selectedClassTimeRepository;

    public PaymentDto.PaymentIdResponse approveOrder(Long userId, OrderSheetDto.OrderSheetIdRequest orderDtoRequest) {
        Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_MENTOR::exception);
        Long orderSheetId = orderDtoRequest.getOrderSheetId();

        OrderSheet orderSheet = orderSheetRepository.findByPostMentorAndOrderSheetIdAndIsDeletedIsFalseAndIsCompletedIsFalse(mentor, orderSheetId)
                .orElseThrow(ApiErrorCode.NOT_FOUND_ORDERSHEET::exception);

        Payment payment = paymentRepository.save(orderSheet.approvedBy(mentor));
        return PaymentDto.PaymentIdResponse.from(payment);
    }

    public OrderSheetDto.OrderSheetIdResponse refuseOrder(Long userId, OrderSheetDto.OrderSheetIdRequest orderDtoRequest) {
        Long orderSheetId = orderDtoRequest.getOrderSheetId();

        OrderSheet orderSheet = orderSheetRepository.findByPostMentorUserUserIdAndOrderSheetIdAndIsDeletedIsFalseAndIsCompletedIsFalse(userId, orderSheetId)
                .orElseThrow(ApiErrorCode.NOT_FOUND_ORDERSHEET::exception);

        orderSheet.beRejected();

        selectedClassTimeRepository.deleteAllByMentorUserUserIdAndOrderSheet(userId, orderSheet);

        return OrderSheetDto.OrderSheetIdResponse.from(orderSheet);
    }

    public OrderSheetDto.OrderSheetIdSetResponse cancelOrders(Long userId, Set<Long> orderSheetIdSet) {
        List<OrderSheet> orderSheets = orderSheetRepository.findAllByMenteeUserUserIdAndOrderSheetIdIsInAndIsCompletedIsFalseAndIsDeletedIsFalse(userId, orderSheetIdSet);
        orderSheets.forEach(OrderSheet::canceled);

        selectedClassTimeRepository.deleteAllByMenteeUserUserIdAndOrderSheetIsIn(userId, orderSheets);
        return OrderSheetDto.OrderSheetIdSetResponse.from(orderSheets);
    }
}
