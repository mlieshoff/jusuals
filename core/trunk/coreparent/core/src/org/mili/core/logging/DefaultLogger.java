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


import java.util.*;

import org.apache.log4j.*;
import org.mili.core.collection.*;


/**
 * This encapsulate a log4j Logger. This wrapper is most useful for encapsulating loggers that
 * logs very much in program code like debug or info messages. This messages are most useful for
 * developers and may contains very much string concatination like this : <br><br>
 * <pre>
 * Logger log = Logger.getLogger(Clazz.class);
 * log.info("This costs [" + x + "] bytes from [" + y + "] available.");
 * </pre>
 * <br>
 * Good developers know that string concatenation is bad but for this log information exampally
 * usefull. Applications keep on info prints this log entry, but application keep on error or
 * fatal compute the string first and decide after computing that this log entry dont needed
 * to display.
 * <br><br>
 * For this these class is made.  Use the log entry above as follows and youre on most
 * performant way.  <br><br>
 * <pre>
 * Logger log = DefaultLogger.GetLogger(Clazz.class);
 * log.info("This costs [", x, "] bytes from [", y, "] available.");
 * </pre>
 * <br>
 * But don't use concatenation in this atoms.
 *
 * @author Michael Lieshoff
 *
 */

public class DefaultLogger {
    private Logger logger = null;

    /**
     * Creates a new default logger.
     */
    public DefaultLogger() {
        super();
        Logger l = Logger.getRootLogger();
        this.setLogger(l);
    }

    /**
     * Creates a new logger from class clazz.
     *
     * @param clazz The class.
     */
    public DefaultLogger(Class<?> clazz) {
        super();
        Logger l = Logger.getLogger(clazz);
        this.setLogger(l);
    }

    /**
     * Static method to create a new logger from class clazz.
     *
     * @param clazz The class.
     * @return logger.
     */
    public static DefaultLogger getLogger(Class<?> clazz) {
        DefaultLogger ml = new DefaultLogger(clazz);
        return ml;
    }

