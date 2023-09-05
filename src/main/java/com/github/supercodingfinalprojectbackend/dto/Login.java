package com.github.supercodingfinalprojectbackend.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String roleName;

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long userId;
        private String accessToken;
        private String refreshToken;
        private String roleName;

        public static Response from(Login login) {
            Long userId = login.getUserId();
            String accessToken = "Bearer " + login.getAccessToken();
            String refreshToken = "Bearer " + login.getRefreshToken();
            String roleName = login.getRoleName();
            return new Response(userId, accessToken, refreshToken, roleName);
        }
    }
}
