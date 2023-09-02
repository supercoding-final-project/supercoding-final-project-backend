package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.service.OauthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final OauthService oauthService;

    @GetMapping("/login")
    public ResponseEntity<?> login () {
        System.out.println("요청 처리함!");
        oauthService.requestAuthorizationCode();

        return ResponseUtils.ok("이거슨 메세지여", "이거시 데이터여");
    }

    @GetMapping("/oauth2/kakao/callback")
    public void acceptKakaoAuthorizationCode(){
        System.out.println("callback!");
    }
}
