/*
 * Log4jAdapterTest.java
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

import java.util.logging.*;

import org.apache.commons.lang.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class JavaAdapterTest {
    private JavaAdapter logger = new JavaAdapter(String.class);
    private Logger log = (Logger) logger.getLogger();

    @Before
    public void setUp() {
        log.setLevel(Level.ALL);
    }

    @Test
    public void testWarnThrowableObjectArray() {
        logger.warn(new NullPointerException("an exception"), "param1", 4711L);
    }

    @Test
    public void testWarnObjectArray() {
        logger.warn("param1", 4711L);
    }

    @Test
    public void testDebugThrowableObjectArray() {
        logger.debug(new NullPointerException("an exception"), "param1", 4711L);
    }

    @Test
    public void testDebugObjectArray() {
        logger.debug("param1", 4711L);
    }

    @Test
    public void testInfoThrowableObjectArray() {
        logger.info(new NullPointerException("an exception"), "param1", 4711L);
    }

    @Test
    public void testInfoObjectArray() {
        logger.info("param1", 4711L);
    }

    @Test
    public void testErrorThrowableObjectArray() {
        logger.error(new NullPointerException("an exception"), "param1", 4711L);
    }

    @Test
    public void testErrorObjectArray() {
        logger.error("param1", 4711L);
    }

    @Test
    public void testFatalThrowableObjectArray() {
        logger.fatal(new NullPointerException("an exception"), "param1", 4711L);
    }

    @Test
    public void testFatalObjectArray() {
        logger.fatal("param1", 4711L);
    }

    @Test
    public void testBeginWarn() {
        logger.beginWarn("house burning", "abbas is in");
    }

    @Test
    public void testBeginDebug() {
        logger.beginDebug("house burning", "abbas is in");
    }

    @Test
    public void testBeginInfo() {
        logger.beginInfo("house burning", "abbas is in");
    }

    @Test
    public void testBeginError() {
        logger.beginError("house burning", "abbas is in");
    }

    @Test
    public void testBeginFatal() {
        logger.beginFatal("house burning", "abbas is in");
    }

    @Test
    public void testBegin() {
        logger.begin("a", "b");
    }

    @Test
    public void testDelegateWarn() {
        logger.delegateWarn("house burning", "abbas is in");
    }

    @Test
    public void testDelegateDebug() {
        logger.delegateDebug("house burning", "abbas is in");
    }

    @Test
    public void testDelegateInfo() {
        logger.delegateInfo("house burning", "abbas is in");
    }

    @Test
    public void testDelegateError() {
        logger.delegateError("house burning", "abbas is in");
    }

    @Test
    public void testDelegateFatal() {
        logger.delegateFatal("house burning", "abbas is in");
    }

    @Test
    public void testDelegate() {
        logger.delegate("house burning", "abbas is in");
    }

    @Test
    public void testEndWarn() {
        logger.endWarn("house burning", "abbas is in");
    }

    @Test
    public void testEndDebug() {
        logger.endDebug("house burning", "abbas is in");
    }

    @Test
    public void testEndInfo() {
        logger.endInfo("house burning", "abbas is in");
    }

    @Test
    public void testEndError() {
        logger.endError("house burning", "abbas is in");
    }

    @Test
    public void testEndFatal() {
        logger.endFatal("house burning", "abbas is in");
    }

    @Test
    public void testEnd() {
        logger.end("house burning", "abbas is in");
    }

    @Test
    public void testWarnVarArgsToParamString() {
        logger.warnVarArgsToParamString("abbas", 4711L);
    }

    @Test
    public void testDebugVarArgsToParamString() {
        logger.debugVarArgsToParamString("abbas", 4711L);
    }

    @Test
    public void testInfoVarArgsToParamString() {
        logger.infoVarArgsToParamString("abbas", 4711L);
    }

    @Test
    public void testErrorVarArgsToParamString() {
        logger.errorVarArgsToParamString("abbas", 4711L);
    }

    @Test
    public void testFatalVarArgsToParamString() {
        logger.fatalVarArgsToParamString("abbas", 4711L);
    }

    @Test
    public void testGetLogger() {
        assertEquals(log, logger.getLogger());
    }

    @Test
    public void shouldLogsATrace() {
        logger.trace("a", 4711L);
    }

    @Test
    public void shouldLogsATraceException() {
        logger.trace(new NullArgumentException("an exception"), "abbas");
    }

    @Test
    public void shouldGetTraceLevelForAll() {
        log.setLevel(Level.ALL);
        assertEquals(org.mili.core.logging.Level.TRACE, logger.getLevel());
    }

    @Test
    public void shouldGetTraceLevelForFinest() {
        log.setLevel(Level.FINEST);
        assertEquals(org.mili.core.logging.Level.TRACE, logger.getLevel());
    }

    @Test
    public void shouldGetDebugLevelForFine() {
        log.setLevel(Level.FINE);
        assertEquals(org.mili.core.logging.Level.DEBUG, logger.getLevel());
    }

    @Test
    public void shouldGetDebugLevelForFiner() {
        log.setLevel(Level.FINER);
        assertEquals(org.mili.core.logging.Level.DEBUG, logger.getLevel());
    }

    @Test
    public void shouldGetInfoLevelForInfo() {
        log.setLevel(Level.INFO);
        assertEquals(org.mili.core.logging.Level.INFO, logger.getLevel());
    }

    @Test
    public void shouldGetInfoLevelForConfig() {
        log.setLevel(Level.INFO);
        assertEquals(org.mili.core.logging.Level.INFO, logger.getLevel());
    }

    @Test
    public void shouldGetWarnLevelForWarning() {
        log.setLevel(Level.WARNING);
        assertEquals(org.mili.core.logging.Level.WARN, logger.getLevel());
    }

    @Test
    public void shouldGetErrorOrFatalLevelForError() {
        log.setLevel(Level.SEVERE);
        assertEquals(org.mili.core.logging.Level.ERROR, logger.getLevel());
    }

}
