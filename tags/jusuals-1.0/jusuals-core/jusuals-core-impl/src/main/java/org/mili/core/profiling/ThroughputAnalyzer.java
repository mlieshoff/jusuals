/*
 * ThroughputAnalyzer
 *
 * 12.05.2010
 *
 * (c) by Michael Lieshoff
 *
 */

package org.mili.core.profiling;

import java.util.*;

import org.apache.commons.lang.*;
import org.mili.core.logging.*;


/**
 * This class defines a analyzer for throughput. With analyze() method an object can register
 * himself at many times. Every time registered some statistic data is collected for this
 * object. With some access methods, the collected data can be viewed on console or log, or in a
 * list.
 *
 * @author Michael Lieshoff
 */
public class ThroughputAnalyzer {
    private final static DefaultLogger log = DefaultLogger.getLogger(ThroughputAnalyzer.class);
    private Map<Object, ThroughputAnalyzerData> data =
            new Hashtable<Object, ThroughputAnalyzerData>();
    private final static ThroughputAnalyzer instance = new ThroughputAnalyzer();
    private final String GLOBAL_PATTERN = "%1$s - %2$s";
    static long MAX_MILLISECONDS = 60000;

    private ThroughputAnalyzer() {
        super();
    }

    /**
     * Gets the single instance of ThroughputAnalyzer.
     *
     * @return single instance of ThroughputAnalyzer
     */
    public static ThroughputAnalyzer getInstance() {
        return instance;
    }

    /**
     * Analyze.
     *
     * @param object the object
     */
    public void analyze(Object object) {
        Validate.notNull(object, "object cannot be null!");
        ThroughputAnalyzerData tad = this.data.get(object);
        if (tad == null) {
            tad = new ThroughputAnalyzerData();
        }
        tad.iterations ++;
        if (System.currentTimeMillis() - tad.ms >= MAX_MILLISECONDS) {
            tad.minuteCount ++;
            tad.iterationsPerMinute = tad.iterations - tad.totalIterations;
            tad.totalIterations = tad.iterations;
            log.info(String.valueOf(object), " - ", tad.iterationsPerMinute,
                    " iterations per minute[", tad.totalIterations, "].");
            tad.ms = System.currentTimeMillis();
        }
        this.data.put(object, tad);
    }

    /**
     * Show.
     *
     * @return the list
     */
    public List<String> show() {
        List<String> s = new ArrayList<String>();
        for (Iterator<Object> i = this.data.keySet().iterator(); i.hasNext(); ) {
            Object o = i.next();
            ThroughputAnalyzerData tda = this.data.get(o);
            s.add(String.format(GLOBAL_PATTERN, o, tda.toString()));
        }
        return s;
    }

    /**
     * Show as info log.
     */
    public void showAsInfoLog() {
        for (Iterator<Object> i = this.data.keySet().iterator(); i.hasNext(); ) {
            Object o = i.next();
            ThroughputAnalyzerData data = this.data.get(o);
            log.info(String.format(GLOBAL_PATTERN, o, data.toString()));
        }
    }

    class ThroughputAnalyzerData {
        final static String PATTERN = "%1$d minutes - %2$d iterations per minute - %3$d total "
                + "iterations in time - %4$d maximal amount of iterations. [%5$s]";
        int iterations = 0;
        int totalIterations = 0;
        int iterationsPerMinute = 0;
        int minuteCount = 0;
        MemoryInfo mi = new MemoryInfo();
        long ms = System.currentTimeMillis();

        @Override
        public String toString() {
            return String.format(PATTERN, minuteCount, iterationsPerMinute, totalIterations,
                    iterations, mi.toString());
        }

    }

    class MemoryInfo {
        final static long MB = 1024*1024;
        final static String PATTERN = "%1$dMB/%2$dMB allocated of maximal memory %3$dMB";
        private Runtime r = Runtime.getRuntime();
        long freeMemory = r.freeMemory()/MB;
        long totalMemory = r.totalMemory()/MB;
        long maxMemory = r.maxMemory()/MB;
        @Override
        public String toString() {
            return String.format(PATTERN, freeMemory, totalMemory, maxMemory);
        }
    }

}