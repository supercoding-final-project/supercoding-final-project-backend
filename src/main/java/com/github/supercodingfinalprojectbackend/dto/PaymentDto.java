package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Payment;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymentDto {
    private Long paymentId;
    private OrderSheetDto orderSheet;
    private UserAbstractAccountDto sellerAbstractAccount;
    private UserAbstractAccountDto consumerAbstarctAccount;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class PaymentIdResponse {
        private Long paymentId;

        public static PaymentIdResponse from(Payment payment) {
            return new PaymentIdResponse(payment.getPaymentId());
        }
    }
}
