/*
 * FileInfo.java
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

package org.mili.core.io;

import java.io.*;

/**
 * This class defines file informations.
 *
 * @author Michael Lieshoff
 */
public class FileInfo {
    private String name = "";
    private String extension = "";
    private String version = "";
    private File file = null;
    private boolean hasVersion = false;

    /**
     * Instantiates a new file info.
     */
    public FileInfo() {
        super();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the hasVersion
     */
    public boolean isHasVersion() {
        return hasVersion;
    }

    /**
     * @param hasVersion the hasVersion to set
     */
    public void setHasVersion(boolean hasVersion) {
        this.hasVersion = hasVersion;
    }

}
