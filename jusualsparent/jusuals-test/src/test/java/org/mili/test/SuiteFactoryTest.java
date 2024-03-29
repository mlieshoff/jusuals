/*
 * SuiteFactoryTest.java
 *
 * 31.01.2011
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

package org.mili.test;

import static org.junit.Assert.*;
import junitx.util.*;

import org.junit.*;

/**
 * @author Michael Lieshoff
 */
public class SuiteFactoryTest {

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailBecauseDirectoryIsNull() throws Exception {
        SuiteFactory.createStandardDirectorySuite(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailBecauseDirectoryIsEmpty() throws Exception {
        SuiteFactory.createStandardDirectorySuite("");
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailBecauseDirectoryNoExisted() throws Exception {
        SuiteFactory.createStandardDirectorySuite("a.b.b.a.s");
    }

    @Test
    public void shouldCreateTestsuiteWithStandardFilter() throws Exception {
        assertNotNull(SuiteFactory.createStandardDirectorySuite("./target/classes"));
    }

    @Test
    public void shouldCreateTestsuiteWithCustomFilter() throws Exception {
        assertNotNull(SuiteFactory.createStandardDirectorySuite("./target/classes", 
                new TestFilter() {
            @Override
            public boolean include(Class arg0) {
                return true;
            }
            @Override
            public boolean include(String arg0) {
                return true;
            }
        }));
    }

}
