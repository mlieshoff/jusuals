/*
 * RevisionUtil.java
 *
 * 02.12.2009
 *
 * Copyright 2009 Michael Lieshoff
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
import java.util.regex.*;

import org.apache.commons.lang.*;

/**
 * This class defines an utility class to organize revision numbers in filenames.
 *
 * @author Michael Lieshoff
 */
public final class RevisionUtil {
    /** Propertyname to defines custom patterns. */
    public final static String PROP_IGNORE_PATTERN = RevisionUtil.class.getName() + ".Pattern";
    private static String REV_PATTERN = "00000";

    /**
     * Instantiates a new revision util.
     */
    public RevisionUtil() {
        super();
    }

    /**
     * Insert revision.
     *
     * @param filenameWithoutMinus the filename without minus
     * @param revisionNumber the revision number
     * @param usePattern the use pattern
     * @return filename with revision
     */
    static String insertRevision(String filenameWithoutMinus, int revisionNumber,
            boolean usePattern) {
        Validate.notEmpty(filenameWithoutMinus, "s");
        Validate.isTrue(revisionNumber > 0, "revision cannot be <= 0 !");
        String rev = String.valueOf(revisionNumber);
        String revision = StringUtils.overlay(REV_PATTERN, rev,
                REV_PATTERN.length() - rev.length(), REV_PATTERN.length());
        return filenameWithoutMinus.replaceAll("^(.*)\\.(.*)$", "$1-".concat(usePattern
                ? revision : rev).concat(".$2"));
    }

    /**
     * List files with revision.
     *
     * @param file the file
     * @return the file list
     * @throws FileNotFoundException the file not found exception
     */
    static File[] listFilesWithRevision(File file) throws FileNotFoundException {
        Validate.notNull(file, "file");
        final String fn = file.getName().substring(0, file.getName().lastIndexOf("."));
        File dir = file.getParentFile();
        if (!dir.exists()) {
            throw new FileNotFoundException(dir.getAbsolutePath() + " not found !");
        }
        File[] a = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String reg = "^" + fn + "[-](.*)[.](.*)$";
                return name.matches(reg);
            }
        });
        return a;
    }

    /**
     * Gets the file with highest revision.
     *
     * @param files the files
     * @return the file with highest revision
     */
    static File getFileWithHighestRevision(File[] files) {
        Validate.notEmpty(files, "fa");
        File last = null;
        int revision = -1;
        String reg = "(.*)[-](.*)[.](.*)$";
        Pattern p = Pattern.compile(reg);
        File f0 = null;
        Matcher m = null;
        for (int i = 0; i < files.length; i++) {
            f0 = files[i];
            m = p.matcher(f0.getName());
            if (m.matches()) {
                int n = Integer.parseInt(m.group(2));
                if (n > revision) {
                    revision = n;
                    last = f0;
                }
            }
        }
        return last;
    }

    /**
     * Extract revision number.
     *
     * @param filenameWithRevision the filename with revision
     * @return the int
     */
    static int extractRevisionNumber(String filenameWithRevision) {
        Validate.notEmpty(filenameWithRevision, "s");
        String reg = "(.*)[-](.*)[.](.*)$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(filenameWithRevision);
        if (m.matches()) {
            return Integer.parseInt(m.group(2));
        }
        return -1;
    }

    /**
     * Creates the new revision from file.
     *
     * @param fileWithoutRevision the file without revision
     * @return the file
     */
    public static File createNewRevisionFromFile(File fileWithoutRevision) {
        return createNewRevisionFromFile(fileWithoutRevision, true);
    }

    /**
     * Creates the new revision from file.
     *
     * @param fileWithoutRevision the file without revision
     * @param usePattern the use pattern
     * @return the file
     */
    public static File createNewRevisionFromFile(File fileWithoutRevision, boolean usePattern) {
        Validate.notNull(fileWithoutRevision, "fileWithoutRevision");
        File f = new File(insertRevision(fileWithoutRevision.getAbsolutePath(), 1, usePattern));
        return f;
    }

    /**
     * Gets the last revision of file.
     *
     * @param fileWithoutRevision the file without revision
     * @return the last revision of file
     * @throws FileNotFoundException the file not found exception
     */
    public static File getLastRevisionOfFile(File fileWithoutRevision)
            throws FileNotFoundException {
        Validate.notNull(fileWithoutRevision, "fileWithoutRevision");
        File[] fa = listFilesWithRevision(fileWithoutRevision);
        if (fa != null && fa.length > 0) {
            return getFileWithHighestRevision(fa);
        }
        return null;
    }

    /**
     * Creates the next revision from file.
     *
     * @param fileWithRevision the file with revision
     * @return the file
     * @throws FileNotFoundException the file not found exception
     */
    public static File createNextRevisionFromFile(File fileWithRevision)
            throws FileNotFoundException {
        return createNextRevisionFromFile(fileWithRevision, true);
    }

    /**
     * Creates the next revision from file.
     *
     * @param fileWithRevision the file with revision
     * @param usePattern the use pattern
     * @return the file
     * @throws FileNotFoundException the file not found exception
     */
    public static File createNextRevisionFromFile(File fileWithRevision, boolean usePattern)
            throws FileNotFoundException {
        Validate.notNull(fileWithRevision, "fileWithRevision");
        File last = getLastRevisionOfFile(fileWithRevision);
        if (last != null) {
            int r = extractRevisionNumber(last.getName()) + 1;
            File f = new File(insertRevision(fileWithRevision.getAbsolutePath(), r,
                    usePattern));
            return f;
        }
        return null;
    }

}
