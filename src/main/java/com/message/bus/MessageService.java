package com.message.bus;

/**
 * Provides services for a message. A {@code MessageService<T>} object provides
 * convenience methods for subscribing to and publishing messages of a given
 * type.
 *
 * @author Benjamin Gale
 * @param <T> the message type handled by this service.
 */
public interface MessageService<T> {

    /**
     * Adds the specified listener to the list of subscribers. Any number of
     * {@code MessageListener<T>} objects can be subscribed to, however, note
     * that duplicate messages will only be subscribed once and repeat
     * registrations will result in the listener being discarded.
     *
     * @param listener the listener to be added to the list of subscribers.
     * Cannot be null.
     */
    void subscribe(MessageListener<T> listener);

    /**
     * Publishes the specified message to any subscribed listeners. The message
     * will still be 'published' even if no subscribers have been registered for
     * the given message type but nothing will happen.
     *
     * @param message the message to be published. Cannot be null.
     */
    void publish(T message);
}
