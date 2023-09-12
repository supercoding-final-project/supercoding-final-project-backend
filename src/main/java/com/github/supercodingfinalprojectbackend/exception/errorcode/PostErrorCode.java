package com.github.supercodingfinalprojectbackend.exception.errorcode;

import com.github.supercodingfinalprojectbackend.exception.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum PostErrorCode implements ErrorCode {
    POST_NOT_POST_ID(HttpStatus.NOT_FOUND,"등록되지 않은 포스트입니다.")
    ;
    private final HttpStatus status;
    private final String message;
    @Override
    public ApiException exception() {
        return new ApiException(this);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return status.value();
    }
}
