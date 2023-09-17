package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.PaymoneyDto;
import com.github.supercodingfinalprojectbackend.dto.UserDto;
import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.entity.UserAbstractAccount;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MenteeRepository;
import com.github.supercodingfinalprojectbackend.repository.MentorRepository;
import com.github.supercodingfinalprojectbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MenteeRepository menteeRepository;
    private final MentorRepository mentorRepository;

    public PaymoneyDto.ChargeResponse chargePaymoney(Long userId, PaymoneyDto.ChargeRequest request) {
        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);
        UserAbstractAccount userAbstractAccount = user.getAbstractAccount();
        Long paymoney = userAbstractAccount.chargePaymoney(request.getChargeAmount());
        return PaymoneyDto.ChargeResponse.from(paymoney);
    }

    public PaymoneyDto.Response getPaymoney(Long userId) {
        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);
        return PaymoneyDto.Response.from(user.getAbstractAccount().getPaymoney());
    }

    public UserDto.UserInfoResponse getUserInfo(Long userId) {
        Mentee mentee = menteeRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_MENTEE::exception);
        Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElse(null);
        return UserDto.UserInfoResponse.of(mentee, mentor);
    }
}
