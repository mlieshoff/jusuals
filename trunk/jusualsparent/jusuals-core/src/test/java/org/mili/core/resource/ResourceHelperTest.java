/*
 * ResourceHelperTest.java
 *
 * 05.03.2010
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

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 *
 */
public class ResourceHelperTest {

    @Test
    public void testResourceHelperClassOfQLocaleString() {
        try {
            new ResourceHelper(null, Locale.GERMANY, "aaa");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new ResourceHelper(String.class, null, "aaa");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new ResourceHelper(String.class, Locale.GERMANY, "");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new ResourceHelper(String.class, Locale.GERMANY, null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        new ResourceHelper(String.class, Locale.GERMANY, "aaa");
    }

    @Test
    public void testResourceHelperObjectLocaleString() {
        try {
            Object o = null;
            new ResourceHelper(o, Locale.GERMANY, "aaa");
            fail();
        } catch (NullPointerException e) {
        }
        try {
            new ResourceHelper(new Object(), null, "aaa");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new ResourceHelper(new Object(), Locale.GERMANY, "");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new ResourceHelper(new Object(), Locale.GERMANY, null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        new ResourceHelper(new Object(), Locale.GERMANY, "aaa");
    }

    @Test
    public void testResourceHelperString() {
        try {
            new ResourceHelper("");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new ResourceHelper(null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        new ResourceHelper("aaa");
    }

    @Test
    public void testResourceHelperLocaleString() {
        try {
            new ResourceHelper(Locale.GERMANY, "");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new ResourceHelper(Locale.GERMANY, null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        new ResourceHelper(Locale.GERMANY, "aaa");
    }

    @Test
    public void testResourceHelperObjectString() {
        try {
            new ResourceHelper(new Object(), "");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new ResourceHelper(new Object(), null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        new ResourceHelper(new Object(), "aaa");
    }

    @Test
    public void testResourceHelperClassOfQString() {
        try {
            new ResourceHelper(Object.class, "");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new ResourceHelper(Object.class, null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        new ResourceHelper(Object.class, "aaa");
    }

    @Test
    public void testGetStringString() {
        try {
            new ResourceHelper(Object.class, "");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new ResourceHelper(Object.class, null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        new ResourceHelper(Object.class, "aaa");
    }

    @Test
    @Ignore
    public void testGetStringObjectString() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testGetStringClassOfQString() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testGetStringLocaleStringString() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testGetStringLocaleStringClassOfQString() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testGetStringLocaleStringObjectString() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testGetProperty() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testLoad() {
        fail("Not yet implemented");
    }

}
