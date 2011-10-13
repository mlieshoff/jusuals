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
 * <p><b>Introduction</b><p>
 *
 * <u> What is QuickBench?</u><p>
 *
 * QuickBench is a lightweight simple and easy micro-benchmark system for Java. There
 * is no need of installation, all is containing in the library jusuals-core in packages
 * &quot;org.mili.core.benchmarking&quot;. It's containing an short interface for benchmarks an
 * abstract class where some methods already defined and the QuickBench class
 * containing the whole system.<p>
 *
 * <u> Future of QuickBench</u><p>
 *
 * QuickBench is already extended by more functionality. Next plans would try to
 * integrate this system as part of jMibs, so be prepared for a little package
 * movement, but the functionalty and simpleness would be always the same.<p>
 * <b>Usage</b><p>
 *
 * <u> Two kinds of benchmarks</u><p>
 *
 * Two kinds of benchmarks are supported. The NxM benches and the N-Intervall
 * benches. NxM benches executes something n times with m objects. It's nice to use it
 * for traversal some data structures and so on. You can execute the get(index) method
 * n times on a list containing m objects.<br>
 * The N-Intervall benches is for working with functions, e.g. the Fibonacci numbers
 * or the faculty. We execute the fib(number) method n times for all the values exists
 * in the intervall.<p>
 *
 * <u> Lifecycle</u><p>
 *
 * If QuickBench is started with benchNxIntervall() or benchNxM() method, it will
 * warmup the benches first. Warmup will execute reset(), prepare() and execute()
 * methods of all defined benches a defined times with a minium count.<br>
 * Since warmed up, every benches will be executed in their way depending of the type
 * NxM oder NxIntervall. Intervall benches will be resetted before each execution but
 * not prepared. NxM benches will be resetted and prepared before each execution.<p>
 * <b>Examples</b><p>
 *
 * <u> NxM</u><p>
 *
 * Simple start with writing a class with a main() method.<br>
 * <pre>
 * public class NxMTests {
 *     public static void main(String[] args) {
 *         QuickBench.benchNxM(
 *                 10,                                      // minimum millis to display nanos
 *                 50000,                                   // warmup times
 *                 new long[] { 10, 100, 1000 },            // n-iteration
 *                 new long[] { 10000, 100000 },            // m-objects
 *                 new Bench[] {                            // the benches
 *                     new ListTraverseBench_HighSpeed(),   // list traversing with high-speed idiom
 *                     new ListTraverseBench_ForEach()      // list traversing with built-in for-each
 *                 });
 *     }
 * }
 * </pre>
 * Then write the two benchmarks.<br>
 * <pre>
 * public class ListTraverseBench_HighSpeed extends AbstractBench {
 *     List&lt;String&gt; l = null;
 *
 *     &#064;Override
 *     public String getName() {
 *         return &quot;Traverse: HighSpeed&quot;;
 *     }
 *
 *     &#064;Override
 *     public void reset() {
 *         l = new ArrayList&lt;String&gt;();
 *     }
 *
 *     &#064;Override
 *     public void prepare(long lm) {
 *         for (int i = 0; i &lt; lm; i++) {
 *             l.add(String.valueOf(i));
 *         }
 *     }
 *
 *     &#064;Override
 *     public void execute(long lm) {
 *         String s = null;
 *         for (int i = 0, n = l.size(); i &lt; n; i++) {
 *             s = l.get(i);
 *         }
 *     }
 * };
 * </pre>
 * <pre>
 * public class ListTraverseBench_ForEach extends AbstractBench {
 *     List&lt;String&gt; l = null;
 *
 *     public ListTraverseBench_ForEach() {
 *         super();
 *     }
 *
 *     &#064;Override
 *     public String getName() {
 *         return &quot;Traverse: ForEach&quot;;
 *     }
 *
 *     &#064;Override
 *     public void reset() {
 *         l = new ArrayList&lt;String&gt;();
 *     }
 *
 *     &#064;Override
 *     public void prepare(long lm) {
 *         for (int i = 0; i &lt; lm; i++) {
 *             l.add(String.valueOf(i));
 *         }
 *     }
 *
 *     &#064;Override
 *     public void execute(long lm) {
 *         for (String s : l) {
 *         }
 *     }
 * };
 * </pre>
 * Then execute the class NxMTests to start the QuickBench system. The summary will be
 * generated while the benches are executed. So you can view the results immediatly.<br>
 * <pre>
 * +---------------------------+----------+------------+----------+----------+-----------------+-----------------+
 * | Bench                     |   Rounds |    Objects |       ms | ms (avg) |              nt |        nt (avg) |
 * +---------------------------+----------+------------+----------+----------+-----------------+-----------------+
 * | Traverse: HighSpeed       |       10 |     10.000 |        2 |        0 |       1.035.210 |         103.521 |
 * | Traverse: HighSpeed       |      100 |     10.000 |        5 |        0 |       5.326.235 |          53.262 |
 * | Traverse: HighSpeed       |    1.000 |     10.000 |       56 |        0 |             ... |             ... |
 * | Traverse: HighSpeed       |       10 |    100.000 |       15 |        1 |             ... |             ... |
 * | Traverse: HighSpeed       |      100 |    100.000 |      142 |        1 |             ... |             ... |
 * | Traverse: HighSpeed       |    1.000 |    100.000 |    1.737 |        1 |             ... |             ... |
 * | Traverse: ForEach         |       10 |     10.000 |        1 |        0 |         604.127 |          60.412 |
 * | Traverse: ForEach         |      100 |     10.000 |        4 |        0 |       6.911.349 |          69.113 |
 * | Traverse: ForEach         |    1.000 |     10.000 |       64 |        0 |             ... |             ... |
 * | Traverse: ForEach         |       10 |    100.000 |       15 |        1 |             ... |             ... |
 * | Traverse: ForEach         |      100 |    100.000 |      212 |        2 |             ... |             ... |
 * | Traverse: ForEach         |    1.000 |    100.000 |    1.796 |        1 |             ... |             ... |
 * +---------------------------+----------+------------+----------+----------+-----------------+-----------------+
 * java version 1.6.0_22
 * OpenJDK Runtime Environment(build 1.6.0_22-b22)
 * OpenJDK Server VM(build 20.0-b11, mixed mode)
 * Linux(2.6.38-8-generic-pae) i386
 * Max memory: 858MB
 * </pre>
 * The benches are executed 10, 100 and 1.000 times, depends on 10.000 and 100.000
 * objects in the array list. The ... display that 10 milliseconds are defined to skip
 * display of nanos. Ms displays the time in milli seconds token and ms(avg) the average
 * time. Nt is the nano time and nt(avg) the average nano time.<p>
 *
 * <u> NxIntervall</u><p>
 *
 * Now continue with writing a new class with a main() method.<br>
 * <pre>
 * public class NxIntervallTests {
 *     public static void main(String[] args) {
 *         QuickBench.benchNxIntervall(
 *                 10,                                      // minimum millis to display nanos
 *                 50000,                                   // warmup times
 *                 new long[] { 10, 100, 1000},             // n-iterations
 *                 1,                                       // intervall start
 *                 25,                                      // intervall end
 *                 new Bench[]{                             // the benches
 *                     new FibBench_Recursive(),            // Fibonacci recursive
 *                     new FibBench_NonRecursive()          // Fibonaccie non-recursive
 *                 });
 *     }
 * }
 * </pre>
 * Then write the two benchmarks.<br>
 * <pre>
 * public class FibBench_Recursive extends AbstractBench {
 *     private int fib(int a) {
 *         if (a == 1 || a == 2) {
 *             return 1;
 *         } else {
 *             return fib(a - 1) + fib(a - 2);
 *         }
 *     }
 *
 *     &#064;Override
 *     public String getName() {
 *         return &quot;Fibonacci: recursive&quot;;
 *     }
 *
 *     &#064;Override
 *     public void prepare(long lm) {
 *         this.fib(1);
 *         this.fib(2);
 *         this.fib(3);
 *     }
 *
 *     &#064;Override
 *     public void execute(long lm) {
 *         this.fib((int) lm);
 *     }
 * };
 * </pre>
 * <pre>
 * public class FibBench_NonRecursive extends AbstractBench {
 *     private long fib(int a) {
 *         long fib = 1;
 *         for (long fib1 = 1, fib2 = 1, i = 3; i &lt;= a; i++) {
 *             fib = fib1 + fib2;
 *             fib1 = fib2;
 *             fib2 = fib;
 *         }
 *         return fib;
 *     }
 *
 *     &#064;Override
 *     public String getName() {
 *         return &quot;Fibonacci: non-recursive&quot;;
 *     }
 *
 *     &#064;Override
 *     public void prepare(long lm) {
 *         this.fib(1);
 *         this.fib(2);
 *         this.fib(3);
 *     }
 *
 *     &#064;Override
 *     public void execute(long lm) {
 *         this.fib((int) lm);
 *     }
 * };
 * </pre>
 * Then execute the class NxIntervalTests like before.<br>
 * <pre>
 * +---------------------------+----------+------------+------------+----------+----------+-----------------+-----------------+
 * | Bench                     |   Rounds |        min |        max |       ms | ms (avg) |              nt |        nt (avg) |
 * +---------------------------+----------+------------+------------+----------+----------+-----------------+-----------------+
 * | Fibonacci: recursive      |       10 |          1 |         25 |       10 |        1 |             ... |             ... |
 * | Fibonacci: recursive      |      100 |          1 |         25 |       89 |        0 |             ... |             ... |
 * | Fibonacci: recursive      |    1.000 |          1 |         25 |      803 |        0 |             ... |             ... |
 * | Fibonacci: non-recursive  |       10 |          1 |         25 |        0 |        0 |         122.255 |          12.225 |
 * | Fibonacci: non-recursive  |      100 |          1 |         25 |        2 |        0 |       1.077.814 |          10.778 |
 * | Fibonacci: non-recursive  |    1.000 |          1 |         25 |       45 |        0 |             ... |             ... |
 * +---------------------------+----------+------------+------------+----------+----------+-----------------+-----------------+
 * java version 1.6.0_22
 * OpenJDK Runtime Environment(build 1.6.0_22-b22)
 * OpenJDK Server VM(build 20.0-b11, mixed mode)
 * Linux(2.6.38-8-generic-pae) i386
 * Max memory: 858MB
 * </pre>
 * The benches are executed 10, 100 and 1.000 times, depends on intervall 1 to 25.
 * Other fields are like before.<p>
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