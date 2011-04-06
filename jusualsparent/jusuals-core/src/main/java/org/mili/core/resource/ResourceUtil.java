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
import java.net.*;
import java.util.*;
import java.util.Map.*;

import org.apache.commons.lang.*;
import org.mili.core.io.*;
import org.mili.core.logging.*;

/**
 * This class is a helper for resources and bundles. It's implemented that a kind of developer
 * resources could be defined for use with a "default" locale. The developer resources for this
 * locale will be loaded first the originally resources for the same locale then.
 *
 * @author Michael Lieshoff
 *
 */
public class ResourceUtil {
    private final static DefaultLogger log = DefaultLogger.getLogger(ResourceUtil.class);
    private final static String ID = ResourceUtil.class.getName() + ".";
    /** the constant for property PROP_LOGMISSINGRESOURCE. */
    public final static String PROP_LOGMISSINGRESOURCE = ID + "LogMissingResource";
    /** the constant for property PROP_THROWEXCEPTIONONMISSINGRESOURCE. */
    public final static String PROP_THROWEXCEPTIONONMISSINGRESOURCE =
            ID + "ThrowExceptionOnMissingResource";
    /* map base -> locale -> resourcebundle */
    private static Map<String, Map<Locale, ResourceBundle>> cache = new Hashtable<String,
            Map<Locale, ResourceBundle>>();
    /* dev map base -> locale -> resourcebundle */
    private static Map<String, Map<Locale, Map<String, String>>> devcache =
            new Hashtable<String, Map<Locale, Map<String, String>>>();

    /**
     * List all basenames.
     *
     * @return list with all basenames.
     */
    public static List<String> listBasenames() {
        return new ArrayList<String>(cache.keySet());
    }

    /**
     * List locales for basename.
     *
     * @param basename the basename
     * @return locales for basename
     */
    public static List<Locale> listLocalesForBasename(String basename) {
        return new ArrayList<Locale>(cache.get(basename).keySet());
    }

    /**
     * Gets the resource bundles for basename and locale.
     *
     * @param basename the basename
     * @param locale the locale
     * @return the resource bundles for basename and locale
     */
    public static ResourceBundle getResourceBundlesForBasenameAndLocale(String basename,
            Locale locale) {
        return cache.get(basename).get(locale);
    }

    /**
     * Loads resource for locale and basename.
     *
     * @param locale the locale
     * @param baseName the base name
     */
    public static synchronized void load(Locale locale, String baseName) {
        load(locale, baseName, ResourceUtil.class.getClassLoader());
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
        Validate.notNull(locale);
        Validate.notEmpty(baseName);
        Validate.notNull(cl);
        int i = baseName.lastIndexOf("/");
        if (i <= 0) {
            i = baseName.lastIndexOf("\\");
        }
        String bn = baseName;
        if (i > 0) {
            bn = baseName.substring(i + 1);
        }
        ResourceBundle b = ResourceBundle.getBundle(baseName, locale, cl);
        Map<Locale, ResourceBundle> m = cache.get(bn);
        if (m == null) {
            m = new java.util.Hashtable<Locale, ResourceBundle>();
        }
        m.put(locale, b);
        cache.put(bn, m);
        if (locale.equals(Locale.GERMAN) || locale.equals(Locale.GERMANY)) {
            Map<String, String> mss = new Hashtable<String, String>();
            Map<Locale, Map<String, String>> m0 = devcache.get(bn);
            if (m0 == null) {
                m0 = new java.util.Hashtable<Locale, Map<String, String>>();
            }
            // load dev
            if (cl instanceof URLClassLoader) {
                URLClassLoader ucl = (URLClassLoader) cl;
                URL url = ucl.getURLs()[0];
                InputStream is = null;
                try {
                    is = FileUtil.getInputStream(url.getPath() + "/" + baseName
                            + "_dev.properties");
                    Properties p = new Properties();
                    try {
                        p.load(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (Iterator<Entry<Object, Object>> iterator = p.entrySet().iterator();
                            iterator.hasNext();) {
                        Entry<Object, Object> e = iterator.next();
                        mss.put(e.getKey().toString(), e.getValue().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // eat it
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            // eat it
                        }
                    }
                }
            } else {
                System.out.println("not supportd: " + locale);
            }
            m0.put(locale, mss);
            devcache.put(bn, m0);
        }
        return;
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
        Validate.notNull(o);
        return getString(locale, baseName, o.getClass(), key);
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
        Validate.notNull(locale);
        Validate.notEmpty(baseName);
        Validate.notNull(cls);
        Validate.notEmpty(key);
        return getString(locale, baseName, cls.getName().concat(".").concat(key));
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
        Validate.notNull(locale);
        Validate.notEmpty(baseName);
        Validate.notEmpty(key);
        Map<Locale, ResourceBundle> m = cache.get(baseName);
        Map<Locale, Map<String, String>> m0 = devcache.get(baseName);
        if (m != null) {
            ResourceBundle rb = m.get(locale);
            String devres = null;
            if (m0 != null) {
                Map<String, String> mm0 = m0.get(locale);
                if (mm0 != null) {
                    devres = mm0.get(key);
                }
            }
            if (rb != null) {
                try {
                    String res = rb.getString(key);
                    return res;
                } catch (MissingResourceException e) {
                    if (devres != null) {
                        return devres;
                    }
                    if (Boolean.getBoolean(PROP_LOGMISSINGRESOURCE)) {
                        MissingResourceLogger.INSTANCE.log(locale, key);
                    }
                    if (Boolean.getBoolean(PROP_THROWEXCEPTIONONMISSINGRESOURCE)) {
                        throw new MissingResourceException("Missing resource for locale ! "
                                + getInfo(locale, baseName, key), e.getClassName(), key);
                    } else {
                        log.warn("Missing resource for locale ! ", getInfo(locale, baseName,
                                key));
                        return "!".concat(key.substring(key.lastIndexOf(".")).concat("!"));
                    }
                }
            }
            throw new IllegalStateException("no resource-bundle for locale defined ! "
                    + getInfo(locale, baseName, key));
        }
        throw new IllegalStateException("no locales for base-name defined ! "
                + getInfo(locale, baseName, key));
    }

    static void clear() {
        cache.clear();
        devcache.clear();
    }

    private static String getInfo(Locale l, String baseName, String key) {
        final String pattern = "[locale=%1-%2, baseName=%3, key=%4]";
        return pattern
                .replace("%1", l.getISO3Country())
                .replace("%2", l.getISO3Language())
                .replace("%3", baseName)
                .replace("%4", key);
    }
}
