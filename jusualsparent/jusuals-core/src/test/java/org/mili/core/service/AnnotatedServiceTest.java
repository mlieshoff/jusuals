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

import java.lang.reflect.*;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class AnnotatedServiceTest {
    static long countOfServiceMethod = 0;
    static long countOfServiceStopMethod = 0;
    private AnnotatedService service = null;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        countOfServiceMethod = 0;
        countOfServiceStopMethod = 0;
        this.service = new AnnotatedService(Simple.class);
    }

    /**
     * Fails construct because null class.
     */
    @Test(expected=IllegalArgumentException.class)
    public void failsConstructBecauseNullClass() {
        new AnnotatedService(null);
    }

    /**
     * Fails construct because throw exception while new instance.
     */
    @Test(expected=IllegalStateException.class)
    public void failsConstructBecauseExceptionInNewInstance() {
        this.service.setWrapper(new AnnotatedService.Wrapper() {
            @Override
            public <T> T newInstance(Class<T> cls) throws InstantiationException,
                    IllegalAccessException {
                throw new InstantiationException();
            }
            @Override
            public void invokeMethod(Object object, Method method) throws IllegalAccessException,
                    InvocationTargetException {
            }
        });
        this.service.service();
    }

    /**
     * Fails construct because throw exception while invocation.
     */
    @Test(expected=IllegalStateException.class)
    public void failsConstructBecauseExceptionInInvocation() {
        this.service.setWrapper(new AnnotatedService.Wrapper() {
            @Override
            public <T> T newInstance(Class<T> cls) throws InstantiationException,
                    IllegalAccessException {
                return cls.newInstance();
            }
            @Override
            public void invokeMethod(Object object, Method method)
                    throws IllegalAccessException, InvocationTargetException {
                throw new IllegalAccessException();
            }
        });
        this.service.service();
    }

    /**
     * Should service.
     */
    @Test
    public void shouldService() {
        this.service.service();
        assertEquals(1, countOfServiceMethod);
    }

    /**
     * Should stop.
     */
    @Test
    public void shouldStop() {
        this.service.stop();
        assertEquals(1, countOfServiceStopMethod);
    }

}