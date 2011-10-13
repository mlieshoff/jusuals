/*
 * CollectionUtil.java
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
 * This utility class provides some useful methods to operate with collections.
 *
 * @author Michael Lieshoff
 */
public final class CollectionUtil {

    /**
     * Transforms a collection to a string representation as same as use of method
     * collectionToString(c, null)
     *
     * @param c a collection.
     * @return string representations like above.
     */
    public static String collectionToString(Collection<?> c) {
        return collectionToString(c, null);
    }

    /**
     * Transforms a collection to a string representation as same as use of method
     * collectionToString(c, mainTemplate, null)
     *
     * @param c a collection.
     * @param mainTemplate template used as main.
     * @return string representations like above.
     */
    public static String collectionToString(Collection<?> c, String mainTemplate) {
        return collectionToString(c, mainTemplate, null);
    }

    /**
     * Transforms a collection to a string representation as same as use of method
     * collectionToString(c, mainTemplate, template, null)
     *
     * @param c a collection.
     * @param mainTemplate template used as main.
     * @param template template used for the inner elements.
     * @return string representations like above.
     */
    public static String collectionToString(Collection<?> c, String mainTemplate,
            String template) {
        return collectionToString(c, mainTemplate, template, null);
    }

    /**
     * Transforms a collection to a string representation as same as use of method
     * collectionToString(c, mainTemplate, template, delimiter, null)
     *
     * @param c a collection.
     * @param mainTemplate template used as main.
     * @param template template used for the inner elements.
     * @param delimiter delimiter used for separate inner elements.
     * @return string representations like above.
     */
    public static String collectionToString(Collection<?> c, String mainTemplate,
            String template, String delimiter) {
        return collectionToString(c, mainTemplate, template, delimiter, null);
    }

    /**
     * Transforms a collection to a string representation as same as use of method.
     * <p>Example: Collection {1, 2, 3, 4}<p>
     * mainTemplate = [%1] ==> [1234]<p>
     * template = [%1] ==> [[1][2][3][4]]<p>
     * delimiter = , ==> [[1],[2],[3],[4]]<p>
     *
     * @param c a collection.
     * @param mainTemplate template used as main.
     * @param template template used for the inner elements.
     * @param delimiter delimiter used for separate inner elements.
     * @param evenDelimiter delimiter used for separate even inner elements.
     * @return string representations like above.
     */
    public static String collectionToString(Collection<?> c, String mainTemplate,
            String template, String delimiter, String evenDelimiter) {
        if (c == null || c.size() == 0) {
            return "";
        }
        boolean deli = delimiter != null && delimiter.length() > 0;
        boolean edeli = evenDelimiter != null && evenDelimiter.length() > 0;
        boolean templ = template != null && template.length() > 0;
        StringBuilder s = new StringBuilder();
        int z = 0;
        for (Iterator<?> i = c.iterator(); i.hasNext();) {
            Object o = i.next();
            if (templ) {
                s.append(template.replace("%1", String.valueOf(o)));
            } else {
                s.append(String.valueOf(o));
            }
            if (z % 2 == 0 && i.hasNext() && edeli) {
                s.append(evenDelimiter);
            } else if (deli && i.hasNext()) {
                s.append(delimiter);
            }
            z ++;
        }
        boolean mainTempl = mainTemplate != null && mainTemplate.length() > 0;
        if (mainTempl) {
            return mainTemplate.replace("%1", s.toString());
        }
        return s.toString();
    }

    /**
     * Transforms a collection of collections to a string representation as same as use of
     * method collectionOfCollectionToString(coc, null)
     *
     * @param coc a collection of collections.
     * @return string representations like above.
     */
    public static String collectionOfCollectionToString(Collection<?> coc) {
        return collectionOfCollectionToString(coc, null);
    }

    /**
     * Transforms a collection of collections to a string representation as same as use of
     * method collectionOfCollectionToString(coc, mainTemplate, null)
     *
     * @param coc a collection of collections.
     * @param mainTemplate template used as main.
     * @return string representations like above.
     */
    public static String collectionOfCollectionToString(Collection<?> coc,
            String mainTemplate) {
        return collectionOfCollectionToString(coc, mainTemplate, null);
    }

    /**
     * Transforms a collection of collections to a string representation as same as use of
     * method collectionOfCollectionToString(coc, mainTemplate, outterTemplate, null)
     *
     * @param coc a collection of collections.
     * @param mainTemplate template used as main.
     * @param outerTemplate template used as outer main.
     * @return string representations like above.
     */
    public static String collectionOfCollectionToString(Collection<?> coc, String mainTemplate,
            String outerTemplate) {
        return collectionOfCollectionToString(coc, mainTemplate, outerTemplate, null);
    }

