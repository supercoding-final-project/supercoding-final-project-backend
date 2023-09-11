package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.dto.PaymoneyDto;
import com.github.supercodingfinalprojectbackend.service.Oauth2Service;
import com.github.supercodingfinalprojectbackend.service.UserService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final Oauth2Service oauth2Service;
    private final UserService userService;

    @PostMapping(value = "/role/join/mentor")
    @Operation(summary = "멘토 등록")
    public ResponseEntity<ResponseUtils.ApiResponse<MentorDto.JoinResponse>> joinMentor(
            @RequestBody @Parameter(name = "멘토 등록 요청 객체", required = true) MentorDto.JoinRequest request
    ){
        Long userId = AuthUtils.getUserId();
        MentorDto mentorDtoRequest = MentorDto.from(request);

        MentorDto mentorDto = oauth2Service.joinMentor(userId, mentorDtoRequest);

        MentorDto.JoinResponse response = MentorDto.JoinResponse.from(mentorDto);
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
