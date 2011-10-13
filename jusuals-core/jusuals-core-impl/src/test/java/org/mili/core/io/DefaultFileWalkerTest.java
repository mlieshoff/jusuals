/*
 * DefaultFileWalkerTest.java
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
import java.util.*;

import org.apache.commons.functor.*;
import org.apache.commons.io.*;
import org.junit.*;
import org.mili.test.*;

import static org.junit.Assert.*;


/**
 * @author Michael Lieshoff
 */
public class DefaultFileWalkerTest {
    private static final File DIR = TestUtils.getTmpFolder(DefaultFileWalkerTest.class);
    private static final File FILE_1 = new File(DIR, "test1.jar");
    private static final File FILE_2 = new File(DIR, "test2.jar");
    private DefaultFileWalker walker = null;

    @BeforeClass
    public static void beforeClass() throws Exception {
        if (DIR.exists()) {
            FileUtils.forceDelete(DIR);
        }
        DIR.mkdirs();
        FileUtils.writeStringToFile(FILE_1, "file1");
        FileUtils.writeStringToFile(FILE_2, "file2");
    }

    @Before
    public void setUp() throws Exception {
        this.walker = new DefaultFileWalker(DIR);
    }

    @Test
    public void shouldConstruct() {
        new DefaultFileWalker(DIR);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseNull() {
        new DefaultFileWalker(null);
    }

    @Test
    public void shouldCreate() {
        assertNotNull(DefaultFileWalker.create(DIR));
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCreateBecauseNull() {
        DefaultFileWalker.create(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failWalkBecauseNull() {
        this.walker.walk(null);
    }

    @Test
    public void shouldWalk() {
        final List<File> list = new ArrayList<File>();
        this.walker.walk(new UnaryProcedure<File>() {
            @Override
            public void run(File file) {
                list.add(file);
            }
        });
        assertEquals(2, list.size());
        assertTrue(list.contains(FILE_1));
        assertTrue(list.contains(FILE_2));
    }

}
