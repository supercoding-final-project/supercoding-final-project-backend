package com.github.supercodingfinalprojectbackend.exception.errorcode;

import com.github.supercodingfinalprojectbackend.exception.ApiException;
import org.springframework.http.HttpStatus;

public enum KakaoErrorCode implements ErrorCode {
    NOT_FOUND_USER_INFO(HttpStatus.UNAUTHORIZED, "유저 정보를 찾지 못했습니다."),
    FAIL_TO_RECEIVE_TOKEN(HttpStatus.UNAUTHORIZED, "카카오 인증에 실패했습니다.")
    ;

    KakaoErrorCode(HttpStatus status, String message) {
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
