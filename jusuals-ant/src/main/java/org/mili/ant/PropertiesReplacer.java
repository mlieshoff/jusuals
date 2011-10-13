/*
 * PropertiesReplacer.java
 *
 * 03.03.2010
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

import javax.xml.bind.*;

/**
 * This interface reads a properties file and a specified xml configuration, to replace other
 * file contents.
 *
 * @author Michael Lieshoff
 *
 */
public interface PropertiesReplacer {
    /**
     * This method starts the replacement process.
     *
     * @param propFile properties file.
     * @param propDesc properties description file.
     * @throws IOException if IO errors on reading the both files occurs.
     * @throws NoSuchElementException if there is no value for property or no replacements found
     * in target file.
     * @throws IllegalStateException if replacement property in description is multiple defined,
     * or properties and/or configuration not initiliaze correctly.
     */
    void replace(File propFile, File propDescFile) throws IOException, JAXBException;
}
