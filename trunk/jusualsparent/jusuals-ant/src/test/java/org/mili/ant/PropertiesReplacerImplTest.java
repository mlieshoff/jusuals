/*
 * PropertiesReplacerTest.java
 *
 * 02.11.2009
 *
 * Copyright 2009 Michael Lieshoff
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
package org.mili.ant;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;
import org.junit.*;

import static junit.framework.Assert.*;

/**
 * @author Michael Lieshoff
 *
 */
public class PropertiesReplacerImplTest {
    File dir = new File("./tmp/test_properties_description");
    File propFile = new File(dir.getAbsolutePath() + "/prop.properties");
    File xmlPropFile = new File(dir.getAbsolutePath() + "/prop.xml");
    File abFile = new File(dir.getAbsolutePath() + "/ab.properties");
    File cdFile = new File(dir.getAbsolutePath() + "/cd.properties");
    File xyFile = new File(dir.getAbsolutePath() + "/xy.properties");
    File xmlFile = new File(dir.getAbsolutePath() + "/test-properties-description.xml");

    @Before
    public void setUp() throws IOException {
        dir.delete();
        dir.mkdirs();
        Properties props = new Properties();
        props.setProperty("st", "abbas");
        props.setProperty("xy", "8080");
        props.store(new FileOutputStream(propFile), "");

        Properties ab = new Properties();
        ab.setProperty("a0.b0", "1098");
        ab.setProperty("a1.b1", "1099");
        ab.store(new FileOutputStream(abFile), "");
        Properties cd = new Properties();
        cd.setProperty("", "");
        cd.store(new FileOutputStream(cdFile), "");
        Properties xy = new Properties();
        xy.setProperty("x0.y0", "1098");
        xy.setProperty("x1.y1", "1099");
        xy.store(new FileOutputStream(xyFile), "");
        Properties p = new Properties();
        p.setProperty("st", "abbas");
        p.setProperty("xy", "8080");
        p.storeToXML(new FileOutputStream(xmlPropFile), "");
    }

    @Test
    public void testConstructor() throws Exception {
        try {
            PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
            pr.replace(null, null);
            fail("exception should not be occured !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
            pr.replace(new File(""), null);
            fail("exception should not be occured !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
            pr.replace(new File("abbas"), xmlFile);
            fail("exception should not be occured !");
        } catch (FileNotFoundException e) {
            assertTrue(true);
        }
        try {
            PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
            pr.replace(propFile, new File("abbas"));
            fail("exception should not be occured !");
        } catch (FileNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testReplace_ReplaceFileNotFound() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"st\" type=\"string\">\n");
        s.append("        <default>abbas</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/st.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace>\n");
        s.append("            <what><![CDATA[x.y=8080]]></what>\n");
        s.append("            <to><![CDATA[x.y=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        try {
            PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
            pr.replace(propFile, xmlFile);
            fail("exception should be occured !");
        } catch (FileNotFoundException e) {
            assertTrue(true);
        }
        s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"st\" type=\"string\">\n");
        s.append("        <default>abbas</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/st.properties\" halt-on-error=\"false\">\n");
        s.append("        <replace>\n");
        s.append("            <what><![CDATA[x.y=8080]]></what>\n");
        s.append("            <to><![CDATA[x.y=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        try {
            PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
            pr.replace(propFile, xmlFile);
            assertTrue(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("exception sholud not occured !");
        }
        s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"st\" type=\"string\">\n");
        s.append("        <default>abbas</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/st.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace>\n");
        s.append("            <what><![CDATA[x.y=8080]]></what>\n");
        s.append("            <to><![CDATA[x.y=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        try {
            PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
            pr.replace(xmlPropFile, xmlFile);
            fail("exception sholud be occured !");
        } catch (FileNotFoundException e) {
            assertTrue(true);
        }
        s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"st\" type=\"string\">\n");
        s.append("        <default>abbas</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/st.properties\" halt-on-error=\"false\">\n");
        s.append("        <replace>\n");
        s.append("            <what><![CDATA[x.y=8080]]></what>\n");
        s.append("            <to><![CDATA[x.y=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        try {
            PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
            pr.replace(xmlPropFile, xmlFile);
            assertTrue(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("exception sholud not occured !");
        }
    }

    @Test
    public void testReplace_OneFileButOneReplaceNotFound() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"xy\" type=\"string\">\n");
        s.append("        <default>8080</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/ab.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"xy\">\n");
        s.append("            <what><![CDATA[x.y=8080]]></what>\n");
        s.append("            <to><![CDATA[x.y=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        try {
            PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
            pr.replace(propFile, xmlFile);
            fail("exception sholud be occured !");
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
        s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"xy\" type=\"string\">\n");
        s.append("        <default>8080</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/ab.properties\" halt-on-error=\"false\">\n");
        s.append("        <replace property=\"xy\">\n");
        s.append("            <what><![CDATA[x.y=8080]]></what>\n");
        s.append("            <to><![CDATA[x.y=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        try {
            PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
            pr.replace(propFile, xmlFile);
            assertTrue(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("exception sholud be occured !");
        }
    }