    /**
     * Transforms a collection of collections to a string representation as same as use of
     * method collectionOfCollectionToString(coc, mainTemplate, outterTemplate,
     * mainInnerTemplate, null)
     *
     * @param coc a collection of collections.
     * @param mainTemplate template used as main.
     * @param outerTemplate template used as outer main.
     * @param mainInnerTemplate template used as main inner.
     * @return string representations like above.
     */
    public static String collectionOfCollectionToString(Collection<?> coc, String mainTemplate,
            String outerTemplate, String mainInnerTemplate) {
        return collectionOfCollectionToString(coc, mainTemplate, outerTemplate,
                mainInnerTemplate, null);
    }

    /**
     * Transforms a collection of collections to a string representation as same as use of
     * method collectionOfCollectionToString(coc, mainTemplate, outterTemplate,
     * mainInnerTemplate, innerTemplate, null)
     *
     * @param coc a collection of collections.
     * @param mainTemplate template used as main.
     * @param outerTemplate template used as outer main.
     * @param mainInnerTemplate template used as main inner.
     * @param innerTemplate template used for inner elements.
     * @return string representations like above.
     */
    public static String collectionOfCollectionToString(Collection<?> coc, String mainTemplate,
            String outerTemplate, String mainInnerTemplate, String innerTemplate) {
        return collectionOfCollectionToString(coc, mainTemplate, outerTemplate,
                mainInnerTemplate, innerTemplate, null);
    }

    /**
     * Transforms a collection of collections to a string representation as same as use of
     * method collectionOfCollectionToString(coc, mainTemplate, outterTemplate,
     * mainInnerTemplate, innerTemplate, outerDelimiter, null)
     *
     * @param coc a collection of collections.
     * @param mainTemplate template used as main.
     * @param outerTemplate template used as outer main.
     * @param mainInnerTemplate template used as main inner.
     * @param innerTemplate template used for inner elements.
     * @param outerDelimiter delimiter used for separate outer elements.
     * @return string representations like above.
     */
    public static String collectionOfCollectionToString(Collection<?> coc, String mainTemplate,
            String outerTemplate, String mainInnerTemplate, String innerTemplate,
            String outerDelimiter) {
        return collectionOfCollectionToString(coc, mainTemplate, outerTemplate,
                mainInnerTemplate, innerTemplate, outerDelimiter, null);
    }

    /**
    /**
     * Transforms a collection of collections to a string representation.
     * <p>Example: Collection {{1, 2, 3, 4}{5, 6, 7, 8}}<p>
     * mainTemplate = [%1] ==> [12345678]<p>
     * outerTemplate = [%1] ==> [[1234][5678]]<p>
     * mainInnerTemplate = [%1] ==> [[[1234]][[5678]]]<p>
     * innerTemplate = [%1] ==> [[[[1][2][3][4]]][[[5][6][7][8]]]]<p>
     * outerDelimiter = , ==> [[[[1][2][3][4]]], [[[5][6][7][8]]]]<p>
     * innerDelimiter = , ==> [[[[1], [2], [3], [4]]], [[[5], [6], [7], [8]]]]<p>
     *
     * @param coc a collection of collections.
     * @param mainTemplate template used as main.
     * @param outerTemplate template used as outer main.
     * @param mainInnerTemplate template used as main inner.
     * @param innerTemplate template used for inner elements.
     * @param outerDelimiter delimiter used for separate outer elements.
     * @param innerDelimiter delimiter used for separate inner elements.
     * @return string representations like above.
     */
    public static String collectionOfCollectionToString(Collection<?> coc, String mainTemplate,
            String outerTemplate, String mainInnerTemplate, String innerTemplate,
            String outerDelimiter, String innerDelimiter) {
        if (coc == null || coc.size() == 0) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        boolean templ = outerTemplate != null && outerTemplate.length() > 0;
        boolean deli = outerDelimiter != null && outerDelimiter.length() > 0;
        for (Iterator<?> i = coc.iterator(); i.hasNext();) {
            Collection<?> c = (Collection<?>) i.next();
            if (templ) {
                s.append(outerTemplate.replace("%1", collectionToString(c, mainInnerTemplate,
                        innerTemplate, innerDelimiter)));
            } else {
                s.append(collectionToString(c, mainInnerTemplate, innerTemplate,
                        innerDelimiter));
            }
            if (deli && i.hasNext()) {
                s.append(outerDelimiter);
            }
        }
        boolean mainTempl = mainTemplate != null && mainTemplate.length() > 0;
        if (mainTempl) {
            return mainTemplate.replace("%1", s.toString());
        }
        return s.toString();
    }

}