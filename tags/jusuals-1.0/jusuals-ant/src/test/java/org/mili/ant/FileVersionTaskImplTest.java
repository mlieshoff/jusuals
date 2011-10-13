/*
 * FileVersionTaskImplTest.java
 *
 * 25.09.2009
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
import org.apache.tools.ant.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 *
 */
public class FileVersionTaskImplTest {
    File dirToCheck = new File("./tmp/fileversiontaskimpl/check");
    String pathToWrite = "./tmp/fileversiontaskimpl/write";
    File fileToWrite = new File(pathToWrite + "/fileversions.properties");

    @Before
    public void setUp() throws Exception {
        dirToCheck.mkdirs();
        new File(pathToWrite).mkdirs();
        File f0 = new File(dirToCheck.getAbsolutePath().concat("/liba-1.jar"));
        FileUtils.writeStringToFile(f0, "lala1");
        File f1 = new File(dirToCheck.getAbsolutePath().concat("/libb-1.0.jar"));
        FileUtils.writeStringToFile(f1, "lala2");
        File f2 = new File(dirToCheck.getAbsolutePath().concat("/libc-1.0.0.jar"));
        FileUtils.writeStringToFile(f2, "lala3");
        File f3 = new File(dirToCheck.getAbsolutePath().concat("/libd-1.0.0.0.jar"));
        FileUtils.writeStringToFile(f3, "lala4");
        File f4 = new File(dirToCheck.getAbsolutePath().concat("/lib-d-1.0.1.0.jar"));
        FileUtils.writeStringToFile(f4, "lala5");
    }

    /**
     * Test execute.
     *
     * @throws Exception the exception
     */
    @Test
    public void testExecute() throws Exception {
        new FileVersionTaskImpl(fileToWrite.getAbsolutePath(),
                dirToCheck.getAbsolutePath(), "jar").execute();
        assertTrue("properties file not created !", fileToWrite.exists());
        Properties p = new Properties();
        p.load(new FileInputStream(fileToWrite));
        assertNull(p.getProperty("liba"));
        assertEquals("1.0", p.getProperty("libb"));
        assertEquals("1.0.0", p.getProperty("libc"));
        assertEquals("1.0.0.0", p.getProperty("libd"));
        assertEquals("1.0.1.0", p.getProperty("lib-d"));
        assertEquals("jar", p.getProperty("libb.ext"));
        assertEquals("jar", p.getProperty("libc.ext"));
        assertEquals("jar", p.getProperty("libd.ext"));
        assertEquals("jar", p.getProperty("lib-d.ext"));
        // null filetowrite
        try {
            new FileVersionTaskImpl("", "bbb", "ccc").execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }
        try {
            new FileVersionTaskImpl(null, "bbb", "ccc").execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }
        // null dirtocheck
        try {
            new FileVersionTaskImpl("aaa", "", "ccc").execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }
        try {
            new FileVersionTaskImpl("aaa", null, "ccc").execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }
        // listofextension
        try {
            new FileVersionTaskImpl("aaa", "bbb", "a").execute();
        } catch (BuildException e) {
            fail("exception occured!");
        }
        try {
            new FileVersionTaskImpl("aaa", "bbb", "a,b").execute();
        } catch (BuildException e) {
            fail("exception occured!");
        }
        // null listofextension
        try {
            new FileVersionTaskImpl("aaa", "bbb", "").execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }
        try {
            new FileVersionTaskImpl("aaa", "bbb", null).execute();
            fail("exception expected!");
        } catch (BuildException e) {
        }

    }
}
