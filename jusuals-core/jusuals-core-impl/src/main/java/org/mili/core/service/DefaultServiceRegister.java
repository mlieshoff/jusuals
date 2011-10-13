/*
 * DefaultServiceRegister.java
 *
 * 18.11.2009
 *
 * Copyright 2009 Michael Lieshoff
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
package org.mili.core.service;


import java.util.*;


/**
 * @author Michael Lieshoff
 */

public class DefaultServiceRegister implements ServiceRegister {
    private Map<Class<?>, DefaultServiceData> register = new Hashtable<Class<?>, DefaultServiceData>();

    /**
     * Instantiates a new default service register.
     */
    public DefaultServiceRegister() {
        super();
    }

    @Override
    public void register(Service s, Map<String, String> p) {
        DefaultServiceData sd = new DefaultServiceData(s, p);
        this.register.put(s.getClass(), sd);
        return;
    }

    @Override
    public ServiceData getServiceData(Class<?> s) {
        return this.register.get(s);
    }

}
