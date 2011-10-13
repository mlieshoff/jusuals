/*
 * StopClockTest.java
 *
 * 12.05.2010
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

package org.mili.core.profiling;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class StopClockTest {

    @Test
    public void testGetInstance() {
        StopClock sc = StopClock.getInstance();
        assertNotNull(sc);
    }

    @Test
    public void testHitGetDataToString() throws Exception {
        // hit
        StopClock.getInstance().hit(this);
        Thread.sleep(1000);
        StopClock.getInstance().hit(this);
        // getData und hit
/*        StopClockData scd = StopClock.getInstance().getData(this);
        assertNotNull(scd);
        assertEquals(1, scd.iterations);
        assertTrue(scd.totalTimeMs > 1000);
        assertEquals(0, scd.ms);
        Thread.sleep(1000);
        StopClock.getInstance().hit(this);
        System.out.println(scd.ms);
        assertTrue(scd.ms < 0);*/
    }

}
