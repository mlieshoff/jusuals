/*
 * ResourceHelper.java
 *
 * 26.10.2009
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

import org.apache.commons.lang.*;

/**
 * This class defines a helper for resource util.
 *
 * @author Michael Lieshoff
 *
 */
public class ResourceHelper implements ResourceManager {
    private Locale locale = null;
    private String baseName = "";
    private Class<?> cls = null;

    /**
     * Instantiates a new resource helper.
     *
     * @param cls the class used as anchor
     * @param locale the locale
     * @param baseName the base name
     */
    public ResourceHelper(Class<?> cls, Locale locale, String baseName) {
        super();
        Validate.notNull(cls, "cls");
        Validate.notNull(locale, "locale");
        Validate.notEmpty(baseName, "basename");
        this.locale = locale;
        this.baseName = baseName;
        this.cls = cls;
    }

    /**
     * Instantiates a new resource helper.
     *
     * @param o the object to get anchor class
     * @param locale the locale
     * @param baseName the base name
     */
    public ResourceHelper(Object o, Locale locale, String baseName) {
        this(o.getClass(), locale, baseName);
    }

    /**
     * Instantiates a new resource helper.
     *
     * @param baseName the base name
     */
    public ResourceHelper(String baseName) {
        super();
        Validate.notEmpty(baseName, "basename");
        this.locale = Locale.getDefault();
        this.baseName = baseName;
    }

    /**
     * Instantiates a new resource helper.
     *
     * @param locale the locale
     * @param baseName the base name
     */
    public ResourceHelper(Locale locale, String baseName) {
        this(ResourceHelper.class, locale, baseName);
    }

    /**
     * Instantiates a new resource helper.
     *
     * @param o the object used to get anchor class
     * @param baseName the base name
     */
    public ResourceHelper(Object o, String baseName) {
        this(o, Locale.getDefault(), baseName);
    }

    /**
     * Instantiates a new resource helper.
     *
     * @param cls the class used as anchor class
     * @param baseName the base name
     */
    public ResourceHelper(Class<?> cls, String baseName) {
        this(cls, Locale.getDefault(), baseName);
    }

    @Override
    public String getString(String key) {
        if (this.cls == ResourceHelper.class) {
            return ResourceUtil.getString(this.locale, this.baseName, key);
        }
        return ResourceUtil.getString(this.locale, this.baseName, this.cls, key);

    }

    @Override
    public String[] getStrings(String... keys) {
        Validate.notEmpty(keys, "keys");
        String[] s = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            String ss = this.getString(keys[i]);
            s[i] = ss;
        }
        return s;
    }

    @Override
    public String getString(Object o, String key) {
        Validate.notNull(o, "o");
        return getString(o.getClass(), key);
    }

    @Override
    public String getString(Class<?> cls, String key) {
        return ResourceUtil.getString(this.locale, this.baseName, cls, key);
    }

    @Override
    public String getString(Locale locale, String baseName, String key) {
        return ResourceUtil.getString(locale, baseName, key);
    }

    @Override
    public String getString(Locale locale, String baseName, Class<?> cls, String key) {
        return ResourceUtil.getString(locale, baseName, cls, key);
    }

    @Override
    public String getString(Locale locale, String baseName, Object o, String key) {
        return ResourceUtil.getString(locale, baseName, o, key);
    }

    @Override
    public String getProperty(String key) {
        return ResourceUtil.getString(this.locale, this.baseName, key);
    }

    @Override
    public synchronized void load(Locale locale, String baseName, ClassLoader cl) {
        ResourceUtil.load(locale, baseName, cl);
    }

    @Override
    public void load(Locale locale, String baseName) {
        ResourceUtil.load(locale, baseName);
    }

    @Override
    public Map<String, String> getResourceBundlesForBasenameAndLocale(String basename, Locale locale) {
        return ResourceUtil.getResourceBundlesForBasenameAndLocale(basename, locale);
    }

    @Override
    public List<String> listBasenames() {
        return ResourceUtil.listBasenames();
    }

    @Override
    public List<Locale> listLocalesForBasename(String basename) {
        return ResourceUtil.listLocalesForBasename(basename);
    }

    @Override
    public boolean contains(String key) {
        return ResourceUtil.contains(this.locale, this.baseName, key);
    }

}
