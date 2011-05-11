/*
 * QuickBenchTest.java
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
package org.mili.core.benchmarking.experimental;

import org.junit.*;
import org.mili.core.benchmarking.*;
import org.mili.core.benchmarking.experimental.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class QuickBenchTest {

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Should run sequence bench.
     */
    @Test
    public void shouldRunSequenceBench() {
        Bench bench = new AnnotatedBench(Simple.class);
        QuickBench.benchNxM(1, 1, new long[]{1}, new long[]{1}, new Bench[]{bench});
        assertEquals(4, AnnotatedBenchTest.countOfPrepares);
        assertEquals(2, AnnotatedBenchTest.countOfExecutes);
        assertEquals(6, AnnotatedBenchTest.countOfResets);
    }

}