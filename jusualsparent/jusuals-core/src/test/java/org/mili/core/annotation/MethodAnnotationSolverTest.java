/*
 * MethodAnnotationSolverTest.java
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

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class MethodAnnotationSolverTest {
    private AnnotationSolver solver = new MethodAnnotationSolver();

    /**
     * Should find no annotations.
     */
    @Test
    public void shouldFindNoAnnotations() {
        assertEquals(0, this.solver.getAnnotations(Foo.class).length);
    }

    /**
     * Should find one annotation.
     */
    @Test
    public void shouldFindOneAnnotation() {
        assertEquals(1, this.solver.getAnnotations(Foo1.class).length);
    }

    /**
     * Should solve but not find handler.
     */
    @Test
    public void shouldSolveButNotFindHandler() {
        this.solver.solve(Foo1.class);
    }

    /**
     * Should solve test method annotation.
     */
    @Test
    public void shouldSolveTestMethodAnnotation() {
        final List<Boolean> l = new ArrayList<Boolean>();
        this.solver.addAnnotationHandler(TestMethodAnnotation1.class, new AnnotationHandler<Method>() {
            @Override
            public void handle(Annotation annotation, Method method) {
                l.add(true);
            }
        });
        this.solver.addAnnotationHandler(TestMethodAnnotation2.class, new AnnotationHandler<Method>() {
            @Override
            public void handle(Annotation annotation, Method method) {
                l.add(true);
            }
        });
        this.solver.solve(Foo1.class);
        assertEquals(1, l.size());
        assertTrue(l.get(0));
    }

    /**
     * Should solve test method annotations.
     */
    @Test
    public void shouldSolveTestMethodAnnotations() {
        final List<Boolean> l = new ArrayList<Boolean>();
        this.solver.addAnnotationHandler(TestMethodAnnotation1.class, new AnnotationHandler<Method>() {
            @Override
            public void handle(Annotation annotation, Method method) {
                l.add(true);
            }
        });
        this.solver.addAnnotationHandler(TestMethodAnnotation2.class, new AnnotationHandler<Method>() {
            @Override
            public void handle(Annotation annotation, Method method) {
                l.add(true);
            }
        });
        this.solver.solve(Foo2.class);
        assertEquals(2, l.size());
        assertTrue(l.get(0));
        assertTrue(l.get(1));
    }

    static class Foo {
        void foo() {
        }
    }

    @TestClassAnnotation1
    static class Foo1 {
        @TestMethodAnnotation1
        void foo() {
        }
    }

    @TestClassAnnotation1
    @TestClassAnnotation2
    static class Foo2 {
        @TestMethodAnnotation1
        @TestMethodAnnotation2
        void foo() {
        }
    }

}
