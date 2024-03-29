/*
 * DefaultLazyCache.java
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
 * This class is a default implementation of interface {@link LazyCache}.
 *
 * @author Michael Lieshoff
 */
public class DefaultLazyCache<K, V> extends DefaultCache<K, V> implements LazyCache<K, V> {
    private LoadFunction<K, V> loadFunction = null;

    /**
     * Instantiates a new default lazy cache.
     *
     * @param loadFunction the load function
     */
    protected DefaultLazyCache(LoadFunction<K, V> loadFunction) {
        super();
        this.loadFunction = loadFunction;
    }

    /**
     * Creates a new lazy cache.
     *
     * @param loadFunction the load function
     * @return the created default lazy cache
     */
    public final static <A, B> DefaultLazyCache<A, B> create(LoadFunction<A, B> loadFunction) {
        return new DefaultLazyCache<A, B>(loadFunction);
    }

    @Override
    public V get(K k) {
        V v = super.get(k);
        if (v == null) {
            v = this.loadFunction.evaluate(k);
        }
        if (v != null) {
            super.put(k, v);
        }
        return v;
    }

    @Override
    public LoadFunction<K, V> getLoadFunction() {
        return this.loadFunction;
    }

}