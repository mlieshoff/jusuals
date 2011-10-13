/*
 * QueryUtils.java
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

import org.apache.commons.lang.*;

/**
 * This utility class provides some useful methods for queries.
 * 
 * @author Michael Lieshoff
 *
 */
public class QueryUtils {

    /**
     * creates a new query util. 
     */
    public QueryUtils() {
    }

    /**
     * Counts rows in defined table of specified connection.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @return the number of row count.
     */
    public static int count(Connection connection, String tablename) throws SQLException {
        return count(connection, tablename, null);
    }

    /**
     * Counts rows in defined table of specified connection.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @param where the where
     * @return the number of row count.
     */
    public static int count(Connection connection, String tablename, String where) 
            throws SQLException {
        StringBuilder s = new StringBuilder();
        s.append("select count(*) from ");
        s.append(tablename);
        if (!StringUtils.isEmpty(where)) {
            s.append(" where ");
            s.append(where);
        }
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(s.toString());
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        statement.close();
        return count;
    }

}
