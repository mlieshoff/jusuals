/*
 * ResourceSupport.java
 *
 * 09.10.2009
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
package org.mili.core.resource;

/**
 * This interface defines a class resource supportable.
 *
 * @author Michael Lieshoff
 *
 */
public interface ResourceSupport {

    /**
     * Gets the resource manager.
     *
     * @return the resource manager
     */
    ResourceManager getResourceManager();

    /**
     * Sets the resource manager.
     *
     * @param rm the new resource manager
     */
    void setResourceManager(ResourceManager rm);

}
