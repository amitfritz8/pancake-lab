package org.pancakelab.domain.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pancakelab.domain.event.listeners.ChefEventListener;
import org.pancakelab.domain.event.listeners.CustomerEventListener;
import org.pancakelab.domain.event.listeners.DeliveryEventListener;

import java.util.UUID;

import static org.mockito.Mockito.*;

class DomainEventTest {

    private ChefEventListener chefEventListener;
    private DeliveryEventListener deliveryEventListener;
    private CustomerEventListener customerEventListener;

    @BeforeEach
    void setupListener() {
        chefEventListener = mock(ChefEventListener.class);
        deliveryEventListener = mock(DeliveryEventListener.class);
        customerEventListener = mock(CustomerEventListener.class);

        DomainEventPublisher.register(chefEventListener, EventType.ORDER_COMPLETED);
        DomainEventPublisher.register(deliveryEventListener, EventType.ORDER_PREPARED);
        DomainEventPublisher.register(customerEventListener, EventType.ORDER_DELIVERED);
    }

    @Test
    void GivenOrderCompletedEvent_WhenPublished_ThenChefListenerIsInvoked_Test() {
        UUID orderId = UUID.randomUUID();
        DomainEvent orderCompletedEvent = new DomainEvent(orderId, EventType.ORDER_COMPLETED);

        DomainEventPublisher.publish(orderCompletedEvent);

        verify(chefEventListener, times(1)).handleEvent(orderCompletedEvent);
    }

    @Test
    void GivenOrderPreparedEvent_WhenPublished_ThenDeliveryListenerIsInvoked_Test() {
        UUID orderId = UUID.randomUUID();
        DomainEvent orderPreparedEvent = new DomainEvent(orderId, EventType.ORDER_PREPARED);

        DomainEventPublisher.publish(orderPreparedEvent);

        verify(deliveryEventListener, times(1)).handleEvent(orderPreparedEvent);
    }

    @Test
    void GivenOrderDeliveredEvent_WhenPublished_ThenCustomerListenerIsInvoked_Test() {
        UUID orderId = UUID.randomUUID();
        DomainEvent orderDeliveredEvent = new DomainEvent(orderId, EventType.ORDER_DELIVERED);

        DomainEventPublisher.publish(orderDeliveredEvent);

        verify(customerEventListener, times(1)).handleEvent(orderDeliveredEvent);
    }
}
