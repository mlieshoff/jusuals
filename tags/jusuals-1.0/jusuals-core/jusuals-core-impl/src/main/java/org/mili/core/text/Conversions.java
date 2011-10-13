/*
 * Conversions.java
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
 * This enum defines conversions.
 *
 * @author Michael Lieshoff
 *
 */
public enum Conversions implements Conversion {
    BOOLEAN("b"),
    CHARACTER("c"),
    DATE_TIME("t"),
    DECIMAL("d"),
    FLOAT("f"),
    FLOAT_HEX("a"),
    FLOAT_SCIENCE("e"),
    FLOAT_SCIENCE_PREC("g"),
    HEX("h"),
    HEX_INT("h"),
    LINE_SEPARATOR("n"),
    OCTAL("o"),
    PERCENT("%"),
    STRING("s");

    String token = "";

    Conversions(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

}
