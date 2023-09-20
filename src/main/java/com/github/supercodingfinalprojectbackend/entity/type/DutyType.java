package com.github.supercodingfinalprojectbackend.entity.type;

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

    // resole 호출 반드시 필요
    @Override
    public DutyType resolve() { return redirect != null ? redirect.resolve() : this; }
}
