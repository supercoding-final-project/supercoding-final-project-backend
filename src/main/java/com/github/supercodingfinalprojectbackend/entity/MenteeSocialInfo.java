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
@Table(name = "mentee_social_infos")
public class MenteeSocialInfo extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentee_social_info_id")
    private int id;
    @Column(name = "mentee_id")
    private int menteeId;
    @Column(name = "social_id")
    private long socialId;
    @Column(name = "social_platform")
    private String socialPlatform;
}
