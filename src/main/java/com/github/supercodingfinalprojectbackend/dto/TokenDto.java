package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.util.jwt.JwtUtils;
import com.github.supercodingfinalprojectbackend.util.jwt.TokenHolder;
import lombok.*;

public class TokenDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class RefreshTokenRequest {
        private String refreshToken;

        public boolean validate() {
            return refreshToken != null;
        }

        public String getRefreshToken() {
            return JwtUtils.cutPrefix(refreshToken);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Response {
        private String accessToken;
        private String refreshToken;

        public static Response from(TokenHolder tokenHolder) {
            return Response.builder()
                    .accessToken(JwtUtils.prefix(tokenHolder.getAccessToken()))
                    .refreshToken(JwtUtils.prefix(tokenHolder.getRefreshToken()))
                    .build();
        }
    }
}
