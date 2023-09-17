package com.github.supercodingfinalprojectbackend.entity.type;

public enum UserRole {
    NONE,
    MENTEE,
    MENTOR;

    private final UserRole redirect;

    UserRole() { this.redirect = null; };
    UserRole(UserRole redirect) { this.redirect = redirect; }

    private UserRole resolve() { return redirect != null ? redirect.resolve() : this; }

    public UserRole from(String userRoleName) {
        return valueOf(userRoleName).resolve();
    }

    @Override
    public String toString() {
        return resolve().toString();
    }
}
