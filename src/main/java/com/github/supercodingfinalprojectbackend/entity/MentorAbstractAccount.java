package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "mentorAbstractAccountId", callSuper = false)
@Table(name = "mentor_abstract_account")
public class MentorAbstractAccount extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_abstract_account_id")
    private Long mentorAbstractAccountId;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "pay_money")
    private Long payMoney;
}
