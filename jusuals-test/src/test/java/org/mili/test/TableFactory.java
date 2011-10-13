/*
 * TableFactory.java
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

/**
 * This test factory provides methods to instantiate object for test only.
 * 
 * @author Michael Lieshoff
 */
public class TableFactory {

    /**
     * Creates a new test table.
     *
     * @param connection the connection
     */
    static void create(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("drop table if exists A;");
        statement.executeUpdate("create table A(i integer primary key, s varchar(255));");
        statement.executeUpdate("insert into A(i, s) values(1, 's1');");
        statement.executeUpdate("insert into A(i, s) values(2, 's2');");
        statement.executeUpdate("insert into A(i, s) values(3, 's3');");
        statement.close();
    }

}
