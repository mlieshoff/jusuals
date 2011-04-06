/*
 * ArrayUtil.java
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

/**
 * This utility class provides some useful methods to operate with arrays.
 *
 * @author Michael Lieshoff
 */
public final class ArrayUtil {

    /**
     * Transforms a list of arguments to a string representation like "key = value". As example
     * the list elements are "'a', 4711, 'b', null" then the resulting representation is
     * "a = 4711, b = null".
     *
     * @param o arguments
     * @return string representations like above.
     */
    public final static String varArgsToParamString(Object... o) {
        return arrayToParamString(o);
    }

    /**
     * Transforms a list of arguments to a string representation like "key = value". As example
     * the list elements are "'a', 4711, 'b', null" then the resulting representation is
     * "a = 4711, b = null".
     *
     * @param o array with objects.
     * @return string representations like above.
     */
    public final static String arrayToParamString(Object[] o) {
        return arrayToString(o, "%1", "%1", ", ", " = ");
    }

    /**
     * Transforms a list of arguments to a string representation as same as use of method
     * varArgsToString(null, c)
     *
     * @param o array with objects.
     * @return string representations like above.
     */
    public static String varArgsToString(Object... o) {
        return varArgsToString(null, o);
    }

    /**
     * Transforms a list of arguments to a string representation as same as use of method
     * varArgsToString(mainTemplate, null, o)
     *
     * @param mainTemplate template used as main.
     * @param o array with objects.
     * @return string representations like above.
     */
    public static String varArgsToString(String mainTemplate, Object... o) {
        return varArgsToString(mainTemplate, null, o);
    }

    /**
     * Transforms a list of arguments to a string representation as same as use of method
     * varArgsToString(mainTemplate, template, null, o)
     *
     * @param mainTemplate template used as main.
     * @param template template used for the inner elements.
     * @param o array with objects.
     * @return string representations like above.
     */
    public static String varArgsToString(String mainTemplate, String template, Object... o) {
        return varArgsToString(mainTemplate, template, null, o);
    }

    /**
     * Transforms a list of arguments to a string representation as same as use of method
     * varArgsToString(mainTemplate, template, delimiter, null, o)
     *
     * @param mainTemplate template used as main.
     * @param template template used for the inner elements.
     * @param delimiter delimiter used for separate inner elements.
     * @param o array with objects.
     * @return string representations like above.
     */
    public static String varArgsToString(String mainTemplate, String template,
            String delimiter, Object... o) {
        return varArgsToString(mainTemplate, template, delimiter, null, o);
    }

    /**
     * Transforms a list of arguments to a string representation as same as use of method
     * CollectionUtil.collectionToString(Arrays.asList(o), mainTemplate, template, delimiter,
     * evenDelimiter)
     *
     * @param mainTemplate template used as main.
     * @param template template used for the inner elements.
     * @param delimiter delimiter used for separate inner elements.
     * @param evenDelimiter delimiter used for separate even inner elements.
     * @param o array with objects.
     * @return string representations like above.
     */
    public static String varArgsToString(String mainTemplate, String template,
            String delimiter, String evenDelimiter, Object... o) {
        return CollectionUtil.collectionToString(Arrays.asList(o), mainTemplate, template,
                delimiter, evenDelimiter);
    }

    /**
     * Transforms a list of arguments to a string representation as same as use of method
     * arrayToString(o, null)
     *
     * @param o array with objects.
     * @return string representations like above.
     */
    public static String arrayToString(Object[] o) {
        return arrayToString(o, null);
    }

    /**
     * Transforms a list of arguments to a string representation as same as use of method
     * arrayToString(o, mainTemplate, null)
     *
     * @param o array with objects.
     * @param mainTemplate template used as main.
     * @return string representations like above.
     */
    public static String arrayToString(Object[] o, String mainTemplate) {
        return arrayToString(o, mainTemplate, null);
    }

    /**
     * Transforms a list of arguments to a string representation as same as use of method
     * arrayToString(o, mainTemplate, template, null)
     *
     * @param o array with objects.
     * @param mainTemplate template used as main.
     * @param template template used for the inner elements.
     * @return string representations like above.
     */
    public static String arrayToString(Object[] o, String mainTemplate, String template) {
        return arrayToString(o, mainTemplate, template, null);
    }

    /**
     * Transforms a list of arguments to a string representation as same as use of method
     * arrayToString(o, mainTemplate, template, delimiter, null)
     *
     * @param o array with objects.
     * @param mainTemplate template used as main.
     * @param template template used for the inner elements.
     * @param delimiter delimiter used for separate inner elements.
     * @return string representations like above.
     */
    public static String arrayToString(Object[] o, String mainTemplate, String template,
                String delimiter) {
        return arrayToString(o, mainTemplate, template, delimiter, null);
    }

    /**
     * Transforms a list of arguments to a string representation as same as use of method
     * CollectionUtil.collectionToString(Arrays.asList(c), mainTemplate, template, delimiter,
     * evenDelimiter)
     *
     * @param o array with objects.
     * @param mainTemplate template used as main.
     * @param template template used for the inner elements.
     * @param delimiter delimiter used for separate inner elements.
     * @param evenDelimiter delimiter used for separate even inner elements.
     * @return string representations like above.
     */
    public static String arrayToString(Object[] o, String mainTemplate, String template,
            String delimiter, String evenDelimiter) {
        return CollectionUtil.collectionToString(Arrays.asList(o), mainTemplate, template,
            delimiter, evenDelimiter);
    }

}
