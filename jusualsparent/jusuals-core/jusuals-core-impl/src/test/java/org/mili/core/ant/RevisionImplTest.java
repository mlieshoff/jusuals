/*
 * RevisionImplTest.java
 *
 * 20.05.2011
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
package org.mili.core.ant;

import java.io.*;

import org.apache.commons.io.*;
import org.junit.*;
import org.mili.test.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class RevisionImplTest {
    private final static File DIR = TestUtils.getTmpFolder(RevisionImplTest.class);
    private final static File FILE1 = new File(DIR, "test-release-3.2.1.jar");
    private final static File FILE2 = new File(DIR, "test-release-3.2.1-a12.jar");
    private final static File FILE3 = new File(DIR, "test-release-3.2.1-a13.jar");
    private final static String EXPECTED_1 = "a1";
    private final static String EXPECTED_2 = "a13";
    private final static String EXPECTED_3 = "a14";
    private RevisionImpl impl = new RevisionImpl();

    @Before
    public void setUp() throws Exception {
        if (DIR.exists()) {
            FileUtils.deleteDirectory(DIR);
        }
        DIR.mkdirs();
    }

    @Test
    public void shouldStartFromUnrevisionedFile() throws Exception {
        FileUtils.writeStringToFile(FILE1, "data");
        assertEquals(EXPECTED_1, this.impl.start(FILE1.getAbsolutePath(), "a"));
    }

    @Test
    public void shouldStartFromRevisionedFile() throws Exception {
        FileUtils.writeStringToFile(FILE2, "data");
        assertEquals(EXPECTED_2, this.impl.start(FILE1.getAbsolutePath(), "a"));
    }

    @Test
    public void shouldStartTwiceFromRevisionedFile() throws Exception {
        FileUtils.writeStringToFile(FILE2, "data");
        assertEquals(EXPECTED_2, this.impl.start(FILE1.getAbsolutePath(), "a"));
        FILE3.createNewFile();
        assertEquals(EXPECTED_3, this.impl.start(FILE1.getAbsolutePath(), "a"));
    }

}
