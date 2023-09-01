package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/login")
    public ApiResponse<String> login () {
        System.out.println("요청 처리함!");
        System.out.println(clientId);

        String url = "https://kauth.kakao.com/oauth/authorize";
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        System.out.println(response.getBody());

        return ApiResponse.success("데이터", "요청 성공적으로 처리됨");
    }
}
