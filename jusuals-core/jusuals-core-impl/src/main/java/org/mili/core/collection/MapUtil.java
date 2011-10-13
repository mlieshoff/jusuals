/*
 * MapUtil.java
 *
 * 15.09.2009
 *
 * Copyright 2009 Michael Lieshoff
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
package org.mili.core.collection;

import java.util.*;

import org.apache.commons.functor.*;
import org.apache.commons.lang.*;

/**
 * This utility class provides some useful methods to operate with maps.
 *
 * @author Michael Lieshoff
 */
public final class MapUtil {

    /** separation modes. */
    public enum DelimiterMode {
        /** normal like {a, b, c, d, e} etc. */
        Normal,
        /** separate all and last, but not the first, like {a b, c, d, e, } etc. */
        AllAndLastButNotFirst,
        /** separate all, but not the first {a b, c, d, e} etc. */
        AllButNotFirst;
    }

    /**
     * Transforms a map to a string representation as same as use of method
     * mapToString(m, " ", null, DelimiterMode.Normal)
     *
     * @param m map.
     * @return string representations like above.
     */
    public static String mapToString(Map<?, ?> m) {
        return mapToString(m, " ", null, DelimiterMode.Normal);
    }

    /**
     * Transforms a map to a string representation as same as use of method
     * mapToString(m, delimiter, null, DelimiterMode.Normal)
     *
     * @param m map.
     * @param delimiter delimiter.
     * @return string representations like above.
     */
    public static String mapToString(Map<?, ?> m, String delimiter) {
        return mapToString(m, delimiter, null, DelimiterMode.Normal);
    }

    /**
     * Transforms a map to a string representation. The delimiter separates the elements. In
     * template %1 sequence iso replaced as the key, %2 as the value. An empty or null map is
     * represented by empty string.
     *
     * @param m map.
     * @param delimiter delimiter.
     * @param template template.
     * @param dm delimiter mode.
     * @return string representations like above.
     */
    public static String mapToString(Map<?, ?> m, String delimiter, String template,
            DelimiterMode dm) {
        if (m == null || m.size() == 0) {
            return "";
        }
        boolean deli = delimiter != null && delimiter.length() > 0;
        boolean templ = template != null && template.length() > 0;
        StringBuilder s = new StringBuilder();
        boolean first = true;
        for (Iterator<?> i = m.keySet().iterator(); i.hasNext();) {
            Object k = i.next();
            if (!templ) {
                s.append(String.valueOf(m.get(k)));
            } else {
                s.append(template.replace("%1", String.valueOf(k)).replace("%2",
                        String.valueOf(m.get(k))));
            }
            if (deli && i.hasNext()) {
                switch (dm) {
                case AllButNotFirst:
                    if (!first) {
                        s.append(delimiter);
                    }
                    break;
                case AllAndLastButNotFirst:
                    if (!first) {
                        s.append(delimiter);
                    }
                    break;
                case Normal:
                default:
                    s.append(delimiter);
                }
            } else if (deli) {
                switch (dm) {
                case AllAndLastButNotFirst:
                    if (!first) {
                        s.append(delimiter);
                    }
                    break;
                case Normal:
                default:
                }
            }
            first = false;
        }
        return s.toString();
    }

    /**
     * Transforms a list with a transformation function to a map. It' same as method call
     * listAsMap(true, l, f)
     *
     * @param l list with objects.
     * @param f transformation function to get the key from an object.
     * @return transformed map based upon the list.
     */
    public static <T, I> Map<I, T> listAsMap(List<T> l, UnaryFunction<T, I> f) {
        return listAsMap(true, l, f);
    }

    /**
     * Transforms a list with a transformation function to a map.
     *
     * @param l list with objects.
     * @param f transformation function to get the key from an object.
     * @return transformed map based upon the list.
     * @throws ArrayStoreException is overwrite is false and duplicate keys occurs.
     * @throws IllegalStateException if error while using the transformation function occurs.
     */
    public static <T, I> Map<I, T> listAsMap(boolean overwrite, List<T> l,
            UnaryFunction<T, I> f) {
        Validate.notNull(l, "list cannot be null!");
        Validate.notNull(f, "function cannot be null!");
        Map<I, T> m = new Hashtable<I, T>();
        try {
            for (int i = 0, n = l.size(); i < n; i++) {
                T o = l.get(i);
                I k = f.evaluate(o);
                if (m.containsKey(k) && !overwrite) {
                    throw new ArrayStoreException("key " + k + " already exists in map!");
                }
                m.put(k, o);
            }
        } catch (ArrayStoreException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("exception while execute function occured!", e);
        }
        return m;
    }

}
