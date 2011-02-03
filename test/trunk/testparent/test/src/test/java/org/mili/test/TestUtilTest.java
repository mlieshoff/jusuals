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
    }

    @After
    public void after() throws SQLException {
        TestUtil.shutdownConnection(this.connection);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailedToGetTheConnectionBecauseEmpty() throws SQLException,
            ClassNotFoundException {
        TestUtil.getConnection("");
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailedToShutdownConnectionBecauseNull() throws SQLException {
        TestUtil.shutdownConnection(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailedToUpdateSqlBecauseNullConnection() throws SQLException {
        TestUtil.update(null, "abbas");
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailedToUpdateSqlBecauseNullSql() throws SQLException {
        TestUtil.update(this.connection, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailedToUpdateSqlBecauseEmptySql() throws SQLException {
        TestUtil.update(this.connection, "");
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

}
