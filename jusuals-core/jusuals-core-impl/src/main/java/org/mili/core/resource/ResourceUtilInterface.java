/*
 * ResourceUtilInterface.java
 *
 * 25.05.2011
 *
 * Copyright 2011 Michael Lieshoff
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

import java.io.*;
import java.util.*;

import javax.xml.bind.*;

/**
 * This interface defines a resource util.
 *
 * @author Michael Lieshoff
 */
public interface ResourceUtilInterface {

    /**
     * Gets the string.
     *
     * @param locale the locale
     * @param baseName the base name
     * @param key the key
     * @return the string
     */
    String getString(Locale locale, String baseName, String key);

    /**
     * Gets the string.
     *
     * @param locale the locale
     * @param baseName the base name
     * @param clazz the clazz
     * @param key the key
     * @return the string
     */
    String getString(Locale locale, String baseName, Class<?> clazz, String key);

    /**
     * Gets the string.
     *
     * @param locale the locale
     * @param baseName the base name
     * @param object the object
     * @param key the key
     * @return the string
     */
    String getString(Locale locale, String baseName, Object object, String key);

    /**
     * Load.
     *
     * @param locale the locale
     * @param baseName the base name
     * @param classLoader the class loader
     */
    void load(Locale locale, String baseName, ClassLoader classLoader);

    /**
     * Load.
     *
     * @param locale the locale
     * @param baseName the base name
     */
    void load(Locale locale, String baseName);

    /**
     * Load from xml.
     *
     * @param locale the locale
     * @param baseName the base name
     * @param classLoader the class loader
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws JAXBException the jAXB exception
     */
    void loadFromXml(Locale locale, String baseName, ClassLoader classLoader)
            throws IOException, JAXBException;

    /**
     * Gets the resource bundles for basename and locale.
     *
     * @param basename the basename
     * @param locale the locale
     * @return the resource bundles for basename and locale
     */
    Map<String, String> getResourceBundlesForBasenameAndLocale(String basename, Locale locale);

    /**
     * List basenames.
     *
     * @return the list
     */
    List<String> listBasenames();

    /**
     * List locales for basename.
     *
     * @param baseName the base name
     * @return the list
     */
    List<Locale> listLocalesForBasename(String baseName);

    /**
     * Contains.
     *
     * @param locale the locale
     * @param baseName the base name
     * @param key the key
     * @return true, if successful
     */
    boolean contains(Locale locale, String baseName, String key);

    /**
     * Clears the resources.
     */
    void clear();

}
