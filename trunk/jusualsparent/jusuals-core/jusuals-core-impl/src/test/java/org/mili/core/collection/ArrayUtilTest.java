/*
 * ArrayUtilTest.java
 *
 * 15.09.2009
 *
 * Copyright 2009 Michael Lieshoff
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mili.core.collection;

import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class ArrayUtilTest {

    @Test
    public void testConstruct() {
        new ArrayUtil();
    }

    @Test
    public void test() {
        List<Integer> cli = new ArrayList<Integer>();
        cli.add(1);
        cli.add(2);
        cli.add(3);
        cli.add(4);
        String s = ArrayUtil.arrayToString(cli.toArray());
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("1234") == 0);
        s = ArrayUtil.arrayToString(cli.toArray(), "[%1]");
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("[1234]") == 0);
        s = ArrayUtil.arrayToString(cli.toArray(), "[%1]", "%1");
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("[1234]") == 0);
        s = ArrayUtil.arrayToString(cli.toArray(), "[%1]", "%1", ", ");
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("[1, 2, 3, 4]") == 0);
        // varargs to string
        s = ArrayUtil.varArgsToString(1, 2, 3, 4);
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("1234") == 0);
        s = ArrayUtil.varArgsToString("[%1]", 1, 2, 3, 4);
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("[1234]") == 0);
        s = ArrayUtil.varArgsToString("[%1]", "%1", 1, 2, 3, 4);
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("[1234]") == 0);
        s = ArrayUtil.varArgsToString("[%1]", "%1", ", ", 1, 2, 3, 4);
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("[1, 2, 3, 4]") == 0);
        // var args to paramstring
        s = ArrayUtil.varArgsToParamString("o", null, "b", 57);
        assertEquals("o = null, b = 57", s);
        return;
    }

}
