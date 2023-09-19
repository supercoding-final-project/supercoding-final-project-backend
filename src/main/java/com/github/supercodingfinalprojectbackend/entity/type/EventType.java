package com.github.supercodingfinalprojectbackend.entity.type;

import java.util.Arrays;
import java.util.StringJoiner;

public enum EventType {
    NONE,
    PAYMENT,
    ORDER;

    private final EventType redirect;

    EventType() { this.redirect = null; };
    EventType(EventType redirect) { this.redirect = redirect; }

    private EventType resolve() { return redirect != null ? redirect.resolve() : this; }

    public static String getScopeAsString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");

        Arrays.stream(values()).filter(v->v.redirect != null).map(EventType::toString).forEach(sj::add);

        return sj.toString();
    }

    public static EventType parseType(String eventTypeName) {
        try {
            return valueOf(eventTypeName).resolve();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return resolve().name();
    }
}
