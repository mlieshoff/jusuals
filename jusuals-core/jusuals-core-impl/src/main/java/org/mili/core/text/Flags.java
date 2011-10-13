/*
 * Flags.java
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
 * Thsi enum defines flags.
 *
 * @author Michael Lieshoff
 *
 */
public enum Flags implements Flag {
    LEFT("-"),
    CONVERSION_DEPENDENT("#"),
    SIGN("+"),
    POSITIVE_LEADING_SPACE("  "),
    ZERO_PADDING("0"),
    LOCALE_GROUPING_SEPARATORS(","),
    PARENTHESES_FOR_NEGATIVES("(");

    String token = "";

    Flags(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

}
