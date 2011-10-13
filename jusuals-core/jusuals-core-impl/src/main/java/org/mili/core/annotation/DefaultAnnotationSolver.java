/*
 * ClassAnnotationSolver.java
 *
 * 03.05.2011
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
package org.mili.core.annotation;

import java.lang.reflect.*;
import java.util.*;


/**
 * This interface defines a default annotation solver.
 *
 * @author Michael Lieshoff
 */
public class DefaultAnnotationSolver extends AbstractAnnotationSolver<Class<?>> {
    private AnnotationSolver<Class<?>> classSolver = new ClassAnnotationSolver();
    private AnnotationSolver<Method> methodSolver = new MethodAnnotationSolver();

    @Override
    public WrappedAnnotation<Class<?>>[] getAnnotations(Class<?> cls) {
        List<WrappedAnnotation> l = new ArrayList<WrappedAnnotation>();
        l.addAll(Arrays.asList(this.classSolver.getAnnotations(cls)));
        l.addAll(Arrays.asList(this.methodSolver.getAnnotations(cls)));
        return l.toArray(new WrappedAnnotation[]{});
    }
}
