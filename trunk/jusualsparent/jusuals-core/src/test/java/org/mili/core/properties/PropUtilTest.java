/*
 * PropUtilTest.java
 *
 * 03.02.2011
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
package org.mili.core.properties;


import java.io.*;
import java.util.*;

import org.easymock.*;
import org.junit.*;
import org.mili.core.io.*;
import org.mili.core.properties.PropUtil.*;
import org.mili.test.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class PropUtilTest {
    private File file = new File(TestUtils.TMP_FOLDER, "abbas.properties");
    private Creator creatorWithException = EasyMock.createMock(Creator.class);
    private Creator creatorWithNull = EasyMock.createMock(Creator.class);
    private Closer closerWithException = EasyMock.createMock(Closer.class);
    private Availabler availablerWithException = EasyMock.createMock(Availabler.class);
    private Availabler availablerWithZero = EasyMock.createMock(Availabler.class);

    @Before
    public void setUp() throws Exception {
        Properties p = new Properties();
        p.setProperty("a", "4711");
        OutputStream os = new FileOutputStream(this.file);
        p.store(os, "test props.");
        os.close();
    }

    @After
    public void after() throws Exception {
        PropUtil.setCreator(new PropUtil.CreatorImpl());
        PropUtil.setCloser(new PropUtil.CloserImpl());
        PropUtil.setAvailabler(new PropUtil.AvailablerImpl());
        EasyMock.reset(this.creatorWithException, this.creatorWithNull,
                this.closerWithException, this.availablerWithException,
                this.availablerWithZero);
    }

    @Test
    public void shouldCreates() {
        new PropUtil();
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailReadPropertiesBecauseNullFile() {
        PropUtil.readProperties((InputStream) null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFailReadPropertiesBecauseFileNotExisted() {
        PropUtil.readProperties(new File("bundy"));
    }

    @Test
    public void shouldReadPropertiesFile() {
        Properties p = PropUtil.readProperties(this.file);
        assertEquals("4711", p.get("a"));
    }

    @Test
    public void shouldReadProperties() throws Exception {
        Properties p = PropUtil.readProperties(FileUtil.getInputStream(this.file
                .getAbsolutePath()));
        assertEquals("4711", p.get("a"));
    }

    @Test(expected=IllegalStateException.class)
    public void failGetPropertyFromFileBecauseExceptionWhileGettingInputStream()
            throws Exception {
        EasyMock.expect(this.creatorWithException.create(this.file)).andThrow(
                new IOException());
        PropUtil.setCreator(this.creatorWithException);
        EasyMock.replay(this.creatorWithException);
        PropUtil.readProperties(this.file);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetPropertyFromFileBecauseNullInputStream() throws Exception {
        EasyMock.expect(this.creatorWithNull.create(this.file)).andReturn(null);
        PropUtil.setCreator(this.creatorWithNull);
        EasyMock.replay(this.creatorWithNull);
        PropUtil.readProperties(this.file);
    }

    @Test(expected=IllegalStateException.class)
    public void failGetPropertyFromFileBecauseExceptionWhileClosingInputStream()
            throws Exception {
        this.closerWithException.close((FileInputStream) EasyMock.anyObject());
        EasyMock.expectLastCall().andThrow(new IOException());
        PropUtil.setCloser(this.closerWithException);
        EasyMock.replay(this.closerWithException);
        PropUtil.readProperties(this.file);
    }

    @Test(expected=IllegalStateException.class)
    public void failReadPropertyFromInputStreamBecauseExceptionWhileAvailable()
            throws Exception {
        this.availablerWithException.available((FileInputStream) EasyMock.anyObject());
        EasyMock.expectLastCall().andThrow(new IOException());
        PropUtil.setAvailabler(this.availablerWithException);
        EasyMock.replay(this.availablerWithException);
        PropUtil.readProperties(new FileInputStream(this.file));
    }

    @Test(expected=IllegalArgumentException.class)
    public void failReadPropertyFromInputStreamBecauseAvailableIsZero() throws Exception {
        EasyMock.expect(this.availablerWithZero.available((FileInputStream) EasyMock
                    .anyObject())).andReturn(0);
        PropUtil.setAvailabler(this.availablerWithZero);
        EasyMock.replay(this.availablerWithZero);
        PropUtil.readProperties(new FileInputStream(this.file));
    }

}