    @Test
    public void testReplace_OneFile() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"xy\" type=\"string\">\n");
        s.append("        <default>8080</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"xy\">\n");
        s.append("            <what><![CDATA[x0.y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        pr.replace(propFile, xmlFile);
        Properties p = new Properties();
        InputStream is = new FileInputStream(xyFile);
        p.load(is);
        assertEquals("8080", p.getProperty("x0.y0"));
    }

    @Test
    public void testReplace_OneFileUsedByTwoReplaces() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"xy\" type=\"string\">\n");
        s.append("        <default>8080</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"xy\">\n");
        s.append("            <what><![CDATA[x0.y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"xy\">\n");
        s.append("            <what><![CDATA[x0.y0=8080]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=9099]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        pr.replace(propFile, xmlFile);
        Properties p = new Properties();
        InputStream is = new FileInputStream(xyFile);
        p.load(is);
        assertEquals("9099", p.getProperty("x0.y0"));
    }

    @Test
    public void testReplace_OneFileOneMultiProperty() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"xy\" type=\"string\">\n");
        s.append("        <default>8080</default>\n");
        s.append("    </property>\n");
        s.append("    <property name=\"xy\" type=\"string\">\n");
        s.append("        <default>8080</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"xy\">\n");
        s.append("            <what><![CDATA[x0.y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        try {
            pr.replace(propFile, xmlFile);
            fail("exception expected!");
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void testReplace_OneFile_Regexp() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"xy\" type=\"string\">\n");
        s.append("        <default>8080</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace regexp=\"true\" property=\"xy\">\n");
        s.append("            <what><![CDATA[x0[.]y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        pr.replace(propFile, xmlFile);
        Properties p = new Properties();
        InputStream is = new FileInputStream(xyFile);
        p.load(is);
        assertEquals("8080", p.getProperty("x0.y0"));
    }

    @Test
    public void testReplace_OneFile_Regexp_HaltOnError() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"xy\" type=\"string\">\n");
        s.append("        <default>8080</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace regexp=\"true\" property=\"xy\">\n");
        s.append("            <what><![CDATA[x0xxxxyyyyxxxx[.]y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        try {
            pr.replace(propFile, xmlFile);
            fail("exception expected!");
        } catch (NoSuchElementException e) {
        }
        // no changes !
        Properties p = new Properties();
        InputStream is = new FileInputStream(xyFile);
        p.load(is);
        assertEquals("1098", p.getProperty("x0.y0"));
        assertEquals("1099", p.getProperty("x1.y1"));
    }

    @Test
    public void testReplace_OneFile_Regexp_DontHaltOnError() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"xy\" type=\"string\">\n");
        s.append("        <default>8080</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"false\">\n");
        s.append("        <replace regexp=\"true\" property=\"xy\">\n");
        s.append("            <what><![CDATA[x0xxxxyyyyxxxx[.]y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        try {
            pr.replace(propFile, xmlFile);
        } catch (Exception e) {
            fail("exception occured!");
        }
        // no changes !
        Properties p = new Properties();
        InputStream is = new FileInputStream(xyFile);
        p.load(is);
        assertEquals("1098", p.getProperty("x0.y0"));
        assertEquals("1099", p.getProperty("x1.y1"));
    }

    @Test
    public void testReplace_TwoFilesWithTwoProps() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"xy\" type=\"integer\">\n");
        s.append("        <default>8080</default>\n");
        s.append("    </property>\n");
        s.append("    <property name=\"st\" type=\"string\">\n");
        s.append("        <default>abbas</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"xy\">\n");
        s.append("            <what><![CDATA[x0.y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("        <replace property=\"st\">\n");
        s.append("            <what><![CDATA[x1.y1=1099]]></what>\n");
        s.append("            <to><![CDATA[x1.y1=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/ab.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"xy\">\n");
        s.append("            <what><![CDATA[a0.b0=1098]]></what>\n");
        s.append("            <to><![CDATA[a0.b0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("        <replace property=\"xy\">\n");
        s.append("            <what><![CDATA[a1.b1=1099]]></what>\n");
        s.append("            <to><![CDATA[a1.b1=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        pr.replace(propFile, xmlFile);
        Properties p = new Properties();
        InputStream is = new FileInputStream(xyFile);
        p.load(is);
        assertEquals("8080", p.getProperty("x0.y0"));
        assertEquals("abbas", p.getProperty("x1.y1"));
        p = new Properties();
        is = new FileInputStream(abFile);
        p.load(is);
        assertEquals("8080", p.getProperty("a0.b0"));
        assertEquals("8080", p.getProperty("a1.b1"));
    }

    @Test
    public void testReplace_OneFilePropertyNotDefinedAndNotFound() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"null\" type=\"string\"/>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"null\">\n");
        s.append("            <what><![CDATA[x0.y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        try {
            pr.replace(propFile, xmlFile);
            fail("exception expected!");
        } catch (NoSuchElementException e) {
        }
    }

    @Test
    public void testReplace_OneFilePropertyNotDefinedAndNotFoundButDefault() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"null\" type=\"string\">\n");
        s.append("        <default>lala</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"null\">\n");
        s.append("            <what><![CDATA[x0.y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        try {
            pr.replace(propFile, xmlFile);
        } catch (IllegalStateException e) {
            fail("exception occured!");
        }
        Properties p = new Properties();
        InputStream is = new FileInputStream(xyFile);
        p.load(is);
        p.list(System.out);
        assertEquals("lala", p.getProperty("x0.y0"));
    }

    @Test
    public void testReplace_OneFilePropertyNotDefinedButReplaced() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"null\" type=\"string\">\n");
        s.append("        <default>lala</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"aaa\">\n");
        s.append("            <what><![CDATA[x0.y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        try {
            pr.replace(propFile, xmlFile);
            fail("exception expected!");
        } catch (IllegalStateException e) {
        }
    }

    @Test(expected=IllegalStateException.class)
    public void shouldFailBecauseReplacePropertyWithConverterClass() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"aaa\" type=\"string\">\n");
        s.append("        <default>lala</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"aaa\" converter-class=\"a.b.ABC\">\n");
        s.append("            <what><![CDATA[x0.y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        pr.replace(propFile, xmlFile);
    }

    @Test
    public void shouldReplacePropertyWithConverterClass() throws Exception {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.append("<properties-description>\n");
        s.append("    <property name=\"aaa\" type=\"string\">\n");
        s.append("        <default>lala</default>\n");
        s.append("    </property>\n");
        s.append("    <replace-file name=\"./tmp/test_properties_description/xy.properties\" halt-on-error=\"true\">\n");
        s.append("        <replace property=\"aaa\" converter-class=\"org.mili.ant.UpperCaseConverter\">\n");
        s.append("            <what><![CDATA[x0.y0=1098]]></what>\n");
        s.append("            <to><![CDATA[x0.y0=$1]]></to>\n");
        s.append("        </replace>\n");
        s.append("    </replace-file>\n");
        s.append("</properties-description>\n");
        FileUtils.writeStringToFile(xmlFile, s.toString());
        PropertiesReplacerImpl pr = new PropertiesReplacerImpl();
        pr.replace(propFile, xmlFile);
        Properties p = new Properties();
        InputStream is = new FileInputStream(xyFile);
        p.load(is);
        p.list(System.out);
        assertEquals("LALA", p.getProperty("x0.y0"));
    }

    @Test
    public void shouldReplaceWithConverterClass() {
        assertEquals("ABC", new UpperCaseConverter().convert("abc"));
    }
}
