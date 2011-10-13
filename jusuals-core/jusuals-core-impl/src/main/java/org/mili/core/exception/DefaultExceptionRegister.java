/*
 * DefaultExceptionRegister.java
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
 * This class defines a default implementation of an exception register.
 *
 * @author Michael Lieshoff
 *
 */
public class DefaultExceptionRegister implements ExceptionRegister {
    private List<WrappedException> l = new ArrayList<WrappedException>();

    /**
     * Instantiates a new default exception register.
     */
    protected DefaultExceptionRegister() {
        super();
    }

    @Override
    public void clear() {
        this.l.clear();
    }

    @Override
    public void add(WrappedException we) {
        this.l.add(we);
    }

    @Override
    public List<WrappedException> getWarnings() {
        List<WrappedException> l0 = new ArrayList<WrappedException>();
        for (int i = 0, n = this.l.size(); i < n; i++) {
            WrappedException we = l.get(i);
            if (we.getExceptionLevel() == ExceptionLevelEnum.WARN) {
                l0.add(we);
            }
        }
        return l0;
    }

    @Override
    public List<WrappedException> getErrors() {
        List<WrappedException> l0 = new ArrayList<WrappedException>();
        for (int i = 0, n = this.l.size(); i < n; i++) {
            WrappedException we = l.get(i);
            if (we.getExceptionLevel() == ExceptionLevelEnum.ERROR) {
                l0.add(we);
            }
        }
        return l0;
    }

    /**
     * Creates the.
     *
     * @return the default exception register
     */
    public static DefaultExceptionRegister create() {
        return new DefaultExceptionRegister();
    }
}
