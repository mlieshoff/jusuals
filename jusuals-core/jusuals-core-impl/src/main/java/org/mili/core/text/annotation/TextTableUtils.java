/*
 * TextTableUtils.java
 *
 * 01.08.2011
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

package org.mili.core.text.annotation;

import org.apache.commons.lang.*;

/**
 * This class helps.
 *
 * @author Michael Lieshoff
 */
public class TextTableUtils {

    /**
     * Header to csv.
     *
     * @param headerValues the header values
     * @return the string
     */
    public static String headerToCSV(String[] headerValues) {
        StringBuilder s = new StringBuilder();
        for(String val : headerValues) {
            s.append(val);
            s.append("\t");
        }
        return s.toString();
    }

    /**
     * Values to csv.
     *
     * @param no the no
     * @param values the values
     * @return the string
     */
    public static String valuesToCSV(int no, Object[] values) {
        StringBuilder s = new StringBuilder();
        for(Object val : values) {
            s.append(val);
            s.append("\t");
        }
        return s.toString();
    }

    /**
     * Creates the format.
     *
     * @param headerValues the header values
     * @param widths the widths
     * @return the string
     */
    public static String createFormat(String[] headerValues, int[] widths) {
        StringBuilder s = new StringBuilder();
        s.append("| ");
        for(int i = 0, n = headerValues.length; i < n; i ++) {
            s.append("%");
            s.append(widths[i]);
            s.append("s");
            if (i < n - 1) {
                s.append(" | ");
            }
        }
        s.append(" | ");
        return s.toString();
    }

    /**
     * Creates the separator format.
     *
     * @param pattern the pattern
     * @return the string
     */
    public static String createSeparatorFormat(String pattern) {
        return pattern.replace("|", "+").replace(" ", "");
    }

    /**
     * Creates the separator.
     *
     * @param widths the widths
     * @return the string[]
     */
    public static String[] createSeparator(int[] widths) {
        String[] separator = new String[widths.length];
        for (int i = 0; i < widths.length; i++) {
            separator[i] = StringUtils.repeat("-", widths[i] + 2);
        }
        return separator;
    }

}
