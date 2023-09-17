package com.github.supercodingfinalprojectbackend.entity.type;

import java.util.Arrays;
import java.util.StringJoiner;

public enum UserRole {
    NONE,
    MENTEE,
    MENTOR;

    private final UserRole redirect;

    UserRole() { this.redirect = null; };
    UserRole(UserRole redirect) { this.redirect = redirect; }

    private UserRole resolve() { return redirect != null ? redirect.resolve() : this; }

    public static UserRole parseType(String userRoleName) {
        try {
            return valueOf(userRoleName).resolve();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return resolve().toString();
    }

    public static String getScopeAsString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        Arrays.stream(values()).filter(v->v.redirect != null).map(UserRole::toString).forEach(sj::add);
        return sj.toString();
    }

    public boolean same(UserRole other) {
        return resolve().equals(other.resolve());
    }
}
