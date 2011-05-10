/*
 * DefaultLoggerTest.java
 *
 * 15.07.2007
 *
 * Copyright 2007 Michael Lieshoff
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

package org.mili.core.logging;

import java.io.*;
import java.text.*;
import java.util.*;

import org.junit.*;
import org.mili.test.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class DefaultLoggerTest {

    private final static File DIR = TestUtils.getTmpFolder(DefaultLoggerTest.class);
    private final static File TMP = new File(System.getProperty("java.io.tmpdir"));
    private final static File TMPDAYDIR = new File(TMP, new SimpleDateFormat("yyyyMMdd").format(
            new Date()));
    private final static File TESTDAYDIR = new File(DIR, new SimpleDateFormat("yyyyMMdd")
            .format(new Date()));
    private DefaultLogger logger = DefaultLogger.getLogger(String.class);

    @BeforeClass
    public static void setUpClass() {
        DIR.mkdirs();
        TMPDAYDIR.mkdirs();
    }

    @Before
    public void setUp() {
        System.clearProperty(DefaultLogger.PROP_LOGTHROWABLES);
        System.clearProperty(DefaultLogger.PROP_LOGTHROWABLESDIR);
    }

    @Test
    public void shouldNotNull() {
        assertNotNull(this.logger);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failsBecauseNullClass() {
        assertNotNull(DefaultLogger.getLogger(null));
    }

    @Test
    public void shouldIgnoresIOExceptionWithinLog() {
        System.setProperty(DefaultLogger.PROP_LOGTHROWABLES, "true");
        this.logger.setThrowableLogger(new IOExceptionThrowableLogger());
        this.logger.error(new IllegalArgumentException("lala"));
    }

    @Test
    public void shouldLogErrorExceptionInTmpDir() {
        System.setProperty(DefaultLogger.PROP_LOGTHROWABLES, "true");
        this.logger.error(new IllegalArgumentException("lala"));
        assertTrue(TMPDAYDIR.exists());
        System.out.println(new File(TMPDAYDIR, "java.lang.String.log").getAbsolutePath());
        assertTrue(new File(TMPDAYDIR, "java.lang.String.log").exists());
    }

    @Test
    public void shouldLogErrorExceptionInSpecifiedDir() {
        System.setProperty(DefaultLogger.PROP_LOGTHROWABLES, "true");
        System.setProperty(DefaultLogger.PROP_LOGTHROWABLESDIR, DIR.getAbsolutePath());
        DefaultLogger.getLogger(String.class).error(new IllegalArgumentException("lala"));
        assertTrue(TESTDAYDIR.exists());
        System.out.println(new File(TESTDAYDIR, "java.lang.String.log").getAbsolutePath());
        assertTrue(new File(TESTDAYDIR, "java.lang.String.log").exists());
    }

    class IOExceptionThrowableLogger implements ThrowableLogger {
        @Override
        public void log(Throwable throwable) throws IOException {
            throw new IOException();
        }
    }
}
