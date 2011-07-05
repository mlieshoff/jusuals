/*
 * Html.java
 *
 * 12.06.2011
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

import org.mili.core.templating.*;

/**
 * This class is a Html extension of class {@link TextTable}.
 *
 * @author Michael Lieshoff
 *
 */
public class Html extends TextTable {
    private Renderer<String, String> headCellRenderer = new Renderer<String, String>() {
        @Override
        public String render(String toRender) {
            StringBuilder s = new StringBuilder();
            s.append("<th>");
            s.append(toRender);
            s.append("</th>");
            return s.toString();
        }
    };
    private Renderer<String, Object> cellRenderer = new Renderer<String, Object>() {
        @Override
        public String render(Object toRender) {
            StringBuilder s = new StringBuilder();
            s.append("<td>");
            s.append(toRender);
            s.append("</td>");
            return s.toString();
        }
    };

    /**
     * Instantiates a new Html.
     */
    public Html() {
        super();
    }

    public Html(Renderer<String, String> headCellRenderer, Renderer<String, ?> cellRenderer) {
        super();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("<table>");
        s.append("<thead>");
        s.append("<tr>");
        for (int i = 0, n = this.getColSize(); i < n; i++) {
            Col c = this.getCol(i);
            s.append(this.headCellRenderer.render(c.getName()));
        }
        s.append("</tr>");
        s.append("</thead>");
        // data
        s.append("<tbody>");
        for (int i = 0, n = this.getRowSize(); i < n; i++) {
            Row r = this.getRow(i);
            s.append("<tr>");
            for (int ii = 0, nn = this.getColSize(); ii < nn; ii++) {
                Object o = r.getValue(ii);
                s.append(this.cellRenderer.render(o));
            }
            s.append("</tr>");
        }
        s.append("</tbody>");
        s.append("</table>");
        return s.toString();
    }

}
