/*
 * UpdateUtils.java
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

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.*;


/**
 * This utility class provides some useful methods for updates.
 *
 * @author Michael Lieshoff
 *
 */
public class UpdateUtils {

    /**
     * creates a new update util.
     */
    public UpdateUtils() {
    }

    /**
     * Executes a sql string on specified connection.
     *
     * @param connection the connection
     * @param sql update sql.
     * @return the result of update operation
     * @throws SQLException if errors occurs
     */
    public static int update(Connection connection, String sql) throws SQLException {
        Validate.notEmpty(sql, "sql query cannot be empty!");
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate(sql);
        statement.close();
        return result;
    }

    /**
     * Executes sql batch commands.
     *
     * @param sqlCmds the sql commands
     * @param connection the connection
     * @throws SQLException if errors occurs
     */
    public static void executeBatch(List<String> sqlCmds, Connection connection)
            throws SQLException {
        for (int i = 0, n = sqlCmds.size(); i < n; i++) {
            update(connection, sqlCmds.get(i));
        }
    }

    /**
     * Executes sql batch commands.
     *
     * @param sqlCmds the sql commands
     * @param databaseName the database name
     * @throws SQLException if errors occurs
     */
    public static void executeBatch(List<String> sqlCmds, String databaseName)
            throws SQLException {
        executeBatch(sqlCmds,ConnectionUtils.getConnection(databaseName));
    }
}
