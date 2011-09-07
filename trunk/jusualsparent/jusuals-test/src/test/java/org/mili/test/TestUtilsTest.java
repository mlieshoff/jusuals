/*
 * TestUtilsTest.java
 *
 * 02.02.2011
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

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 *
 */
public class TestUtilsTest {

    private Connection connection = null;
    private List<String> commands = this.createBatchCommands();

    @Before
    public void setUp() throws Exception {
        TestUtils.TMP_DIR.mkdirs();
        TestUtils.DB_DIR.mkdirs();
        this.connection = TestUtils.getConnection(TestUtils.class);
        TableFactory.create(this.connection);
    }

    @After
    public void after() throws SQLException {
        TestUtils.shutdownConnection(this.connection);
    }

    @Test
    public void shouldCreateNewConnectionUtil() {
        new ConnectionUtils();
    }

    @Test
    public void shouldCreateNewUpdateUtil() {
        new UpdateUtils();
    }

    @Test
    public void shouldCreateNewQueryUtil() {
        new QueryUtils();
    }

    @Test
    public void shouldCreateNewTestUtil() {
        new TestUtils();
    }

    @Test
    public void shouldCreateNewFolderUtils() {
        new FolderUtils();
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailedToGetTheConnectionBecauseEmpty() throws SQLException,
            ClassNotFoundException {
        TestUtils.getConnection("");
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailsBecauseNoClassDefFound() throws Exception {
        TestUtils.getConnection(DbInfoFactory.createDefect());
    }

    @Test
    public void shouldGetConnectionByClass() throws Exception {
        Connection c = TestUtils.getConnection(String.class);
        assertNotNull(c);
        TestUtils.shutdownConnection(c);
    }

    @Test
    public void shouldGetConnectionByString() throws Exception {
        Connection c = TestUtils.getConnection("lala");
        assertNotNull(c);
        TestUtils.shutdownConnection(c);
    }

    @Test
    public void shouldGetConnectionByDbInfos() throws Exception {
        Connection c = TestUtils.getConnection(DbInfoFactory.create());
        assertNotNull(c);
        TestUtils.shutdownConnection(c);
    }

    @Test
    public void shouldDropsTheDatabase() throws Exception {
        TestUtils.getConnection("abc");
        TestUtils.dropDatabase("abc");
        assertFalse(new File(TestUtils.DB_DIR, "abc").exists());
    }

    @Test
    public void shouldGetDbInfos() {
        assertEquals(DbInfoFactory.create(), TestUtils.getDBInfo("xyz"));
    }

    @Test
    public void shouldCountNumberOfRows() throws Exception {
        assertEquals(3, TestUtils.count(this.connection, "A"));
    }

    @Test
    public void shouldCountNumberOfRowsWithWhere() throws Exception {
        assertEquals(1, TestUtils.count(this.connection, "A", "s='s1'"));
    }

    @Test
    public void shouldCountNumberOfRowsWithDatabasename() throws Exception {
        assertEquals(3, TestUtils.count("TestUtils", "A"));
    }

    @Test
    public void shouldCountNumberOfRowsWithDatabasenameAndWhere() throws Exception {
        assertEquals(1, TestUtils.count("TestUtils", "A", "s='s1'"));
    }

    @Test
    public void shouldUpdate() throws Exception {
        TestUtils.update(this.connection, "delete from A where s='s1'");
        assertEquals(2, TestUtils.count(this.connection, "A"));
    }

    @Test
    public void shouldUpdateWithDatabasename() throws Exception {
        TestUtils.update("TestUtils", "delete from A where s='s1'");
        assertEquals(2, TestUtils.count(this.connection, "A"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailUpdateBecauseEmptySql() throws Exception {
        TestUtils.update(this.connection, "");
    }

    @Ignore
    public void shouldShowCompleteTableWithDatabasename() throws Exception {
        TestUtils.showTable("TestUtil", "A");
    }

    @Ignore
    public void shouldShowCompleteTableWithDatabasenameAndColumns() throws Exception {
        TestUtils.showTable("TestUtil", "A", null, "s", "i");
    }

    @Ignore
    public void shouldShowCompleteTableWithDatabasenameAndWhereAndColumns() throws Exception {
        TestUtils.showTable("TestUtil", "A", "s='1'", "s", "i");
    }

    @Test
    public void shouldGetTmpFolderForClass() {
        File f = new File(TestUtils.TMP_DIR, "/java/lang/String");
        assertEquals(f, TestUtils.getTmpFolder(String.class));
    }

    @Test
    public void shouldExecuteBatchWithConnection() throws SQLException {
        TestUtils.executeBatch(this.commands, this.connection);
        assertEquals(5, TestUtils.count(this.connection, "A"));
    }

    @Test
    public void shouldExecuteBatchWithDatabaseName() throws SQLException {
        TestUtils.executeBatch(this.commands, "TestUtils");
        assertEquals(5, TestUtils.count("TestUtils", "A"));
    }

    private List<String> createBatchCommands() {
        List<String> commands = new ArrayList<String>();
        commands.add("insert into A(i, s) values(4, 's4');");
        commands.add("insert into A(i, s) values(5, 's5');");
        return commands;
    }

}
