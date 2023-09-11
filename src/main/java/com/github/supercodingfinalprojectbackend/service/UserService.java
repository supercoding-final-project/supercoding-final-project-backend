package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.entity.UserAbstractAccount;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long chargePaymoney(Long userId, Long chargeAmount) {
        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);
        UserAbstractAccount userAbstractAccount = user.getAbstractAccount();
        return userAbstractAccount.chargePaymoney(chargeAmount);
    }
}
