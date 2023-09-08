package com.github.supercodingfinalprojectbackend.util;

import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
    private AuthUtils() {};

    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) throw ApiErrorCode.NOT_AUTHENTICATED.exception();
        return Long.parseLong((String) authentication.getPrincipal());
    }
}
