package com.github.supercodingfinalprojectbackend.exception.errorcode;

import com.github.supercodingfinalprojectbackend.exception.ApiException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PostErrorCode implements ErrorCode {

    ;

    private final String message;
    private final int status;

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
        return status;
    }
}
