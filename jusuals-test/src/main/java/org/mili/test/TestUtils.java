/*
 * TestUtil.java
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
package org.mili.test;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.mili.core.api.database.*;

/**
 * This utility class provides some useful methods for testing.
 *
 * @author Michael Lieshoff
 */
public final class TestUtils {
    /** The Constant TMP_FOLDER. */
    public static final String TMP_FOLDER = FolderUtils.TMP_FOLDER;
    /** The Constant TMP_DIR. */
    public static final File TMP_DIR = FolderUtils.TMP_DIR;
    /** The Constant DB_FOLDER. */
    public static final String DB_FOLDER = FolderUtils.DB_FOLDER;
    /** The Constant DB_DIR. */
    public static final File DB_DIR = FolderUtils.DB_DIR;

    /**
     * creates a new test util.
     */
    public TestUtils() {
        super();
    }

    /**
     * Gets the db info.
     *
     * @param name the name of db
     * @return the db info
     */
    public static Properties getDBInfo(String name) {
        return ConnectionUtils.getDBInfo(name);
    }

    /**
     * Gets the connection.
     *
     * @param dbInfo the db info
     * @return the connection
     * @throws SQLException if errors occurs
     */
    public static Connection getConnection(Properties dbInfo) throws SQLException {
        return ConnectionUtils.getConnection(dbInfo);
    }

    /**
     * Gets the connection.
     *
     * @param name the name of db
     * @return the connection
     * @throws SQLException if errors occurs
     */
    public static Connection getConnection(String name) throws SQLException {
        return ConnectionUtils.getConnection(name);
    }

    /**
     * Gets the connection.
     *
     * @param cls the class of test, to get a db by it's simple name
     * @return the connection
     * @throws SQLException if errors occurs
     */
    public static Connection getConnection(Class<?> cls) throws SQLException {
        return ConnectionUtils.getConnection(cls);
    }

    /**
     * Shutdown connection.
     *
     * @param c the connection
     * @throws SQLException if errors occurs
     */
    public static void shutdownConnection(Connection c) throws SQLException {
        ConnectionUtils.shutdownConnection(c);
    }

    /**
     * Drop the database.
     *
     * @param databaseName the database name
     * @throws Exception if errors occurs
     */
    public static void dropDatabase(String databaseName) throws Exception {
        ConnectionUtils.dropDatabase(databaseName);
    }

    /**
     * Gets the tmp folder.
     *
     * @param cls the class based for tmp folder creation
     * @return the tmp folder
     * @throws Exception if errors occurs
     */
    public static File getTmpFolder(Class<?> cls) {
        return FolderUtils.getTmpFolder(cls);
    }

    /**
     * Executes a sql string on specified connection.
     *
     * @param connection the connection
     * @param sql update sql
     * @return the result of update operation
     * @throws SQLException if errors occurs
     */
    public static int update(Connection connection, String sql) throws SQLException {
        return UpdateUtils.update(connection, sql);
    }

    /**
     * Executes a sql string on a database specified with name.
     *
     * @param databaseName name of database
     * @param sql update sql
     * @return the result of update operation
     * @throws SQLException if errors occurs
     */
    public static int update(String databaseName, String sql) throws SQLException {
        return UpdateUtils.update(TestUtils.getConnection(databaseName), sql);
    }

    /**
     * Executes SQL batch commands.
     *
     * @param sqlCmds the sql commands
     * @param connection the connection
     * @throws SQLException if errors occurs
     */
    public static void executeBatch(List<String> sqlCmds, Connection connection)
            throws SQLException {
        UpdateUtils.executeBatch(sqlCmds, connection);
    }

    /**
     * Executes SQL batch commands.
     *
     * @param sqlCmds the sql commands
     * @param databaseName the database name
     * @throws SQLException if errors occurs
     */
    public static void executeBatch(List<String> sqlCmds, String databaseName)
            throws SQLException {
        UpdateUtils.executeBatch(sqlCmds, databaseName);
    }

    /**
     * Counts rows in defined table of specified connection.
     *
     * @param databaseName the name of database
     * @param tablename the tablename
     * @return the number of row count
     */
    public static int count(String databaseName, String tablename) throws SQLException {
        return QueryUtils.count(TestUtils.getConnection(databaseName), tablename);
    }

    /**
     * Counts rows in defined table of specified connection.
     *
     * @param databaseName the name of database
     * @param tablename the tablename
     * @param where the where
     * @return the number of row count
     */
    public static int count(String databaseName, String tablename, String where)
            throws SQLException {
        return QueryUtils.count(TestUtils.getConnection(databaseName), tablename, where);
    }

    /**
     * Counts rows in defined table of specified connection.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @return the number of row count
     */
    public static int count(Connection connection, String tablename) throws SQLException {
        return QueryUtils.count(connection, tablename);
    }

    /**
     * Counts rows in defined table of specified connection.
     *
     * @param databaseName the name of database
     * @param tablename the tablename
     * @param where the where
     * @return the number of row count
     */
    public static int count(Connection connection, String tablename, String where)
            throws SQLException {
        return QueryUtils.count(connection, tablename, where);
    }

    /**
     * Shows table at console.
     *
     * @param databasename the databasename
     * @param tablename the tablename
     */
    public static void showTable(String databasename, String tablename) throws SQLException {
        showTable(databasename, tablename, null);
    }

    /**
     * Shows table at console.
     *
     * @param databasename the databasename
     * @param tablename the tablename
     * @param where the where
     */
    public static void showTable(String databasename, String tablename, String where)
            throws SQLException {
        showTable(databasename, tablename, where, null);
    }

    /**
     * Shows table at console.
     *
     * @param databasename the databasename
     * @param tablename the tablename
     * @param where the where
     * @param columns the columns
     */
    public static void showTable(String databasename, String tablename, String where,
            String... columns) throws SQLException {
        DumpUtilInterface dui = null;
        try {
            Class<? extends DumpUtilInterface> cls = (Class<? extends DumpUtilInterface>) Class
                    .forName("org.mili.core.database.DumpUtil");
            dui = (DumpUtilInterface) cls.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        dui.printTable(getConnection(databasename), tablename, where, columns);
    }

}