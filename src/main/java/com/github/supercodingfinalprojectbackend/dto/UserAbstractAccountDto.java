package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.UserAbstractAccount;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserAbstractAccountDto {
    private Long abstractAccountId;
    private String accountNumber;
    private Long paymoney;

    public static UserAbstractAccountDto from(UserAbstractAccount abstractAccount) {
        return UserAbstractAccountDto.builder()
                .abstractAccountId(abstractAccount.getAbstractAccountId())
                .accountNumber(abstractAccount.getAccountNumber())
                .paymoney(abstractAccount.getPaymoney())
                .build();
    }
}
