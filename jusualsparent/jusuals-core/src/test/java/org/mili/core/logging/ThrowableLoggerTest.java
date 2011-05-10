/*
 * ThrowableLoggerTest.java
 *
 * 06.05.2011
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
public class ThrowableLoggerTest {
    private ThrowableLogger logger = null;
    private File dir = null;
    private File dayDir = null;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        this.dir = TestUtils.getTmpFolder(ThrowableLoggerTest.class);
        this.dayDir = new File(this.dir, new SimpleDateFormat("yyyyMMdd").format(new Date()));
        this.logger = new DefaultThrowableLogger(ThrowableLoggerTest.class, this.dir);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failsToCreateBecauseDirIsNull() {
        new DefaultThrowableLogger(String.class, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failsToCreateBecauseClassIsNull() {
        new DefaultThrowableLogger(null, new File(""));
    }

    @Test
    public void shouldCreateFolderForDay() throws Exception {
        this.logger.log(new IllegalStateException());
        assertTrue(this.dayDir.exists());
    }

    @Test
    public void shouldCreateThrowableFile() throws Exception {
        this.logger.log(new IllegalStateException(new IllegalArgumentException()));
        String name = ThrowableLoggerTest.class.getName() + ".log";
        assertTrue(new File(this.dayDir, name).exists());
    }

}
