/*
 * VersionUtilsTest.java
 *
 * 10.05.2011
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

import org.junit.*;
import static org.junit.Assert.*;
import org.mili.core.manifest.VersionUtils.*;

/**
 * @author Michael Lieshoff
 */
public class VersionUtilsTest {

    @After
    public void after() {
        VersionUtils.setClassWrapper(new VersionUtils.DefaultClassWrapper());
        VersionUtils.setPackageWrapper(new VersionUtils.DefaultPackageWrapper());
    }

    @Test
    public void shouldConstruct() {
        new VersionUtils();
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetImplementationTitleBecauseClassIsNull() {
        VersionUtils.getImplementationTitle(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetImplementationTitleBecausePackageIsNull() {
        VersionUtils.setClassWrapper(new NullClassWrapper());
        VersionUtils.getImplementationTitle(String.class);
    }

    @Test
    public void shouldGetImplementationTitle() {
        VersionUtils.setClassWrapper(new TestClassWrapper());
        VersionUtils.setPackageWrapper(new TestPackageWrapper());
        assertEquals("ImplementationTitle", VersionUtils.getImplementationTitle(String.class));
    }

    @Test
    public void shouldGetRealImplementationTitle() {
        assertTrue(VersionUtils.getImplementationTitle(String.class).length() > 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetImplementationVersionBecauseClassIsNull() {
        VersionUtils.getImplementationVersion(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetImplementationVersionBecausePackageIsNull() {
        VersionUtils.setClassWrapper(new NullClassWrapper());
        VersionUtils.getImplementationVersion(String.class);
    }

    @Test
    public void shouldGetImplementationVersion() {
        VersionUtils.setClassWrapper(new TestClassWrapper());
        VersionUtils.setPackageWrapper(new TestPackageWrapper());
        assertEquals("ImplementationVersion", VersionUtils.getImplementationVersion(
                String.class));
    }

    @Test
    public void shouldGetRealImplementationVersion() {
        assertTrue(VersionUtils.getImplementationVersion(String.class).length() > 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetImplementationVendorBecauseClassIsNull() {
        VersionUtils.getImplementationVendor(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetImplementationVendorBecausePackageIsNull() {
        VersionUtils.setClassWrapper(new NullClassWrapper());
        VersionUtils.getImplementationVendor(String.class);
    }

    @Test
    public void shouldGetImplementationVendor() {
        VersionUtils.setClassWrapper(new TestClassWrapper());
        VersionUtils.setPackageWrapper(new TestPackageWrapper());
        assertEquals("ImplementationVendor", VersionUtils.getImplementationVendor(
                String.class));
    }

    @Test
    public void shouldGetRealImplementationVendor() {
        assertTrue(VersionUtils.getImplementationVendor(String.class).length() > 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetSpecificationTitleBecauseClassIsNull() {
        VersionUtils.getSpecificationTitle(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetSpecificationTitleBecausePackageIsNull() {
        VersionUtils.setClassWrapper(new NullClassWrapper());
        VersionUtils.getSpecificationTitle(String.class);
    }

    @Test
    public void shouldGetSpecificationTitle() {
        VersionUtils.setClassWrapper(new TestClassWrapper());
        VersionUtils.setPackageWrapper(new TestPackageWrapper());
        assertEquals("SpecificationTitle", VersionUtils.getSpecificationTitle(String.class));
    }

    @Test
    public void shouldGetRealSpecificationTitle() {
        assertTrue(VersionUtils.getSpecificationTitle(String.class).length() > 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetSpecificationVersionBecauseClassIsNull() {
        VersionUtils.getSpecificationVersion(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetSpecificationVersionBecausePackageIsNull() {
        VersionUtils.setClassWrapper(new NullClassWrapper());
        VersionUtils.getSpecificationVersion(String.class);
    }

    @Test
    public void shouldGetSpecificationVersion() {
        VersionUtils.setClassWrapper(new TestClassWrapper());
        VersionUtils.setPackageWrapper(new TestPackageWrapper());
        assertEquals("SpecificationVersion", VersionUtils.getSpecificationVersion(
                String.class));
    }

    @Test
    public void shouldGetRealSpecificationVersion() {
        assertTrue(VersionUtils.getSpecificationVersion(String.class).length() > 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetSpecificationVendorBecauseClassIsNull() {
        VersionUtils.getSpecificationVendor(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetSpecificationVendorBecausePackageIsNull() {
        VersionUtils.setClassWrapper(new NullClassWrapper());
        VersionUtils.getSpecificationVendor(String.class);
    }

    @Test
    public void shouldGetSpecificationVendor() {
        VersionUtils.setClassWrapper(new TestClassWrapper());
        VersionUtils.setPackageWrapper(new TestPackageWrapper());
        assertEquals("SpecificationVendor", VersionUtils.getSpecificationVendor(String.class));
    }

    @Test
    public void shouldGetRealSpecificationVendor() {
        assertTrue(VersionUtils.getSpecificationVendor(String.class).length() > 0);
    }

    class NullClassWrapper implements ClassWrapper {
        @Override
        public Package getPackage(Class<?> cls) {
            return null;
        }
    }

    class TestClassWrapper implements ClassWrapper {
        @Override
        public Package getPackage(Class<?> cls) {
            return cls.getPackage();
        }
    }

    class NullPackageWrapper implements PackageWrapper {

        @Override
        public String getImplementationTitle(Package pckge) {
            return null;
        }

        @Override
        public String getImplementationVendor(Package pckge) {
            return null;
        }

        @Override
        public String getImplementationVersion(Package pckge) {
            return null;
        }

        @Override
        public String getSpecificationTitle(Package pckge) {
            return null;
        }

        @Override
        public String getSpecificationVendor(Package pckge) {
            return null;
        }

        @Override
        public String getSpecificationVersion(Package pckge) {
            return null;
        }
    }

    class TestPackageWrapper implements PackageWrapper {

        @Override
        public String getImplementationTitle(Package pckge) {
            return "ImplementationTitle";
        }

        @Override
        public String getImplementationVendor(Package pckge) {
            return "ImplementationVendor";
        }

        @Override
        public String getImplementationVersion(Package pckge) {
            return "ImplementationVersion";
        }

        @Override
        public String getSpecificationTitle(Package pckge) {
            return "SpecificationTitle";
        }

        @Override
        public String getSpecificationVendor(Package pckge) {
            return "SpecificationVendor";
        }

        @Override
        public String getSpecificationVersion(Package pckge) {
            return "SpecificationVersion";
        }
    }

}
