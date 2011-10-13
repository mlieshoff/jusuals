/*
 * ServiceRegister.java
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

public interface ServiceRegister {

    /**
     * Register.
     *
     * @param service the service
     * @param parameters the parameters
     */
    void register(Service service, Map<String, String> parameters);

    /**
     * Gets the service data.
     *
     * @param serviceClass the service class
     * @return the service data
     */
    ServiceData getServiceData(Class<?> serviceClass);

}
