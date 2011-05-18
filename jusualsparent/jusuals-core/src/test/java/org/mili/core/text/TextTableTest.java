/*
 * TextTableTest.java
 *
 * 17.05.2011
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
package org.mili.core.text;

import org.junit.*;

import static org.junit.Assert.*;


/**
 * @author Michael Lieshoff
 */
public class TextTableTest {
    private TextTable table = null;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        this.table = new TextTable();
    }

    @Test
    public void shouldAddColName() {
        this.table.addCol("col1");
        assertEquals("col1", this.table.getCol(0).getName());
    }

    @Test
    public void shouldAddColNameFlag() {
        this.table.addCol("col1", Flags.LEFT);
        assertEquals("col1", this.table.getCol(0).getName());
        assertEquals(Flags.LEFT, this.table.getCol(0).getFlags()[0]);
    }

    @Test
    public void shouldAddColNameConversionFlag() {
        this.table.addCol("col1", Conversions.BOOLEAN, Flags.LEFT);
        assertEquals("col1", this.table.getCol(0).getName());
        assertEquals(Flags.LEFT, this.table.getCol(0).getFlags()[0]);
        assertEquals(Conversions.BOOLEAN, this.table.getCol(0).getConversion());
    }

    @Test
    public void shouldAddColNameConversionDateTimeConversionFlag() {
        this.table.addCol("col1", Conversions.BOOLEAN, DateTimeConversions.FULL_DATE,
                Flags.LEFT);
        assertEquals("col1", this.table.getCol(0).getName());
        assertEquals(Flags.LEFT, this.table.getCol(0).getFlags()[0]);
        assertEquals(Conversions.BOOLEAN, this.table.getCol(0).getConversion());
        assertEquals(DateTimeConversions.FULL_DATE, this.table.getCol(0)
                .getDateTimeConversion());
    }

    @Test
    public void shouldGetColSize() {
        assertEquals(0, this.table.getColSize());
        this.table.addCol("1");
        assertEquals(1, this.table.getColSize());
    }

    @Test
    public void shouldGetRowSize() {
        assertEquals(0, this.table.getRowSize());
        this.table.addCol("1");
        this.table.addRow(1);
        assertEquals(1, this.table.getRowSize());
    }

    @Test
    public void shouldGetRow() {
        this.table.addCol("1");
        this.table.addRow(1);
        assertEquals(1, ((Integer) this.table.getRow(0).getValue(0)).intValue());
    }

    @Test
    public void shouldGetString() {
        this.table = MockFactory.createTextTable();
        assertEquals(MockFactory.getTextString(), this.table.toString().replaceAll("[\r\n]",
                ""));
    }

}
