/*
 * DirectoryCleaner.java
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

import org.apache.commons.functor.*;
import org.apache.commons.lang.*;

/**
 * This class defines a helper to clean diretories.
 *
 * @author Michael Lieshoff
 */
public class DirectoryCleaner {
    private final static long ONE_DAY_IN_MS = 24 * 60 * 60 * 1000;
    BinaryPredicate<Long, Integer> isExpiring = new BinaryPredicate<Long, Integer>() {
        @Override
        public boolean test(Long time, Integer days) {
            Validate.isTrue(days >= 0, "days cannot be negative!");
            Validate.isTrue(time >= 0, "time cannot be negative!");
            long ms0 = 0;
            if (days > 0) {
                ms0 = ONE_DAY_IN_MS * days;
            } else {
                return true;
            }
            long ms = System.currentTimeMillis() - ms0 - ONE_DAY_IN_MS;
            return ms > time;
        }
    };
    UnaryPredicate<File> deleter = new UnaryPredicate<File>() {
        @Override
        public boolean test(File file) {
            return file.delete();
        }
    };

    /**
     * Instantiates a new directory cleaner.
     */
    protected DirectoryCleaner() {
        super();
    }

    /**
     * Creates a new directory cleaner.
     *
     * @return the directory cleaner
     */
    public static DirectoryCleaner create() {
        return new DirectoryCleaner();
    }

    /**
     * Deletes a file.
     *
     * @param f the file
     */
    void delete(File f) {
        if (!this.deleter.test(f)) {
            f.deleteOnExit();
        }
    }

    /**
     * Checks if is file expired.
     *
     * @param t the actual time in milli seconds.
     * @param days the days for expiring.
     * @return true, if is file expired
     */
    boolean isFileExpired(long t, int days) {
        return this.isExpiring.test(t, days);
    }

    /**
     * Checks if is file expired.
     *
     * @param f the file
     * @param days the days for expiring
     * @return true, if is file expired
     */
    public boolean isFileExpired(File f, int days) {
        Validate.notNull(f, "file cannot be null!");
        Validate.isTrue(f.isFile(), "file is not a file: " + f.getAbsolutePath());
        return this.isFileExpired(f.lastModified(), days);
    }

    /**
     * Cleans a file.
     *
     * @param f the file
     * @param days the days for expiring
     */
    public void cleanFile(File f, int days) {
        Validate.notNull(f, "file cannot be null!");
        Validate.isTrue(days >= 0, "days cannot be negative!");
        Validate.isTrue(f.isFile(), "file is not a file: " + f.getAbsolutePath());
        if(f.exists() && this.isFileExpired(f, days)){
            this.delete(f);
        }
    }

    /**
     * Cleans files.
     *
     * @param fs the files
     * @param days the days for expiring
     */
    public void cleanFiles(File[] fs, int days) {
        Validate.notNull(fs, "array of files cannot be null!");
        Validate.isTrue(days >= 0, "days cannot be negative!");
        for (int i = 0; i < fs.length; i++) {
            File file = fs[i];
            this.cleanFile(file, days);
        }
    }

    /**
     * Cleans a directory.
     *
     * @param d the directory
     * @param days the days for expiring
     */
    public void cleanDirectory(File d, int days) {
        this.cleanDirectory(d, false, days);
    }

    /**
     * Cleans a directory.
     *
     * @param d the directorx
     * @param recursive if recursive clean
     * @param days the days for expiring
     */
    public void cleanDirectory(File d, boolean recursive, int days) {
        Validate.notNull(d, "directory cannot be null!");
        Validate.isTrue(days >= 0, "days cannot be negative!");
        Validate.isTrue(d.isDirectory(), "file is not a directory: " + d.getAbsolutePath());
        if(d.exists()) {
            File[] fs = d.listFiles();
            for (int i = 0; i < fs.length; i++) {
                File file = fs[i];
                if (recursive && file.isDirectory()) {
                    this.cleanDirectory(file, days);
                } else if (file.isFile()) {
                    if (this.isFileExpired(file, days)) {
                        this.delete(file);
                    }
                }
            }
        }
    }

    /**
     * Cleans directories.
     *
     * @param ds the directories
     * @param days the days for expiring
     */
    public void cleanDirectories(File[] ds, int days) {
        this.cleanDirectories(ds, false, days);
    }

    /**
     * Cleans directories.
     *
     * @param ds the directories
     * @param recursive if recursive
     * @param days the days for expiring
     */
    public void cleanDirectories(File[] ds, boolean recursive, int days) {
        Validate.notNull(ds, "array of directories cannot be null!");
        Validate.isTrue(days >= 0, "days cannot be negative!");
        for (int i = 0; i < ds.length; i++) {
            File file = ds[i];
            this.cleanDirectory(file, recursive, days);
        }
    }

}
