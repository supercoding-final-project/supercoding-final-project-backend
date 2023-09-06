package com.github.supercodingfinalprojectbackend.entity.type;

public enum UserRole {
    MENTEE("mentee"),
    MENTOR("mentor");
    private final String name;

    UserRole(String name) {
        this.name = name;
    };



    public static boolean contains(String roleName) {
        for (UserRole role : values()) {
            if (role.name.equals(roleName)) return true;
        }

        return false;
    }


    @Override
    public String toString() { return name; }
}
