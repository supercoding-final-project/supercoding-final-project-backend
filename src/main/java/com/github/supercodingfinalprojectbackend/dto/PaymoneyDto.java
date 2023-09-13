package com.github.supercodingfinalprojectbackend.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class PaymoneyDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ChargeRequest {
        private Long chargeAmount;

        public boolean validate() {
            return chargeAmount != null && chargeAmount > 0;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ChargeResponse {
        private Long paymoney;

        public static ChargeResponse from(Long paymoney) {
            return new ChargeResponse(paymoney);
        }
    }
}
