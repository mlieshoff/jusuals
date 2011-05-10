/*
 * Log4jAdapterTest.java
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

package org.mili.core.logging.log4j;

import org.apache.log4j.*;
import org.apache.log4j.spi.*;
import org.junit.*;
import org.mili.core.logging.Logger;
import org.mili.core.logging.log4j.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class Log4jAdapterTest {

    private Logger logger = new Log4jAdapter(String.class);
    private org.apache.log4j.Logger baseLogger = (org.apache.log4j.Logger) this.logger
            .getLogger();
    private TestAppender testAppender = new TestAppender();
    private Level actLevel = null;
    private String actMessage = null;

    @Before
    public void setUp() {
        BasicConfigurator.configure(this.testAppender);
        this.baseLogger.setLevel(Level.ALL);
    }

    @Test
    public void testDefaultLogger() {
        new Log4jAdapter();
    }

    @Test
    public void testWarnThrowableObjectArray() {
        this.baseLogger.setLevel(Level.WARN);
        this.logger.warn(new NullPointerException(), "a");
    }

    @Test
    public void testWarnObjectArray() {
        this.baseLogger.setLevel(Level.WARN);
        this.logger.warn("a");
        assertEquals(Level.WARN, this.actLevel);
        assertEquals("a", this.actMessage);
    }

    @Test
    public void testDebugThrowableObjectArray() {
        this.baseLogger.setLevel(Level.DEBUG);
        this.logger.debug(new NullPointerException(), "a");
        // test o[][]
        this.logger.debug(new NullPointerException(), new Object[][]{{"a"}, {"b"}});
        // test caused by exception
        this.logger.debug(new Exception("", new IllegalArgumentException()),
                new Object[][]{{"a"}, {"b"}});
    }

    @Test
    public void testDebugObjectArray() {
        this.baseLogger.setLevel(Level.DEBUG);
        this.logger.debug("a");
        assertEquals(Level.DEBUG, this.actLevel);
        assertEquals("a", this.actMessage);
    }

    @Test
    public void testInfoThrowableObjectArray() {
        this.baseLogger.setLevel(Level.INFO);
        this.logger.info(new NullPointerException(), "a");
    }

    @Test
    public void testInfoObjectArray() {
        this.baseLogger.setLevel(Level.INFO);
        this.logger.info("a");
        assertEquals(Level.INFO, this.actLevel);
        assertEquals("a", this.actMessage);
    }

    @Test
    public void testErrorThrowableObjectArray() {
        this.baseLogger.setLevel(Level.ERROR);
        this.logger.error(new NullPointerException(), "a");
    }

    @Test
    public void testErrorObjectArray() {
        this.baseLogger.setLevel(Level.ERROR);
        this.logger.error("a");
        assertEquals(Level.ERROR, this.actLevel);
        assertEquals("a", this.actMessage);
    }

    @Test
    public void testFatalThrowableObjectArray() {
        this.baseLogger.setLevel(Level.FATAL);
        this.logger.fatal(new NullPointerException(), "a");
    }

    @Test
    public void testFatalObjectArray() {
        this.baseLogger.setLevel(Level.FATAL);
        this.logger.fatal("a");
        assertEquals(Level.FATAL, this.actLevel);
        assertEquals("a", this.actMessage);
    }

    @Test
    public void testBeginWarn() {
        this.baseLogger.setLevel(Level.WARN);
        this.logger.beginWarn("a", "b", "c");
        assertEquals(Level.WARN, this.actLevel);
        assertEquals("BEGIN a: bc", this.actMessage);
    }

    @Test
    public void testBeginDebug() {
        this.baseLogger.setLevel(Level.DEBUG);
        this.logger.beginDebug("a", "b", "c");
        assertEquals(Level.DEBUG, this.actLevel);
        assertEquals("BEGIN a: bc", this.actMessage);
    }

    @Test
    public void testBeginInfo() {
        this.baseLogger.setLevel(Level.INFO);
        this.logger.beginInfo("a", "b", "c");
        assertEquals(Level.INFO, this.actLevel);
        assertEquals("BEGIN a: bc", this.actMessage);
    }

    @Test
    public void testBeginError() {
        this.baseLogger.setLevel(Level.ERROR);
        this.logger.beginError("a", "b", "c");
        assertEquals(Level.ERROR, this.actLevel);
        assertEquals("BEGIN a: bc", this.actMessage);
    }

    @Test
    public void testBeginFatal() {
        this.baseLogger.setLevel(Level.FATAL);
        this.logger.beginFatal("a", "b", "c");
        assertEquals(Level.FATAL, this.actLevel);
        assertEquals("BEGIN a: bc", this.actMessage);
    }

    @Test
    public void testBegin() {
        this.logger.begin("a", "b");
    }

    @Test
    public void testDelegateWarn() {
        this.baseLogger.setLevel(Level.WARN);
        this.logger.delegateWarn("a", "b", "c");
        assertEquals(Level.WARN, this.actLevel);
        assertEquals("DELEGATE a: bc", this.actMessage);
    }

    @Test
    public void testDelegateDebug() {
        this.baseLogger.setLevel(Level.DEBUG);
        this.logger.delegateDebug("a", "b", "c");
        assertEquals(Level.DEBUG, this.actLevel);
        assertEquals("DELEGATE a: bc", this.actMessage);
    }

    @Test
    public void testDelegateInfo() {
        this.baseLogger.setLevel(Level.INFO);
        this.logger.delegateInfo("a", "b", "c");
        assertEquals(Level.INFO, this.actLevel);
        assertEquals("DELEGATE a: bc", this.actMessage);
    }

    @Test
    public void testDelegateError() {
        this.baseLogger.setLevel(Level.ERROR);
        this.logger.delegateError("a", "b", "c");
        assertEquals(Level.ERROR, this.actLevel);
        assertEquals("DELEGATE a: bc", this.actMessage);
    }

    @Test
    public void testDelegateFatal() {
        this.baseLogger.setLevel(Level.FATAL);
        this.logger.delegateFatal("a", "b", "c");
        assertEquals(Level.FATAL, this.actLevel);
        assertEquals("DELEGATE a: bc", this.actMessage);
    }

    @Test
    public void testDelegate() {
        this.logger.delegate("a", "b");
    }

    @Test
    public void testEndWarn() {
        this.baseLogger.setLevel(Level.WARN);
        this.logger.endWarn("a", "b");
        assertEquals(Level.WARN, this.actLevel);
        assertEquals("END a: b", this.actMessage);
    }

    @Test
    public void testEndDebug() {
        this.baseLogger.setLevel(Level.DEBUG);
        this.logger.endDebug("a", "b");
        assertEquals(Level.DEBUG, this.actLevel);
        assertEquals("END a: b", this.actMessage);
    }

    @Test
    public void testEndInfo() {
        this.baseLogger.setLevel(Level.INFO);
        this.logger.endInfo("a", "b");
        assertEquals(Level.INFO, this.actLevel);
        assertEquals("END a: b", this.actMessage);
    }

    @Test
    public void testEndError() {
        this.baseLogger.setLevel(Level.ERROR);
        this.logger.endError("a", "b");
        assertEquals(Level.ERROR, this.actLevel);
        assertEquals("END a: b", this.actMessage);
    }

    @Test
    public void testEndFatal() {
        this.baseLogger.setLevel(Level.FATAL);
        this.logger.endFatal("a", "b");
        assertEquals(Level.FATAL, this.actLevel);
        assertEquals("END a: b", this.actMessage);
    }

    @Test
    public void testEnd() {
        this.logger.end("a", "b");
    }

    @Test
    public void testWarnVarArgsToParamString() {
        this.baseLogger.setLevel(Level.WARN);
        assertNotNull(this.logger.warnVarArgsToParamString("a", "b"));
        this.baseLogger.setLevel(Level.FATAL);
        assertNull(this.logger.warnVarArgsToParamString("a", "b"));
    }

    @Test
    public void testDebugVarArgsToParamString() {
        this.baseLogger.setLevel(Level.DEBUG);
        assertNotNull(this.logger.debugVarArgsToParamString("a", "b"));
        this.baseLogger.setLevel(Level.FATAL);
        assertNull(this.logger.debugVarArgsToParamString("a", "b"));
    }

    @Test
    public void testInfoVarArgsToParamString() {
        this.baseLogger.setLevel(Level.INFO);
        assertNotNull(this.logger.infoVarArgsToParamString("a", "b"));
        this.baseLogger.setLevel(Level.FATAL);
        assertNull(this.logger.infoVarArgsToParamString("a", "b"));
    }

    @Test
    public void testErrorVarArgsToParamString() {
        this.baseLogger.setLevel(Level.ERROR);
        assertNotNull(this.logger.errorVarArgsToParamString("a", "b"));
        this.baseLogger.setLevel(Level.FATAL);
        assertNull(this.logger.errorVarArgsToParamString("a", "b"));
    }

    @Test
    public void testFatalVarArgsToParamString() {
        this.baseLogger.setLevel(Level.FATAL);
        assertNotNull(this.logger.fatalVarArgsToParamString("a", "b"));
        this.baseLogger.setLevel(Level.OFF);
        assertNull(this.logger.fatalVarArgsToParamString("a", "b"));
    }

    @Test
    public void testGetLogger() {
        assertNotNull(this.baseLogger);
    }

    @Test
    public void shouldLogsATrace() {
        this.baseLogger.setLevel(Level.TRACE);
        this.logger.trace("a");
        assertEquals("a", this.actMessage);
    }

    @Test
    public void shouldLogsATraceException() {
        this.baseLogger.setLevel(Level.TRACE);
        this.logger.trace(new NullPointerException(), "a");
        assertNotNull(this.actMessage);
    }

    class TestAppender implements Appender {

        @Override
        public void addFilter(Filter arg0) {
        }

        @Override
        public void clearFilters() {
        }

        @Override
        public void close() {
        }

        @Override
        public void doAppend(LoggingEvent loggingEvent) {
            actLevel = loggingEvent.getLevel();
            actMessage = loggingEvent.getRenderedMessage();
        }

        @Override
        public ErrorHandler getErrorHandler() {
            return new ErrorHandler() {

                @Override
                public void activateOptions() {
                }

                @Override
                public void setLogger(org.apache.log4j.Logger arg0) {
                }

                @Override
                public void setBackupAppender(Appender arg0) {
                }

                @Override
                public void setAppender(Appender arg0) {
                }

                @Override
                public void error(String arg0, Exception arg1, int arg2, LoggingEvent arg3) {
                }

                @Override
                public void error(String arg0, Exception arg1, int arg2) {
                }

                @Override
                public void error(String arg0) {
                }
            };
        }

        @Override
        public Filter getFilter() {
            return null;
        }

        @Override
        public Layout getLayout() {
            return new Layout() {

                @Override
                public boolean ignoresThrowable() {
                    return false;
                }

                @Override
                public String format(LoggingEvent loggingEvent) {
                    return loggingEvent.getRenderedMessage();
                }

                @Override
                public void activateOptions() {
                }
            };
        }

        @Override
        public String getName() {
            return "TestAppender";
        }

        @Override
        public boolean requiresLayout() {
            return false;
        }

        @Override
        public void setErrorHandler(ErrorHandler arg0) {
        }

        @Override
        public void setLayout(Layout arg0) {
        }

        @Override
        public void setName(String arg0) {
        }

    }


}
