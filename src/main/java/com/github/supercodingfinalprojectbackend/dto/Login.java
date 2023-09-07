package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.type.SocialPlatformType;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {
    private String accessToken;
    private String refreshToken;
    private UserRole userRole;
    private SocialPlatformType socialPlatformType;
    private String socialAccessToken;

    public String getKakaoToken() {
        return socialPlatformType.equals(SocialPlatformType.KAKAO) ? socialAccessToken : null;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String accessToken;
        private String refreshToken;
        private String roleName;

        public static Response from(Login login) {
            String accessToken = "Bearer " + login.accessToken;
            String refreshToken = "Bearer " + login.refreshToken;
            String roleName = login.userRole.name();
            return new Response(accessToken, refreshToken, roleName);
        }
    }
}
