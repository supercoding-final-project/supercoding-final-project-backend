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

    public static PaymentDto from(Payment payment) {
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .orderSheet(OrderSheetDto.from(payment.getOrderSheet()))
                .sellerAbstractAccount(UserAbstractAccountDto.from(payment.getSellerAbstractAccount()))
                .consumerAbstarctAccount(UserAbstractAccountDto.from(payment.getConsumerAbstarctAccount()))
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class PaymentIdResponse {
        private Long paymentId;

        public static PaymentIdResponse from(PaymentDto paymentDto) {
            return new PaymentIdResponse(paymentDto.getPaymentId());
        }
    }
}
