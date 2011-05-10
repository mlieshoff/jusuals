/*
 * RollingZipAppender.java
 *
 * 08.05.2011
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
package org.mili.core.logging.log4j;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;
import org.junit.*;
import org.mili.core.logging.log4j.RollingZipAppender.*;
import org.mili.test.*;

import static org.junit.Assert.*;


/**
 * @author Michael Lieshoff
 */
public class RollingZipAppenderTest {
    private File dir = TestUtils.getTmpFolder(RollingZipAppenderTest.class);
    private RollingZipAppender appender = new RollingZipAppender();
    private File logFile = new File(this.dir, "test.log");

    @Before
    public void setUp() throws Exception {
        if (this.dir.exists()) {
            this.dir.delete();
            FileUtils.forceDelete(this.dir);
        }
        this.dir.mkdirs();
        this.appender.setFile(this.logFile.getAbsolutePath());
        this.appender.setMaxFileSize("1KB");
        this.appender.setMaxBackupIndex(5);
    }

    @Test
    public void shouldThrowExceptionButContinuesToArchive() throws Exception {
        this.appender.setArchiver(new Archiver() {
            @Override
            public void archiveFile(File file) throws IOException {
                throw new IOException();
            }
        });
        this.createLogFile();
        this.appender.rollOver();
    }

    @Test
    public void shouldThrowExceptionWhileCloseButContinuesToArchive() throws Exception {
        this.appender.setCloser(new Closer() {
            @Override
            public void closeFile(String filename, boolean append, boolean bufferedIO,
                    int bufferSize) throws IOException {
                throw new IOException();
            }
        });
        this.createLogFile();
        this.appender.rollOver();
    }

    @Test
    public void shouldOnlyOneFile() throws Exception {
        this.createLogFile();
        this.appender.rollOver();
        assertEquals(1, this.countZips());
    }

    private void createLogFile() throws Exception {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 11; i ++) {
            sb.append(new Date());
            sb.append(" DEBUG ");
            sb.append(this.getClass().getName());
            sb.append(" msg-");
            sb.append(i);
            sb.append("\r\n");
        }
        FileUtils.writeStringToFile(this.logFile, sb.toString());
    }

    private int countZips() {
        return FileUtils.listFiles(this.dir, new String[]{"zip"}, false).size();
    }

    @Test
    public void shouldOnlyFiveFile() throws Exception {
        for (int i = 0; i < 10; i++) {
            this.createLogFile();
            this.appender.rollOver();
        }
        assertEquals(5, this.countZips());
    }

}
