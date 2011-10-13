/*
 * Converter.java
 *
 * 11.02.2011
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
package org.mili.ant;

/**
 * This interface defines a converter, that convert a string into another string.This converter
 * are used in the class PropertiesReplacer.
 * In al optional assignment in file <code>properties-description.xml</code> it will be used.
 * Example:<p/>
 * <code>
 *     <replace property="property" converter-class="my.package.MyConverter">
 * </code><p/>
 * will uses the specified converter.
 *
 * @author Michael Lieshoff
 *
 */
public interface Converter {
    /**
     * Convert.
     *
     * @param value the value
     * @return the string
     */
    String convert(String value);
}
