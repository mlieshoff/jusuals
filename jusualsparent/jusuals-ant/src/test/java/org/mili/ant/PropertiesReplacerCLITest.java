/*
 * PropertiesReplacerCLITest.java
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

import javax.xml.bind.*;

import org.apache.commons.cli.*;
import org.easymock.*;
import org.junit.*;

/**
 * @author Michael Lieshoff
 */
public class PropertiesReplacerCLITest {
    private PropertiesReplacer pr = null;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        this.pr = EasyMock.createMock(PropertiesReplacer.class);
    }

    /**
     * Test execute.
     *
     * @throws Exception the exception
     */
    @Test
    public void testExecute() throws Exception {
        File f0 = new File("aaa");
        File f1 = new File("bbb");
        this.pr.replace(f0, f1);
        EasyMock.expectLastCall().times(1);
        EasyMock.replay(this.pr);
        try {
            PropertiesReplacerCLI o = new PropertiesReplacerCLI();
            o.execute(this.pr, new String[]{"-propfile", f0.getName(), "-propdesc",
                    f1.getName()});
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("exception occured!");
        }
        EasyMock.reset(this.pr);
        this.pr.replace(f0, f1);
        EasyMock.expectLastCall().andThrow(new JAXBException("")).times(1);
        EasyMock.replay(this.pr);
        // jaxb exception
        try {
            PropertiesReplacerCLI o = new PropertiesReplacerCLI();
            o.execute(this.pr, new String[]{"-propfile", "aaa", "-propdesc", "bbb"});
            Assert.fail("exception expected!");
        } catch (JAXBException e) {
        }
        // parse exception
        try {
            PropertiesReplacerCLI o = new PropertiesReplacerCLI();
            o.execute(this.pr, new String[]{"-popel"});
            Assert.fail("exception expected!");
        } catch (ParseException e) {
        }
        // help
        try {
            PropertiesReplacerCLI o = new PropertiesReplacerCLI();
            o.execute(this.pr, new String[]{"-help"});
        } catch (Exception e) {
            Assert.fail("exception occured!");
        }
        // version
        try {
            PropertiesReplacerCLI o = new PropertiesReplacerCLI();
            o.execute(this.pr, new String[]{"-version"});
        } catch (Exception e) {
            Assert.fail("exception occured!");
        }
        // missing propfile
        try {
            PropertiesReplacerCLI o = new PropertiesReplacerCLI();
            o.execute(this.pr, new String[]{"-propdesc", "aaa"});
            Assert.fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        // missing propdesc
        try {
            PropertiesReplacerCLI o = new PropertiesReplacerCLI();
            o.execute(this.pr, new String[]{"-propfile", "aaa"});
            Assert.fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }

        EasyMock.verify(this.pr);
    }

    /**
     * Test main.
     *
     * @throws Exception the exception
     */
    @Test
    public void testMain() throws Exception {
        try {
            PropertiesReplacerCLI.main(new String[]{"-propfile", "aaa", "-propdesc", "bbb"});
            Assert.fail("exception expected!");
        } catch (RuntimeException e) {
        }
    }

    /**
     * Test main_failure.
     *
     * @throws Exception the exception
     */
    @Test
    public void testMain_failure() throws Exception {
        try {
            PropertiesReplacerCLI.main(new String[]{"-propfile", "aaa", "-propdesc", "b"});
            Assert.fail("exception expected!");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

}
