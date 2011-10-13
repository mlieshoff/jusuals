/*
 * DefaultServiceData.java
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
public class DefaultServiceData implements ServiceData {
    private Map<String, String> parameters = null;
    private Service service = null;

    /**
     * Instantiates a new default service data.
     *
     * @param service the service
     * @param parameters the parameters
     */
    public DefaultServiceData(Service service, Map<String, String> parameters) {
        super();
        this.service = service;
        this.parameters = parameters;
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

}