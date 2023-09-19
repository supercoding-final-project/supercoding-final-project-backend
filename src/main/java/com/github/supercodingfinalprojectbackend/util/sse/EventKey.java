package com.github.supercodingfinalprojectbackend.util.sse;

import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.entity.type.EventType;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"userId", "eventType"})
public class EventKey {
    private final Long userId;
    private final EventType eventType;

    private EventKey(Long recipientUserId, EventType eventType) {
        this.userId = recipientUserId;
        this.eventType = eventType;
    }

    public static EventKey of(User recipient, EventType eventType) {
        return new EventKey(recipient.getUserId(), eventType);
    }
    public static EventKey aboutOrder(User recipient) {
        return new EventKey(recipient.getUserId(), EventType.ORDER);
    }

    @Override
    public String toString() { return userId + "/" + eventType; }
}
