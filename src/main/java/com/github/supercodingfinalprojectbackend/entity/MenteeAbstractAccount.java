package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "mentee_abstract_account")
public class MenteeAbstractAccount extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentee_abstract_account_id")
    private Long menteeAccountId;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "paymoney")
    private Long paymoney;
}
