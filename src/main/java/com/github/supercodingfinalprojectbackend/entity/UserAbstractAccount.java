package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "abstractAccountId", callSuper = false)
@Table(name = "user_abstract_accounts")
public class UserAbstractAccount extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "abstract_account_id")
    private Long abstractAccountId;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "paymoney")
    private Long paymoney;

    public Long chargePaymoney(Long chargeAmount) {
        return this.paymoney += chargeAmount;
    }

    public void spendPayMoney(Integer price) {
        this.paymoney -= price;
    }
}
