/*
 * ThrowableLogger.java
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

import org.apache.commons.lang.*;

/**
 * This class logs throwables in folder generated by day and files generated by classname.
 *
 * @author Michael Lieshoff
 */
public class ThrowableLogger {
    private DateFormat format = new SimpleDateFormat("yyyyMMdd");
    private File baseDir = null;
    private File dayDir = null;
    private Class<?> cls = null;
    /**
     * Instantiates a new throwable logger.
     *
     * @param baseDir the base dir
     */
    public ThrowableLogger(Class<?> cls, File baseDir) {
        Validate.notNull(cls);
        Validate.notNull(baseDir);
        baseDir.mkdirs();
        this.baseDir = baseDir;
        this.cls = cls;
    }

    /**
     * Log.
     *
     * @param throwable the throwable
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void log(Throwable throwable) throws IOException {
        this.createDayFolderIfNeeded();
        this.createFile(throwable);
    }

    private void createDayFolderIfNeeded() {
        this.dayDir = new File(this.baseDir, this.format.format(new Date()));
        if (!this.dayDir.exists()) {
            this.dayDir.mkdirs();
        }
    }

    private void createFile(Throwable throwable) throws IOException {
        String name = cls.getName() + ".log";
        File file = new File(this.dayDir, name);
        PrintStream ps = new PrintStream(file);
        this.writeInfos(ps, throwable);
    }

    private void writeInfos(PrintStream ps, Throwable throwable) {
        ps.println(new Date());
        throwable.printStackTrace(ps);
        ps.println(StringUtils.repeat("-", 80));
    }

}
