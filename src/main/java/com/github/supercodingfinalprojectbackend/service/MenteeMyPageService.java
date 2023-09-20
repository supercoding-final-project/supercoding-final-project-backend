package com.github.supercodingfinalprojectbackend.service;


import com.github.supercodingfinalprojectbackend.dto.MenteeMyPageDto;
import com.github.supercodingfinalprojectbackend.entity.*;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.OrderSheetRepository;
import com.github.supercodingfinalprojectbackend.repository.PaymentRepository;
import com.github.supercodingfinalprojectbackend.repository.SelectedClassTimeRepository;
import com.github.supercodingfinalprojectbackend.repository.UserRepository;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MenteeMyPageService {
    private final UserRepository userRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final SelectedClassTimeRepository selectedClassTimeRepository;
    private final PaymentRepository paymentRepository;
    private final S3Service s3Service;
    public ResponseEntity<?> changeNickName(Long userId, String menteeNickname) {

        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);

        ValidateUtils.requireTrue(!(user.getNickname().matches(menteeNickname)), ApiErrorCode.MENTEE_MYPAGE_CHANGEINFO_BAD_REQUEST);

        MenteeMyPageDto.ResponseChangeInfo responseChangeInfo = MenteeMyPageDto.ResponseChangeInfo.builder().nickname(menteeNickname).build();

        user.changeUserNameNickname(menteeNickname);

        return ResponseUtils.ok("닉네임 변경에 성공하였습니다.", responseChangeInfo);
    }

    public ResponseEntity<?> getOrderList(Long userId) {

        List<OrderSheet> orderSheet = orderSheetRepository.findAllByMenteeUserUserIdAndIsCompletedIsFalse(userId);

        List<MenteeMyPageDto.ResponseOrderDto> responseOrderDtoList = orderSheet.stream()
                .sorted(Comparator.comparing(OrderSheet::getCreatedAt).reversed())
                .map(MenteeMyPageDto::from)
                .collect(Collectors.toList());

        MenteeMyPageDto.ResponseOrderList responseOrderList = MenteeMyPageDto.ResponseOrderList.builder()
                .orderDtoList(responseOrderDtoList)
                .build();

        return ResponseUtils.ok("성공적으로 조회하였습니다", responseOrderList);
    }

        public ResponseEntity<?> getMenteeTransactionList(Long userId) {

            List<OrderSheet> orderSheet = orderSheetRepository.findAllByMenteeUserUserIdAndIsCompletedIsTrue(userId);


            List<MenteeMyPageDto.ResponseTransactionDto> lists = new ArrayList<>();


            for (OrderSheet orderSheets : orderSheet) {
                Payment payments = paymentRepository.findByOrderSheet_OrderSheetId(orderSheets.getOrderSheetId());
                List<SelectedClassTime> selectedClassTime = selectedClassTimeRepository.findAllByOrderSheet(orderSheets);
                List<String> convertTime =  convertToFormattedStrings(selectedClassTime);
                Posts posts = orderSheets.getPost();
                MenteeMyPageDto.ResponseTransactionDto responseTransactionDto = MenteeMyPageDto.from(convertTime,posts,orderSheets,payments);
                lists.add(responseTransactionDto);
            }
            lists.sort(Comparator.comparing(dto -> dto.getCreatedAt(),Comparator.reverseOrder()));
            return ResponseUtils.ok("성공적으로 조회하였습니다.", lists);
        }

    private List<String> convertToFormattedStrings(List<SelectedClassTime> classTimes) {
        List<String> formattedClassTimes = classTimes.stream()
                .map(classTime -> {
                    int year = classTime.getYear();
                    int month = classTime.getMonth();
                    int day = classTime.getDay();
                    int hour = classTime.getHour();
                    LocalDateTime dateTime = LocalDateTime.of(year,month,day,hour,0);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");
                    String formattedDateTime = dateTime.format(formatter);
                    return formattedDateTime;
                })
                .collect(Collectors.toList());

        return formattedClassTimes;
    }

    public ResponseEntity<?> getMenteeCalendersList(MenteeMyPageDto.RequestCalendersList requestCalendersList) {
        List<SelectedClassTime> selectedClassTimes =  selectedClassTimeRepository.findAllByMenteeUserUserIdAndMonth(requestCalendersList.getUserId(), requestCalendersList.getMonth());

        List<MenteeMyPageDto.ResponseCalenderList> calendarList = new ArrayList<>();

        Map<Integer, List<SelectedClassTime>> groupedByMonth = selectedClassTimes.stream()
                .collect(Collectors.groupingBy(SelectedClassTime::getMonth));

        for (Map.Entry<Integer, List<SelectedClassTime>> entry : groupedByMonth.entrySet()) {
            Integer month = entry.getKey();
            List<SelectedClassTime> monthSelectedClassTimes = entry.getValue();

            Map<Integer, List<SelectedClassTime>> groupedByDay = monthSelectedClassTimes.stream()
                    .collect(Collectors.groupingBy(SelectedClassTime::getDay));

            List<MenteeMyPageDto.ReservationDate> reservationDates = new ArrayList<>();
            for (Map.Entry<Integer, List<SelectedClassTime>> dayEntry : groupedByDay.entrySet()) {
                Integer day = dayEntry.getKey();
                List<SelectedClassTime> daySelectedClassTimes = dayEntry.getValue();

                List<MenteeMyPageDto.MentorReservation> mentorReservations = daySelectedClassTimes.stream()
                        .map(selectedClassTime -> new MenteeMyPageDto.MentorReservation(
                                selectedClassTime.getMentor().getUser().getNickname()
                        ))
                        .collect(Collectors.toList());

                reservationDates.add(new MenteeMyPageDto.ReservationDate(day, mentorReservations));
            }
            calendarList.add(new MenteeMyPageDto.ResponseCalenderList(month, reservationDates));
        }

        return ResponseEntity.ok(calendarList);

    }

    public ResponseEntity<?> changeUserImage(Long userId, MultipartFile multipartFile) {
        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);
        try {
            String userImage = s3Service.uploadImageFile(multipartFile);
            user.changeThumbnailImageUrl(userImage);
            return ResponseUtils.ok("성공적으로 이미지를 변경하였습니다", userImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
