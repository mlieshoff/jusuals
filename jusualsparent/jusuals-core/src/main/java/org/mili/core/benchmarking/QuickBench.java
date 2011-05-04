/*
 * QuickBench.java
 *
 * 03.06.2010
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

package org.mili.core.benchmarking;


/**
 * This class defines a quick and simple benchmark system.
 *
 * @author Michael Lieshoff
 */
public class QuickBench {
    private final static String headerNxMSep = "+---------------------------+----------+-------"
            + "-----+----------+----------+-----------------+-----------------+";
    private final static String headerNxM = "| %-25s | %8s | %10s | %8s | %8s | %15s | %15s "
            + "|\n";
    private final static String headerNxIntervallSep = "+---------------------------+----------"
            + "+------------+------------+----------+----------+-----------------+-------------"
            + "----+";
    private final static String headerNxIntervall = "| %-25s | %8s | %10s | %10s | %8s | %8s | "
            + "%15s | %15s |\n";
    private final static String patternNxM = "| %-25s | %,8d | %,10d | %,8d | %,8d | %,15d | "
            + "%,15d |\n";
    private final static String patternNxMWNT = "| %-25s | %,8d | %,10d | %,8d | %,8d | %15s | "
            + "%15s |\n";
    private final static String patternNxIntervall = "| %-25s | %,8d | %,10d | %,10d | %,8d | "
            + "%,8d | %,15d | %,15d |\n";
    private final static String patternNxIntervallWNT = "| %-25s | %,8d | %,10d | %,10d | %,8d "
            + "| %,8d | %15s | %15s |\n";

    /**
     * Instantiates a new quick bench.
     */
    private QuickBench() {
    }

    /**
     * This method is used to bench mathematically functions based on interval inputs, like
     * fibonacci, faculty and so on. First the benches will be warmed up, then they will be
     * resetted but not prepared. All benches will be repeated n times for a value stored in
     * interval.
     *
     * @param minMs minimal nano seconds to strip display
     * @param wm count warm up times
     * @param n count of iterations
     * @param mmin interval minimum
     * @param mmax interval maximum
     * @param ba benches
     */
    public static void benchNxIntervall(long minMs, long wm, long[] n, long mmin, long mmax,
            Bench[] ba) {
        QuickBench qb = new QuickBench();
        qb.warmup(ba, wm);
        System.out.println(headerNxIntervallSep);
        System.out.printf(headerNxIntervall, "Bench", "Rounds", "min", "max", "ms",
                "ms (avg)", "nt", "nt (avg)");
        System.out.println(headerNxIntervallSep);
        for (int i = 0; i < ba.length; i++) {
            Bench b = ba[i];
            qb.executeIntervall(b, n, minMs, mmin, mmax);
        }
        System.out.println(headerNxIntervallSep);
        qb.showInfo();
    }

    /**
     * This method executes benches that bases on a count of objects, like collections and so
     * on. Benches will be warmed up first. Then a bench will be resetted and prepared for a
     * value from m. The count of iterations and the number of object count must be defined. For
     * each entry in m a bench will be executed n times.
     *
     * @param minMs minimal nano seconds to strip display
     * @param wm count warm up times
     * @param n count of iterations
     * @param m count of objects
     * @param ba benches
     */
    public static void benchNxM(long minMs, long wm, long[] n, long[] m, Bench[] ba) {
        QuickBench qb = new QuickBench();
        qb.warmup(ba, wm);
        System.out.println(headerNxMSep);
        System.out.printf(headerNxM, "Bench", "Rounds", "Objects", "ms", "ms (avg)",
                "nt", "nt (avg)");
        System.out.println(headerNxMSep);
        for (int i = 0; i < ba.length; i++) {
            Bench b = ba[i];
            for (int ii = 0; ii < m.length; ii++) {
                qb.execute(b, n, minMs, m[ii]);
            }
        }
        System.out.println(headerNxMSep);
        qb.showInfo();
    }

    private void showInfo() {
        System.out.println("java version " + System.getProperty("java.version"));
        System.out.println(System.getProperty("java.runtime.name") + "(build " +
                System.getProperty("java.runtime.version") + ")");
        System.out.println(System.getProperty("java.vm.name") + "(build "
                + System.getProperty("java.vm.version") + ", "
                + System.getProperty("java.vm.info") + ")");
        System.out.println(System.getProperty("os.name") + "("
                + System.getProperty("os.version") + ") " + System.getProperty("os.arch"));
        System.out.println("Max memory: " + Runtime.getRuntime().maxMemory() / (1024 * 1024)
                + "MB");
    }

    private void warmup(Bench[] ba, long n) {
        for (int i = 0; i < n; i++) {
            for (int ii = 0; ii < ba.length; ii++) {
                Bench b = ba[ii];
                b.reset();
                b.prepare(1);
                b.execute(1);
            }
        }
    }

    private void executeIntervall(Bench b, long[] n, long minMs, long mmin, long mmax) {
        for (int i = 0; i < n.length; i++) {
            long ln = n[i];
            long ms = 0;
            long nt = 0;
            long ms0 = 0;
            long nt0 = 0;
            for (int ii = 0; ii < ln; ii++) {
                for (long l = mmin; l < mmax; l++) {
                    b.reset();
                    ms0 = -System.currentTimeMillis();
                    nt0 = -System.nanoTime();
                    b.execute(l);
                    nt0 += System.nanoTime();
                    ms0 += System.currentTimeMillis();
                    ms += ms0;
                    nt += nt0;
                }
            }
            if (ms < minMs) {
                System.out.printf(patternNxIntervall, b.getName(), ln, mmin, mmax, ms, ms / ln,
                        nt, (nt / ln));
            } else {
                System.out.printf(patternNxIntervallWNT, b.getName(), ln, mmin, mmax, ms,
                        ms / ln, "...", "...");
            }
        }
    }

    private void execute(Bench b, long[] n, long minMs, long val) {
        for (int i = 0; i < n.length; i++) {
            long ln = n[i];
            long ms = 0;
            long nt = 0;
            long ms0 = 0;
            long nt0 = 0;
            for (int ii = 0; ii < ln; ii++) {
                b.reset();
                b.prepare(val);
                ms0 = -System.currentTimeMillis();
                nt0 = -System.nanoTime();
                b.execute(val);
                nt0 += System.nanoTime();
                ms0 += System.currentTimeMillis();
                ms += ms0;
                nt += nt0;
            }
            if (ms < minMs) {
                System.out.printf(patternNxM, b.getName(), ln, val, ms, ms / ln, nt, (nt / ln));
            } else {
                System.out.printf(patternNxMWNT, b.getName(), ln, val, ms, ms / ln, "...",
                        "...");
            }
        }
    }
}