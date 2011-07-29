package org.mili.core.profiling;

import org.apache.log4j.*;
import org.junit.*;


public class ThroughputAnalyzerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void should() throws Exception {
        BasicConfigurator.configure();
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                for(;;) {
                    ThroughputAnalyzer.getInstance().analyze("a1");
                    ThroughputAnalyzer.getInstance().analyze("a2");
                    ThroughputAnalyzer.getInstance().analyze("a3");
                    ThroughputAnalyzer.getInstance().analyze("a4");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                for(;;) {
                    ThroughputAnalyzer.getInstance().analyze("b1");
                    ThroughputAnalyzer.getInstance().analyze("b2");
                    ThroughputAnalyzer.getInstance().analyze("b3");
                    ThroughputAnalyzer.getInstance().analyze("b4");
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        return;
                    }
                    ThroughputAnalyzer.getInstance().showAsInfoLog();
                }
            }
        };

        Runnable r3 = new Runnable() {
            @Override
            public void run() {
                for(;;) {
                    ThroughputAnalyzer.getInstance().showAsInfoLog();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r3);
        t1.start();
        t2.start();
//        t3.start();

        Thread.sleep(1000);
    }


}
