package com.github.supercodingfinalprojectbackend.exception;

import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServletExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        return ResponseUtils.status(e.getStatus(), e.getMessage(), null);
    }
}
