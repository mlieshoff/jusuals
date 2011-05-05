/*
 * Simple.java
 *
 * 05.05.2011
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
package org.mili.core.benchmarking;

import org.mili.core.benchmarking.experimental.*;

/**
 * @author Michael Lieshoff
 */
public class Simple {

    /**
     * Instantiates a new simple.
     */
    public Simple() {
        super();
    }

    /**
     * Adds the.
     *
     * @param s the s
     * @param i the i
     */
    @Prepare
    public void add(String s, int i) {
        AnnotatedBenchTest.countOfPrepares ++;
    }

    /**
     * Gets the.
     *
     * @param i the i
     * @return the string
     */
    @Execute
    public String get(int i) {
        AnnotatedBenchTest.countOfExecutes ++;
        return null;
    }

    /**
     * Clear.
     */
    @Reset
    public void clear() {
        AnnotatedBenchTest.countOfResets ++;
    }

}
