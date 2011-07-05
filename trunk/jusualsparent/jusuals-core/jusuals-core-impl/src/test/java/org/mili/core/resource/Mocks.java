/*
 * Mocks.java
 *
 * 27.05.2011
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

import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.bind.*;

import org.apache.commons.io.*;
import org.mili.core.resource.generated.*;
import org.mili.core.xml.*;
import org.mili.test.*;

/**
 * @author Michael Lieshoff
 */
public class Mocks {
    private ObjectFactory factory = new ObjectFactory();
    private Class<?> anchor = null;
    private String id = null;
    private File root = null;

    /**
     * Instantiates a new mocks.
     *
     * @param anchor the anchor
     */
    public Mocks(Class<?> anchor) {
        this.anchor = anchor;
        this.id = this.anchor.getName();
    }

    /**
     * Prepare.
     */
    public void prepare() {
        this.root = TestUtils.getTmpFolder(this.anchor);
        if (this.root.exists()) {
            try {
                FileUtils.forceDelete(this.root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.root.mkdirs();
    }

    /**
     * Creates the properties for class de.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void createPropertiesForClassDe() throws IOException {
        Properties p = new Properties();
        p.setProperty(this.id + ".p0", "hallo");
        p.setProperty(this.id + ".p1", "welt");
        this.createProperties(p, new File(this.root, "test_de.properties"));
    }

    /**
     * Creates the xml for class de.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws PropertyException the property exception
     * @throws JAXBException the jAXB exception
     */
    public void createXmlForClassDe() throws IOException, PropertyException, JAXBException {
        Resources r = this.factory.createResources();
        r.setName("test");
        r.setToken("t");
        Text t = this.factory.createText();
        t.setName(this.id + ".p0");
        t.setContent("hallo");
        r.getText().add(t);
        t = this.factory.createText();
        t.setName(this.id + ".p1");
        t.setContent("welt");
        r.getText().add(t);
        XmlAccess.write(r, new File(this.root, "test_de.xml").getAbsolutePath(),
                ResourceUtil.NAMESPACE);
    }

    /**
     * Creates the xml with reference for class de.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws PropertyException the property exception
     * @throws JAXBException the jAXB exception
     */
    public void createXmlWithReferenceForClassDe() throws IOException, PropertyException,
            JAXBException {
        Resources r = this.factory.createResources();
        r.setName("test");
        r.setToken("t");
        Text t = this.factory.createText();
        t.setName(this.id + ".p0");
        t.setContent("hallo");
        r.getText().add(t);
        t = this.factory.createText();
        t.setName(this.id + ".p1");
        t.setContent("welt");
        r.getText().add(t);
        Reference ref = this.factory.createReference();
        ref.setNamespace("test");
        ref.setFilename("test_ref");
        r.getReference().add(ref);
        XmlAccess.write(r, new File(this.root, "test_de.xml").getAbsolutePath(),
                ResourceUtil.NAMESPACE);
    }

    /**
     * Creates the xml reference for class de.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws PropertyException the property exception
     * @throws JAXBException the jAXB exception
     */
    public void createXmlReferenceForClassDe() throws IOException, PropertyException,
            JAXBException {
        Resources r = this.factory.createResources();
        r.setName("test_ref");
        r.setToken("t");
        Text t = this.factory.createText();
        t.setName(this.id + ".p2");
        t.setContent("selber");
        r.getText().add(t);
        t = this.factory.createText();
        t.setName(this.id + ".p3");
        t.setContent("hallo");
        r.getText().add(t);
        XmlAccess.write(r, new File(this.root, "test_ref_de.xml").getAbsolutePath(),
                ResourceUtil.NAMESPACE);
    }

    /**
     * Creates the dev properties for class.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void createDevPropertiesForClass() throws IOException {
        Properties p = new Properties();
        p.setProperty(this.id + ".p1", "spongebob");
        p.setProperty(this.id + ".p2", "selber");
        p.setProperty(this.id + ".p3", "hallo");
        this.createProperties(p, new File(this.root, "test_dev.properties"));
    }

    /**
     * Creates the dev xml for class.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws PropertyException the property exception
     * @throws JAXBException the jAXB exception
     */
    public void createDevXmlForClass() throws IOException, PropertyException, JAXBException {
        Resources r = this.factory.createResources();
        r.setName("test");
        r.setToken("t");
        Text t = this.factory.createText();
        t.setName(this.id + ".p2");
        t.setContent("selber");
        r.getText().add(t);
        t = this.factory.createText();
        t.setName(this.id + ".p3");
        t.setContent("hallo");
        r.getText().add(t);
        t = this.factory.createText();
        t.setName(this.id + ".p1");
        t.setContent("sponge");
        r.getText().add(t);
        XmlAccess.write(r, new File(this.root, "test_dev.xml").getAbsolutePath(),
                ResourceUtil.NAMESPACE);
    }

    /**
     * Creates the other properties for class de.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void createOtherPropertiesForClassDe() throws IOException {
        Properties p = new Properties();
        p.setProperty(this.id + ".p0", "mache");
        p.setProperty(this.id + ".p1", "besser");
        this.createProperties(p, new File(this.root, "other_de.properties"));
    }

    /**
     * Creates the other xml for class de.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws PropertyException the property exception
     * @throws JAXBException the jAXB exception
     */
    public void createOtherXmlForClassDe() throws IOException, PropertyException, JAXBException {
        Resources r = this.factory.createResources();
        r.setName("other");
        r.setToken("o");
        Text t = this.factory.createText();
        t.setName(this.id + ".p0");
        t.setContent("mache");
        r.getText().add(t);
        t = this.factory.createText();
        t.setName(this.id + ".p1");
        t.setContent("besser");
        r.getText().add(t);
        XmlAccess.write(r, new File(this.root, "other_de.xml").getAbsolutePath(),
                ResourceUtil.NAMESPACE);
    }

    /**
     * Creates the properties for class en.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void createPropertiesForClassEn() throws IOException {
        Properties p = new Properties();
        p.setProperty(this.id + ".p0", "hello");
        p.setProperty(this.id + ".p1", "world");
        this.createProperties(p, new File(this.root, "test_en.properties"));
    }

    /**
     * Creates the other properties for class en.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void createOtherPropertiesForClassEn() throws IOException {
        Properties p = new Properties();
        p.setProperty(this.id + ".p0", "make");
        p.setProperty(this.id + ".p1", "better");
        this.createProperties(p, new File(this.root, "other_en.properties"));
    }

    /**
     * Creates the url class loader.
     *
     * @return the uRL class loader
     * @throws MalformedURLException the malformed url exception
     */
    public URLClassLoader createUrlClassLoader() throws MalformedURLException {
        return new URLClassLoader(new URL[] {this.root.toURI().toURL()});
    }

    /**
     * Creates the properties.
     *
     * @param p the p
     * @param f the f
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void createProperties(Properties p, File f) throws IOException {
        OutputStream os = new FileOutputStream(f);
        p.store(os, "");
        os.close();
    }

}
