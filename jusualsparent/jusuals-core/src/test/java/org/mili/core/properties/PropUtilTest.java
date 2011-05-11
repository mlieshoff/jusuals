/*
 * PropUtilTest.java
 *
 * 03.02.2011
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
package org.mili.core.properties;


import java.io.*;
import java.util.*;

import org.junit.*;
import org.mili.core.io.*;
import org.mili.core.properties.PropUtil.*;
import org.mili.test.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class PropUtilTest {
    private File file = new File(TestUtils.TMP_FOLDER, "abbas.properties");
    private FileInputStream defectOnAvailableFileInputStream = null;
    private FileInputStream zeroAvailableFileInputStream = null;
    private FileInputStream defectOnCloseFileInputStream = null;
    private Wrapper defectOnCreateFileInputStreamWrapper = null;
    private Wrapper nullFileInputStreamWrapper = null;
    private Wrapper wrapperToGetDefectOnCloseInputStream = null;

    @Before
    public void setUp() throws Exception {
        Properties p = new Properties();
        p.setProperty("a", "4711");
        OutputStream os = new FileOutputStream(this.file);
        p.store(os, "test props.");
        os.close();
        this.defectOnAvailableFileInputStream = new DefectOnAvailableFileInputStream(this.file);
        this.zeroAvailableFileInputStream = new ZeroAvailableFileInputStream(this.file);
        this.defectOnCloseFileInputStream = new DefectOnCloseFileInputStream(this.file);
        this.defectOnCreateFileInputStreamWrapper = new DefectWrapper();
        this.nullFileInputStreamWrapper = new NullWrapper();
        this.wrapperToGetDefectOnCloseInputStream =
                new WrapperToGetDefectOnCloseFileInputStream();
        PropUtil.setWrapper(new PropUtil.DefaultWrapper());
    }

    @Test
    public void shouldCreates() {
        new PropUtil();
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailReadPropertiesBecauseNullFile() {
        PropUtil.readProperties((InputStream) null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailReadPropertiesBecauseFileNotExisted() {
        PropUtil.readProperties(new File("bundy"));
    }

    @Test(expected=IllegalStateException.class)
    public void shouldFailReadPropertiesBecauseStreamNotAvailable() throws Exception {
        PropUtil.readProperties(this.defectOnAvailableFileInputStream);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailReadPropertiesBecauseStreamZeroAvailable() throws Exception {
        PropUtil.readProperties(this.zeroAvailableFileInputStream);
    }

    @Test(expected=IllegalStateException.class)
    public void shouldFailReadPropertiesFileBecauseStreamNotAvailable() throws Exception {
        PropUtil.setWrapper(this.defectOnCreateFileInputStreamWrapper);
        PropUtil.readProperties(this.file);
    }

    @Test(expected=IllegalStateException.class)
    public void shouldFailReadPropertiesFileBecauseExceptionWhileClose() throws Exception {
        PropUtil.setWrapper(this.wrapperToGetDefectOnCloseInputStream);
        PropUtil.readProperties(this.file);
    }

    @Test(expected=NullPointerException.class)
    public void shouldFailReadPropertiesFileBecauseNullInputStream() throws Exception {
        PropUtil.setWrapper(this.nullFileInputStreamWrapper);
        PropUtil.readProperties(this.file);
    }

    @Test
    public void shouldReadPropertiesFile() {
        Properties p = PropUtil.readProperties(this.file);
        assertEquals("4711", p.get("a"));
    }

    @Test
    public void shouldReadProperties() throws Exception {
        Properties p = PropUtil.readProperties(FileUtil.getInputStream(this.file
                .getAbsolutePath()));
        assertEquals("4711", p.get("a"));
    }

    class DefectOnAvailableFileInputStream extends FileInputStream {

        public DefectOnAvailableFileInputStream(File file) throws FileNotFoundException {
            super(file);
        }

        public DefectOnAvailableFileInputStream(FileDescriptor fdObj) {
            super(fdObj);
        }

        public DefectOnAvailableFileInputStream(String name) throws FileNotFoundException {
            super(name);
        }

        @Override
        public int available() throws IOException {
            throw new IOException();
        }

        @Override
        public int read() throws IOException {
            return 0;
        }
    }

    class ZeroAvailableFileInputStream extends FileInputStream {

        public ZeroAvailableFileInputStream(File file) throws FileNotFoundException {
            super(file);
        }

        public ZeroAvailableFileInputStream(FileDescriptor fdObj) {
            super(fdObj);
        }

        public ZeroAvailableFileInputStream(String name) throws FileNotFoundException {
            super(name);
        }

        @Override
        public int available() throws IOException {
            return 0;
        }

        @Override
        public int read() throws IOException {
            return 0;
        }
    }

    class DefectOnCloseFileInputStream extends FileInputStream {

        public DefectOnCloseFileInputStream(File file) throws FileNotFoundException {
            super(file);
        }

        public DefectOnCloseFileInputStream(FileDescriptor fdObj) {
            super(fdObj);
        }

        public DefectOnCloseFileInputStream(String name) throws FileNotFoundException {
            super(name);
        }

        @Override
        public void close() throws IOException {
            throw new IOException();
        }

        @Override
        public int available() throws IOException {
            return 1;
        }

        @Override
        public int read() throws IOException {
            return 0;
        }
    }

    class DefectWrapper implements Wrapper {
        @Override
        public FileInputStream createFileInputStream(File file) throws IOException {
            throw new IOException();
        }
    }

    class NullWrapper implements Wrapper {
        @Override
        public FileInputStream createFileInputStream(File file) throws IOException {
            throw null;
        }
    }

    class WrapperToGetDefectOnCloseFileInputStream implements Wrapper {
        @Override
        public FileInputStream createFileInputStream(File file) throws IOException {
            return defectOnCloseFileInputStream;
        }
    }

}
