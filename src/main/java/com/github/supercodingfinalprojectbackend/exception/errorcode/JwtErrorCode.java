package com.github.supercodingfinalprojectbackend.exception.errorcode;

import com.github.supercodingfinalprojectbackend.exception.ApiException;
import org.springframework.http.HttpStatus;

public enum JwtErrorCode implements ErrorCode {
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "토큰의 형식을 만족하지 않습니다."),
    EMPTY_JWT(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    UNRELIABLE_JWT(HttpStatus.UNAUTHORIZED, "신뢰할 수 없는 토큰입니다.")
    ;

    JwtErrorCode(HttpStatus status, String message) {
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
