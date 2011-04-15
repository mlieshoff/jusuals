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

import javax.xml.bind.*;

import org.apache.commons.lang.*;
import org.mili.core.io.*;
import org.mili.core.logging.*;
import org.mili.core.properties.*;
import org.mili.core.resource.generated.*;
import org.mili.core.xml.*;

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
    private final static String MISSING_RESOURCE = "!%0!";
    public final static String NAMESPACE = "org.mili.core.resource.generated";
    /** the constant for property PROP_LOGMISSINGRESOURCE. */
    public final static String PROP_LOGMISSINGRESOURCE = ID + "LogMissingResource";
    /** the constant for property PROP_THROWEXCEPTIONONMISSINGRESOURCE. */
    public final static String PROP_THROWEXCEPTIONONMISSINGRESOURCE =
            ID + "ThrowExceptionOnMissingResource";
    /* map base -> locale -> resourcebundle */
    private static Map<String, Map<Locale, Map<String, String>>> cache = new Hashtable<String,
            Map<Locale, Map<String, String>>>();

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
    public static Map<String, String> getResourceBundlesForBasenameAndLocale(String basename,
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
        URL url = getUrlFromUrlClassLoader(cl);
        String realBaseName = getRealBaseName(baseName);
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, cl);
        copyResourceBundleInResMap(bundle, realBaseName, locale);
        loadDevResources(locale, url, realBaseName, baseName);
    }

    private static void loadDevResources(Locale locale, URL url, String realBaseName,
            String baseName) {
        if ((locale.equals(Locale.GERMAN) || locale.equals(Locale.GERMANY)) && url != null) {
            InputStream is = null;
            try {
                is = FileUtil.getInputStream(url.getPath() + "/" + baseName
                        + "_dev.properties");
                copyPropertiesInResMap(PropUtil.readProperties(is), realBaseName, locale);
            } catch (Exception e) {
                // eat it
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) { // eat it!
                    }
                }
            }
        }
    }

    /**
     * Loads resources from xml.
     *
     * @param locale the locale
     * @param basename the basename
     * @param cl the classloader
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws JAXBException the jAXB exception
     */
    public static synchronized void loadFromXml(Locale locale, String baseName, ClassLoader cl)
            throws IOException, JAXBException {
        URL url = getUrlFromUrlClassLoader(cl);
        String filename = createFilename(url, baseName, locale, ".xml");
        loadFromXml(filename, locale, baseName, cl);
        filename = createFilename(url, baseName, null, "_dev.xml");
        if (new File(filename).exists()) {
            loadFromXml(filename, locale, baseName, cl);
        }
    }

    private static synchronized void loadFromXml(String filename, Locale locale,
            String baseName, ClassLoader cl) throws IOException,
            JAXBException {
        InputStream is = FileUtil.getInputStream(filename, FileAccessOrder
                .FilesystemThenClassloader, cl);
        Resources r = (Resources) XmlAccess.read(is, NAMESPACE);
        // check references
        for (int i = 0, m = r.getReference().size(); i < m; i++) {
            Reference ref = r.getReference().get(i);
//            loadFromXml(fn, locale, ref.getNamespace(), cl);
        }
        cacheText(r, locale, baseName);
        is.close();
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
        Map<Locale, Map<String, String>> m = cache.get(baseName);
        if (m != null) {
            Map<String, String> rb = m.get(locale);
            if (rb != null) {
                try {
                    String res = rb.get(key);
                    if (res == null) {
                        throw new MissingResourceException("missing resource!", null, key);
                    }
                    return res;
                } catch (MissingResourceException e) {
                    if (Boolean.getBoolean(PROP_LOGMISSINGRESOURCE)) {
                        MissingResourceLogger.INSTANCE.log(locale, key);
                    }
                    if (Boolean.getBoolean(PROP_THROWEXCEPTIONONMISSINGRESOURCE)) {
                        throw new MissingResourceException("Missing resource for locale ! "
                                + getInfo(locale, baseName, key), e.getClassName(), key);
                    } else {
                        log.warn("Missing resource for locale ! ", getInfo(locale, baseName,
                                key));
                        return createMissingResource(key);
                    }
                }
            }
            throw new IllegalStateException("no resource-bundle for locale defined ! "
                    + getInfo(locale, baseName, key));
        }
        throw new IllegalStateException("no locales for base-name defined ! "
                + getInfo(locale, baseName, key));
    }

    /**
     * Clears the resources.
     */
    public synchronized static void clear() {
        cache.clear();
    }

    private static String createMissingResource(String key) {
        int i = key.lastIndexOf(".");
        return MISSING_RESOURCE.replace("%0", i > 0 ? key.substring(i) : key);
    }

    private static String getInfo(Locale l, String baseName, String key) {
        final String pattern = "[locale=%1-%2, baseName=%3, key=%4]";
        return pattern
                .replace("%1", l.getISO3Country())
                .replace("%2", l.getISO3Language())
                .replace("%3", baseName)
                .replace("%4", key);
    }

    private static void cacheText(Resources r, Locale locale, String baseName) {
        List<Text> l = r.getText();
        System.out.println("baseName: " + baseName);
        Map<String, String> resMap = getResourceMap(baseName, locale);
        for (int i = 0, n = l.size(); i < n; i++) {
            Object o = l.get(i);
            if (o instanceof Text) {
                Text t = (Text) o;
                System.out.println(t.getName());
                resMap.put(t.getName(), StringUtils.strip(t.getContent().toString().trim()));
            }
        }
    }

    private static URL getUrlFromUrlClassLoader(ClassLoader cl) {
        if (cl instanceof URLClassLoader) {
            URLClassLoader urlCL = (URLClassLoader) cl;
            return urlCL.getURLs()[0];
        }
        return null;
    }

    private static String getRealBaseName(String baseName) {
        int i = baseName.lastIndexOf("/");
        if (i <= 0) {
            i = baseName.lastIndexOf("\\");
        }
        String bn = baseName;
        if (i > 0) {
            bn = baseName.substring(i + 1);
        }
        return bn;
    }

    private static Map<Locale, Map<String, String>> getLocaleMap(String baseName) {
        Map<Locale, Map<String, String>> localeMap = cache.get(baseName);
        if (localeMap == null) {
            localeMap = new Hashtable<Locale, Map<String, String>>();
            cache.put(baseName, localeMap);
        }
        return localeMap;
    }

    private static Map<String, String> getResourceMap(String baseName, Locale locale) {
        Map<Locale, Map<String, String>> localeMap = getLocaleMap(baseName);
        Map<String, String> resMap = localeMap.get(locale);
        if (resMap == null) {
            resMap = new Hashtable<String, String>();
            localeMap.put(locale, resMap);
        }
        return resMap;
    }

    private static void copyResourceBundleInResMap(ResourceBundle bundle, String baseName,
            Locale locale) {
        Map<String, String> resMap = getResourceMap(baseName, locale);
        for(String key : bundle.keySet()) {
            resMap.put(key, bundle.getString(key));
        }
    }

    private static void copyPropertiesInResMap(Properties p, String baseName, Locale locale) {
        Map<String, String> resMap = getResourceMap(baseName, locale);
        for (Iterator<Entry<Object, Object>> iterator = p.entrySet().iterator();
                iterator.hasNext();) {
            Entry<Object, Object> e = iterator.next();
            resMap.put(e.getKey().toString(), e.getValue().toString());
        }
    }

    private static String createFilename(URL url, String baseName, Locale locale,
            String extension) {
        StringBuilder filename = new StringBuilder();
        filename.append(url.getPath());
        filename.append(baseName);
        if (locale != null) {
            filename.append("_");
            filename.append(locale.getLanguage());
        }
        filename.append(extension);
        return filename.toString();
    }

}
