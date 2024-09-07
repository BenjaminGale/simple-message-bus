/*
 * Copyright 2014 Benjamin Gale.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.benjamingale.simplemessenger;

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
