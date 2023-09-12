package com.github.supercodingfinalprojectbackend.service;


import com.github.supercodingfinalprojectbackend.dto.MenteeMyPageDto;
import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import com.github.supercodingfinalprojectbackend.entity.SelectedClassTime;
import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MenteeRepository;
import com.github.supercodingfinalprojectbackend.repository.OrderSheetRepository;
import com.github.supercodingfinalprojectbackend.repository.SelectedClassTimeRepository;
import com.github.supercodingfinalprojectbackend.repository.UserRepository;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MenteeMyPageService {
    private final UserRepository userRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final MenteeRepository menteeRepository;
    private final SelectedClassTimeRepository selectedClassTimeRepository;

    public ResponseEntity<?> changeNickName(MenteeMyPageDto myPageDto) {

        User user = userRepository.findByUserIdAndIsDeletedIsFalse(myPageDto.getUserId()).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);

        ValidateUtils.requireTrue(user.getNickname().matches(myPageDto.getNickname()), ApiErrorCode.MENTEE_MYPAGE_CHANGEINFO_BAD_REQUEST);

        MenteeMyPageDto.ResponseChangeInfo responseChangeInfo = MenteeMyPageDto.ResponseChangeInfo.builder().nickname(myPageDto.getNickname()).build();

        user.changeUserNameNickname(myPageDto.getNickname());

        return ResponseUtils.ok("닉네임 변경에 성공하였습니다.", responseChangeInfo);
    }

    public ResponseEntity<?> getOrderList(Long userId) {


        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);

        Mentee mentee = menteeRepository.findByUserAndIsDeletedIsFalse(user).orElseThrow(ApiErrorCode.NOT_FOUND_MENTEE::exception);

        List<OrderSheet> orderSheet = orderSheetRepository.findByMenteeAndIsDeletedIsFalse(mentee).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);

        // 주문 항목을 DTO로 변환
        List<MenteeMyPageDto.ResponseOrderDto> responseOrderDtoList = orderSheet.stream()
                .map(MenteeMyPageDto::from)
                .collect(Collectors.toList());

        // 응답 DTO를 생성
        MenteeMyPageDto.ResponseOrderList responseOrderList = MenteeMyPageDto.ResponseOrderList.builder()
                .orderDtoList(responseOrderDtoList)
                .build();

        return ResponseUtils.ok("성공적으로 조회하였습니다", responseOrderList);
    }

    public ResponseEntity<?> getMenteeTransactionList(Long userId) {
        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);
        Mentee mentee = menteeRepository.findByUserAndIsDeletedIsFalse(user).orElseThrow(ApiErrorCode.NOT_FOUND_MENTEE::exception);
        List<OrderSheet> orderSheet = orderSheetRepository.findByMenteeAndIsDeletedIsFalseAndIsCompletedIsTrue(mentee).orElseThrow(ApiErrorCode.NOT_FOUND_ORDERSHEET::exception);

        List<SelectedClassTime> selectedClassTime = selectedClassTimeRepository.findByOrderSheet(orderSheet);

        List<String> convertTime = convertToFormattedStrings(selectedClassTime);

//        List<MenteeMyPageDto.ResponseTransactionDto> responseTransactionDtos = orderSheet.stream()

//     TODO : 할것들이 많음
        return ResponseUtils.ok("성공적으로 조회하였습니다.", selectedClassTime);
    }

    private List<String> convertToFormattedStrings(List<SelectedClassTime> classTimes) {
        List<String> formattedClassTimes = classTimes.stream()
                .map(classTime -> {
                    int year = classTime.getYear();
                    int month = classTime.getMonth();
                    int day = classTime.getDay();
                    int hour = classTime.getHour();

                    // "년-월-일-시간" 형식의 문자열 생성
                    return year + "-" + month + "-" + day + "-" + hour + ":00";
                })
                .collect(Collectors.toList());

        return formattedClassTimes;
    }
}
