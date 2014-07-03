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
