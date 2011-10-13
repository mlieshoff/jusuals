/*
 * ContextFactory.java
 *
 * 11.03.2010
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

import java.util.*;

import javax.naming.*;

/**
 * This interface defines a context factory.
 *
 * @author Michael Lieshoff
 */
public interface ContextFactory {

    /**
     * Creates a new Context object.
     *
     * @return the context
     * @throws NamingException the naming exception
     */
    Context createLocalContext() throws NamingException;

    /**
     * Creates a new Context object.
     *
     * @param parameters the parameters
     * @return the context
     * @throws NamingException the naming exception
     */
    Context createRemoteContext(Hashtable<String, String> parameters) throws NamingException;

}
