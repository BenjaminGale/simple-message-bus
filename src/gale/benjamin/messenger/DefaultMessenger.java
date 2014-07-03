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

import java.util.Objects;

/**
 * Provides a basic implementation of the Messenger interface. The majority of
 * the work is delegated to a {@code MessageListenerStore} object and this class
 * merely provides a user-friendly API for accessing {@code MessageService<T>}
 * objects.
 *
 * @author Benjamin Gale
 */
public class DefaultMessenger implements Messenger {

    private final MessageListenerStore store;

    /**
     * Constructs a new DefaultMessenger instance using the specified store.
     *
     * @param store the {@code MessageListenerStore} object to be used to store
     * and retrieve message listeners. Cannot be null.
     */
    public DefaultMessenger(MessageListenerStore store) {
        Objects.requireNonNull(store);
        this.store = store;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> MessageService<T> getMessage(Class<T> messageClass) {
        Objects.requireNonNull(messageClass);
        return new DefaultMessageService<>(this.store, messageClass);
    }

    /**
     * Provides a default implementation of the {@code MessageService<T>}
     * interface. This is an implementation detail of the
     * {@code DefaultMessenger} class.
     */
    private final static class DefaultMessageService<T> implements MessageService<T> {

        private final MessageListenerStore store;
        private final Class<T> messageClass;

        public DefaultMessageService(MessageListenerStore store, Class<T> messageClass) {
            this.store = store;
            this.messageClass = messageClass;
        }

        @Override
        public void subscribe(MessageListener<T> listener) {
            Objects.requireNonNull(listener);
            this.store.addListener(messageClass, listener);
        }

        @Override
        public void publish(T message) {
            Objects.requireNonNull(message);

            Iterable<MessageListener<T>> listeners = this.store.getListeners(messageClass);

            for (MessageListener<T> listener : listeners) {
                listener.handle(message);
            }
        }
    }
}
