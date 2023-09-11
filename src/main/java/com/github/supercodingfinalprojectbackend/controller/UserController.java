package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MentorCareerDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.dto.PaymoneyDto;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import com.github.supercodingfinalprojectbackend.service.Oauth2Service;
import com.github.supercodingfinalprojectbackend.service.UserService;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
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
    private final UserService userService;

    @PostMapping(value = "/role/join/mentor")
    @Operation(summary = "멘토 등록")
    public ResponseEntity<ResponseUtils.ApiResponse<MentorDto.MentorInfoResponse>> joinMentor(
            @RequestBody @Parameter(name = "멘토 등록 요청 객체", required = true) MentorDto.JoinRequest request
    ){
        String company = request.getCompany();
        String introduction = request.getIntroduction();
        Set<MentorCareerDto.Request> careers = request.getCareers();
        Set<String> skillStackNames = request.getSkillStackNames();

        Set<SkillStackType> skillStackTypeSet = skillStackNames != null ? skillStackNames.stream().map(SkillStackType::findBySkillStackType).collect(Collectors.toSet()) : null;
        Set<MentorCareerDto> careerDtoSet = ValidateUtils.requireApply(careers, c->c.stream().map(MentorCareerDto::from).collect(Collectors.toSet()), 400, "careers는 null이 될 수 없습니다.");

        MentorDto mentorDto = oauth2Service.joinMentor(company, introduction, careerDtoSet, skillStackTypeSet);

        MentorDto.MentorInfoResponse response = MentorDto.MentorInfoResponse.from(mentorDto);
        return ResponseUtils.created("멘토 등록에 성공했습니다.", response);
    }


    @PostMapping("/paymoney")
    @Operation(summary = "페이머니 충전")
    public ResponseEntity<ResponseUtils.ApiResponse<PaymoneyDto>> chargePaymoney(@RequestBody PaymoneyDto.ChargeRequest request) {
        Long userId = AuthUtils.getUserId();
        Long chargeAmount = request.getChargeAmount();

        Long chargePaymoney = userService.chargePaymoney(userId, chargeAmount);
        PaymoneyDto response = new PaymoneyDto(chargePaymoney);
        return ResponseUtils.ok("페이머니를 성공적으로 충전했습니다!", response);
    }

}
