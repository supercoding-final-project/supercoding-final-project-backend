package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.Login;
import com.github.supercodingfinalprojectbackend.dto.MentorCareerDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
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

import java.util.Set;
import java.util.stream.Collectors;

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

    @PostMapping(value = "/role/join/mentor")
    @Operation(summary = "멘토 등록")
    public ResponseEntity<ResponseUtils.ApiResponse<MentorDto.MentorInfoResponse>> joinMentor(@RequestBody MentorDto.JoinRequest request){
        System.out.println(request.toString());
        String company = request.getCompany();
        String introduction = request.getIntroduction();
        Set<MentorCareerDto.Request> careers = request.getCareers();
        Set<String> skillStackNames = request.getSkillStackNames();
        Set<SkillStackType> skillStackTypeSet = skillStackNames != null ? skillStackNames.stream().map(SkillStackType::findBySkillStackType).collect(Collectors.toSet()) : null;
        Set<MentorCareerDto> careerDtoSet;

        try {
            careerDtoSet = careers != null ? careers.stream().map(MentorCareerDto::from).collect(Collectors.toSet()) : null;
        } catch (IllegalArgumentException e) {
            throw ApiErrorCode.INVALID_PATH_VARIABLE.exception();
        }
        MentorDto mentorDto = oauth2Service.joinMentor(company, introduction, careerDtoSet, skillStackTypeSet);
        MentorDto.MentorInfoResponse response = MentorDto.MentorInfoResponse.from(mentorDto);
        return ResponseUtils.created("멘토 등록에 성공했습니다.", response);
    }
}
