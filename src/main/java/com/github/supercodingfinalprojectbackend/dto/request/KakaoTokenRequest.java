package com.github.supercodingfinalprojectbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

public class KakaoTokenRequest {
    @JsonProperty("grant_type")
    private final String grantType = "authorization_code";
    @JsonProperty("client_id")
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @JsonProperty("redirect_uri")
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;
    private String code;

    public KakaoTokenRequest(String code) {
        this.code = code;
    }
}
