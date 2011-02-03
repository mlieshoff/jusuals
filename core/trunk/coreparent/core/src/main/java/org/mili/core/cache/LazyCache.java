/*
 * LazyCache.java
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


/**
 * This interface describes a lazy cache. A lazy cache uses a load function to load non existing
 * entries.
 *
 * @author Michael Lieshoff
 */
public interface LazyCache<K, V> {

    /**
     * Gets the load function.
     *
     * @return the load function
     */
    LoadFunction<K, V> getLoadFunction();

    /**
     * Gets the value.
     *
     * @param key the key
     * @return the value
     */
    V get(K key);

    /**
     * Removes the value.
     *
     * @param key the key
     * @return the value
     */
    V remove(K key);

    /**
     * Clears.
     */
    void clear();
}
