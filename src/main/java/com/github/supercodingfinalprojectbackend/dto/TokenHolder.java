package com.github.supercodingfinalprojectbackend.dto;

import java.util.HashMap;
import java.util.Map;

public class TokenHolder {
    private final Map<String, String> tokenMap = new HashMap<>(2);
    public static final String ACCESS_TOKEN_KEY_NAME = "access_token";
    public static final String REFRESH_TOKEN_KEY_NAME = "refresh_token";

    public TokenHolder() {}

    public TokenHolder putAccessToken(String accessToken) {
        tokenMap.put(ACCESS_TOKEN_KEY_NAME, accessToken);
        return this;
    }
    public TokenHolder putRefreshToken(String refreshToken) {
        tokenMap.put(REFRESH_TOKEN_KEY_NAME, refreshToken);
        return this;
    }
    public String getAccessToken() { return tokenMap.get(ACCESS_TOKEN_KEY_NAME); }
    public String getRefreshTokenKeyName() { return tokenMap.get(REFRESH_TOKEN_KEY_NAME); }
}
