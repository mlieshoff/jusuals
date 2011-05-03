package org.mili.core.benchmarking;

import java.util.*;

public class TestAnnotatedSequenceBench {

    private List<String> list = new ArrayList<String>();

    @Prepare
    public void add(String s) {
        this.list.add(s);
    }

    public String get(int i) {
        return this.list.get(i);
    }

}
