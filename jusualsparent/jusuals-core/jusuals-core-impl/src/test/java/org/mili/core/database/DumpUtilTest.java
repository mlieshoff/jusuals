/*
 * DumpUtilTest.java
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

import org.junit.*;
import org.mili.core.text.*;
import org.mili.test.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class DumpUtilTest {
    private Connection connection = null;


    @BeforeClass
    public static void createDB() throws Exception {
        Connection c = null;
        try {
            c = TestUtils.getConnection(TestUtils.getDBInfo("DumpUtil"));
            TestUtils.update(c, "drop table if exists a;");
            TestUtils.update(c, "create table a (a integer primary key, b varchar(50), "
                    + "c integer);");
            TestUtils.update(c, "insert into a (a, b, c) values (1, 'a', 10);");
            TestUtils.update(c, "insert into a (a, b, c) values (2, 'b', 11);");
            TestUtils.update(c, "insert into a (a, b, c) values (3, 'c', 12);");
            TestUtils.update(c, "insert into a (a, b, c) values (4, 'd', 13);");
            TestUtils.update(c, "insert into a (a, b, c) values (5, 'e', 14);");
            TestUtils.update(c, "drop table if exists empty;");
            TestUtils.update(c, "create table empty (a integer primary key, b varchar(50), "
                    + "c integer);");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                TestUtils.shutdownConnection(c);
            } catch (SQLException e) {
                e.printStackTrace();
                fail();
            }
        }
    }

    @Before
    public void setUp() throws Exception {
        this.connection = TestUtils.getConnection(TestUtils.getDBInfo("DumpUtil"));
    }

    @After
    public void after() throws Exception {
        TestUtils.shutdownConnection(this.connection);
    }

    @Test
    public void shouldCreates() {
        new DumpUtil();
    }

    @Test
    public void test() throws Exception {
        List<List<Object>> l = DumpUtil.dumpTable(this.connection, "a");
        assertTrue(l != null && l.size() > 0);
        assertTrue(l.get(0).toString().compareTo("[a]") == 0);
        assertTrue(l.get(1).toString().compareTo("[A, B, C]") == 0);
        assertTrue(l.get(2).toString().compareTo("[1, a, 10]") == 0);
        assertTrue(l.get(3).toString().compareTo("[2, b, 11]") == 0);
        assertTrue(l.get(4).toString().compareTo("[3, c, 12]") == 0);
        assertTrue(l.get(5).toString().compareTo("[4, d, 13]") == 0);
        assertTrue(l.get(6).toString().compareTo("[5, e, 14]") == 0);
        //
        String s = DumpUtil.tableToString(this.connection, "a");
        assertTrue(s != null && s.length() > 0);
        s = s.replace("\n\r", "#");
        s = s.replace("\n", "#");
        assertTrue(s.toString().compareTo(
                "a#A, B, C#1, a, 10#2, b, 11#3, c, 12#4, d, 13#5,"
                        + " e, 14") == 0);
        //
        l = DumpUtil.dumpTable(this.connection, "a", new String[] { "a" });
        assertTrue(l != null && l.size() > 0);
        assertTrue(l.get(0).toString().compareTo("[a]") == 0);
        assertTrue(l.get(1).toString().compareTo("[A]") == 0);
        assertTrue(l.get(2).toString().compareTo("[1]") == 0);
        assertTrue(l.get(3).toString().compareTo("[2]") == 0);
        assertTrue(l.get(4).toString().compareTo("[3]") == 0);
        assertTrue(l.get(5).toString().compareTo("[4]") == 0);
        assertTrue(l.get(6).toString().compareTo("[5]") == 0);

        l = DumpUtil.dumpTable(this.connection, "a", new String[] { "a", "b", "c" });
        assertTrue(l != null && l.size() > 0);
        assertTrue(l.get(0).toString().compareTo("[a]") == 0);
        assertTrue(l.get(1).toString().compareTo("[A, B, C]") == 0);
        assertTrue(l.get(2).toString().compareTo("[1, a, 10]") == 0);
        assertTrue(l.get(3).toString().compareTo("[2, b, 11]") == 0);
        assertTrue(l.get(4).toString().compareTo("[3, c, 12]") == 0);
        assertTrue(l.get(5).toString().compareTo("[4, d, 13]") == 0);
        assertTrue(l.get(6).toString().compareTo("[5, e, 14]") == 0);

        l = DumpUtil.dumpTable(this.connection, "a", "a=1", new String[] { "a", "b", "c" });
        assertTrue(l != null && l.size() > 0);
        assertTrue(l.get(0).toString().compareTo("[a]") == 0);
        assertTrue(l.get(1).toString().compareTo("[A, B, C]") == 0);
        assertTrue(l.get(2).toString().compareTo("[1, a, 10]") == 0);

        s = DumpUtil.tableToString(this.connection, "a", new String[] { "a", "b", "c" });
        assertTrue(s != null && s.length() > 0);
        s = s.replace("\n\r", "#");
        s = s.replace("\n", "#");
        assertTrue(s.toString().compareTo(
                "a#A, B, C#1, a, 10#2, b, 11#3, c, 12#4, d, 13#5,"
                        + " e, 14") == 0);
        Table t = DumpUtil.tableToTextTable(this.connection, "a", "a=1", "a", "b");
    }

    @Test(expected=SQLException.class)
    public void failDumpTableBecauseException() throws Exception {
        DumpUtil.dumpTable(this.connection, "a", "aaaa=1", new String[] { "a", "b", "c" });
    }

    @Test
    public void shouldNullTextTableToStringBecauseNoEntries() throws Exception {
        assertNull(DumpUtil.tableToTextTable(this.connection, "empty", ""));
    }

    @Test
    public void shouldShowTable() throws Exception {
        DumpUtil.showTable(this.connection, "empty", "");
    }

    @Test
    public void shouldConsoleOutputShowTableBecauseException() throws Exception {
        DumpUtil.showTable(this.connection, "a", "aaaa=1", new String[] { "a", "b", "c" });
    }

    @Test
    public void shouldPrintTable() throws Exception {
        new DumpUtil().printTable(connection, "empty", null, null);
    }

}