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

import org.apache.commons.io.*;
import org.junit.*;
import org.mili.core.resource.*;
import org.mili.test.*;

import static org.junit.Assert.*;


/**
 * @author MLI
 *
 */
public class XmlAccessTest {
    private static File dir = TestUtils.getTmpFolder(XmlAccessTest.class);
    private static File file1 = new File(dir, "test1-resources.xml");

    @BeforeClass
    public static void setUp() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        builder.append("<resources name=\"test\" token=\"DE\">");
        builder.append("    <text name=\"test\">abc</text>");
        builder.append("</resources>");
        FileUtils.writeStringToFile(file1, builder.toString());
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
    public void shouldRead() throws Exception {
        assertNotNull(XmlAccess.read(file1, ResourceUtil.NAMESPACE));
    }

}
