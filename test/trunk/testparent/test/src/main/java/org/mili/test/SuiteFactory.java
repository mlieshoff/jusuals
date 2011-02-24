/*
 * SuiteFactory.java
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

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import junit.framework.*;
import junitx.util.*;

import org.apache.commons.lang.*;

/**
 * This class defines a factory for test suites.
 *
 * @author Michael Lieshoff
 *
 */
public class SuiteFactory {
    /** standard filter for class files and source code files. */
    public final static TestFilter STANDARDFILTER = new TestFilter() {
        @Override
        public boolean include(Class cls) {
            return include(cls.getName());
        }
        @Override
        public boolean include(String s) {
            return s.endsWith("Test") || s.endsWith("Test.class");
        }
    };

    /**
     * <p>This method creates a test suite upon a directory containing test sources or classes
     * and throws an <code>IllegalArgumentException</code>, if the directory not exists or is
     * empty or is not a directory.</p>
     *
     * <pre>
     * SuiteFactory.createStandardDirectorySuite("./tmp/bin"); // test classes<br/>
     * </pre>
     *
     * @param dir directory with test classes or sources
     * @return created test suite.
     * @throws Exception if error occurs.
     * @precondition dir <code>notEmpty</code> and <code>existed</code>.
     */
    public static Test createStandardDirectorySuite(String dir) throws Exception {
        return createStandardDirectorySuite(dir, STANDARDFILTER);
    }
    
    /**
     * <p>This method creates a test suite upon a directory containing test sources or classes
     * and throws an <code>IllegalArgumentException</code>, if the directory not exists or is
     * empty or is not a directory.</p>
     *
     * <pre>
     * SuiteFactory.createStandardDirectorySuite("./tmp/bin", myFilter);<br/>
     * SuiteFactory.createStandardDirectorySuite("./tmp/bin", STANDARDFILTER);<br/>
     * </pre>
     *
     * @param dir directory with test classes or sources
     * @param filter the test filter to use.
     * @return created test suite.
     * @throws Exception if error occurs.
     * @precondition dir <code>notEmpty</code> and <code>existed</code>.
     */
    public static Test createStandardDirectorySuite(String dir, final TestFilter filter) 
            throws Exception {
        Validate.notEmpty(dir);
        Validate.isTrue(new File(dir).exists());
        DirectorySuiteBuilder builder = new DirectorySuiteBuilder(filter) {
            @Override
            protected void merge(List classList, TestSuite suite)
                    throws ClassNotFoundException, IllegalAccessException,
                    InvocationTargetException {
                for (Object o : classList) {
                    Class<?> cls = Class.forName((String) o);
                    if (filter.include(cls)) {
                        if (TestCase.class.isAssignableFrom(cls)) { // JUnit 3
                            suite.addTest(new TestSuite(cls));
                        } else { // wrap JUnit 4 test
                            suite.addTest(new JUnit4TestAdapter(cls));
                        }
                    }
                }
            }
        };
        return builder.suite(dir);
    }

}
