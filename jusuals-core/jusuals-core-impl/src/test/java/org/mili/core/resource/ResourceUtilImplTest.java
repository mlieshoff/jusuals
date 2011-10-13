/*
 * ResourceUtilImplTest.java
 *
 * 25.05.2011
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

package org.mili.core.resource;

import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class ResourceUtilImplTest {
    public static String salter = "";
    private ResourceUtilImpl impl = null;
    private Mocks mocks = null;

    @Before
    public void setUp() throws Exception {
        this.mocks = new Mocks(this.getClass());
        this.mocks.prepare();
        this.impl = new ResourceUtilImpl();
    }

    @After
    public void after() {
        this.impl.clear();
        System.clearProperty(ResourceUtilImpl.PROP_MISSINGRESOURCEHANDLER);
        System.setProperty(ResourceUtilImpl.PROP_THROWEXCEPTIONONMISSINGRESOURCE, "false");
        System.setProperty(ResourceUtilImpl.PROP_LOGMISSINGRESOURCE, "false");
        salter = "";
    }

    @Test
    public void shouldConstruct() {
        new ResourceUtilImpl();
    }

    @Test
    public void shouldProcessOneLocaleAndOneResource() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        assertEquals("hallo", this.impl.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", this.impl.getString(Locale.GERMANY, "test", this, "p1"));
    }

    @Test
    public void shouldProcessOneLocaleAndOneResourceXml() throws Exception {
        this.mocks.createXmlForClassDe();
        this.impl.loadFromXml(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        assertEquals("hallo", this.impl.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", this.impl.getString(Locale.GERMANY, "test", this, "p1"));
    }

    @Test
    @Ignore
    public void shouldProcessOneLocaleAndOneResourceXmlWithReference() throws Exception {
        this.mocks.createXmlForClassDe();
        this.mocks.createXmlWithReferenceForClassDe();
        this.mocks.createXmlReferenceForClassDe();
        this.impl.loadFromXml(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        assertEquals("hallo", this.impl.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", this.impl.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("selber", this.impl.getString(Locale.GERMANY, "test", this, "p2"));
        assertEquals("hallo", this.impl.getString(Locale.GERMANY, "test", this, "p3"));
    }

    @Test
    public void shouldProcessOneLocaleAndTwoResources() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.mocks.createOtherPropertiesForClassDe();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        this.impl.load(Locale.GERMANY, "other", this.mocks.createUrlClassLoader());
        assertEquals("hallo", this.impl.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", this.impl.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("mache", this.impl.getString(Locale.GERMANY, "other", this, "p0"));
        assertEquals("besser", this.impl.getString(Locale.GERMANY, "other", this, "p1"));
    }

    @Test
    public void shouldProcessOneLocaleAndTwoXml() throws Exception {
        this.mocks.createXmlForClassDe();
        this.mocks.createOtherXmlForClassDe();
        this.impl.loadFromXml(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        this.impl.loadFromXml(Locale.GERMANY, "other", this.mocks.createUrlClassLoader());
        assertEquals("hallo", this.impl.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", this.impl.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("mache", this.impl.getString(Locale.GERMANY, "other", this, "p0"));
        assertEquals("besser", this.impl.getString(Locale.GERMANY, "other", this, "p1"));
    }

    @Test
    public void shouldProcessOneLocaleAndDevResources() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.mocks.createDevPropertiesForClass();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        assertEquals("hallo", this.impl.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", this.impl.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("selber", this.impl.getString(Locale.GERMANY, "test", this, "p2"));
        assertEquals("hallo", this.impl.getString(Locale.GERMANY, "test", this, "p3"));
    }

    @Test
    public void shouldProcessOneLocaleAndDevXml() throws Exception {
        this.mocks.createXmlForClassDe();
        this.mocks.createDevXmlForClass();
        this.impl.loadFromXml(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        assertEquals("hallo", this.impl.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", this.impl.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("selber", this.impl.getString(Locale.GERMANY, "test", this, "p2"));
        assertEquals("hallo", this.impl.getString(Locale.GERMANY, "test", this, "p3"));
    }

    @Test
    public void shouldProcessTwoLocales() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.mocks.createOtherPropertiesForClassDe();
        this.mocks.createPropertiesForClassEn();
        this.mocks.createOtherPropertiesForClassEn();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        this.impl.load(Locale.ENGLISH, "test", this.mocks.createUrlClassLoader());
        assertEquals("hallo", this.impl.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", this.impl.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("hello", this.impl.getString(Locale.ENGLISH, "test", this, "p0"));
        assertEquals("world", this.impl.getString(Locale.ENGLISH, "test", this, "p1"));
    }

    @Test(expected=MissingResourceException.class)
    public void shouldTestTheExceptionThrowing() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        System.setProperty(this.impl.PROP_THROWEXCEPTIONONMISSINGRESOURCE, "true");
        this.impl.getString(Locale.GERMANY, "test", "key.555");
    }

    @Test
    public void shouldTestTheMissingResourceLogging() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        System.setProperty(this.impl.PROP_LOGMISSINGRESOURCE, "true");
        assertEquals("!.555!", this.impl.getString(Locale.GERMANY, "test", "key.555"));
        // TODO check the exported file
    }

    @Test(expected=IllegalStateException.class)
    public void shouldFoundNoLocale() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        this.impl.getString(Locale.CHINESE, "test", "key.555");
    }

    @Test(expected=IllegalStateException.class)
    public void shouldFoundNoBaseName() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        this.impl.getString(Locale.GERMANY, "alfons", "key.555");
    }

    @Test
    public void shouldGetResourceBundlesForBasenameAndLocale() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        assertNotNull(this.impl.getResourceBundlesForBasenameAndLocale("test",
                Locale.GERMANY));
    }

    @Test
    public void shouldGetListLocalesForBasename() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.mocks.createPropertiesForClassEn();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        this.impl.load(Locale.ENGLISH, "test", this.mocks.createUrlClassLoader());
        assertTrue(this.impl.listLocalesForBasename("test").contains(Locale.GERMANY));
        assertTrue(this.impl.listLocalesForBasename("test").contains(Locale.ENGLISH));
    }

    @Test
    public void shouldListBasenames() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        assertEquals("test", this.impl.listBasenames().get(0));
    }

    @Test
    public void shouldHandle() throws Exception {
        System.setProperty(this.impl.PROP_MISSINGRESOURCEHANDLER,
                "org.mili.core.resource.BasicHandler");
        this.mocks.createPropertiesForClassDe();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        this.impl.getString(Locale.GERMANY, "test", "abbas");
        assertEquals("abbas", salter);
    }

    @Test
    public void shouldContains() throws Exception {
        this.mocks.createPropertiesForClassDe();
        this.impl.load(Locale.GERMANY, "test", this.mocks.createUrlClassLoader());
        assertTrue(this.impl.contains(Locale.GERMANY, "test", this.getClass().getName() + ".p0"));
        assertFalse(this.impl.contains(Locale.GERMANY, "test", "abc"));
    }

}

class BasicHandler implements MissingResourceHandler {
    @Override
    public void handle(Locale locale, String baseName, String key) {
        ResourceUtilImplTest.salter = key;
    }
}

