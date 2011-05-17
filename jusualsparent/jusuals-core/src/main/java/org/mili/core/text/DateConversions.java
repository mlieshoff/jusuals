/*
 * DateConversions.java
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
 * This enum defines date conversions.
 *
 * @author Michael Lieshoff
 *
 */
public enum DateConversions implements DateConversion {
    FULL_MONTH_NAME("tB"),
    ABBREVATED_MONTH_NAME("tb"),
    ABBREVATED_MONTH_NAME_H("th"),
    FULL_DAY_OF_THE_WEEK_NAME("tA"),
    SHORT_DAY_OF_THE_WEEK_NAME("ta"),
    FOUR_DIGIT_YEAR("tC"),
    LEADING_ZEROS_YEAR("tY"),
    LAST_TWO_DIGITS_YEAR("ty"),
    THREE_DIGIT_DAY_OF_THE_YEAR("tj"),
    TWO_DIGIT_MONTH("tm"),
    TWO_DIGIT_DAY_OF_MONTH("td"),
    DAY_OF_MONTH("te");

    String token = "";

    DateConversions(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

}
