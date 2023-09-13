package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.util.jwt.JwtUtils;
import com.github.supercodingfinalprojectbackend.util.jwt.TokenHolder;
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

    public static Login of(UserRole userRole, TokenHolder tokenHolder) {
        return Login.builder()
                .userRole(userRole)
                .accessToken(tokenHolder.getAccessToken())
                .refreshToken(tokenHolder.getRefreshToken())
                .build();
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String accessToken;
        private String refreshToken;
        private String roleName;

        public static Response from(Login login) {
            return Response.builder()
                    .accessToken(JwtUtils.prefix(login.accessToken))
                    .refreshToken(JwtUtils.prefix(login.refreshToken))
                    .roleName(login.userRole.resolve().name())
                    .build();
        }
    }
}
