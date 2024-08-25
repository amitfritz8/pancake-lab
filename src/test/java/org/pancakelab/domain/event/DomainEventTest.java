package org.pancakelab.domain.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pancakelab.domain.event.listeners.ChefEventListener;
import org.pancakelab.domain.event.listeners.CustomerEventListener;
import org.pancakelab.domain.event.listeners.DeliveryEventListener;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DomainEventTest {

    private ChefEventListener chefListener;
    private DeliveryEventListener deliveryListener;
    private CustomerEventListener customerEventListener;

    @BeforeEach
    void setUp() {
        chefListener = mock(ChefEventListener.class);
        deliveryListener = mock(DeliveryEventListener.class);
        customerEventListener = mock(CustomerEventListener.class);
        DomainEventPublisher.register(chefListener, EventType.ORDER_COMPLETED);
        DomainEventPublisher.register(deliveryListener, EventType.ORDER_PREPARED);
        DomainEventPublisher.register(customerEventListener, EventType.ORDER_DELIVERED);
    }

    @Test
    void testPreparedEvent() {
        UUID orderId = UUID.randomUUID();
        DomainEvent event = new DomainEvent(orderId, EventType.ORDER_PREPARED);
        DomainEventPublisher.publish(event);
        verify(deliveryListener, times(1)).handleEvent(event);
    }

    @Test
    void testOrderCompletedEvent() {
        UUID orderId = UUID.randomUUID();
        DomainEvent event = new DomainEvent(orderId, EventType.ORDER_COMPLETED);
        DomainEventPublisher.publish(event);
        verify(chefListener, times(1)).handleEvent(event);
    }

    @Test
    void testOrderDeliveredEvent() {
        UUID orderId = UUID.randomUUID();
        DomainEvent event = new DomainEvent(orderId, EventType.ORDER_DELIVERED);
        DomainEventPublisher.publish(event);
        verify(customerEventListener, times(1)).handleEvent(event);
    }

}
