package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MenteeDto;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.service.MenteeService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mentees")
@RequiredArgsConstructor
@Tag(name = "멘티 API")
public class MenteeController {
    private MenteeService menteeService;

    @PostMapping("/info")
    @Operation(summary = "멘티 정보 저장")
    public ResponseEntity<ResponseUtils.ApiResponse<Object>> saveMenteeInfo(@RequestBody MenteeDto.MenteeInfoRequest request) {
        ValidateUtils.requireTrue(request.validate(), ApiErrorCode.INVALID_REQUEST_BODY);
        Long userId = AuthUtils.getUserId();
        menteeService.saveMenteeInfo(userId, request);
        return ResponseUtils.ok("성공적으로 저장했습니다", null);
    }
}
