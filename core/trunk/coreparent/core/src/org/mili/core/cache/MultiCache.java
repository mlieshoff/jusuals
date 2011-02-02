/*
 * MultiCache.java
 *
 * 27.25.2010
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

/**
 * This interface defines a multi cache. A multi cache is a cache of caches.
 *
 * @author Michael Lieshoff
 */
public interface MultiCache<K> {

    /**
     * Put a value in the super cache with key superKey and in the inner cache under the key.
     *
     * @param key the inner cache key
     * @param superKey the super cache key
     * @param value the value
     */
    <V, S> void put(K key, S superKey, V value);

    /**
     * Gets the cache from the super cache with superKey. The returned caches value is found by
     * the key.
     *
     * @param key the inner cache key
     * @param superKey the super cache key
     * @return the value
     */
    <V, S> V get(K key, S superKey);

    /**
     * Gets the cache from the super cache with superKey. The returned caches value is removed
     * by the key.
     *
     * @param key the inner cache key
     * @param superKey the super cache key
     * @return the value
     */
    <V, S> V remove(K key, S superKey);

    /**
     * Clears the inner cache found by key.
     *
     * @param key the inner cache key
     */
    void clear(K key);

    /**
     * Clears.
     */
    void clear();
}
