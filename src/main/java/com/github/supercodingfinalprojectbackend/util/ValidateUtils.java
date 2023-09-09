package com.github.supercodingfinalprojectbackend.util;

import com.github.supercodingfinalprojectbackend.exception.ApiException;

import java.util.function.Predicate;

public class ValidateUtils {
    private ValidateUtils() {}

    public static <T> T notNull(T obj, int status, String message) {
        if (obj == null) throw new ApiException(status, message);
        return obj;
    }
    public static void predicate(boolean condition, int status, String message) {
        if (!condition) throw new ApiException(status, message);
    }
    public static <T> void predicate(T obj, Predicate<T> predicate, int status, String message) {
        if (predicate.test(notNull(obj, 500, "ValidateUtils.predicate(obj,...)의 obj는 null이 될 수 없습니다."))) throw new ApiException(status, message);
    }
}
