package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.response.KakaoOauthTokenResponse;
import lombok.RequiredArgsConstructor;
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

    public void getToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<?> request = createKakaoTokenRequest(code);
        ResponseEntity<KakaoOauthTokenResponse> response = restTemplate.exchange(request, KakaoOauthTokenResponse.class);
        KakaoOauthTokenResponse kakaoOauthTokenResponse = Objects.requireNonNull(response.getBody());
        System.out.println(kakaoOauthTokenResponse);
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
}
