package com.github.supercodingfinalprojectbackend.util;

import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ErrorCode;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ValidateUtils {
    private ValidateUtils() {}

    private static boolean isValidStatus(int status) { return status >= 100 && status < 600; }
    private static int validateStatus(int status) {
        if (status < 100 || status >= 600) throw new ApiException(500, "status는 100이상 600미만이어야 합니다.");
        return status;
    }
    private static String validateMessage(String message) { return message != null ? message : ""; }

    public static <T> T requireNotNull(T obj, int status, String message) {
        if (obj == null) throw new ApiException(validateStatus(status), validateMessage(message));
        return obj;
    }
    public static <T> T requireNotNull(T obj, ErrorCode errorCode) {
        if (obj == null) throw errorCode.exception();
        return obj;
    }
    public static <T> T requireNotNullElse(T obj, T other) {
        requireNotNull(other, 500, "other는 null일 수 없습니다.");

        return obj != null ? obj : other;
    }
    public static <T> T requireNotNullElseGet(T obj, Supplier<T> supplier) {
        requireNotNull(supplier, 500, "supplier는 null일 수 없습니다.");

        return obj != null ? obj : supplier.get();
    }
    public static void requireTrue(boolean condition, int status, String message) {
        if (!isValidStatus(status)) throw new ApiException(500, "status는 100이상 600미만이어야 합니다.");

        if (!condition) throw new ApiException(validateStatus(status), validateMessage(message));
    }
    public static void requireTrue(boolean condition, ErrorCode errorCode) {
        if (!condition) throw errorCode.exception();
    }
    public static <T> T requireTrue(T obj, Predicate<T> predicate, int status, String message) {
        requireNotNull(obj, 500, "obj는 null이 될 수 없습니다.");

        if (!predicate.test(obj)) throw new ApiException(validateStatus(status), validateMessage(message));
        return obj;
    }
    public static <T> T requireTrue(T obj, Predicate<T> predicate, ErrorCode errorCode) {
        requireNotNull(obj, 500, "obj는 null이 될 수 없습니다.");

        if (!predicate.test(obj)) throw errorCode.exception();
        return obj;
    }
    public static <T> int compare(T o1, T o2, Comparator<T> comparator) {
        requireNotNull(o1, 500, "o1은 null일 수 없습니다.");
        requireNotNull(o2, 500, "o2은 null일 수 없습니다.");

        return o1.equals(o2) ? 0 : comparator.compare(o1, o2);
    }
    public static <T, R> R requireApply(T obj, Function<T, R> function, int status, String message) {
        requireNotNull(obj, 500, "obj는 null일 수 없습니다.");
        requireNotNull(function, 500, "function은 null일 수 없습니다.");

        try {
            return function.apply(obj);
        } catch (Exception e) {
            throw new ApiException(validateStatus(status), validateMessage(message));
        }
    }
    public static <T, R> R requireApply(T obj, Function<T, R> function, ErrorCode errorCode) {
        requireNotNull(obj, 500, "obj는 null일 수 없습니다.");
        requireNotNull(function, 500, "function은 null일 수 없습니다.");

        try {
            return function.apply(obj);
        } catch (Exception e) {
            throw errorCode.exception();
        }
    }
}
