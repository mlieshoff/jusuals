/*
 * DefaultChangeSupportTest.java
 *
 * 19.05.2011
 *
 * Copyright 2011 Michael Lieshoff
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
import org.junit.*;

import static org.junit.Assert.*;


/**
 * @author Michael Lieshoff
 */
public class DefaultChangeSupportTest {
    private DefaultChangeSupport support = null;
    private PropertyChangeEvent changeEvent = null;
    private PropertyChangeEvent vetoEvent = null;
    private PropertyChangeListener changeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            changeEvent = evt;
        }
    };
    private VetoableChangeListener vetoableListener = new VetoableChangeListener() {
        @Override
        public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
            vetoEvent = evt;
        }
    };

    @Before
    public void setUp() throws Exception {
        this.support = new DefaultChangeSupport("abbas");
        this.vetoEvent = null;
        this.changeEvent = null;
    }

    @Test
    public void shouldConstruct() {
        new DefaultChangeSupport("abbas");
    }

    @Test
    public void shouldCreate() {
        DefaultChangeSupport.create("abbas");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstruct() {
        new DefaultChangeSupport(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failCreate() {
        DefaultChangeSupport.create(null);
    }

    @Test
    public void shouldAddPropertyChangeListener() {
        this.support.addPropertyChangeListener(this.changeListener);
        assertTrue(ArrayUtils.contains(this.support.getPropertyChangeSupport()
                .getPropertyChangeListeners(), this.changeListener));
    }

    @Test
    public void shouldRemovePropertyChangeListener() {
        this.support.addPropertyChangeListener(this.changeListener);
        this.support.removePropertyChangeListener(this.changeListener);
        assertFalse(ArrayUtils.contains(this.support.getPropertyChangeSupport()
                .getPropertyChangeListeners(), this.changeListener));
    }

    @Test
    public void shouldAddPropertyChangeListenerForProp() {
        this.support.addPropertyChangeListener("abc", this.changeListener);
        this.support.firePropertyChange("abc", "1", "2");
        assertEquals("abc", this.changeEvent.getPropertyName());
        assertEquals("1", this.changeEvent.getOldValue());
        assertEquals("2", this.changeEvent.getNewValue());
    }

    @Test
    public void shouldAddVetoableChangeListener() {
        this.support.addVetoableChangeListener(this.vetoableListener);
        assertTrue(ArrayUtils.contains(this.support.getVetoableChangeSupport()
                .getVetoableChangeListeners(), this.vetoableListener));
    }

    @Test
    public void shouldRemoveVetoableChangeListener() {
        this.support.addVetoableChangeListener(this.vetoableListener);
        this.support.removeVetoableChangeListener(this.vetoableListener);
        assertFalse(ArrayUtils.contains(this.support.getVetoableChangeSupport()
                .getVetoableChangeListeners(), this.vetoableListener));
    }

    @Test
    public void shouldAddVetoableChangeListenerForProp() throws Exception {
        this.support.addVetoableChangeListener("abc", this.vetoableListener);
        this.support.fireVetoableChange("abc", "1", "2");
        assertEquals("abc", this.vetoEvent.getPropertyName());
        assertEquals("1", this.vetoEvent.getOldValue());
        assertEquals("2", this.vetoEvent.getNewValue());
    }

}
