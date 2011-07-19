/*
 * WrappedException.java
 *
 * 09.02.2010
 *
 * Copyright 2010 Michael Lieshoff
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

package org.mili.core.exception;

import java.util.*;

/**
 * This class defines a wrapped exception.
 *
 * @author Michael Lieshoff
 *
 */
public class WrappedException extends Exception {
    private Map<String, Object> params = new Hashtable<String, Object>();
    private ExceptionType exceptionType = null;
    private ExceptionLevel exceptionLevel = null;
    private MessageType messageType = null;

    /**
     * Instantiates a new wrapped exception.
     */
    protected WrappedException() {
        super();
    }

    /**
     * Instantiates a new wrapped exception.
     *
     * @param messageType the message type
     * @param cause the cause
     * @param exceptionType the exception type
     * @param exceptionLevel the exception level
     * @param parameters the parameters
     */
    public WrappedException(MessageType messageType, Throwable cause,
            ExceptionType exceptionType, ExceptionLevel exceptionLevel, Object... parameters) {
        super(cause);
        this.messageType = messageType;
        this.exceptionType = exceptionType;
        this.exceptionLevel = exceptionLevel;
        this.fillParams(parameters);
    }

    /**
     * Instantiates a new wrapped exception.
     *
     * @param messageType the message type
     * @param exceptionType the exception type
     * @param exceptionLevel the exception level
     * @param parameters the parameters
     */
    public WrappedException(MessageType messageType, ExceptionType exceptionType,
            ExceptionLevel exceptionLevel, Object... parameters) {
        this(messageType, null, exceptionType, exceptionLevel, parameters);
    }

    /**
     * Instantiates a new wrapped exception.
     *
     * @param cause the cause
     * @param exceptionType the exception type
     * @param exceptionLevel the exception level
     * @param parameters the parameters
     */
    public WrappedException(Throwable cause, ExceptionType exceptionType,
            ExceptionLevel exceptionLevel, Object... parameters) {
        this(null, cause, exceptionType, exceptionLevel, parameters);
    }

    private void fillParams(Object... params) {
        if (params != null && params.length > 0) {
            if (params.length % 2 != 0) {
                throw new IllegalArgumentException(
                        "params must have form name0, value0, ..., nameN, valueN");
            }
            for (int i = 0; i < params.length; i = i + 2) {
                String k = String.valueOf(params[i]);
                Object v = params[i + 1];
                this.params.put(k, v == null ? "NULL" : v);
            }
        }
    }

    /**
     * @return the params
     */
    public Map<String, Object> getParams() {
        return new Hashtable<String, Object>(this.params);
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * @return the exceptionType
     */
    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    /**
     * @param exceptionType the exceptionType to set
     */
    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    /**
     * @return the exceptionLevel
     */
    public ExceptionLevel getExceptionLevel() {
        return exceptionLevel;
    }

    /**
     * @param exceptionLevel the exceptionLevel to set
     */
    public void setExceptionLevel(ExceptionLevel exceptionLevel) {
        this.exceptionLevel = exceptionLevel;
    }

    /**
     * @return the messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getMessage() {
        StringBuilder s = new StringBuilder();
        if (this.messageType == null && this.getCause() != null) {
            s.append(this.getCause().getClass().getName());
        } else {
            s.append(this.messageType);
        }
        s.append(" ");
        s.append(this.getExceptionLevel());
        s.append(" [");
        s.append(this.getExceptionType());
        s.append("]");
        if (this.getCause() != null) {
            s.append(", cause is ");
            s.append(this.getCause().getMessage());
        }
        s.append(", parameters[");
        for (Iterator<String> i = this.params.keySet().iterator(); i.hasNext();) {
            String k = i.next();
            Object v = this.params.get(k);
            s.append(k);
            s.append("=");
            s.append(String.valueOf(v));
            if (i.hasNext()) {
                s.append(", ");
            }
        }
        s.append("].");
        return s.toString();
    }

}
