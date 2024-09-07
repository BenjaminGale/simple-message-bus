package com.message.bus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests the default method of the MessageListener interface.
 * 
 * @author Benjamin Gale
 */
public class MessageListenerTest {
    
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
    public void testApplyCondition() {
        MessageListener<TestMessage> listener;
        listener = _ -> fail();

        listener = listener.applyCondition(p -> !p.isCondition());
        
        this.messenger.getMessage(TestMessage.class).subscribe(listener);
        this.messenger.getMessage(TestMessage.class).publish(new TestMessage());
    }
    
    @Test
    public void testApplyConditionComposesCorrectly() {
        MessageListener<TestMessage> listener;
        listener = _ -> fail();
        
        listener = listener.applyCondition(p -> !p.isCondition());
        listener = listener.applyCondition(p -> p.getText().equals("XYZ"));
        
        this.messenger.getMessage(TestMessage.class).subscribe(listener);
        this.messenger.getMessage(TestMessage.class).publish(new TestMessage());
    }
    
    @Test
    public void testApplyConditionWorksWithComposedPredicate() {
        MessageListener<TestMessage> listener;
        listener = _ -> fail();
        
        Predicate<TestMessage> predicate;
        predicate = TestMessage::isCondition;
        predicate = predicate.and(p -> p.getText().equals("ABC"));
        
        listener = listener.applyCondition(predicate);
        
        this.messenger.getMessage(TestMessage.class).subscribe(listener);
        this.messenger.getMessage(TestMessage.class).publish(new TestMessage());
    }
}
