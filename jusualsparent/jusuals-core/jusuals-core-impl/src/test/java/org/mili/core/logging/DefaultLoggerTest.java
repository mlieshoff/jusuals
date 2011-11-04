/*
 * DefaultLoggerTest.java
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
import java.text.*;
import java.util.*;

import org.easymock.*;
import org.junit.*;
import org.mili.core.logging.java.*;
import org.mili.test.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class DefaultLoggerTest {
    private final static File DIR = TestUtils.getTmpFolder(DefaultLoggerTest.class);
    private final static File TMP = new File(System.getProperty("java.io.tmpdir"));
    private final static File TMPDAYDIR = new File(TMP, new SimpleDateFormat("yyyyMMdd").format(
            new Date()));
    private final static File TESTDAYDIR = new File(DIR, new SimpleDateFormat("yyyyMMdd")
            .format(new Date()));
    private DefaultLogger logger = DefaultLogger.getLogger(String.class);
    private Logger root = null;
    private Object[] emptyResult = null;

    @BeforeClass
    public static void setUpClass() {
        DIR.mkdirs();
        TMPDAYDIR.mkdirs();
    }

    @Before
    public void setUp() {
        System.clearProperty(DefaultLogger.PROP_LOGTHROWABLES);
        System.clearProperty(DefaultLogger.PROP_LOGTHROWABLESDIR);
        System.clearProperty(DefaultLogger.PROP_ADAPTERCLASS);
        this.root = EasyMock.createMock(Logger.class);
        this.logger.setRoot(this.root);
    }

    @Test
    public void shouldInstantiateParameterlessWithCreateForCaller() {
        Logger log = DefaultLogger.createForCaller();
        assertEquals(getClass(), log.getLoggedClass());
    }

    @Test
    public void shouldInstantiateParameterlessWithGetLoggerForCaller() {
        DefaultLogger log = DefaultLogger.getLoggerForCaller();
        assertEquals(getClass(), log.getLoggedClass());
    }

    @Test
    public void shouldInstantiateParameterlessWithConstructor() {
        DefaultLogger log = new DefaultLogger();
        assertEquals(getClass(), log.getLoggedClass());
    }

    @Test
    public void shouldSetAdapterClassWithProp() {
        Logger log = DefaultLogger.getLoggerForCaller();
        assertEquals(org.apache.log4j.Logger.class, log.getLogger().getClass());
        System.setProperty(DefaultLogger.PROP_ADAPTERCLASS, JavaAdapter.class.getName());
        log = DefaultLogger.getLoggerForCaller();
        assertEquals(java.util.logging.Logger.class, log.getLogger().getClass());
    }

    @Test
    public void shouldSetAdapterClassWithSetMethod() {
        Logger log = DefaultLogger.getLoggerForCaller();
        assertEquals(org.apache.log4j.Logger.class, log.getLogger().getClass());
        DefaultLogger.setAdapterClass(JavaAdapter.class);
        log = DefaultLogger.getLoggerForCaller();
        assertEquals(java.util.logging.Logger.class, log.getLogger().getClass());
    }

    @Test
    public void shouldNotNull() {
        assertNotNull(this.logger);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failsBecauseNullClass() {
        assertNotNull(DefaultLogger.getLogger(null));
    }

    @Test
    public void shouldIgnoresIOExceptionWithinLog() {
        System.setProperty(DefaultLogger.PROP_LOGTHROWABLES, "true");
        this.logger.setThrowableLogger(new IOExceptionThrowableLogger());
        this.logger.error(new IllegalArgumentException("lala"));
    }

    @Test
    public void shouldLogErrorExceptionInTmpDir() {
        System.setProperty(DefaultLogger.PROP_LOGTHROWABLES, "true");
        this.logger.error(new IllegalArgumentException("lala"));
        assertTrue(TMPDAYDIR.exists());
        System.out.println(new File(TMPDAYDIR, "java.lang.String.log").getAbsolutePath());
        assertTrue(new File(TMPDAYDIR, "java.lang.String.log").exists());
    }

    @Test
    public void shouldLogErrorExceptionInSpecifiedDir() {
        System.setProperty(DefaultLogger.PROP_LOGTHROWABLES, "true");
        System.setProperty(DefaultLogger.PROP_LOGTHROWABLESDIR, DIR.getAbsolutePath());
        DefaultLogger.getLogger(String.class).error(new IllegalArgumentException("lala"));
        assertTrue(TESTDAYDIR.exists());
        System.out.println(new File(TESTDAYDIR, "java.lang.String.log").getAbsolutePath());
        assertTrue(new File(TESTDAYDIR, "java.lang.String.log").exists());
    }

    @Test
    public void shouldBegin() {
        EasyMock.expect(this.root.begin("begin")).andReturn(this.emptyResult);
        EasyMock.replay(this.root);
        this.logger.begin("begin");
    }

    @Test
    public void shouldBeginDebug() {
        this.root.beginDebug("beginDebug");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.beginDebug("beginDebug");
    }

    @Test
    public void shouldBeginFatal() {
        this.root.beginFatal("beginFatal");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.beginFatal("beginFatal");
    }

    @Test
    public void shouldBeginError() {
        this.root.beginError("beginError");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.beginError("beginError");
    }

    @Test
    public void shouldBeginInfo() {
        this.root.beginInfo("beginInfo");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.beginInfo("beginInfo");
    }

    @Test
    public void shouldBeginWarn() {
        this.root.beginWarn("beginWarn");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.beginWarn("beginWarn");
    }

    @Test
    public void shouldEnd() {
        EasyMock.expect(this.root.end("end")).andReturn(this.emptyResult);
        EasyMock.replay(this.root);
        this.logger.end("end");
    }

    @Test
    public void shouldEndDebug() {
        this.root.endDebug("endDebug");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.endDebug("endDebug");
    }

    @Test
    public void shouldEndFatal() {
        this.root.endFatal("endFatal");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.endFatal("endFatal");
    }

    @Test
    public void shouldEndError() {
        this.root.endError("endError");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.endError("endError");
    }

    @Test
    public void shouldEndInfo() {
        this.root.endInfo("endInfo");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.endInfo("endInfo");
    }

    @Test
    public void shouldEndWarn() {
        this.root.endWarn("endWarn");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.endWarn("endWarn");
    }

    @Test
    public void shouldDelegate() {
        EasyMock.expect(this.root.delegate("delegate")).andReturn(this.emptyResult);
        EasyMock.replay(this.root);
        this.logger.delegate("delegate");
    }

    @Test
    public void shouldDelegateDebug() {
        this.root.delegateDebug("delegateDebug");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.delegateDebug("delegateDebug");
    }

    @Test
    public void shouldDelegateFatal() {
        this.root.delegateFatal("delegateFatal");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.delegateFatal("delegateFatal");
    }

    @Test
    public void shouldDelegateError() {
        this.root.delegateError("delegateError");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.delegateError("delegateError");
    }

    @Test
    public void shouldDelegateInfo() {
        this.root.delegateInfo("delegateInfo");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.delegateInfo("delegateInfo");
    }

    @Test
    public void shouldDelegateWarn() {
        this.root.delegateWarn("delegateWarn");
        EasyMock.expectLastCall();
        EasyMock.replay(this.root);
        this.logger.delegateWarn("delegateWarn");
    }

    @Test
    public void shouldDebug() {
        this.root.debug("debug");
        EasyMock.replay(this.root);
        this.logger.debug("debug");
    }

    @Test
    public void shouldDebugThrowable() {
        this.logger.debug(new IllegalArgumentException());
    }

    @Test
    public void shouldDebugVarArgs() {
        this.logger.debugVarArgsToParamString("a", "b");
    }

    @Test
    public void shouldError() {
        this.root.error("error");
        EasyMock.replay(this.root);
        this.logger.error("error");
    }

    @Test
    public void shouldErrorThrowable() {
        this.logger.error(new IllegalArgumentException());
    }

    @Test
    public void shouldErrorVarArgs() {
        this.logger.errorVarArgsToParamString("a", "b");
    }

    @Test
    public void shouldFatal() {
        this.root.fatal("fatal");
        EasyMock.replay(this.root);
        this.logger.fatal("fatal");
    }

    @Test
    public void shouldFatalThrowable() {
        this.logger.fatal(new IllegalArgumentException());
    }

    @Test
    public void shouldFatalVarArgs() {
        this.logger.fatalVarArgsToParamString("a", "b");
    }

    @Test
    public void shouldInfo() {
        this.root.info("info");
        EasyMock.replay(this.root);
        this.logger.info("info");
    }

    @Test
    public void shouldInfoThrowable() {
        this.logger.info(new IllegalArgumentException());
    }

    @Test
    public void shouldInfoVarArgs() {
        this.logger.infoVarArgsToParamString("a", "b");
    }

    @Test
    public void shouldWarn() {
        this.root.warn("warn");
        EasyMock.replay(this.root);
        this.logger.warn("warn");
    }

    @Test
    public void shouldWarnThrowable() {
        this.logger.warn(new IllegalArgumentException());
    }

    @Test
    public void shouldWarnVarArgs() {
        this.logger.warnVarArgsToParamString("a", "b");
    }

    @Test
    public void shouldTrace() {
        this.root.trace("trace");
        EasyMock.replay(this.root);
        this.logger.trace("trace");
    }

    @Test
    public void shouldGetLogger() {
        EasyMock.expect(this.root.getLogger()).andReturn(this.logger);
        EasyMock.replay(this.root);
        assertEquals(this.logger, this.logger.getLogger());
    }

    @Test
    public void shouldTraceThrowable() {
        this.logger.trace(new IllegalArgumentException());
    }

    class IOExceptionThrowableLogger implements ThrowableLogger {
        @Override
        public void log(Throwable throwable) throws IOException {
            throw new IOException();
        }
    }
}
