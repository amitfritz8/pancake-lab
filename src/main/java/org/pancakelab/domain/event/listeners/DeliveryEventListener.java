package org.pancakelab.domain.event.listeners;

import lombok.extern.slf4j.Slf4j;
import org.pancakelab.domain.event.DomainEvent;
import org.pancakelab.domain.event.EventType;

@Slf4j
public class DeliveryEventListener implements DomainEventListener {
    @Override
    public void handleEvent(DomainEvent event) {
        if (event.getEventType().equals(EventType.ORDER_PREPARED)) {
            log.info("Delivery: Order is prepared and ready for delivery.");
        }
    }
}
