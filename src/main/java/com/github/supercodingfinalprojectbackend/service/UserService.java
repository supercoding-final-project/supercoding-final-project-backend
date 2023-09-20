package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.PaymoneyDto;
import com.github.supercodingfinalprojectbackend.dto.UserDto;
import com.github.supercodingfinalprojectbackend.dto.UserDto.UserInfoResponse;
import com.github.supercodingfinalprojectbackend.dto.UserDto.UserThumbnailImageUrlResponse;
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
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MenteeRepository menteeRepository;
    private final MentorRepository mentorRepository;
    private final S3Service s3Service;

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

    public UserInfoResponse getUserInfo(Long userId) {
        Mentee mentee = menteeRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_MENTEE::exception);
        Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElse(null);
        return UserInfoResponse.of(mentee, mentor);
    }

    public UserThumbnailImageUrlResponse changeUserThumbnailImageUrl(Long userId, MultipartFile imageFile) throws IOException {
        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);
        String changedThumbnailImageUrl = s3Service.uploadImageFile(imageFile);
        user.changeThumbnailImageUrl(changedThumbnailImageUrl);
        return UserThumbnailImageUrlResponse.from(changedThumbnailImageUrl);
    }
}
