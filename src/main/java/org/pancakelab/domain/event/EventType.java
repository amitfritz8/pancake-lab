package org.pancakelab.domain.event;

/**
 * Enum representing the different types of events in the order lifecycle.
 */
public enum EventType {
    ORDER_CREATED,
    ORDER_CANCELLED,
    ORDER_COMPLETED,
    ORDER_PREPARED,
    ORDER_DELIVERED,
}