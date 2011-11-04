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
import java.lang.reflect.*;

import org.apache.commons.lang.*;
import org.apache.commons.lang.reflect.*;
import org.mili.core.logging.java.*;
import org.mili.core.logging.log4j.*;
import org.mili.core.properties.*;

import sun.reflect.*;


/**
 * This class defines an implementation of the facade defines in interface Logger.
 *
 * <p><b>Introduction</b><p>
 *
 * <u> What is DefaultLogger?</u><p>
 *
 * DefaultLogger is a logging facade for Java. Technical it is an implementation of interface
 * org.mili.logging.Logger where a logging facade is defined. This interface provides a couple of
 * methods to log like in Log4j and little bit more. The logger is independent of the underlying
 * logging system. So by default Log4j is used but it is very easy to switch to java.util.logging
 * with this facade changing absolutely nothing in your implementation.<p>
 *
 * <u> Future of DefaultLogger</u><p>
 *
 * The DefaultLogger is used in three companies and a couple of open-source projects.<p>
 * <b>Usage</b><p>
 *
 * <u> How to setup?</u><p>
 *
 * The setup of the DefaultLogger is easy as well. Just define a member in your class and set up
 * a Log4j configuration.<br>
 * <pre>
 * import org.mili.logging.*;
 * public class Foo {
 *     private final static Logger LOG = new DefaultLogger(Foo.class);
 *     private final static Logger LOG = DefaultLogger.getLogger(Foo.class);
 *     private final static Logger LOG = DefaultLogger.create(Foo.class);
 *
 *     // or for implicitly use in Foo.class
 *
 *     private final static Logger LOG = new DefaultLogger();
 *     private final static Logger LOG = DefaultLogger.getLoggerForCaller();
 *     private final static Logger LOG = DefaultLogger.createForCaller();
 * }
 * </pre>
 * How to switch to java.util.logging ? It is quite easy. Feel free to set a property file for
 * logging configuration and just set a system property like this:<br>
 * <pre>
 * System.setProperty(org.mili.logging.DefaultLogger.PROP_ADAPTERCLASS, org.mili.logging.java.JavaAdapter.getName());
 *
 * // or
 *
 * DefaultLogger.setAdapterClass(org.mili.logging.java.JavaAdapter.class);
 * </pre>
 *
 * <u> What methods are there for logging?</u><p>
 *
 * There are methods to log for every log levels known in Log4j. The DefaultLogger maps the
 * java.util.logging Levels to these levels. The mapped levels are in braces.<br>
 * <pre>
 * - trace    (finest)
 * - debug    (fine, finer)
 * - info     (info, config)
 * - warn     (warning)
 * - error    (severe)
 * - fatal    (severe)
 * </pre>
 * This basic methods can be parametrized in two ways. &lt;level&gt; stands for one of the levels. The
 * objects parameter will be transformed to a string like &quot;a=1, b=4711&quot; for example &quot;a, 1, b, 4711&quot; and so on. Do not
 * concatenate string at this point it will decrease performance! The string will only and only
 * calculated if the log levels fits otherwise it will be ignored and do not costs performance.<br>
 * <pre>
 * void &lt;level&gt;(Throwable t, Object... o)
 * void &lt;level&gt;(Object... o)
 * </pre>
 * For more structural logs you can mark a block for delegating, beginning and ending. Use
 * delegate if your methods just delegates to another one. Use beginning to mark the
 * beginning of a levelled block and ending to mark it's ending.<br>
 * <pre>
 * void begin&lt;Level&gt;(String method, Object... o)
 * void end&lt;Level&gt;(String method, Object... o)
 * void delegate&lt;Level&gt;(String method, Object... o)
 * </pre>
 * The received output is:<br>
 * <pre>
 * BEGIN method1: a, 1, b, abbas
 * ...
 * BEGIN method3: s, hello
 * ...
 * END method3.
 * ...
 * DELEGATE method2: x, 4711
 * ...
 * END method1.
 * </pre>
 * You can use methods to generate this logged string as object array:<br>
 * <pre>
 * Object[] delegate(String method, Object... o)
 * Object[] end(String method, Object... o)
 * Object[] delegate(String method, Object... o)
 * </pre>
 * To generate more readable parameter informations for log entries you can use the methods:<br>
 * <pre>
 * String &lt;level&gt;VarArgsToParamString(Object... o);
 * ...
 * System.out.println(LOG.debugVarArgsToParamString(&quot;a&quot;, 1, &quot;s&quot;, &quot;abbas&quot;));
 * </pre>
 * Results in:<br>
 * <pre>
 * a=1, s=abbas
 * </pre>
 * Only and only if the log levels fits for debug.<br>
 * Feel free to combine everything:<br>
 * <pre>
 * try {
 *     ...
 * } catch(Exception e) {
 *     LOG.error(e, LOG.delegate(&quot;my delegate for errors&quot;, LOG.debugVarArgsToParamString(&quot;a&quot;, 1, &quot;s&quot;, &quot;abbas&quot;)));
 *     // shortcut is
 *     // LOG.delegateError(&quot;my delegate for errors&quot;, e, LOG.debugVarArgsToParamString(&quot;a&quot;, 1, &quot;s&quot;, &quot;abbas&quot;));
 * }
 * </pre>
 * What happens for log level error?<br>
 * <pre>
 * ERROR delegate my delegate for errors:
 * </pre>
 * And for debug?<br>
 * <pre>
 * ERROR delegate my delegate for errors: a=1, s=abbas
 * </pre>
 *
 * <u> How to log throwables in separate?</u><p>
 *
 * You can log throwables in separate directory and file structure. At each time a throwable is
 * logged it will be logged on filesystem too. To turn on this behaviour just set a system property:<br>
 * <pre>
 * System.setProperty(org.mili.logging.DefaultLogger.PROP_LOGTHROWABLES, &quot;true&quot;);
 * </pre>
 * And if another base dir then system property &quot;java.io.tmpdir&quot; is wanted:<br>
 * <pre>
 * System.setProperty(org.mili.logging.DefaultLogger.PROP_LOGTHROWABLESDIR, &quot;what/ever/you/want&quot;);
 * </pre>
 * The resulting structure should look like this:<br>
 * <pre>
 * - what
 *     - ever
 *         - you
 *             - want
 *                 - 20110101
 *                     - my.package.and.my.class.Foo
 * </pre>
 * The stacktrace of the throwbables occured in the referenced class will be appended at this
 * file. A timestamp and a special string is used to separate the stacktraces<p>
 *
 * @author Michael Lieshoff
 */
