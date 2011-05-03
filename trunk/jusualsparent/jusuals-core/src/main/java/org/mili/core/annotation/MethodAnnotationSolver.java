/*
 * MethodAnnotationSolver.java
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

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * This interface defines a method annotation solver.
 *
 * @author Michael Lieshoff
 */
public class MethodAnnotationSolver extends AbstractAnnotationSolver<Method>{
    @Override
    public WrappedAnnotation[] getAnnotations(Class<?> cls) {
        List<Annotation> annotations = new ArrayList<Annotation>();
        for(Method method : cls.getDeclaredMethods()) {
            annotations.addAll(Arrays.asList(method.getAnnotations()));
        }
        return annotations.toArray(new WrappedAnnotation<?>[]{});
    }
}
