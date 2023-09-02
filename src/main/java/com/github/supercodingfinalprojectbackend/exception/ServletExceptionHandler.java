package com.github.supercodingfinalprojectbackend.exception;

import com.github.supercodingfinalprojectbackend.dto.response.ApiResponse;
import com.github.supercodingfinalprojectbackend.dto.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServletExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public JsonResponse<?> handleApiException(ApiException e) {
        return JsonResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();
    }
}
