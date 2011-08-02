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
import org.mili.core.properties.Props.*;

/**
 * This utility class defines some useful methods to operate with properties.
 *
 * @author Michael Lieshoff
 *
 */
public class PropUtil {
    private static Creator creator = new CreatorImpl();
    private static Closer closer = new CloserImpl();
    private static Availabler availabler = new AvailablerImpl();
    private static SystemRequiredProps systemRequiredProps = new SystemRequiredProps();
    private static SystemOptionalProps systemOptionalProps = new SystemOptionalProps();
    private static RequiredProps requiredProps = new RequiredProps();
    private static OptionalProps optionalProps = new OptionalProps();

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
        Validate.notNull(f, "file cannot be null!");
        Validate.isTrue(f.exists(), "file not exists! Base is: " + new File(""));
        InputStream is = null;
        try {
            is = creator.create(f);
            return readProperties(is);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            if (is != null) {
                try {
                    closer.close(is);
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
     * @param is {@link InputStream} file contains the {@link Properties}.
     * @return readed {@link Properties}.
     */
    public static Properties readProperties(InputStream is) {
        Validate.notNull(is, "input stream cannot be null!");
        try {
            Validate.isTrue(availabler.available(is) > 0, "input stream not available!");
            Properties p = new Properties();
            p.load(is);
            return p;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Change system properties.
     *
     * @param properties the properties
     */
    public static void changeSystemProperties(Properties properties) {
        Props.SYSTEM_PROPERTIES = properties;
    }

    public static String getSystemRequired(Object object, String key) {
        return systemRequiredProps.get(object, key);
    }

    public static String getSystemRequired(Class<?> clazz, String key) {
        return systemRequiredProps.get(clazz, key);
    }

    public static String getSystemRequired(String key) {
        return systemRequiredProps.get(key);
    }

    public static <T> T getSystemRequired(String key, Class<T> clazz) {
        return systemRequiredProps.get(key, clazz);
    }

    /* system required methods: byte */

    public static byte getSystemRequiredByte(Object o, String k) {
        return systemRequiredProps.getByte(o, k);
    }

    public static byte getSystemRequiredByte(Class<?> c, String k) {
        return systemRequiredProps.getByte(c, k);
    }

    public static byte getSystemRequiredByte(String k) {
        return systemRequiredProps.getByte(k);
    }

    /* system required methods: boolean */

    public static boolean getSystemRequiredBoolean(Object o, String k) {
        return systemRequiredProps.getBoolean(o, k);
    }

    public static boolean getSystemRequiredBoolean(Class<?> c, String k) {
        return systemRequiredProps.getBoolean(c, k);
    }

    public static boolean getSystemRequiredBoolean(String k) {
        return systemRequiredProps.getBoolean(k);
    }

    /* system required methods: short */

    public static short getSystemRequiredShort(Object o, String k) {
        return systemRequiredProps.getShort(o, k);
    }

    public static short getSystemRequiredShort(Class<?> c, String k) {
        return systemRequiredProps.getShort(c, k);
    }

    public static short getSystemRequiredShort(String k) {
        return systemRequiredProps.getShort(k);
    }

    /* system required methods: int */

    public static int getSystemRequiredInteger(Object o, String k) {
        return systemRequiredProps.getInteger(o, k);
    }

    public static int getSystemRequiredInteger(Class<?> c, String k) {
        return systemRequiredProps.getInteger(c, k);
    }

    public static int getSystemRequiredInteger(String k) {
        return systemRequiredProps.getInteger(k);
    }

    /* system required methods: char */

    public static char getSystemRequiredChar(Object o, String k) {
        return systemRequiredProps.getChar(o, k);
    }

    public static char getSystemRequiredChar(Class<?> c, String k) {
        return systemRequiredProps.getChar(c, k);
    }

    public static char getSystemRequiredChar(String k) {
        return systemRequiredProps.getChar(k);
    }

    /* system required methods: float */

    public static float getSystemRequiredFloat(Object o, String k) {
        return systemRequiredProps.getFloat(o, k);
    }

    public static float getSystemRequiredFloat(Class<?> c, String k) {
        return systemRequiredProps.getFloat(c, k);
    }

    public static float getSystemRequiredFloat(String k) {
        return systemRequiredProps.getFloat(k);
    }

    /* system required methods: long */

    public static long getSystemRequiredLong(Object o, String k) {
        return systemRequiredProps.getLong(o, k);
    }

    public static long getSystemRequiredLong(Class<?> c, String k) {
        return systemRequiredProps.getLong(c, k);
    }

    public static long getSystemRequiredLong(String k) {
        return systemRequiredProps.getLong(k);
    }

    /* system required methods: double */

    public static double getSystemRequiredDouble(Object o, String k) {
        return systemRequiredProps.getDouble(o, k);
    }

    public static double getSystemRequiredDouble(Class<?> c, String k) {
        return systemRequiredProps.getDouble(c, k);
    }

    public static double getSystemRequiredDouble(String k) {
        return systemRequiredProps.getDouble(k);
    }

    /* system required methods: parameter */

    public static String getSystemRequiredParameter(Map<String, ?> ps, String k) {
        return systemRequiredProps.getParameter(ps, k);
    }

    public static <T> T getSystemRequiredParameter(Map<String, ?> ps, String k, Class<T> c) {
        return systemRequiredProps.getParameter(ps, k, c);
    }

    /* system methods */

    public static String getSystem(Object object, String key) {
        return systemOptionalProps.get(object, key);
    }

    public static String getSystem(Object o, String k, String dv) {
        return systemOptionalProps.get(o, k, dv);
    }

    public static String getSystem(Class<?> c, String k) {
        return systemOptionalProps.get(c, k);
    }

    public static String getSystem(Class<?> c, String k, String dv) {
        return systemOptionalProps.get(c, k, dv);
    }

    public static String getSystem(String k) {
        return systemOptionalProps.get(k);
    }

    public static String getSystem(String k, String dv) {
        return systemOptionalProps.get(k, dv);
    }

    public static <T> T getSystem(String k, Class<T> c, T dv) {
        return systemOptionalProps.get(k, c, dv);
    }

    public static <T> T getSystem(String k, Class<T> c) {
        return systemOptionalProps.get(k, c);
    }

    /* system methods: byte */

    public static byte getSystemByte(Object o, String k) {
        return systemOptionalProps.getByte(o, k);
    }

    public static byte getSystemByte(Class<?> c, String k) {
        return systemOptionalProps.getByte(c, k);
    }

    public static byte getSystemByte(String k) {
        return systemOptionalProps.getByte(k);
    }

    /* system methods: boolean */

    public static boolean getSystemBoolean(Object o, String k) {
        return systemOptionalProps.getBoolean(o, k);
    }

    public static boolean getSystemBoolean(Class<?> c, String k) {
        return systemOptionalProps.getBoolean(c, k);
    }

    public static boolean getSystemBoolean(String k) {
        return systemOptionalProps.getBoolean(k);
    }

    /* system methods: short */

    public static short getSystemShort(Object o, String k) {
        return systemOptionalProps.getShort(o, k);
    }

    public static short getSystemShort(Class<?> c, String k) {
        return systemOptionalProps.getShort(c, k);
    }

    public static short getSystemShort(String k) {
        return systemOptionalProps.getShort(k);
    }

    /* system methods: int */

    public static int getSystemInteger(Object o, String k) {
        return systemOptionalProps.getInteger(o, k);
    }

    public static int getSystemInteger(Class<?> c, String k) {
        return systemOptionalProps.getInteger(c, k);
    }

    public static int getSystemInteger(String k) {
        return systemOptionalProps.getInteger(k);
    }

    public static int getSystemInteger(Object o, String k, int dv) {
        return systemOptionalProps.getInteger(o, k, dv);
    }

    public static int getSystemInteger(Class<?> c, String k, int dv) {
        return systemOptionalProps.getInteger(c, k, dv);
    }

    public static int getSystemInteger(String k, int dv) {
        return systemOptionalProps.getInteger(k, dv);
    }

    /* system methods: char */

    public static char getSystemChar(Object o, String k) {
        return systemOptionalProps.getChar(o, k);
    }

    public static char getSystemChar(Class<?> c, String k) {
        return systemOptionalProps.getChar(c, k);
    }

    public static char getSystemChar(String k) {
        return systemOptionalProps.getChar(k);
    }

    /* system methods: float */

    public static float getSystemFloat(Object o, String k) {
        return systemOptionalProps.getFloat(o, k);
    }

    public static float getSystemFloat(Class<?> c, String k) {
        return systemOptionalProps.getFloat(c, k);
    }

    public static float getSystemFloat(String k) {
        return systemOptionalProps.getFloat(k);
    }

    /* system methods: long */

    public static long getSystemLong(Object o, String k) {
        return systemOptionalProps.getLong(o, k);
    }

    public static long getSystemLong(Class<?> c, String k) {
        return systemOptionalProps.getLong(c, k);
    }

    public static long getSystemLong(String k) {
        return systemOptionalProps.getLong(k);
    }

    public static long getSystemLong(Object o, String k, long dv) {
        return systemOptionalProps.getLong(o, k, dv);
    }

    public static long getSystemLong(Class<?> c, String k, long dv) {
        return systemOptionalProps.getLong(c, k, dv);
    }

    public static long getSystemLong(String k, long dv) {
        return systemOptionalProps.getLong(k, dv);
    }

    /* system methods: double */

    public static double getSystemDouble(Object o, String k) {
        return systemOptionalProps.getDouble(o, k);
    }

    public static double getSystemDouble(Class<?> c, String k) {
        return systemOptionalProps.getDouble(c, k);
    }

    public static double getSystemDouble(String k) {
        return systemOptionalProps.getDouble(k);
    }

    /* system methods: parameter */

    public static String getSystemParameter(Map<String, ?> ps, String k) {
        return getSystemParameter(ps, k, String.class);
    }

    public static <T> T getSystemParameter(Map<String, ?> ps, String k, Class<T> c) {
        return getParameter(ps, Props.SYSTEM_PROPERTIES, k, Props.Usage.OPTIONAL, c);
    }

    /* required methods */

    public static String getRequired(Properties p, Object o, String k) {
        return requiredProps.get(p, o, k);
    }

    public static String getRequired(Properties p, Class<?> c, String k) {
        return requiredProps.get(p, c, k);
    }

    /* required methods: byte */
    public static byte getRequiredByte(Properties p, Object o, String k) {
        return requiredProps.getByte(p, o, k);
    }

    public static byte getRequiredByte(Properties p, Class<?> c, String k) {
        return requiredProps.getByte(p, c, k);
    }

    public static byte getRequiredByte(Properties p, String k) {
        return requiredProps.getByte(p, k);
    }

    /* required methods: boolean */
    public static boolean getRequiredBoolean(Properties p, Object o, String k) {
        return requiredProps.getBoolean(p, o, k);
    }

    public static boolean getRequiredBoolean(Properties p, Class<?> c, String k) {
        return requiredProps.getBoolean(p, c, k);
    }

    public static boolean getRequiredBoolean(Properties p, String k) {
        return requiredProps.getBoolean(p, k);
    }

    /* required methods: short */
    public static short getRequiredShort(Properties p, Object o, String k) {
        return requiredProps.getShort(p, o, k);
    }

    public static short getRequiredShort(Properties p, Class<?> c, String k) {
        return requiredProps.getShort(p, c, k);
    }

    public static short getRequiredShort(Properties p, String k) {
        return getRequired(p, k, Short.class);
    }

    /* required methods: int */
    public static int getRequiredInteger(Properties p, Object o, String k) {
        return requiredProps.getInteger(p, o, k);
    }

    public static int getRequiredInteger(Properties p, Class<?> c, String k) {
        return requiredProps.getInteger(p, c, k);
    }

    public static int getRequiredInteger(Properties p, String k) {
        return requiredProps.getInteger(p, k);
    }

    /* required methods: char */
    public static char getRequiredChar(Properties p, Object o, String k) {
        return requiredProps.getChar(p, o, k);
    }

    public static char getRequiredChar(Properties p, Class<?> c, String k) {
        return requiredProps.getChar(p, c, k);
    }

    public static char getRequiredChar(Properties p, String k) {
        return requiredProps.getChar(p, k);
    }

    /* required methods: float */
    public static float getRequiredFloat(Properties p, Object o, String k) {
        return requiredProps.getFloat(p, o, k);
    }

    public static float getRequiredFloat(Properties p, Class<?> c, String k) {
        return requiredProps.getFloat(p, c, k);
    }

    public static float getRequiredFloat(Properties p, String k) {
        return requiredProps.getFloat(p, k);
    }

    /* required methods: long */
    public static long getRequiredLong(Properties p, Object o, String k) {
        return requiredProps.getLong(p, o, k);
    }

    public static long getRequiredLong(Properties p, Class<?> c, String k) {
        return requiredProps.getLong(p, c, k);
    }

    public static long getRequiredLong(Properties p, String k) {
        return requiredProps.getLong(p, k);
    }

    /* required methods: double */
    public static double getRequiredDouble(Properties p, Object o, String k) {
        return requiredProps.getDouble(p, o, k);
    }

    public static double getRequiredDouble(Properties p, Class<?> c, String k) {
        return requiredProps.getDouble(p, c, k);
    }

    public static double getRequiredDouble(Properties p, String k) {
        return requiredProps.getDouble(p, k);
    }

    /* required methods: parameter */
    public static String getRequiredParameter(Map<String, ?> ps, Properties p, String k) {
        return requiredProps.getParameter(ps, p, k);
    }

    public static <T> T getRequiredParameter(Map<String, ?> ps, String k, Class<T> cls) {
        return requiredProps.getParameter(ps, k, cls);
    }

    public static <T> T getRequiredParameter(Map<String, ?> ps, Properties p, String k,
            Class<T> c) {
        return requiredProps.getParameter(ps, p, k, c);
    }

    /* required methods: bottom */
    public static String getRequired(Properties p, String k) {
        return requiredProps.get(p, k);
    }

    public static <T> T getRequired(Properties p, String k, Class<T> c) {
        return requiredProps.get(p, k, c);
    }

    /* optional methods: byte */
    public static byte getByte(Properties p, Object o, String k) {
        return optionalProps.getByte(p, o, k);
    }

    public static byte getByte(Properties p, Class<?> c, String k) {
        return optionalProps.getByte(p, c, k);
    }

    public static byte getByte(Properties p, String k) {
        return optionalProps.getByte(p, k);
    }

    /* optional methods: boolean */
    public static boolean getBoolean(Properties p, Object o, String k) {
        return optionalProps.getBoolean(p, o, k);
    }

    public static boolean getBoolean(Properties p, Class<?> c, String k) {
        return optionalProps.getBoolean(p, c, k);
    }

    public static boolean getBoolean(Properties p, String k) {
        return optionalProps.getBoolean(p, k);
    }

    /* optional methods: short */
    public static short getShort(Properties p, Object o, String k) {
        return optionalProps.getShort(p, o, k);
    }

    public static short getShort(Properties p, Class<?> c, String k) {
        return optionalProps.getShort(p, c, k);
    }

    public static short getShort(Properties p, String k) {
        return optionalProps.getShort(p, k);
    }

    /* optional methods: int */
    public static int getInteger(Properties p, Object o, String k) {
        return optionalProps.getInteger(p, o, k);
    }

    public static int getInteger(Properties p, Class<?> c, String k) {
        return optionalProps.getInteger(p, c, k);
    }

    public static int getInteger(Properties p, String k) {
        return optionalProps.getInteger(p, k);
    }

    /* optional methods: char */
    public static char getChar(Properties p, Object o, String k) {
        return optionalProps.getChar(p, o, k);
    }

    public static char getChar(Properties p, Class<?> c, String k) {
        return optionalProps.getChar(p, c, k);
    }

    public static char getChar(Properties p, String k) {
        return optionalProps.getChar(p, k);
    }

    /* optional methods: float */
    public static float getFloat(Properties p, Object o, String k) {
        return optionalProps.getFloat(p, o, k);
    }

    public static float getFloat(Properties p, Class<?> c, String k) {
        return optionalProps.getFloat(p, c, k);
    }

    public static float getFloat(Properties p, String k) {
        return optionalProps.getFloat(p, k);
    }

    /* optional methods: long */
    public static long getLong(Properties p, Object o, String k) {
        return optionalProps.getLong(p, o, k);
    }

    public static long getLong(Properties p, Class<?> c, String k) {
        return optionalProps.getLong(p, c, k);
    }

    public static long getLong(Properties p, String k) {
        return optionalProps.getLong(p, k);
    }

    /* optional methods: long */
    public static double getDouble(Properties p, Object o, String k) {
        return optionalProps.getDouble(p, o, k);
    }

    public static double getDouble(Properties p, Class<?> c, String k) {
        return optionalProps.getDouble(p, c, k);
    }

    public static double getDouble(Properties p, String k) {
        return optionalProps.getDouble(p, k);
    }

    /* optional methods: bottom */
    public static String get(Properties p, String k) {
        return optionalProps.get(p, k);
    }

    public static <T> T get(Properties p, String k, Class<T> c) {
        return optionalProps.get(p, k, c);
    }

    public static String get(Properties p, Object o, String k) {
        return optionalProps.get(p, o, k);
    }

    public static String get(Properties p, Class<?> c, String k) {
        return optionalProps.get(p, c, k);
    }

    public static <T> T get(Properties p, Class<?> c, String k, Class<T> cls) {
        return optionalProps.get(p, c, k, cls);
    }

    /* bottom methods: byte */
    public static byte getByte(Properties p, Object o, String k, Usage u) {
        return Props.getByte(p, o, k, u);
    }

    public static byte getByte(Properties p, Class<?> c, String k, Usage u) {
        return Props.getByte(p, c, k, u);
    }

    public static byte getByte(Properties p, String k, Usage u) {
        return Props.getByte(p, k, u);
    }

    /* bottom methods: boolean */
    public static boolean getBoolean(Properties p, Object o, String k, Usage u) {
        return Props.getBoolean(p, o, k, u);
    }

    public static boolean getBoolean(Properties p, Class<?> c, String k, Usage u) {
        return Props.getBoolean(p, c, k, u);
    }

    public static boolean getBoolean(Properties p, String k, Usage u) {
        return Props.getBoolean(p, k, u);
    }

    /* bottom methods: short */
    public static short getShort(Properties p, Object o, String k, Usage u) {
        return Props.getShort(p, o, k, u);
    }

    public static short getShort(Properties p, Class<?> c, String k, Usage u) {
        return Props.getShort(p, c, k, u);
    }

    public static short getShort(Properties p, String k, Usage u) {
        return Props.getShort(p, k, u);
    }

    /* bottom methods: int */
    public static int getInteger(Properties p, Object o, String k, Usage u) {
        return Props.getInteger(p, o, k, u);
    }

    public static int getInteger(Properties p, Class<?> c, String k, Usage u) {
        return Props.getInteger(p, c, k, u);
    }

    public static int getInteger(Properties p, String k, Usage u) {
        return Props.getInteger(p, k, u);
    }

    /* bottom methods: char */
    public static char getChar(Properties p, Object o, String k, Usage u) {
        return Props.getChar(p, o, k, u);
    }

    public static char getChar(Properties p, Class<?> c, String k, Usage u) {
        return Props.getChar(p, c, k, u);
    }

    public static char getChar(Properties p, String k, Usage u) {
        return Props.getChar(p, k, u);
    }

    /* bottom methods: float */
    public static float getFloat(Properties p, Object o, String k, Usage u) {
        return Props.getFloat(p, o, k, u);
    }

    public static float getFloat(Properties p, Class<?> c, String k, Usage u) {
        return Props.getFloat(p, c, k, u);
    }

    public static float getFloat(Properties p, String k, Usage u) {
        return Props.getFloat(p, k, u);
    }

    /* bottom methods: long */
    public static long getLong(Properties p, Object o, String k, Usage u) {
        return Props.getLong(p, o, k, u);
    }

    public static long getLong(Properties p, Class<?> c, String k, Usage u) {
        return Props.getLong(p, c, k, u);
    }

    public static long getLong(Properties p, String k, Usage u) {
        return Props.getLong(p, k, u);
    }

    /* bottom methods: double */
    public static double getDouble(Properties p, Object o, String k, Usage u) {
        return Props.getDouble(p, o, k, u);
    }

    public static double getDouble(Properties p, Class<?> c, String k, Usage u) {
        return Props.getDouble(p, c, k, u);
    }

    public static double getDouble(Properties p, String k, Usage u) {
        return Props.getDouble(p, k, u);
    }

    /* bottom methods: parameter */
    public static <T> T getParameter(Map<String, ?> ps, Properties p, String k, Usage u,
            Class<T> c) {
        return Props.getParameter(ps, p, k, u, c);
    }

    /* bottom methods */
    public static String get(Properties p, Object o, String k, Usage u) {
        return Props.get(p, o, k, u);
    }

    public static String get(Properties p, Class<?> c, String k, Usage u) {
        return Props.get(p, c, k, u);
    }

    public static String get(Properties p, String k, Usage u) {
        return Props.get(p, k, u);
    }

    public static <T> T get(Properties p, String k, Usage u, Class<T> c) {
        return Props.get(p, k, u, c);
    }

    public static <T> T get(Properties p, String k, Usage u, Class<T> c, T dv) {
        return Props.get(p, k, u, c, dv);
    }

    /* bottom methods: set */

    /**
     * Sets the the value.
     *
     * @param properties the properties
     * @param object the object
     * @param key the key
     * @param value the value
     */
    public static void set(Properties properties, Object object, String key, String value) {
        Props.set(properties, object, key, value);
    }

    /**
     * Sets the value.
     *
     * @param properties the properties
     * @param clazz the clazz
     * @param key the key
     * @param value the value
     */
    public static void set(Properties properties, Class clazz, String key, String value) {
        Props.set(properties, clazz, key, value);
    }

    /**
     * Sets the value.
     *
     * @param properties the properties
     * @param key the key
     * @param value the value
     */
    public static void set(Properties properties, String key, String value) {
        Props.set(properties, key, value);
    }

    /**
     * Sets the system value.
     *
     * @param object the object
     * @param key the key
     * @param value the value
     */
    public static void setSystem(Object object, String key, String value) {
        Validate.notNull(object, "object cannot be null!");
        setSystem(object.getClass(), key, value);
    }

    /**
     * Sets the system value.
     *
     * @param clazz the clazz
     * @param key the key
     * @param value the value
     */
    public static void setSystem(Class<?> clazz, String key, String value) {
        setSystem(createName(clazz, key), value);
    }

    /**
     * Sets the system value.
     *
     * @param key the key
     * @param value the value
     */
    public static void setSystem(String key, String value) {
        set(Props.SYSTEM_PROPERTIES, key, value);
    }

    /* utility methods */

    /**
     * Creates the name.
     *
     * @param object the object
     * @param key the key
     * @return the string
     */
    public static String createName(Object object, String key) {
        return Props.createName(object, key);
    }

    /**
     * Creates the name.
     *
     * @param clazz the clazz
     * @param key the key
     * @return the string
     */
    public static String createName(Class<?> clazz, String key) {
        return Props.createName(clazz, key);
    }

    /**
     * Creates the name.
     *
     * @param name1 the name1
     * @param name2 the name2
     * @return the string
     */
    public static String createName(String name1, String name2) {
        return Props.createName(name1, name2);
    }

    static void setCreator(Creator newCreator) {
        creator = newCreator;
    }

    static void setCloser(Closer newCloser) {
        closer = newCloser;
    }

    static void setAvailabler(Availabler newAvailabler) {
        availabler = newAvailabler;
    }

    interface Creator {
        FileInputStream create(File file) throws IOException;
    }

    static class CreatorImpl implements Creator {
        @Override
        public FileInputStream create(File file) throws IOException {
            return new FileInputStream(file);
        }
    }

    interface Closer {
        void close(InputStream is) throws IOException;
    }

    static class CloserImpl implements Closer {
        @Override
        public void close(InputStream is) throws IOException {
            is.close();
        }
    }

    interface Availabler {
        int available(InputStream is) throws IOException;
    }

    static class AvailablerImpl implements Availabler {
        @Override
        public int available(InputStream is) throws IOException {
            return is.available();
        }
    }

}
