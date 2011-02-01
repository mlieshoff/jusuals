/*
 * FileUtilTest.java
 *
 * 20.08.2008
 *
 * Copyright 2008 Michael Lieshoff
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

import org.apache.commons.io.*;
import org.junit.*;

/**
 * @author Michael Lieshoff
 */
public class FileUtilTest {

    private File dir = new File("./tmp/fileutiltest");
    private File file = new File(dir, "test.txt");

    @Before
    public void setUp() throws Exception {
        this.dir.delete();
        this.dir.mkdirs();
        FileUtils.writeStringToFile(file, "hallo welt!");
    }

    @Test(expected=FileNotFoundException.class)
    public void shouldFailBecauseFileNotFound() throws Exception {
        FileUtil.getInputStream("a.b.c");
    }

    @Test
    public void shouldFindWindowsFileFromClassloader() throws Exception {
        FileUtil.getInputStream("c:\\java\\lang\\Object.class", FileAccessOrder.Classloader);
    }

    @Test
    public void shouldFindRelativeFileFromClassloader() throws Exception {
        FileUtil.getInputStream("./java/lang/Object.class", FileAccessOrder.Classloader);
    }

    @Test
    public void shouldFindFileFromFilesystem() throws Exception {
        FileUtil.getInputStream(this.file.getAbsolutePath());
    }

    @Test
    public void shouldSearchFileInFilesystemAndFindFileInClassloader() throws Exception {
        FileUtil.getInputStream("/java/lang/Object.class");
    }

    @Test(expected=FileNotFoundException.class)
    public void shouldFailBecauseFileInClassloaderAndInFilesystemNotFound() throws Exception {
        FileUtil.getInputStream("a.b", FileAccessOrder
                .ClassloaderThenFilesystem);
    }

    @Test
    public void shouldSearchFileInClassloaderAndFindFileInFilesystem() throws Exception {
        FileUtil.getInputStream(this.file.getAbsolutePath(), FileAccessOrder
                .ClassloaderThenFilesystem);
    }

    @Test
    public void shouldSearchFileInClassloaderAndFindFileInClassloader() throws Exception {
        FileUtil.getInputStream("/java/lang/Object.class", FileAccessOrder
                .ClassloaderThenFilesystem);
    }

    @Test(expected=FileNotFoundException.class)
    public void shouldFailBecauseFileInFilesystemNotFound() throws Exception {
        FileUtil.getInputStream("a.b.c", FileAccessOrder.Filesystem);
    }

    @Test
    public void shouldFindFileInFilesystem() throws Exception {
        FileUtil.getInputStream(this.file.getAbsolutePath(), FileAccessOrder.Filesystem);
    }

    @Test(expected=FileNotFoundException.class)
    public void shouldFailBecauseFileInClassloaderNotFound() throws Exception {
        FileUtil.getInputStream("a.b.c", FileAccessOrder.Classloader);
    }

    @Test
    public void shouldFindFileInClassloader() throws Exception {
        FileUtil.getInputStream("/java/lang/Object.class", FileAccessOrder.Classloader);
    }

}