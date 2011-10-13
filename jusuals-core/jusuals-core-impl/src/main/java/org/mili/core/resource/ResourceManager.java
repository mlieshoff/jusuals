/*
 * ResourceManager.java
 *
 * 09.10.2009
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
package org.mili.core.resource;

import java.util.*;

/**
 * This interface defines a resource manager.
 *
 * @author Michael Lieshoff
 *
 */
public interface ResourceManager {

    /**
     * Returns a label named by key.
     *
     * @param key key of label
     * @return label
     * @throws IllegalStateException if no labels for locale or basename defined
     * @throws MissingResourceException i no label could be found
     */
    String getString(String key);

    /**
     * Returns a label named by key. The key is generated from class and package concatenated
     * with specified key.
     *
     * @param cls class and package
     * @param key key of label
     * @return label
     * @throws IllegalStateException if no labels for locale or basename defined
     * @throws MissingResourceException i no label could be found
     */
    String getString(Class<?> cls, String key);

    /**
     * Returns a label named by key. The key is generated from objects class and package
     * concatenated with specified key.
     *
     * @param o object to get class and package
     * @param key key of label
     * @return label
     * @throws IllegalStateException if no labels for locale or basename defined
     * @throws MissingResourceException i no label could be found
     */
    String getString(Object o, String key);

    /**
     * Returns a label from locale and basename. The key is generated from objects class and
     * package concatenated with specified key.
     *
     * @param locale locale
     * @param baseName basename of bundle
     * @param o object used to get class and package
     * @param key key of label
     * @return label
     * @throws IllegalStateException if no labels for locale or basename defined
     * @throws MissingResourceException i no label could be found
     */
    String getString(Locale locale, String baseName, Object o, String key);

    /**
     * Returns a label from locale and basename. The key is generated from class and package
     * concatenated with specified key.
     *
     * @param locale locale
     * @param baseName basename of bundle
     * @param cls class used to get package
     * @param key key of label
     * @return label
     * @throws IllegalStateException if no labels for locale or basename defined
     * @throws MissingResourceException i no label could be found
     */
    String getString(Locale locale, String baseName, Class<?> cls, String key);

    /**
     * Returns a label named by key from locale and basename.
     *
     * @param locale locale
     * @param baseName basename of bundle
     * @param key key of label
     * @return label
     * @throws IllegalStateException if no labels for locale or basename defined
     * @throws MissingResourceException i no label could be found
     */
    String getString(Locale locale, String baseName, String key);

    /**
     * Returns labels in an array of strings named by keys.
     *
     * @param keys keys of labels
     * @return labels in array
     * @throws IllegalStateException if no labels for locale or basename defined
     * @throws MissingResourceException i no label could be found
     */
    String[] getStrings(String... keys);

    /**
     * Returns a label named by plain key.
     *
     * @param key key of label
     * @return label
     * @throws IllegalStateException if no labels for locale or basename defined
     * @throws MissingResourceException i no label could be found
     */
    String getProperty(String key);

    /**
     * Loads resource for locale and basename.
     *
     * @param locale the locale
     * @param baseName the base name
     */
    void load(Locale locale, String baseName);

    /**
     * Loads resource for locale and basename with use of specified classloader.
     *
     * @param locale the locale
     * @param baseName basename of resource, can be writtenm as path, but only last part will be
     * used: "a/b/d" goes to "d"
     * @param cl classloader
     */
    void load(Locale locale, String baseName, ClassLoader cl);

    /**
     * List all basenames.
     *
     * @return list with all basenames.
     */
    List<String> listBasenames();

    /**
     * List locales for basename.
     *
     * @param basename the basename
     * @return locales for basename
     */
    List<Locale> listLocalesForBasename(String basename);

    /**
     * Gets the resource bundles for basename and locale.
     *
     * @param basename the basename
     * @param locale the locale
     * @return the resource bundles for basename and locale
     */
    Map<String, String> getResourceBundlesForBasenameAndLocale(String basename, Locale locale);

    /**
     * Contains.
     *
     * @param key the key
     * @return true, if successful
     */
    boolean contains(String key);

}
