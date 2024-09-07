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
