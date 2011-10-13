/*
 * DependencyCheckerImpl.java
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

import org.apache.tools.ant.*;


/**
 * This class is an ant task for the file version.
 *
 * @author Michael Lieshoff
 *
 */
public class FileVersionTask extends Task {
    private String dirToCheck = "";
    private String fileToWrite = "";
    private String listOfExtensions = "";
    private FileVersion impl = null;

    /**
     * Instantiates a new file version task.
     */
    public FileVersionTask() {
        super();
    }

    /**
     * Instantiates a new file version task.
     *
     * @param dirToCheck the dir to check
     * @param fileToWrite the file to write
     * @param listOfExtensions the list of extensions
     */
    public FileVersionTask(String dirToCheck, String fileToWrite, String listOfExtensions) {
        super();
        this.dirToCheck = dirToCheck;
        this.fileToWrite = fileToWrite;
        this.listOfExtensions = listOfExtensions;
    }

    /**
     * Execute.
     *
     * @throws BuildException the build exception
     */
    @Override
    public void execute() throws BuildException {
        if (this.impl == null) {
            this.impl = new FileVersionTaskImpl(this.fileToWrite, this.dirToCheck,
                    this.listOfExtensions);
        }
        this.impl.execute();
        return;
    }

    /**
     * Gets the dir to check.
     *
     * @return the dir to check
     */
    public String getDirToCheck() {
        return dirToCheck;
    }

    /**
     * Sets the dir to check.
     *
     * @param dirToCheck the new dir to check
     */
    public void setDirToCheck(String dirToCheck) {
        this.dirToCheck = dirToCheck;
    }

    /**
     * Gets the file to write.
     *
     * @return the file to write
     */
    public String getFileToWrite() {
        return fileToWrite;
    }

    /**
     * Sets the file to write.
     *
     * @param fileToWrite the new file to write
     */
    public void setFileToWrite(String fileToWrite) {
        this.fileToWrite = fileToWrite;
    }

    /**
     * Gets the list of extensions.
     *
     * @return the list of extensions
     */
    public String getListOfExtensions() {
        return listOfExtensions;
    }

    /**
     * Sets the list of extensions.
     *
     * @param listOfExtensions the new list of extensions
     */
    public void setListOfExtensions(String listOfExtensions) {
        this.listOfExtensions = listOfExtensions;
    }

    /**
     * Gets the impl.
     *
     * @return the impl
     */
    public FileVersion getImpl() {
        return impl;
    }

    /**
     * Sets the impl.
     *
     * @param impl the new impl
     */
    public void setImpl(FileVersion impl) {
        this.impl = impl;
    }

}
