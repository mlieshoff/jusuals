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
    HOUR_OF_DAY_LEADING_24("tH"),
    HOUR_OF_DAY_LEADING_12("tI"),
    HOUR_OF_DAY_24("tk"),
    HOUR_OF_DAY_12("tl"),
    TWO_DIGIT_MINUTE("tM"),
    TWO_DIGIT_SECOND("tS"),
    THREE_DIGIT_MILLISECOND("tL"),
    TWO_DIGIT_NANOSECOND("tN"),
    MORING_AFTERNOON_MARKER("tp"),
    RFC822_TIME_ZONE_OFFSET("tz"),
    TIME_ZONE_ABBREVATION("tZ"),
    BEGINNING_SECONDS("ts"),
    BEGINNING_MILLISECONDS("tQ");

    String token = "";

    TimeConversions(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

}
