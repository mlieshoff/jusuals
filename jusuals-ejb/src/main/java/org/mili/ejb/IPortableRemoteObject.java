/*
 * IPortableRemoteObject.java
 *
 * 08.03.2010
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

package org.mili.ejb;

/**
 * This class defines an interface to delegate methods to a protable remote object.
 *
 * @author Michael Lieshoff
 */
public interface IPortableRemoteObject {

    /**
     * Narrow.
     *
     * @param narrowFrom the narrow from
     * @param narrowTo the narrow to
     * @return the object
     * @throws ClassCastException the class cast exception
     */
    Object narrow(Object narrowFrom, Class<?> narrowTo) throws ClassCastException;

}
