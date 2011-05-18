/*
 * MockFactory.java
 *
 * 18.05.2011
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
package org.mili.core.text;

import java.util.*;


/**
 * @author Michael Lieshoff
 */
public class MockFactory {

    /** The date. */
    public final static Date DATE;

    static {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date(2000, 1, 1));
        DATE = calendar.getTime();
    }

    /**
     * Creates a new Mock object.
     *
     * @return the text table
     */
    public static TextTable createTextTable() {
        TextTable table = new TextTable();
        return fillTable(table);
    }

    /**
     * Creates a new Mock object.
     *
     * @return the XML
     */
    public static XML createXML() {
        XML table = new XML();
        return fillTable(table);
    }

    /**
     * Creates a new Mock object.
     *
     * @return the CSV
     */
    public static CSV createCSV() {
        CSV table = new CSV(";", "\"");
        return fillTable(table);
    }

    /**
     * Fill table.
     *
     * @param <T> the generic type
     * @param table the table
     * @return the t
     */
    public static <T extends Table> T fillTable(T table) {
        table.addCol("byte");
        table.addCol("short");
        table.addCol("integer");
        table.addCol("char");
        table.addCol("long");
        table.addCol("float");
        table.addCol("double");
        table.addCol("boolean");
        table.addCol("string", Flags.LEFT);
        table.addCol("foo");
        table.addCol("date", Conversions.DATE_TIME, TimeConversions.BEGINNING_MILLISECONDS,
                Flags.LEFT);
        table.addRow((byte) 1, (short) 1, 1, 'c', 1L, 1.0F, 1.0, Boolean.TRUE, "abbas",
                new Foo(), DATE);
        return table;
    }

    /**
     * Gets the csv string.
     *
     * @return the csv string
     */
    public static String getCsvString() {
        StringBuilder s = new StringBuilder();
        s.append("\"byte\";\"short\";\"integer\";\"char\";\"long\";\"float\";\"double\";\"boolean\";\"string\";\"foo\";\"date\"");
        s.append("\"1\";\"1\";\"1\";\"c\";\"1\";\"1.0\";\"1.0\";\"true\";\"abbas\";\"Foo\";\"Thu Feb 01 00:00:00 CET 3900\"");
        return s.toString();
    }

    /**
     * Gets the custom csv string.
     *
     * @return the custom csv string
     */
    public static String getCustomCsvString() {
        return getCsvString().replace(";", ",").replace("\"", "'");
    }

    /**
     * Gets the xml string.
     *
     * @return the xml string
     */
    public static String getXmlString() {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><table><header><cell>byte</cell><cell>short</cell><cell>integer</cell><cell>char</cell><cell>long</cell><cell>float</cell><cell>double</cell><cell>boolean</cell><cell>string</cell><cell>foo</cell><cell>date</cell></header><row><cell>1</cell><cell>1</cell><cell>1</cell><cell>c</cell><cell>1</cell><cell>1.0</cell><cell>1.0</cell><cell>true</cell><cell>abbas</cell><cell>Foo</cell><cell>Thu Feb 01 00:00:00 CET 3900</cell></row></table>");
        return s.toString();
    }

    /**
     * Gets the text string.
     *
     * @return the text string
     */
    public static String getTextString() {
        StringBuilder s = new StringBuilder();
        s.append("+------+-------+---------+------+------+----------+----------+---------+--------+-----+------------------------------+");
        s.append("| byte | short | integer | char | long |    float |   double | boolean | string | foo |                         date |");
        s.append("+------+-------+---------+------+------+----------+----------+---------+--------+-----+------------------------------+");
        s.append("|    1 |     1 |       1 |    c |    1 | 1,000000 | 1,000000 |    true | abbas  | Foo | 60907590000000               |");
        s.append("+------+-------+---------+------+------+----------+----------+---------+--------+-----+------------------------------+");
        return s.toString();
    }

    static class Foo {
        @Override
        public String toString() {
            return "Foo";
        }
    }

}
