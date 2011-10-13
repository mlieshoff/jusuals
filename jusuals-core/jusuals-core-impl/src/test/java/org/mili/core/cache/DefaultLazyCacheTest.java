/*
 * DefaultLazyCacheTest.java
 *
 * 04.04.2011
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
public class DefaultLazyCacheTest {

    private LazyCache<String, Integer> cache = null;
    private LoadFunction<String, Integer> loadFunction = null;

    @Before
    public void setUp() {
        this.loadFunction = new LoadFunction<String, Integer>() {
            @Override
            public Integer evaluate(String key) {
                return Integer.valueOf(key);
            }
        };
        this.cache = DefaultLazyCache.create(this.loadFunction);
    }

    @Test
    public void shouldCreate() {
        assertEquals(0, this.cache.size());
    }

    @Test
    public void shouldPut() {
        assertEquals(4711, this.cache.get("4711").intValue());
        assertEquals(1, this.cache.size());
    }

    @Test
    public void shouldClear() {
        assertEquals(4711, this.cache.get("4711").intValue());
        this.cache.clear();
        assertEquals(0, this.cache.size());
    }

    @Test
    public void shouldGetsTheLoadFunction() {
        assertEquals(this.loadFunction, this.cache.getLoadFunction());
    }
}
