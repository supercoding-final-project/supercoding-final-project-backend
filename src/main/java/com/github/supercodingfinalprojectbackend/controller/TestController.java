package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.response.PageResponse;
import com.github.supercodingfinalprojectbackend.dto.response.JsonResponse;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/api/v1/test")
@Tag(name = "테스트용 API", description = "테스트를 위해 작성된 API입니다.")
public class TestController {
    @GetMapping("/")
    @Operation(summary = "스웨거 정상 동작 테스트")
    public JsonResponse<PageResponse<String>> test() {

        return JsonResponse.<PageResponse<String>>builder()
                .status(203)
                .message("hello, world")
                .data(new PageResponse<String>())
                .build();
    }
}