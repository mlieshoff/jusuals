/*
 * MapUtilTest.java
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

import static org.junit.Assert.*;

import java.util.*;

import org.apache.commons.functor.*;
import org.junit.*;
import org.mili.core.collection.MapUtil.*;

/**
 * @author Michael Lieshoff
 */
public class MapUtilTest {

    /* the abbas test map. ;) */
    private Map<Integer, String> m = new TreeMap<Integer, String>(){{
        put(1, "abbas");
        put(2, "abba");
        put(3, "abb");
        put(4, "ab");
        put(5, "a");
    }};

    @Test
    public void testStandard() {
        String s = "abbas abba abb ab a";
        assertTrue(s.compareTo(MapUtil.mapToString(this.m)) == 0);
        assertEquals("", MapUtil.mapToString(null));
        assertEquals("", MapUtil.mapToString(new Hashtable()));
    }
    @Test
    public void testNoDelimiter() {
        String s = "abbasabbaabbaba";
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, null)) == 0);
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, "")) == 0);
    }

    @Test
    public void testDelimiter() {
        String s = "abbas;abba;abb;ab;a";
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, ";")) == 0);
    }

    @Test
    public void testNoDelimiterNoTemplate() {
        String s = "abbasabbaabbaba";
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, null, null,
                DelimiterMode.Normal)) == 0);
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, null, null,
                DelimiterMode.AllButNotFirst)) == 0);
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, "", "", DelimiterMode.Normal)) == 0);
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, "", "",
                DelimiterMode.AllButNotFirst)) == 0);
    }

    @Test
    public void testDelimiterTemplate() {
        String s = "1:abbas;2:abba;3:abb;4:ab;5:a";
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, ";", "%1:%2",
                DelimiterMode.Normal)) == 0);
        s = "1:abbas2:abba;3:abb;4:ab;5:a";
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, ";", "%1:%2",
                DelimiterMode.AllButNotFirst)) == 0);
        s = "1:abbas2:abba;3:abb;4:ab;5:a;";
        assertEquals(s, MapUtil.mapToString(this.m, ";", "%1:%2",
                DelimiterMode.AllAndLastButNotFirst));
    }

    @Test
    public void testNoDelimiterTemplate() {
        String s = "1:abbas2:abba3:abb4:ab5:a";
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, null, "%1:%2",
                DelimiterMode.Normal)) == 0);
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, null, "%1:%2",
                DelimiterMode.AllButNotFirst)) == 0);
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, "", "%1:%2",
                DelimiterMode.Normal)) == 0);
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, "", "%1:%2",
                DelimiterMode.AllButNotFirst)) == 0);
        assertTrue(s.compareTo(MapUtil.mapToString(this.m, "", "%1:%2",
                DelimiterMode.AllAndLastButNotFirst)) == 0);
    }

    @Test
    public void testListAsMap_List_Function() {
        UnaryFunction<FooId, Integer> f = new UnaryFunction<FooId, Integer>() {
            @Override
            public Integer evaluate(FooId o) {
                return o.id;
            }
        };
        // negativ
        try {
            MapUtil.listAsMap(null, null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            MapUtil.listAsMap(null, f);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            MapUtil.listAsMap(new ArrayList<Object>(), null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        // positiv
        List<FooId> l = new ArrayList<FooId>();
        FooId fi0 = new FooId(4711, "lala");
        FooId fi1 = new FooId(4712, "tinki");
        FooId fi2 = new FooId(4713, "tele");
        FooId fi3 = new FooId(4714, "abbas");
        l.add(fi0);
        l.add(fi1);
        l.add(fi2);
        l.add(fi3);
        Map<Integer, FooId> m = MapUtil.listAsMap(l, f);
        assertEquals(4, m.size());
        assertEquals(m.get(4711), fi0);
        assertEquals(m.get(4712), fi1);
        assertEquals(m.get(4713), fi2);
        assertEquals(m.get(4714), fi3);
        // positiv, double adding
        l.add(fi0);
        l.add(fi1);
        l.add(fi2);
        l.add(fi3);
        m = MapUtil.listAsMap(l, f);
        assertEquals(4, m.size());
        assertEquals(m.get(4711), fi0);
        assertEquals(m.get(4712), fi1);
        assertEquals(m.get(4713), fi2);
        assertEquals(m.get(4714), fi3);
        // multiple check
        try {
            m = MapUtil.listAsMap(false, l, f);
            fail("exception expected!");
        } catch (ArrayStoreException e) {
        }
    }

    class FooId {
        int id = 0;
        String value = "";

        FooId(int id, String value) {
            super();
            this.id = id;
            this.value = value;
        }

    }

}