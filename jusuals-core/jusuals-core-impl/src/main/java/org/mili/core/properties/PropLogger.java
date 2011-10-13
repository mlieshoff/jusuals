/*
 * PropLogger.java
 *
 * 02.12.2010
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

package org.mili.core.properties;

import java.io.*;
import java.util.*;

import org.mili.core.properties.Props.*;

/**
 * This enum defines a prop logger.
 *
 * @author Michael Lieshoff
 *
 */
enum PropLogger {
    INSTANCE;

    private Map<String, String> props = new TreeMap<String, String>();

    PropLogger() {
    }

    <T> void log(String key, Usage usage, Class<T> typeClass, T defaultValue) {
        this.props.put(String.valueOf(usage) + "_" + (typeClass != null ?
                typeClass.getSimpleName() : "NO_TYPE") + "_" + key, String.valueOf(
                defaultValue));
        try {
            this.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    void save() throws IOException {
        Properties p = new Properties();
        File f = new File(new File(System.getProperty("java.io.tmpdir")),
                "accessed.properties");
        if (f.exists()) {
            InputStream is = new FileInputStream(f);
            p.load(is);
            is.close();
        }
        for (Iterator<String> i = this.props.keySet().iterator(); i.hasNext();) {
            String k = i.next();
            p.put(k, this.props.get(k));
        }
        OutputStream os = new FileOutputStream(f);
        p.store(os, "accessed properties found by core.properties.PropLogger");
        os.close();
        return;
    }

}
