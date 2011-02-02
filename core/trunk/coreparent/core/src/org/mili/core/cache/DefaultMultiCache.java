/*
 * DefaultMultiCache.java
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
 * This class is a default implementation of interface {@link MultiCache}.
 *
 * @author Michael Lieshoff
 */
public class DefaultMultiCache<K> implements MultiCache<K> {
    private DefaultCache<K, Cache<?, ?>> model = DefaultCache.create();

    /**
     * Instantiates a new default multi cache.
     */
    protected DefaultMultiCache() {
        super();
    }

    /**
     * Creates a new multi cache.
     *
     * @return the created default multi cache
     */
    public final static <K> DefaultMultiCache<K> create() {
        return new DefaultMultiCache<K>();
    }

    @Override
    public <V, S> V get(K k, S s) {
        Cache<S, V> c = (Cache<S, V>) this.model.get(k);
        if (c != null) {
            return c.get(s);
        } else {
            return null;
        }
    }

    @Override
    public <V, S> void put(K k, S s, V v) {
        Cache<S, V> c = (Cache<S, V>) this.model.get(k);
        if (c == null) {
            c = DefaultCache.create();
            this.model.put(k, c);
        }
        c.put(s, v);
    }

    @Override
    public void clear(K k) {
        Cache<?, ?> c = this.model.get(k);
        if (c != null) {
            c.clear();
        }
    }

    @Override
    public <V, S> V remove(K k, S s) {
        Cache<S, V> c = (Cache<S, V>) this.model.get(k);
        if (c != null) {
            return c.remove(s);
        }
        return null;
    }

    @Override
    public void clear() {
        this.model.clear();
    }

}
