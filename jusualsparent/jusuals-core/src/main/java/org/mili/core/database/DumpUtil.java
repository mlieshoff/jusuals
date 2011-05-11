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
     * Instantiates a new dump util.
     */
    public DumpUtil() {
    }

    /**
     * Table to string.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @return the string
     * @throws SQLException the sQL exception
     */
    public static String tableToString(Connection connection, String tablename)
            throws SQLException {
        return tableToString(connection, tablename, null);
    }

    /**
     * Table to string.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @param columns the columns
     * @return the string
     * @throws SQLException the sQL exception
     */
    public static String tableToString(Connection connection, String tablename,
            String[] columns) throws SQLException {
        return tableToString(connection, tablename, null, columns);
    }

    /**
     * Table to string.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @param where the where
     * @param columns the columns
     * @return the string
     * @throws SQLException the sQL exception
     */
    public static String tableToString(Connection connection, String tablename, String where,
            String[] columns) throws SQLException {
        List<List<Object>> l = dumpTable(connection, tablename, where, columns);
        return CollectionUtil.collectionOfCollectionToString(l, "%1", "%1", "%1", "%1", "\n",
                ", ");
    }

    /**
     * Table to text table.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @param where the where
     * @param columns the columns
     * @return the table
     * @throws SQLException the sQL exception
     */
    public static Table tableToTextTable(Connection connection, String tablename, String where,
            String... columns) throws SQLException {
        List<List<Object>> l = dumpTable(connection, tablename, where, columns);
        return tableToTextTable(l);
    }

    /**
     * Table to text table.
     *
     * @param list the list
     * @return the table
     * @throws SQLException the sQL exception
     */
    public static Table tableToTextTable(List<List<Object>> list) throws SQLException {
        if (list.size() < 3) {
            return null;
        }
        Table t = new TextTable();
        // cols
        List<Object> l0 = list.get(1);
        for (int i = 0, n = l0.size(); i < n; i++) {
            String s = l0.get(i).toString();
            t = t.addCol(s);
        }
        // data
        for (int i = 2, n = list.size(); i < n; i++) {
            l0 = list.get(i);
            t.addRow(l0.toArray());
        }
        return t;
    }

    /**
     * Show table.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @param where the where
     * @param columns the columns
     */
    public static void showTable(Connection connection, String tablename, String where,
            String... columns) {
        Table t = null;
        try {
            t = DumpUtil.tableToTextTable(connection, tablename, where, columns);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(String.valueOf(t));
    }

    /**
     * Dump table.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @return the list
     * @throws SQLException the sQL exception
     */
    public static List<List<Object>> dumpTable(Connection connection, String tablename)
            throws SQLException {
        return dumpTable(connection, tablename, null);
    }

    /**
     * Dump table.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @param columns the columns
     * @return the list
     * @throws SQLException the sQL exception
     */
    public static List<List<Object>> dumpTable(Connection connection, String tablename,
            String[] columns) throws SQLException {
        return dumpTable(connection, tablename, null, columns);
    }

    /**
     * Dump table.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @param where the where
     * @param columns the columns
     * @return the list
     * @throws SQLException the sQL exception
     */
    public static List<List<Object>> dumpTable(Connection connection, String tablename,
            String where, String[] columns) throws SQLException {
        String q = SQL_SELECT_ALL.replace("%1", tablename);
        if (where != null && where.length() > 0) {
            q += " where " + where;
        }
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(q);
        s.close();
        return dumpResultSet(rs, tablename, columns);
    }

    /**
     * Dump result set.
     *
     * @param resultSet the result set
     * @param tablename the tablename
     * @param columns the columns
     * @return the list
     * @throws SQLException the sQL exception
     */
    public static List<List<Object>> dumpResultSet(ResultSet resultSet, String tablename,
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
        ResultSetMetaData rsmd = resultSet.getMetaData();
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
        while (resultSet.next()) {
            List<Object> d = new ArrayList<Object>();
            for (int i = 0, n = rsmd.getColumnCount(); i < n; i++) {
                int z = i + 1;
                Object o = resultSet.getObject(z);
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
        resultSet.close();
        return l;
    }

    @Override
    public void printTable(Connection c, String tablename, String where, String... columns) {
        DumpUtil.showTable(c, tablename, where, columns);
    }

}
