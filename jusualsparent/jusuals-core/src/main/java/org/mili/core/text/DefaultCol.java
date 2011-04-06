/*
 * DefaultCol.java
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
 * This class is a default implementation of interface {@link Col}.
 *
 * @author Michael Lieshoff
 *
 */
public class DefaultCol implements Col {
    private Flag[] flags = null;
    private Conversion conversion = null;
    private DTConversion dateTimeConversion = null;
    private String name = "";
    private int width = 0;

    /**
     * creates a new column.
     *
     * @param name name of column.
     * @param conversion conversion.
     * @param flags flags.
     */
    public DefaultCol(String name, Conversion conversion, Flag... flags) {
        this(name, conversion, null, flags);
    }

    /**
     * creates a new column.
     *
     * @param name name of column.
     * @param conversion conversion.
     * @param dateTimeConversion date time conversion.
     * @param flags flags.
     */
    public DefaultCol(String name, Conversion conversion, DTConversion dateTimeConversion,
            Flag... flags) {
        super();
        this.dateTimeConversion = dateTimeConversion;
        this.conversion = conversion;
        this.flags = flags;
        this.name = name;
        this.width = this.name.length();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Conversion getConversion() {
        return this.conversion;
    }

    @Override
    public void setConversion(Conversion conversionForType) {
        this.conversion = conversionForType;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public void setWidth(int nl) {
        this.width = nl;
    }

    @Override
    public Flag[] getFlags() {
        return this.flags;
    }

    @Override
    public DTConversion getDateTimeConversion() {
        return this.dateTimeConversion;
    }

}
