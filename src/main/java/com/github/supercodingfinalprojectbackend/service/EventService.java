package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.entity.type.EventType;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.util.sse.EmitterHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EmitterHolder emitterHolder;
    private static final String DELIMITER = "/";

    public SseEmitter createEmitter(Long userId, UserRole userRole, EventType eventType) {
        String key = createEmitterKey(userId, userRole, eventType);

        final Long timeoutMillis = 3_600_000L;    // 1시간
        SseEmitter emitter = new SseEmitter(timeoutMillis);
        emitter.onTimeout(emitter::complete);
        emitter.onCompletion(()->removeEmitter(key));

        emitterHolder.put(key, emitter);

        return emitter;
    }

    public String createEmitterKey(Long userId, UserRole userRole, EventType eventType) {
        return userId + DELIMITER + userRole + DELIMITER + eventType;
    }

    public void removeEmitter(String key) {
        emitterHolder.remove(key);
    }

    public void pushEvent(String key, Object obj) throws IOException {
        SseEmitter emitter = emitterHolder.get(key);
        emitter.send(obj);
    }
}
