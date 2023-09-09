package com.github.supercodingfinalprojectbackend.entity.type;

public enum UserRole implements CustomEnum {
    NONE,
    MENTEE,
    MENTOR;

    private final UserRole redirect;

    UserRole() { this.redirect = null; };
    UserRole(UserRole redirect) { this.redirect = redirect; }

    @Override
    public UserRole resolve() { return redirect != null ? redirect.resolve() : this; }
}
