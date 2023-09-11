package com.github.supercodingfinalprojectbackend.service;


import com.github.supercodingfinalprojectbackend.dto.MenteeMyPageDto;
import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.UserRepository;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MenteeMyPageService {
    private final UserRepository userRepository;

    public ResponseEntity<?> changeNickName(MenteeMyPageDto myPageDto){

        User user = userRepository.findByUserIdAndIsDeletedIsFalse(myPageDto.getUserId()).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);

        MenteeMyPageDto.ResponseChangeInfo responseChangeInfo = new MenteeMyPageDto.ResponseChangeInfo();

        if (user.getNickname().matches(myPageDto.getNickname())){
            throw new ApiException(ApiErrorCode.MYPAGE_CHANGEINFO_BAD_REQUEST);
        }

        responseChangeInfo.setNickname(myPageDto.getNickname());

        user.setNickname(myPageDto.getNickname());

        return ResponseUtils.ok("닉네임 변경에 성공하였습니다.",responseChangeInfo);
    }
}
