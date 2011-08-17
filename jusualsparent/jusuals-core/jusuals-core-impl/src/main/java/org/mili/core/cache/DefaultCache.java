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
 * <p><b>Introduction</b><p>
 *
 * <u> What is a default cache?</u><p>
 *
 * A default cache is a standard simple cache. It stores values under keys and provides binding
 * listeners for key changes. It is not thread-safe.<p>
 * <b>Usage</b><p>
 *
 * <u> Define a default cache</u><p>
 *
 * The default cache is simple to use and provides some methods to create, modify or traverse a
 * cache.<p>
 * <b>Examples</b><p>
 *
 * <u> Default cache for foo objects</u><p>
 *
 * Define a simple class called Foo that object wants to be cached in a default cache.<br>
 * <pre>
 * public class Foo() {
 * }
 * </pre>
 * No create a default cache. For simplicity the Foo objects wants to be stored by a String value.<br>
 * <pre>
 * Cache&lt;String, Foo&gt; cache = DefaultCache.create();
 * // put some foos
 * cache.put(&quot;foo 1&quot;, new Foo());
 * cache.put(&quot;foo 2&quot;, new Foo());
 * // get some foos
 * System.out.println(&quot;foo 1 : &quot; + cache.get(&quot;foo 1&quot;));
 * System.out.println(&quot;foo 1 : &quot; + cache.get(&quot;foo 2&quot;));
 * // get the number of cached entries
 * System.out.println(&quot;size  : &quot; + cache.size());
 * // get all values
 * System.out.println(&quot;values: &quot; + cache.values());
 * // get all keys
 * System.out.println(&quot;keys  : &quot; + cache.keySet());
 * // remove a value
 * System.out.println(&quot;remove: &quot; + cache.remove(&quot;foo 1&quot;));
 * // clear the cache
 * cache.clear();
 * </pre>
 * Okay that is simple. Now modify the class Foo to privide an id.<br>
 * <pre>
 * public class Foo() {
 *     String id;
 *
 *     public Foo(String id) {
 *         this.id = id;
 *     }
 *
 *     public String getId() {
 *         return id;
 *     }
 * }
 * </pre>
 * Create a cache from a collection of Foo objects via the create method that provides an id
 * functionality.<br>
 * <pre>
 * Now the cache contains the two Foo objects with keys &quot;foo 1&quot; and &quot;foo 2&quot;.
 * </pre>
 * Now the cache contains the two Foo objects with keys &quot;foo 1&quot; and &quot;foo 2&quot;.<br>
 * Next use the change support of the cache. With the method getChangeSupport() the possibility is
 * getted to add listeners of type java.beans.PropertyChangeListener. The support will fire
 * property change events whenever the cache is modified.<br>
 * <pre>
 * cache.getChangeSupport().addPropertyChangeListener(new PropertyChangeListener() {
 *     &#064;Override
 *     public void propertyChange(PropertyChangeEvent evt) {
 *         // change stuff here
 *     }
 * });
 * </pre>
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