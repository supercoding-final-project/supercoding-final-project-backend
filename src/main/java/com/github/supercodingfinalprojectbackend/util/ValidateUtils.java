package com.github.supercodingfinalprojectbackend.util;

import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ErrorCode;

public class ValidateUtils {
    private ValidateUtils() {}

    public static <T> T notNull(T obj, int status, String message) {
        if (obj == null) throw new ApiException(status, message);
        return obj;
    }

    public static <T> T notNull(T obj, ErrorCode errorCode) {
        if (obj == null) throw errorCode.exception();
        return obj;
    }
}
