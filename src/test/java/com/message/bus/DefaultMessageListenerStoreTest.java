package com.message.bus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for DefaultMessageListenerStore class.
 * 
 * @author Benjamin Gale
 */
public class DefaultMessageListenerStoreTest {
    
    private MessageListenerStore store;
    
    @BeforeEach
    public void setUp() {
        this.store = new DefaultMessageListenerStore();
    }
    
    @AfterEach
    public void tearDown() {
        this.store = null;
    }
    
    @Test
    public void testAddListenerDoesNotAcceptNullMessageClass() {
        assertThrows(
            NullPointerException.class,
            () -> this.store.addListener(null, null)
        );
    }
    
    @Test
    public void testAddListenerDoesNotAcceptNullListener() {
        assertThrows(
            NullPointerException.class,
            () -> this.store.addListener(TestMessage.class, null)
        );
    }
    
    @Test
    public void testListenersAreAddedCorrectly() {        
        this.store.addListener(Object.class, m -> { });
        this.store.addListener(Object.class, m -> { });
        
        Iterable<MessageListener<Object>> listeners;
        listeners = this.store.getListeners(Object.class);
        
        int count = 0;
        
        for (MessageListener<Object> _ : listeners) {
            count = count + 1;
        }
        
        assertEquals(2, count);
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
        
        for (MessageListener<Object> _ : listeners) {
            count = count + 1;
        }
        
        assertEquals(2, count);
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
        
        for (MessageListener<TestMessage> _ : testMessageListeners) {
            testMessageCount = testMessageCount + 1;
        }
        
        for (MessageListener<Object> _ : objectMessageListeners) {
            objectMessageCount = objectMessageCount + 1;
        }
        
        assertEquals(1, objectMessageCount);
        assertEquals(1, testMessageCount);
    }
    
    @Test
    public void testListenersCantBeModifiedIfCastToCollection() {
        assertThrows(
            UnsupportedOperationException.class,
            () -> {
                Iterable<MessageListener<Object>> listeners;
                listeners = this.store.getListeners(Object.class);

                Collection<MessageListener<Object>> col;
                col = (Collection<MessageListener<Object>>)listeners;

                if (col != null) {
                    col.add(_ -> {});
                }
            });
    }
}
