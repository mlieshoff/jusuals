/*
 * FileVersionTaskImpl.java
 *
 * 14.10.2009
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
package org.mili.ant;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;
import org.apache.tools.ant.*;
import org.mili.core.io.*;

/**
 * This class defines a file version task. All folder will be searched recursive for files with
 * version suffixes. The version number will be extracted and written in a property file with
 * simple filename as key.
 * Example:<br/>
 * liba1.jar --> WRONG!<br/>
 * liba1.0.jar --> liba=1.0<br/>liba.ext=jar<br/>
 * liba1.0.0.jar --> liba=1.0.0<br/>liba.ext=jar<br/>
 * liba1.0.0.0.jar --> liba=1.0.0.0<br/>liba.ext=jar<br/>
 *
 * @author Michael Lieshoff
 *
 */
public class FileVersionTaskImpl implements FileVersion {
    private String fileToWrite = "";
    private String dirToCheck = "";
    private String listOfExtensions = "";

    /**
     * Instantiates a new file version task impl.
     *
     * @param fileToWrite the file to write
     * @param dirToCheck the dir to check
     * @param listOfExtensions the list of extensions
     */
    public FileVersionTaskImpl(String fileToWrite, String dirToCheck, String listOfExtensions) {
        super();
        this.fileToWrite = fileToWrite;
        this.dirToCheck = dirToCheck;
        this.listOfExtensions = listOfExtensions;
    }

    /**
     * Execute.
     *
     * @throws BuildException the build exception
     */
    public void execute() throws BuildException  {
        if (this.fileToWrite == null || this.fileToWrite.length() == 0) {
            throw new BuildException("No file to write setted !");
        }
        if (this.dirToCheck == null || this.dirToCheck.length() == 0) {
            throw new BuildException("No dir to check setted !");
        }
        if (this.listOfExtensions == null || this.listOfExtensions.length() == 0) {
            throw new BuildException("No list of extensions setted !");
        }
        String[] loe = this.listOfExtensions.split("[,]");
        String[] dirs = this.dirToCheck.split("[,]");
        Properties p = new Properties();
        FileVersionFunction fvf = new FileVersionFunction();
        for (int i = 0; i < dirs.length; i++) {
            File f0 = new File(dirs[i]);
            if (f0.exists()) {
                Collection<File> l = FileUtils.listFiles(f0, loe, true);
                for (Iterator<File> ii = l.iterator(); ii.hasNext();) {
                    File f = ii.next();
                    FileInfo fi = fvf.evaluate(f);
                    if (fi.isHasVersion()) {
                        p.setProperty(fi.getName(), fi.getVersion());
                        p.setProperty(fi.getName().concat(".ext"), fi.getExtension());
                    }
                }
            }
        }
        if (p.size() > 0) {
            File ftw = new File(this.fileToWrite);
            try {
                FileOutputStream fos = new FileOutputStream(ftw);
                p.store(fos, null);
            } catch (FileNotFoundException e) {
                throw new BuildException("File[" + ftw.getAbsolutePath() + "] not found !", e);
            } catch (IOException e) {
                throw new BuildException("File[" + ftw.getAbsolutePath()
                        + "] cannot be accessed !", e);
            }
        }
        return;
    }

}
