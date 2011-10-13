/*
 * DefaultServiceLocator.java
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

import javax.ejb.*;
import javax.naming.*;

import org.jnp.interfaces.*;
import org.mili.core.logging.*;


/**
 * This class defines a default implementation of a service locator.
 *
 * @author Michael Lieshoff
 */
public class DefaultServiceLocator implements ServiceLocator {
    private static Class<?> ID = DefaultServiceLocator.class;
    private static DefaultLogger log = DefaultLogger.getLogger(ID);
    private Context icRemote = null;
    private Context icLocal = null;
    private Hashtable<String, String> parameters = new Hashtable<String, String>();
    protected Map<String, Object> contextCacheLocal = new Hashtable<String, Object>();
    protected Map<String, Object> contextCacheRemote = new Hashtable<String, Object>();
    private String servername = System.getProperty(ID.getName() + ".Hostname", "localhost");
    private int port = Integer.getInteger(ID.getName() + ".Port", 1099);
    private int soTimeout = Integer.getInteger(ID.getName() + ".SoTimeout", 1000);
    private int timeout = Integer.getInteger(ID.getName() + ".Timeout", 1000);
    protected Map<String, EJBHome> homeCacheRemote = new Hashtable<String, EJBHome>();
    protected Map<String, EJBLocalHome> homeCacheLocal = new Hashtable<String, EJBLocalHome>();
    private ContextFactory cf = null;
    private PortableRemoteObjectFactory prof = null;

    /**
     * Instantiates a new default service locator.
     */
    protected DefaultServiceLocator() {
        super();
        this.cf = new DefaultContextFactory();
        this.prof = new DefaultPortableRemoteObjectFactory();
        this.getParameters().put(TimedSocketFactory.JNP_SO_TIMEOUT, String.valueOf(
                this.getSoTimeout()));
        this.getParameters().put(TimedSocketFactory.JNP_TIMEOUT, String.valueOf(
                this.getTimeout()));
        this.getParameters().put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
        this.getParameters().put(Context.URL_PKG_PREFIXES,
                "org.jboss.naming:org.jnp.interfaces");
        this.getParameters().put(Context.PROVIDER_URL, "jnp://" + this.getServername() + ":"
                + this.getPort());
    }

    @Override
    public Object getContextObject(String typename, boolean remote, boolean reconnect)
            throws NamingException {
        log.debug(log.begin("getContextObect"), "typename", typename, "remote", remote,
                "reconnect", reconnect);
        Object o = null;
        if (remote) {
            if (reconnect || this.icRemote == null) {
                this.icRemote = this.cf.createRemoteContext(this.parameters);
            }
            o = this.contextCacheRemote.get(typename);
            if (reconnect || o == null) {
                o = this.icRemote.lookup(typename);
                this.contextCacheRemote.put(typename, o);
            }
        } else {
            if (reconnect || this.icLocal == null) {
                this.icLocal = this.cf.createLocalContext();
            }
            o = this.contextCacheLocal.get(typename);
            if (reconnect || o == null) {
                o = this.icLocal.lookup("java:comp/env/" + typename);
                this.contextCacheLocal.put("java:comp/env/" + typename, o);
            }
        }
        log.debug(log.end("getContextObect"));
        return o;
    }

    /**
     * Creates the service locator.
     *
     * @return the service locator
     */
    public static ServiceLocator create() {
        return new DefaultServiceLocator();
    }

    @Override
    public Hashtable<String, String> getParameters() {
        return parameters;
    }

    @Override
    public String getServername() {
        return servername;
    }

    @Override
    public void setServername(String servername) {
        this.servername = servername;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int getSoTimeout() {
        return soTimeout;
    }

    @Override
    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    @Override
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public ContextFactory getContextFactory() {
        return this.cf;
    }

    @Override
    public PortableRemoteObjectFactory getPortableRemoteObjectFactory() {
        return this.prof;
    }

    @Override
    public void setContextFactory(ContextFactory cf) {
        this.cf = cf;
    }

    @Override
    public void setPortableRemoteObjectFactory(PortableRemoteObjectFactory prof) {
        this.prof = prof;
    }

    @Override
    public void clearCaches() {
        this.contextCacheLocal.clear();
        this.contextCacheRemote.clear();
        this.homeCacheLocal.clear();
        this.homeCacheRemote.clear();
    }

}
