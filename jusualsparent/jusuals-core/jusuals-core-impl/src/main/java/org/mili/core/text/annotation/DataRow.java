/*
 * DataRow.java
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
package org.mili.core.text.annotation;

import java.lang.annotation.*;
import java.util.*;

import org.mili.core.annotation.*;

/**
 * This class defines a data row.
 *
 * @author Michael Lieshoff
 */
public abstract class DataRow<T> {
    private final List<Object> values = new ArrayList<Object>();
    private final int no;

    protected DataRow(int no, final T msg) {
        this.no = no;
        values.add(no);
        AnnotationSolver solver = new ClassAnnotationSolver();
        solver.addAnnotationHandler(Mapping.class, new AnnotationHandler<Class>() {
            public void handle(Annotation annotation, Class source) {
                if (annotation instanceof Mapping) {
                    Mapping mapping = (Mapping) annotation;
                    for (Data data : mapping.elements()) {
                        Object value = null;
                        if (data.type() == Integer.class) {
                            value = getInt(msg, data.from());
                        } else if (data.type() == Long.class) {
                            value = getLong(msg, data.from());
                        } else {
                            value = get(msg, data.from());
                        }
                        values.add(value);
                    }
                }
            }
        });
        solver.solve(this.getClass());
    }

    /**
     * Gets the.
     *
     * @param msg the msg
     * @param key the key
     * @return the string
     */
    protected abstract String get(T msg, String key);

    /**
     * Gets the int.
     *
     * @param msg the msg
     * @param key the key
     * @return the int
     */
    protected abstract int getInt(T msg, String key);

    /**
     * Gets the long.
     *
     * @param msg the msg
     * @param key the key
     * @return the long
     */
    protected abstract long getLong(T msg, String key);

    /**
     * Gets the header values.
     *
     * @return the header values
     */
    public String[] getHeaderValues() {
        final List<String> list = new ArrayList<String>();
        list.add("no.");
        AnnotationSolver solver = new ClassAnnotationSolver();
        solver.addAnnotationHandler(Mapping.class, new AnnotationHandler<Class>() {
            public void handle(Annotation annotation, Class source) {
                if (annotation instanceof Mapping) {
                    Mapping mapping = (Mapping) annotation;
                    Data[] dataArray = mapping.elements();
                    for (int i = 0, n = dataArray.length; i < n; i++) {
                        list.add(dataArray[i].displayName());
                    }
                }
            }
        });
        solver.solve(this.getClass());
        return list.toArray(new String[] {});
    }

    /**
     * Gets the values.
     *
     * @return the values
     */
    public Object[] getValues() {
        final Object[] list = new Object[values.size()];
        list[0] = no;
        AnnotationSolver solver = new ClassAnnotationSolver();
        solver.addAnnotationHandler(Mapping.class, new AnnotationHandler<Class>() {
            public void handle(Annotation annotation, Class source) {
                if (annotation instanceof Mapping) {
                    Mapping mapping = (Mapping) annotation;
                    Map<String, Formatter> formatters = new HashMap<String, Formatter>();
                    for (Format format : mapping.formatters()) {
                        try {
                            formatters.put(format.name(), format.formatClass().newInstance());
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    int i = 1;
                    for (Data data : mapping.elements()) {
                        Object value = values.get(i);
                        String key = data.displayName();
                        if (formatters.containsKey(key)) {
                            Formatter formatter = formatters.get(key);
                            value = formatter.format(value);
                        }
                        list[i] = value;
                        i++;
                    }
                }
            }
        });
        solver.solve(this.getClass());
        return list;
    }

    /**
     * Gets the widths.
     *
     * @return the widths
     */
    public int[] getWidths() {
        final List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        AnnotationSolver solver = new ClassAnnotationSolver();
        solver.addAnnotationHandler(Mapping.class, new AnnotationHandler<Class>() {
            public void handle(Annotation annotation, Class source) {
                if (annotation instanceof Mapping) {
                    Mapping mapping = (Mapping) annotation;
                    Data[] dataArray = mapping.elements();
                    for (int i = 0, n = dataArray.length; i < n; i++) {
                        list.add(dataArray[i].width());
                    }
                }
            }
        });
        solver.solve(this.getClass());
        int[] result = new int[list.size()];
        for(int i = 0, n = list.size(); i < n; i ++) {
            result[i] = list.get(i);
        }
        return result;
    }

}
