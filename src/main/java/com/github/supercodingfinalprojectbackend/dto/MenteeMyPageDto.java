package com.github.supercodingfinalprojectbackend.dto;


import com.github.supercodingfinalprojectbackend.entity.*;
import lombok.*;

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
        private Long postId;
        private Long menteeId;
        private Long totalPrice;
        private Boolean isCompleted = false;
    }

    public static ResponseOrderDto from(OrderSheet orderSheet){
        return ResponseOrderDto.builder()
                .orderId(orderSheet.getOrderSheetId())
                .postId(orderSheet.getPost().getPostId())
                .menteeId(orderSheet.getMentee().getMenteeId())
                .totalPrice(Long.valueOf(orderSheet.getTotlaPrice()))
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
        private List<TransactionCalendar> transactionCalendars;
        private String email;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionCalendar{
        private String Calendar;
    }

    public static ResponseTransactionDto from(OrderSheet orderSheet, Posts posts, SelectedClassTime selectedClassTime){
        return ResponseTransactionDto.builder()
                .email(posts.getMentor().getUser().getEmail())
                .postName(posts.getTitle())
                .mentorNickname(posts.getMentor().getUser().getNickname())
                .build();
    }
}
