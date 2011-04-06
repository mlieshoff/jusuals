/*
 * Transformator.java
 *
 * 15.10.2010
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
package org.mili.core.text.transformation;

import org.mili.core.text.*;


// TODO: Auto-generated Javadoc
/**
 * This interface defines a tranformator.
 *
 * @author Michael Lieshoff
 *
 */
public interface Transformator {

    /**
     * Transforms.
     *
     * @param from from table.
     * @param to to table.
     * @param params the params
     * @return the object as tranformed table
     */
    Object transform(Table from,  AvailableTransformations to, Object... params);

}
