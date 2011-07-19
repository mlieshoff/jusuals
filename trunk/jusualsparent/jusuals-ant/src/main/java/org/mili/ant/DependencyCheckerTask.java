/*
 * DependencyCheckerTask.java
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

/**
 * This class is an ant task for the dependency checker.
 *
 * @author Michael Lieshoff
 */
public class DependencyCheckerTask implements DependencyChecker {
    private String dirToCheck = "";
    private String patternToIgnore = "";
    private DependencyChecker impl = null;

    /**
     * Instantiates a new dependency checker task.
     */
    public DependencyCheckerTask() {
        super();
    }

    /**
     * Instantiates a new dependency checker task.
     *
     * @param dirToCheck the dir to check
     * @param patternToIgnore the pattern to ignore
     */
    public DependencyCheckerTask(String dirToCheck, String patternToIgnore) {
        super();
        this.dirToCheck = dirToCheck;
        this.patternToIgnore = patternToIgnore;
    }

    @Override
    public void execute() throws BuildException {
        if (this.dirToCheck == null || this.dirToCheck.length() == 0) {
            throw new BuildException("No dir to check setted !");
        }
        if (this.patternToIgnore == null) {
            this.patternToIgnore = "";
        }
        if (this.impl == null) {
            this.impl = new DependencyCheckerImpl(new File(dirToCheck), patternToIgnore);
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
     * @return the patternToIgnore
     */
    public String getPatternToIgnore() {
        return patternToIgnore;
    }

    /**
     * @param patternToIgnore the patternToIgnore to set
     */
    public void setPatternToIgnore(String patternToIgnore) {
        this.patternToIgnore = patternToIgnore;
    }

}
