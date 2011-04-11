/*
 * Cache.java
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

import org.mili.core.properties.*;


/**
 * This interface defines a cache.
 *
 * @author Michael Lieshoff
 */
public interface Cache<K, V> extends ChangeSupportable {

    /**
     * Gets the value.
     *
     * @param key the key
     * @return the value
     */
    V get(K key);

    /**
     * Puts the value.
     *
     * @param key the key
     * @param value the value
     */
    void put(K key, V value);

    /**
     * Removes the value.
     *
     * @param key the key
     * @return the value
     */
    V remove(K key);

    /**
     * Clears the cache.
     */
    void clear();

    /**
     * Returns the number of stored elements.
     *
     * @return the number of stored elements
     */
    int size();

    /**
     * Return a key set.
     *
     * @return set with keys.
     */
    Set<K> keySet();

    /**
     * Return a value set.
     *
     * @return set with values.
     */
    Collection<V> values();

}
