package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.util.jwt.JwtUtils;
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

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String accessToken;
        private String refreshToken;
        private String roleName;

        public static Response from(Login login) {
            String accessToken = JwtUtils.prefix(login.accessToken);
            String refreshToken = JwtUtils.prefix(login.refreshToken);
            String roleName = login.userRole.name();
            return new Response(accessToken, refreshToken, roleName);
        }
    }
}
