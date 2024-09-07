package com.message.bus;

/**
 * Sample message class to be utilized by unit tests.
 * 
 * @author Benjamin Gale
 */
public class TestMessage {
    
    public String getText() {
        return "XYZ";
    }
    
    public int getCount() {
        return 42;
    }
    
    public boolean isCondition() {
        return true;
    }
}
