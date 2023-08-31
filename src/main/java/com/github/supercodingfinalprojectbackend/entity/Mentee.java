package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "mentees")
public class Mentee extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentee_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private MenteeAbstractAccount abstractAccount;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;
}
