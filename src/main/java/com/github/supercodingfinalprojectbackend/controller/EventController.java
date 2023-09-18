package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.entity.type.EventType;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.service.EventService;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    @GetMapping("/test")
    public SseEmitter sseTest(@RequestParam String eventTypeName) {
//        Long userId = AuthUtils.getUserId();
        EventType eventType = ValidateUtils.requireNotNull(EventType.parseType(eventTypeName), 400, "eventTypeName은 다음 중 하나여야 합니다. " + EventType.getScopeAsString());
        return eventService.createEmitter(1L, UserRole.NONE, eventType);
    }

    @GetMapping("/identifier")
    public SseEmitter connectEventMentorOrders(@RequestParam String eventTypeName) {
        Long userId = AuthUtils.getUserId();
        EventType eventType = ValidateUtils.requireNotNull(EventType.parseType(eventTypeName), 400, "eventTypeName은 다음 중 하나여야 합니다. " + EventType.getScopeAsString());
        UserRole userRole = AuthUtils.getUserRole();

        return eventService.createEmitter(userId, userRole, eventType);
    }
}
