/*
 * DefaultTransformator.java
 *
 * 14.10.2010
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

package org.mili.core.text.transformation;

import java.util.*;

import org.mili.core.text.*;


/**
 * This class is a default implementation of interface {@link Transformator}.
 *
 * @author Michael Lieshoff
 *
 */
public class DefaultTransformator implements Transformator {

    /**
     * Instantiates a new default transformator.
     */
    protected DefaultTransformator() {
        super();
    }

    /**
     * Creates the transformator.
     *
     * @return the default transformator
     */
    public static DefaultTransformator create() {
        return new DefaultTransformator();
    }

    @Override
    public Object transform(Table from, AvailableTransformations to, Object... params) {
        switch (to) {
        case CSV:
            return CsvTransformator.create().transform(from, params);
        case EXCEL:
            return ExcelTransformator.create().transform(from, params);
        case TABLE:
            return TableTransformator.create().transform(from, params);
        case XML:
            return XMLTransformator.create().transform(from, params);
        default:
            throw new UnsupportedOperationException("transform not supported: "
                    + from.getClass().getName() + " -> " + to);
        }
    }

    /**
     * Copy.
     *
     * @param from the from
     * @param to the to
     * @return the table
     */
    public static Table copy(Table from, Table to) {
        // header
        for (int i = 0, n = from.getColSize(); i < n; i++) {
            Col c = from.getCol(i);
            to.addCol(c.getName(), c.getConversion(), c.getDateTimeConversion(), c.getFlags());
        }
        // data
        for (int i = 0, n = from.getRowSize(); i < n; i++) {
            Row r = from.getRow(i);
            to.addRow(r.getValues());
        }
        return to;
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        Table t = new TextTable();
        t = t.addCol("char")
                .addCol("byte")
                .addCol("short")
                .addCol("int")
                .addCol("long")
                .addCol("float")
                .addCol("double")
                .addCol("boolean")
                .addCol("string")
                .addCol("object")
                .addCol("null")
                .addCol("PTimestamp")
                .addCol("Date")
                ;
        t.addRow('a', (byte) 1, (short) 1, 1, 1L, 1.0F, 1.0, true, "ab", new Object(), null,
                new Date(), new Date());
        t.addRow('a', (byte) 1, (short) 1, 10, 1L, 1.0F, 1.0, true, "abbas", new Object(), null,
                new Date(), new Date());
        t.addRow('a', (byte) 1, (short) 1, 100, 1L, 1.0F, 1.0, true, "sdhjsdhjdsghsdghds",
                new Object(), null, new Date(), new Date());
        System.out.println(DefaultTransformator.create().transform(t, AvailableTransformations.CSV));
        System.out.println(DefaultTransformator.create().transform(t, AvailableTransformations.XML));
        System.out.println(DefaultTransformator.create().transform(t, AvailableTransformations.EXCEL));
    }

}
