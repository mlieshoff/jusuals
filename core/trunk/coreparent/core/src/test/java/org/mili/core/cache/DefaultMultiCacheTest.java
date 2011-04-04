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

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class DefaultMultiCacheTest {

    private DefaultMultiCache<String> cache = DefaultMultiCache.create();

    @Test
    public void testCreate() {
        assertEquals(0, this.cache.size());
    }

    @Test
    public void testGetObject() {
        this.cache.put("a", "b", 4711);
        assertEquals(4711, this.cache.get("a", "b"));
    }

    @Test
    public void testPutObject() {
        this.cache.put("a", "b", 4711);
        assertEquals(4711, this.cache.get("a", "b"));
        assertEquals(1, this.cache.size());
    }

    @Test
    public void testRemove() {
        this.cache.put("a", "b", 4711);
        assertEquals(4711, this.cache.remove("a", "b"));
        assertNull(this.cache.get("a", "b"));
    }

    @Test
    public void shouldRemoveNothing() {
        assertNull(this.cache.remove("x", "y"));
    }

    @Test
    public void testClear_K() {
        this.cache.put("a", "b", 4711);
        assertEquals(4711, this.cache.remove("a", "b"));
        this.cache.clear("a");
        assertNull(this.cache.get("a", "b"));
    }

    @Test
    public void testClear() {
        this.cache.put("a", "b", 4711);
        assertEquals(4711, this.cache.remove("a", "b"));
        this.cache.clear();
        assertNull(this.cache.get("a", "b"));
    }

    @Test
    public void shouldChangeSupport() {
        assertNotNull(this.cache.getChangeSupport());
    }
}
