package com.github.supercodingfinalprojectbackend.entity.type;

public enum OrderState {
    APPLIED,
    APPROVED,
    REJECTED,
    CANCELED;

    @Override
    public String toString() { return name(); }
}
