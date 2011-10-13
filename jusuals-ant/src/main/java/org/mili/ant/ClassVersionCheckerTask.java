/*
 * ClassVersionCheckerTask.java
 *
 * 09.05.2010
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

import org.apache.tools.ant.*;
import org.mili.ant.ClassVersionCheckerImpl.*;

/**
 * This class is an ant task for the class version checker.
 *
 * @author Michael Lieshoff
 */
public class ClassVersionCheckerTask implements FileVersion {
    private String dirToCheck = "";
    private String version = "";
    private ClassVersionChecker impl = null;

    /**
     * Instantiates a new class version checker task.
     */
    public ClassVersionCheckerTask() {
        super();
    }

    /**
     * Instantiates a new class version checker task.
     *
     * @param dirToCheck the dir to check
     * @param version the version
     */
    public ClassVersionCheckerTask(String dirToCheck, String version) {
        super();
        this.dirToCheck = dirToCheck;
        this.version = version;
    }

    @Override
    public void execute() throws BuildException {
        if (this.dirToCheck == null || this.dirToCheck.length() == 0) {
            throw new BuildException("No dir to check setted !");
        }
        if (this.impl == null) {
            this.impl = new ClassVersionCheckerImpl(new File(this.dirToCheck), Type.fromInt(
                    (int) Double.parseDouble(this.version)));
        }
        this.impl.execute();
        return;
    }

    /**
     * @return the dirToCheck
     */
    public String getDirToCheck() {
        return dirToCheck;
    }

    /**
     * @param dirToCheck the dirToCheck to set
     */
    public void setDirToCheck(String dirToCheck) {
        this.dirToCheck = dirToCheck;
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

}
