/*
 * DbInfoFactory.java
 *
 * 21.02.2011
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

package org.mili.test;

import java.util.*;

/**
 * This test factory provides methods to instantiate object for test only.
 *
 * @author Michael Lieshoff
 */
public class DbInfoFactory {

    /**
     * Creates new database info properties.
     *
     * @return the database info properties.
     */
    static Properties create() {
        Properties p = new Properties();
        p.setProperty("driver", "org.hsqldb.jdbcDriver");
        p.setProperty("url", "jdbc:hsqldb:file:" + TestUtils.DB_FOLDER + "/" + "xyz");
        p.setProperty("user", "sa");
        p.setProperty("password", "");
        return p;
    }

    /**
     * Creates new defect database info properties.
     *
     * @return the defect database info properties.
     */
    static Properties createDefect() {
        Properties p = new Properties();
        p.setProperty("driver", "a.b.C");
        p.setProperty("url", "jdbc:hsqldb:file:" + TestUtils.DB_FOLDER + "/" + "xyz");
        p.setProperty("user", "sa");
        p.setProperty("password", "");
        return p;
    }

}
