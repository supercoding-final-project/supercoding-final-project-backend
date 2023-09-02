package com.github.supercodingfinalprojectbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class OauthService {
    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String kakaoAuthorizationUri;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<?> requestAuthorizationCode() {
        System.out.println(kakaoAuthorizationUri);
        URI uri = URI.create(String.format("%s?response_type=code&client_id=%s&redirect_uri=%s", kakaoAuthorizationUri, kakaoClientId, kakaoRedirectUri));
        String response = restTemplate.getForObject(uri, String.class);
        System.out.println(response);
        return ResponseEntity.status(200).contentType(MediaType.TEXT_HTML).body(response);
    }
}
