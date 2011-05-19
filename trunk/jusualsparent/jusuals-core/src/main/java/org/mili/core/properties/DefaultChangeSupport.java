/*
 * DefaultChangeSupport.java
 *
 * 05.05.2010
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

package org.mili.core.properties;

import java.beans.*;

import org.apache.commons.lang.*;

/**
 * This class is a default implementation of interface {@link ChangeSupport}.
 *
 * @author Michael Lieshoff
 */
public class DefaultChangeSupport implements ChangeSupport {
    private Object source = null;
    private PropertyChangeSupport pcs = null;
    private VetoableChangeSupport vcs = null;

    /**
     * Instantiates a new default change support.
     *
     * @param source the source
     */
    protected DefaultChangeSupport(Object source) {
        super();
        this.source = source;
        this.vcs = new VetoableChangeSupport(source);
        this.pcs = new PropertyChangeSupport(source);
    }

    /**
     * Creates a change support.
     *
     * @param source the source
     * @return the default change support
     */
    public static DefaultChangeSupport create(Object source) {
        return new DefaultChangeSupport(source);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void addVetoableChangeListener(VetoableChangeListener listener) {
        this.vcs.addVetoableChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(String prop, PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(prop, listener);
    }

    @Override
    public void addVetoableChangeListener(String prop, VetoableChangeListener listener) {
        this.vcs.addVetoableChangeListener(prop, listener);
    }

    @Override
    public PropertyChangeSupport getPropertyChangeSupport() {
        return this.pcs;
    }

    @Override
    public VetoableChangeSupport getVetoableChangeSupport() {
        return this.vcs;
    }

    @Override
    public void firePropertyChange(String prop, Object o, Object n) {
        if (!ObjectUtils.equals(o, n)) {
            this.pcs.firePropertyChange(prop, o, n);
        }
    }

    @Override
    public void fireVetoableChange(String prop, Object o, Object n)
            throws PropertyVetoException {
        if (!ObjectUtils.equals(o, n)) {
            this.vcs.fireVetoableChange(prop, o, n);
        }
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    @Override
    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        this.vcs.removeVetoableChangeListener(listener);
    }

}
