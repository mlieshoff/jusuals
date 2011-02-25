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


import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;
import org.mili.test.*;

/**
 * @author Michael Lieshoff
 */
public class PropUtilTest {

    private File file = new File(TestUtils.TMP_FOLDER, "abbas.properties");

    @Before
    public void setUp() throws Exception {
        Properties p = new Properties();
        p.setProperty("a", "4711");
        OutputStream os = new FileOutputStream(this.file);
        p.store(os, "test props.");
        os.close();
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailReadPropertiesBecauseNullFile() {
        PropUtil.readProperties(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailReadPropertiesBecauseFileNotExisted() {
        PropUtil.readProperties(new File("bundy"));
    }

    @Test
    public void shouldReadProperties() {
        Properties p = PropUtil.readProperties(this.file);
        assertEquals("4711", p.get("a"));
    }
}
