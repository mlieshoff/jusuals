/*
 * DefaultCacheTest.java
 *
 * 27.05.2010
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
package org.mili.core.cache;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.commons.functor.*;
import org.junit.*;

/**
 * @author Michael Lieshoff
 */
public class DefaultCacheTest {

    @Test
    public void testDefaultCache() {
        DefaultCache<String, Integer> c = new DefaultCache<String, Integer>();
    }

    @Test
    public void testCreate() {
        assertNotNull(DefaultCache.create());
    }

    @Test
    public void testCreate_Collection() {
        class Foo {
            String s;
            int i;
            Foo(String s, int i) {
                this.s = s;
                this.i = i;
            }
        }
        List<Foo> l = new ArrayList<Foo>();
        l.add(new Foo("a", 1));
        l.add(new Foo("b", 2));
        l.add(new Foo("c", 3));
        Cache<String, Foo> c = DefaultCache.create(l, new UnaryFunction<Foo, String>() {
            @Override
            public String evaluate(Foo f) {
                return f.s;
            }
        });
        assertEquals(1, c.get("a").i);
        assertEquals(2, c.get("b").i);
        assertEquals(3, c.get("c").i);
    }

    @Test
    public void testClear() {
        DefaultCache<String, Integer> c = new DefaultCache<String, Integer>();
        c.put("a", 4711);
        assertEquals(4711, (int) c.get("a"));
        c.clear();
        assertNull(c.get("a"));
    }

    @Test
    public void testGet() {
        DefaultCache<String, Integer> c = new DefaultCache<String, Integer>();
        c.put("a", 4711);
        assertEquals(4711, (int) c.get("a"));
    }

    @Test
    public void testPut() {
        DefaultCache<String, Integer> c = new DefaultCache<String, Integer>();
        c.put("a", 4711);
        assertEquals(4711, (int) c.get("a"));
    }

    @Test
    public void testRemove() {
        DefaultCache<String, Integer> c = new DefaultCache<String, Integer>();
        c.put("a", 4711);
        assertEquals(4711, (int) c.get("a"));
        assertEquals(4711, (int) c.remove("a"));
        assertNull(c.get("a"));
    }

}
