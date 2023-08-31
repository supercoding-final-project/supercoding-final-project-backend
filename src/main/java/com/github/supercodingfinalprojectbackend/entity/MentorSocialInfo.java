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
@Table(name = "montor_social_infos")
public class MentorSocialInfo extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_social_info_id")
    private int id;
    @Column(name = "mentor_id")
    private int mentorId;
    @Column(name = "social_id")
    private long socialId;
    @Column(name = "social_platform")
    private String socialPlatform;
}
