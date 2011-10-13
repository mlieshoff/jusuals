/*
 * Exceptionregister.java
 *
 * 09.02.2010
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

package org.mili.core.exception;

import java.util.*;

/**
 * This interface defines an exception register.
 *
 * @author Michael Lieshoff
 *
 */
public interface ExceptionRegister {
    /**
     * Clear.
     */
    void clear();

    /**
     * Adds the.
     *
     * @param wrappedException the wrapped exception
     */
    void add(WrappedException wrappedException);

    /**
     * Gets the warnings.
     *
     * @return the warnings
     */
    List<WrappedException> getWarnings();

    /**
     * Gets the errors.
     *
     * @return the errors
     */
    List<WrappedException> getErrors();
}
