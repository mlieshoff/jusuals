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

import org.apache.commons.lang.*;

/**
 * This utility class provides some useful methods for testing.
 *
 * @author Michael Lieshoff
 */
public final class TestUtil {
    /** The Constant TMP_FOLDER. */
    public static final String TMP_FOLDER = "./tmp";
    /** The Constant TMP_DIR. */
    public static final File TMP_DIR = new File(TMP_FOLDER);
    /** The Constant DB_FOLDER. */
    public static final String DB_FOLDER = TMP_FOLDER + "/testdb";
    /** The Constant DB_DIR. */
    public static final File DB_DIR = new File(DB_FOLDER);

    /**
     * Gets the db info.
     *
     * @param name the name of db
     * @return the db info
     */
    public static Properties getDBInfo(String name) {
        Properties p = new Properties();
        p.setProperty("driver", "org.hsqldb.jdbcDriver");
        p.setProperty("url", "jdbc:hsqldb:file:" + DB_FOLDER + "/" + name);
        p.setProperty("user", "sa");
        p.setProperty("password", "");
        return p;
    }

    /**
     * Gets the connection.
     *
     * @param dbInfo the db info
     * @return the connection
     * @throws SQLException if errors occurs
     * @throws ClassNotFoundException
     */
    public static Connection getConnection(Properties dbInfo) throws SQLException,
            ClassNotFoundException {
        Validate.notNull(dbInfo);
        Class.forName(dbInfo.getProperty("driver"));
        return DriverManager.getConnection(dbInfo.getProperty("url"), dbInfo.getProperty(
                "user"), dbInfo.getProperty("password"));
    }

    /**
     * Gets the connection.
     *
     * @param name the name of db
     * @return the connection
     * @throws SQLException if errors occurs
     * @throws ClassNotFoundException
     */
    public static Connection getConnection(String name) throws SQLException,
            ClassNotFoundException {
        Validate.notEmpty(name);
        return getConnection(getDBInfo(name));
    }

    /**
     * Gets the connection.
     *
     * @param cls the class of test, to get a db by it's simple name
     * @return the connection
     * @throws SQLException if errors occurs
     * @throws ClassNotFoundException
     */
    public static Connection getConnection(Class<?> cls) throws SQLException,
            ClassNotFoundException {
        Validate.notNull(cls);
        return getConnection(getDBInfo(cls.getSimpleName()));
    }

    /**
     * Shutdown connection.
     *
     * @param c the connection
     * @throws SQLException if errors occurs
     */
    public static void shutdownConnection(Connection c) throws SQLException {
        Validate.notNull(c);
        update(c, "SHUTDOWN");
        if (!c.isClosed()) {
            c.close();
        }
    }

    /**
     * Update.
     *
     * @param c the connection
     * @param sql the sql
     * @return the result of update operation
     * @throws SQLException if errors occurs
     */
    public static int update(Connection c, String sql) throws SQLException {
        Validate.notNull(c);
        Validate.notEmpty(sql);
        Statement statement = c.createStatement();
        int result = statement.executeUpdate(sql);
        statement.close();
        return result;
    }

}