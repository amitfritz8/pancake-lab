package org.pancakelab.domain.event;

import org.pancakelab.domain.event.listeners.DomainEventListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Handles the execution of Domain Event Listeners for a specific event type.
 *
 * <p>Each dispatcher instance is responsible for managing listeners of one specific event type.</p>
 */
public class DomainEventDispatcher {

    private final EventType eventType;
    private final List<DomainEventListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * Constructs a DomainEventDispatcher for a specific event type.
     *
     * @param eventType The event type this dispatcher handles.
     */
    public DomainEventDispatcher(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Registers a listener to this dispatcher.
     *
     * @param listener The listener to register.
     * @return True if the listener was successfully registered, false if it was already registered.
     */
    public boolean register(DomainEventListener listener) {
        if (!listeners.contains(listener)) {
            return listeners.add(listener);
        }
        return false;
    }

    /**
     * Deregisters a listener from this dispatcher.
     *
     * @param listener The listener to deregister.
     * @return True if the listener was successfully deregistered, false if it was not found.
     */
    public boolean deregister(DomainEventListener listener) {
        return listeners.remove(listener);
    }

    /**
     * Executes the event handler code for all registered listeners.
     *
     * @param event The event to dispatch to the listeners.
     * @return True if the event was successfully dispatched to at least one listener, false otherwise.
     */
    public boolean dispatch(DomainEvent event) {
        if (listeners.isEmpty()) {
            return false;
        }

        for (DomainEventListener listener : listeners) {
            listener.handleEvent(event);
        }

        return true;
    }

    /**
     * Gets the event type handled by this dispatcher.
     *
     * @return The event type.
     */
    public EventType getEventType() {
        return eventType;
    }
}
