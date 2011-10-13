/*
 * GenericProps.java
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

import java.util.*;

import org.apache.commons.lang.*;

/**
 * This class defines methods for access properties.
 *
 * @author Michael Lieshoff
 */
abstract class GenericProps {

    // byte
    byte getByte(Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getByte(object.getClass(), key);
    }

    byte getByte(Class<?> clazz, String key) {
        return getByte(Props.createName(clazz, key));
    }

    byte getByte(String key) {
        return get(key, Byte.class);
    }

    byte getByte(Properties properties, Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getByte(properties, object.getClass(), key);
    }

    byte getByte(Properties properties, Class<?> clazz, String key) {
        return getByte(properties, Props.createName(clazz, key));
    }

    byte getByte(Properties properties, String key) {
        return get(properties, key, Byte.class);
    }

    // short
    short getShort(Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getShort(object.getClass(), key);
    }

    short getShort(Class<?> clazz, String key) {
        return getShort(Props.createName(clazz, key));
    }

    short getShort(String key) {
        return get(key, Short.class);
    }

    short getShort(Properties properties, Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getShort(properties, object.getClass(), key);
    }

    short getShort(Properties properties, Class<?> clazz, String key) {
        return getShort(properties, Props.createName(clazz, key));
    }

    short getShort(Properties properties, String key) {
        return get(properties, key, Short.class);
    }

    // int
    int getInteger(Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getInteger(object.getClass(), key);
    }

    int getInteger(Class<?> clazz, String key) {
        return getInteger(Props.createName(clazz, key));
    }

    int getInteger(String key) {
        return get(key, Integer.class);
    }

    int getInteger(Properties properties, Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getInteger(properties, object.getClass(), key);
    }

    int getInteger(Properties properties, Class<?> clazz, String key) {
        return getInteger(properties, Props.createName(clazz, key));
    }

    int getInteger(Properties properties, String key) {
        return get(properties, key, Integer.class);
    }

    // char
    char getChar(Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getChar(object.getClass(), key);
    }

    char getChar(Class<?> clazz, String key) {
        return getChar(Props.createName(clazz, key));
    }

    char getChar(String key) {
        return get(key, Character.class).charValue();
    }

    char getChar(Properties properties, Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getChar(properties, object.getClass(), key);
    }

    char getChar(Properties properties, Class<?> clazz, String key) {
        return getChar(properties, Props.createName(clazz, key));
    }

    char getChar(Properties properties, String key) {
        return get(properties, key, Character.class);
    }

    // long
    long getLong(Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getLong(object.getClass(), key);
    }

    long getLong(Class<?> clazz, String key) {
        return getLong(Props.createName(clazz, key));
    }

    long getLong(String key) {
        return get(key, Long.class);
    }

    long getLong(Properties properties, Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getLong(properties, object.getClass(), key);
    }

    long getLong(Properties properties, Class<?> clazz, String key) {
        return getLong(properties, Props.createName(clazz, key));
    }

    long getLong(Properties properties, String key) {
        return get(properties, key, Long.class);
    }

    // float
    float getFloat(Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getFloat(object.getClass(), key);
    }

    float getFloat(Class<?> clazz, String key) {
        return getFloat(Props.createName(clazz, key));
    }

    float getFloat(String key) {
        return get(key, Float.class);
    }

    float getFloat(Properties properties, Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getFloat(properties, object.getClass(), key);
    }

    float getFloat(Properties properties, Class<?> clazz, String key) {
        return getFloat(properties, Props.createName(clazz, key));
    }

    float getFloat(Properties properties, String key) {
        return get(properties, key, Float.class);
    }

    // double
    double getDouble(Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getDouble(object.getClass(), key);
    }

    double getDouble(Class<?> clazz, String key) {
        return getDouble(Props.createName(clazz, key));
    }

    double getDouble(String key) {
        return get(key, Double.class);
    }

    double getDouble(Properties properties, Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getDouble(properties, object.getClass(), key);
    }

    double getDouble(Properties properties, Class<?> clazz, String key) {
        return getDouble(properties, Props.createName(clazz, key));
    }

    double getDouble(Properties properties, String key) {
        return get(properties, key, Double.class);
    }

    // boolean
    boolean getBoolean(Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getBoolean(object.getClass(), key);
    }

    boolean getBoolean(Class<?> clazz, String key) {
        return getBoolean(Props.createName(clazz, key));
    }

    boolean getBoolean(String key) {
        return get(key, Boolean.class);
    }

    boolean getBoolean(Properties properties, Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getBoolean(properties, object.getClass(), key);
    }

    boolean getBoolean(Properties properties, Class<?> clazz, String key) {
        return getBoolean(properties, Props.createName(clazz, key));
    }

    boolean getBoolean(Properties properties, String key) {
        return get(properties, key, Boolean.class);
    }

    // String
    String getString(Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getString(object.getClass(), key);
    }

    String getString(Class<?> clazz, String key) {
        return getString(Props.createName(clazz, key));
    }

    String getString(String key) {
        return get(key, String.class);
    }

    String getString(Properties properties, Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return getString(properties, object.getClass(), key);
    }

    String getString(Properties properties, Class<?> clazz, String key) {
        return getString(properties, Props.createName(clazz, key));
    }

    String getString(Properties properties, String key) {
        return get(properties, key, String.class);
    }

    // Parameter
    String getParameter(Map<String, ?> parameters, String key) {
        return getParameter(parameters, key, String.class);
    }

    String getParameter(Map<String, ?> parameters, Properties properties, String key) {
        return getParameter(parameters, properties, key, String.class);
    }

    // bottom
    String get(Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return get(object.getClass(), key);
    }

    String get(Class<?> clazz, String key) {
        return get(Props.createName(clazz, key));
    }

    String get(String key) {
        return get(key, String.class);
    }

    String get(Properties properties, Object object, String key) {
        Validate.notNull(object, "object cannot be null!");
        return get(properties, object.getClass(), key);
    }

    String get(Properties properties, Class<?> clazz, String key) {
        return get(properties, Props.createName(clazz, key));
    }

    String get(Properties properties, String key) {
        return get(properties, key, String.class);
    }

    // abstracts

    abstract <T> T get(String key, Class<T> clazz);
    abstract <T> T get(Properties properties, String key, Class<T> clazz);
    abstract <T> T getParameter(Map<String, ?> parameters, String key, Class<T> clazz);
    abstract <T> T getParameter(Map<String, ?> parameters, Properties properties, String key,
            Class<T> clazz);

}
