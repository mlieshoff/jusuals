/*
 * FileHeaderCheckerImpl.java
 *
 * 10.05.2010
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
package org.mili.ant;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import org.apache.commons.io.*;
import org.mili.core.regex.*;

/**
 * This class is a default implementation of a file header checker.
 *
 * @author Michael Lieshoff
 *
 */
public class FileHeaderCheckerImpl {
    private File dir = null;
    private File template = null;

    /**
     * Instantiates a new file header checker impl.
     *
     * @param dir the dir
     * @param template the template
     */
    protected FileHeaderCheckerImpl(File dir, File template) {
        super();
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("directory not valid!");
        }
        if (!template.exists() || !template.isFile()) {
            throw new IllegalArgumentException("template not valid!");
        }
        this.dir = dir;
        this.template = template;
    }

    /**
     * Creates the.
     *
     * @param dir the dir
     * @param template the template
     * @return the file header checker impl
     */
    public static FileHeaderCheckerImpl create(File dir, File template) {
        return new FileHeaderCheckerImpl(dir, template);
    }

    /**
     * Start.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void start() throws IOException {
        Iterator<File> i = FileUtils.iterateFiles(this.dir, new String[]{"java"}, true);
        String temp = FileUtils.readFileToString(this.template);
        while (i.hasNext()) {
            File f = i.next();
            try {
                this.proceed(f, temp);
            } catch (IOException e) {
                System.out.println("ERROR: " + f + " -> " + e.getMessage());
            }
        }
    }

    /**
     * Proceed.
     *
     * @param f the f
     * @param temp the temp
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void proceed(File f, String temp) throws IOException {
        String s = FileUtils.readFileToString(f);
        String ds = "";
        int end = -1;
        if (s.startsWith("/*")) {
            // filezeit aus header holen
            end = s.indexOf("*/");
            if (end > 0) {
                String s0 = s.substring(0, end);
                Matcher m = RegExLib.Date.getMatcher(s0);
                if (m.find()) {
                    ds = m.group(1);
                }
            } else {
                System.out.println("WARN: File has not well formed file header! " + f);
            }
        }
        if (ds.length() == 0) {
            ds = String.format("%1$te.%1$tm.%1$tY", new Date(f.lastModified()));
        }
        String t = temp.replace("${filename}", f.getName()).replace("${date}", ds);
        if (end > 0) {
            FileUtils.writeStringToFile(f, t + s.substring(end + 2));
        } else {
            FileUtils.writeStringToFile(f, t + s);
        }
        System.out.println("process " + f);
    }

}

