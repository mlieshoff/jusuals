/*
 * ServiceDataTest.java
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

public class ServiceDataTest {

    private Service service = new Service() {
        @Override
        public void start(Map<String, String> params) throws Exception {
        }
        @Override
        public void stop() throws Exception {
        }
    };

    @Test
    public void testConstructorServiceMapStringString() {
        Map<String, String> m = new Hashtable<String, String>();
        ServiceData sd = null;
        m.put("Test", "Das ist ein Test");
        try {
            // Parameter sind unbelegt.
            sd = new DefaultServiceData(null, null);
            assertNull(sd.getParameters());
            assertNull(sd.getService());
            // Nur Service ist gesetzt.
            sd = new DefaultServiceData(this.service, null);
            assertNull(sd.getParameters());
            assertNotNull(sd.getService());
            assertSame(this.service, sd.getService());
            // Nur die Parameterliste ist gesetzt.
            sd = new DefaultServiceData(null, m);
            assertNotNull(sd.getParameters());
            assertEquals(1, sd.getParameters().size());
            assertEquals("Das ist ein Test", sd.getParameters().get("Test"));
            // Beide Parameter sind gesetzt.
            sd = new DefaultServiceData(this.service, m);
            assertNotNull(sd.getService());
            assertSame(this.service, sd.getService());
            assertNotNull(sd.getParameters());
            assertEquals(1, sd.getParameters().size());
            assertEquals("Das ist ein Test", sd.getParameters().get("Test"));
        } catch (Exception e) {
            fail("no exception should be thrown here!");
        }
        // set/get service
        sd = new DefaultServiceData(null, null);
        sd.setService(this.service);
        assertEquals(this.service, sd.getService());
        // set/get params
        sd = new DefaultServiceData(null, null);
        sd.setParameters(m);
        assertEquals(m, sd.getParameters());
    }

}
