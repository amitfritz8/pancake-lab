package org.pancakelab.domain.event;

import org.pancakelab.domain.event.listeners.DomainEventListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The DomainEventPublisher is responsible for managing the publication of domain events
 * and the registration/deregistration of event listeners.
 *
 * <p>This class ensures thread safety by using concurrent collections, making it safe to use
 * in a multi-threaded environment.</p>
 */
public class DomainEventPublisher {

    private static final Map<EventType, DomainEventDispatcher> eventDispatchers = new ConcurrentHashMap<>();

    /**
     * Publishes a domain event to all registered listeners.
     *
     * @param event The event to be published.
     */
    public static void publish(DomainEvent event) {

        // Notify listeners
        DomainEventDispatcher dispatcher = eventDispatchers.get(event.getEventType());
        if (dispatcher != null) {
            dispatcher.dispatch(event);
        }
    }

    /**
     * Registers a listener for a specific event type.
     *
     * @param listener  The listener to be registered.
     * @param eventType The event type the listener is interested in.
     * @return True if the listener was successfully registered, false otherwise.
     */
    public static boolean register(DomainEventListener listener, EventType eventType) {
        DomainEventDispatcher dispatcher = eventDispatchers.computeIfAbsent(eventType, DomainEventDispatcher::new);
        return dispatcher.register(listener);
    }

    /**
     * Deregisters a listener from a specific event type.
     *
     * @param eventType The event type the listener is deregistering from.
     * @param listener  The listener to be deregistered.
     * @return True if the listener was successfully deregistered, false otherwise.
     */
    public static boolean deregister(EventType eventType, DomainEventListener listener) {
        DomainEventDispatcher dispatcher = eventDispatchers.get(eventType);
        return dispatcher != null && dispatcher.deregister(listener);
    }

}
