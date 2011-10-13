/*
 * PropertiesReplaceTaskImpl.java
 *
 * 14.10.2009
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

import org.apache.commons.io.*;
import org.apache.commons.lang.*;
import org.apache.tools.ant.*;

/**
 * This class is an implementation of an ant task for properties replace.
 *
 * @author Michael Lieshoff
 *
 */
public class PropertiesReplaceTaskImpl implements PropertiesReplace {
    private String propertiesFile = "";
    private String fileToReplace = "";
    private boolean errorIfFileToReplaceDontExists = true;

    /**
     * Instantiates a new properties replace task impl.
     *
     * @param propertiesFile the properties file
     * @param fileToReplace the file to replace
     * @param errorIfFileToReplaceDontExists the error if file to replace dont exists
     */
    public PropertiesReplaceTaskImpl(String propertiesFile, String fileToReplace,
            boolean errorIfFileToReplaceDontExists) {
        super();
        this.propertiesFile = propertiesFile;
        this.fileToReplace = fileToReplace;
        this.errorIfFileToReplaceDontExists = errorIfFileToReplaceDontExists;
    }

    @Override
    public void execute() throws BuildException {
        try {
            Validate.notEmpty(this.propertiesFile, "propertiesFile");
        } catch (IllegalArgumentException e) {
            throw new BuildException("No properties file is setted !", e);
        }
        try {
            Validate.notEmpty(this.fileToReplace, "fileToReplace");
        } catch (IllegalArgumentException e) {
            throw new BuildException("No file to replace is setted !", e);
        }
        Properties p = new Properties();
        File pf = new File(this.propertiesFile);
        try {
            p.load(new FileInputStream(pf));
        } catch (FileNotFoundException e) {
            throw new BuildException("Properties file not found !", e);
        } catch (IOException e) {
            throw new BuildException("Properties file cannot be accessed !", e);
        }
        File rf = new File(this.fileToReplace);
        if (!rf.exists()) {
            if (this.errorIfFileToReplaceDontExists) {
                throw new BuildException("File to replace not found !");
            }
            return;
        }
        String s = null;
        try {
            s = FileUtils.readFileToString(rf);
        } catch (IOException e) {
            throw new BuildException("File to replace cannot be accessed !", e);
        }
        for (Enumeration<?> e = p.propertyNames(); e.hasMoreElements(); ) {
            String pn = e.nextElement().toString();
            if (!pn.endsWith(".ext")) {
                String pv = p.getProperty(pn);
                String pe = p.getProperty(pn.concat(".ext"));
                String nn = pn + "-" + pv + "." + pe;
                String re = pn + "[-]{0}[-][0-9](.*)[.]" + pe;
                s = s.replaceAll(re, nn);
            }
        }
        try {
            FileUtils.writeStringToFile(rf, s);
        } catch (IOException e) {
            throw new BuildException("File to replace cannot be accessed !", e);
        }
        return;
    }

}
