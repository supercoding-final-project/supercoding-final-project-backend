package com.github.supercodingfinalprojectbackend.entity.type;

import java.util.Random;

public enum PostContentType {
    WORK_CAREER,
    EDUCATE_CAREER,
    REVIEW_STYLE;

    public static String dummy() {
        return values()[new Random().nextInt(values().length)].name();
    }
}
