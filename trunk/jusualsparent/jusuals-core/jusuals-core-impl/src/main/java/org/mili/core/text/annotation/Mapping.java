/*
 * Mapping.java
 *
 * 01.08.2011
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

package org.mili.core.text.annotation;

import java.lang.annotation.*;


/**
 * This annotation defines a mapping.
 *
 * @author Michael Lieshoff
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mapping {

    /**
     * Elements.
     *
     * @return the data[]
     */
    public Data[] elements();

    /**
     * Formatters.
     *
     * @return the format[]
     */
    public Format[] formatters();
}