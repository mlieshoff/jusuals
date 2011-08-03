/*
 * ThroughputAnalayzerTest.java
 *
 * 01.08.2011
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

package org.mili.core.profiling;

import org.apache.log4j.*;
import org.junit.*;


/**
 * @author Michael Lieshoff
 */
public class ThroughputAnalyzerTest {

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
