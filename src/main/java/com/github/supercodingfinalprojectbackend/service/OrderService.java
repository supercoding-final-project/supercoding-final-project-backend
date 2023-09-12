package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.OrderSheetDto;
import com.github.supercodingfinalprojectbackend.dto.PaymentDto;
import com.github.supercodingfinalprojectbackend.entity.*;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MentorRepository;
import com.github.supercodingfinalprojectbackend.repository.OrderSheetRepository;
import com.github.supercodingfinalprojectbackend.repository.PaymentRepository;
import com.github.supercodingfinalprojectbackend.repository.SelectedClassTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final MentorRepository mentorRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final PaymentRepository paymentRepository;
    private final SelectedClassTimeRepository selectedClassTimeRepository;

    public PaymentDto approveOrder(Long userId, OrderSheetDto orderDtoRequest) {
        Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_MENTOR::exception);
        Long orderSheetId = orderDtoRequest.getOrderSheetId();

        OrderSheet orderSheet = orderSheetRepository.findByPostMentorAndOrderSheetIdAndIsDeletedIsFalse(mentor, orderSheetId)
                .orElseThrow(()->new ApiException(401, "다른 멘토의 결제 요청을 승인할 수 없습니다."));

        Payment payment = orderSheet.approvedBy(mentor);
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentDto.from(savedPayment);
    }

    public OrderSheetDto refuseOrder(Long userId, OrderSheetDto orderDtoRequest) {
        Long orderSheetId = orderDtoRequest.getOrderSheetId();
        Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_MENTOR::exception);

        OrderSheet orderSheet = orderSheetRepository.findByPostMentorAndOrderSheetIdAndIsDeletedIsFalse(mentor, orderSheetId)
                .orElseThrow(()->new ApiException(401, "다른 멘토의 결제 요청을 취소할 수 없습니다."));

        orderSheet.beRejected();
        List<SelectedClassTime> selectedClassTimes = selectedClassTimeRepository.findAllByMentorAndOrderSheetAndIsDeletedIsFalse(mentor, orderSheet);
        selectedClassTimes.forEach(SelectedClassTime::beRejected);

        return OrderSheetDto.from(orderSheet);
    }
}
