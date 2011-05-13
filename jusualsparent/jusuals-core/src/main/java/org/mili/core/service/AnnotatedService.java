/*
 * AnnotatedService.java
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

import java.lang.annotation.*;
import java.lang.reflect.*;

import org.apache.commons.lang.*;
import org.mili.core.annotation.*;

/**
 * This class defines an annotated service.
 *
 * @author Michael Lieshoff
 */
public class AnnotatedService {
    private Class<?> cls = null;
    private Object instance = null;
    private AnnotationSolver solver = new MethodAnnotationSolver();
    private Wrapper wrapper = new DefaultWrapper();

    /**
     * Instantiates a new annotated service.
     *
     * @param cls the class
     */
    public AnnotatedService(Class<?> cls) {
        Validate.notNull(cls);
        this.cls = cls;
        this.solver.addAnnotationHandler(ServiceMethod.class,
                this.createMethodAnnotationHandler());
        this.solver.addAnnotationHandler(ServiceStopMethod.class,
                this.createMethodAnnotationHandler());
    }

    /**
     * Service.
     */
    public void service() {
        this.createInstance();
        this.solver.solve(this.cls);
    }

    void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    private void createInstance() {
        try {
            this.instance = this.wrapper.newInstance(this.cls);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Stop.
     */
    public void stop() {
        this.createInstance();
        this.solver.solve(this.cls);
    }

    private AnnotationHandler<Method> createMethodAnnotationHandler() {
        return new AnnotationHandler<Method>() {
            @Override
            public void handle(Annotation annotation, Method method) {
                try {
                    wrapper.invokeMethod(instance, method);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

    interface Wrapper {
        <T> T newInstance(Class<T> cls) throws InstantiationException, IllegalAccessException;
        void invokeMethod(Object object, Method method) throws IllegalAccessException,
                InvocationTargetException;
    }

    class DefaultWrapper implements Wrapper {
        @Override
        public void invokeMethod(Object object, Method method) throws IllegalAccessException,
                InvocationTargetException {
            method.invoke(object);
        }
        @Override
        public <T> T newInstance(Class<T> cls) throws InstantiationException,
                IllegalAccessException {
            return cls.newInstance();
        }
    }

}
