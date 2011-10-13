/*
 * CsvTransformator.java
 *
 * 15.10.2010
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

package org.mili.core.text.transformation;

import org.mili.core.text.*;

/**
 * This class defins a transformator for CSV.
 *
 * @author Michael Lieshoff
 *
 */
public class CsvTransformator {

    /**
     * Instantiates a new csv tranformator.
     */
    protected CsvTransformator() {
        super();
    }

    /**
     * Creates the transformator.
     *
     * @return the csv tranformator
     */
    public static CsvTransformator create() {
        return new CsvTransformator();
    }

    /**
      * Transforms.
      *
      * @param from from table
      * @param params the parameter for separation and encapsulation.
      * @return CSV
      */
     public CSV transform(Table from, Object... params) {
        String sep = params != null && params.length > 0 ? String.valueOf(params[0]) : ";";
        String enc = params != null && params.length > 1 ? String.valueOf(params[1]) : "\"";
        CSV to = new CSV(sep, enc);
        return (CSV) DefaultTransformator.copy(from, to);
    }

}
