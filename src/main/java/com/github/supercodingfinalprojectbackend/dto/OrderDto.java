package com.github.supercodingfinalprojectbackend.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderDto {
    private Long orderSheetId;

    public static OrderDto from(OrderIdRequest request) {
        return OrderDto.builder()
                .orderSheetId(request.orderSheetId)
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class OrderIdRequest {
        private Long orderSheetId;
    }
}
