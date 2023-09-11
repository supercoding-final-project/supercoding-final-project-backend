package com.github.supercodingfinalprojectbackend.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymoneyDto {
    private Long paymoney;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ChargeRequest {
        private Long chargeAmount;
    }
}
