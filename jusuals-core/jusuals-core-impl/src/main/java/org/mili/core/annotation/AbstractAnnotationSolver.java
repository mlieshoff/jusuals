/*
 * AbstractAnnotationSolver.java
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
import java.util.*;

/**
 * This abstract class is an implementation of interface {@link AnnotationSolver}.
 *
 * @param <X> the generic type
 * @author Michael Lieshoff
 */
public abstract class AbstractAnnotationSolver<X> implements AnnotationSolver<X> {
    private Map<Class<?>, List<AnnotationHandler>> handler = new HashMap<Class<?>,
            List<AnnotationHandler>>();

    @Override
    public void solve(Class<?> cls) {
        WrappedAnnotation[] was = this.getAnnotations(cls);
        for(WrappedAnnotation wa : was) {
            Annotation annotation = wa.getAnnotation();
            Object source = wa.getSource();
            List<AnnotationHandler> list = this.handler.get(annotation.annotationType());
            if (list != null) {
                for (int i = 0, n = list.size(); i < n; i++) {
                    AnnotationHandler ah = list.get(i);
                    ah.handle(annotation, source);
                }
            }
        }
    }

    @Override
    public void addAnnotationHandler(Class<?> forAnnotation, AnnotationHandler ah) {
        List<AnnotationHandler> list = this.handler.get(ah);
        if (list == null) {
            list = new ArrayList<AnnotationHandler>();
            this.handler.put(forAnnotation, list);
        }
        list.add(ah);
    }

}
