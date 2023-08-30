package com.github.supercodingfinalprojectbackend.exception;

import com.github.supercodingfinalprojectbackend.exception.errorcode.ErrorCode;

public class ApiException extends RuntimeException {
    private final int status;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }

    public int getStatus() { return status; }
}