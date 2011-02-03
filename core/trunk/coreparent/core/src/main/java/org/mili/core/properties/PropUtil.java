/*
 * PropUtil.java
 *
 * 14.06.2010
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

import org.apache.commons.lang.*;

/**
 * This utility class defines some useful methods to operate with properties.
 *
 * @author Michael Lieshoff
 *
 */
public class PropUtil {

    /**
     * <p>This method reads a {@link Properties} file from a specified {@link File} and throws
     * <code>IllegalArgumentException</code>, if parameter is null or file not exists.
     * </p>
     *
     * <p>This method is used to read a {@link Properties} from a specified {@link File}
     * object.</p>
     *
     * <pre>
     * Util.readProperties(new File("example.properties"));
     * </pre>
     *
     * @param f {@link File} file contains the {@link Properties}.
     * @return readed {@link Properties}.
     */
    public static Properties readProperties(File f) {
        Validate.notNull(f);
        Validate.isTrue(f.exists());
        InputStream is = null;
        Properties p = new Properties();
        try {
            is = new FileInputStream(f);
            p.load(is);
            return p;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

}
