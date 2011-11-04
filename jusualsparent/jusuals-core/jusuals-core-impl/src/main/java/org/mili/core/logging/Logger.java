/*
 * Logger.java
 *
 * 01.02.2011
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
package org.mili.core.logging;


/**
 * This interface defines a logger.
 *
 * @author Michael Lieshoff
 */
public interface Logger {

    /**
     * Gets the basic logger implementation.
     *
     * @return the logger
     */
    Object getLogger();

    /**
     * Traces a message and a throwable represented as array of objects.
     *
     * @param t Throwable.
     * @param o Message represented as array of objects.
     */
    void trace(Throwable t, Object... o);

    /**
     * Traces a message represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    void trace(Object... o);

    /**
     * Warns a message and a throwable represented as array of objects.
     *
     * @param t    Throwable.
     * @param o Message represented as array of objects.
     */
    void warn(Throwable t, Object... o);

    /**
     * Warns a message represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    void warn(Object... o);

    /**
     * Debugs a message and a throwable represented as array of objects.
     *
     * @param t Throwable.
     * @param o Message represented as array of objects.
     */
    void debug(Throwable t, Object... o);

    /**
     * Debugs a message represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    void debug(Object... o);

    /**
     * Infos a message and a throwable represented as array of objects.
     *
     * @param t Throwable.
     * @param o Message represented as array of objects.
     */
    void info(Throwable t, Object... o);

    /**
     * Infos a message represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    void info(Object... o);

    /**
     * Errors a message and a throwable represented as array of objects.
     *
     * @param t Throwable.
     * @param o Message represented as array of objects.
     */
    void error(Throwable t, Object... o);

    /**
     * Errors a message represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    void error(Object... o);

    /**
     * Debugs a message and a throwable represented as array of objects.
     *
     * @param t Throwable.
     * @param o Message represented as array of objects.
     */
    void fatal(Throwable t, Object... o);

    /**
     * Fatals a message represented as array of objects.
     *
     * @param o Message represented as array of objects.
     */
    void fatal(Object... o);

    /**
     * Creates a begin warn log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void beginWarn(String method, Object... o);

    /**
     * Creates a begin debug log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void beginDebug(String method, Object... o);

    /**
     * Creates a begin info log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void beginInfo(String method, Object... o);

    /**
     * Creates a begin error log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void beginError(String method, Object... o);

    /**
     * Creates a begin fatal log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void beginFatal(String method, Object... o);

    /**
     * Creates a begin message.
     *
     * @param method method name.
     * @param o objects to message.
     * @return something like BEGIN: method os
     */
    Object[] begin(String method, Object... o);

    /**
     * Creates a delegate warn log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void delegateWarn(String method, Object... o);

    /**
     * Creates a delegate debug log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void delegateDebug(String method, Object... o);

    /**
     * Creates a delegate info log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void delegateInfo(String method, Object... o);

    /**
     * Creates a delegate error log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void delegateError(String method, Object... o);

    /**
     * Creates a delegate fatal log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void delegateFatal(String method, Object... o);

    /**
     * Creates a delegate message.
     *
     * @param method method name.
     * @param o objects to message.
     * @return something like DELEGSTE: method os
     */
    Object[] delegate(String method, Object... o);

    /**
     * Creates a end warn log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void endWarn(String method, Object... o);

    /**
     * Creates a end debug log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void endDebug(String method, Object... o);

    /**
     * Creates a end info log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void endInfo(String method, Object... o);

    /**
     * Creates a end error log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void endError(String method, Object... o);

    /**
     * Creates a end fatal log entry.
     *
     * @param method Method name.
     * @param o Message represented as var args.
     */
    void endFatal(String method, Object... o);

    /**
     * Creates a end message.
     *
     * @param method method name.
     * @param o objects to message.
     * @return something like END: method os
     */
    Object[] end(String method, Object... o);

    /**
     * Proceeds the o's even if log level is equals.
     *
     * @param o var args to tranform to parameter string.
     * @return Parameter string, if log level is equals.
     */
    String warnVarArgsToParamString(Object... o);

    /**
     * Proceeds the o's even if log level is equals.
     *
     * @param o var args to tranform to parameter string.
     * @return Parameter string, if log level is equals.
     */
    String debugVarArgsToParamString(Object... o);

    /**
     * Proceeds the o's even if log level is equals.
     *
     * @param o var args to tranform to parameter string.
     * @return Parameter string, if log level is equals.
     */
    String infoVarArgsToParamString(Object... o);

    /**
     * Proceeds the o's even if log level is equals.
     *
     * @param o var args to tranform to parameter string.
     * @return Parameter string, if log level is equals.
     */
    String errorVarArgsToParamString(Object... o);

    /**
     * Proceeds the o's even if log level is equals.
     *
     * @param o var args to tranform to parameter string.
     * @return Parameter string, if log level is equals.
     */
    String fatalVarArgsToParamString(Object... o);

    /**
     * Gets the logged class.
     *
     * @return the logged class
     */
    Class<?> getLoggedClass();

}