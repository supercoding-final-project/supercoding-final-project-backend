package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.Login;
import com.github.supercodingfinalprojectbackend.entity.MentorCareer;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.service.Oauth2Service;
import com.github.supercodingfinalprojectbackend.util.AuthUtils;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final Oauth2Service oauth2Service;

    @GetMapping("/oauth2/kakao/login")
    @Operation(summary = "카카오 로그인")
    public ResponseEntity<ResponseUtils.ApiResponse<Login.Response>> kakaoLogin(
            @RequestParam(name = "code") @Parameter(name = "카카오 인가 코드", required = true) String code
    ){
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

    @GetMapping("/switch/{roleName}")
    @Operation(summary = "역할 전환")
    public ResponseEntity<ResponseUtils.ApiResponse<Login.Response>> switchRole(
            @PathVariable(name = "roleName") @Parameter(name = "역할 이름", required = true) String roleName) {
        UserRole userRole;
        try {
            userRole = UserRole.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw ApiErrorCode.INVALID_PATH_VARIABLE.exception();
        }
        Long userId = AuthUtils.getUserId();
        Login login = oauth2Service.switchRole(userId, userRole);
        Login.Response response = Login.Response.from(login);
        return ResponseUtils.ok("역할을 성공적으로 전환했습니다.", response);
    }

    @PostMapping(value = "/role/join/mentor", consumes = {"application/x-www-form-urlencoded; charset=utf-8"})
    @Operation(summary = "멘토 등록")
    public void joinMentor(
            @RequestParam(name = "company") @Parameter(name = "현재 다니는 회사", required = true) String company,
            @RequestParam(name = "introduction") @Parameter(name = "멘토 소개글", required = true) String introduction,
            @RequestParam(name = "careers", required = false) @Parameter(name = "커리어들") List<MentorCareer> careers,
            @RequestParam(name = "skills", required = false) @Parameter(name = "기술스택들") List<String> skills
    ){

    }
}
