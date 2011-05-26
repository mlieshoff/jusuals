/*
 * ResourceHelperTest.java
 *
 * 05.03.2010
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

package org.mili.core.resource;

import java.util.*;

import org.easymock.*;
import org.junit.*;

import static org.junit.Assert.*;


/**
 * @author Michael Lieshoff
 *
 */
public class ResourceHelperTest {
    private ResourceHelper helper = null;
    private ResourceHelper helperWithoutClass = null;
    private ResourceUtilInterface impl = EasyMock.createMock(ResourceUtilInterface.class);

    @Before
    public void setUp() {
        StaticResource.impl = this.impl;
        this.helper = new ResourceHelper(String.class, Locale.GERMAN, "base");
        this.helperWithoutClass = new ResourceHelper(Locale.GERMAN, "base");
    }

    @After
    public void after() {
        EasyMock.reset(this.impl);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseNullClassAndBasename() {
        new ResourceHelper((Class<?>) null, "a");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseClassAndEmptyBasename() {
        new ResourceHelper(String.class, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseClassAndNullBasename() {
        new ResourceHelper(String.class, null);
    }

    @Test
    public void shouldConstructClassAndBasename() {
        new ResourceHelper(String.class, "base");
    }

    @Test(expected=NullPointerException.class)
    public void failConstructBecauseNullObjectAndBasename() {
        new ResourceHelper((Object) null, "a");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseObjectAndEmptyBasename() {
        new ResourceHelper("a", "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseObjectAndNullBasename() {
        new ResourceHelper("a", null);
    }

    @Test
    public void shouldConstructObjectAndBasename() {
        new ResourceHelper("a", "base");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseNullLocaleAndBasename() {
        new ResourceHelper((Locale) null, "a");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseLocaleAndEmptyBasename() {
        new ResourceHelper(Locale.GERMAN, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseLocaleAndNullBasename() {
        new ResourceHelper(Locale.GERMAN, null);
    }

    @Test
    public void shouldConstructLocaleAndBasename() {
        new ResourceHelper(Locale.GERMAN, "base");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseEmptyBasename() {
        new ResourceHelper("");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseNullBasename() {
        new ResourceHelper(null);
    }

    @Test
    public void shouldConstructBasename() {
        new ResourceHelper("base");
    }

    @Test(expected=NullPointerException.class)
    public void failConstructBecauseNullObjectAndLocaleAndBasename() {
        new ResourceHelper((Object) null, Locale.GERMAN, "a");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseObjectAndNullLocaleAndBasename() {
        new ResourceHelper("a", null, "a");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseObjectAndLocaleAndEmptyBasename() {
        new ResourceHelper("a", Locale.GERMAN, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseObjectAndLocaleAndNullBasename() {
        new ResourceHelper("a", Locale.GERMAN, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseNullClassAndLocaleAndBasename() {
        new ResourceHelper((Class<?>) null, Locale.GERMAN, "a");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseClassAndNullLocaleAndBasename() {
        new ResourceHelper(String.class, null, "a");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseClassAndLocaleAndEmptyBasename() {
        new ResourceHelper(String.class, Locale.GERMAN, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void failConstructBecauseClassAndLocaleAndNullBasename() {
        new ResourceHelper(String.class, Locale.GERMAN, null);
    }

    @Test
    public void shouldGetStringFromKeyWithClass() {
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "base", String.class, "key1"))
                .andReturn("result");
        EasyMock.replay(this.impl);
        assertEquals("result", this.helper.getString("key1"));
        EasyMock.verify(this.impl);
    }

    @Test
    public void shouldGetStringFromKeyWithoutClass() {
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "base", "key1")).andReturn("result");
        EasyMock.replay(this.impl);
        assertEquals("result", this.helperWithoutClass.getString("key1"));
    }

    @Test
    public void shouldGetStrings() {
        String[] result = new String[]{"a", "b"};
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "base", String.class, "key1"))
                .andReturn("a");
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "base", String.class, "key2"))
                .andReturn("b");
        EasyMock.replay(this.impl);
        assertArrayEquals(result, this.helper.getStrings("key1", "key2"));
    }

    @Test
    public void shouldGetStringFromObjectAndKey() {
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "base", "object", "key1"))
                .andReturn("result");
        EasyMock.replay(this.impl);
        assertEquals("result", this.helper.getString("object", "key1"));
        EasyMock.verify(this.impl);
    }

    @Test
    public void shouldGetStringFromClassAndKey() {
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "base", String.class, "key1"))
                .andReturn("result");
        EasyMock.replay(this.impl);
        assertEquals("result", this.helper.getString(String.class, "key1"));
        EasyMock.verify(this.impl);
    }

    @Test
    public void shouldGetStringFromLocaleAndBaseNameAndKey() {
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "base", "key1")).andReturn("result");
        EasyMock.replay(this.impl);
        assertEquals("result", this.helper.getString(Locale.GERMAN, "base", "key1"));
        EasyMock.verify(this.impl);
    }

    @Test
    public void shouldGetStringFromLocaleAndBaseNameAndClassAndKey() {
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "base", String.class, "key1"))
                .andReturn("result");
        EasyMock.replay(this.impl);
        assertEquals("result", this.helper.getString(Locale.GERMAN, "base", String.class,
                "key1"));
        EasyMock.verify(this.impl);
    }

    @Test
    public void shouldGetStringFromLocaleAndBaseNameAndObjectAndKey() {
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "base", "object", "key1"))
                .andReturn("result");
        EasyMock.replay(this.impl);
        assertEquals("result", this.helper.getString(Locale.GERMAN, "base", "object", "key1"));
        EasyMock.verify(this.impl);
    }

    @Test
    public void shouldGetProperty() {
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "base", "key1")).andReturn("result");
        EasyMock.replay(this.impl);
        assertEquals("result", this.helper.getProperty("key1"));
        EasyMock.verify(this.impl);
    }

    @Test
    public void shouldLoad() {
        this.impl.load(Locale.GERMAN, "base");
        EasyMock.expectLastCall();
        EasyMock.replay(this.impl);
        this.helper.load(Locale.GERMAN, "base");
        EasyMock.verify(this.impl);
    }

    @Test
    public void shouldLoadFromClassLoader() {
        this.impl.load(Locale.GERMAN, "base", this.getClass().getClassLoader());
        EasyMock.expectLastCall();
        EasyMock.replay(this.impl);
        this.helper.load(Locale.GERMAN, "base", this.getClass().getClassLoader());
        EasyMock.verify(this.impl);
    }

    @Test
    public void shouldContains() {
        EasyMock.expect(this.impl.contains(Locale.GERMAN, "base", "key")).andReturn(true);
        EasyMock.replay(this.impl);
        assertTrue(this.helper.contains("key"));
    }

    @Test
    public void shouldListBasenames() {
        List<String> result = new ArrayList<String>();
        result.add("a");
        EasyMock.expect(this.impl.listBasenames()).andReturn(result);
        EasyMock.replay(this.impl);
        assertEquals(result, this.helper.listBasenames());
    }

    @Test
    public void shouldListLocalesForBasename() {
        List<Locale> result = new ArrayList<Locale>();
        result.add(Locale.GERMAN);
        EasyMock.expect(this.impl.listLocalesForBasename("a")).andReturn(result);
        EasyMock.replay(this.impl);
        assertEquals(result, this.helper.listLocalesForBasename("a"));
    }

    @Test
    public void shouldGetResourceBundlesForBasenameAndLocale() {
        Map<String, String> result = new Hashtable<String, String>();
        result.put("a", "b");
        EasyMock.expect(this.impl.getResourceBundlesForBasenameAndLocale("a", Locale.GERMAN))
                .andReturn(result);
        EasyMock.replay(this.impl);
        assertEquals(result, this.helper.getResourceBundlesForBasenameAndLocale("a",
                Locale.GERMAN));
    }

}