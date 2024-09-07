package com.message.bus;

/**
 * A Messenger facilitates loosely coupled communication between components. The
 * {@code Messenger} provides access to {@code MessageService<T>} objects which
 * allows messages to be subscribed to and published.
 *
 * @author Benjamin Gale
 */
public interface Messenger {

    /**
     * Gets a message service representing the specified message class. A
     * {@code MessageService<T>} object can be used to add subscribers and to
     * publish messages of a given message type.
     *
     * Even if no subscribers have been registered for a given message type, a
     * {@code MessageService<T>} object will still be returned, allowing a
     * message to be published.
     *
     * @param <T> the message type represented by the service.
     * @param messageClass the class of MessageService to be retrieved. Cannot
     * be null.
     * @return a MessageService object for the specified class.
     */
    <T> MessageService<T> getMessage(Class<T> messageClass);
}
