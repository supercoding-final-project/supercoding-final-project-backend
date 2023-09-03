package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.response.ApiResponse;
import com.github.supercodingfinalprojectbackend.dto.response.JsonResponse;
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
    public JsonResponse<String> login () {
        System.out.println("요청 처리함!");
        System.out.println(clientId);

        String url = "https://kauth.kakao.com/oauth/authorize";
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        System.out.println(response.getBody());

        return JsonResponse.<String>builder()
                .status(200)
                .message("이거슨 메세지여")
                .data("이거시 데이터여")
                .build();
    }
}
