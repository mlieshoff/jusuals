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
public class DirectoryCleanerTest {
    private DirectoryCleaner cleaner = null;
    private final static File DIR = TestUtils.getTmpFolder(DirectoryCleanerTest.class);
    private File dir_01 = new File(DIR, "test");
    private File file_01 = new File(dir_01, "1.xml");
    private File file_02 = new File(dir_01, "2.dat");
    private File dir_02 = new File(dir_01, "sub");
    private File file_03 = new File(dir_02, "3.txt");
    private File dir_03 = new File(DIR, "test2");
    private File file_04 = new File(dir_03, "4.txt");
    private File dir_04 = new File(dir_03, "sub");
    private File file_05 = new File(dir_04, "5.txt");

    @Before
    public void setUp() throws Exception {
        cleaner = DirectoryCleaner.create();
        if (dir_02.exists()) {
            try {
                FileUtils.forceDelete(dir_02);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dir_02.mkdirs();
        if (dir_04.exists()) {
            try {
                FileUtils.forceDelete(dir_04);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dir_04.mkdirs();
        FileUtils.writeStringToFile(file_01, "a");
        FileUtils.writeStringToFile(file_02, "b");
        FileUtils.writeStringToFile(file_03, "c");
        FileUtils.writeStringToFile(file_04, "d");
        FileUtils.writeStringToFile(file_05, "e");
    }

    @Test
    public void shouldConstruct() {
        new DirectoryCleaner();
    }

    @Test
    public void shouldCreate() {
        assertNotNull(DirectoryCleaner.create());
    }

    @Test
    public void shouldDeleteOnExit() {
        cleaner.deleter = new UnaryPredicate<File>() {
            @Override
            public boolean test(File file) {
                return false;
            }
        };
        cleaner.delete(file_01);
        assertTrue(file_01.exists());
    }

    @Test
    public void shouldChecksBeforeDelete() {
        final List<File> list = new ArrayList<File>();
        cleaner = DirectoryCleaner.create(new UnaryFunction<File, Boolean>() {
            @Override
            public Boolean evaluate(File file) {
                list.add(file);
                return true;
            }
        });
        cleaner.delete(file_01);
        assertFalse(file_01.exists());
        assertEquals(1, list.size());
        assertEquals(file_01, list.get(0));
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanFileBecauseNullFile() {
        cleaner.cleanFile(null, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanFileBecauseNegativeDays() {
        cleaner.cleanFile(file_01, -1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanFileBecauseDirectory() {
        cleaner.cleanFile(dir_01, 1);
    }

    @Test
    public void shouldCleanFile() {
        cleaner.cleanFile(file_01, 0);
        assertEquals(2, dir_01.listFiles().length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanFilesBecauseNullFiles() {
        cleaner.cleanFiles(null, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanFilesBecauseDir() {
        cleaner.cleanFiles(new File[]{dir_01}, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanFilesBecauseNegativeDays() {
        cleaner.cleanFiles(new File[]{file_01}, -1);
    }

    @Test
    public void cleanFiles() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        dc.cleanFiles(new File[]{file_01, file_02}, 1);
        assertEquals(3, dir_01.listFiles().length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanDirectoryBecauseNullDir() {
        cleaner.cleanDirectory(null, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanDirectoryBecauseFile() {
        cleaner.cleanDirectory(file_01, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanDirectoryBecauseNegativeDays() {
        cleaner.cleanDirectory(dir_01, -1);
    }

    @Test
    public void shouldCleanDirectory() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        dc.cleanDirectory(dir_01, 5);
        assertEquals(3, dir_01.listFiles().length);
        assertEquals(1, dir_02.listFiles().length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanDirectoryRecursiveBecauseNullDir() {
        cleaner.cleanDirectory(null, true, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanDirectoryRecursiveBecauseNegativeDays() {
        cleaner.cleanDirectory(dir_01, true, -1);
    }

    @Test
    public void shouldCleanDirectoryRecursive() {
        cleaner.isExpiring = new BinaryPredicate<Long, Integer>() {
            @Override
            public boolean test(Long time, Integer days) {
                return true;
            }
        };
        cleaner.cleanDirectory(dir_01, true, 1);
        assertEquals(1, dir_01.listFiles().length);
        assertEquals(0, dir_02.listFiles().length);
    }


    @Test(expected=IllegalArgumentException.class)
    public void failCleanDirectoriesBecauseNullDirs() {
        cleaner.cleanDirectories(null, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanDirectoriesBecauseNegativeDays() {
        cleaner.cleanDirectories(new File[]{dir_01}, -1);
    }

    @Test
    public void shouldCleanDirectories() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        dc.cleanDirectories(new File[]{dir_01, dir_03}, 1);
        assertEquals(3, dir_01.listFiles().length);
        assertEquals(1, dir_02.listFiles().length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanDirectoriesRecursiveBecauseNegativeDays() {
        cleaner.cleanDirectories(new File[]{dir_01}, true, -1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCleanDirectoriesRecursiveBecauseNullFiles() {
        cleaner.cleanDirectories(null, true, 1);
    }

    @Test
    public void shouldCleanDirectoriesRecursive() {
        DirectoryCleaner dc = DirectoryCleaner.create();
        dc.cleanDirectories(new File[]{dir_01, dir_03}, true, 10);
        assertEquals(3, dir_01.listFiles().length);
        assertEquals(1, dir_02.listFiles().length);
        assertEquals(2, dir_03.listFiles().length);
        assertEquals(1, dir_04.listFiles().length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failIsFileExpiredBecauseNullFile() {
        cleaner.isFileExpired(null, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failIsFileExpiredBecauseNegativeDays() {
        cleaner.isFileExpired(file_01, -1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failIsFileExpiredBecauseDir() {
        cleaner.isFileExpired(dir_01, -1);
    }

    @Test
    @Ignore // use another variant to set the actual date
    public void shouldIsFileExpiredWithFileTime() throws Exception {
        DirectoryCleaner dc = new DirectoryCleaner();
        GregorianCalendar gc = new GregorianCalendar();
        gc.roll(Calendar.DAY_OF_YEAR, -10);
        assertTrue(dc.isFileExpired(gc.getTimeInMillis(), 4));
        assertFalse(dc.isFileExpired(gc.getTimeInMillis(), 10));
        assertFalse(dc.isFileExpired(gc.getTimeInMillis(), 11));
    }

    @Test
    public void shouldIsFileExpired() {
        DirectoryCleaner dc = new DirectoryCleaner();
        assertTrue(dc.isFileExpired(file_01, 0));
        assertFalse(dc.isFileExpired(file_01, 1));
        assertFalse(dc.isFileExpired(file_01, 10));
    }

    @Test(expected=IllegalArgumentException.class)
    public void failIsFileExpiredWithFileTimeBecauseNegativeDays() {
        cleaner.isFileExpired(1, -1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failIsFileExpiredWithFileTimeBecauseNegativeTime() {
        cleaner.isFileExpired(-1, 1);
    }
}
