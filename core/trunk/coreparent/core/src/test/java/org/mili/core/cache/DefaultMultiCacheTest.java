/*
 * DefaultMultiCacheTest.java
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

import org.junit.*;

/**
 * @author Michael Lieshoff
 */
public class DefaultMultiCacheTest {

    @Test
    public void testDefaultMultiCache() {
        DefaultMultiCache<String> c = new DefaultMultiCache<String>();
    }

    @Test
    public void testCreate() {
        assertNotNull(DefaultMultiCache.create());
    }

    @Test
    public void testGetObject() {
        DefaultMultiCache<String> c = DefaultMultiCache.create();
        c.put("a", "b", 4711);
        assertEquals(4711, c.get("a", "b"));
    }

    @Test
    public void testPutObject() {
        DefaultMultiCache<String> c = DefaultMultiCache.create();
        c.put("a", "b", 4711);
        assertEquals(4711, c.get("a", "b"));
    }

    @Test
    public void testRemove() {
        DefaultMultiCache<String> c = DefaultMultiCache.create();
        c.put("a", "b", 4711);
        assertEquals(4711, c.remove("a", "b"));
        assertNull(c.get("a", "b"));
    }

    @Test
    public void testClear_K() {
        DefaultMultiCache<String> c = DefaultMultiCache.create();
        c.put("a", "b", 4711);
        assertEquals(4711, c.remove("a", "b"));
        c.clear("a");
        assertNull(c.get("a", "b"));
    }

    @Test
    public void testClear() {
        DefaultMultiCache<String> c = DefaultMultiCache.create();
        c.put("a", "b", 4711);
        assertEquals(4711, c.remove("a", "b"));
        c.clear();
        assertNull(c.get("a", "b"));
    }

}
