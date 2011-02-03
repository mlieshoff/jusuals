/*
 * ICol.java
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
 * This interface defines a column.
 *
 * @author Michael Lieshoff
 *
 */
public interface Col {

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the conversion.
     *
     * @return the conversion
     */
    Conversion getConversion();

    /**
     * Sets the conversion.
     *
     * @param conversionForType the new conversion
     */
    void setConversion(Conversion conversionForType);

    /**
     * Gets the width.
     *
     * @return the width
     */
    int getWidth();

    /**
     * Sets the width.
     *
     * @param width the new width
     */
    void setWidth(int width);

    /**
     * Gets the flags.
     *
     * @return the flags
     */
    Flag[] getFlags();

    /**
     * Gets the date time conversion.
     *
     * @return the date time conversion
     */
    DTConversion getDateTimeConversion();

}