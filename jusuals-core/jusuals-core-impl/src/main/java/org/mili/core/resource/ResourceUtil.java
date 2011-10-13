/*
 * ResourceUtil.java
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

import java.io.*;
import java.util.*;

import javax.xml.bind.*;

import org.mili.core.text.*;

/**
 * This class is a helper for resources and bundles. It's StaticResource.implemented that a kind of developer
 * resources could be defined for use with a "default" locale. The developer resources for this
 * locale will be loaded first the originally resources for the same locale then.
 *
 * @author Michael Lieshoff
 *
 */
public class ResourceUtil {
    /** the namespace. */
    public final static String NAMESPACE = ResourceUtilImpl.NAMESPACE;
    /** the constant for property PROP_MISSINGRESOURCEHANDLER. */
    public final static String PROP_MISSINGRESOURCEHANDLER = ResourceUtilImpl
            .PROP_MISSINGRESOURCEHANDLER;
    /** the constant for property PROP_LOGMISSINGRESOURCE. */
    public final static String PROP_LOGMISSINGRESOURCE = ResourceUtilImpl
            .PROP_LOGMISSINGRESOURCE;
    /** the constant for property PROP_THROWEXCEPTIONONMISSINGRESOURCE. */
    public final static String PROP_THROWEXCEPTIONONMISSINGRESOURCE = ResourceUtilImpl
            .PROP_THROWEXCEPTIONONMISSINGRESOURCE;
    /**
     * property for the filename of the output file from the missing resource logger. The
     * pattern %0 will be replaced with the locale's ISO 3 Language.
     */
    public final static String PROP_MISSINGRESOURCELOGGERFILENAME = ResourceUtilImpl
            .PROP_MISSINGRESOURCELOGGERFILENAME;
    /**
     * property to log missing resources as warning.
     */
    public final static String PROP_LOGMISSINGRESOURCEASWARNING = ResourceUtilImpl
            .PROP_LOGMISSINGRESOURCEASWARNING;

    /**
     * List all basenames.
     *
     * @return list with all basenames.
     */
    public static List<String> listBasenames() {
        return StaticResource.impl.listBasenames();
    }

    /**
     * List locales for basename.
     *
     * @param basename the basename
     * @return locales for basename
     */
    public static List<Locale> listLocalesForBasename(String basename) {
        return StaticResource.impl.listLocalesForBasename(basename);
    }

    /**
     * Gets the resource bundles for basename and locale.
     *
     * @param basename the basename
     * @param locale the locale
     * @return the resource bundles for basename and locale
     */
    public static Map<String, String> getResourceBundlesForBasenameAndLocale(String basename,
            Locale locale) {
        return StaticResource.impl.getResourceBundlesForBasenameAndLocale(basename, locale);
    }

    /**
     * Loads resource for locale and basename.
     *
     * @param locale the locale
     * @param baseName the base name
     */
    public static synchronized void load(Locale locale, String baseName) {
        StaticResource.impl.load(locale, baseName);
    }

    /**
     * Loads resource for locale and basename with use of specified classloader.
     *
     * @param locale the locale
     * @param baseName basename of resource, can be writtenm as path, but only last part will be
     * used: "a/b/d" goes to "d"
     * @param cl classloader
     */
    public static synchronized void load(Locale locale, String baseName, ClassLoader cl) {
        StaticResource.impl.load(locale, baseName, cl);
    }

    /**
     * Loads resources from xml.
     *
     * @param locale the locale
     * @param baseName the basename
     * @param cl the classloader
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws JAXBException the jAXB exception
     */
    public static synchronized void loadFromXml(Locale locale, String baseName, ClassLoader cl)
            throws IOException, JAXBException {
        StaticResource.impl.loadFromXml(locale, baseName, cl);
    }

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
    public static String getString(Locale locale, String baseName, Object o, String key) {
        return StaticResource.impl.getString(locale, baseName, o, key);
    }

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
    public static String getString(Locale locale, String baseName, Class<?> cls, String key) {
        return StaticResource.impl.getString(locale, baseName, cls, key);
    }

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
    public static String getString(Locale locale, String baseName, String key) {
        return StaticResource.impl.getString(locale, baseName, key);
    }

    /**
     * Clears the resources.
     */
    public synchronized static void clear() {
        StaticResource.impl.clear();
    }

    /**
     * Contains.
     *
     * @param locale the locale
     * @param baseName the base name
     * @param key the key
     * @return true, if successful
     */
    public static boolean contains(Locale locale, String baseName, String key) {
        return StaticResource.impl.contains(locale, baseName, key);
    }

    /**
     * Dump.
     *
     * @return the table
     */
    public static Table dump() {
        Table t = new TextTable();
        t.addCol("basename");
        t.addCol("locale");
        t.addCol("keys");
        for(String basename : ResourceUtil.listBasenames()) {
            for(Locale locale : ResourceUtil.listLocalesForBasename(basename)) {
                Map<String, String> bundle = ResourceUtil
                        .getResourceBundlesForBasenameAndLocale(basename, locale);
                t.addRow(basename, locale, bundle.size());
            }
        }
        return t;
    }

}
