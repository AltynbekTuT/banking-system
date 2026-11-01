package com.bank.observer;

import java.time.Instant;

public final class Event {
    private final EventType type;
    private final String payload;
    private final String targetBranchId;
    private final Instant timestamp;

    public Event(EventType type, String payload, String targetBranchId) {
        this.type = type;
        this.payload = payload;
        this.targetBranchId = targetBranchId;
        this.timestamp = Instant.now();
    }

    public EventType getType() { return type; }
    public String getPayload() { return payload; }
    public String getTargetBranchId() { return targetBranchId; }
    public Instant getTimestamp() { return timestamp; }
}