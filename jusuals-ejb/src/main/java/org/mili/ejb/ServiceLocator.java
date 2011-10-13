/*
 * ServiceLocator.java
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
 * This interface defines a service locator.
 *
 * @author Michael Lieshoff
 */
public interface ServiceLocator {

    /**
     * Gets the context object.
     *
     * @param typename the typename
     * @param remote the remote
     * @param reconnect the reconnect
     * @return the context object
     * @throws NamingException the naming exception
     */
    Object getContextObject(String typename, boolean remote, boolean reconnect)
            throws NamingException;

    /**
     * Clear caches.
     */
    void clearCaches();

    /**
     * Gets the parameters.
     *
     * @return the parameters
     */
    Hashtable<String, String> getParameters();

    /**
     * Gets the servername.
     *
     * @return the servername
     */
    String getServername();

    /**
     * Sets the servername.
     *
     * @param servername the new servername
     */
    void setServername(String servername);

    /**
     * Gets the port.
     *
     * @return the port
     */
    int getPort();

    /**
     * Sets the port.
     *
     * @param port the new port
     */
    void setPort(int port);

    /**
     * Gets the so timeout.
     *
     * @return the so timeout
     */
    int getSoTimeout();

    /**
     * Sets the so timeout.
     *
     * @param soTimeout the new so timeout
     */
    void setSoTimeout(int soTimeout);

    /**
     * Gets the timeout.
     *
     * @return the timeout
     */
    int getTimeout();

    /**
     * Sets the timeout.
     *
     * @param timeout the new timeout
     */
    void setTimeout(int timeout);

    /**
     * Gets the context factory.
     *
     * @return the context factory
     */
    ContextFactory getContextFactory();

    /**
     * Sets the context factory.
     *
     * @param cf the new context factory
     */
    void setContextFactory(ContextFactory cf);

    /**
     * Gets the portable remote object factory.
     *
     * @return the portable remote object factory
     */
    PortableRemoteObjectFactory getPortableRemoteObjectFactory();

    /**
     * Sets the portable remote object factory.
     *
     * @param prof the new portable remote object factory
     */
    void setPortableRemoteObjectFactory(PortableRemoteObjectFactory prof);

}
