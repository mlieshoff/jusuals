/*
 * DefaultContextFactory.java
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
 * This class defines a default implementation of interface ContextFactory.
 *
 * @author Michael Lieshoff
 */
public class DefaultContextFactory implements ContextFactory {

    /**
     * Instantiates a new default context factory.
     */
    protected DefaultContextFactory() {
        super();
    }

    @Override
    public Context createLocalContext() throws NamingException {
        return new InitialContext();
    }

    @Override
    public Context createRemoteContext(Hashtable<String, String> parameters)
            throws NamingException {
        return new InitialContext(parameters);
    }

    /**
     * Creates the context factory.
     *
     * @return the context factory
     */
    public static ContextFactory create() {
        return new DefaultContextFactory();
    }
}
