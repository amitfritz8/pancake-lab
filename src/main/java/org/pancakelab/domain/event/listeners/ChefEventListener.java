package org.pancakelab.domain.event.listeners;

import lombok.extern.slf4j.Slf4j;
import org.pancakelab.domain.event.DomainEvent;
import org.pancakelab.domain.event.EventType;

@Slf4j
public class ChefEventListener implements DomainEventListener {
    @Override
    public void handleEvent(DomainEvent event) {
        if (event.getEventType().equals(EventType.ORDER_COMPLETED)) {
            log.info("Chef: Order is completed and ready for preparation.");
        }
    }
}
