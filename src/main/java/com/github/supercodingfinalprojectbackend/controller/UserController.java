package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.dto.PaymoneyDto;
import com.github.supercodingfinalprojectbackend.dto.UserDto.UserInfoResponse;
import com.github.supercodingfinalprojectbackend.dto.UserDto.UserThumbnailImageUrlResponse;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.service.Oauth2Service;
import com.github.supercodingfinalprojectbackend.service.UserService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils.ApiResponse;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "회원 API")
public class UserController {

    private final Oauth2Service oauth2Service;
    private final UserService userService;

    @PostMapping(value = "/role/join/mentor")
    @Operation(summary = "멘토 등록")
    public ResponseEntity<ApiResponse<MentorDto.JoinResponse>> joinMentor(@RequestBody MentorDto.JoinRequest request){
        ValidateUtils.requireTrue(request.validate(), ApiErrorCode.INVALID_REQUEST_BODY);

        Long userId = AuthUtils.getUserId();
        MentorDto.JoinResponse response = oauth2Service.joinMentor(userId, request);

        return ResponseUtils.created("멘토 등록에 성공했습니다.", response);
    }

    @PostMapping("/paymoney")
    @Operation(summary = "페이머니 충전")
    public ResponseEntity<ApiResponse<PaymoneyDto.ChargeResponse>> chargePaymoney(@RequestBody PaymoneyDto.ChargeRequest request) {
        ValidateUtils.requireTrue(request.validate(), ApiErrorCode.INVALID_REQUEST_BODY);

        Long userId = AuthUtils.getUserId();

        PaymoneyDto.ChargeResponse response = userService.chargePaymoney(userId, request);
        return ResponseUtils.ok("페이머니를 성공적으로 충전했습니다!", response);
    }

    @GetMapping("/paymoney")
    @Operation(summary = "페이머니 조회")
    public ResponseEntity<ApiResponse<PaymoneyDto.Response>> getPaymoney(){
        Long userId = AuthUtils.getUserId();

        PaymoneyDto.Response response = userService.getPaymoney(userId);
        return ResponseUtils.ok("페이머니를 성공적으로 조회했습니다.", response);
    }

    @GetMapping("/info")
    @Operation(summary = "유저 정보 조회")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUserInfo() {
        Long userId = AuthUtils.getUserId();
        UserInfoResponse response = userService.getUserInfo(userId);
        return ResponseUtils.ok("유저 정보를 성공적으로 조회했습니다.", response);
    }

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "유저 썸네일 이미지 변경")
    public ResponseEntity<ApiResponse<Object>> changeUserThumbnailImageUrl(@RequestParam MultipartFile imageFile) {
        Long userId = AuthUtils.getUserId();
        UserThumbnailImageUrlResponse response = null;
        try {
            response = userService.changeUserThumbnailImageUrl(userId, imageFile);
        } catch (IOException e) {
            throw ApiErrorCode.FAIL_TO_UPLOAD_IMAGE_BY_IO_EXCEPTION.exception();
        }
        return ResponseUtils.ok("썸네일 이미지가 성공적으로 변경되었습니다.", response);
    }
}
