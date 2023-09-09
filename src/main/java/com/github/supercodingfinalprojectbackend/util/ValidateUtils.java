package com.github.supercodingfinalprojectbackend.util;

import com.github.supercodingfinalprojectbackend.exception.ApiException;

import java.util.Comparator;
import java.util.function.Predicate;

public class ValidateUtils {
    private ValidateUtils() {}

    public static <T> T requireNotNull(T obj, int status, String message) {
        if (obj == null) throw new ApiException(status, message);
        return obj;
    }
    public static void requireTrue(boolean condition, int status, String message) {
        if (!condition) throw new ApiException(status, message);
    }
    public static <T> void requireTrue(T obj, Predicate<T> predicate, int status, String message) {
        if (predicate.test(requireNotNull(obj, 500, "ValidateUtils.predicate(obj,...)의 obj는 null이 될 수 없습니다."))) throw new ApiException(status, message);
    }

    public static <T> int compare(T o1, T o2, Comparator<T> comparator, int status, String message) {
        requireNotNull(o1, 500, "ValidateUtils.compare(o1, ...) o1은 null일 수 없습니다.");
        requireNotNull(o2, 500, "ValidateUtils.compare(o2, ...) o2은 null일 수 없습니다.");
        return o1.equals(o2) ? 0 : comparator.compare(o1, o2);
    }
}
