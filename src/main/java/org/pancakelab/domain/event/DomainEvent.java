package org.pancakelab.domain.event;

import lombok.Data;

import java.util.UUID;

/**
 * DomainEvent class encapsulating an event in the order lifecycle.
 */

@Data
public class DomainEvent {

    private final UUID orderId;
    private final EventType eventType;
    private final Long occurredOn;

    public DomainEvent(UUID orderId, EventType eventType) {
        this.orderId = orderId;
        this.eventType = eventType;
        this.occurredOn = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "DomainEvent{" +
                "orderId=" + orderId +
                ", eventType=" + eventType +
                ", occurredOn=" + occurredOn +
                '}';
    }
}
