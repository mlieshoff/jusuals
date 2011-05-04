/*
 * TestAnnotatedSequenceBench.javaimport java.util.*;
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
package org.mili.core.benchmarking.experimental;

import java.util.*;

import org.mili.core.benchmarking.*;
import org.mili.core.benchmarking.experimental.*;

/**
 * @author Michael Lieshoff
 */
public class TestAnnotatedSequenceBench {
    private List<String> list = new ArrayList<String>();

    @Prepare(values={ValueType.AUTO})
    public void add(String s) {
        this.list.add(s);
    }

    @Execute
    public String get(int i) {
        return this.list.get(i);
    }

    public static void main(String[] args) {
        QuickBench.benchNxM(10, 50000, new long[] { 10, 100, 1000 }, new long[] { 10000,
                100000 }, new Bench[] {new AnnotatedBench(TestAnnotatedSequenceBench.class)});
    }
}
