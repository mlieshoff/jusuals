/*
 * PropertiesReplaceTask.java
 *
 * 02.11.2009
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

import org.apache.tools.ant.*;

/**
 * This class is an ant task for properties replace.
 *
 * @author Michael Lieshoff
 *
 */
public class PropertiesReplaceTask extends Task {
    private String propertiesFile = "";
    private String fileToReplace = "";
    private boolean errorIfFileToReplaceDontExists = true;
    private PropertiesReplace impl = null;

    /**
     * Instantiates a new properties replace task.
     */
    public PropertiesReplaceTask() {
        super();
    }

    /**
     * Instantiates a new properties replace task.
     *
     * @param propertiesFile the properties file
     * @param fileToReplace the file to replace
     * @param errorIfFileToReplaceDontExists the error if file to replace dont exists
     */
    public PropertiesReplaceTask(String propertiesFile, String fileToReplace,
            boolean errorIfFileToReplaceDontExists) {
        super();
        this.propertiesFile = propertiesFile;
        this.fileToReplace = fileToReplace;
        this.errorIfFileToReplaceDontExists = errorIfFileToReplaceDontExists;
    }

    @Override
    public void execute() throws BuildException {
        if (this.impl == null) {
            this.impl = new PropertiesReplaceTaskImpl(this.propertiesFile, this.fileToReplace,
                    this.errorIfFileToReplaceDontExists);
        }
        this.impl.execute();
        return;
    }

    /**
     * @return the impl
     */
    public PropertiesReplace getImpl() {
        return impl;
    }

    /**
     * @param impl the impl to set
     */
    public void setImpl(PropertiesReplace impl) {
        this.impl = impl;
    }

    /**
     * @return the propertiesFile
     */
    public String getPropertiesFile() {
        return propertiesFile;
    }

    /**
     * @param propertiesFile
     *            the propertiesFile to set
     */
    public void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    /**
     * @return the fileToReplace
     */
    public String getFileToReplace() {
        return fileToReplace;
    }

    /**
     * @param fileToReplace
     *            the fileToReplace to set
     */
    public void setFileToReplace(String fileToReplace) {
        this.fileToReplace = fileToReplace;
    }

    /**
     * @return the errorIfFileToReplaceDontExists
     */
    public boolean isErrorIfFileToReplaceDontExists() {
        return errorIfFileToReplaceDontExists;
    }

    /**
     * Sets the error if file to replace dont exists.
     *
     * @param errorIfFileToReplaceDontExists the new error if file to replace dont exists
     */
    public void setErrorIfFileToReplaceDontExists(boolean errorIfFileToReplaceDontExists) {
        this.errorIfFileToReplaceDontExists = errorIfFileToReplaceDontExists;
    }

}
