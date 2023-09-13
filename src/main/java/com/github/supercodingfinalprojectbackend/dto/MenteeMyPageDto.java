package com.github.supercodingfinalprojectbackend.dto;


import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import com.github.supercodingfinalprojectbackend.entity.Payment;
import com.github.supercodingfinalprojectbackend.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenteeMyPageDto {
    private Long userId;
    private String nickname;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class  ResponseChangeInfo{
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseOrderList{
        private List<ResponseOrderDto> orderDtoList;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseOrderDto{
        private Long orderId;
        private String postTitle;
        private String mentorNickname;
        private Integer totalPrice;
        private Boolean isCompleted;
    }

    public static ResponseOrderDto from(OrderSheet orderSheet){
        return ResponseOrderDto.builder()
                .orderId(orderSheet.getOrderSheetId())
                .postTitle(orderSheet.getPost().getTitle())
                .mentorNickname(orderSheet.getPost().getMentor().getUser().getNickname())
                .totalPrice(orderSheet.getTotlaPrice())
                .isCompleted(orderSheet.getIsCompleted())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseTransactionList{
        private List<ResponseTransactionDto> transactionDtoList;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseTransactionDto{
        private String postName;
        private String mentorNickname;
        private List<String> transactionCalendars;
        private String email;
        private Integer totalPrice;
        private Instant createdAt;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionCalendar{
        private String Calendar;
    }

    public static ResponseTransactionDto from(List<String> selectedClassTime, Posts posts, OrderSheet orderSheet, Payment payment){
        return ResponseTransactionDto.builder()
                .email(posts.getMentor().getUser().getEmail())
                .postName(posts.getTitle())
                .transactionCalendars(selectedClassTime)
                .mentorNickname(posts.getMentor().getUser().getNickname())
                .totalPrice(orderSheet.getTotlaPrice())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
