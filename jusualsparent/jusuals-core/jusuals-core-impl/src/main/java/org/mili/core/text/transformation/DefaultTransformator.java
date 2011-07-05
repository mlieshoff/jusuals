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

}
