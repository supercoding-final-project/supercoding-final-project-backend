package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "userSocialInfoId", callSuper = false)
@Table(name = "user_social_infos")
public class UserSocialInfo extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_social_info_id")
    private Long userSocialInfoId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "social_id")
    private Long socialId;
    @Column(name = "social_platform_name")
    private String socialPlatformName;
}
