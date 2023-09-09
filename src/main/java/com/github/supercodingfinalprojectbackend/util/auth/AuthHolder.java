package com.github.supercodingfinalprojectbackend.util.auth;

import com.github.supercodingfinalprojectbackend.dto.Login;

import java.util.concurrent.ConcurrentHashMap;

public class AuthHolder {
    private final ConcurrentHashMap<Long, Login> authMap = new ConcurrentHashMap<>();

    public Login put(Long key, Login value) { return authMap.put(key, value); }
    public Login remove(Long key) { return authMap.remove(key); }
    public Login get(Long key) { return authMap.get(key); }

    public AuthHolder() {}

    public Login findByRefreshToken(String refreshToken) {
        for (Login value : authMap.values()) {
            if (value.getRefreshToken().equals(refreshToken)) return value;
        }
        return null;
    }
}
