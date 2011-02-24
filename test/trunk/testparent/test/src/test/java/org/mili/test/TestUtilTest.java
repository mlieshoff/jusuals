/*
 * TestUtilTest.java
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


import static org.junit.Assert.*;

import java.sql.*;

import org.junit.*;

/**
 * @author Michael Lieshoff
 *
 */
public class TestUtilTest {

    private Connection connection = null;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        TestUtil.TMP_DIR.mkdirs();
        TestUtil.DB_DIR.mkdirs();
        this.connection = TestUtil.getConnection(TestUtil.class);
        TableFactory.create(this.connection);
    }

    @After
    public void after() throws SQLException {
        TestUtil.shutdownConnection(this.connection);
    }

    @Test
    public void shouldCreateNewConnectionUtil() {
        new ConnectionUtil();
    }

    @Test
    public void shouldCreateNewUpdateUtil() {
        new UpdateUtil();
    }

    @Test
    public void shouldCreateNewQueryUtil() {
        new QueryUtil();
    }

    @Test
    public void shouldCreateNewTestUtil() {
        new TestUtil();
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailedToGetTheConnectionBecauseEmpty() throws SQLException,
            ClassNotFoundException {
        TestUtil.getConnection("");
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailsBecauseNoClassDefFound() throws Exception {
        TestUtil.getConnection(DbInfoFactory.createDefect());
    }
    
    @Test
    public void shouldGetConnectionByClass() throws Exception {
        Connection c = TestUtil.getConnection(String.class);
        assertNotNull(c);
        TestUtil.shutdownConnection(c);
    }

    @Test
    public void shouldGetConnectionByString() throws Exception {
        Connection c = TestUtil.getConnection("lala");
        assertNotNull(c);
        TestUtil.shutdownConnection(c);
    }

    @Test
    public void shouldGetConnectionByDbInfos() throws Exception {
        Connection c = TestUtil.getConnection(DbInfoFactory.create());
        assertNotNull(c);
        TestUtil.shutdownConnection(c);
    }
    
    @Test
    public void shouldGetDbInfos() {
        assertEquals(DbInfoFactory.create(), TestUtil.getDBInfo("xyz"));
    }
    
    @Test
    public void shouldCountNumberOfRows() throws Exception {
        assertEquals(3, TestUtil.count(this.connection, "A"));
    }

    @Test
    public void shouldCountNumberOfRowsWithWhere() throws Exception {
        assertEquals(1, TestUtil.count(this.connection, "A", "s='s1'"));
    }

    @Test
    public void shouldCountNumberOfRowsWithDatabasename() throws Exception {
        assertEquals(3, TestUtil.count("TestUtil", "A"));
    }

    @Test
    public void shouldCountNumberOfRowsWithDatabasenameAndWhere() throws Exception {
        assertEquals(1, TestUtil.count("TestUtil", "A", "s='s1'"));
    }

    @Test
    public void shouldUpdate() throws Exception {
        TestUtil.update(this.connection, "delete from A where s='s1'");
        assertEquals(2, TestUtil.count(this.connection, "A"));
    }

    @Test
    public void shouldUpdateWithDatabasename() throws Exception {
        TestUtil.update("TestUtil", "delete from A where s='s1'");
        assertEquals(2, TestUtil.count(this.connection, "A"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailUpdateBecauseEmptySql() throws Exception {
        TestUtil.update(this.connection, "");
    }

    @Test
    public void shouldShowCompleteTableWithDatabasename() throws Exception {
        TestUtil.showTable("TestUtil", "A");
    }
    
    @Test
    public void shouldShowCompleteTableWithDatabasenameAndColumns() throws Exception {
        TestUtil.showTable("TestUtil", "A", null, "s", "i");
    }

    @Test
    public void shouldShowCompleteTableWithDatabasenameAndWhereAndColumns() throws Exception {
        TestUtil.showTable("TestUtil", "A", "s='1'", "s", "i");
    }

}
