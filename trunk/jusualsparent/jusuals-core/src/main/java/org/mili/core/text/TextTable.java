/*
 * TextTable.java
 *
 * 16.06.2010
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
package org.mili.core.text;

import java.util.*;

/**
 * This class is a default implementation of interface {@link Table}.
 *
 * @author Michael Lieshoff
 */
public class TextTable implements Table {
    private List<Col> cols = new ArrayList<Col>();
    private List<Row> rows = new ArrayList<Row>();

    /**
     * Instantiates a new table.
     */
    public TextTable() {
        super();
    }

    @Override
    public Table addCol(String n) {
        this.cols.add(new DefaultCol(n, null));
        return this;
    }

    /**
     * Adds the col.
     *
     * @param name the name
     * @param flags the flags
     * @return the i table
     */
    public Table addCol(String name, Flag... flags) {
        this.cols.add(new DefaultCol(name, null, flags));
        return this;
    }

    /**
     * Adds the col.
     *
     * @param name the name
     * @param conversion the conversion
     * @param flags the flags
     * @return the i table
     */
    public Table addCol(String name, Conversion conversion, Flag... flags) {
        this.cols.add(new DefaultCol(name, conversion, flags));
        return this;
    }

    @Override
    public Table addCol(String name, Conversion conversion, DTConversion dtconversion,
            Flag... flags) {
        this.cols.add(new DefaultCol(name, conversion, dtconversion, flags));
        return this;
    }

    @Override
    public int getColSize() {
        return this.cols.size();
    }

    @Override
    public Col getCol(int index) {
        return this.cols.get(index);
    }

    @Override
    public int getRowSize() {
        return this.rows.size();
    }

    @Override
    public Row getRow(int index) {
        return this.rows.get(index);
    }

    @Override
    public void addRow(Object... values) {
        this.rows.add(new DefaultRow(values));
    }

    @Override
    public String toString() {
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        // data
        for (int i = 0, n = this.rows.size(); i < n; i++) {
            Row r = this.rows.get(i);
            for (int ii = 0, nn = this.cols.size(); ii < nn; ii++) {
                Col c = this.cols.get(ii);
                Object o = r.getValue(ii);
                if (c.getConversion() == null) {
                    c.setConversion(this.getConversionForType(o));
                }
                int nl = Math.max(c.getName().length(), String.valueOf(o).length());
                if (nl > c.getWidth()) {
                    c.setWidth(nl);
                }
                int ol = String.format(this.getToken(o, c.getWidth(), c.getConversion(),
                        c.getDateTimeConversion(), c.getFlags()), o).length();
                if (ol > c.getWidth()) {
                    c.setWidth(ol);
                }
            }
        }
        for (int i = 0, n = this.rows.size(); i < n; i++) {
            Row r = this.rows.get(i);
            StringBuilder data = new StringBuilder();
            for (int ii = 0, nn = this.cols.size(); ii < nn; ii++) {
                Col c = this.cols.get(ii);
                Object o = r.getValue(ii);
                data.append("| ");
                data.append(this.getToken(o, c.getWidth(), c.getConversion(),
                        c.getDateTimeConversion(), c.getFlags()));
                if (ii < nn - 1) {
                    data.append(" ");
                }
            }
            data.append(" |\n");
            s2.append(String.format(data.toString(), r.getValues()));
        }
        // head
        StringBuilder sep = new StringBuilder();
        StringBuilder begin = new StringBuilder();
        StringBuilder end = new StringBuilder();
        StringBuilder head = new StringBuilder();
        Object[] oa = new Object[this.cols.size()];
        for (int i = 0, n = this.cols.size(); i < n; i++) {
            Col c = this.cols.get(i);
            oa[i] = c.getName();
            if (i == 0) {
                begin.append("+");
                end.append("+");
                sep.append("+");
            } else {
                begin.append("+");
                end.append("+");
                sep.append("+");
            }
            head.append("| %");
            head.append(String.valueOf(c.getWidth()));
            head.append("s");
            for (int ii = 0; ii < c.getWidth() + 2; ii++) {
                begin.append("-");
                sep.append("-");
                end.append("-");
            }
            if (i < n - 1) {
                head.append(" ");
            }
        }
        begin.append("+\n");
        sep.append("+\n");
        end.append("+\n");
        head.append(" |\n");
        s1.append(begin);
        s1.append(String.format(head.toString(), oa));
        s1.append(sep);
        s1.append(s2);
        s1.append(end);
        return s1.toString();
    }

    /**
     * Gets the token.
     *
     * @param object the object
     * @param width the width
     * @param conversion the conversion
     * @param dtconversion the dtconversion
     * @param flags the flags
     * @return the token
     */
    protected String getToken(Object object, int width, Conversion conversion,
            DTConversion dtconversion, Flag... flags) {
        StringBuilder s = new StringBuilder();
        s.append("%");
        if (flags != null && flags.length > 0) {
            for (int i = 0; i < flags.length; i++) {
                Flag f = flags[i];
                s.append(f.getToken());
            }
        }
        if (width > 0) {
            s.append(width);
        }
        if (conversion != null) {
            s.append(conversion.getToken());
        } else {
            s.append(this.getConversionForType(object).getToken());
        }
        if (dtconversion != null) {
            s.append(dtconversion.getToken());
        }
        return s.toString();
    }

    /**
     * Gets the conversion for type.
     *
     * @param object the object
     * @return the conversion for type
     */
    protected Conversion getConversionForType(Object object) {
        if (object instanceof Byte) {
            return Conversions.DECIMAL;
        } else if (object instanceof Short) {
            return Conversions.DECIMAL;
        } else if (object instanceof Integer) {
            return Conversions.DECIMAL;
        } else if (object instanceof Character) {
            return Conversions.CHARACTER;
        } else if (object instanceof Long) {
            return Conversions.DECIMAL;
        } else if (object instanceof Float) {
            return Conversions.FLOAT;
        } else if (object instanceof Double) {
            return Conversions.FLOAT;
        } else if (object instanceof Boolean) {
            return Conversions.BOOLEAN;
        } else if (object instanceof String) {
            return Conversions.STRING;
        } else {
            return Conversions.STRING;
        }
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
        System.out.println(t);
    }

}