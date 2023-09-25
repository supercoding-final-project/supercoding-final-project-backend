package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.entity.type.EventType;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.UserRepository;
import com.github.supercodingfinalprojectbackend.util.sse.EmitterHolder;
import com.github.supercodingfinalprojectbackend.util.sse.EventKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EmitterHolder emitterHolder;
    private final UserRepository userRepository;

    public SseEmitter createEmitter(EventKey key) {

        final Long timeoutMillis = 3_600_000L;    // 1시간
        SseEmitter emitter = new SseEmitter(timeoutMillis);
        emitter.onTimeout(emitter::complete);
        emitter.onCompletion(()->removeEmitter(key));

        emitterHolder.put(key, emitter);

        return emitter;
    }
    public SseEmitter createEmitter(Long userId, EventType eventType) {
        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);
        EventKey key = EventKey.of(user, eventType);

        final Long timeoutMillis = 3_600_000L;    // 1시간
        SseEmitter emitter = new SseEmitter(timeoutMillis);
        emitter.onTimeout(emitter::complete);
        emitter.onCompletion(()->removeEmitter(key));

        emitterHolder.put(key, emitter);

        return emitter;
    }

    public void removeEmitter(EventKey key) {
        emitterHolder.remove(key);
    }

    public void pushEvent(EventKey key, Object data) {
        SseEmitter emitter = emitterHolder.get(key);
        if (emitter != null) {
            try {
                emitter.send(data);
            } catch (IOException e) {
                log.warn("이벤트 발생에 실패했습니다.");
            }
        }
    }
}
