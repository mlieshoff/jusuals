/*
 * ResourceUtilTest.java
 *
 * 09.10.2009
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
 */
public class ResourceUtilTest {
    private ResourceUtilInterface impl = EasyMock.createMock(ResourceUtilInterface.class);


    @Before
    public void setUp() throws Exception {
        ResourceUtil.setImpl(this.impl);
    }

    @After
    public void after() throws Exception {
        EasyMock.reset(this.impl);
        ResourceUtil.setImpl(new ResourceUtilImpl());
    }

    @Test
    public void shouldConstruct() {
        new ResourceUtil();
    }

    @Test
    public void shouldListBasenames() {
        List<String> result = new ArrayList<String>();
        result.add("a");
        EasyMock.expect(this.impl.listBasenames()).andReturn(result);
        EasyMock.replay(this.impl);
        assertEquals(result, ResourceUtil.listBasenames());
    }

    @Test
    public void shouldListLocalesForBasename() {
        List<Locale> result = new ArrayList<Locale>();
        result.add(Locale.GERMAN);
        EasyMock.expect(this.impl.listLocalesForBasename("a")).andReturn(result);
        EasyMock.replay(this.impl);
        assertEquals(result, ResourceUtil.listLocalesForBasename("a"));
    }

    @Test
    public void shouldGetResourceBundlesForBasenameAndLocale() {
        Map<String, String> result = new Hashtable<String, String>();
        result.put("a", "b");
        EasyMock.expect(this.impl.getResourceBundlesForBasenameAndLocale("a", Locale.GERMAN))
                .andReturn(result);
        EasyMock.replay(this.impl);
        assertEquals(result, ResourceUtil.getResourceBundlesForBasenameAndLocale("a",
                Locale.GERMAN));
    }

    @Test
    public void shouldLoad() {
        this.impl.load(Locale.GERMAN, "a");
        EasyMock.expectLastCall();
        ResourceUtil.load(Locale.GERMAN, "a");
    }

    @Test
    public void shouldLoadFromClassLoader() {
        this.impl.load(Locale.GERMAN, "a", this.getClass().getClassLoader());
        EasyMock.expectLastCall();
        ResourceUtil.load(Locale.GERMAN, "a", this.getClass().getClassLoader());
    }

    @Test
    public void shouldLoadFromXml() throws Exception {
        this.impl.loadFromXml(Locale.GERMAN, "a", this.getClass().getClassLoader());
        EasyMock.expectLastCall();
        ResourceUtil.loadFromXml(Locale.GERMAN, "a", this.getClass().getClassLoader());
    }

    @Test
    public void shouldGetStringFromLocaleAndBasenameAndObjectAndKey() {
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "a", "o", "b")).andReturn("result");
        EasyMock.replay(this.impl);
        assertEquals("result", ResourceUtil.getString(Locale.GERMAN, "a", "o", "b"));
    }

    @Test
    public void shouldGetStringFromLocaleAndBasenameAndClassAndKey() {
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "a", String.class, "b")).andReturn(
                "result");
        EasyMock.replay(this.impl);
        assertEquals("result", ResourceUtil.getString(Locale.GERMAN, "a", String.class, "b"));
    }

    @Test
    public void shouldGetStringFromLocaleAndBasenameAndKey() {
        EasyMock.expect(this.impl.getString(Locale.GERMAN, "a", "b")).andReturn(
                "result");
        EasyMock.replay(this.impl);
        assertEquals("result", ResourceUtil.getString(Locale.GERMAN, "a", "b"));
    }

    @Test
    public void shouldClear() throws Exception {
        this.impl.clear();
        EasyMock.expectLastCall();
        ResourceUtil.clear();
    }

    @Test
    public void shouldContains() {
        EasyMock.expect(this.impl.contains(Locale.GERMAN, "a", "b")).andReturn(true);
        EasyMock.replay(this.impl);
        assertTrue(ResourceUtil.contains(Locale.GERMAN, "a", "b"));
    }

}