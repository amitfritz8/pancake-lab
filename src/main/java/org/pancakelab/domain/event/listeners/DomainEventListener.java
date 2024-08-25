package org.pancakelab.domain.event.listeners;

import org.pancakelab.domain.event.DomainEvent;

public interface DomainEventListener {
    void handleEvent(DomainEvent event);
}
