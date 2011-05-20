/*
 * RevisionTask.java
 *
 * 20.05.2011
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
package org.mili.core.ant;

import org.apache.tools.ant.*;

/**
 * @author Michael Lieshoff
 */
public class RevisionTask extends Task {
    private String filename = "";
    private String property = "";
    private Revision impl = new RevisionImpl();

    /**
     * Instantiates a new revision task.
     */
    public RevisionTask() {
        super();
    }

    /**
     * Instantiates a new revision task.
     *
     * @param filename the filename
     * @param property the property
     */
    public RevisionTask(String filename, String property) {
        super();
        this.filename = filename;
        this.property = property;
    }

    @Override
    public void execute() throws BuildException {
        try {
            this.getProject().setProperty(this.property, this.impl.start(this.filename));
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

}
