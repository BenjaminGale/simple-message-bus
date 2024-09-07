package com.message.bus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the DefaultMessenger class.
 * @author Benjamin Gale
 */
public class DefaultMessengerTest {
    
    private Messenger messenger;
    
    @BeforeEach
    public void setUp() {
        this.messenger = new DefaultMessenger(new DefaultMessageListenerStore());
    }
    
    @AfterEach
    public void tearDown() {
        this.messenger = null;
    }
    
    @Test
    public void testGetMessageIsNeverNull() {
        MessageService<Object> objMsg = this.messenger.getMessage(Object.class);
        MessageService<String> stringMsg = this.messenger.getMessage(String.class);
        MessageService<TestMessage> testMsg = this.messenger.getMessage(TestMessage.class);
        
        assertNotNull(objMsg);
        assertNotNull(stringMsg);
        assertNotNull(testMsg);
     }
    
    @Test
    public void testGetMessageDoesNotAcceptNull() {
        assertThrows(
            NullPointerException.class,
            () -> this.messenger.getMessage(null)
        );
    }
}
