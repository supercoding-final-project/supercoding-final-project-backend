package com.github.supercodingfinalprojectbackend.entity.type;

public enum DutyType {
    BACKEND_DEVELOPER,
    BACKEND(BACKEND_DEVELOPER),
    FRONTEND_DEVELOPER(),
    DATA_ENGINEER
    ;
    private final DutyType redirect;

    DutyType() {
        this.redirect = null;
    }
    DutyType(DutyType redirect) {
        this.redirect = redirect;
    }

    // get 메서드를 반드시 호출해야 합니다!
    public DutyType get() {
        return redirect != null ? redirect : this;
    }
}
