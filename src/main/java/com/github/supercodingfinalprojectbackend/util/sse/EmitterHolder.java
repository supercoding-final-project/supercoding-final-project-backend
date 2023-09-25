package com.github.supercodingfinalprojectbackend.util.sse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class EmitterHolder {
    private final ConcurrentHashMap<EventKey, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    public SseEmitter put(EventKey key, SseEmitter emitter) { return emitterMap.put(key, emitter); }
    public SseEmitter remove(EventKey key) { return emitterMap.remove(key); }
    public SseEmitter get(EventKey key) { return emitterMap.get(key); }

    public EmitterHolder() {}
}
