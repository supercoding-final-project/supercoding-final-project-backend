package com.github.supercodingfinalprojectbackend.exception.errorcode;

import com.github.supercodingfinalprojectbackend.exception.ApiException;

public interface ErrorCode {
    ApiException exception();
    String getMessage();
    int getStatus();
}
