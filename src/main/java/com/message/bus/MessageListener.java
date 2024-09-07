package com.message.bus;

import java.util.function.Predicate;

/**
 * Represents an object that can handle a message of a given type.
 *
 * @author Benjamin Gale
 * @param <T> the type of message handled by this listener.
 */
@FunctionalInterface
public interface MessageListener<T> {

    /**
     * Handles the received message.
     *
     * @param message the message to be handled. Will never be null.
     */
    void handle(T message);

    /**
     * Creates a MessageListener that will only be invoked if the specified
     * condition is true.
     *
     * @param condition the condition to be tested prior to handling the
     * message.
     *
     * @return a new {@code MessageListener<T>} object that will only be invoked
     * if the specified {@code Predicate<T>} evaluates to true.
     */
    default MessageListener<T> applyCondition(Predicate<T> condition) {
        return message -> {
            if (condition.test(message)) {
                this.handle(message);
            }
        };
    }
}
