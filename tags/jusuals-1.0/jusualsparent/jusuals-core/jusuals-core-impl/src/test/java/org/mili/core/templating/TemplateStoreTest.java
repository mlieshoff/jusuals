/*
 * TemplateStoreTest.java
 *
 * 18.05.2011
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
package org.mili.core.templating;

import java.util.*;

import org.junit.*;
import org.mili.core.properties.*;

import static org.junit.Assert.*;

import static org.mili.core.templating.MockFactory.*;

/**
 * @author Michael Lieshoff
 */
public class TemplateStoreTest {
    private TemplateStore store = new TemplateStore();

    @BeforeClass
    public static void beforeClass() throws Exception {
        MockFactory.prepare();
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldLoad() {
        this.store.read(FILE_TEMPLATE_WITH_ONE.getAbsolutePath(), "Test");
        assertEquals("Test_Main", this.store.listTemplates()[0]);
    }

    @Test
    public void shouldLoadWithSettedPath() {
        this.store.setPath(MockFactory.DIR.getAbsolutePath());
        this.store.read(FILE_TEMPLATE_WITH_ONE.getName(), "Test");
        assertEquals("Test_Main", this.store.listTemplates()[0]);
    }

    @Test
    public void shouldLoadTwoTemplateFiles() {
        this.store.read(FILE_TEMPLATE_WITH_ONE.getAbsolutePath(), "Test1");
        this.store.read(FILE_OTHER_TEMPLATE_WITH_ONE.getAbsolutePath(), "Test2");
        assertEquals("Test1_Main", this.store.listTemplates()[0]);
        assertEquals("Test2_Other", this.store.listTemplates()[1]);
    }

    @Test
    public void shouldLoadTemplateFileWithInnerTemplate() {
        this.store.read(FILE_TEMPLATE_WITH_ONE_INNER.getAbsolutePath(), "Test3");
        assertEquals("Test3_Inner", this.store.listTemplates()[0]);
        assertEquals("Test3_Main", this.store.listTemplates()[1]);
    }

    @Test
    public void shouldStoreDataTemplateFileWithInnerTemplate() throws Exception {
        this.store.store(MockFactory.getTemplateWithInner(), "Test3");
        assertEquals("Test3_Inner", this.store.listTemplates()[0]);
        assertEquals("Test3_Main", this.store.listTemplates()[1]);
    }

    @Test
    public void failStoreDefectEnd() throws Exception {
        this.store.store(MockFactory.getDefectEndTemplateWithOne(), "Test");
        assertEquals(0, this.store.listTemplates().length);
    }

    @Test
    public void failLoadDefectEnd() {
        this.store.read(FILE_DEFECTEND_TEMPLATE_WITH_ONE.getAbsolutePath(), "Test");
        assertEquals(0, this.store.listTemplates().length);
    }

    @Test
    public void failLoadDefectContent() {
        this.store.read(FILE_DEFECTCONTENT_TEMPLATE_WITH_ONE.getAbsolutePath(), "Test");
        assertEquals(0, this.store.listTemplates().length);
    }

    @Test
    public void failLoadDefectInnerEnd() {
        this.store.read(FILE_DEFECTEND_TEMPLATE_WITH_ONE_INNER.getAbsolutePath(), "Test");
        assertEquals(0, this.store.listTemplates().length);
    }

    @Test
    public void failLoadDefectInnerContent() {
        this.store.read(FILE_DEFECTCONTENT_TEMPLATE_WITH_ONE_INNER.getAbsolutePath(), "Test");
        assertEquals(0, this.store.listTemplates().length);
    }

    @Test
    public void shouldGetPath() {
        this.store.setPath("abbas");
        assertEquals("abbas", this.store.getPath());
    }

    @Test
    public void shouldGetTemplate() throws Exception {
        this.store.store(MockFactory.getTemplateWithInner(), "Test3");
        assertEquals("abc${Inner}", this.store.get("Test3_Main"));
    }

    @Test
    public void failGetTemplate() throws Exception {
        assertEquals("", this.store.get("ABC"));
    }

    @Test
    public void shouldGetRequiredTemplate() throws Exception {
        this.store.store(MockFactory.getTemplateWithInner(), "Test3");
        assertEquals("abc${Inner}", this.store.getRequired("Test3_Main"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void failGetRequiredTemplate() throws Exception {
        this.store.getRequired("A");
    }

    @Test
    public void shouldPut() {
        this.store.put("A", "lala");
        assertEquals("lala", this.store.get("A"));
    }

    @Test
    public void failPutBecauseNullKey() {
        this.store.put(null, "lala");
    }

    @Test
    public void failPutBecauseEmptyKey() {
        this.store.put("", "lala");
    }

    @Test
    public void failPutBecauseNullTemplate() {
        this.store.put("A", null);
        assertEquals("", this.store.get("A"));
    }

    @Test
    public void failPutBecauseEmptyTemplate() {
        this.store.put("A", "");
        assertEquals("", this.store.get("A"));
    }

    @Test
    public void shouldReadComponentSheet() {
        this.store.read(FILE_TEMPLATE_WITH_ONE_INNER.getAbsolutePath(), "Test3");
        this.store.readComponentSheet(FILE_COMPONENT_SHEET.getAbsolutePath(), "Test3");
    }

    @Test
    public void shouldGetComponentSheet() {
        this.store.read(FILE_TEMPLATE_WITH_ONE_INNER.getAbsolutePath(), "Test3");
        this.store.readComponentSheet(FILE_COMPONENT_SHEET.getAbsolutePath(), "Test3");
        Properties properties = PropUtil.readProperties(FILE_COMPONENT_SHEET);
        assertEquals(properties, this.store.getComponentSheet("Test3"));
    }

    @Test
    public void shouldReplaceWithComponentSheetsDefault() {
        Properties defaultProp = PropUtil.readProperties(FILE_DEFAULT_COMPONENT_SHEET);
        Properties compProp = PropUtil.readProperties(FILE_COMPONENT_SHEET);
        String template = "${one-more}";
        assertEquals("b", TemplateStore.replaceWithComponentSheets(template, defaultProp,
                compProp));
    }

    @Test
    public void shouldNotReplace() {
        assertEquals("", TemplateStore.replace(null, new String[][]{{}}));
    }

    @Test
    public void shouldReplace() {
        assertEquals("sabba", TemplateStore.replace("abbas", new String[][]{{"ab", "sa"}, {"bas", "bba"}}));
    }

    @Test
    public void failToReplace() {
        TemplateStore.replace("abbas", new String[][]{{"ab"}});
    }

}
