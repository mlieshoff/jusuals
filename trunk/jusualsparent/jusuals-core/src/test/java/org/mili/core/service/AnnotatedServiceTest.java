/*
 * AnnotatedServiceTest.java
 *
 * 13.05.2011
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
package org.mili.core.service;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class AnnotatedServiceTest {
    static long countOfServiceMethod = 0;
    static long countOfServiceStopMethod = 0;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        countOfServiceMethod = 0;
        countOfServiceStopMethod = 0;
    }

    /**
     * Fails construct because null class.
     */
    @Test(expected=IllegalArgumentException.class)
    public void failsConstructBecauseNullClass() {
        new AnnotatedService(null);
    }

    /**
     * Should service.
     */
    @Test
    public void shouldService() {
        AnnotatedService service = new AnnotatedService(Simple.class);
        service.service();
        assertEquals(1, countOfServiceMethod);
    }

    /**
     * Should stop.
     */
    @Test
    public void shouldStop() {
        AnnotatedService service = new AnnotatedService(Simple.class);
        service.stop();
        assertEquals(1, countOfServiceStopMethod);
    }

}