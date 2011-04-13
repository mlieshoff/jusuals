/*
 * ResourceUtilTest.java
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

import org.apache.commons.io.*;
import org.junit.*;
import org.mili.test.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class ResourceUtilTest {
    private File dir = null;
    private File dir1 = null;
    private File file1 = null;
    private File file3 = null;
    private File dir2 = null;
    private File dir2xml = null;
    private File file2 = null;
    private File file4 = null;
    private File file5 = null;
    private File dir3 = null;
    private File file6 = null;
    private File file7 = null;

    @Before
    public void setUp() throws Exception {
        this.dir = TestUtils.getTmpFolder(ResourceUtilTest.class);
        this.dir1 = new File(this.dir, "dir1");
        this.file1 = new File(this.dir1, "test_de.properties");
        this.file3 = new File(this.dir1, "local_de.properties");
        this.dir2 = new File(this.dir, "dir2");
        this.dir2xml = new File(this.dir, "dir2/xml");
        this.file2 = new File(this.dir2, "local_de.properties");
        this.file4 = new File(this.dir2, "test_en.properties");
        this.file5 = new File(this.dir2, "test_de.properties");
        this.dir3 = new File(this.dir, "dir3");
        this.file6 = new File(this.dir3, "conf/test_de.properties");
        this.file7 = new File(this.dir3, "conf/test_dev.properties");
        if (this.dir1.exists()) {
            try {
                FileUtils.forceDelete(this.dir1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.dir1.mkdirs();
        if (this.dir2.exists()) {
            try {
                FileUtils.forceDelete(this.dir2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.dir2.mkdirs();
        if (this.dir3.exists()) {
            try {
                FileUtils.forceDelete(this.dir3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.dir3.mkdirs();
        File fd = new File(this.dir3, "conf");
        fd.mkdirs();
        // f1
        final String ID = ResourceUtilTest.class.getName();
        Properties p = new Properties();
        p.setProperty(ID + ".p0", "hallo");
        p.setProperty(ID + ".p1", "welt");
        OutputStream os = new FileOutputStream(this.file1);
        p.store(os, "");
        os = new FileOutputStream(this.file5);
        p.store(os, "");
        os.close();
        os = new FileOutputStream(this.file6);
        p.store(os, "");
        // f2
        StringBuilder s = new StringBuilder();
        s.append("key.1=Push\n");
        s.append("java.lang.String.key.1=Push\n");
        FileUtils.writeStringToFile(this.file2, s.toString());
        FileUtils.writeStringToFile(this.file3, s.toString());
        FileUtils.writeStringToFile(this.file7, s.toString());

        p = new Properties();
        p.setProperty(ID + ".p0", "hello");
        p.setProperty(ID + ".p1", "world");
        os = new FileOutputStream(this.file4);
        p.store(os, "");
    }

    @After
    public void after() {
        ResourceUtil.clear();
    }

    @Test
    public void shouelOneLocaleLoadThroughURLClassLoader() throws Exception {
        URL url = this.dir3.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
        ResourceUtil.load(Locale.GERMAN, "conf/test", classLoader);
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMAN, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMAN, "test", this, "p1"));
        assertEquals("Push", ResourceUtil.getString(Locale.GERMAN, "test", "key.1"));
        assertEquals("Push", ResourceUtil.getString(Locale.GERMAN, "test",
                "java.lang.String.key.1"));
    }

    @Test
    public void shouldLoadTwoLocalesThroughURLClassLoader() throws Exception {
        URL url = this.dir2.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
        ResourceUtil.load(Locale.GERMAN, "test", classLoader);
        ResourceUtil.load(Locale.ENGLISH, "test", classLoader);
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMAN, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMAN, "test", this, "p1"));
        assertEquals("hello", ResourceUtil.getString(Locale.ENGLISH, "test", this, "p0"));
        assertEquals("world", ResourceUtil.getString(Locale.ENGLISH, "test", this, "p1"));
    }

    @Test
    public void shouldTestNormalUse() throws Exception {
        URL url = this.dir1.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
        ResourceUtil.load(Locale.GERMANY, "local", classLoader);
        // negative
        try {
            System.setProperty(ResourceUtil.PROP_THROWEXCEPTIONONMISSINGRESOURCE, "true");
            assertEquals("Push", ResourceUtil.getString(Locale.GERMANY, "local", "key.555"));
            fail("exception awaited !");
        } catch (MissingResourceException e) {
            assertTrue(true);
        }
        System.setProperty(ResourceUtil.PROP_THROWEXCEPTIONONMISSINGRESOURCE, "false");
        assertEquals("!.555!", ResourceUtil.getString(Locale.GERMANY, "local", "key.555"));
        System.setProperty(ResourceUtil.PROP_LOGMISSINGRESOURCE, "true");
        assertEquals("!.555!", ResourceUtil.getString(Locale.GERMANY, "local", "key.555"));
        try {
            assertEquals("Push", ResourceUtil.getString(null, "local", "key.1"));
            fail("exception awaited !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            assertEquals("Push", ResourceUtil.getString(Locale.GERMANY, null, "key.1"));
            fail("exception awaited !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            assertEquals("Push", ResourceUtil.getString(Locale.GERMANY, "", "key.1"));
            fail("exception awaited !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            assertEquals("Push", ResourceUtil.getString(Locale.GERMANY, "local", null));
            fail("exception awaited !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            assertEquals("Push", ResourceUtil.getString(Locale.GERMANY, "local", ""));
            fail("exception awaited !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            assertEquals("Push", ResourceUtil.getString(Locale.FRANCE, "local", "key.1"));
            fail("exception awaited !");
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
        try {
            assertEquals("Push", ResourceUtil.getString(Locale.GERMANY, "local4711", "key.1"));
            fail("exception awaited !");
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
        try {
            assertEquals("Push", ResourceUtil.getString(Locale.GERMANY, "local", null, "key.1"));
            fail("exception awaited !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        // test
        assertEquals("Push", ResourceUtil.getString(Locale.GERMANY, "local", "key.1"));
        assertEquals("Push", ResourceUtil.getString(Locale.GERMANY, "local", String.class,
                "key.1"));
        assertEquals("Push", ResourceUtil.getString(Locale.GERMANY, "local", "string",
                "key.1"));
    }

    @Test
    public void shouldGetStringWithClass() throws Exception {
        URL url = this.dir2.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
        ResourceUtil.load(Locale.GERMAN, "test", classLoader);
        // negative
        try {
            ResourceUtil.getString(null, "aaa", String.class, "aaa");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            ResourceUtil.getString(Locale.GERMANY, null, String.class, "aaa");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            ResourceUtil.getString(Locale.GERMANY, "", String.class, "aaa");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            ResourceUtil.getString(Locale.GERMANY, "aaa", null, "aaa");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            ResourceUtil.getString(Locale.GERMANY, "aaa", String.class, null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            ResourceUtil.getString(Locale.GERMANY, "aaa", String.class, "");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void shouldGetStringWithObject() throws Exception {
        URL url = this.dir2.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
        ResourceUtil.load(Locale.GERMAN, "test", classLoader);
        // negative
        try {
            Object o = null;
            ResourceUtil.getString(Locale.GERMANY, "aaa", o, "aaa");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void shouldGetStringWithKey() throws Exception {
        URL url = this.dir2.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
        // negative
        try {
            ResourceUtil.load(null, "test", classLoader);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            ResourceUtil.load(Locale.GERMANY, "", classLoader);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            ResourceUtil.load(Locale.GERMANY, null, classLoader);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            ResourceUtil.load(Locale.GERMANY, "aaa", null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        // positive
        ResourceUtil.load(Locale.GERMANY, "test", classLoader);
    }

    @Test
    public void shouldGetResourceBundlesForBasenameAndLocale() throws Exception {
        URL url = this.dir2.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
        ResourceUtil.load(Locale.GERMANY, "test", classLoader);
        assertNotNull(ResourceUtil.getResourceBundlesForBasenameAndLocale("test",
                Locale.GERMANY));
    }

    @Test
    public void shouldGetListLocalesForBasename() throws Exception {
        URL url = this.dir2.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
        ResourceUtil.load(Locale.GERMANY, "test", classLoader);
        ResourceUtil.load(Locale.ENGLISH, "test", classLoader);
        assertTrue(ResourceUtil.listLocalesForBasename("test").contains(Locale.GERMANY));
        assertTrue(ResourceUtil.listLocalesForBasename("test").contains(Locale.ENGLISH));
    }

    @Test
    public void shouldListBasenames() throws Exception {
        URL url = this.dir2.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
        ResourceUtil.load(Locale.GERMANY, "test", classLoader);
        assertEquals("test", ResourceUtil.listBasenames().get(0));
    }

    @Test
    public void shouldLoadPerXml() throws Exception {
        URL url = this.dir2.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        s.append("<resources name=\"name\" token=\"token\">");
        s.append("    <text name=\"name\">");
        s.append("    text");
        s.append("    </text>");
        s.append("</resources>");
        File f = new File(this.dir2xml, "test_de.xml");
        FileUtils.writeStringToFile(f, s.toString());
        ResourceUtil.load(Locale.GERMANY, "test", classLoader);
        url = this.dir2xml.toURI().toURL();
        classLoader = new URLClassLoader(new URL[] {url});
        ResourceUtil.loadFromXml(Locale.GERMANY, "test", classLoader);
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("text", ResourceUtil.getString(Locale.GERMANY, "test", "name"));
    }

    @Test
    public void shouldLoadPerDevXml() throws Exception {
        URL url = this.dir2.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
        // normal
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        s.append("<resources name=\"name\" token=\"token\">");
        s.append("    <text name=\"name\">");
        s.append("    text");
        s.append("    </text>");
        s.append("</resources>");
        File f = new File(this.dir2xml, "test_de.xml");
        FileUtils.writeStringToFile(f, s.toString());
        // dev
        s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        s.append("<resources name=\"devname\" token=\"devtoken\">");
        s.append("    <text name=\"devname\">");
        s.append("    devtext");
        s.append("    </text>");
        s.append("</resources>");
        f = new File(this.dir2xml, "test_de_dev.xml");
        FileUtils.writeStringToFile(f, s.toString());

        ResourceUtil.load(Locale.GERMANY, "test", classLoader);
        url = this.dir2xml.toURI().toURL();
        classLoader = new URLClassLoader(new URL[] {url});
        ResourceUtil.loadFromXml(Locale.GERMANY, "test", classLoader);
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("text", ResourceUtil.getString(Locale.GERMANY, "test", "name"));
        assertEquals("devtext", ResourceUtil.getString(Locale.GERMANY, "test", "devname"));
    }

}
