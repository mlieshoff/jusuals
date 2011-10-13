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
     * @param revisionPrefix the revision prefix
     * @return filename with revision
     */
    static String insertRevision(String filenameWithoutMinus, int revisionNumber,
            boolean usePattern, String revisionPrefix) {
        Validate.notEmpty(filenameWithoutMinus, "filename cannot be empty!");
        Validate.isTrue(revisionNumber > 0, "revision cannot be <= 0!");
        Validate.notNull(revisionPrefix, "revision prefix cannot be null!");
        String rev = String.valueOf(revisionNumber);
        if (usePattern) {
            rev = StringUtils.overlay(REV_PATTERN, rev, REV_PATTERN.length() - rev.length(),
                    REV_PATTERN.length());
        }
        if (revisionPrefix.length() > 0) {
            rev = revisionPrefix + rev;
        }
        return filenameWithoutMinus.replaceAll("^(.*)\\.(.*)$", "$1-".concat(rev).concat(".$2"));
    }

    /**
     * List files with revision.
     *
     * @param file the file
     * @return the file list
     * @throws FileNotFoundException the file not found exception
     */
    static File[] listFilesWithRevision(File file) throws FileNotFoundException {
        Validate.notNull(file, "file cannot be null!");
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
     * @param revisionPrefix revision prefix
     * @return the file with highest revision
     */
    static File getFileWithHighestRevision(File[] files, String revisionPrefix) {
        Validate.notEmpty(files, "array of files cannot be null!");
        File last = null;
        int revision = -1;
        String prefix = "";
        if (revisionPrefix.length() > 0) {
            prefix = "[" + revisionPrefix + "]";
        }
        String reg = "(.*)[-]" + prefix + "(.*)[.](.*)$";
        Pattern p = Pattern.compile(reg);
        File f0 = null;
        Matcher m = null;
        for (int i = 0; i < files.length; i++) {
            f0 = files[i];
            m = p.matcher(f0.getName());
            if (m.matches()) {
                String r = m.group(2);
                int n = Integer.parseInt(r);
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
     * @param revisionPrefix the revision prefix
     * @return the revision number
     */
    static int extractRevisionNumber(String filenameWithRevision, String revisionPrefix) {
        Validate.notEmpty(filenameWithRevision, "filename cannot be empty!");
        Validate.notNull(revisionPrefix, "revision prefix cannot be null!");
        String prefix = "";
        if (revisionPrefix.length() > 0) {
            prefix = "[" + revisionPrefix + "]";
        }
        String reg = "(.*)[-]" + prefix + "(.*)[.](.*)$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(filenameWithRevision);
        if (m.matches()) {
            return Integer.parseInt(m.group(2));
        }
        return -1;
    }

    /**
     * Extract revision number.
     *
     * @param file the file
     * @return the revision number
     */
    public static int extractRevisionNumber(File file) {
        return extractRevisionNumber(file.getAbsolutePath(), "");
    }

    /**
     * Extract revision number.
     *
     * @param file the file
     * @param revisionPrefix the revision prefix
     * @return the revision number
     */
    public static int extractRevisionNumber(File file, String revisionPrefix) {
        return extractRevisionNumber(file.getAbsolutePath(), revisionPrefix);
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
        return createNewRevisionFromFile(fileWithoutRevision, usePattern, "");
    }

    /**
     * Creates the new revision from file.
     *
     * @param fileWithoutRevision the file without revision
     * @param usePattern the use pattern
     * @param revisionPrefix the revision prefix
     * @return the file
     */
    public static File createNewRevisionFromFile(File fileWithoutRevision, boolean usePattern,
            String revisionPrefix) {
        Validate.notNull(fileWithoutRevision, "file cannot be null!");
        Validate.notNull(revisionPrefix, "revision prefix cannot be null!");
        File f = new File(insertRevision(fileWithoutRevision.getAbsolutePath(), 1, usePattern,
                revisionPrefix));
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
        return getLastRevisionOfFile(fileWithoutRevision, "");
    }

    /**
     * Gets the last revision of file.
     *
     * @param fileWithoutRevision the file without revision
     * @param revisionPrefix the revision prefix
     * @return the last revision of file
     * @throws FileNotFoundException the file not found exception
     */
    public static File getLastRevisionOfFile(File fileWithoutRevision, String revisionPrefix)
            throws FileNotFoundException {
        Validate.notNull(fileWithoutRevision, "file cannot be null!");
        Validate.notNull(revisionPrefix, "revision prefix cannot be null!");
        File[] fa = listFilesWithRevision(fileWithoutRevision);
        if (fa != null && fa.length > 0) {
            return getFileWithHighestRevision(fa, revisionPrefix);
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
     * @param fileWithoutRevision the file without revision
     * @param usePattern the use pattern
     * @return the file
     * @throws FileNotFoundException the file not found exception
     */
    public static File createNextRevisionFromFile(File fileWithoutRevision, boolean usePattern)
            throws FileNotFoundException {
        return createNextRevisionFromFile(fileWithoutRevision, usePattern, "");
    }

    /**
     * Creates the next revision from file.
     *
     * @param fileWithoutRevision the file without revision
     * @param usePattern the use pattern
     * @param revisionPrefix the revision prefix
     * @return the file
     * @throws FileNotFoundException the file not found exception
     */
    public static File createNextRevisionFromFile(File fileWithoutRevision, boolean usePattern,
            String revisionPrefix) throws FileNotFoundException {
        Validate.notNull(fileWithoutRevision, "file cannot be null!");
        Validate.notNull(revisionPrefix, "revision prefix cannot be null!");
        File last = getLastRevisionOfFile(fileWithoutRevision, revisionPrefix);
        if (last != null) {
            int r = extractRevisionNumber(last.getName(), revisionPrefix) + 1;
            File f = new File(insertRevision(fileWithoutRevision.getAbsolutePath(), r,
                    usePattern, revisionPrefix));
            return f;
        }
        return null;
    }

}
