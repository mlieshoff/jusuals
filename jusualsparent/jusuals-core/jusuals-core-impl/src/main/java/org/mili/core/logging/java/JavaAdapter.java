/*
 * JavaAdapter.java
 *
 * 17.10.2011
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

package org.mili.core.logging.java;


import java.util.*;
import java.util.logging.Level;

import org.apache.commons.lang.*;
import org.mili.core.collection.*;
import org.mili.core.logging.Logger;


/**
 * This adapter encapsulates a java.util.Logger. The logging levels are mapped to the
 * org.mili.Logger as following: <p/>
 *
 * <p/>
 * SEVERE (highest) => fatal/error <br/>
 * WARNING          => warn <br/>
 * INFO             => info <br/>
 * CONFIG <br/>
 * FINE             => debug <br/>
 * FINER <br/>
 * FINEST           => trace <br/>
 *
 * @author Michael Lieshoff
 */

public class JavaAdapter implements Logger {
    private java.util.logging.Logger logger = java.util.logging.Logger.getAnonymousLogger();
    private Class<?> clazz;

    /**
     * Creates a new default logger.
     */
    public JavaAdapter() {
        fixNullLevel();
    }

    /**
     * Creates a new logger from class clazz.
     *
     * @param clazz The class.
     */
    public JavaAdapter(Class<?> clazz) {
        logger = java.util.logging.Logger.getLogger(clazz.getName());
        fixNullLevel();
        this.clazz = clazz;
    }

    org.mili.core.logging.Level transformLevel(Level level) {
        Validate.notNull(level, "level cannot be null!");
        if (level == Level.ALL || level == Level.FINEST) {
            return org.mili.core.logging.Level.TRACE;
        } else if (level == Level.FINE || level == Level.FINER) {
            return org.mili.core.logging.Level.DEBUG;
        } else if (level == Level.INFO || level == Level.CONFIG) {
            return org.mili.core.logging.Level.INFO;
        } else if (level == Level.WARNING) {
            return org.mili.core.logging.Level.WARN;
        } else if (level == Level.SEVERE) {
            return org.mili.core.logging.Level.ERROR;
        } else {
            return org.mili.core.logging.Level.FATAL;
        }
    }

    void fixNullLevel() {
        if (logger.getLevel() == null) {
            logger.setLevel(Level.ALL);
        }
    }

    boolean isOn() {
        return logger.getLevel() != Level.OFF;
    }

    boolean isAll() {
        return logger.getLevel() == Level.ALL;
    }

    boolean isFatal() {
        Level level = logger.getLevel();
        return isOn() && (isAll() || Level.SEVERE.intValue() >= level.intValue());
    }

    boolean isError() {
        return isFatal();
    }

    boolean isWarn() {
        Level level = logger.getLevel();
        return isOn() && (isAll() || Level.WARNING.intValue() >= level.intValue());
    }

    boolean isInfo() {
        Level level = logger.getLevel();
        return isOn() && (isAll() || Level.INFO.intValue() >= level.intValue()
                || Level.CONFIG.intValue() >= level.intValue());
    }

    boolean isDebug() {
        Level level = logger.getLevel();
        return isOn() && (isAll() || Level.FINER.intValue() >= level.intValue()
                || Level.FINE.intValue() >= level.intValue());
    }

    boolean isTrace() {
        Level level = logger.getLevel();
        return isOn() && (isAll() || Level.FINEST.intValue() >= level.intValue());
    }

    @Override
    public void warn(Throwable t, Object... o) {
        if (isWarn()) {
            logger.warning(getO(o));
            logException(t);
        }
    }

    @Override
    public void warn(Object... o) {
        if (isWarn()) {
            logger.warning(getO(o));
        }
    }

    @Override
    public void debug(Throwable t, Object... o) {
        if (isDebug()) {
            logger.fine(getO(o));
            logException(t);
        }
    }

    @Override
    public void debug(Object... o) {
        if (isDebug()) {
            logger.finest(getO(o));
        }
    }

    @Override
    public void info(Throwable t, Object... o) {
        if (isInfo()) {
            logger.info(getO(o));
            logException(t);
        }
    }

    @Override
    public void info(Object... o) {
        if (isInfo()) {
            logger.info(getO(o));
        }
    }

    @Override
    public void error(Throwable t, Object... o) {
        if (isError()) {
            logger.severe(getO(o));
            logException(t);
        }
    }

    @Override
    public void error(Object... o) {
        if (isError()) {
            logger.severe(getO(o));
        }
    }

    @Override
    public void trace(Throwable t, Object... o) {
        if (isTrace()) {
            logger.finest(getO(o));
            logException(t);
        }
    }

    @Override
    public void trace(Object... o) {
        if (isTrace()) {
            logger.finest(getO(o));
        }
    }

    @Override
    public void fatal(Throwable t, Object... o) {
        if (isFatal()) {
            logger.severe(getO(o));
            logException(t);
        }
    }

    @Override
    public void fatal(Object... o) {
        if (isFatal()) {
            logger.severe(getO(o));
        }
    }

    @Override
    public void beginWarn(String method, Object... o) {
        if (isWarn()) {
            warn(begin(method, o));
        }
    }

    @Override
    public void beginDebug(String method, Object... o) {
        if (isDebug()) {
            debug(begin(method, o));
        }
    }

    @Override
    public void beginInfo(String method, Object... o) {
        if (isInfo()) {
            info(begin(method, o));
        }
    }

    @Override
    public void beginError(String method, Object... o) {
        if (isError()) {
            error(begin(method, o));
        }
    }

    @Override
    public void beginFatal(String method, Object... o) {
        if (isFatal()) {
            fatal(begin(method, o));
        }
    }

