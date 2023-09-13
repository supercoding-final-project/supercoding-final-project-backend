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
    @JoinColumn(name = "user_id")
    private User user;

    public static Mentee from(User user) {
        return Mentee.builder()
                .user(user)
                .build();
    }
}
