package com.github.supercodingfinalprojectbackend.exception;

import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServletExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        return ResponseUtils.status(e.getStatus(), e.getMessage(), null);
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> PostApiException() {
        return ResponseUtils.badRequest("입력값이 올바르지 않습니다.",null);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException e) {
        return ResponseUtils.badRequest(
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
    }
}
