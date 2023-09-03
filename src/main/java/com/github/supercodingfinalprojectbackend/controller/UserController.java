package com.github.supercodingfinalprojectbackend.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/oauth2/kakao/login")
    public void kakaoLogin(@RequestParam(name = "code") String code){
        System.out.println(code);
    }
}
