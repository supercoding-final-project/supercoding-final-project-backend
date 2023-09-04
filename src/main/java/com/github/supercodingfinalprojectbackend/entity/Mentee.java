package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "menteeId", callSuper = false)
@Table(name = "mentees")
public class Mentee extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentee_id")
    private Long menteeId;

    @OneToOne
    @JoinColumn(name = "account_id")
    private MenteeAbstractAccount abstractAccount;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;
}
