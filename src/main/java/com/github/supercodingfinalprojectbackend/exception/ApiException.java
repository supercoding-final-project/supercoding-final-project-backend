package com.github.supercodingfinalprojectbackend.exception;

import com.github.supercodingfinalprojectbackend.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final int status;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status.value();
    }
    public ApiException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() { return status; }
}