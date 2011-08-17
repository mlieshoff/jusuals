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
import java.util.*;

import org.apache.commons.lang.*;
import org.mili.core.annotation.*;

/**
 * This class defines an annotated service.
 *
 * <p><b>Introduction</b><p>
 *
 * <u> What is an annotated service?</u><p>
 *
 * An annotated service is created by a class having service annotations. The annotated service
 * wraps the class into a more serviceable structure, having start()- and stop()-methods.<p>
 *
 * <u> Future of annotated services</u><p>
 *
 * It's planned to have parameters in service methods and define more then one service method and
 * service stop method. These method's should be executed by used annotated defined order.<p>
 * <b>Usage</b><p>
 *
 * <u> Wrap an annotated Class</u><p>
 *
 * The wrapped class must have at least a service method, e.g. &#064;ServiceMethod placed on a
 * parameterless method. There can be a &#064;ServiceStopMethod, but not must.<p>
 * <b>Examples</b><p>
 *
 * <u> Class Foo</u><p>
 *
 * Start writing a class with a simple method. This method will be annotated with the
 * &#064;ServiceMethod annotation. Then add a method called at stop, use the &#064;ServiceStopMethod
 * annotation.<br>
 * <pre>
 * public class Foo() {
 *     &#064;ServiceMethod
 *     public void doFoo() {
 *         System.out.println(&quot;Hello world!&quot;);
 *     }
 *     &#064;ServiceStopMethod
 *     public void doEnd() {
 *         System.out.println(&quot;Stop.&quot;);
 *     }
 * }
 * </pre>
 * Now the class can be wrapped into an annotated service, simple by use the class
 * AnnotatedService.<br>
 * <pre>
 * public void somewhere() {
 *     AnnotatedService service = new AnnotatedService(Foo.class);
 *     service.start();
 *     service.stop();
 * }
 * </pre>
 * An execution of somewhere()-method will produce following output:<br>
 * <pre>
 * Hello world!
 * Stop.
 * </pre>
 * How to handle a service method with passed parameters? It's easy to do. Just pass the parameters
 * as map through the service method. The only restriction is that the method has just one
 * parameter and the map ist parametrized as String for keys and values.<br>
 * <pre>
 * &#064;ServiceMethod
 * public void doFoo(Map&lt;String, String&gt; params) {
 *     System.out.println(&quot;Hello world! &quot; + params.get(&quot;foo&quot;));
 * }
 * </pre>
 *
 * @author Michael Lieshoff
 */
public class AnnotatedService {
    private Class<?> cls = null;
    private Object instance = null;
    private AnnotationSolver<?> solver = new MethodAnnotationSolver();
    private Wrapper wrapper = new DefaultWrapper();
    private Map<String, String> params = null;

    /**
     * Instantiates a new annotated service.
     *
     * @param cls the class
     */
    public AnnotatedService(Class<?> cls) {
        Validate.notNull(cls, "class cannot be null!");
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

    /**
     * @return the params
     */
    public Map<String, String> getParams() {
        return this.params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
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
            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length > 0) {
                method.invoke(object, params);
            } else {
                method.invoke(object);
            }
        }
        @Override
        public <T> T newInstance(Class<T> cls) throws InstantiationException,
                IllegalAccessException {
            return cls.newInstance();
        }
    }

}
