/*
 * Table.java
 *
 * 14.10.2010
 *
 * Copyright 2010 Michael Lieshoff
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


/**
 * Thsi interface defines a table.
 *
 * @author Michael Lieshoff
 *
 */
public interface Table {

    /**
     * Adds the col.
     *
     * @param name the name
     * @return the i table
     */
    Table addCol(String name);

    /**
     * Adds the col.
     *
     * @param name the name
     * @param flags the flags
     * @return the table
     */
    Table addCol(String name, Flag... flags);

    /**
     * Adds the row.
     *
     * @param values the values
     */
    void addRow(Object... values);

    /**
     * Gets the col size.
     *
     * @return the col size
     */
    int getColSize();

    /**
     * Gets the col.
     *
     * @param index the index
     * @return the col
     */
    Col getCol(int index);

    /**
     * Gets the row size.
     *
     * @return the row size
     */
    int getRowSize();

    /**
     * Gets the row.
     *
     * @param index the index
     * @return the row
     */
    Row getRow(int index);

    /**
     * Adds the col.
     *
     * @param name the name
     * @param conversion the conversion
     * @param dateTimeConversion the date time conversion
     * @param flags the flags
     * @return the i table
     */
    Table addCol(String name, Conversion conversion, DTConversion dateTimeConversion,
            Flag... flags);
}