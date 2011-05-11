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
    private static Wrapper wrapper = new DefaultWrapper();

    /**
     * Instantiates a new prop util.
     */
    public PropUtil() {
    }

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
        try {
            is = wrapper.createFileInputStream(f);
            return readProperties(is);
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

    /**
     * <p>This method reads a {@link Properties} file from a specified {@link InputStream} and
     * throws <code>IllegalArgumentException</code>, if parameter is null or file not available.
     * </p>
     *
     * <p>This method is used to read a {@link Properties} from a specified {@link InputStream}
     * object.</p>
     *
     * <pre>
     * Util.readProperties(new FileInputStream(new File("example.properties")));
     * </pre>
     *
     * @param f {@link InputStream} file contains the {@link Properties}.
     * @return readed {@link Properties}.
     */
    public static Properties readProperties(InputStream is) {
        Validate.notNull(is);
        try {
            Validate.isTrue(is.available() > 0);
            Properties p = new Properties();
            p.load(is);
            return p;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    static void setWrapper(Wrapper newWrapper) {
        wrapper = newWrapper;
    }

    interface Wrapper {
        FileInputStream createFileInputStream(File file) throws IOException ;
    }

    static class DefaultWrapper implements Wrapper {
        @Override
        public FileInputStream createFileInputStream(File file) throws IOException {
            return new FileInputStream(file);
        }
    }
}
