/*
 * TimeConversions.java
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
 * This enum defines time conversions.
 *
 * @author Michael Lieshoff
 *
 */
public enum TimeConversions implements TimeConversion {
    HOUR_OF_DAY_LEADING_24("H"),
    HOUR_OF_DAY_LEADING_12("I"),
    HOUR_OF_DAY_24("k"),
    HOUR_OF_DAY_12("l"),
    TWO_DIGIT_MINUTE("M"),
    TWO_DIGIT_SECOND("S"),
    THREE_DIGIT_MILLISECOND("L"),
    TWO_DIGIT_NANOSECOND("N"),
    MORING_AFTERNOON_MARKER("p"),
    RFC822_TIME_ZONE_OFFSET("z"),
    TIME_ZONE_ABBREVATION("Z"),
    BEGINNING_SECONDS("s"),
    BEGINNING_MILLISECONDS("Q");

    String token = "";

    TimeConversions(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

}
