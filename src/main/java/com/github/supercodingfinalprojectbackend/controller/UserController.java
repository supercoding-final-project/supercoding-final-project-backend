package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.Login;
import com.github.supercodingfinalprojectbackend.dto.MenteeDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
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

    @GetMapping("/switch/{roleName}")
    public ResponseEntity<?> switchUserRole(@PathVariable String roleName) {
        UserRole userRole;
        try {
            userRole = UserRole.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw ApiErrorCode.INVALID_PATH_VARIABLE.exception();
        }

        switch (userRole) {
            case MENTEE:
                return switchToMentee();
            case MENTOR:
                return switchToMentor();
            default:
                return ResponseUtils.internalServerError("이 응답은 절대 내려갈 일 없는 응답입니다.", null);
        }
    }

    private ResponseEntity<ResponseUtils.ApiResponse<MenteeDto>> switchToMentee() {
        Long userId = AuthUtils.getUserId();
        Mentee mentee = oauth2Service.switchToMentee(userId);
        MenteeDto response = MenteeDto.from(mentee);
        return ResponseUtils.ok("멘티로 성공적으로 전환했습니다.", response);
    }

    private ResponseEntity<ResponseUtils.ApiResponse<MentorDto>> switchToMentor() {
        Long userId = AuthUtils.getUserId();
        Mentor mentor = oauth2Service.switchToMentor(userId);
        MentorDto response = MentorDto.fromEntity(mentor);
        return ResponseUtils.ok("멘토로 성공적으로 전환했습니다.", response);
    }
}
