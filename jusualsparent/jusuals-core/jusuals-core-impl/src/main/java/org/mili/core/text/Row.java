/*
 * Row.java
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
 * This interface defines a row.
 *
 * @author Michael Lieshoff
 *
 */
public interface Row {

    /**
     * Gets the value.
     *
     * @param index the index
     * @return the value
     */
    Object getValue(int index);

    /**
     * Gets the values.
     *
     * @return the values
     */
    Object[] getValues();
}