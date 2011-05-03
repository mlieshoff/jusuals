/*
 * LocaleInfo.java
 *
 * 03.05.2011
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
package org.mili.core.info;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.*;

/**
 * This class defines some methods for locales.
 *
 * @author Michael Lieshoff
 */
public class LocaleInfo {
    /**
     * Show default.
     *
     * @param locale the locale
     * @param os the os
     */
    public static void show(Locale locale, PrintStream os) {
        Validate.notNull(locale);
        Validate.notNull(os);
        os.println("country         : " + locale.getCountry());
        os.println("display-country : " + locale.getDisplayCountry());
        os.println("display-language: " + locale.getDisplayLanguage());
        os.println("display-name    : " + locale.getDisplayName());
        os.println("display-variant : " + locale.getDisplayVariant());
        os.println("iso3-country    : " + locale.getISO3Country());
        os.println("iso3-language   : " + locale.getISO3Language());
        os.println("language        : " + locale.getLanguage());
        os.println("variant         : " + locale.getVariant());
        os.println("user-country    : " + System.getProperty("user.country"));
        os.println("user-language   : " + System.getProperty("user.language"));
        os.println("user-variant    : " + System.getProperty("user.variant"));
    }
}