    @Override
    public Object[] begin(String method, Object... o) {
        StringBuilder s = new StringBuilder();
        s.append("BEGIN ");
        s.append(method);
        s.append(": ");
        return new Object[]{s.toString(), ArrayUtil.varArgsToString(o)};
    }

    @Override
    public void delegateWarn(String method, Object... o) {
        if (isWarn()) {
            warn(delegate(method, o));
        }
    }

    @Override
    public void delegateDebug(String method, Object... o) {
        if (isDebug()) {
            debug(delegate(method, o));
        }
    }

    @Override
    public void delegateInfo(String method, Object... o) {
        if (isInfo()) {
            info(delegate(method, o));
        }
    }

    @Override
    public void delegateError(String method, Object... o) {
        if (isError()) {
            error(delegate(method, o));
        }
    }

    @Override
    public void delegateFatal(String method, Object... o) {
        if (isFatal()) {
            fatal(delegate(method, o));
        }
    }

    @Override
    public Object[] delegate(String method, Object... o) {
        StringBuilder s = new StringBuilder();
        s.append("DELEGATE ");
        s.append(method);
        s.append(": ");
        return new Object[]{s.toString(), ArrayUtil.varArgsToString(o)};
    }

    @Override
    public void endWarn(String method, Object... o) {
        if (isWarn()) {
            warn(end(method, o));
        }
    }

    @Override
    public void endDebug(String method, Object... o) {
        if (isDebug()) {
            debug(end(method, o));
        }
    }

    @Override
    public void endInfo(String method, Object... o) {
        if (isInfo()) {
            info(end(method, o));
        }
    }

    @Override
    public void endError(String method, Object... o) {
        if (isError()) {
            error(end(method, o));
        }
    }

    @Override
    public void endFatal(String method, Object... o) {
        if (isFatal()) {
            fatal(end(method, o));
        }
    }

    @Override
    public Object[] end(String method, Object... o) {
        StringBuilder s = new StringBuilder();
        s.append("END ");
        s.append(method);
        s.append(": ");
        return new Object[]{s.toString(), ArrayUtil.varArgsToString(o)};
    }

    @Override
    public String warnVarArgsToParamString(Object... o) {
        if (isWarn()) {
            return ArrayUtil.varArgsToParamString(o);
        }
        return null;
    }

    @Override
    public String debugVarArgsToParamString(Object... o) {
        if (isDebug()) {
            return ArrayUtil.varArgsToParamString(o);
        }
        return null;
    }

    @Override
    public String infoVarArgsToParamString(Object... o) {
        if (isInfo()) {
            return ArrayUtil.varArgsToParamString(o);
        }
        return null;
    }

    @Override
    public String errorVarArgsToParamString(Object... o) {
        if (isError()) {
            return ArrayUtil.varArgsToParamString(o);
        }
        return null;
    }

    @Override
    public String fatalVarArgsToParamString(Object... o) {
        if (isFatal()) {
            return ArrayUtil.varArgsToParamString(o);
        }
        return null;
    }

    @Override
    public Object getLogger() {
        return logger;
    }

    @Override
    public Class<?> getLoggedClass() {
        return clazz;
    }

    @Override
    public org.mili.core.logging.Level getLevel() {
        return transformLevel(logger.getLevel());
    }

    @Override
    public boolean isDebugEnabled() {
        return getLevel() == org.mili.core.logging.Level.DEBUG;
    }

    @Override
    public boolean isErrorEnabled() {
        return getLevel() == org.mili.core.logging.Level.ERROR;
    }

    @Override
    public boolean isFatalEnabled() {
        return getLevel() == org.mili.core.logging.Level.FATAL;
    }

    @Override
    public boolean isInfoEnabled() {
        return getLevel() == org.mili.core.logging.Level.INFO;
    }

    @Override
    public boolean isTraceEnabled() {
        return getLevel() == org.mili.core.logging.Level.TRACE;
    }

    @Override
    public boolean isWarnEnabled() {
        return getLevel() == org.mili.core.logging.Level.WARN;
    }

    private String getO(Object... o) {
        List<Object> l = new ArrayList<Object>();
        for (int i = 0; i < o.length; i++) {
            Object oo = o[i];
            if (oo instanceof Object[]) {
                Object[] ooo = (Object[]) oo;
                for (int j = 0; j < ooo.length; j++) {
                    l.add(ooo[j]);
                }
            } else {
                l.add(oo);
            }
        }
        return CollectionUtil.collectionToString(l);
    }

    private void log(Object... o) {
        if (isDebug()) {
            logger.finer(getO(o));
        } else if (isInfo()) {
            logger.info(getO(o));
        } else if (isWarn()) {
            logger.warning(getO(o));
        } else if (isError() || isFatal()) {
            logger.severe(getO(o));
        }
    }

    private void logException(Throwable t) {
        logException(logger.getLevel(), t, false);
    }

    private void logException(Level level, Throwable t, boolean caused) {
        if (t == null) {
            return;
        }
        StringBuilder s = new StringBuilder();
        if (caused) {
            s.append("caused by: ");
        }
        s.append(t.getClass().getName());
        s.append(": ");
        s.append(t.getMessage());
        log(s);
        s.setLength(0);
        List<StackTraceElement> l0 = Arrays.asList(t.getStackTrace());
        for (int i = 0, n = l0.size(); i < n; i++) {
            if (i > 0) {
                s.append("    ");
            }
            s.append(String.valueOf(l0.get(i)));
            log(s);
            s.setLength(0);
        }
        logException(level, t.getCause(), true);
        return;
    }

}