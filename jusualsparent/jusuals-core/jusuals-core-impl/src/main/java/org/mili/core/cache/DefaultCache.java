/*
 * DefaultCache.java
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
import org.mili.core.properties.*;

/**
 * This class is a default implementation of interface {@link Cache}.
 *
 * @author Michael Lieshoff
 */
public class DefaultCache<K, V> implements Cache<K, V> {
    private Map<K, V> model = new Hashtable<K, V>();
    private ChangeSupport cs = DefaultChangeSupport.create(this);

    /**
     * Instantiates a new default cache.
     */
    protected DefaultCache() {
        super();
    }

    /**
     * Creates a new default cache.
     *
     * @return the new default cache
     */
    public final static <A, B> DefaultCache<A, B> create() {
        return new DefaultCache<A, B>();
    }

    /**
     * Creates a cache from a collection.
     *
     * @param collection the collection
     * @param idFunction the id function
     * @return the created default cache
     */
    public final static <A, B> DefaultCache<A, B> create(Collection<B> collection,
            UnaryFunction<B, A> idFunction) {
        DefaultCache<A, B> cache = new DefaultCache<A, B>();
        for (Iterator<B> i = collection.iterator(); i.hasNext(); ) {
            B b = i.next();
            cache.put(idFunction.evaluate(b), b);
        }
        return cache;
    }

    @Override
    public void clear() {
        for (Iterator<K> i = this.model.keySet().iterator(); i.hasNext();) {
            K key = i.next();
            V old = this.model.get(key);
            i.remove();
            this.cs.firePropertyChange(key.toString(), old, null);
        }
    }

    @Override
    public V get(K k) {
        return this.model.get(k);
    }

    @Override
    public void put(K k, V v) {
        V old = this.model.get(k);
        this.model.put(k, v);
        this.cs.firePropertyChange(k.toString(), old, v);
    }

    @Override
    public V remove(K k) {
        V old = this.model.remove(k);
        this.cs.firePropertyChange(k.toString(), old, null);
        return old;
    }

    @Override
    public int size() {
        return this.model.size();
    }

    @Override
    public ChangeSupport getChangeSupport() {
        return this.cs;
    }

    @Override
    public Set<K> keySet() {
        return this.model.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.model.values();
    }

}