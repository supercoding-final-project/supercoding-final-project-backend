package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.OrderSheetDto;
import com.github.supercodingfinalprojectbackend.dto.PaymentDto;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import com.github.supercodingfinalprojectbackend.entity.Payment;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MentorRepository;
import com.github.supercodingfinalprojectbackend.repository.OrderSheetRepository;
import com.github.supercodingfinalprojectbackend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final MentorRepository mentorRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final PaymentRepository paymentRepository;

    public PaymentDto approveOrder(Long userId, OrderSheetDto orderDtoRequest) {
        Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_MENTOR::exception);
        Long orderSheetId = orderDtoRequest.getOrderSheetId();

        OrderSheet orderSheet = orderSheetRepository.findByPostMentorAndOrderSheetIdAndIsDeletedIsFalse(mentor, orderSheetId)
                .orElseThrow(()->new ApiException(401, "다른 멘토의 결제 승인을 대신할 수 없습니다."));

        Payment payment = orderSheet.approvedBy(mentor);
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentDto.from(savedPayment);
    }
}
