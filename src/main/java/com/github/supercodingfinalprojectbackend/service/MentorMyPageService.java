package com.github.supercodingfinalprojectbackend.service;


import com.github.supercodingfinalprojectbackend.dto.MentorMyPageDto;
import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import com.github.supercodingfinalprojectbackend.entity.Payment;
import com.github.supercodingfinalprojectbackend.entity.Posts;
import com.github.supercodingfinalprojectbackend.entity.SelectedClassTime;
import com.github.supercodingfinalprojectbackend.repository.OrderSheetRepository;
import com.github.supercodingfinalprojectbackend.repository.PaymentRepository;
import com.github.supercodingfinalprojectbackend.repository.SelectedClassTimeRepository;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MentorMyPageService {
    private final OrderSheetRepository orderSheetRepository;
    private final SelectedClassTimeRepository selectedClassTimeRepository;
    private final PaymentRepository paymentRepository;
    public ResponseEntity<?> getOrderList(Long userId) {

        List<MentorMyPageDto.ResponseOrderDto> responseOrderDtoList = new ArrayList<>();

            List<OrderSheet> orderSheet = orderSheetRepository.findAllByPostMentorUserUserIdAndIsCompletedIsFalse(userId);
            responseOrderDtoList.addAll(orderSheet.stream()
                    .sorted(Comparator.comparing(OrderSheet::getCreatedAt).reversed())
                    .map(MentorMyPageDto::from)
                    .collect(Collectors.toList()));
        MentorMyPageDto.ResponseOrderList responseOrderList = MentorMyPageDto.ResponseOrderList.builder()
                .orderDtoList(responseOrderDtoList)
                .build();
        return ResponseUtils.ok("성공적으로 조회하였습니다", responseOrderList);
    }

    public ResponseEntity<?> getTransactionList(Long userId) {

            List<OrderSheet> orderSheet = orderSheetRepository.findAllByPostMentorUserUserIdAndIsCompletedTrue(userId);


            List<MentorMyPageDto.ResponseTransactionDto> lists = new ArrayList<>();

            for (OrderSheet orderSheets : orderSheet) {
                Payment payments = paymentRepository.findByOrderSheet_OrderSheetId(orderSheets.getOrderSheetId());
                List<SelectedClassTime> selectedClassTime = selectedClassTimeRepository.findAllByOrderSheet(orderSheets);
                List<String> convertTime =  convertToFormattedStrings(selectedClassTime);
                Posts posts = orderSheets.getPost();
                MentorMyPageDto.ResponseTransactionDto responseTransactionDto = MentorMyPageDto.from(convertTime,posts,orderSheets,payments);
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
    }
