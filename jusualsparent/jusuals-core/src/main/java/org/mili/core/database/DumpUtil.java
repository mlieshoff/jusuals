/*
 * DumpUtil.java
 *
 * 15.09.2009
 *
 * Copyright 2009 Michael Lieshoff
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
package org.mili.core.database;

import java.sql.*;
import java.util.*;

import org.mili.core.collection.*;
import org.mili.core.text.*;
import org.mili.test.*;

/**
 * This utility class provides some useful methods for dumping.
 *
 * @author Michael Lieshoff
 */
public class DumpUtil implements DumpUtilInterface {

    private final static String SQL_SELECT_ALL = "select * from %1";

    /**
     * Gibt eine lesbare Darstellung der Tabellendaten wieder.
     *
     * @param c Connection.
     * @param tablename Tabellenname.
     * @return lesbare Darstellung.
     * @throws SQLException falls Fehler entstehen.
     */
    public static String tableToString(Connection c, String tablename) throws SQLException {
        return tableToString(c, tablename, null);
    }

    /**
     * Gibt eine lesbare Darstellung der Tabellendaten wieder.
     *
     * @param c Connection.
     * @param tablename Tabellenname.
     * @param columns Spalten.
     * @return lesbare Darstellung.
     * @throws SQLException falls Fehler entstehen.
     */
    public static String tableToString(Connection c, String tablename, String[] columns)
            throws SQLException {
        return tableToString(c, tablename, null, columns);
    }

    /**
     * Gibt eine lesbare Darstellung der Tabellendaten wieder.
     *
     * @param c Connection.
     * @param tablename Tabellenname.
     * @param where SQL-Where.
     * @param columns Spalten.
     * @return lesbare Darstellung.
     * @throws SQLException falls Fehler entstehen.
     */
    public static String tableToString(Connection c, String tablename, String where,
            String[] columns) throws SQLException {
        List<List<Object>> l = dumpTable(c, tablename, where, columns);
        return CollectionUtil.collectionOfCollectionToString(l, "%1", "%1", "%1", "%1", "\n",
                ", ");
    }

    /**
     * Gibt eine lesbare Darstellung der Tabellendaten in Form einer {@link TextTable} wieder.
     *
     * @param c {@link Connection}
     * @param tablename Tabellenname.
     * @param where SQL-Where.
     * @param columns Spalten.
     * @return {@link TextTable}
     * @throws SQLException falls Fehler entstehen.
     */
    public static Table tableToTextTable(Connection c, String tablename, String where,
            String... columns) throws SQLException {
        List<List<Object>> l = dumpTable(c, tablename, where, columns);
        return tableToTextTable(l);
    }

    public static Table tableToTextTable(List<List<Object>> l) throws SQLException {
        if (l.size() == 0) {
            return null;
        }
        Table t = new TextTable();
        // cols
        List<Object> l0 = l.get(1);
        for (int i = 0, n = l0.size(); i < n; i++) {
            String s = l0.get(i).toString();
            t = t.addCol(s);
        }
        // data
        for (int i = 2, n = l.size(); i < n; i++) {
            l0 = l.get(i);
            t.addRow(l0.toArray());
        }
        return t;
    }

    /**
     * Shows table at console.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @param where the where
     * @param columns the columns
     */
    public static void showTable(Connection c, String tablename, String where,
            String... columns) {
        Table t = null;
        try {
            t = DumpUtil.tableToTextTable(c, tablename, where, columns);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!c.isClosed()) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.valueOf(t));
    }

    /**
     * Dumpt eine Tabelle.
     *
     * @param c Connection.
     * @param tablename Tabellenname.
     * @return Dump.
     * @throws SQLException bei Fehlern.
     */
    public static List<List<Object>> dumpTable(Connection c, String tablename)
            throws SQLException {
        return dumpTable(c, tablename, null);
    }

    /**
     * Dumpt eine Tabelle.
     *
     * @param c Connection.
     * @param tablename Tabellenname.
     * @param columns Spalten.
     * @return Dump.
     * @throws SQLException bei Fehlern.
     */
    public static List<List<Object>> dumpTable(Connection c, String tablename, String[] columns)
            throws SQLException {
        return dumpTable(c, tablename, null, columns);
    }

    /**
     * Dumpt eine Tabelle.
     *
     * @param c Connection.
     * @param tablename Tabellenname.
     * @param where SQL-Where.
     * @param columns Spalten.
     * @return Dump.
     * @throws SQLException bei Fehlern.
     */
    public static List<List<Object>> dumpTable(Connection c, String tablename, String where,
            String[] columns) throws SQLException {
        String q = SQL_SELECT_ALL.replace("%1", tablename);
        if (where != null && where.length() > 0) {
            q += " where " + where;
        }
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(q);
        s.close();
        return dumpResultSet(rs, tablename, columns);
    }

    public static List<List<Object>> dumpResultSet(ResultSet rs, String tablename,
            String[] columns) throws SQLException {
        List<List<Object>> l = new ArrayList<List<Object>>();
        List<Object> t = new ArrayList<Object>();
        t.add(tablename);
        l.add(t);
        Map<String, Boolean> cs = new Hashtable<String, Boolean>();
        if (columns != null) {
            for (int i = 0, n = columns.length; i < n; i++) {
                cs.put(columns[i].toLowerCase(), true);
            }
        }
        ResultSetMetaData rsmd = rs.getMetaData();
        List<Object> h = new ArrayList<Object>();
        for (int i = 0, n = rsmd.getColumnCount(); i < n; i++) {
            int z = i + 1;
            String name = rsmd.getColumnName(z);
            boolean add = false;
            if (cs.size() > 0) {
                if (cs.get(name.toLowerCase()) != null) {
                    add = true;
                }
            } else {
                add = true;
            }
            if (add) {
                h.add(name);
            }
        }
        l.add(h);
        while (rs.next()) {
            List<Object> d = new ArrayList<Object>();
            for (int i = 0, n = rsmd.getColumnCount(); i < n; i++) {
                int z = i + 1;
                Object o = rs.getObject(z);
                String name = rsmd.getColumnName(z);
                boolean add = false;
                if (cs.size() > 0) {
                    if (cs.get(name.toLowerCase()) != null) {
                        add = true;
                    }
                } else {
                    add = true;
                }
                if (add) {
                    d.add(o);
                }
            }
            l.add(d);
        }
        rs.close();
        return l;
    }

    @Override
    public void printTable(Connection c, String tablename, String where, String... columns) {
        DumpUtil.showTable(c, tablename, where, columns);
    }

}
