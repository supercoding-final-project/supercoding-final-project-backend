package com.github.supercodingfinalprojectbackend.util.auth;

import java.util.concurrent.ConcurrentHashMap;

public class AuthHolder<K, V> {
    private final ConcurrentHashMap<K, V> authMap = new ConcurrentHashMap<>();

    public V put(K key, V value) { return authMap.put(key, value); }
    public V remove(K key) { return authMap.remove(key); }
    public V get(K key) { return authMap.get(key); }

    public AuthHolder() {}
}
