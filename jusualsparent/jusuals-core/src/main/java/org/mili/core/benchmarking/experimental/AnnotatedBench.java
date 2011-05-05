/*
 * AnnotatedBench.java
 *
 * 04.05.2011
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
package org.mili.core.benchmarking.experimental;

import java.lang.annotation.*;
import java.lang.reflect.*;

import org.apache.commons.lang.*;
import org.mili.core.annotation.*;
import org.mili.core.benchmarking.*;

/**
 * This class defines a benchmark defined by annotations.
 *
 * @author Michael Lieshoff
 */
public class AnnotatedBench implements Bench {
    private Class<?> cls = null;
    private Object instance = null;
    private AnnotationSolver solver = new MethodAnnotationSolver();
    private long prepareCount = 0;
    private long executeCount = 0;

    /**
     * Instantiates a new annotated bench.
     *
     * @param cls the cls
     */
    public AnnotatedBench(Class<?> cls) {
        super();
        Validate.notNull(cls);
        this.cls = cls;
        this.reset();
        this.solver.addAnnotationHandler(Prepare.class, new AnnotationHandler<Method>() {
            @Override
            public void handle(Annotation annotation, Method method) {
                try {
                    prepareLoop((Prepare) annotation, method);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        });
        this.solver.addAnnotationHandler(Execute.class, new AnnotationHandler<Method>() {
            @Override
            public void handle(Annotation annotation, Method method) {
                try {
                    executeLoop((Execute) annotation, method);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        });
        this.solver.addAnnotationHandler(Reset.class, new AnnotationHandler<Method>() {
            @Override
            public void handle(Annotation annotation, Method method) {
                try {
                    reset((Reset) annotation, method);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        });
    }

    @Override
    public void execute(long lm) {
        this.executeCount = lm;
        this.solver.solve(this.cls);
    }

    @Override
    public String getName() {
        return this.cls.getSimpleName();
    }

    @Override
    public void prepare(long lm) {
        this.prepareCount = lm;
        solver.solve(this.cls);
    }

    @Override
    public void reset() {
        try {
            this.instance = this.cls.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        this.executeCount = 0;
        this.prepareCount = 0;
        this.solver.solve(this.cls);
    }

    private void reset(Reset reset, Method method) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        method.invoke(this.instance);
    }

    private void prepareLoop(Prepare prepare, Method method) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        for(long l = 0; l < this.prepareCount; l ++) {
            method.invoke(this.instance, this.createParameters(l, method.getParameterTypes()));
        }
    }

    private void executeLoop(Execute sequence, Method method) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        for(long l = 0; l < this.executeCount; l ++) {
            method.invoke(this.instance, new Object[]{(int) l});
        }
    }

    private Object[] createParameters(long lap, Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];
        for(int i = 0; i < parameters.length; i ++) {
            parameters[i] = this.createValue(lap, parameterTypes[i]);
        }
        return parameters;
    }

    private Object createValue(long lap, Class<?> cls) {
        if (cls == byte.class) {
            return Byte.valueOf((byte) lap);
        } else if (cls == short.class) {
            return Short.valueOf((short) lap);
        } else if (cls == char.class) {
            return (char) lap;
        } else if (cls == int.class) {
            return Integer.valueOf((int) lap);
        } else if (cls == long.class) {
            return lap;
        } else if (cls == double.class) {
            return (double) lap;
        } else if (cls == float.class) {
            return (float) lap;
        } else if (cls == boolean.class) {
            return lap % 2 != 0;
        } else if (cls == String.class) {
            return String.valueOf(lap);
        }
        throw new IllegalStateException("parameter class not handled: " + cls);
    }

}
