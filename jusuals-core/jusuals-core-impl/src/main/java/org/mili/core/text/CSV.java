/*
 * CSV.java
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
 * This class is a CSV extension of class {@link TextTable}.
 *
 * @author Michael Lieshoff
 *
 */
public class CSV extends TextTable {
    private String separator = "";
    private String encapsulator = "";
    private String lineSeperator = "\n";

    /**
     * Erzeugt ein neues Objekt vom Typ CSV.
     *
     * @param separator Trennzeichen.
     * @param encapsulator Huellzeichen.
     */
    public CSV(String separator, String encapsulator) {
        super();
        this.separator = separator;
        this.encapsulator = encapsulator;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0, n = this.getColSize(); i < n; i++) {
            Col c = this.getCol(i);
            s.append(this.encapsulator);
            s.append(c.getName());
            s.append(this.encapsulator);
            if (i < n - 1) {
                s.append(this.separator);
            }
        }
        s.append(this.lineSeperator);
        // data
        for (int i = 0, n = this.getRowSize(); i < n; i++) {
            Row r = this.getRow(i);
            for (int ii = 0, nn = this.getColSize(); ii < nn; ii++) {
                Object o = r.getValue(ii);
                s.append(this.encapsulator);
                s.append(o);
                s.append(this.encapsulator);
                if (ii < nn - 1) {
                    s.append(this.separator);
                }
            }
            s.append(this.lineSeperator);
        }
        return s.toString();
    }

}