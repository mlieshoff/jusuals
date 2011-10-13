/*
 * ConnectionUtils.java
 *
 * 21.02.2011
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
package org.mili.test;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.commons.io.*;
import org.apache.commons.lang.*;

/**
 * This utility class provides some useful methods for connections.
 *
 * @author Michael Lieshoff
 */
public final class ConnectionUtils {

    /**
     * creates a new connection util.
     */
    public ConnectionUtils() {
    }

    /**
     * Gets the db info.
     *
     * @param name the name of db
     * @return the db info
     */
    static Properties getDBInfo(String name) {
        Properties p = new Properties();
        p.setProperty("driver", "org.hsqldb.jdbcDriver");
        p.setProperty("url", "jdbc:hsqldb:file:" + FolderUtils.DB_FOLDER + "/" + name);
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
     */
    static Connection getConnection(Properties dbInfo) throws SQLException {
        try {
            Class.forName(dbInfo.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        return DriverManager.getConnection(dbInfo.getProperty("url"), dbInfo.getProperty(
                "user"), dbInfo.getProperty("password"));
    }

    /**
     * Gets the connection.
     *
     * @param name the name of db
     * @return the connection
     * @throws SQLException if errors occurs
     */
    static Connection getConnection(String name) throws SQLException {
        Validate.notEmpty(name, "name cannot be empty!");
        return getConnection(getDBInfo(name));
    }

    /**
     * Gets the connection.
     *
     * @param cls the class of test, to get a db by it's simple name
     * @return the connection
     * @throws SQLException if errors occurs
     */
    static Connection getConnection(Class<?> cls) throws SQLException {
        return getConnection(getDBInfo(cls.getSimpleName()));
    }

    /**
     * Shutdown connection.
     *
     * @param c the connection
     * @throws SQLException if errors occurs
     */
    static void shutdownConnection(Connection c) throws SQLException {
        TestUtils.update(c, "SHUTDOWN");
        if (!c.isClosed()) {
            c.close();
        }
    }

    /**
     * Drops the database.
     *
     * @param databaseName the database name
     * @throws SQLException if errors occurs
     */
    static void dropDatabase(String databaseName) throws SQLException {
        shutdownConnection(getConnection(databaseName));
        File dir = new File(FolderUtils.DB_FOLDER + "/" + databaseName);
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            throw new SQLException(dir.getAbsolutePath() + " cant be dropped!", e);
        }
        if (dir.exists()) {
            throw new SQLException(dir.getAbsolutePath() + " cant be dropped!");
        }
    }

}