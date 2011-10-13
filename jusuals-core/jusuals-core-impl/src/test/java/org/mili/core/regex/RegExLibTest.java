/*
 * RegExLibTest.java
 *
 * 05.03.2010
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

package org.mili.core.regex;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * @author Michael Lieshoff
 */
public class RegExLibTest {

    @Test
    public void shouldCreatesAllEnums() {
        for(RegExLib regex : RegExLib.values()) {
            assertEquals(regex, RegExLib.valueOf(regex.name()));
            assertEquals(regex, RegExLib.valueOf(RegExLib.class, regex.toString()));
        }
    }

    @Test
    public void testGetRegularExpression() {
        assertTrue(RegExLib.I18NRessourceFile_CountryLanguage.getRegularExpression().length()
                > 0);
        assertTrue(RegExLib.VersionNumberOfFiles.getRegularExpression().length() > 0);
    }

    @Test
    public void testGetPattern() {
        assertNotNull(RegExLib.I18NRessourceFile_CountryLanguage.getPattern());
        assertNotNull(RegExLib.VersionNumberOfFiles.getPattern());
    }

    @Test
    public void testGetMatcher() {
        assertNotNull(RegExLib.I18NRessourceFile_CountryLanguage.getMatcher("aa_ccc.ddd"));
        assertNotNull(RegExLib.VersionNumberOfFiles.getMatcher("a-1.3.6.txt"));
    }

}
