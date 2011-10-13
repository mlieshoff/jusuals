/*
 * ASFValidateTest.java
 *
 * 21.09.2010
 *
 * Copyright 2010 Michael Lieshoff
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

package org.mili.core.lang;

import java.util.*;

import org.apache.commons.lang.math.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class ASFValidateTest {

    @Test
    public void shouldCreate() {
        new ASFValidate();
    }

    @Test
    public void shouldCheckInRange_NumberRangeString() {
        try {
            ASFValidate.isInRange(13, new IntRange(1, 10), "nope!");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            ASFValidate.isInRange(1, new IntRange(1, 10), "nope!");
        } catch (IllegalArgumentException e) {
            fail("exception expected!");
        }
        try {
            ASFValidate.isInRange(10, new IntRange(1, 10), "nope!");
        } catch (IllegalArgumentException e) {
            fail("exception expected!");
        }
    }

    @Test
    public void shouldCheckInRange_NumberRange() {
        try {
            ASFValidate.isInRange(13, new IntRange(1, 10));
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            ASFValidate.isInRange(1, new IntRange(1, 10));
        } catch (IllegalArgumentException e) {
            fail("exception expected!");
        }
        try {
            ASFValidate.isInRange(10, new IntRange(1, 10));
        } catch (IllegalArgumentException e) {
            fail("exception expected!");
        }
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new TreeMap<String, Integer>();
        map.put("ente", 4);
        map.put("hase", 9);
        map.put("hund", 2);
        map.put("maus", 5);
        System.out.println(map);
        final Map<String, Integer> map0 = map;
        Map<String, Integer> sortedMap = new TreeMap<String, Integer>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return map0.get(o2).compareTo(map0.get(o1));
            }
        });

        sortedMap.putAll(map);
        for (Map.Entry<String, Integer> e : sortedMap.entrySet())
            System.out.println(e.getKey() + ": " + e.getValue());
    }

}
