/*
 * RegExLib.java
 *
 * 24.09.2009
 *
 * Copyright 2009 Michael Lieshoff
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

package org.mili.core.regex;

import java.util.regex.*;

/**
 * This enumeration defines some useful regular expressions.
 *
 * @author Michael Lieshoff
 */
public enum RegExLib {

    /** Date */
    Date("(?=.*)([\\d]+[./-][\\d]+[./-][\\d]+(?=.*))"),

    /** Version numbers in filenames. */
    VersionNumberOfFiles(
            "(.*)[-](([\\d]{0,})[.]([\\d]{0,})[.]([\\d]{0,})([.]([\\d]{0,})){0,1})"),

    /** I18n country name */
    I18NRessourceFile_CountryLanguage("(?=.*)[_](.*)[.](.*)");

    /** The regular expression. */
    String regularExpression = "";

    /**
     * Instantiates a new reg ex lib.
     *
     * @param regularExpression the regular expression
     */
    RegExLib(String regularExpression) {
        this.regularExpression = regularExpression;
    }

    /**
     * @return the regularExpression.
     */
    public String getRegularExpression() {
        return regularExpression;
    }

    /**
     * @return the pattern.
     */
    public Pattern getPattern() {
        return Pattern.compile(this.getRegularExpression());
    }

    /**
     * @param s String to match.
     * @return the matcher.
     */
    public Matcher getMatcher(String s) {
        return this.getPattern().matcher(s);
    }
}
