/*
 * VersionUtils.java
 *
 * 03.05.2011
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
package org.mili.core.manifest;

import org.apache.commons.lang.*;

/**
 * @author Michael Lieshoff
 *
 */
public class VersionUtils {

    /**
     * Gets the implementation title.
     *
     * @param cls the cls
     * @return the implementation title
     */
    public static String getImplementationTitle(Class<?> cls) {
        Validate.notNull(cls);
        Validate.notNull(cls.getPackage());
        return cls.getPackage().getImplementationTitle();
    }

    /**
     * Gets the implementation vendor.
     *
     * @param cls the cls
     * @return the implementation vendor
     */
    public static String getImplementationVendor(Class<?> cls) {
        Validate.notNull(cls);
        Validate.notNull(cls.getPackage());
        return cls.getPackage().getImplementationVendor();
    }

    /**
     * Gets the implementation version.
     *
     * @param cls the cls
     * @return the implementation version
     */
    public static String getImplementationVersion(Class<?> cls) {
        Validate.notNull(cls);
        Validate.notNull(cls.getPackage());
        return cls.getPackage().getImplementationVersion();
    }

    /**
     * Gets the specification title.
     *
     * @param cls the cls
     * @return the specification title
     */
    public static String getSpecificationTitle(Class<?> cls) {
        Validate.notNull(cls);
        Validate.notNull(cls.getPackage());
        return cls.getPackage().getSpecificationTitle();
    }

    /**
     * Gets the specification vendor.
     *
     * @param cls the cls
     * @return the specification vendor
     */
    public static String getSpecificationVendor(Class<?> cls) {
        Validate.notNull(cls);
        Validate.notNull(cls.getPackage());
        return cls.getPackage().getSpecificationVendor();
    }

    /**
     * Gets the specification version.
     *
     * @param cls the cls
     * @return the specification version
     */
    public static String getSpecificationVersion(Class<?> cls) {
        Validate.notNull(cls);
        Validate.notNull(cls.getPackage());
        return cls.getPackage().getSpecificationVersion();
    }

}
