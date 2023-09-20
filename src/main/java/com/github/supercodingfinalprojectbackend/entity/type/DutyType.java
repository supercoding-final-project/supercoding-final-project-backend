package com.github.supercodingfinalprojectbackend.entity.type;

import java.util.Random;

public enum DutyType implements CustomEnum {
    NONE,
    BACKEND_DEVELOPER,
    FRONTEND_DEVELOPER,
    FULL_STACK_DEVELOPER,
    MOBILE_APP_DEVELOPER,
    DEVOPS_ENGINEER,
    DATA_ENGINEER,
    GAME_DEVELOPER,
    AI_ML_ENGINEER,
    SECURITY_ENGINEER;
    private final DutyType redirect;

    DutyType() { this.redirect = null; }
    DutyType(DutyType redirect) { this.redirect = redirect; }

    public static boolean contains(String dutyName) {
        for (DutyType dutyType : values()) {
            if (dutyType.name().equals(dutyName)) return true;
        }
        return false;
    }

    public static String resolvedName(String dutyName) {
        return dutyName != null ? DutyType.valueOf(dutyName).resolve().name() : null;
    }

    public static String dummy() {
        final int count = values().length - 1;
        final int index = 1 + new Random().nextInt(count);
        return values()[index].toString();
    }

    // resole 호출 반드시 필요
    @Override
    public DutyType resolve() { return redirect != null ? redirect.resolve() : this; }


    @Override
    public String toString() {
        return resolve().name();
    }
}
