/*
 * PropertiesReplaceTaskTest.java
 *
 * 03.03.2010
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
package org.mili.ant;

import java.io.*;

import org.apache.commons.io.*;
import org.apache.tools.ant.*;
import org.easymock.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 *
 */
public class PropertiesReplaceTaskTest {

    private PropertiesReplace pr = null;
    String path = "./tmp/propertiesreplacetasktest";
    File propertiesFile = new File(this.path + "/props.properties");

    @Before
    public void setUp() throws Exception {
        new File(path).mkdirs();
        String s = "a=1";
        FileUtils.writeStringToFile(this.propertiesFile, s);
        this.pr = EasyMock.createMock(PropertiesReplace.class);
    }

    @Test
    public void testExecute() {
        this.pr.execute();
        EasyMock.expectLastCall().times(1);
        EasyMock.replay(this.pr);
        PropertiesReplaceTask o = new PropertiesReplaceTask("aaa", "bbb", true);
        o.setImpl(this.pr);
        o.execute();
        EasyMock.reset(this.pr);
        EasyMock.replay(this.pr);
        o = new PropertiesReplaceTask("aaa", "bbb", true);
        assertNull(o.getImpl());
        try {
            o.execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }
        // null properties file
        try {
            new PropertiesReplaceTask(null, "bbb", true).execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }
        try {
            new PropertiesReplaceTask("", "bbb", true).execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }
        // null properties desc
        try {
            new PropertiesReplaceTask("aaa", null, true).execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }
        try {
            new PropertiesReplaceTask("aaa", "", true).execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }
        // file to replace dont exists
        try {
            new PropertiesReplaceTask(propertiesFile.getAbsolutePath(), "abbas", true).execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }
        try {
            new PropertiesReplaceTask(propertiesFile.getAbsolutePath(), "abbas", false).execute();
        } catch (BuildException e) {
            e.printStackTrace();
            fail("exception occured!");
        }
        EasyMock.verify(this.pr);
    }

    @Test
    public void testPropertiesReplaceTask() {
        PropertiesReplaceTask o = new PropertiesReplaceTask();
    }

    @Test
    public void testPropertiesReplaceTaskStringStringBoolean() {
        PropertiesReplaceTask o = new PropertiesReplaceTask("aaa", "bbb", true);
        assertEquals("aaa", o.getPropertiesFile());
        assertEquals("bbb", o.getFileToReplace());
        assertTrue(o.isErrorIfFileToReplaceDontExists());
    }

    @Test
    public void testGetPropertiesFile() {
        PropertiesReplaceTask o = new PropertiesReplaceTask();
        o.setPropertiesFile("aaa");
        assertEquals("aaa", o.getPropertiesFile());
    }

    @Test
    public void testGetFileToReplace() {
        PropertiesReplaceTask o = new PropertiesReplaceTask();
        o.setFileToReplace("aaa");
        assertEquals("aaa", o.getFileToReplace());
    }

    @Test
    public void testIsErrorIfFileToReplaceDontExists() {
        PropertiesReplaceTask o = new PropertiesReplaceTask();
        o.setErrorIfFileToReplaceDontExists(true);
        assertTrue(o.isErrorIfFileToReplaceDontExists());
    }

}
