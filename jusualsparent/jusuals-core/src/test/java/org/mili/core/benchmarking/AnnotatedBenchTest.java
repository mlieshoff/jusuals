/*
 * AnnotatedBenchTest.java
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

import org.junit.*;
import org.mili.core.benchmarking.experimental.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class AnnotatedBenchTest {
    static long countOfPrepares = 0;
    static long countOfExecutes = 0;
    static long countOfResets = 0;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        countOfPrepares = 0;
        countOfExecutes = 0;
        countOfResets = 0;
    }

    /**
     * Fails construct because null class.
     */
    @Test(expected=IllegalArgumentException.class)
    public void failsConstructBecauseNullClass() {
        new AnnotatedBench(null);
    }

    /**
     * Should prepares.
     */
    @Test
    public void shouldPrepares() {
        Bench bench = new AnnotatedBench(Simple.class);
        bench.prepare(50);
        assertEquals(50, countOfPrepares);
    }

    /**
     * Should executes.
     */
    @Test
    public void shouldExecutes() {
        Bench bench = new AnnotatedBench(Simple.class);
        bench.execute(50);
        assertEquals(50, countOfExecutes);
    }

    /**
     * Should resets.
     */
    @Test
    public void shouldResets() {
        Bench bench = new AnnotatedBench(Simple.class);
        bench.reset();
        assertEquals(1, countOfResets);
    }

}