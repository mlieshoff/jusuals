/*
 * TimestampFormatter.java
 *
 * 03.08.2011
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

import java.text.*;
import java.util.*;

/**
 * The class defines a timestamp formatter.
 *
 * @author Michael Lieshoff
 */
public class TimestampFormatter implements Formatter {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public Object format(Object object) {
        return DATE_FORMAT.format(new Date(((Long) object).longValue()));
    }

}
