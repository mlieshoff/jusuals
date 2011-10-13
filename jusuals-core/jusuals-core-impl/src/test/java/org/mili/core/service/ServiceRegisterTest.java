/*
 * ServiceRegisterTest.java
 *
 * 26.11.2009
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

import org.junit.*;

import static org.junit.Assert.*;


/**
 * @author Michael Lieshoff
 */

public class ServiceRegisterTest {
    private Service service = new Service() {
        @Override
        public void start(Map<String, String> params) throws Exception {
        }
        @Override
        public void stop() throws Exception {
        }
    };

    @Test
    public void testRegisterServiceMapStringString() {
        Map<String, String> m = new Hashtable<String, String>();
        ServiceData sd = null;
        ServiceRegister sr = new DefaultServiceRegister();
        m.put("Test", "Das ist ein Test");
        // Kontrakt-Pruefung.
        try {
            sr.register(null, m);
            fail("a NullPointerException should be thrown here!");
        } catch (NullPointerException npe) {
        } catch (Exception e) {
            fail("a NullPointerException should be thrown here!");
        }
        try {
            sr.register(this.service, null);
        } catch (Exception e) {
            fail("no exception should be thrown here!");
        }
        // Nutzung.
        try {
            // Null-Parameter bunkern.
            sr = new DefaultServiceRegister();
            sr.register(this.service, null);
            sd = sr.getServiceData(this.service.getClass());
            assertNotNull(sd);
            assertNull(sd.getParameters());
            // Service mit Parametern registrieren.
            sr = new DefaultServiceRegister();
            sr.register(this.service, m);
            sd = sr.getServiceData(this.service.getClass());
            assertNotNull(sd);
            assertNotNull(sd.getParameters());
            assertEquals("Das ist ein Test", sd.getParameters().get("Test"));
        } catch (Exception e) {
            fail("no exception should be thrown here!");
        }
    }

}
