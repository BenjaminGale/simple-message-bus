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

import com.github.benjamingale.simplemessenger.MessageListener;
import com.github.benjamingale.simplemessenger.MessageListenerStore;
import com.github.benjamingale.simplemessenger.DefaultMessageListenerStore;
import java.util.Collection;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for DefaultMessageListenerStore class.
 * 
 * @author Benjamin Gale
 */
public class DefaultMessageListenerStoreTest {
    
    private MessageListenerStore store;
    
    @Before
    public void setUp() {
        this.store = new DefaultMessageListenerStore();
    }
    
    @After
    public void tearDown() {
        this.store = null;
    }
    
    @Test(expected = NullPointerException.class)
    public void testAddListenerDoesNotAcceptNullMessageClass() {
        this.store.addListener(null, null);
    }
    
    @Test(expected = NullPointerException.class)
    public void testAddListenerDoesNotAcceptNullListener() {
        this.store.addListener(TestMessage.class, null);
    }
    
    @Test
    public void testListenersAreAddedCorrectly() {        
        this.store.addListener(Object.class, m -> { });
        this.store.addListener(Object.class, m -> { });
        
        Iterable<MessageListener<Object>> listeners;
        listeners = this.store.getListeners(Object.class);
        
        int count = 0;
        
        for (MessageListener<Object> listener : listeners) {
            count = count + 1;
        }
        
        Assert.assertEquals(2, count);
    }
    
    @Test
    public void testDuplicateListenersAreNotAdded() {
        MessageListener<Object> listener = o -> { };
        MessageListener<Object> listener2 = o -> { };
        
        this.store.addListener(Object.class, listener);
        this.store.addListener(Object.class, listener);
        this.store.addListener(Object.class, listener2);
        this.store.addListener(Object.class, listener2);
        
        Iterable<MessageListener<Object>> listeners;
        listeners = this.store.getListeners(Object.class);
        
        int count = 0;
        
        for (MessageListener<Object> alistener : listeners) {
            count = count + 1;
        }
        
        Assert.assertEquals(2, count);
    }
    
    @Test
    public void testDifferentMessageClassesAreStoredAndRetrievedCorrectly() {
        MessageListener<TestMessage> testMessageListener = o -> {};
        MessageListener<Object> objectMessageListener = o -> {};
        
        this.store.addListener(TestMessage.class, testMessageListener);
        this.store.addListener(Object.class, objectMessageListener);
        
        int objectMessageCount = 0;
        int testMessageCount = 0;
        
        Iterable<MessageListener<TestMessage>> testMessageListeners;
        testMessageListeners = this.store.getListeners(TestMessage.class);
        
        Iterable<MessageListener<Object>> objectMessageListeners;
        objectMessageListeners = this.store.getListeners(Object.class);
        
        for (MessageListener<TestMessage> listener : testMessageListeners) {
            testMessageCount = testMessageCount + 1;
        }
        
        for (MessageListener<Object> listener : objectMessageListeners) {
            objectMessageCount = objectMessageCount + 1;
        }
        
        Assert.assertEquals(1, objectMessageCount);
        Assert.assertEquals(1, testMessageCount);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testListenersCantBeModifiedIfCastToCollection() {
        Iterable<MessageListener<Object>> listeners;
        listeners = this.store.getListeners(Object.class);
        
        Collection<MessageListener<Object>> col;
        col = (Collection<MessageListener<Object>>)listeners;
        
        if (col != null) {
            col.add(o -> {});
        }
    }
}
