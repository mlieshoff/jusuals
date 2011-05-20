/*
 * RevisionUtilTest.java
 *
 * 02.12.2009
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
package org.mili.core.io;

import java.io.*;

import org.apache.commons.io.*;
import org.junit.*;
import org.mili.test.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class RevisionUtilTest {
    private final static File DIR = TestUtils.getTmpFolder(RevisionUtilTest.class);
    private final static File FILE_WITH_MINUS = new File(DIR, "file-3.2.1-4711.txt");
    private final static File FILE_WITH_MINUS_1 = new File(DIR, "file-3.2.1-4711-1.txt");
    private final static File FILE_WITH_MINUS_2 = new File(DIR, "file-3.2.1-4711-2.txt");
    private final static File FILE_WITH_PREFIX_1 = new File(DIR, "file-3.2.1-4711-a1.txt");
    private final static File FILE_WITH_PREFIX_2 = new File(DIR, "file-3.2.1-4711-a2.txt");
    private final static File FILE_WITH_PREFIX_3 = new File(DIR, "file-3.2.1-4711-a3.txt");
    private final static File FILE_1 = new File(DIR, "file-3.2.1-4711.txt");
    File dir = null;
    File f0 = null;
    File f1 = null;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        this.dir = TestUtils.getTmpFolder(RevisionUtilTest.class);
        this.f0 = new File(dir, "file_4711.txt");
        this.f1 = new File(dir, "file_0815.txt");
        FileUtils.deleteDirectory(DIR);
        FileUtils.deleteDirectory(dir);
        dir.mkdirs();
        DIR.mkdirs();
        FileUtils.writeStringToFile(f0, "hello world !");
        FileUtils.writeStringToFile(f1, "hello abbas !");
    }

    @Test
    public void shouldConstruct() throws Exception {
        new RevisionUtil();
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCreateNewRevisionFromFile() {
        RevisionUtil.createNewRevisionFromFile(null);
    }

    @Test
    public void shouldCreateNewRevisionFromFileWithMinus() {
        File newRevFile = RevisionUtil.createNewRevisionFromFile(FILE_WITH_MINUS);
        assertNotNull(newRevFile);
        assertEquals("file-3.2.1-4711-00001.txt", newRevFile.getName());
    }

    @Test
    public void shouldCreateNewRevisionFromFile() throws Exception {
        File newRevFile = RevisionUtil.createNewRevisionFromFile(FILE_1);
        assertNotNull(newRevFile);
        assertEquals("file-3.2.1-4711-00001.txt", newRevFile.getName());
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetLastRevisionOfFile() throws Exception {
        RevisionUtil.getLastRevisionOfFile(null);
    }

    @Test
    public void shouldGetLastRevisionOfFile() throws Exception {
        File d = new File(dir, "abbas/test.txt");
        d.mkdirs();
        d.createNewFile();
        assertNull(RevisionUtil.getLastRevisionOfFile(d));
        // ein paar Revisionen erzeugen
        new File(dir, "file_4711-00001.txt").createNewFile();
        new File(dir, "file_4711-00002.txt").createNewFile();
        new File(dir, "file_4711-00003.txt").createNewFile();
        new File(dir, "file_4711-00004.txt").createNewFile();
        new File(dir, "file_0815-00001.txt").createNewFile();
        new File(dir, "file_0815-00002.txt").createNewFile();
        new File(dir, "file_0815-00003.txt").createNewFile();
        // und letzte holen
        File nf0 = RevisionUtil.getLastRevisionOfFile(f0);
        assertNotNull(nf0);
        assertTrue(nf0.exists());
        assertEquals("file_4711-00004.txt", nf0.getName());
    }

    @Test
    public void shouldGetNullLastRevisionOfFileWithMinus() throws Exception {
        assertNull(RevisionUtil.getLastRevisionOfFile(FILE_WITH_MINUS));
    }

    @Test
    public void shouldGetLastRevisionOfFileWithMinus() throws Exception {
        FILE_WITH_MINUS_1.createNewFile();
        assertEquals(FILE_WITH_MINUS_1, RevisionUtil.getLastRevisionOfFile(FILE_WITH_MINUS));
    }

    @Test
    public void shouldGetLastRevisionOfFileWithPrefix() throws Exception {
        FILE_WITH_PREFIX_1.createNewFile();
        assertEquals(FILE_WITH_PREFIX_1, RevisionUtil.getLastRevisionOfFile(FILE_1, "a"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCreateNextRevisionFromFile() throws Exception {
        RevisionUtil.createNextRevisionFromFile(null);
    }

    @Test
    public void shouldCreateNextRevisionFromFileWithMinusWithoutPattern() throws Exception {
        FILE_WITH_MINUS_1.createNewFile();
        assertEquals(FILE_WITH_MINUS_2.getName(), RevisionUtil.createNextRevisionFromFile(
                FILE_WITH_MINUS, false).getName());
    }

    @Test
    public void shouldCreateNextRevisionFromFileWithPrefix() throws Exception {
        FILE_WITH_PREFIX_1.createNewFile();
        assertEquals(FILE_WITH_PREFIX_2.getName(), RevisionUtil.createNextRevisionFromFile(
                FILE_1, false, "a").getName());
    }

    @Test
    public void shouldTwiceCreateNextRevisionFromFileWithPrefix() throws Exception {
        FILE_WITH_PREFIX_1.createNewFile();
        assertEquals(FILE_WITH_PREFIX_2.getName(), RevisionUtil.createNextRevisionFromFile(
                FILE_1, false, "a").getName());
        FILE_WITH_PREFIX_2.createNewFile();
        assertEquals(FILE_WITH_PREFIX_3.getName(), RevisionUtil.createNextRevisionFromFile(
                FILE_1, false, "a").getName());
    }

    @Test
    public void test_File_createNextRevisionFromFile_File() throws Exception {
        File d = new File(dir, "abbas/test.txt");
        d.mkdirs();
        d.createNewFile();
        assertNull(RevisionUtil.createNextRevisionFromFile(d));
        // positiv
        try {
            // eine erste Revisionen erzeugen
            new File(dir, "file_4711-00001.txt").createNewFile();
            // die naechste revision erzeugen
            File nf0 = RevisionUtil.createNextRevisionFromFile(f0); // 2
            nf0.createNewFile();
            assertNotNull(nf0);
            assertTrue(nf0.exists());
            assertEquals("file_4711-00002.txt", nf0.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        return;
    }

    /**
     * Test_ string_insert revision_ string_int.
     */
    @Test
    public void test_String_insertRevision_String_int() {
        // positiv
        assertEquals("c:/a/b/c./y/abbas-00001.txt", RevisionUtil.insertRevision(
                "c:/a/b/c./y/abbas.txt", 1, true, ""));
        assertEquals("c:/a/b/c./y/abbas-1.txt", RevisionUtil.insertRevision(
                "c:/a/b/c./y/abbas.txt", 1, false, ""));
        // negativ
        try {
            RevisionUtil.insertRevision("c:/a/b/c./y/abbas.txt", 0, true, "");
            fail("exception expected!");
        } catch(IllegalArgumentException e) {
        }
        try {
            RevisionUtil.insertRevision("c:/a/b/c./y/abbas.txt", -1, true, "");
            fail("exception expected!");
        } catch(IllegalArgumentException e) {
        }
    }

    /**
     * Test_ file array_list_ file.
     */
    @Test
    public void test_FileArray_list_File() {
        // negativ
        try {
            RevisionUtil.listFilesWithRevision(new File(new File("bassdar"), "abbas.xyz"));
            fail();
        } catch (FileNotFoundException e) {
        }
        // positiv
        try {
            // ein paar Revisionen erzeugen
            new File(dir, "file_4711-00001.txt").createNewFile();
            new File(dir, "file_4711-00002.txt").createNewFile();
            new File(dir, "file_4711-00003.txt").createNewFile();
            new File(dir, "file_4711-00004.txt").createNewFile();
            new File(dir, "file_0815-00001.txt").createNewFile();
            new File(dir, "file_0815-00002.txt").createNewFile();
            new File(dir, "file_0815-00003.txt").createNewFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        File[] fa;
        try {
            fa = RevisionUtil.listFilesWithRevision(f0);
            assertNotNull(fa);
            assertEquals(4, fa.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Test_ file_get last_ file array.
     */
    @Test
    public void test_File_getLast_FileArray() {
        try {
            // ein paar Revisionen erzeugen
            new File(dir, "file_4711-00001.txt").createNewFile();
            new File(dir, "file_4711-00002.txt").createNewFile();
            new File(dir, "file_4711-00003.txt").createNewFile();
            new File(dir, "file_4711-00004.txt").createNewFile();
            new File(dir, "file_0815-00001.txt").createNewFile();
            new File(dir, "file_0815-00002.txt").createNewFile();
            new File(dir, "file_0815-00003.txt").createNewFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        File[] fa;
        try {
            fa = RevisionUtil.listFilesWithRevision(f0);
            File f = RevisionUtil.getFileWithHighestRevision(fa, "");
            assertNotNull(f);
            assertEquals("file_4711-00004.txt", f.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
        return;
    }

    /**
     * Test_int_get revision_ string.
     */
    @Test
    public void test_int_getRevision_String() {
        assertEquals(4, RevisionUtil.extractRevisionNumber("c:/a/b/c/abbas-00004.txt", ""));
        // negativ
        assertEquals(-1, RevisionUtil.extractRevisionNumber("blarabbas.txt", ""));
        return;
    }

}
