/*
 * WrappedAnnotationTest.java
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

import org.junit.*;

import static org.junit.Assert.*;


/**
 * @author Michael Lieshoff
 */
public class WrappedAnnotationTest {

    private Annotation annotation = new Annotation() {
        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
    };

    /**
     * Fails because null annotation.
     */
    @Test(expected=IllegalArgumentException.class)
    public void failsBecauseNullAnnotation() {
        new WrappedAnnotation<String>(null, "abbas");
    }

    /**
     * Fails because null id.
     */
    @Test(expected=IllegalArgumentException.class)
    public void failsBecauseNullSource() {
        new WrappedAnnotation<String>(this.annotation, null);
    }

    /**
     * Should get source.
     */
    @Test
    public void shouldGetSource() {
        WrappedAnnotation<String> wa = new WrappedAnnotation<String>(this.annotation, "abbas");
        assertEquals("abbas", wa.getSource());
    }

    /**
     * Should get annotation.
     */
    @Test
    public void shouldGetAnnotation() {
        WrappedAnnotation<String> wa = new WrappedAnnotation<String>(this.annotation, "abbas");
        assertEquals(this.annotation, wa.getAnnotation());
    }

}
