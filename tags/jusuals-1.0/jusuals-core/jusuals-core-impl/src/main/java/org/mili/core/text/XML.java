/*
 * XML.java
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
 * This class is a XML extension of class {@link TextTable}.
 *
 * @author Michael Lieshoff
 */
public class XML extends TextTable {

    /**
     * Instantiates a new xML.
     */
    public XML() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        s.append("<table>");
        s.append("<header>");
        for (int i = 0, n = this.getColSize(); i < n; i++) {
            Col c = this.getCol(i);
            s.append("<cell>");
            s.append(c.getName());
            s.append("</cell>");
        }
        s.append("</header>");
        // data
        for (int i = 0, n = this.getRowSize(); i < n; i++) {
            Row r = this.getRow(i);
            s.append("<row>");
            for (int ii = 0, nn = this.getColSize(); ii < nn; ii++) {
                Object o = r.getValue(ii);
                s.append("<cell>");
                s.append(o);
                s.append("</cell>");
            }
            s.append("</row>");
        }
        s.append("</table>");
        return s.toString();
    }

}