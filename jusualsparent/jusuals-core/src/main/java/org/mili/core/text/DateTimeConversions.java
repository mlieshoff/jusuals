/*
 * DateTimeConversions.java
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
 * This enum defines date/time conversions.
 *
 * @author Michael Lieshoff
 *
 */
public enum DateTimeConversions implements DateTimeConversion {
    TIME_HOUR_CLOCK_24("tR"),
    LONG_TIME_HOUR_CLOCK_24("tT"),
    TIME_HOUR_CLOCK_12("tr"),
    LONG_DATE("tD"),
    ISO8601_DATE("tF"),
    FULL_DATE("tc");

    String token = "";

    DateTimeConversions(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

}
