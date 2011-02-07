/*
 * DirectoryCleanerTest.java
 *
 * 24.06.2010
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

package org.mili.core.io;


import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;
import org.junit.*;

/**
 * @author Michael Lieshoff
 */
public class DirectoryCleanerTest {

    File dir_01 = new File("tmp/DirectoryCleanerTest/test");
    File file_01 = new File(dir_01, "1.xml");
    File file_02 = new File(dir_01, "2.dat");

    File dir_02 = new File(dir_01, "sub");
    File file_03 = new File(dir_02, "3.txt");

    File dir_03 = new File("tmp/DirectoryCleanerTest/test2");
    File file_04 = new File(dir_03, "4.txt");

    File dir_04 = new File(dir_03, "sub");
    File file_05 = new File(dir_04, "5.txt");

    @Before
    public void setUp() throws Exception {
        if (this.dir_02.exists()) {
            try {
                FileUtils.forceDelete(this.dir_02);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.dir_02.mkdirs();
        if (this.dir_04.exists()) {
            try {
                FileUtils.forceDelete(this.dir_04);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.dir_04.mkdirs();
        FileUtils.writeStringToFile(this.file_01, "a");
        FileUtils.writeStringToFile(this.file_02, "b");
        FileUtils.writeStringToFile(this.file_03, "c");
        FileUtils.writeStringToFile(this.file_04, "d");
        FileUtils.writeStringToFile(this.file_05, "e");
    }

    @Test
    public void testDirectoryCleaner() {
        new DirectoryCleaner();
    }

    @Test
    public void testCreate() {
        assertNotNull(DirectoryCleaner.create());
    }

    @Test
    public void cleanFile_Negative() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        try {
            dc.cleanFile(null, 1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            dc.cleanFile(this.file_01, -1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            dc.cleanFile(this.dir_01, 1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void cleanFile() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        dc.cleanFile(this.file_01, 1);
        assertEquals(3, this.dir_01.listFiles().length);
    }

    @Test
    public void cleanFiles_Negative() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        try {
            dc.cleanFiles(null, 1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            dc.cleanFiles(new File[]{this.file_01, this.file_02}, -1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            dc.cleanFiles(new File[]{this.dir_01}, -1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void cleanFiles() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        dc.cleanFiles(new File[]{this.file_01, this.file_02}, 1);
        assertEquals(3, this.dir_01.listFiles().length);
    }

    @Test
    public void cleanDirectory_Negative() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        try {
            dc.cleanDirectory(null, 1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            dc.cleanDirectory(this.dir_01, -1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            dc.cleanDirectory(this.file_01, 1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void cleanDirectory_File_int() {
        System.out.println(Arrays.asList(this.dir_01.list()));
        System.out.println(Arrays.asList(this.dir_02.list()));
        DirectoryCleaner dc = DirectoryCleaner.create();
        dc.cleanDirectory(this.dir_01, 5);
        assertEquals(3, this.dir_01.listFiles().length);
        assertEquals(1, this.dir_02.listFiles().length);
        System.out.println(Arrays.asList(this.dir_01.list()));
        System.out.println(Arrays.asList(this.dir_02.list()));
    }

    @Test
    public void cleanDirectory_File_boolean_int_Negative() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        try {
            dc.cleanDirectory(null, true, 1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            dc.cleanDirectory(this.file_01, true, -1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void cleanDirectory_File_boolean_int() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        dc.cleanDirectory(this.dir_01, true, 1);
        assertEquals(3, this.dir_01.listFiles().length);
        assertEquals(1, this.dir_02.listFiles().length);
    }

    @Test
    public void cleanDirectories_Negative() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        try {
            dc.cleanDirectories(null, 1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            dc.cleanDirectories(new File[]{this.file_01, this.file_02}, -1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void cleanDirectories() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        dc.cleanDirectories(new File[]{this.dir_01, this.dir_03}, 1);
        assertEquals(3, this.dir_01.listFiles().length);
        assertEquals(1, this.dir_02.listFiles().length);
    }

    @Test
    public void cleanDirectories_File_boolean_int_Negative() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        try {
            dc.cleanDirectories(null, true, 1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            dc.cleanDirectories(new File[]{this.file_01, this.file_02}, true, -1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void cleanDirectories_File_boolean_int() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        dc.cleanDirectories(new File[]{this.dir_01, this.dir_03}, true, 10);
        assertEquals(3, this.dir_01.listFiles().length);
        assertEquals(1, this.dir_02.listFiles().length);
        assertEquals(2, this.dir_03.listFiles().length);
        assertEquals(1, this.dir_04.listFiles().length);
    }

    @Test
    public void testIsFileExpired_Negative() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        try {
            dc.isFileExpired(null, 1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            dc.isFileExpired(this.file_01, -1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            dc.isFileExpired(this.dir_01, 1);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testIsFileExpired_long_int() {
        DirectoryCleaner dc = new DirectoryCleaner();
        GregorianCalendar gc = new GregorianCalendar();
        gc.roll(Calendar.DAY_OF_YEAR, -10);
        assertTrue(dc.isFileExpired(gc.getTimeInMillis(), 4));
        assertFalse(dc.isFileExpired(gc.getTimeInMillis(), 10));
        assertFalse(dc.isFileExpired(gc.getTimeInMillis(), 11));
    }

    @Test
    public void testIsFileExpired() {
        DirectoryCleaner dc = new DirectoryCleaner();
        assertFalse(dc.isFileExpired(this.file_01, 0));
        assertFalse(dc.isFileExpired(this.file_01, 1));
        assertFalse(dc.isFileExpired(this.file_01, 10));
    }
}
