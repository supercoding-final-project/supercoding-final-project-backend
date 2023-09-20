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
@Builder
public class MentorMyPageDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseOrderList{
        private List<MentorMyPageDto.ResponseOrderDto> orderDtoList;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseOrderDto{
        private Long orderId;
        private String postTitle;
        private String menteeNickname;
        private Integer totalPrice;
        private Boolean isCompleted;
    }

    public static MentorMyPageDto.ResponseOrderDto from(OrderSheet orderSheet){
        return ResponseOrderDto.builder()
                .orderId(orderSheet.getOrderSheetId())
                .postTitle(orderSheet.getPost().getTitle())
                .menteeNickname(orderSheet.getMentee().getUser().getNickname())
                .totalPrice(orderSheet.getTotalPrice())
                .isCompleted(orderSheet.getIsCompleted())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseTransactionDto{
        private String postName;
        private String menteeNickname;
        private List<String> transactionCalendars;
        private String email;
        private Integer totalPrice;
        private Instant createdAt;
    }

    public static MentorMyPageDto.ResponseTransactionDto from(List<String> selectedClassTime, Posts posts, OrderSheet orderSheet, Payment payment){
        return ResponseTransactionDto.builder()
                .email(orderSheet.getMentee().getUser().getEmail())
                .postName(posts.getTitle())
                .transactionCalendars(selectedClassTime)
                .createdAt(payment.getCreatedAt())
                .menteeNickname(orderSheet.getMentee().getUser().getNickname())
                .totalPrice(orderSheet.getTotalPrice())
                .build();
    }
}
