/*
 * StopClock.java
 *
 * 21.09.2010
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

import java.util.*;

import org.mili.core.logging.*;

/**
 * This class defines a simple stop clock.
 *
 * @author Michael Lieshoff
 */
public class StopClock {
    private final static DefaultLogger log = DefaultLogger.getLogger(StopClock.class);
    private static StopClock instance = new StopClock();
    private Map<Object, StopClockData> data = new Hashtable<Object, StopClockData>();

    private StopClock() {
        super();
    }

    /**
     * Gets the single instance of StopClock.
     *
     * @return single instance of StopClock
     */
    public static StopClock getInstance() {
        return instance;
    }

    /**
     * Hit.
     *
     * @param source the source
     */
    public void hit(Object source) {
        StopClockData tad = this.data.get(source);
        if (tad == null) {
            tad = new StopClockData();
        }
        if (tad.ms == 0) {
            tad.ms = -System.currentTimeMillis();
        } else {
            tad.ms += System.currentTimeMillis();
            tad.iterations ++;
            tad.totalTimeMs += tad.ms;
            tad.ms = 0;
        }
        this.data.put(source, tad);
    }

    /**
     * Gets the data.
     *
     * @param source the source
     * @return the data
     */
    public StopClockData getData(Object source) {
        return this.data.get(source);
    }

    /**
     * Reset.
     */
    public void reset() {
        this.data.clear();
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        for (Iterator<Object> i = this.data.keySet().iterator(); i.hasNext(); ) {
            Object o = i.next();
            StopClockData tda = this.data.get(o);
            s.append(o.toString());
            s.append(" - ");
            s.append(tda.toString());
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Show as info log.
     */
    public void showAsInfoLog() {
        for (Iterator<Object> i = this.data.keySet().iterator(); i.hasNext(); ) {
            Object o = i.next();
            StopClockData tda = this.data.get(o);
            log.info(o.toString(), " - ", tda.toString());
        }
    }

    class StopClockData {
        final static String t0 =
                "%1 iterations with total time of [%2] ms and average [%3] ms.";
        int iterations = 0;
        int totalTimeMs = 0;
        long ms = 0;

        StopClockData() {
            super();
        }

        @Override
        public String toString() {
            return t0
                    .replace("%1", String.valueOf(iterations))
                    .replace("%2", String.valueOf(totalTimeMs))
                    .replace("%3", String.valueOf(iterations > 0 ? totalTimeMs / iterations
                            : 0));
        }

    }

}