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

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Provides a default implementation of the MessageListenerStore interface.
 * {@code MessageListener<T>} objects are stored using {@code WeakReference}
 * objects so that they can be disposed of automatically when no other objects
 * hold strong references to them.
 *
 * The {@code WeakReferences} for a given message type are disposed every time
 * the listener list is requested for that message type. That is, only 'live'
 * listeners will be returned by a call to the {@code getListeners} method.
 *
 * @author Benjamin Gale
 */
public class DefaultMessageListenerStore implements MessageListenerStore {

    private final Map<Class<?>, Set<WeakReference<MessageListener<?>>>> listenerMap;
    private final ReferenceQueue queue;

    /**
     * Constructs a new DefaultMessageListenerStore instance.
     */
    public DefaultMessageListenerStore() {
        this.listenerMap = new HashMap<>();
        this.queue = new ReferenceQueue<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void addListener(Class<T> messageClass, MessageListener<T> listener) {
        Objects.requireNonNull(messageClass);
        Objects.requireNonNull(listener);

        if (listenerMap.containsKey(messageClass)) {
            listenerMap.get(messageClass).add(new WeakReference(listener, queue));
        } else {
            Set<WeakReference<MessageListener<?>>> listeners = new HashSet<>();
            listeners.add(new WeakReference(listener, queue));
            listenerMap.put(messageClass, listeners);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Iterable<MessageListener<T>> getListeners(Class<T> messageClass) {
        Objects.requireNonNull(messageClass);

        disposeReferences(messageClass);

        Set<MessageListener<T>> listeners = new HashSet<>();

        if (listenerMap.containsKey(messageClass)) {
            listenerMap.get(messageClass)
                    .stream()
                    .forEach(wr -> {
                        if (wr.get() != null) {
                            listeners.add((MessageListener<T>) wr.get());
                        }
                    });
        }

        return Collections.unmodifiableSet(listeners);
    }

    /**
     * Disposes of any references that are no longer alive. Private helper
     * method to remove the weak references from the listener map.
     */
    private <T> void disposeReferences(Class<T> eventClass) {

        if (listenerMap.containsKey(eventClass)) {

            Set<Reference> referenceSet = Collections.emptySet();
            Reference ref = queue.poll();

            while (ref != null) {
                referenceSet.add(ref);
                ref = queue.poll();
            }

            listenerMap.get(eventClass).removeAll(referenceSet);
        }
    }
}
