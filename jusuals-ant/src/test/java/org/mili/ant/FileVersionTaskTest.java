/*
 * FileVersionTaskTest.java
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

import org.easymock.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 *
 */
public class FileVersionTaskTest {
    private FileVersion pr = null;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        this.pr = EasyMock.createMock(FileVersion.class);
    }

    /**
     * Test execute.
     */
    @Test
    public void testExecute() {
        this.pr.execute();
        EasyMock.expectLastCall().times(1);
        EasyMock.replay(this.pr);
        FileVersionTask o = new FileVersionTask("aaa", "bbb", "ccc");
        o.setImpl(this.pr);
        o.execute();
        EasyMock.reset(this.pr);
        EasyMock.replay(this.pr);
        o = new FileVersionTask("aaa", "bbb", "ccc");
        assertNull(o.getImpl());
        o.execute();
        EasyMock.verify(this.pr);
    }

    /**
     * Test file version task.
     */
    @Test
    public void testFileVersionTask() {
        FileVersionTask o = new FileVersionTask();
    }

    /**
     * Test file version task string string string.
     */
    @Test
    public void testFileVersionTaskStringStringString() {
        FileVersionTask o = new FileVersionTask("aaa", "bbb", "ccc");
        assertEquals("aaa", o.getDirToCheck());
        assertEquals("bbb", o.getFileToWrite());
        assertEquals("ccc", o.getListOfExtensions());
    }

    /**
     * Test dir to check.
     */
    @Test
    public void testDirToCheck() {
        FileVersionTask o = new FileVersionTask();
        o.setDirToCheck("aaa");
        assertEquals("aaa", o.getDirToCheck());
    }

    /**
     * Test get file to write.
     */
    @Test
    public void testGetFileToWrite() {
        FileVersionTask o = new FileVersionTask();
        o.setFileToWrite("aaa");
        assertEquals("aaa", o.getFileToWrite());
    }

    /**
     * Test list of extensions.
     */
    @Test
    public void testListOfExtensions() {
        FileVersionTask o = new FileVersionTask();
        o.setListOfExtensions("aaa");
        assertEquals("aaa", o.getListOfExtensions());
    }

}