    /**
     * Warns a message and a throwable represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    public void warn(Throwable t, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.WARN.isGreaterOrEqual(l)) {
            this.logger.warn(this.getO(o));
            this.logException(l, t);
        }
    }

    /**
     * Warns a message represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    public void warn(Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.WARN.isGreaterOrEqual(l)) {
            this.logger.warn(this.getO(o));
        }
    }

    /**
     * Debugs a message and a throwable represented as array of objects.
     *
     * @param t Throwable.
     * @param o Message represented as array of objects.
     */
    public void debug(Throwable t, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.DEBUG.isGreaterOrEqual(l)) {
            this.logger.debug(this.getO(o));
            this.logException(l, t);
        }
    }

    /**
     * Debugs a message represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    public void debug(Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.DEBUG.isGreaterOrEqual(l)) {
            this.logger.debug(this.getO(o));
        }
    }

    /**
     * Infos a message and a throwable represented as array of objects.
     *
     * @param t Throwable.
     * @param o Message represented as array of objects.
     */
    public void info(Throwable t, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.INFO.isGreaterOrEqual(l)) {
            this.logger.info(this.getO(o));
            this.logException(l, t);
        }
    }

    /**
     * Infos a message represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    public void info(Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.INFO.isGreaterOrEqual(l)) {
            this.logger.info(this.getO(o));
        }
    }

    /**
     * Errors a message and a throwable represented as array of objects.
     *
     * @param t Throwable.
     * @param o Message represented as array of objects.
     */
    public void error(Throwable t, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.ERROR.isGreaterOrEqual(l)) {
            this.logger.error(this.getO(o));
            this.logException(l, t);
        }
    }

    /**
     * Errors a message represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    public void error(Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.ERROR.isGreaterOrEqual(l)) {
            this.logger.error(this.getO(o));
        }
    }

    /**
     * Debugs a message and a throwable represented as array of objects.
     *
     * @param t Throwable.
     * @param o Message represented as array of objects.
     */
    public void fatal(Throwable t, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.FATAL.isGreaterOrEqual(l)) {
            this.logger.fatal(this.getO(o));
            this.logException(l, t);
        }
    }

    /**
     * Fatals a message represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    public void fatal(Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.FATAL.isGreaterOrEqual(l)) {
            this.logger.fatal(this.getO(o));
        }
    }

    /**
     * Creates a begin warn log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void beginWarn(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.WARN.isGreaterOrEqual(l)) {
            this.warn(this.begin(method, o));
        }
    }

    /**
     * Creates a begin debug log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void beginDebug(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.DEBUG.isGreaterOrEqual(l)) {
            this.debug(this.begin(method, o));
        }
    }

    /**
     * Creates a begin info log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void beginInfo(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.INFO.isGreaterOrEqual(l)) {
            this.info(this.begin(method, o));
        }
    }

    /**
     * Creates a begin error log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void beginError(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.ERROR.isGreaterOrEqual(l)) {
            this.error(this.begin(method, o));
        }
    }

    /**
     * Creates a begin fatal log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void beginFatal(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.FATAL.isGreaterOrEqual(l)) {
            this.fatal(this.begin(method, o));
        }
    }

    /**
     * Creates a begin message.
     *
     * @param method method name.
     * @param o objects to message.
     * @return something like BEGIN: method os
     */
    public Object[] begin(String method, Object... o) {
        StringBuilder s = new StringBuilder();
        s.append("BEGIN ");
        s.append(method);
        s.append(": ");
        return new Object[]{s.toString(), ArrayUtil.varArgsToString(o)};
    }

    /**
     * Creates a delegate warn log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void delegateWarn(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.WARN.isGreaterOrEqual(l)) {
            this.warn(this.delegate(method, o));
        }
    }

    /**
     * Creates a delegate debug log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void delegateDebug(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.DEBUG.isGreaterOrEqual(l)) {
            this.debug(this.delegate(method, o));
        }
    }

    /**
     * Creates a delegate info log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void delegateInfo(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.INFO.isGreaterOrEqual(l)) {
            this.info(this.delegate(method, o));
        }
    }

    /**
     * Creates a delegate error log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void delegateError(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.ERROR.isGreaterOrEqual(l)) {
            this.error(this.delegate(method, o));
        }
    }

    /**
     * Creates a delegate fatal log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void delegateFatal(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.FATAL.isGreaterOrEqual(l)) {
            this.fatal(this.delegate(method, o));
        }
    }

    /**
     * Creates a delegate message.
     *
     * @param method method name.
     * @param o objects to message.
     * @return something like DELEGSTE: method os
     */
    public Object[] delegate(String method, Object... o) {
        StringBuilder s = new StringBuilder();
        s.append("DELEGATE ");
        s.append(method);
        s.append(": ");
        return new Object[]{s.toString(), ArrayUtil.varArgsToString(o)};
    }

    /**
     * Creates a end warn log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void endWarn(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.WARN.isGreaterOrEqual(l)) {
            this.warn(this.end(method, o));
        }
    }

    /**
     * Creates a end debug log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void endDebug(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.DEBUG.isGreaterOrEqual(l)) {
            this.debug(this.end(method, o));
        }
    }

    /**
     * Creates a end info log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void endInfo(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.INFO.isGreaterOrEqual(l)) {
            this.info(this.end(method, o));
        }
    }

    /**
     * Creates a end error log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void endError(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.ERROR.isGreaterOrEqual(l)) {
            this.error(this.end(method, o));
        }
    }

    /**
     * Creates a end fatal log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    public void endFatal(String method, Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.FATAL.isGreaterOrEqual(l)) {
            this.fatal(this.end(method, o));
        }
    }

    /**
     * Creates a end message.
     *
     * @param method method name.
     * @param o objects to message.
     * @return something like END: method os
     */
    public Object[] end(String method, Object... o) {
        StringBuilder s = new StringBuilder();
        s.append("END ");
        s.append(method);
        s.append(": ");
        return new Object[]{s.toString(), ArrayUtil.varArgsToString(o)};
    }

    /**
     * Proceeds the o's even if log level is equals.
     *
     * @param o var args to tranform to parameter string.
     * @return Parameter string, if log level is equals.
     */
    public String warnVarArgsToParamString(Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.WARN.isGreaterOrEqual(l)) {
            return ArrayUtil.varArgsToParamString(o);
        }
        return null;
    }

    /**
     * Proceeds the o's even if log level is equals.
     *
     * @param o var args to tranform to parameter string.
     * @return Parameter string, if log level is equals.
     */
    public String debugVarArgsToParamString(Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.DEBUG.isGreaterOrEqual(l)) {
            return ArrayUtil.varArgsToParamString(o);
        }
        return null;
    }

    /**
     * Proceeds the o's even if log level is equals.
     *
     * @param o var args to tranform to parameter string.
     * @return Parameter string, if log level is equals.
     */
    public String infoVarArgsToParamString(Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.INFO.isGreaterOrEqual(l)) {
            return ArrayUtil.varArgsToParamString(o);
        }
        return null;
    }

    /**
     * Proceeds the o's even if log level is equals.
     *
     * @param o var args to tranform to parameter string.
     * @return Parameter string, if log level is equals.
     */
    public String errorVarArgsToParamString(Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.ERROR.isGreaterOrEqual(l)) {
            return ArrayUtil.varArgsToParamString(o);
        }
        return null;
    }

    /**
     * Proceeds the o's even if log level is equals.
     *
     * @param o var args to tranform to parameter string.
     * @return Parameter string, if log level is equals.
     */
    public String fatalVarArgsToParamString(Object... o) {
        Level l = this.logger.getEffectiveLevel();
        if (Level.FATAL.isGreaterOrEqual(l)) {
            return ArrayUtil.varArgsToParamString(o);
        }
        return null;
    }

    /**
     * @param logger the logger to set
     */
    private void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * @return logger
     */
    public Logger getLogger() {
        return this.logger;
    }

    /**
     * Creates a string from object array.
     *
     * @param o Object array.
     * @return String from object array.
     */
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
        Level l = this.logger.getEffectiveLevel();
        if (Level.FATAL.isGreaterOrEqual(l)) {
            this.logger.fatal(this.getO(o));
        } else if (Level.ERROR.isGreaterOrEqual(l)) {
            this.logger.error(this.getO(o));
        } else if (Level.INFO.isGreaterOrEqual(l)) {
            this.logger.info(this.getO(o));
        } else if (Level.WARN.isGreaterOrEqual(l)) {
            this.logger.warn(this.getO(o));
        } else if (Level.DEBUG.isGreaterOrEqual(l)) {
            this.logger.debug(this.getO(o));
        }
    }

    private void logException(Level level, Throwable t) {
        this.logException(level, t, false);
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
        this.log(s);
        s.setLength(0);
        List<StackTraceElement> l0 = Arrays.asList(t.getStackTrace());
        for (int i = 0, n = l0.size(); i < n; i++) {
            if (i > 0) {
                s.append("    ");
            }
            s.append(String.valueOf(l0.get(i)));
            this.log(s);
            s.setLength(0);
        }
        this.logException(level, t.getCause(), true);
        return;
    }

}