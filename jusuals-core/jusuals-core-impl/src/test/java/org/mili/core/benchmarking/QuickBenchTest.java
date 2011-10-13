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
package org.mili.core.benchmarking;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class QuickBenchTest {
    private static long countOfPrepares = 0;
    private static long countOfExecutes = 0;
    private static long countOfResets = 0;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        countOfPrepares = 0;
        countOfExecutes = 0;
        countOfResets = 0;
    }

    @Test
    public void shouldRunSequenceBenchWithMilis() {
        Bench bench = new NxMBench();
        QuickBench.benchNxM(1, 1, new long[]{1}, new long[]{1}, new Bench[]{bench});
        assertEquals(2, countOfPrepares);
        assertEquals(2, countOfExecutes);
        assertEquals(2, countOfResets);
    }

    @Test
    public void shouldRunSequenceBenchWithoutMilis() {
        Bench bench = new NxMBench();
        QuickBench.benchNxM(0, 1, new long[]{1}, new long[]{1}, new Bench[]{bench});
        assertEquals(2, countOfPrepares);
        assertEquals(2, countOfExecutes);
        assertEquals(2, countOfResets);
    }

    @Test
    public void shouldRunIntervalBenchWithMilis() {
        Bench bench = new NxIntervalBench();
        QuickBench.benchNxIntervall(1, 1, new long[]{1}, 1, 2, new Bench[]{bench});
        assertEquals(1, countOfPrepares);
        assertEquals(2, countOfExecutes);
        assertEquals(2, countOfResets);
    }

    @Test
    public void shouldRunIntervalBenchWithoutMilis() {
        Bench bench = new NxIntervalBench();
        QuickBench.benchNxIntervall(0, 1, new long[]{1}, 1, 2, new Bench[]{bench});
        assertEquals(1, countOfPrepares);
        assertEquals(2, countOfExecutes);
        assertEquals(2, countOfResets);
    }

    class NxMBench extends AbstractBench {
        @Override
        public void reset() {
            countOfResets ++;
        }
        @Override
        public void prepare(long rounds) {
            countOfPrepares ++;
        }
        @Override
        public void execute(long rounds) {
            countOfExecutes ++;
        }
    }

    class NxIntervalBench extends AbstractBench {
        @Override
        public void reset() {
            countOfResets ++;
        }
        @Override
        public void prepare(long rounds) {
            countOfPrepares ++;
        }
        @Override
        public void execute(long rounds) {
            countOfExecutes ++;
        }
    }

}