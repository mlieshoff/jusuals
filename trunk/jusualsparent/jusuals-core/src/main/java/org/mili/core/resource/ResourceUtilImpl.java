/*
 * ResourceUtilImpl.java
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
public class ResourceUtilImpl implements ResourceUtilInterface {
    private final static DefaultLogger log = DefaultLogger.getLogger(ResourceUtilImpl.class);
    private final static String ID = ResourceUtil.class.getName() + ".";
    private final static String MISSING_RESOURCE = "!%0!";
    public final static String NAMESPACE = "org.mili.core.resource.generated";
    /** the constant for property PROP_MISSINGRESOURCEHANDLER. */
    public final static String PROP_MISSINGRESOURCEHANDLER = ID + "MissingResourceHandler";
    /** the constant for property PROP_LOGMISSINGRESOURCE. */
    public final static String PROP_LOGMISSINGRESOURCE = ID + "LogMissingResource";
    /** the constant for property PROP_THROWEXCEPTIONONMISSINGRESOURCE. */
    public final static String PROP_THROWEXCEPTIONONMISSINGRESOURCE =
            ID + "ThrowExceptionOnMissingResource";
    /* map base -> locale -> resourcebundle */
    private Map<String, Map<Locale, Map<String, String>>> cache = new Hashtable<String,
            Map<Locale, Map<String, String>>>();
    private MissingResourceHandler handler = null;

    @Override
    public List<String> listBasenames() {
        return new ArrayList<String>(cache.keySet());
    }

    @Override
    public List<Locale> listLocalesForBasename(String basename) {
        return new ArrayList<Locale>(cache.get(basename).keySet());
    }

    @Override
    public Map<String, String> getResourceBundlesForBasenameAndLocale(String basename,
            Locale locale) {
        return cache.get(basename).get(locale);
    }

    @Override
    public synchronized void load(Locale locale, String baseName) {
        load(locale, baseName, ResourceUtilImpl.class.getClassLoader());
    }

    @Override
    public synchronized void load(Locale locale, String baseName, ClassLoader cl) {
        Validate.notNull(locale, "locale cannot be null!");
        Validate.notEmpty(baseName, "basename cannot be empty!");
        Validate.notNull(cl, "classloader cannot be null!");
        URL url = getUrlFromUrlClassLoader(cl);
        String realBaseName = getRealBaseName(baseName);
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, cl);
        copyResourceBundleInResMap(bundle, realBaseName, locale);
        loadDevResources(locale, url, realBaseName, baseName);
    }

    private void loadDevResources(Locale locale, URL url, String realBaseName,
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

    @Override
    public synchronized void loadFromXml(Locale locale, String baseName, ClassLoader cl)
            throws IOException, JAXBException {
        URL url = getUrlFromUrlClassLoader(cl);
        String filename = createFilename(url, baseName, locale, ".xml");
        loadFromXml(filename, locale, baseName, cl);
        filename = createFilename(url, baseName, null, "_dev.xml");
        if (new File(filename).exists()) {
            loadFromXml(filename, locale, baseName, cl);
        }
    }

    private synchronized void loadFromXml(String filename, Locale locale, String baseName,
            ClassLoader cl) throws IOException, JAXBException {
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

    @Override
    public String getString(Locale locale, String baseName, Object o, String key) {
        Validate.notNull(o, "object cannot be null!");
        return getString(locale, baseName, o.getClass(), key);
    }

    @Override
    public String getString(Locale locale, String baseName, Class<?> cls, String key) {
        Validate.notNull(locale, "locale cannot be null!");
        Validate.notEmpty(baseName, "basename cannot be empty!");
        Validate.notNull(cls, "class cannot be null!");
        Validate.notEmpty(key, "key cannot be empty!");
        return getString(locale, baseName, cls.getName().concat(".").concat(key));
    }

    @Override
    public String getString(Locale locale, String baseName, String key) {
        Validate.notNull(locale, "locale cannot be null!");
        Validate.notEmpty(baseName, "basename cannot be empty!");
        Validate.notEmpty(key, "key cannot be empty!");
        Map<String, String> bundle = getBundle(locale, baseName, key);
        try {
            String res = bundle.get(key);
            if (res == null) {
                throw new MissingResourceException("missing resource!", null, key);
            }
            return res;
        } catch (MissingResourceException e) {
            setUpMissingResourceHandler(locale, baseName, key);
            if (Boolean.getBoolean(PROP_LOGMISSINGRESOURCE)) {
                MissingResourceLogger.INSTANCE.log(locale, key);
            }
            if (Boolean.getBoolean(PROP_THROWEXCEPTIONONMISSINGRESOURCE)) {
                throw new MissingResourceException("Missing resource for locale ! "
                        + getInfo(locale, baseName, key), e.getClassName(), key);
            } else {
//                log.warn("Missing resource for locale ! ", getInfo(locale, baseName,
//                        key));
                return createMissingResource(key);
            }
        }
    }

    @Override
    public synchronized void clear() {
        cache.clear();
    }

    @Override
    public boolean contains(Locale locale, String baseName, String key) {
        Map<String, String> bundle = getBundle(locale, baseName, key);
        return bundle.containsKey(key);
    }

    private Map<String, String> getBundle(Locale locale, String baseName, String key) {
        Map<Locale, Map<String, String>> localeBundles = cache.get(baseName);
        if (localeBundles != null) {
            Map<String, String> bundle = localeBundles.get(locale);
            if (bundle != null) {
                return bundle;
            } else {
                throw new IllegalStateException("no resource-bundle for locale defined ! "
                        + getInfo(locale, baseName, key));
            }
        } else {
            throw new IllegalStateException("no locales for base-name defined ! "
                    + getInfo(locale, baseName, key));
        }
    }

    private String createMissingResource(String key) {
        int i = key.lastIndexOf(".");
        return MISSING_RESOURCE.replace("%0", i > 0 ? key.substring(i) : key);
    }

    private String getInfo(Locale l, String baseName, String key) {
        final String pattern = "[locale=%1-%2, baseName=%3, key=%4]";
        return pattern
                .replace("%1", l.getISO3Country())
                .replace("%2", l.getISO3Language())
                .replace("%3", baseName)
                .replace("%4", key);
    }

    private void cacheText(Resources r, Locale locale, String baseName) {
        List<Text> l = r.getText();
        Map<String, String> resMap = getResourceMap(baseName, locale);
        for (int i = 0, n = l.size(); i < n; i++) {
            Object o = l.get(i);
            if (o instanceof Text) {
                Text t = (Text) o;
                resMap.put(t.getName(), StringUtils.strip(t.getContent().toString().trim()));
            }
        }
    }

    private URL getUrlFromUrlClassLoader(ClassLoader cl) {
        if (cl instanceof URLClassLoader) {
            URLClassLoader urlCL = (URLClassLoader) cl;
            return urlCL.getURLs()[0];
        }
        return null;
    }

    private String getRealBaseName(String baseName) {
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

    private Map<Locale, Map<String, String>> getLocaleMap(String baseName) {
        Map<Locale, Map<String, String>> localeMap = cache.get(baseName);
        if (localeMap == null) {
            localeMap = new Hashtable<Locale, Map<String, String>>();
            cache.put(baseName, localeMap);
        }
        return localeMap;
    }

    private Map<String, String> getResourceMap(String baseName, Locale locale) {
        Map<Locale, Map<String, String>> localeMap = getLocaleMap(baseName);
        Map<String, String> resMap = localeMap.get(locale);
        if (resMap == null) {
            resMap = new Hashtable<String, String>();
            localeMap.put(locale, resMap);
        }
        return resMap;
    }

    private void copyResourceBundleInResMap(ResourceBundle bundle, String baseName,
            Locale locale) {
        Map<String, String> resMap = getResourceMap(baseName, locale);
        for(String key : bundle.keySet()) {
            resMap.put(key, bundle.getString(key));
        }
    }

    private void copyPropertiesInResMap(Properties p, String baseName, Locale locale) {
        Map<String, String> resMap = getResourceMap(baseName, locale);
        for (Iterator<Entry<Object, Object>> iterator = p.entrySet().iterator();
                iterator.hasNext();) {
            Entry<Object, Object> e = iterator.next();
            resMap.put(e.getKey().toString(), e.getValue().toString());
        }
    }

    private String createFilename(URL url, String baseName, Locale locale,
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

    private synchronized void setUpMissingResourceHandler(Locale locale, String baseName,
            String key) {
        String clsname = System.getProperty(PROP_MISSINGRESOURCEHANDLER);
        if (!StringUtils.isEmpty(clsname) && handler == null) {
            try {
                log.debug("create missing resource handler[", clsname, "]");
                handler = (MissingResourceHandler) Class.forName(clsname).newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        if (handler != null) {
            log.debug("call missing resource handler to handle[", baseName, ", ", key, "]");
            handler.handle(locale, baseName, key);
        }
    }

}
