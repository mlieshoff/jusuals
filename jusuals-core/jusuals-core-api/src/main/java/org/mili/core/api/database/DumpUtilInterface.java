/*
 * DumpUtilInterface.java
 *
 * 08.04.2011
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

package org.mili.core.api.database;

import java.sql.*;

/**
 * This interface defines an interface for dump utils.
 *
 * @author Michael Lieshoff
 *
 */
public interface DumpUtilInterface {

    /**
     * Prints table at console.
     *
     * @param connection the connection
     * @param tablename the tablename
     * @param where the where
     * @param columns the columns
     */
    void printTable(Connection connection, String tablename, String where, String[] columns);

}
