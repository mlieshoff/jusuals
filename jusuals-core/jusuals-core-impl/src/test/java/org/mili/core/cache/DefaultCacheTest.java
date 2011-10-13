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

import java.util.*;

import org.apache.commons.functor.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class DefaultCacheTest {
    private DefaultCache<String, Integer> cache = new DefaultCache<String, Integer>();

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
        this.cache.put("a", 4711);
        assertEquals(4711, (int) this.cache.get("a"));
        this.cache.clear();
        assertNull(this.cache.get("a"));
    }

    @Test
    public void testGet() {
        this.cache.put("a", 4711);
        assertEquals(4711, (int) this.cache.get("a"));
    }

    @Test
    public void testPut() {
        this.cache.put("a", 4711);
        assertEquals(4711, (int) this.cache.get("a"));
        assertEquals(1, this.cache.size());
    }

    @Test
    public void testRemove() {
        this.cache.put("a", 4711);
        assertEquals(4711, (int) this.cache.get("a"));
        assertEquals(4711, (int) this.cache.remove("a"));
        assertNull(this.cache.get("a"));
    }

    @Test
    public void shouldHaveValidKeys() {
        this.cache.put("a", 4711);
        this.cache.put("b", 4712);
        this.cache.put("c", 4713);
        Set<String> keys = this.cache.keySet();
        assertTrue(keys.contains("a"));
        assertTrue(keys.contains("b"));
        assertTrue(keys.contains("c"));
    }

    @Test
    public void shouldHaveValidValues() {
        this.cache.put("a", 4711);
        this.cache.put("b", 4712);
        this.cache.put("c", 4713);
        Collection<Integer> values = this.cache.values();
        assertTrue(values.contains(4711));
        assertTrue(values.contains(4712));
        assertTrue(values.contains(4713));
    }
}
