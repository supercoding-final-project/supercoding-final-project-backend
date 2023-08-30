package com.github.supercodingfinalprojectbackend.exception;

import com.github.supercodingfinalprojectbackend.exception.errorcode.ErrorCode;

public abstract class ApiException extends RuntimeException {
    private final int status;

    protected ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }

    public int getStatus() { return status; }
}