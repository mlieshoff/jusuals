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
 * This class helpswith acces to manifest information in manifest.
 *
 * @author Michael Lieshoff
 *
 */
public class VersionUtils {
    private static ClassWrapper classWrapper = new DefaultClassWrapper();
    private static PackageWrapper packageWrapper = new DefaultPackageWrapper();

    /**
     * Gets the implementation title.
     *
     * @param cls the cls
     * @return the implementation title
     */
    public static String getImplementationTitle(Class<?> cls) {
        checkClass(cls);
        return packageWrapper.getImplementationTitle(classWrapper.getPackage(cls));
    }

    /**
     * Gets the implementation vendor.
     *
     * @param cls the cls
     * @return the implementation vendor
     */
    public static String getImplementationVendor(Class<?> cls) {
        checkClass(cls);
        return packageWrapper.getImplementationVendor(classWrapper.getPackage(cls));
    }

    /**
     * Gets the implementation version.
     *
     * @param cls the cls
     * @return the implementation version
     */
    public static String getImplementationVersion(Class<?> cls) {
        checkClass(cls);
        return packageWrapper.getImplementationVersion(classWrapper.getPackage(cls));
    }

    /**
     * Gets the specification title.
     *
     * @param cls the cls
     * @return the specification title
     */
    public static String getSpecificationTitle(Class<?> cls) {
        checkClass(cls);
        return packageWrapper.getSpecificationTitle(classWrapper.getPackage(cls));
    }

    /**
     * Gets the specification vendor.
     *
     * @param cls the cls
     * @return the specification vendor
     */
    public static String getSpecificationVendor(Class<?> cls) {
        checkClass(cls);
        return packageWrapper.getSpecificationVendor(classWrapper.getPackage(cls));
    }

    /**
     * Gets the specification version.
     *
     * @param cls the cls
     * @return the specification version
     */
    public static String getSpecificationVersion(Class<?> cls) {
        checkClass(cls);
        return packageWrapper.getSpecificationVersion(classWrapper.getPackage(cls));
    }

    private static void checkClass(Class<?> cls) {
        Validate.notNull(cls);
        Validate.notNull(classWrapper.getPackage(cls));
    }

    static void setClassWrapper(ClassWrapper newClassWrapper) {
        classWrapper = newClassWrapper;
    }

    static void setPackageWrapper(PackageWrapper newPackageWrapper) {
        packageWrapper = newPackageWrapper;
    }

    interface ClassWrapper {
        Package getPackage(Class<?> cls);
    }

    static class DefaultClassWrapper implements ClassWrapper {
        @Override
        public Package getPackage(Class<?> cls) {
            return cls.getPackage();
        }
    }

    interface PackageWrapper {
        String getImplementationTitle(Package pckge);
        String getImplementationVendor(Package pckge);
        String getImplementationVersion(Package pckge);
        String getSpecificationTitle(Package pckge);
        String getSpecificationVendor(Package pckge);
        String getSpecificationVersion(Package pckge);
    }

    static class DefaultPackageWrapper implements PackageWrapper {
        @Override
        public String getImplementationTitle(Package pckge) {
            return pckge.getImplementationTitle();
        }

        @Override
        public String getImplementationVendor(Package pckge) {
            return pckge.getImplementationVendor();
        }

        @Override
        public String getImplementationVersion(Package pckge) {
            return pckge.getImplementationVersion();
        }

        @Override
        public String getSpecificationTitle(Package pckge) {
            return pckge.getSpecificationTitle();
        }

        @Override
        public String getSpecificationVendor(Package pckge) {
            return pckge.getSpecificationVendor();
        }

        @Override
        public String getSpecificationVersion(Package pckge) {
            return pckge.getSpecificationVersion();
        }
    }
}
