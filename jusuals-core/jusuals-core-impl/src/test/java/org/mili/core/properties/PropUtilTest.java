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
    private Properties props = null;
    private Foo foo = new Foo();

    @Before
    public void setUp() throws Exception {
        Properties p = new Properties();
        p.setProperty("a", "4711");
        OutputStream os = new FileOutputStream(this.file);
        p.store(os, "test props.");
        os.close();
        PropUtil.setSystem("org.mili.core.properties.Foo.charmax", String.valueOf(Character
                .MAX_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.charmin", String.valueOf(Character
                .MIN_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.bytemax", String.valueOf(Byte
                .MAX_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.bytemin", String.valueOf(Byte
                .MIN_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.shortmax", String.valueOf(Short
                .MAX_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.shortmin", String.valueOf(Short
                .MIN_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.intmax", String.valueOf(Integer
                .MAX_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.intmin", String.valueOf(Integer
                .MIN_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.longmax", String.valueOf(Long
                .MAX_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.longmin", String.valueOf(Long
                .MIN_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.floatmax", String.valueOf(Float
                .MAX_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.floatmin", String.valueOf(Float
                .MIN_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.doublemax", String.valueOf(Double
                .MAX_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.doublemin", String.valueOf(Double
                .MIN_VALUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.booleantrue", String.valueOf(Boolean
                .TRUE));
        PropUtil.setSystem("org.mili.core.properties.Foo.booleanfalse", String.valueOf(Boolean
                .FALSE));
        PropUtil.setSystem("org.mili.core.properties.Foo.string", "abc");
        props = new Properties();
        props.putAll(System.getProperties());
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

    @Test
    public void testChangeSystemProperties() {
        Properties p = System.getProperties();
        p.setProperty("abbas", "1");
        assertEquals("1", PropUtil.getSystem("abbas"));
        PropUtil.changeSystemProperties(new Properties());
        assertNull(PropUtil.getSystem("abbas"));
        System.clearProperty("abbas");
    }

    @Test
    public void testGetSystemRequiredObjectString() {
        try {
            PropUtil.getSystemRequired(new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("abc", PropUtil.getSystemRequired(new Foo(), "string"));
    }

    @Test
    public void testGetSystemRequiredClassOfQString() {
        try {
            PropUtil.getSystemRequired(Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("abc", PropUtil.getSystemRequired(Foo.class, "string"));
    }

    @Test
    public void testGetSystemRequiredString() {
        try {
            PropUtil.getSystemRequired("org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("abc", PropUtil.getSystemRequired("org.mili.core.properties.Foo.string"));
    }

    @Test
    public void testGetSystemRequiredStringClassOfT() {
        try {
            PropUtil.getSystemRequired("org.mili.core.properties.Foo.abbas", String.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(String.class, PropUtil.getSystemRequired("org.mili.core.properties.Foo.string",
                String.class).getClass());
        assertEquals("abc", PropUtil.getSystemRequired("org.mili.core.properties.Foo.string",
                String.class));
    }

    @Test
    public void testGetSystemRequiredByteObjectString() {
        try {
            PropUtil.getSystemRequiredByte(new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Byte.MAX_VALUE, PropUtil.getSystemRequiredByte(new Foo(), "bytemax"));
        assertEquals(Byte.MIN_VALUE, PropUtil.getSystemRequiredByte(new Foo(), "bytemin"));
    }

    @Test
    public void testGetSystemRequiredByteClassOfQString() {
        try {
            PropUtil.getSystemRequiredByte(Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Byte.MAX_VALUE, PropUtil.getSystemRequiredByte(Foo.class, "bytemax"));
        assertEquals(Byte.MIN_VALUE, PropUtil.getSystemRequiredByte(Foo.class, "bytemin"));
    }

    @Test
    public void testGetSystemRequiredByteString() {
        try {
            PropUtil.getSystemRequiredByte("org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Byte.MAX_VALUE, PropUtil.getSystemRequiredByte("org.mili.core.properties.Foo.bytemax"));
        assertEquals(Byte.MIN_VALUE, PropUtil.getSystemRequiredByte("org.mili.core.properties.Foo.bytemin"));
    }

    @Test
    public void testGetSystemRequiredBooleanObjectString() {
        try {
            PropUtil.getSystemRequiredBoolean(new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Boolean.TRUE, PropUtil.getSystemRequiredBoolean(new Foo(), "booleantrue"));
        assertEquals(Boolean.FALSE, PropUtil.getSystemRequiredBoolean(new Foo(), "booleanfalse"));
    }

    @Test
    public void testGetSystemRequiredBooleanClassOfQString() {
        try {
            PropUtil.getSystemRequiredBoolean(Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Boolean.TRUE, PropUtil.getSystemRequiredBoolean(Foo.class, "booleantrue"));
        assertEquals(Boolean.FALSE, PropUtil.getSystemRequiredBoolean(Foo.class, "booleanfalse"));
    }

    @Test
    public void testGetSystemRequiredBooleanString() {
        try {
            PropUtil.getSystemRequiredBoolean("org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Boolean.TRUE, PropUtil.getSystemRequiredBoolean("org.mili.core.properties.Foo.booleantrue"));
        assertEquals(Boolean.FALSE, PropUtil.getSystemRequiredBoolean("org.mili.core.properties.Foo.booleanfalse"));
    }

    @Test
    public void testGetSystemRequiredShortObjectString() {
        try {
            PropUtil.getSystemRequiredShort(new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Short.MAX_VALUE, PropUtil.getSystemRequiredShort(new Foo(), "shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getSystemRequiredShort(new Foo(), "shortmin"));
    }

    @Test
    public void testGetSystemRequiredShortClassOfQString() {
        try {
            PropUtil.getSystemRequiredShort(Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Short.MAX_VALUE, PropUtil.getSystemRequiredShort(Foo.class, "shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getSystemRequiredShort(Foo.class, "shortmin"));
    }

    @Test
    public void testGetSystemRequiredShortString() {
        try {
            PropUtil.getSystemRequiredShort("org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Short.MAX_VALUE, PropUtil.getSystemRequiredShort("org.mili.core.properties.Foo.shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getSystemRequiredShort("org.mili.core.properties.Foo.shortmin"));
    }

    @Test
    public void testGetSystemRequiredIntegerObjectString() {
        try {
            PropUtil.getSystemRequiredInteger(new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Integer.MAX_VALUE, PropUtil.getSystemRequiredInteger(new Foo(), "intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getSystemRequiredInteger(new Foo(), "intmin"));
    }

    @Test
    public void testGetSystemRequiredIntegerClassOfQString() {
        try {
            PropUtil.getSystemRequiredInteger(Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Integer.MAX_VALUE, PropUtil.getSystemRequiredInteger(Foo.class, "intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getSystemRequiredInteger(Foo.class, "intmin"));
    }

    @Test
    public void testGetSystemRequiredIntegerString() {
        try {
            PropUtil.getSystemRequiredInteger("org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Integer.MAX_VALUE, PropUtil.getSystemRequiredInteger("org.mili.core.properties.Foo.intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getSystemRequiredInteger("org.mili.core.properties.Foo.intmin"));
    }

    @Test
    public void testGetSystemRequiredCharObjectString() {
        try {
            PropUtil.getSystemRequiredShort(new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Character.MAX_VALUE, PropUtil.getSystemRequiredChar(new Foo(), "charmax"));
        assertEquals(Character.MIN_VALUE, PropUtil.getSystemRequiredChar(new Foo(), "charmin"));
    }

    @Test
    public void testGetSystemRequiredCharClassOfQString() {
        try {
            PropUtil.getSystemRequiredChar(Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Character.MAX_VALUE, PropUtil.getSystemRequiredChar(Foo.class, "charmax"));
        assertEquals(Character.MIN_VALUE, PropUtil.getSystemRequiredChar(Foo.class, "charmin"));
    }

    @Test
    public void testGetSystemRequiredCharString() {
        try {
            PropUtil.getSystemRequiredChar("org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Character.MAX_VALUE, PropUtil.getSystemRequiredChar("org.mili.core.properties.Foo.charmax"));
        assertEquals(Character.MIN_VALUE, PropUtil.getSystemRequiredChar("org.mili.core.properties.Foo.charmin"));
    }

    /**
     * Test method for {@link core.properties.PropUtil#getSystemRequiredFloat(java.lang.Object, java.lang.String)}.
     */
    @Test
    public void testGetSystemRequiredFloatObjectString() {
        try {
            PropUtil.getSystemRequiredFloat(new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Float.MAX_VALUE, PropUtil.getSystemRequiredFloat(new Foo(), "floatmax"),
                0.0F);
        assertEquals(Float.MIN_VALUE, PropUtil.getSystemRequiredFloat(new Foo(), "floatmin"),
                0.0F);
    }

    @Test
    public void testGetSystemRequiredFloatClassOfQString() {
        try {
            PropUtil.getSystemRequiredFloat(Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Float.MAX_VALUE, PropUtil.getSystemRequiredFloat(Foo.class, "floatmax"),
                0.0);
        assertEquals(Float.MIN_VALUE, PropUtil.getSystemRequiredFloat(Foo.class, "floatmin"),
                0.0);
    }

    @Test
    public void testGetSystemRequiredFloatString() {
        try {
            PropUtil.getSystemRequiredFloat("org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Float.MAX_VALUE, PropUtil.getSystemRequiredFloat("org.mili.core.properties.Foo.floatmax"),
                0.0);
        assertEquals(Float.MIN_VALUE, PropUtil.getSystemRequiredFloat("org.mili.core.properties.Foo.floatmin"),
                0.0);
    }

    @Test
    public void testGetSystemRequiredLongObjectString() {
        try {
            PropUtil.getSystemRequiredLong(new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Long.MAX_VALUE, PropUtil.getSystemRequiredLong(new Foo(), "longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getSystemRequiredLong(new Foo(), "longmin"));
    }

    @Test
    public void testGetSystemRequiredLongClassOfQString() {
        try {
            PropUtil.getSystemRequiredLong(Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Long.MAX_VALUE, PropUtil.getSystemRequiredLong(Foo.class, "longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getSystemRequiredLong(Foo.class, "longmin"));
    }

    @Test
    public void testGetSystemRequiredLongString() {
        try {
            PropUtil.getSystemRequiredLong("org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Long.MAX_VALUE, PropUtil.getSystemRequiredLong("org.mili.core.properties.Foo.longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getSystemRequiredLong("org.mili.core.properties.Foo.longmin"));
    }

    @Test
    public void testGetSystemRequiredDoubleObjectString() {
        try {
            PropUtil.getSystemRequiredDouble(new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Double.MAX_VALUE, PropUtil.getSystemRequiredDouble(new Foo(),
                "doublemax"), 0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getSystemRequiredDouble(new Foo(),
                "doublemin"), 0.0);
    }

    @Test
    public void testGetSystemRequiredDoubleClassOfQString() {
        try {
            PropUtil.getSystemRequiredDouble(Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Double.MAX_VALUE, PropUtil.getSystemRequiredDouble(Foo.class, "doublemax"),
                0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getSystemRequiredDouble(Foo.class, "doublemin"),
                0.0);
    }

    @Test
    public void testGetSystemRequiredDoubleString() {
        try {
            PropUtil.getSystemRequiredDouble("org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Double.MAX_VALUE, PropUtil.getSystemRequiredDouble("org.mili.core.properties.Foo.doublemax"),
                0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getSystemRequiredDouble("org.mili.core.properties.Foo.doublemin"),
                0.0);
    }

    @Test
    public void testGetSystemRequiredParameterMapOfStringQString() {
        Map<String, Object> m = new Hashtable<String, Object>();
        m.put("string", "abc");
        try {
            PropUtil.getSystemRequiredParameter(null, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getSystemRequiredParameter(new Hashtable<String, Object>(), null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getSystemRequiredParameter(m, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("abc", PropUtil.getSystemRequiredParameter(m, "string"));
    }

    @Test
    public void testGetSystemRequiredParameterMapOfStringQStringClassOfT() {
        Map<String, Object> m = new Hashtable<String, Object>();
        m.put("double", 47.11);
        try {
            PropUtil.getSystemRequiredParameter(null, "double", Double.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getSystemRequiredParameter(m, null, Double.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getSystemRequiredParameter(m, "", Double.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getSystemRequiredParameter(m, "double", null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getSystemRequiredParameter(m, "abbas", Double.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(47.11, PropUtil.getSystemRequiredParameter(m, "double", Double.class),
                0.0);
    }

    @Test
    public void testGetSystemObjectString() {
        assertNull(PropUtil.getSystem(new Foo(), "abbas"));
        assertEquals("abc", PropUtil.getSystem(new Foo(), "string"));
    }

    @Test
    public void testGetSystemObjectStringString() {
        assertEquals("xyz", PropUtil.getSystem(new Foo(), "abbas", "xyz"));
        assertEquals("abc", PropUtil.getSystem(new Foo(), "string", "xyz"));
    }

    @Test
    public void testGetSystemClassOfQString() {
        assertNull(PropUtil.getSystem(Foo.class, "abbas"));
        assertEquals("abc", PropUtil.getSystem(Foo.class, "string"));
    }

    @Test
    public void testGetSystemClassOfQStringString() {
        assertEquals("xyz", PropUtil.getSystem(Foo.class, "abbas", "xyz"));
        assertEquals("abc", PropUtil.getSystem(Foo.class, "string", "xyz"));
    }

    @Test
    public void testGetSystemString() {
        assertNull(PropUtil.getSystem("org.mili.core.properties.Foo.abbas"));
        assertEquals("abc", PropUtil.getSystem("org.mili.core.properties.Foo.string"));
    }

    @Test
    public void testGetSystemStringString() {
        assertEquals("xyz", PropUtil.getSystem("org.mili.core.properties.Foo.abbas", "xyz"));
        assertEquals("abc", PropUtil.getSystem("org.mili.core.properties.Foo.string", "xyz"));
    }

    @Test
    public void testGetSystemStringClassOfTT() {
        assertEquals("xyz", PropUtil.getSystem("org.mili.core.properties.Foo.abbas", String.class, "xyz"));
        assertEquals("abc", PropUtil.getSystem("org.mili.core.properties.Foo.string", String.class, "xyz"));
    }

    @Test
    public void testGetSystemStringClassOfT() {
        assertNull(PropUtil.getSystem("org.mili.core.properties.Foo.abbas", String.class));
        assertEquals("abc", PropUtil.getSystem("org.mili.core.properties.Foo.string", String.class));
    }

    @Test
    public void testGetSystemByteObjectString() {
        assertEquals(0, PropUtil.getSystemByte(new Foo(), "abbas"));
        assertEquals(Byte.MAX_VALUE, PropUtil.getSystemByte(new Foo(), "bytemax"), 0.0);
        assertEquals(Byte.MIN_VALUE, PropUtil.getSystemByte(new Foo(), "bytemin"), 0.0);
    }

    @Test
    public void testGetSystemByteClassOfQString() {
        assertEquals(0, PropUtil.getSystemByte(Foo.class, "abbas"));
        assertEquals(Byte.MAX_VALUE, PropUtil.getSystemByte(Foo.class, "bytemax"), 0.0);
        assertEquals(Byte.MIN_VALUE, PropUtil.getSystemByte(Foo.class, "bytemin"), 0.0);
    }

    @Test
    public void testGetSystemByteString() {
        assertEquals(0, PropUtil.getSystemByte("org.mili.core.properties.Foo.abbas"));
        assertEquals(Byte.MAX_VALUE, PropUtil.getSystemByte("org.mili.core.properties.Foo.bytemax"), 0.0);
        assertEquals(Byte.MIN_VALUE, PropUtil.getSystemByte("org.mili.core.properties.Foo.bytemin"), 0.0);
    }

    @Test
    public void testGetSystemBooleanObjectString() {
        assertEquals(false, PropUtil.getSystemBoolean(new Foo(), "abbas"));
        assertEquals(Boolean.TRUE, PropUtil.getSystemBoolean(new Foo(), "booleantrue"));
        assertEquals(Boolean.FALSE, PropUtil.getSystemBoolean(new Foo(), "booleanfalse"));
    }

    @Test
    public void testGetSystemBooleanClassOfQString() {
        assertEquals(false, PropUtil.getSystemBoolean(Foo.class, "abbas"));
        assertEquals(Boolean.TRUE, PropUtil.getSystemBoolean(Foo.class, "booleantrue"));
        assertEquals(Boolean.FALSE, PropUtil.getSystemBoolean(Foo.class, "booleanfalse"));
    }

    @Test
    public void testGetSystemBooleanString() {
        assertEquals(false, PropUtil.getSystemBoolean("org.mili.core.properties.Foo.abbas"));
        assertEquals(Boolean.TRUE, PropUtil.getSystemBoolean("org.mili.core.properties.Foo.booleantrue"));
        assertEquals(Boolean.FALSE, PropUtil.getSystemBoolean("org.mili.core.properties.Foo.booleanfalse"));
    }

    @Test
    public void testGetSystemShortObjectString() {
        assertEquals(0, PropUtil.getSystemShort(new Foo(), "abbas"));
        assertEquals(Short.MAX_VALUE, PropUtil.getSystemShort(new Foo(), "shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getSystemShort(new Foo(), "shortmin"));
    }

    @Test
    public void testGetSystemShortClassOfQString() {
        assertEquals(0, PropUtil.getSystemShort(Foo.class, "abbas"));
        assertEquals(Short.MAX_VALUE, PropUtil.getSystemShort(Foo.class, "shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getSystemShort(Foo.class, "shortmin"));
    }

    @Test
    public void testGetSystemShortString() {
        assertEquals(0, PropUtil.getSystemShort("org.mili.core.properties.Foo.abbas"));
        assertEquals(Short.MAX_VALUE, PropUtil.getSystemShort("org.mili.core.properties.Foo.shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getSystemShort("org.mili.core.properties.Foo.shortmin"));
    }

    @Test
    public void testGetSystemIntegerObjectString() {
        assertEquals(0, PropUtil.getSystemShort(new Foo(), "abbas"));
        assertEquals(Integer.MAX_VALUE, PropUtil.getSystemInteger(new Foo(), "intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getSystemInteger(new Foo(), "intmin"));
    }

    @Test
    public void testGetSystemIntegerClassOfQString() {
        assertEquals(0, PropUtil.getSystemInteger(Foo.class, "abbas"));
        assertEquals(Integer.MAX_VALUE, PropUtil.getSystemInteger(Foo.class, "intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getSystemInteger(Foo.class, "intmin"));
    }

    @Test
    public void testGetSystemIntegerString() {
        assertEquals(0, PropUtil.getSystemInteger("org.mili.core.properties.Foo.abbas"));
        assertEquals(Integer.MAX_VALUE, PropUtil.getSystemInteger(
                "org.mili.core.properties.Foo.intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getSystemInteger(
                "org.mili.core.properties.Foo.intmin"));
    }

    @Test
    public void testSystemIntegerObjectStringInt() {
        assertEquals(4711, PropUtil.getSystemInteger(foo, "abbas", 4711));
        assertEquals(Integer.MAX_VALUE, PropUtil.getSystemInteger(foo, "intmax", 4711));
        assertEquals(Integer.MIN_VALUE, PropUtil.getSystemInteger(foo, "intmin", 4711));
    }

    @Test
    public void testSystemIntegerClassStringInt() {
        assertEquals(4711, PropUtil.getSystemInteger(Foo.class, "abbas", 4711));
        assertEquals(Integer.MAX_VALUE, PropUtil.getSystemInteger(Foo.class, "intmax", 4711));
        assertEquals(Integer.MIN_VALUE, PropUtil.getSystemInteger(Foo.class, "intmin", 4711));
    }

    @Test
    public void testSystemIntegerStringInt() {
        assertEquals(4711, PropUtil.getSystemInteger("org.mili.core.properties.Foo.abbas",
                4711));
        assertEquals(Integer.MAX_VALUE, PropUtil.getSystemInteger(
                "org.mili.core.properties.Foo.intmax", 4711));
        assertEquals(Integer.MIN_VALUE, PropUtil.getSystemInteger(
                "org.mili.core.properties.Foo.intmin"), 4711);
    }

    @Test
    public void testGetSystemCharObjectString() {
        assertEquals(' ', PropUtil.getSystemChar(new Foo(), "abbas"));
        assertEquals(Character.MAX_VALUE, PropUtil.getSystemChar(new Foo(), "charmax"));
        assertEquals(Character.MIN_VALUE, PropUtil.getSystemChar(new Foo(), "charmin"));
    }

    @Test
    public void testGetSystemCharClassOfQString() {
        assertEquals(' ', PropUtil.getSystemChar(Foo.class, "abbas"));
        assertEquals(Character.MAX_VALUE, PropUtil.getSystemChar(Foo.class, "charmax"));
        assertEquals(Character.MIN_VALUE, PropUtil.getSystemChar(Foo.class, "charmin"));
    }

    @Test
    public void testGetSystemCharString() {
        assertEquals(' ', PropUtil.getSystemChar("org.mili.core.properties.Foo.abbas"));
        assertEquals(Character.MAX_VALUE, PropUtil.getSystemChar("org.mili.core.properties.Foo.charmax"));
        assertEquals(Character.MIN_VALUE, PropUtil.getSystemChar("org.mili.core.properties.Foo.charmin"));
    }

    @Test
    public void testGetSystemFloatObjectString() {
        assertEquals(0.0F, PropUtil.getSystemFloat(new Foo(), "abbas"), 0.0F);
        assertEquals(Float.MAX_VALUE, PropUtil.getSystemFloat(new Foo(), "floatmax"), 0.0F);
        assertEquals(Float.MIN_VALUE, PropUtil.getSystemFloat(new Foo(), "floatmin"), 0.0F);
    }

    @Test
    public void testGetSystemFloatClassOfQString() {
        assertEquals(0.0F, PropUtil.getSystemFloat(Foo.class, "abbas"), 0.0F);
        assertEquals(Float.MAX_VALUE, PropUtil.getSystemFloat(Foo.class, "floatmax"), 0.0F);
        assertEquals(Float.MIN_VALUE, PropUtil.getSystemFloat(Foo.class, "floatmin"), 0.0F);
    }

    @Test
    public void testGetSystemFloatString() {
        assertEquals(0.0F, PropUtil.getSystemFloat("org.mili.core.properties.Foo.abbas"), 0.0F);
        assertEquals(Float.MAX_VALUE, PropUtil.getSystemFloat("org.mili.core.properties.Foo.floatmax"), 0.0F);
        assertEquals(Float.MIN_VALUE, PropUtil.getSystemFloat("org.mili.core.properties.Foo.floatmin"), 0.0F);
    }

    @Test
    public void testGetSystemLongObjectString() {
        assertEquals(0L, PropUtil.getSystemLong(new Foo(), "abbas"));
        assertEquals(Long.MAX_VALUE, PropUtil.getSystemLong(new Foo(), "longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getSystemLong(new Foo(), "longmin"));
    }

    @Test
    public void testGetSystemLongClassOfQString() {
        assertEquals(0L, PropUtil.getSystemLong(Foo.class, "abbas"));
        assertEquals(Long.MAX_VALUE, PropUtil.getSystemLong(Foo.class, "longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getSystemLong(Foo.class, "longmin"));
    }

    @Test
    public void testGetSystemLongString() {
        assertEquals(0L, PropUtil.getSystemLong("org.mili.core.properties.Foo.abbas"));
        assertEquals(Long.MAX_VALUE, PropUtil.getSystemLong("org.mili.core.properties.Foo.longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getSystemLong("org.mili.core.properties.Foo.longmin"));
    }

    @Test
    public void testGetSystemLongObjectStringLong() {
        assertEquals(1L, PropUtil.getSystemLong(new Foo(), "abbas", 1L));
        assertEquals(Long.MAX_VALUE, PropUtil.getSystemLong(new Foo(), "longmax", 1L));
        assertEquals(Long.MIN_VALUE, PropUtil.getSystemLong(new Foo(), "longmin", 1L));
    }

    @Test
    public void testGetSystemLongClassOfQStringLong() {
        assertEquals(1L, PropUtil.getSystemLong(Foo.class, "abbas", 1L));
        assertEquals(Long.MAX_VALUE, PropUtil.getSystemLong(Foo.class, "longmax", 1L));
        assertEquals(Long.MIN_VALUE, PropUtil.getSystemLong(Foo.class, "longmin", 1L));
    }

    @Test
    public void testGetSystemLongStringLong() {
        assertEquals(1L, PropUtil.getSystemLong("org.mili.core.properties.Foo.abbas", 1L));
        assertEquals(Long.MAX_VALUE, PropUtil.getSystemLong("org.mili.core.properties.Foo.longmax", 1L));
        assertEquals(Long.MIN_VALUE, PropUtil.getSystemLong("org.mili.core.properties.Foo.longmin", 1L));
    }

    @Test
    public void testGetSystemDoubleObjectString() {
        assertEquals(0.0, PropUtil.getSystemLong(new Foo(), "abbas"), 0.0);
        assertEquals(Double.MAX_VALUE, PropUtil.getSystemDouble(new Foo(), "doublemax"), 0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getSystemDouble(new Foo(), "doublemin"), 0.0);
    }

    @Test
    public void testGetSystemDoubleClassOfQString() {
        assertEquals(0.0, PropUtil.getSystemDouble(Foo.class, "abbas"), 0.0);
        assertEquals(Double.MAX_VALUE, PropUtil.getSystemDouble(Foo.class, "doublemax"), 0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getSystemDouble(Foo.class, "doublemin"), 0.0);
    }

    @Test
    public void testGetSystemDoubleString() {
        assertEquals(0.0, PropUtil.getSystemDouble("org.mili.core.properties.Foo.abbas"), 0.0);
        assertEquals(Double.MAX_VALUE, PropUtil.getSystemDouble("org.mili.core.properties.Foo.doublemax"), 0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getSystemDouble("org.mili.core.properties.Foo.doublemin"), 0.0);
    }

    @Test
    public void testGetSystemParameterMapOfStringQString() {
        Map<String, Object> m = new Hashtable<String, Object>();
        m.put("string2", "xyz");
        assertNull(PropUtil.getSystemParameter(m, "abbas"));
        assertEquals("xyz", PropUtil.getSystemParameter(m, "string2"));
        assertEquals("abc", PropUtil.getSystemParameter(m, "org.mili.core.properties.Foo.string"));
    }

    @Test
    public void testGetSystemParameterMapOfStringQStringClassOfT() {
        Map<String, Object> m = new Hashtable<String, Object>();
        m.put("double", 47.11);
        assertNull(PropUtil.getSystemParameter(m, "abbas"));
        assertEquals(47.11, PropUtil.getSystemParameter(m, "double", Double.class), 0.0);
        assertEquals(Double.MAX_VALUE, PropUtil.getSystemParameter(m,
                "org.mili.core.properties.Foo.doublemax", Double.class), 0.0);
    }

    @Test
    public void testGetRequiredPropertiesObjectString() {
        try {
            PropUtil.getRequired(props, new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("abc", PropUtil.getRequired(props, new Foo(), "string"));
    }

    @Test
    public void testGetRequiredPropertiesClassOfQString() {
        try {
            PropUtil.getRequired(props, Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("abc", PropUtil.getRequired(props, Foo.class, "string"));
    }

    @Test
    public void testGetRequiredBytePropertiesObjectString() {
        try {
            PropUtil.getRequiredByte(props, new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Byte.MAX_VALUE, PropUtil.getRequiredByte(props, new Foo(), "bytemax"));
        assertEquals(Byte.MIN_VALUE, PropUtil.getRequiredByte(props, new Foo(), "bytemin"));
    }

    @Test
    public void testGetRequiredBytePropertiesClassOfQString() {
        try {
            PropUtil.getRequiredByte(props, Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Byte.MAX_VALUE, PropUtil.getRequiredByte(props, Foo.class, "bytemax"));
        assertEquals(Byte.MIN_VALUE, PropUtil.getRequiredByte(props, Foo.class, "bytemin"));
    }

    @Test
    public void testGetRequiredBytePropertiesString() {
        try {
            PropUtil.getRequiredByte(props, "org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Byte.MAX_VALUE, PropUtil.getRequiredByte(props, "org.mili.core.properties.Foo.bytemax"));
        assertEquals(Byte.MIN_VALUE, PropUtil.getRequiredByte(props, "org.mili.core.properties.Foo.bytemin"));
    }

    @Test
    public void testGetRequiredBooleanPropertiesObjectString() {
        try {
            PropUtil.getRequiredBoolean(props, new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Boolean.TRUE, PropUtil.getRequiredBoolean(props, new Foo(), "booleantrue"));
        assertEquals(Boolean.FALSE, PropUtil.getRequiredBoolean(props, new Foo(), "booleanfalse"));
    }

    @Test
    public void testGetRequiredBooleanPropertiesClassOfQString() {
        try {
            PropUtil.getRequiredBoolean(props, Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Boolean.TRUE, PropUtil.getRequiredBoolean(props, Foo.class, "booleantrue"));
        assertEquals(Boolean.FALSE, PropUtil.getRequiredBoolean(props, Foo.class, "booleanfalse"));
    }

    @Test
    public void testGetRequiredBooleanPropertiesString() {
        try {
            PropUtil.getRequiredBoolean(props, "org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Boolean.TRUE, PropUtil.getRequiredBoolean(props, "org.mili.core.properties.Foo.booleantrue"));
        assertEquals(Boolean.FALSE, PropUtil.getRequiredBoolean(props, "org.mili.core.properties.Foo.booleanfalse"));
    }

    @Test
    public void testGetRequiredShortPropertiesObjectString() {
        try {
            PropUtil.getRequiredShort(props, new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Short.MAX_VALUE, PropUtil.getRequiredShort(props, new Foo(), "shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getRequiredShort(props, new Foo(), "shortmin"));
    }

    @Test
    public void testGetRequiredShortPropertiesClassOfQString() {
        try {
            PropUtil.getRequiredShort(props, Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Short.MAX_VALUE, PropUtil.getRequiredShort(props, Foo.class, "shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getRequiredShort(props, Foo.class, "shortmin"));
    }

    @Test
    public void testGetRequiredShortPropertiesString() {
        try {
            PropUtil.getRequiredShort(props, "org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Short.MAX_VALUE, PropUtil.getRequiredShort(props, "org.mili.core.properties.Foo.shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getRequiredShort(props, "org.mili.core.properties.Foo.shortmin"));
    }

    @Test
    public void testGetRequiredIntegerPropertiesObjectString() {
        try {
            PropUtil.getRequiredInteger(props, new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Integer.MAX_VALUE, PropUtil.getRequiredInteger(props, new Foo(), "intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getRequiredInteger(props, new Foo(), "intmin"));
    }

    @Test
    public void testGetRequiredIntegerPropertiesClassOfQString() {
        try {
            PropUtil.getRequiredInteger(props, Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Integer.MAX_VALUE, PropUtil.getRequiredInteger(props, Foo.class, "intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getRequiredInteger(props, Foo.class, "intmin"));
    }

    @Test
    public void testGetRequiredIntegerPropertiesString() {
        try {
            PropUtil.getRequiredInteger(props, "org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Integer.MAX_VALUE, PropUtil.getRequiredInteger(props, "org.mili.core.properties.Foo.intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getRequiredInteger(props, "org.mili.core.properties.Foo.intmin"));
    }

    @Test
    public void testGetRequiredCharPropertiesObjectString() {
        try {
            PropUtil.getRequiredChar(props, new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Character.MAX_VALUE, PropUtil.getRequiredChar(props, new Foo(), "charmax"));
        assertEquals(Character.MIN_VALUE, PropUtil.getRequiredChar(props, new Foo(), "charmin"));
    }

    @Test
    public void testGetRequiredCharPropertiesClassOfQString() {
        try {
            PropUtil.getRequiredChar(props, Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Character.MAX_VALUE, PropUtil.getRequiredChar(props, Foo.class, "charmax"));
        assertEquals(Character.MIN_VALUE, PropUtil.getRequiredChar(props, Foo.class, "charmin"));
    }

    @Test
    public void testGetRequiredCharPropertiesString() {
        try {
            PropUtil.getRequiredChar(props, "org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Character.MAX_VALUE, PropUtil.getRequiredChar(props, "org.mili.core.properties.Foo.charmax"));
        assertEquals(Character.MIN_VALUE, PropUtil.getRequiredChar(props, "org.mili.core.properties.Foo.charmin"));
    }

    @Test
    public void testGetRequiredFloatPropertiesObjectString() {
        try {
            PropUtil.getRequiredFloat(props, new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Float.MAX_VALUE, PropUtil.getRequiredFloat(props, new Foo(), "floatmax"), 0.0F);
        assertEquals(Float.MIN_VALUE, PropUtil.getRequiredFloat(props, new Foo(), "floatmin"), 0.0F);
    }

    @Test
    public void testGetRequiredFloatPropertiesClassOfQString() {
        try {
            PropUtil.getRequiredFloat(props, Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Float.MAX_VALUE, PropUtil.getRequiredFloat(props, Foo.class, "floatmax"), 0.0F);
        assertEquals(Float.MIN_VALUE, PropUtil.getRequiredFloat(props, Foo.class, "floatmin"), 0.0F);
    }

    @Test
    public void testGetRequiredFloatPropertiesString() {
        try {
            PropUtil.getRequiredFloat(props, "org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Float.MAX_VALUE, PropUtil.getRequiredFloat(props, "org.mili.core.properties.Foo.floatmax"), 0.0F);
        assertEquals(Float.MIN_VALUE, PropUtil.getRequiredFloat(props, "org.mili.core.properties.Foo.floatmin"), 0.0F);
    }

    @Test
    public void testGetRequiredLongPropertiesObjectString() {
        try {
            PropUtil.getRequiredLong(props, new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Long.MAX_VALUE, PropUtil.getRequiredLong(props, new Foo(), "longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getRequiredLong(props, new Foo(), "longmin"));
    }

    @Test
    public void testGetRequiredLongPropertiesClassOfQString() {
        try {
            PropUtil.getRequiredLong(props, Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Long.MAX_VALUE, PropUtil.getRequiredLong(props, Foo.class, "longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getRequiredLong(props, Foo.class, "longmin"));
    }

    @Test
    public void testGetRequiredLongPropertiesString() {
        try {
            PropUtil.getRequiredLong(props, "org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Long.MAX_VALUE, PropUtil.getRequiredLong(props, "org.mili.core.properties.Foo.longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getRequiredLong(props, "org.mili.core.properties.Foo.longmin"));
    }

    @Test
    public void testGetRequiredDoublePropertiesObjectString() {
        try {
            PropUtil.getRequiredDouble(props, new Foo(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Double.MAX_VALUE, PropUtil.getRequiredDouble(props, new Foo(), "doublemax"), 0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getRequiredDouble(props, new Foo(), "doublemin"), 0.0);
    }

    @Test
    public void testGetRequiredDoublePropertiesClassOfQString() {
        try {
            PropUtil.getRequiredDouble(props, Foo.class, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Double.MAX_VALUE, PropUtil.getRequiredDouble(props, Foo.class, "doublemax"), 0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getRequiredDouble(props, Foo.class, "doublemin"), 0.0);
    }

    @Test
    public void testGetRequiredDoublePropertiesString() {
        try {
            PropUtil.getRequiredDouble(props, "org.mili.core.properties.Foo.abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(Double.MAX_VALUE, PropUtil.getRequiredDouble(props, "org.mili.core.properties.Foo.doublemax"), 0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getRequiredDouble(props, "org.mili.core.properties.Foo.doublemin"), 0.0);
    }

    @Test
    public void testRequiredParameterMapPropertiesString() {
        Map<String, Object> m = new Hashtable<String, Object>();
        m.put("string", "abc");
        try {
            PropUtil.getRequiredParameter(null, props, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(m, null, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(m, props, "");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(m, props, null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(new Hashtable<String, Object>(),
                    new Properties(), "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("abc", PropUtil.getRequiredParameter(m, props, "string"));
    }

    @Test
    public void testRequiredParameterMapStringClass() {
        Map<String, Object> m = new Hashtable<String, Object>();
        m.put("string", "abc");
        try {
            PropUtil.getRequiredParameter(null, "abbas", String.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(m , null, String.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(m , "", String.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(m , "abbas", null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(new Hashtable<String, Object>(), "abbas",
                    String.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("abc", PropUtil.getRequiredParameter(m, "string", String.class));
    }

    @Test
    public void testRequiredParameterMapPropertiesStringClass() {
        Map<String, Object> m = new Hashtable<String, Object>();
        m.put("string", "abc");
        try {
            PropUtil.getRequiredParameter(null, props, "abbas", String.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(m, null, "abbas", String.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(m, props, null, String.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(m, props, "", String.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(m, props, "abbas", null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequiredParameter(new Hashtable<String, Object>(), props, "abbas",
                    String.class);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("abc", PropUtil.getRequiredParameter(m, props, "string", String.class));
        assertEquals("abc", PropUtil.getRequiredParameter(m, props,
                "org.mili.core.properties.Foo.string", String.class));
    }

    @Test
    public void testGetRequiredPropertiesString() {
        try {
            PropUtil.getRequired(null, "abbas");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequired(props, "");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.getRequired(props, null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("abc", PropUtil.getRequired(props, "org.mili.core.properties.Foo.string"));
    }

    @Test
    public void testGetBytePropertiesObjectString() {
        assertEquals(0, PropUtil.getByte(props, new Foo(), "abbas"));
        assertEquals(Byte.MAX_VALUE, PropUtil.getByte(props, new Foo(), "bytemax"));
        assertEquals(Byte.MIN_VALUE, PropUtil.getByte(props, new Foo(), "bytemin"));
    }

    @Test
    public void testGetBytePropertiesClassOfQString() {
        assertEquals(0, PropUtil.getByte(props, Foo.class, "abbas"));
        assertEquals(Byte.MAX_VALUE, PropUtil.getByte(props, Foo.class, "bytemax"));
        assertEquals(Byte.MIN_VALUE, PropUtil.getByte(props, Foo.class, "bytemin"));
    }

    @Test
    public void testGetBytePropertiesString() {
        assertEquals(0, PropUtil.getByte(props, "org.mili.core.properties.Foo.abbas"));
        assertEquals(Byte.MAX_VALUE, PropUtil.getByte(props, "org.mili.core.properties.Foo.bytemax"));
        assertEquals(Byte.MIN_VALUE, PropUtil.getByte(props, "org.mili.core.properties.Foo.bytemin"));
    }

    @Test
    public void testGetBooleanPropertiesObjectString() {
        assertFalse(PropUtil.getBoolean(props, new Foo(), "abbas"));
        assertTrue(PropUtil.getBoolean(props, new Foo(), "booleantrue"));
        assertFalse(PropUtil.getBoolean(props, new Foo(), "booleanfalse"));
    }

    @Test
    public void testGetBooleanPropertiesClassOfQString() {
        assertFalse(PropUtil.getBoolean(props, Foo.class, "abbas"));
        assertTrue(PropUtil.getBoolean(props, Foo.class, "booleantrue"));
        assertFalse(PropUtil.getBoolean(props, Foo.class, "booleanfalse"));
    }

    @Test
    public void testGetBooleanPropertiesString() {
        assertFalse(PropUtil.getBoolean(props, "org.mili.core.properties.Foo.abbas"));
        assertTrue(PropUtil.getBoolean(props, "org.mili.core.properties.Foo.booleantrue"));
        assertFalse(PropUtil.getBoolean(props, "org.mili.core.properties.Foo.booleanfalse"));
    }

    @Test
    public void testGetShortPropertiesObjectString() {
        assertEquals(0, PropUtil.getShort(props, new Foo(), "abbas"));
        assertEquals(Short.MAX_VALUE, PropUtil.getShort(props, new Foo(), "shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getShort(props, new Foo(), "shortmin"));
    }

    @Test
    public void testGetShortPropertiesClassOfQString() {
        assertEquals(0, PropUtil.getShort(props, Foo.class, "abbas"));
        assertEquals(Short.MAX_VALUE, PropUtil.getShort(props, Foo.class, "shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getShort(props, Foo.class, "shortmin"));
    }

    @Test
    public void testGetShortPropertiesString() {
        assertEquals(0, PropUtil.getShort(props, "org.mili.core.properties.Foo.abbas"));
        assertEquals(Short.MAX_VALUE, PropUtil.getShort(props, "org.mili.core.properties.Foo.shortmax"));
        assertEquals(Short.MIN_VALUE, PropUtil.getShort(props, "org.mili.core.properties.Foo.shortmin"));
    }

    @Test
    public void testGetIntegerPropertiesObjectString() {
        assertEquals(0, PropUtil.getInteger(props, new Foo(), "abbas"));
        assertEquals(Integer.MAX_VALUE, PropUtil.getInteger(props, new Foo(), "intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getInteger(props, new Foo(), "intmin"));
    }

    @Test
    public void testGetIntegerPropertiesClassOfQString() {
        assertEquals(0, PropUtil.getInteger(props, Foo.class, "abbas"));
        assertEquals(Integer.MAX_VALUE, PropUtil.getInteger(props, Foo.class, "intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getInteger(props, Foo.class, "intmin"));
    }

    @Test
    public void testGetIntegerPropertiesString() {
        assertEquals(0, PropUtil.getInteger(props, "org.mili.core.properties.Foo.abbas"));
        assertEquals(Integer.MAX_VALUE, PropUtil.getInteger(props, "org.mili.core.properties.Foo.intmax"));
        assertEquals(Integer.MIN_VALUE, PropUtil.getInteger(props, "org.mili.core.properties.Foo.intmin"));
    }

    @Test
    public void testGetCharPropertiesObjectString() {
        assertEquals(' ', PropUtil.getChar(props, new Foo(), "abbas"));
        assertEquals('a', PropUtil.getChar(props, new Foo(), "string"));
    }

    @Test
    public void testGetCharPropertiesClassOfQString() {
        assertEquals(' ', PropUtil.getChar(props, Foo.class, "abbas"));
        assertEquals('a', PropUtil.getChar(props, Foo.class, "string"));
    }

    @Test
    public void testGetCharPropertiesString() {
        assertEquals(' ', PropUtil.getChar(props, "org.mili.core.properties.Foo.abbas"));
        assertEquals('a', PropUtil.getChar(props, "org.mili.core.properties.Foo.string"));
    }

    @Test
    public void testGetFloatPropertiesObjectString() {
        assertEquals(0.0F, PropUtil.getFloat(props, new Foo(), "abbas"), 0.0F);
        assertEquals(Float.MAX_VALUE, PropUtil.getFloat(props, new Foo(), "floatmax"), 0.0F);
        assertEquals(Float.MIN_VALUE, PropUtil.getFloat(props, new Foo(), "floatmin"), 0.0F);
    }

    @Test
    public void testGetFloatPropertiesClassOfQString() {
        assertEquals(0.0F, PropUtil.getFloat(props, Foo.class, "abbas"), 0.0F);
        assertEquals(Float.MAX_VALUE, PropUtil.getFloat(props, Foo.class, "floatmax"), 0.0F);
        assertEquals(Float.MIN_VALUE, PropUtil.getFloat(props, Foo.class, "floatmin"), 0.0F);
    }

    @Test
    public void testGetFloatPropertiesString() {
        assertEquals(0.0F, PropUtil.getFloat(props, "org.mili.core.properties.Foo.abbas"), 0.0F);
        assertEquals(Float.MAX_VALUE, PropUtil.getFloat(props, "org.mili.core.properties.Foo.floatmax"), 0.0F);
        assertEquals(Float.MIN_VALUE, PropUtil.getFloat(props, "org.mili.core.properties.Foo.floatmin"), 0.0F);
    }

    @Test
    public void testGetLongPropertiesObjectString() {
        assertEquals(0, PropUtil.getLong(props, new Foo(), "abbas"));
        assertEquals(Long.MAX_VALUE, PropUtil.getLong(props, new Foo(), "longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getLong(props, new Foo(), "longmin"));
    }

    @Test
    public void testGetLongPropertiesClassOfQString() {
        assertEquals(0, PropUtil.getLong(props, Foo.class, "abbas"));
        assertEquals(Long.MAX_VALUE, PropUtil.getLong(props, Foo.class, "longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getLong(props, Foo.class, "longmin"));
    }

    @Test
    public void testGetLongPropertiesString() {
        assertEquals(0, PropUtil.getLong(props, "org.mili.core.properties.Foo.abbas"));
        assertEquals(Long.MAX_VALUE, PropUtil.getLong(props, "org.mili.core.properties.Foo.longmax"));
        assertEquals(Long.MIN_VALUE, PropUtil.getLong(props, "org.mili.core.properties.Foo.longmin"));
    }

    @Test
    public void testGetDoublePropertiesObjectString() {
        assertEquals(0.0, PropUtil.getDouble(props, new Foo(), "abbas"), 0.0);
        assertEquals(Double.MAX_VALUE, PropUtil.getDouble(props, new Foo(), "doublemax"), 0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getDouble(props, new Foo(), "doublemin"), 0.0);
    }

    @Test
    public void testGetDoublePropertiesClassOfQString() {
        assertEquals(0.0, PropUtil.getDouble(props, Foo.class, "abbas"), 0.0);
        assertEquals(Double.MAX_VALUE, PropUtil.getDouble(props, Foo.class, "doublemax"), 0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getDouble(props, Foo.class, "doublemin"), 0.0);
    }

    @Test
    public void testGetDoublePropertiesString() {
        assertEquals(0.0, PropUtil.getDouble(props, "org.mili.core.properties.Foo.abbas"), 0.0);
        assertEquals(Double.MAX_VALUE, PropUtil.getDouble(props, "org.mili.core.properties.Foo.doublemax"), 0.0);
        assertEquals(Double.MIN_VALUE, PropUtil.getDouble(props, "org.mili.core.properties.Foo.doublemin"), 0.0);
    }

    @Test
    public void testGetPropertiesString() {
        assertEquals("abc", PropUtil.get(props, "org.mili.core.properties.Foo.string"));
        assertNull(PropUtil.get(props, "lala"));
    }

    /**
     * Test method for {@link core.properties.PropUtil#get(java.util.Properties, java.lang.String, java.lang.Class)}.
     */
    @Test
    @Ignore
    public void testGetPropertiesStringClassOfT() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#get(java.util.Properties, java.lang.Object, java.lang.String)}.
     */
    @Test
    @Ignore
    public void testGetPropertiesObjectString() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#get(java.util.Properties, java.lang.Class, java.lang.String)}.
     */
    @Test
    @Ignore
    public void testGetPropertiesClassOfQString() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#get(java.util.Properties, java.lang.Class, java.lang.String, java.lang.Class)}.
     */
    @Test
    @Ignore
    public void testGetPropertiesClassOfQStringClassOfT() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getByte(java.util.Properties, java.lang.Object, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetBytePropertiesObjectStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getByte(java.util.Properties, java.lang.Class, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetBytePropertiesClassOfQStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getByte(java.util.Properties, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetBytePropertiesStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getBoolean(java.util.Properties, java.lang.Object, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetBooleanPropertiesObjectStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getBoolean(java.util.Properties, java.lang.Class, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetBooleanPropertiesClassOfQStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getBoolean(java.util.Properties, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetBooleanPropertiesStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getShort(java.util.Properties, java.lang.Object, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetShortPropertiesObjectStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getShort(java.util.Properties, java.lang.Class, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetShortPropertiesClassOfQStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getShort(java.util.Properties, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetShortPropertiesStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getInteger(java.util.Properties, java.lang.Object, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetIntegerPropertiesObjectStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getInteger(java.util.Properties, java.lang.Class, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetIntegerPropertiesClassOfQStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getInteger(java.util.Properties, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetIntegerPropertiesStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getChar(java.util.Properties, java.lang.Object, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetCharPropertiesObjectStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getChar(java.util.Properties, java.lang.Class, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetCharPropertiesClassOfQStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getChar(java.util.Properties, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetCharPropertiesStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getFloat(java.util.Properties, java.lang.Object, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetFloatPropertiesObjectStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getFloat(java.util.Properties, java.lang.Class, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetFloatPropertiesClassOfQStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getFloat(java.util.Properties, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetFloatPropertiesStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getLong(java.util.Properties, java.lang.Object, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetLongPropertiesObjectStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getLong(java.util.Properties, java.lang.Class, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetLongPropertiesClassOfQStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getLong(java.util.Properties, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetLongPropertiesStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getDouble(java.util.Properties, java.lang.Object, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetDoublePropertiesObjectStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getDouble(java.util.Properties, java.lang.Class, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetDoublePropertiesClassOfQStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getDouble(java.util.Properties, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetDoublePropertiesStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#getParameter(java.util.Map, java.util.Properties, java.lang.String, core.properties.Props.Usage, java.lang.Class)}.
     */
    @Test
    @Ignore
    public void testGetParameter() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#get(java.util.Properties, java.lang.Object, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetPropertiesObjectStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#get(java.util.Properties, java.lang.Class, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetPropertiesClassOfQStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#get(java.util.Properties, java.lang.String, core.properties.Props.Usage)}.
     */
    @Test
    @Ignore
    public void testGetPropertiesStringUsage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#get(java.util.Properties, java.lang.String, core.properties.Props.Usage, java.lang.Class)}.
     */
    @Test
    @Ignore
    public void testGetPropertiesStringUsageClassOfT() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#get(java.util.Properties, java.lang.String, core.properties.Props.Usage, java.lang.Class, java.lang.Object)}.
     */
    @Test
    @Ignore
    public void testGetPropertiesStringUsageClassOfTT() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#set(java.util.Properties, java.lang.Object, java.lang.String, java.lang.String)}.
     */
    @Test
    @Ignore
    public void testSetPropertiesObjectStringString() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#set(java.util.Properties, java.lang.Class, java.lang.String, java.lang.String)}.
     */
    @Test
    @Ignore
    public void testSetPropertiesClassOfQStringString() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#set(java.util.Properties, java.lang.String, java.lang.String)}.
     */
    @Test
    @Ignore
    public void testSetPropertiesStringString() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#setSystem(java.lang.Object, java.lang.String, java.lang.String)}.
     */
    @Test
    @Ignore
    public void testSetSystemObjectStringString() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#setSystem(java.lang.Class, java.lang.String, java.lang.String)}.
     */
    @Test
    @Ignore
    public void testSetSystemClassOfQStringString() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link core.properties.PropUtil#setSystem(java.lang.String, java.lang.String)}.
     */
    @Test
    @Ignore
    public void testSetSystemStringString() {
        fail("Not yet implemented");
    }

    @Test
    public void testCreateNameObjectString() {
        try {
            PropUtil.createName(new Object(), null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.createName((Object) null, "b");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("java.lang.Object.b", PropUtil.createName(new Object(), "b"));
    }

    @Test
    public void testCreateNameClassOfQString() {
        try {
            PropUtil.createName(String.class, null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.createName((Class<?>) null, "b");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("java.lang.String.b", PropUtil.createName(String.class, "b"));
    }

    @Test
    public void testCreateNameStringString() {
        try {
            PropUtil.createName("a", null);
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        try {
            PropUtil.createName((String) null, "b");
            fail("exception expected!");
        } catch (IllegalArgumentException e) {
        }
        assertEquals("a.b", PropUtil.createName("a", "b"));
    }

}
