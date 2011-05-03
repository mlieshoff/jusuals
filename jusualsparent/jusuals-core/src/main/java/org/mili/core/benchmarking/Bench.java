/*
 * Bench.java
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
 * Diese Schnittstelle beschreibt einen Benchmark.
 *
 * @author Michael Lieshoff
 * @version 1.0
 */
public interface Bench {

    /**
     * @return der Name.
     */
    String getName();

    /**
     * setzt den Benchmark und alle Resourcen, die er benutzt in den Urzustand zur&uuml;ck.
     */
    void reset();

    /**
     * Bereitet den Benchmark zur Ausf&uuml;hrung vor.
     *
     * @param lm Parameter anhand dessen der Benchmark vorbereitet wird.
     */
    void prepare(long lm);

    /**
     * F&uuml;hrt den Benchmark aus.
     *
     * @param lm Parameter anhand dessen der Benchmark ausgef&uuml;hrt wird.
     */
    void execute(long lm);
}