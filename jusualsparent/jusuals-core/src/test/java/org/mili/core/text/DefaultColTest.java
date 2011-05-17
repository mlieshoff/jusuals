/*
 * DefaultColTest.java
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
public class DefaultColTest {
    private DefaultCol col = null;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        this.col = new DefaultCol("name", Conversions.STRING, DateTimeConversions.FULL_DATE,
                Flags.LEFT);
    }

    /**
     * Should construct default1.
     */
    @Test
    public void shouldConstructDefault1() {
        this.col = new DefaultCol("name", Conversions.BOOLEAN);
    }

    /**
     * Should construct default2.
     */
    @Test
    public void shouldConstructDefault2() {
        this.col = new DefaultCol("name", Conversions.BOOLEAN, DateTimeConversions.FULL_DATE);
    }

    /**
     * Should get conversion.
     */
    @Test
    public void shouldGetConversion() {
        assertEquals(Conversions.STRING, this.col.getConversion());
        this.col.setConversion(Conversions.BOOLEAN);
        assertEquals(Conversions.BOOLEAN, this.col.getConversion());
    }

    /**
     * Should get width.
     */
    @Test
    public void shouldGetWidth() {
        assertEquals("name".length(), this.col.getWidth());
        this.col.setWidth(50);
        assertEquals(50, this.col.getWidth());
    }

    /**
     * Should get name.
     */
    @Test
    public void shouldGetName() {
        assertEquals("name", this.col.getName());
    }

    /**
     * Should get date time conversions.
     */
    @Test
    public void shouldGetDateTimeConversions() {
        assertEquals(DateTimeConversions.FULL_DATE, this.col.getDateTimeConversion());
    }

    /**
     * Should get flags.
     */
    @Test
    public void shouldGetFlags() {
        assertEquals(Flags.LEFT, this.col.getFlags()[0]);
    }

}
