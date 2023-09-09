package com.github.supercodingfinalprojectbackend.entity.type;

public enum DutyType implements CustomEnum {
    BACKEND_DEVELOPER,
    FRONTEND_DEVELOPER,
    DATA_ENGINEER
    ;
    private final DutyType redirect;

    DutyType() { this.redirect = null; }
    DutyType(DutyType redirect) { this.redirect = redirect; }

    // resole 호출 반드시 필요
    @Override
    public DutyType resolve() { return redirect != null ? redirect.resolve() : this; }
}
