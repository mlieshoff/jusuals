/*
 * FileInfoTest.java
 *
 * 19.05.2011
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
package org.mili.core.io;

import java.io.*;

import org.junit.*;

import static org.junit.Assert.*;


/**
 * @author Michael Lieshoff
 */
public class FileInfoTest {
    private FileInfo fileInfo = null;


    @Before
    public void setUp() throws Exception {
        this.fileInfo = new FileInfo();
    }

    @Test
    public void shouldConstruct() {
        new FileInfo();
    }

    @Test
    public void shouldGetName() {
        this.fileInfo.setName("abbas");
        assertEquals("abbas", this.fileInfo.getName());
    }

    @Test
    public void shouldGetExtension() {
        this.fileInfo.setExtension("abbas");
        assertEquals("abbas", this.fileInfo.getExtension());
    }

    @Test
    public void shouldGetVersion() {
        this.fileInfo.setVersion("abbas");
        assertEquals("abbas", this.fileInfo.getVersion());
    }

    @Test
    public void shouldGetFile() {
        File file = new File("a");
        this.fileInfo.setFile(file);
        assertEquals(file, this.fileInfo.getFile());
    }

    @Test
    public void shouldHasVersion() {
        assertFalse(this.fileInfo.isHasVersion());
        this.fileInfo.setHasVersion(true);
        assertTrue(this.fileInfo.isHasVersion());
    }

}
