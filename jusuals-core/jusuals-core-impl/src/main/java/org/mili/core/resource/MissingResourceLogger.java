/*
 * MissingResourceLogger.java
 *
 * 15.12.2009
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

package org.mili.core.resource;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.*;

/**
 * This enum is used to log missing resources.
 *
 * @author Michael Lieshoff
 *
 */
enum MissingResourceLogger {
    INSTANCE;

    private Map<Locale, Map<String, String>> model =
            new Hashtable<Locale, Map<String,String>>();

    MissingResourceLogger() {
    }

    void log(Locale locale, String key) {
        Map<String, String> m = model.get(locale);
        if (m == null) {
            m = new TreeMap<String, String>();
            model.put(locale, m);
        }
        m.put(key, "");
        try {
            this.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    void save() throws IOException {
        for (Iterator<Locale> i = this.model.keySet().iterator(); i.hasNext();) {
            Locale l = i.next();
            this.save(l);
        }
        return;
    }

    void save(Locale l) throws IOException {
        Properties p = new Properties();
        String filenameProp = System.getProperty(ResourceUtil
                .PROP_MISSINGRESOURCELOGGERFILENAME);
        File file = null;
        if (StringUtils.isEmpty(filenameProp)) {
            file = new File(new File(System.getProperty("java.io.tmpdir")),
                    "missing-properties-" + l.getISO3Language() + ".properties");
        } else {
            file = new File(filenameProp.replace("%0", l.getISO3Language()));
        }
        if (file.exists()) {
            InputStream is = new FileInputStream(file);
            p.load(is);
            is.close();
        }
        Map<String, String> m = this.model.get(l);
        for (Iterator<String> i = m.keySet().iterator(); i.hasNext();) {
            String k = i.next();
            p.put(k, "");
        }
        OutputStream os = new FileOutputStream(file);
        p.store(os, "missing properties found by core.resource.MissingResourceLogger");
        os.close();
        return;
    }

}
