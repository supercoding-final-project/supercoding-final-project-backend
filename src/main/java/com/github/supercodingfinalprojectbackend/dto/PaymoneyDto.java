package com.github.supercodingfinalprojectbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "페이머니 충전 요청 객체")
    public static class ChargeRequest {
        @Schema(name = "페이머니 충전 금액")
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
