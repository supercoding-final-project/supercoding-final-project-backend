package com.github.supercodingfinalprojectbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoOauthTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_id")
    private Integer expiresIn;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("refresh_token_expires_in")
    private Integer refreshTokenExpiresIn;
}
