/*
 * RevisionTaskTest.java
 *
 * 25.05.2011
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
import org.junit.*;

import static org.junit.Assert.*;


/**
 * @author Michael Lieshoff
 */
public class RevisionTaskTest {
    private RevisionTask task = null;

    @Before
    public void setUp() throws Exception {
        this.task = new RevisionTask("filename", "property", "revisionPrefix");
        this.task.setProject(new Project());
    }

    @Test
    public void shouldConstruct() {
        new RevisionTask();
    }

    @Test
    public void shouldConstructWithParameters() {
        new RevisionTask("filename", "property", "revisionPrefix");
    }

    @Test
    public void shouldGetFilename() {
        this.task.setFilename("a");
        assertEquals("a", this.task.getFilename());
    }

    @Test
    public void shouldGetProperty() {
        this.task.setProperty("a");
        assertEquals("a", this.task.getProperty());
    }

    @Test
    public void shouldGetRevisionPrefix() {
        this.task.setRevisionPrefix("a");
        assertEquals("a", this.task.getRevisionPrefix());
    }

    @Test
    public void shouldExecute() {
        this.task.setImpl(new RevisionImpl());
        this.task.execute();
        assertEquals("a", this.task.getProject().getProperty("property"));
    }

    @Test(expected=BuildException.class)
    public void failsExecute() {
        this.task.setImpl(new DefectRevisionImpl());
        this.task.execute();
    }

    class RevisionImpl implements Revision {
        @Override
        public String start(String filenameWithoutRevision, String revisionPrefix)
                throws Exception {
            return "a";
        }
    }

    class DefectRevisionImpl implements Revision {
        @Override
        public String start(String filenameWithoutRevision, String revisionPrefix)
                throws Exception {
            throw new IllegalStateException();
        }
    }

}
