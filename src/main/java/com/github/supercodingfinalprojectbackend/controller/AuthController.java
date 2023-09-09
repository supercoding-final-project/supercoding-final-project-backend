package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.Login;
import com.github.supercodingfinalprojectbackend.dto.RefreshTokenDTO;
import com.github.supercodingfinalprojectbackend.service.Oauth2Service;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final Oauth2Service oauth2Service;

    @PostMapping("/token/refresh")
    public ResponseEntity<ResponseUtils.ApiResponse<Login.Response>> renewTokens(@RequestBody RefreshTokenDTO request) {
        String refreshToken = request.getRefreshToken();

        ValidateUtils.requireNotNull(refreshToken, 401, "리프레쉬 토큰이 존재하지 않습니다.");

        Login login =  oauth2Service.renewTokens(refreshToken);
        Login.Response response = Login.Response.from(login);
        return ResponseUtils.ok("토큰이 성공적으로 갱신되었습니다.", response);
    }
}
