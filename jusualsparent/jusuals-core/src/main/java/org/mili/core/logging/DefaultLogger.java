/*
 * DefaultLogger.java
 *
 * 15.07.2007
 *
 * Copyright 2007 Michael Lieshoff
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
package org.mili.core.logging;

import java.io.*;


/**
 * This class defines a logger factory.
 *
 * @author Michael Lieshoff
 *
 */
public class DefaultLogger implements Logger {
    public final static String PROP_LOGTHROWABLES = DefaultLogger.class.getName()
            + ".LogThrowables";
    public final static String PROP_LOGTHROWABLESDIR = DefaultLogger.class.getName()
            + ".LogThrowablesDir";
    private Logger root = null;
    private ThrowableLogger throwableLogger = null;
    private Class<?> cls = null;

    /**
     * Create a new logger from class clazz.
     *
     * @param clazz the class.
     */
    public DefaultLogger(Class<?> clazz) {
        this.root = create(clazz);
        this.cls = clazz;
    }

    /**
     * Static method to create a new default logger from class clazz.
     *
     * @param clazz the class.
     * @return default logger.
     */
    public static DefaultLogger getLogger(Class<?> clazz) {
        return new DefaultLogger(clazz);
    }

    /**
     * Static method to create a new logger from class clazz.
     *
     * @param clazz the class.
     * @return logger.
     */
    public static Logger create(Class<?> clazz) {
        return new Log4jAdapter(clazz);
    }

    @Override
    public Object[] begin(String method, Object... o) {
        return root.begin(method, o);
    }

    @Override
    public void beginDebug(String method, Object... o) {
        root.beginDebug(method, o);
    }

    @Override
    public void beginError(String method, Object... o) {
        root.beginError(method, o);
    }

    @Override
    public void beginFatal(String method, Object... o) {
        root.beginFatal(method, o);
    }

    @Override
    public void beginInfo(String method, Object... o) {
        root.beginInfo(method, o);
    }

    @Override
    public void beginWarn(String method, Object... o) {
        root.beginWarn(method, o);
    }

    @Override
    public void debug(Object... o) {
        root.debug(o);
    }

    @Override
    public void debug(Throwable t, Object... o) {
        this.logThrowableIfNeeded(t);
        root.debug(t, o);
    }

    @Override
    public String debugVarArgsToParamString(Object... o) {
        return root.debugVarArgsToParamString(o);
    }

    @Override
    public Object[] delegate(String method, Object... o) {
        return root.delegate(method, o);
    }

    @Override
    public void delegateDebug(String method, Object... o) {
        root.delegateDebug(method, o);
    }

    @Override
    public void delegateError(String method, Object... o) {
        root.delegateError(method, o);
    }

    @Override
    public void delegateFatal(String method, Object... o) {
        root.delegateFatal(method, o);
    }

    @Override
    public void delegateInfo(String method, Object... o) {
        root.delegateInfo(method, o);
    }

    @Override
    public void delegateWarn(String method, Object... o) {
        root.delegateWarn(method, o);
    }

    @Override
    public Object[] end(String method, Object... o) {
        return root.end(method, o);
    }

    @Override
    public void endDebug(String method, Object... o) {
        root.endDebug(method, o);
    }

    @Override
    public void endError(String method, Object... o) {
        root.endError(method, o);
    }

    @Override
    public void endFatal(String method, Object... o) {
        root.endFatal(method, o);
    }

    @Override
    public void endInfo(String method, Object... o) {
        root.endInfo(method, o);
    }

    @Override
    public void endWarn(String method, Object... o) {
        root.endWarn(method, o);
    }

    @Override
    public void error(Object... o) {
        root.error(o);
    }

    @Override
    public void error(Throwable t, Object... o) {
        this.logThrowableIfNeeded(t);
        root.error(t, o);
    }

    @Override
    public String errorVarArgsToParamString(Object... o) {
        return root.errorVarArgsToParamString(o);
    }

    @Override
    public void fatal(Object... o) {
        root.fatal(o);
    }

    @Override
    public void fatal(Throwable t, Object... o) {
        this.logThrowableIfNeeded(t);
        root.fatal(t, o);
    }

    @Override
    public String fatalVarArgsToParamString(Object... o) {
        return root.fatalVarArgsToParamString(o);
    }

    @Override
    public Object getLogger() {
        return root.getLogger();
    }

    @Override
    public void info(Object... o) {
        root.info(o);
    }

    @Override
    public void info(Throwable t, Object... o) {
        this.logThrowableIfNeeded(t);
        root.info(t, o);
    }

    @Override
    public String infoVarArgsToParamString(Object... o) {
        return root.infoVarArgsToParamString(o);
    }

    @Override
    public void warn(Object... o) {
        root.warn(o);
    }

    @Override
    public void warn(Throwable t, Object... o) {
        this.logThrowableIfNeeded(t);
        root.warn(t, o);
    }

    @Override
    public String warnVarArgsToParamString(Object... o) {
        return root.warnVarArgsToParamString(o);
    }

    @Override
    public void trace(Object... o) {
        root.trace(o);
    }

    @Override
    public void trace(Throwable t, Object... o) {
        this.logThrowableIfNeeded(t);
        root.trace(t, o);
    }

    private void logThrowableIfNeeded(Throwable throwable) {
        if (Boolean.getBoolean(PROP_LOGTHROWABLES)) {
            if (this.throwableLogger == null) {
                String name = null;
                if (!System.getProperties().containsKey(PROP_LOGTHROWABLESDIR)) {
                    name = System.getProperty("java.io.tmpdir");
                } else {
                    name = System.getProperty(PROP_LOGTHROWABLESDIR);
                }
                File dir = new File(name);
                this.throwableLogger = new ThrowableLogger(this.cls, dir);
            }
            try {
                this.throwableLogger.log(throwable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
