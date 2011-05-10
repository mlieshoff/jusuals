/*
 * XmlAccessTest.java
 *
 * 09.05.2011
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

package org.mili.core.xml;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;
import org.junit.*;
import org.mili.core.resource.*;
import org.mili.core.resource.generated.*;
import org.mili.test.*;

import static org.junit.Assert.*;


/**
 * @author MLI
 *
 */
public class XmlAccessTest {
    private static File dir = TestUtils.getTmpFolder(XmlAccessTest.class);
    private static File file1 = new File(dir, "test1-resources.xml");
    private static File file2 = new File(dir, "test2-resources.xml");
    private static File controlFile = new File(dir, "control-resources.xml");
    private Resources resources = null;
    private ObjectFactory factory = null;

    @BeforeClass
    public static void setUpClass() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        builder.append("<resources name=\"test\" token=\"DE\">\n");
        builder.append("    <text name=\"test\">abc</text>\n");
        builder.append("</resources>");
        FileUtils.writeStringToFile(file1, builder.toString());
        builder.setLength(0);
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        builder.append("<resources name=\"test\" token=\"DE\">\n");
        builder.append("    <text name=\"test\"><![CDATA[abc]]></text>\n");
        builder.append("</resources>");
        FileUtils.writeStringToFile(file2, builder.toString());
    }

    @Before
    public void setUp() throws Exception {
        this.factory = new ObjectFactory();
        this.resources = this.factory.createResources();
        this.resources.setName("test");
        this.resources.setToken("DE");
        Text text = this.factory.createText();
        text.setName("test");
        text.setContent("abc");
        this.resources.getText().add(text);
    }

    @Test
    public void shouldConstruct() {
        new XmlAccess();
    }

    @Test(expected=IllegalArgumentException.class)
    public void failReadBecauseNullFile() throws Exception {
        XmlAccess.read((File) null, ResourceUtil.NAMESPACE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failReadBecauseFileAndNullNamespace() throws Exception {
        XmlAccess.read(file1, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failReadBecauseFileAndEmptyNamespace() throws Exception {
        XmlAccess.read(file1, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failReadBecauseNullString() throws Exception {
        XmlAccess.read((String) null, ResourceUtil.NAMESPACE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failReadBecauseEmptyString() throws Exception {
        XmlAccess.read("", ResourceUtil.NAMESPACE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failReadBecauseStringAndEmptyNamespace() throws Exception {
        XmlAccess.read("abc", "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failReadBecauseStringAndNullNamespace() throws Exception {
        XmlAccess.read("abc", null);
    }

    @Test
    public void shouldReadWithFile() throws Exception {
        assertNotNull(XmlAccess.read(file1, ResourceUtil.NAMESPACE));
    }

    @Test
    public void shouldReadWithFilename() throws Exception {
        assertNotNull(XmlAccess.read(file1.getAbsolutePath(), ResourceUtil.NAMESPACE));
    }

    @Test(expected=IllegalArgumentException.class)
    public void failWriteBecauseObjectIsNull() throws Exception {
        XmlAccess.write(null, "filename", "namespace");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failWriteBecauseFilenameIsNull() throws Exception {
        XmlAccess.write("object", (String) null, "namespace");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failWriteBecauseFilenameIsEmpty() throws Exception {
        XmlAccess.write("object", "", "namespace");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failWriteBecauseNamespaceIsNull() throws Exception {
        XmlAccess.write("object", "filename", null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failWriteBecauseNamespaceIsEmpty() throws Exception {
        XmlAccess.write("object", "filename", "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failWriteFileBecauseObjectIsNull() throws Exception {
        XmlAccess.write(null, new File(""), "namespace");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failWriteFileBecauseFileIsNull() throws Exception {
        XmlAccess.write("object", (File) null, "namespace");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failWriteFileBecauseNamespaceIsNull() throws Exception {
        XmlAccess.write("object", new File(""), null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failWriteFileBecauseNamespaceIsEmpty() throws Exception {
        XmlAccess.write("object", new File(""), "");
    }

    @Test
    public void shouldWriteWithoutCDATA() throws Exception {
        XmlAccess.write(this.resources, controlFile.getAbsolutePath(), ResourceUtil.NAMESPACE);
        assertResourceEquals(file1, controlFile);
    }

    @Test
    public void shouldWriteWithCDATA() throws Exception {
        XmlAccess.write(this.resources, controlFile.getAbsolutePath(), ResourceUtil.NAMESPACE,
                new String[]{"^text"});
        assertResourceEquals(file2, controlFile);
    }

    private void assertResourceEquals(File file, File controlFile) throws Exception {
        Resources resources1 = (Resources) XmlAccess.read(file, ResourceUtil.NAMESPACE);
        Resources resources2 = (Resources) XmlAccess.read(controlFile, ResourceUtil.NAMESPACE);
        assertEquals(resources1.getName(), resources2.getName());
        assertEquals(resources1.getToken(), resources2.getToken());
        List<Text> texts1 = resources1.getText();
        List<Text> texts2 = resources2.getText();
        for (int i = 0, n = texts1.size(); i < n; i++) {
            Text text1 = texts1.get(i);
            Text text2 = texts2.get(i);
            assertEquals(text1.getContent(), text2.getContent());
            assertEquals(text1.getName(), text2.getName());
        }
    }

}
