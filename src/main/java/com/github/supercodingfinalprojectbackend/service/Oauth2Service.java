package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.KakaoOauthToken;
import com.github.supercodingfinalprojectbackend.dto.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUri;
    private final String kakaoGrantType = "authorization_code";
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    public KakaoOauthToken getToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<?> request = createKakaoTokenRequest(code);
        ResponseEntity<KakaoOauthToken> response = restTemplate.exchange(request, KakaoOauthToken.class);
        KakaoOauthToken kakaoOauthToken = Objects.requireNonNull(response.getBody());
        System.out.println(kakaoOauthToken);
        return kakaoOauthToken;
    }

    private RequestEntity<MultiValueMap<String, String>> createKakaoTokenRequest(String code) {
        URI uri = URI.create(kakaoTokenUri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", kakaoGrantType);
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", kakaoRedirectUri);
        body.add("code", code);

        return RequestEntity.post(uri).headers(headers).body(body);
    }

    public KakaoUserInfo getUserInfo(KakaoOauthToken kakaoOauthToken) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<?> request = createKakaoUserInfoRequest(kakaoOauthToken);
        ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(request, KakaoUserInfo.class);
        KakaoUserInfo userInfo = Objects.requireNonNull(response.getBody());
        System.out.println(userInfo);
        return userInfo;
    }

    private RequestEntity<Void> createKakaoUserInfoRequest(KakaoOauthToken kakaoOauthToken) {
        System.out.println(kakaoUserInfoUri);
        URI uri = URI.create(kakaoUserInfoUri);

        String accessToken = kakaoOauthToken.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return RequestEntity.get(uri).headers(headers).build();
    }
}
