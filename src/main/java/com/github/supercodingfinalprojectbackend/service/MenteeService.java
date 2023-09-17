package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.MenteeDto;
import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MenteeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenteeService {

    private final MenteeRepository menteeRepository;

    public void saveMenteeInfo(Long userId, MenteeDto.MenteeInfoRequest request) {
        Mentee mentee = menteeRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_MENTEE::exception);
        mentee.changeInfo(request.getEmail(), request.getNickname(), request.getThumbnailImageUrl());
    }
}
