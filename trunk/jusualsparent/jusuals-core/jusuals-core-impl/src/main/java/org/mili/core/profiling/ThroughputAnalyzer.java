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

import org.mili.core.logging.*;
import org.mili.core.text.*;


/**
 * @author Michael Lieshoff
 */
public class ThroughputAnalyzer {
    private final static DefaultLogger log = DefaultLogger.getLogger(ThroughputAnalyzer.class);
    private Map<Object, ThroughputAnalyzerData> data =
            new Hashtable<Object, ThroughputAnalyzerData>();
    private final static ThroughputAnalyzer instance = new ThroughputAnalyzer();

    private ThroughputAnalyzer() {
        super();
    }

    public static ThroughputAnalyzer getInstance() {
        return instance;
    }

    public void analyze(Object o) {
        ThroughputAnalyzerData tad = this.data.get(o);
        if (tad == null) {
            tad = new ThroughputAnalyzerData();
        }
        tad.iterations ++;
        if (System.currentTimeMillis() - tad.ms >= 60000) {
            tad.minuteCount ++;
            tad.iterationsPerMinute = tad.iterations - tad.totalIterations;
            tad.totalIterations = tad.iterations;
            log.info(String.valueOf(o), " - ", tad.iterationsPerMinute,
                    " iterations per minute[", tad.totalIterations, "].");
            tad.ms = System.currentTimeMillis();
        }
        this.data.put(o, tad);
    }

    public List<String> show() {
        List<String> s = new ArrayList<String>();
        for (Iterator<Object> i = this.data.keySet().iterator(); i.hasNext(); ) {
            Object o = i.next();
            ThroughputAnalyzerData tda = this.data.get(o);
            s.add(String.valueOf(o).concat(" - ").concat(tda.toString()));
        }
        return s;
    }

    public void showAsInfoLog() {
        Table table = new TextTable();
        table.addCol("object");
        table.addCol("minutes");
        table.addCol("iterations min");
        table.addCol("iterations total");
        table.addCol("iterations max");
        table.addCol("mem");
        for (Iterator<Object> i = this.data.keySet().iterator(); i.hasNext(); ) {
            Object o = i.next();
            ThroughputAnalyzerData data = this.data.get(o);
            table.addRow(o, data.minuteCount, data.iterationsPerMinute, data.totalIterations, data.iterations, data.mi);
        }
        System.out.println(table);
        return ;
    }

    class ThroughputAnalyzerData {
        final static String t0 = "%1$d minutes - %2$d iterations per minute - %3$d total "
                + "iterations in time - %4$d maximal amount of iterations. [%5$s]";
        int iterations = 0;
        int totalIterations = 0;
        int iterationsPerMinute = 0;
        int minuteCount = 0;
        MemoryInfo mi = new MemoryInfo();
        long ms = System.currentTimeMillis();

        @Override
        public String toString() {
            return String.format(t0, minuteCount, iterationsPerMinute, totalIterations,
                    iterations, mi.toString());
        }

    }

    class MemoryInfo {
        final static long MB = 1024*1024;
        final static String t0 = "%1$dMB/%2$dMB allocated of maximal memory %3$dMB";
        private Runtime r = Runtime.getRuntime();
        long freeMemory = r.freeMemory()/MB;
        long totalMemory = r.totalMemory()/MB;
        long maxMemory = r.maxMemory()/MB;
        @Override
        public String toString() {
            return String.format(t0, freeMemory, totalMemory, maxMemory);
        }
    }

}