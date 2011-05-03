/*
 * AnnotatedBench.java
 *
 * 03.05.2011
 *
 * (c) HealthCarion GmbH
 */

package org.mili.core.benchmarking;

import java.lang.annotation.*;

import org.mili.core.annotation.*;

/**
 * @author mlieshoff
 *
 */
public class AnnotatedBench implements Bench {

    private Class<?> cls = null;

    /**
     * @param cls
     */
    public AnnotatedBench(Class<?> cls) {
        super();
        this.cls = cls;
    }

    @Override
    public void execute(long lm) {

    }

    @Override
    public String getName() {
        return this.cls.getName();
    }

    @Override
    public void prepare(long lm) {
        AnnotationSolver solver = new MethodAnnotationSolver();
        solver.addAnnotationHandler(Prepare.class, new AnnotationHandler() {
            @Override
            public void handle(Annotation annotation) {
                Prepare prepare = (Prepare) annotation;
            }
        });
    }

    @Override
    public void reset() {
    }

}
