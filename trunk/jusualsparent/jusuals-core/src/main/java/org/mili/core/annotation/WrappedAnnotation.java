/*
 * WrappedAnnotation.java
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

import org.apache.commons.lang.*;

/**
 * @author Michael Lieshoff
 */
public class WrappedAnnotation<T> {
    private T source = null;
    private Annotation annotation = null;

    /**
     * Instantiates a new wrapped annotation.
     *
     * @param annotation the annotation
     * @param source the source
     */
    public WrappedAnnotation(Annotation annotation, T source) {
        Validate.notNull(annotation);
        Validate.notNull(source);
        this.annotation = annotation;
        this.source = source;
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    public T getSource() {
        return this.source;
    }

    /**
     * Gets the annotation.
     *
     * @return the annotation
     */
    public Annotation getAnnotation() {
        return this.annotation;
    }

}
