package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.Login;
import com.github.supercodingfinalprojectbackend.service.Oauth2Service;
import com.github.supercodingfinalprojectbackend.util.AuthUtils;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final Oauth2Service oauth2Service;

    @GetMapping("/oauth2/kakao/login")
    public ResponseEntity<ResponseUtils.ApiResponse<Login.Response>> kakaoLogin(@RequestParam(name = "code") String code){
        Login login = oauth2Service.kakaoLogin(code);
        Login.Response response = Login.Response.from(login);
        return ResponseUtils.ok("로그인에 성공했습니다.", response);
    }

    @GetMapping("/oauth2/kakao/logout")
    public ResponseEntity<ResponseUtils.ApiResponse<Void>> kakaoLogout() {
        oauth2Service.kakaoLogout();
        oauth2Service.serviceLogout();

        return ResponseUtils.noContent("로그아웃에 성공했습니다.", null);
    }

    @GetMapping("/switch/mentee")
    public ResponseEntity<ResponseUtils.ApiResponse<Login.Response>> switchToMentee() {
        Long userId = AuthUtils.getUserId();
        Login login = oauth2Service.switchToMentee(userId);
        Login.Response response = Login.Response.from(login);
        return ResponseUtils.ok("멘티로 성공적으로 전환했습니다.", response);
    }

    @GetMapping("/switch/mentor")
    public ResponseEntity<ResponseUtils.ApiResponse<Login.Response>> switchToMentor() {
        Long userId = AuthUtils.getUserId();
        Login login = oauth2Service.switchToMentor(userId);
        Login.Response response = Login.Response.from(login);
        return ResponseUtils.ok("멘토로 성공적으로 전환했습니다.", response);
    }
}
