package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.util.jwt.JwtUtils;
import com.github.supercodingfinalprojectbackend.util.jwt.TokenHolder;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TokenDto {
    private String accessToken;
    private String refreshToken;

    public static TokenDto from(TokenHolder tokenHolder) {
        return new TokenDto(tokenHolder.getAccessToken(), tokenHolder.getRefreshToken());
    }

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

        public static Response from (TokenDto tokenDto) {
            return Response.builder()
                    .accessToken(JwtUtils.prefix(tokenDto.accessToken))
                    .refreshToken(JwtUtils.prefix(tokenDto.refreshToken))
                    .build();
        }
    }
}
