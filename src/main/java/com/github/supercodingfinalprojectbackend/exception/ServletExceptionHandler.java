package com.github.supercodingfinalprojectbackend.exception;

import com.github.supercodingfinalprojectbackend.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServletExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(ApiException e) {
        ApiResponse<?> data = ApiResponse.fail(e.getStatus(), e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(data);
    }
}
