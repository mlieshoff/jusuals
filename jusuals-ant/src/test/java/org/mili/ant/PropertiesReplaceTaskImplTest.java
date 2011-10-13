/*
 * PropertiesReplacerTaskImplTest.java
 *
 * 25.09.2009
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
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 *
 */
public class PropertiesReplaceTaskImplTest {
    String path = "./tmp/propertiesreplacetaskimpl";
    File fileToReplace = new File(this.path + "/hello.properties");
    File propertiesFile = new File(this.path + "/props.properties");

    @Before
    public void setUp() throws Exception {
        new File(path).mkdirs();
        FileUtils.writeStringToFile(fileToReplace, "c=core-1.0.5.jar\n\ra=core-1.0.0.jar\n\rb=archimedes-1.0.0.0.jar\n\rx=archimedes-utils-1.0.0.0.jar\n\rxy=jusuals-core-1.0.jar");
        String s = "core=1.0.0\n\rcore.ext=jar\n\rarchimedes=1.0.0.1\n\rarchimedes.ext=jar\n\rarchimedes-utils.ext=jar\n\rjusuals-core=1.0\n\rjusuals-core.ext=jar";
        FileUtils.writeStringToFile(propertiesFile, s);
    }

    @Test
    public void testExecute() throws Exception {
        new PropertiesReplaceTaskImpl(propertiesFile.getAbsolutePath(),
                fileToReplace.getAbsolutePath(), true).execute();
        Properties p = new Properties();
        p.load(new FileInputStream(this.fileToReplace));
        assertEquals("core-1.0.0.jar", p.getProperty("c"));
        assertEquals("core-1.0.0.jar", p.getProperty("a"));
        assertEquals("archimedes-1.0.0.1.jar", p.getProperty("b"));
        assertEquals("archimedes-utils-1.0.0.0.jar", p.getProperty("x"));
        assertEquals("jusuals-core-1.0.jar", p.getProperty("xy"));
    }
}