public class DefaultLogger implements Logger {
    /** property to log throwables onto filesystem. */
    public final static String PROP_LOGTHROWABLES = DefaultLogger.class.getName()
            + ".LogThrowables";
    /** property to set throwables log directory. */
    public final static String PROP_LOGTHROWABLESDIR = DefaultLogger.class.getName()
            + ".LogThrowablesDir";
    /** property for used adapter class. */
    public final static String PROP_ADAPTERCLASS = DefaultLogger.class.getName()
            + ".AdapterClass";
    private Logger root = null;
    private ThrowableLogger throwableLogger = null;
    private Class<?> cls = null;

    /**
     * Create a new logger for a caller.
     */
    public DefaultLogger() {
        this.root = create(Reflection.getCallerClass(2));
        this.cls = this.root.getLoggedClass();
    }

    /**
     * Create a new logger from class clazz.
     *
     * @param clazz the class.
     */
    public DefaultLogger(Class<?> clazz) {
        Validate.notNull(clazz, "class cannot be null!");
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
     * Gets the logger for caller.
     *
     * @return the logger for caller
     */
    public static DefaultLogger getLoggerForCaller() {
        return getLogger(getCaller());
    }

    private static Class<?> getCaller() {
        return Reflection.getCallerClass(3);
    }

    /**
     * Static method to create a new logger from class clazz.
     *
     * @param clazz the class.
     * @return logger.
     */
    public static Logger create(Class<?> clazz) {
        String adapterClassname = PropUtil.getSystem(PROP_ADAPTERCLASS, Log4jAdapter.class
                .getName());
        Class<?> cls;
        try {
            cls = Class.forName(adapterClassname);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        Constructor<Logger> constructor = ConstructorUtils.getAccessibleConstructor(cls,
                Class.class);
        try {
            return constructor.newInstance(clazz);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Creates the logger for the caller.
     *
     * @return the logger
     */
    public static Logger createForCaller() {
        return create(getCaller());
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
                this.throwableLogger = new DefaultThrowableLogger(this.cls, dir);
            }
            try {
                this.throwableLogger.log(throwable);
            } catch (IOException e) {
                this.root.error(e);
            }
        }
    }

    void setThrowableLogger(ThrowableLogger throwableLogger) {
        this.throwableLogger = throwableLogger;
    }

    void setRoot(Logger logger) {
        this.root = logger;
    }

    @Override
    public Class<?> getLoggedClass() {
        return cls;
    }

    /**
     * Sets the adapter class.
     *
     * @param adapterClass the new adapter class
     */
    public static void setAdapterClass(Class<JavaAdapter> adapterClass) {
        PropUtil.setSystem(DefaultLogger.PROP_ADAPTERCLASS, adapterClass.getName());
    }

}
