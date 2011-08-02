/*
 * OptionalProps.java
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

/**
 * This class defines methods for access properties on optional way.
 *
 * @author Michael Lieshoff
 */

class OptionalProps extends GenericPropsWithDefault {

    OptionalProps() {
        super();
    }

    @Override
    <T> T getParameter(Map<String, ?> parameters, Properties properties, String key,
            Class<T> clazz) {
        return Props.getParameter(parameters, properties, key, Props.Usage.OPTIONAL, clazz);
    }

    @Override
    String get(Properties properties, String key) {
        return Props.get(properties, key, Props.Usage.OPTIONAL, String.class);
    }

    @Override
    <T> T get(Properties properties, String key, Class<T> clazz) {
        return Props.get(properties, key, Props.Usage.OPTIONAL, clazz);
    }

    @Override
    <T> T get(String key, Class<T> clazz, T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    <T> T get(String key, Class<T> clazz) {
        throw new UnsupportedOperationException();
    }

    @Override
    <T> T getParameter(Map<String, ?> parameters, String key, Class<T> clazz) {
        throw new UnsupportedOperationException();
    }

    @Override
    <T> T get(Properties properties, Class<?> clazz, String key, Class<T> typeClass) {
        return Props.get(properties, key, Props.Usage.OPTIONAL, typeClass);
    }

}
