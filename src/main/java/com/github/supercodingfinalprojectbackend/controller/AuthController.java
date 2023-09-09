package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.Login;
import com.github.supercodingfinalprojectbackend.dto.TokenDto;
import com.github.supercodingfinalprojectbackend.service.Oauth2Service;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.github.supercodingfinalprojectbackend.util.jwt.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final Oauth2Service oauth2Service;

    @GetMapping("/login/kakao")
    @Operation(summary = "카카오 로그인")
    public ResponseEntity<ResponseUtils.ApiResponse<Login.Response>> kakaoLogin(
            @RequestParam(name = "code") @Parameter(name = "카카오 인가 코드", required = true) String code
    ){
        Login login = oauth2Service.kakaoLogin(code);
        Login.Response response = Login.Response.from(login);
        return ResponseUtils.ok("로그인에 성공했습니다.", response);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<ResponseUtils.ApiResponse<TokenDto.Response>> renewTokens(@RequestBody TokenDto.RefreshTokenRequest request) {
        String bearerToken = request.getRefreshToken();

        ValidateUtils.requireNotNull(bearerToken, 401, "리프레쉬 토큰이 존재하지 않습니다.");
        String refreshToken = JwtUtils.cutPrefix(bearerToken);

        TokenDto tokenDto =  oauth2Service.renewTokens(refreshToken);
        TokenDto.Response response = TokenDto.Response.from(tokenDto);
        return ResponseUtils.ok("토큰이 성공적으로 갱신되었습니다.", response);
    }
}
