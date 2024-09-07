package com.message.bus;

/**
 * Provides storage for MessageListeners. A {@code MessageListenerStore}
 * provides the ability to add and retrieve {@code MessageListener<T>} objects
 * for a given class of message.
 *
 * Implementors can use any backing or storage method they wish. The only
 * requirement is that a {@code MessageListenerStore} should handle the disposal
 * of any {@code MessageListener<T>} objects that it has registered.
 *
 * @author Benjamin Gale
 */
public interface MessageListenerStore {

    /**
     * Add a listener to the list of listeners for the specified message type.
     * The {@code MessageListener<T>} objects should be saved to the backing
     * store ready for retrieval at a later time.
     *
     * Any number of {@code MessageListener<T>} objects can be added for a given
     * message type, however, note that duplicate handlers will be ignored. That
     * is, they will not be added to the listener list.
     *
     * @param <T> the type of message to be handled.
     *
     * @param messageClass the class of message the listener is associated with.
     * Cannot be null.
     * @param listener the listener to be added to the listener list. Duplicates
     * will be ignored. Cannot be null.
     */
    <T> void addListener(Class<T> messageClass, MessageListener<T> listener);

    /**
     * Gets a sequence of message listeners for the specified message type.
     * Returns all current, live {@code MessageListener<T>} objects for the
     * specified message type.
     *
     * This method returns an {@code Iterable<MessageListener<T>>} object
     * because the only requirement is that consumers need to iterate over the
     * sequence and do 'something' with each {@code MessageListener<T>} object.
     *
     * @param <T> the type of message to retrieve the {@code MessageListener<T>}
     * objects for.
     *
     * @param messageClass the class of messages to retrieve the
     * {@code MessageListener<T>} objects for. Cannot be null.
     *
     * @return an sequence of {@code MessageListener<T>} objects for the
     * specified message class.
     */
    <T> Iterable<MessageListener<T>> getListeners(Class<T> messageClass);
}
