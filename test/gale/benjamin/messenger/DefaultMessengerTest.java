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

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the DefaultMessenger class.
 * @author Benjamin Gale
 */
public class DefaultMessengerTest {
    
    private Messenger messenger;
    
    @Before
    public void setUp() {
        this.messenger = new DefaultMessenger(new DefaultMessageListenerStore());
    }
    
    @After
    public void tearDown() {
        this.messenger = null;
    }
    
    @Test
    public void testGetMessageIsNeverNull() {
        MessageService<Object> objMsg = this.messenger.getMessage(Object.class);
        MessageService<String> stringMsg = this.messenger.getMessage(String.class);
        MessageService<TestMessage> testMsg = this.messenger.getMessage(TestMessage.class);
        
        Assert.assertNotNull(objMsg);
        Assert.assertNotNull(stringMsg);
        Assert.assertNotNull(testMsg);
     }
    
    @Test(expected = NullPointerException.class)
    public void testGetMessageDoesNotAcceptNull() {
        this.messenger.getMessage(null);
    }
}
