package com.github.supercodingfinalprojectbackend.exception.errorcode;

import com.github.supercodingfinalprojectbackend.exception.ApiException;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {
    ALREADY_LOGGED_OUT(HttpStatus.OK, "이미 로그아웃 되었습니다.");

    UserErrorCode(HttpStatus status, String message) {
        this.message = message;
        this.status = status.value();
    }
    private final String message;
    private final int status;
    @Override
    public ApiException exception() { return new ApiException(this); }
    @Override
    public String getMessage() { return message; }
    @Override
    public int getStatus() { return status; }
}
