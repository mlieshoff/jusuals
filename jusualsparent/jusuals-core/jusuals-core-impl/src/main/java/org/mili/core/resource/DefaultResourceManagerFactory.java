/*
 * DefaultResourceManagerFactory.java
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

import org.apache.commons.lang.*;

/**
 * This class defines a default implementation of a resource manager factory.
 *
 * @author Michael Lieshoff
 *
 */
public class DefaultResourceManagerFactory {
    private final static DefaultResourceManagerFactory INSTANCE =
            new DefaultResourceManagerFactory();

    private DefaultResourceManagerFactory() {
        super();
    }

    /**
     * Creates a resource manager for class and locale. The base name is generated by the
     * package. By example : java -> java, java.lang -> java-lang, java.lang.core -> java-lang<br/>
     * Default packages are not supported!
     *
     * @param cls class
     * @param locale locale
     * @return resource manager
     */
    public ResourceManager create(Class<?> cls, Locale locale) {
        Validate.notNull(cls, "class cannot be null!");
        Validate.notNull(locale, "locale cannot be null!");
        String bn = this.createBasename(cls.getPackage().getName());
        Validate.notEmpty(bn, "basename cannot be empty!");
        return new ResourceHelper(cls, locale, bn);
    }

    String createBasename(String pn) {
        Validate.notEmpty(pn, "package name cannot be empty!");
        int i0 = pn.indexOf(".");
        if (i0 > 0) {
            // java.lang
            int i1 = pn.indexOf(".", i0 + 1);
            if (i1 > 0) {
                // java.lang.core
                return pn.substring(0, i1).replace(".", "-");
            } else {
                // java.lang
                return pn.replace(".", "-");
            }
        } else {
            // java
            return pn;
        }
    }

    /**
     * Gets the single instance of DefaultResourceManagerFactory.
     *
     * @return single instance of DefaultResourceManagerFactory
     */
    public static DefaultResourceManagerFactory getInstance() {
        return INSTANCE;
    }

}
