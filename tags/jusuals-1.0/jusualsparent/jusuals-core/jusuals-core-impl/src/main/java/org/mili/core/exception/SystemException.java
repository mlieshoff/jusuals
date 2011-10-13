/*
 * SystemException.java
 *
 * 10.02.2010
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

/**
 * This class defines a system exception.
 *
 * @author Michael Lieshoff
 *
 */
public class SystemException extends WrappedException {

    /**
     * Instantiates a new system exception.
     *
     * @param messageType the message type
     * @param exceptionLevel the exception level
     * @param parameters the parameters
     */
    public SystemException(MessageType messageType, ExceptionLevel exceptionLevel,
            Object... parameters) {
        super(messageType, ExceptionTypeEnum.SYSTEM, exceptionLevel, parameters);
    }

    /**
     * Instantiates a new system exception.
     *
     * @param messagetype the messagetype
     * @param cause the cause
     * @param exceptionLevel the exception level
     * @param parameters the parameters
     */
    public SystemException(MessageType messagetype, Throwable cause, ExceptionLevel
            exceptionLevel, Object... parameters) {
        super(messagetype, cause, ExceptionTypeEnum.SYSTEM, exceptionLevel, parameters);
    }

    /**
     * Instantiates a new system exception.
     *
     * @param cause the cause
     * @param exceptionLevel the exception level
     * @param parameters the parameters
     */
    public SystemException(Throwable cause, ExceptionLevel exceptionLevel,
            Object... parameters) {
        super(cause, ExceptionTypeEnum.SYSTEM, exceptionLevel, parameters);
    }

}
