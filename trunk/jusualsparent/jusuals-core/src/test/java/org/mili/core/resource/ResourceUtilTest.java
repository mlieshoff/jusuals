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

import javax.xml.bind.*;

import org.apache.commons.io.*;
import org.junit.*;
import org.mili.core.resource.generated.*;
import org.mili.core.xml.*;
import org.mili.test.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class ResourceUtilTest {
    private File root = null;
    private final String ID = ResourceUtilTest.class.getName();
    private ObjectFactory factory = new ObjectFactory();
    public static String salter = "";

    @Before
    public void setUp() throws Exception {
        this.root = TestUtils.getTmpFolder(ResourceUtilTest.class);
        if (this.root.exists()) {
            try {
                FileUtils.forceDelete(this.root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.root.mkdirs();
    }

    @After
    public void after() {
        ResourceUtil.clear();
        System.clearProperty(ResourceUtil.PROP_MISSINGRESOURCEHANDLER);
        System.setProperty(ResourceUtil.PROP_THROWEXCEPTIONONMISSINGRESOURCE, "false");
        System.setProperty(ResourceUtil.PROP_LOGMISSINGRESOURCE, "false");
        salter = "";
    }

    @Test
    public void shouldProcessOneLocaleAndOneResource() throws Exception {
        this.createPropertiesForClassDe();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMANY, "test", this, "p1"));
    }

    @Test
    public void shouldProcessOneLocaleAndOneResourceXml() throws Exception {
        this.createXmlForClassDe();
        ResourceUtil.loadFromXml(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMANY, "test", this, "p1"));
    }

    @Test
    @Ignore
    public void shouldProcessOneLocaleAndOneResourceXmlWithReference() throws Exception {
        this.createXmlForClassDe();
        this.createXmlWithReferenceForClassDe();
        this.createXmlReferenceForClassDe();
        ResourceUtil.loadFromXml(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("selber", ResourceUtil.getString(Locale.GERMANY, "test", this, "p2"));
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p3"));
    }

    @Test
    public void shouldProcessOneLocaleAndTwoResources() throws Exception {
        this.createPropertiesForClassDe();
        this.createOtherPropertiesForClassDe();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        ResourceUtil.load(Locale.GERMANY, "other", this.createUrlClassLoader(this.root));
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("mache", ResourceUtil.getString(Locale.GERMANY, "other", this, "p0"));
        assertEquals("besser", ResourceUtil.getString(Locale.GERMANY, "other", this, "p1"));
    }

    @Test
    public void shouldProcessOneLocaleAndTwoXml() throws Exception {
        this.createXmlForClassDe();
        this.createOtherXmlForClassDe();
        ResourceUtil.loadFromXml(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        ResourceUtil.loadFromXml(Locale.GERMANY, "other", this.createUrlClassLoader(this.root));
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("mache", ResourceUtil.getString(Locale.GERMANY, "other", this, "p0"));
        assertEquals("besser", ResourceUtil.getString(Locale.GERMANY, "other", this, "p1"));
    }

    @Test
    public void shouldProcessOneLocaleAndDevResources() throws Exception {
        this.createPropertiesForClassDe();
        this.createDevPropertiesForClass();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("selber", ResourceUtil.getString(Locale.GERMANY, "test", this, "p2"));
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p3"));
    }

    @Test
    public void shouldProcessOneLocaleAndDevXml() throws Exception {
        this.createXmlForClassDe();
        this.createDevXmlForClass();
        ResourceUtil.loadFromXml(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("selber", ResourceUtil.getString(Locale.GERMANY, "test", this, "p2"));
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p3"));
    }

    @Test
    public void shouldProcessTwoLocales() throws Exception {
        this.createPropertiesForClassDe();
        this.createOtherPropertiesForClassDe();
        this.createPropertiesForClassEn();
        this.createOtherPropertiesForClassEn();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        ResourceUtil.load(Locale.ENGLISH, "test", this.createUrlClassLoader(this.root));
        assertEquals("hallo", ResourceUtil.getString(Locale.GERMANY, "test", this, "p0"));
        assertEquals("welt", ResourceUtil.getString(Locale.GERMANY, "test", this, "p1"));
        assertEquals("hello", ResourceUtil.getString(Locale.ENGLISH, "test", this, "p0"));
        assertEquals("world", ResourceUtil.getString(Locale.ENGLISH, "test", this, "p1"));
    }

    @Test(expected=MissingResourceException.class)
    public void shouldTestTheExceptionThrowing() throws Exception {
        this.createPropertiesForClassDe();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        System.setProperty(ResourceUtil.PROP_THROWEXCEPTIONONMISSINGRESOURCE, "true");
        ResourceUtil.getString(Locale.GERMANY, "test", "key.555");
    }

    @Test
    public void shouldTestTheMissingResourceLogging() throws Exception {
        this.createPropertiesForClassDe();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        System.setProperty(ResourceUtil.PROP_LOGMISSINGRESOURCE, "true");
        assertEquals("!.555!", ResourceUtil.getString(Locale.GERMANY, "test", "key.555"));
        // TODO check the exported file
    }

    @Test(expected=IllegalStateException.class)
    public void shouldFoundNoLocale() throws Exception {
        this.createPropertiesForClassDe();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        ResourceUtil.getString(Locale.CHINESE, "test", "key.555");
    }

    @Test(expected=IllegalStateException.class)
    public void shouldFoundNoBaseName() throws Exception {
        this.createPropertiesForClassDe();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        ResourceUtil.getString(Locale.GERMANY, "alfons", "key.555");
    }

    @Test
    public void shouldGetResourceBundlesForBasenameAndLocale() throws Exception {
        this.createPropertiesForClassDe();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        assertNotNull(ResourceUtil.getResourceBundlesForBasenameAndLocale("test",
                Locale.GERMANY));
    }

    @Test
    public void shouldGetListLocalesForBasename() throws Exception {
        this.createPropertiesForClassDe();
        this.createPropertiesForClassEn();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        ResourceUtil.load(Locale.ENGLISH, "test", this.createUrlClassLoader(this.root));
        assertTrue(ResourceUtil.listLocalesForBasename("test").contains(Locale.GERMANY));
        assertTrue(ResourceUtil.listLocalesForBasename("test").contains(Locale.ENGLISH));
    }

    @Test
    public void shouldListBasenames() throws Exception {
        this.createPropertiesForClassDe();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        assertEquals("test", ResourceUtil.listBasenames().get(0));
    }

    @Test
    public void shouldHandle() throws Exception {
        System.setProperty(ResourceUtil.PROP_MISSINGRESOURCEHANDLER,
                "org.mili.core.resource.BasicHandler");
        this.createPropertiesForClassDe();
        ResourceUtil.load(Locale.GERMANY, "test", this.createUrlClassLoader(this.root));
        ResourceUtil.getString(Locale.GERMANY, "test", "abbas");
        assertEquals("abbas", salter);
    }

    private void createPropertiesForClassDe() throws IOException {
        Properties p = new Properties();
        p.setProperty(ID + ".p0", "hallo");
        p.setProperty(ID + ".p1", "welt");
        this.createProperties(p, new File(this.root, "test_de.properties"));
    }

    private void createXmlForClassDe() throws IOException, PropertyException, JAXBException {
        Resources r = this.factory.createResources();
        r.setName("test");
        r.setToken("t");
        Text t = this.factory.createText();
        t.setName(ID + ".p0");
        t.setContent("hallo");
        r.getText().add(t);
        t = this.factory.createText();
        t.setName(ID + ".p1");
        t.setContent("welt");
        r.getText().add(t);
        XmlAccess.write(r, new File(this.root, "test_de.xml").getAbsolutePath(),
                ResourceUtil.NAMESPACE);
    }

    private void createXmlWithReferenceForClassDe() throws IOException, PropertyException,
            JAXBException {
        Resources r = this.factory.createResources();
        r.setName("test");
        r.setToken("t");
        Text t = this.factory.createText();
        t.setName(ID + ".p0");
        t.setContent("hallo");
        r.getText().add(t);
        t = this.factory.createText();
        t.setName(ID + ".p1");
        t.setContent("welt");
        r.getText().add(t);
        Reference ref = this.factory.createReference();
        ref.setNamespace("test");
        ref.setFilename("test_ref");
        r.getReference().add(ref);
        XmlAccess.write(r, new File(this.root, "test_de.xml").getAbsolutePath(),
                ResourceUtil.NAMESPACE);
    }

    private void createXmlReferenceForClassDe() throws IOException, PropertyException,
            JAXBException {
        Resources r = this.factory.createResources();
        r.setName("test_ref");
        r.setToken("t");
        Text t = this.factory.createText();
        t.setName(ID + ".p2");
        t.setContent("selber");
        r.getText().add(t);
        t = this.factory.createText();
        t.setName(ID + ".p3");
        t.setContent("hallo");
        r.getText().add(t);
        XmlAccess.write(r, new File(this.root, "test_ref_de.xml").getAbsolutePath(),
                ResourceUtil.NAMESPACE);
    }

    private void createDevPropertiesForClass() throws IOException {
        Properties p = new Properties();
        p.setProperty(ID + ".p2", "selber");
        p.setProperty(ID + ".p3", "hallo");
        this.createProperties(p, new File(this.root, "test_dev.properties"));
    }

    private void createDevXmlForClass() throws IOException, PropertyException, JAXBException {
        Resources r = this.factory.createResources();
        r.setName("test");
        r.setToken("t");
        Text t = this.factory.createText();
        t.setName(ID + ".p2");
        t.setContent("selber");
        r.getText().add(t);
        t = this.factory.createText();
        t.setName(ID + ".p3");
        t.setContent("hallo");
        r.getText().add(t);
        XmlAccess.write(r, new File(this.root, "test_dev.xml").getAbsolutePath(),
                ResourceUtil.NAMESPACE);
    }

    private void createOtherPropertiesForClassDe() throws IOException {
        Properties p = new Properties();
        p.setProperty(ID + ".p0", "mache");
        p.setProperty(ID + ".p1", "besser");
        this.createProperties(p, new File(this.root, "other_de.properties"));
    }

    private void createOtherXmlForClassDe() throws IOException, PropertyException, JAXBException {
        Resources r = this.factory.createResources();
        r.setName("other");
        r.setToken("o");
        Text t = this.factory.createText();
        t.setName(ID + ".p0");
        t.setContent("mache");
        r.getText().add(t);
        t = this.factory.createText();
        t.setName(ID + ".p1");
        t.setContent("besser");
        r.getText().add(t);
        XmlAccess.write(r, new File(this.root, "other_de.xml").getAbsolutePath(),
                ResourceUtil.NAMESPACE);
    }

    private void createPropertiesForClassEn() throws IOException {
        Properties p = new Properties();
        p.setProperty(ID + ".p0", "hello");
        p.setProperty(ID + ".p1", "world");
        this.createProperties(p, new File(this.root, "test_en.properties"));
    }

    private void createOtherPropertiesForClassEn() throws IOException {
        Properties p = new Properties();
        p.setProperty(ID + ".p0", "make");
        p.setProperty(ID + ".p1", "better");
        this.createProperties(p, new File(this.root, "other_en.properties"));
    }

    private void createProperties(Properties p, File f) throws IOException {
        OutputStream os = new FileOutputStream(f);
        p.store(os, "");
        os.close();
    }

    private URLClassLoader createUrlClassLoader(File file) throws MalformedURLException {
        return new URLClassLoader(new URL[] {file.toURI().toURL()});
    }

}

class BasicHandler implements MissingResourceHandler {
    @Override
    public void handle(Locale locale, String baseName, String key) {
        ResourceUtilTest.salter = key;
    }
}

