/*
 * PortableRemoteObjectDelegate.java
 *
 * 08.03.2010
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

import javax.rmi.*;

/**
 * This class defines a delegate for the protable remote object.
 *
 * @author Michael Lieshoff
 */
public class PortableRemoteObjectDelegate implements IPortableRemoteObject {
    private IPortableRemoteObject intf = null;

    /**
     * Instantiates a new portable remote object delegate.
     *
     * @param intf the intf
     */
    protected PortableRemoteObjectDelegate(IPortableRemoteObject intf) {
        super();
        this.intf = intf;
    }

    /**
     * Instantiates a new portable remote object delegate.
     */
    protected PortableRemoteObjectDelegate() {
        super();
    }

    /**
     * Creates the delegate.
     *
     * @return the portable remote object delegate
     */
    public static PortableRemoteObjectDelegate create() {
        return new PortableRemoteObjectDelegate();
    }

    /**
     * Creates the delegate.
     *
     * @param intf the intf
     * @return the portable remote object delegate
     */
    public static PortableRemoteObjectDelegate create(IPortableRemoteObject intf) {
        return new PortableRemoteObjectDelegate(intf);
    }

    @Override
    public Object narrow(Object narrowFrom, Class<?> narrowTo) throws ClassCastException {
        if (this.intf != null) {
            return this.intf.narrow(narrowFrom, narrowTo);
        } else {
            return PortableRemoteObject.narrow(narrowFrom, narrowTo);
        }
    }

}
