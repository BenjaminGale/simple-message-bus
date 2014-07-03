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
package gale.benjamin.messenger;

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
