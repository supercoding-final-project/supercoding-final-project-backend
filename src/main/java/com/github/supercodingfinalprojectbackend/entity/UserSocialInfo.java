package com.github.supercodingfinalprojectbackend.entity;

import com.github.supercodingfinalprojectbackend.entity.type.SocialPlatformType;
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

    public static UserSocialInfo of(User user, Long socialId, SocialPlatformType socialPlatformType) {
        return UserSocialInfo.builder()
                .user(user)
                .socialId(socialId)
                .socialPlatformName(socialPlatformType.name())
                .build();
    }
}
