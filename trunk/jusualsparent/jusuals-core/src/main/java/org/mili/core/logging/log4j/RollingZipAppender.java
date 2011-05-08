/*
 * RollingZipAppender.java
 *
 * 08.05.2011
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
package org.mili.core.logging.log4j;

import java.io.*;
import java.text.*;
import java.util.zip.*;

import org.apache.log4j.*;
import org.mili.core.logging.*;
import org.mili.core.logging.Logger;

/**
 * This class defines an appender that zips the rotated file.
 */
public class RollingZipAppender extends RollingFileAppender {
    private static final Logger LOG = DefaultLogger.create(RollingFileAppender.class);
    private static final String archivedLogFilePattern = "{0}.{1}.zip";

    @Override
    public void rollOver() {
        this.backupIfNeeded();
        this.closeLogFile();
    }

    private void backupIfNeeded() {
        if (this.maxBackupIndex > 0) {
            this.deleteOldestLogFile();
            this.remapBackupZipFiles();
            File target = this.renameLogFile();
            this.archiveLogFile(target);
        }
    }

    private void deleteOldestLogFile() {
        File file = new File(this.createArchivedLogFilename(this.maxBackupIndex));
        if (file.exists()) {
            file.delete();
        }
    }

    private String createArchivedLogFilename(int nr) {
        return MessageFormat.format(archivedLogFilePattern, this.fileName, nr);
    }

    private void remapBackupZipFiles() {
        // remap {(maxBackupIndex - 1), ..., 2, 1} will be {maxBackupIndex, ..., 3, 2}
        for (int i = this.maxBackupIndex - 1; i >= 1; i--) {
            File file = new File(this.createArchivedLogFilename(i));
            if (file.exists()) {
                File target = new File(this.createArchivedLogFilename(i + 1));
                LOG.debug("Renaming file " + file + " to " + target);
                file.renameTo(target);
            }
        }
    }

    private File renameLogFile() {
        File target = new File(this.fileName + "." + 1);
        this.closeFile();
        File file = new File(this.fileName);
        LOG.debug("Renaming file " + file + " to " + target);
        file.renameTo(target);
        return target;
    }

    private void archiveLogFile(File target) {
        try {
            archiveFile(target);
            target.delete();
        } catch (IOException e) {
            LOG.error("Failed to zip file [" + target.getPath() + "].");
        }
    }

    private void closeLogFile() {
        try {
            this.setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
        } catch (IOException e) {
            LOG.error("setFile(" + fileName + ", false) call failed.", e);
        }
    }

    private void archiveFile(File logFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(logFile.getPath() + ".zip");
        ZipOutputStream zos = new ZipOutputStream(fos);
        FileInputStream fis = new FileInputStream(logFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ZipEntry entry = new ZipEntry(logFile.getCanonicalFile().getName());
        zos.putNextEntry(entry);
        byte[] barray = new byte[1024];
        int bytes;
        while ((bytes = bis.read(barray, 0, 1024)) > -1) {
            zos.write(barray, 0, bytes);
        }
        zos.flush();
        zos.close();
        fos.close();
    }
}
