/*
 * AbstractFilteredTracker.java
 *
 * 14.10.2010
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

package org.mili.core.service;

import java.util.*;

import org.apache.commons.lang.*;

/**
 * @author Michael Lieshoff
 */
public abstract class AbstractFilteredTracker implements FilteredTracker {
    private Set<String> filter = null;

    /**
     * Instantiates a new abstract filtered tracker.
     */
    protected AbstractFilteredTracker() {
        super();
    }

    /**
     * Instantiates a new abstract filtered tracker.
     *
     * @param filteredClasses the filtered classes
     */
    protected AbstractFilteredTracker(String filteredClasses) {
        if (!StringUtils.isEmpty(filteredClasses)) {
            this.filter = new HashSet<String>();
            String[] ss = filteredClasses.split("[,]");
            for (int i = 0; i < ss.length; i++) {
                filter.add(ss[i]);
            }
        }
    }

    @Override
    public boolean isFiltered(Service service) {
        Validate.notNull(service, "service can't be null!");
        if (this.filter != null) {
            return this.filter.contains(service.getClass().getSimpleName());
        } else {
            return true;
        }
    }

}
