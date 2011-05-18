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
    FULL_MONTH_NAME("B"),
    ABBREVATED_MONTH_NAME("b"),
    ABBREVATED_MONTH_NAME_H("h"),
    FULL_DAY_OF_THE_WEEK_NAME("A"),
    SHORT_DAY_OF_THE_WEEK_NAME("a"),
    FOUR_DIGIT_YEAR("C"),
    LEADING_ZEROS_YEAR("Y"),
    LAST_TWO_DIGITS_YEAR("y"),
    THREE_DIGIT_DAY_OF_THE_YEAR("j"),
    TWO_DIGIT_MONTH("m"),
    TWO_DIGIT_DAY_OF_MONTH("d"),
    DAY_OF_MONTH("e");

    String token = "";

    DateConversions(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

}
