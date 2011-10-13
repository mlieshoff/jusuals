/*
 * MockFactory.java
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

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;
import org.mili.test.*;

/**
 * @author Michael Lieshoff
 */
public class MockFactory {
    final static File DIR = TestUtils.getTmpFolder(TemplateStoreTest.class);
    final static File FILE_TEMPLATE_WITH_ONE = new File(DIR, "temp-1.temp");
    final static File FILE_OTHER_TEMPLATE_WITH_ONE = new File(DIR, "temp-2.temp");
    final static File FILE_TEMPLATE_WITH_ONE_INNER = new File(DIR, "temp-3.temp");

    final static File FILE_DEFECTEND_TEMPLATE_WITH_ONE = new File(DIR, "defectend-1.temp");
    final static File FILE_DEFECTCONTENT_TEMPLATE_WITH_ONE = new File(DIR, "defectcontent-1.temp");
    final static File FILE_DEFECTEND_TEMPLATE_WITH_ONE_INNER = new File(DIR, "defectend-2.temp");
    final static File FILE_DEFECTCONTENT_TEMPLATE_WITH_ONE_INNER = new File(DIR, "defectcontent-2.temp");

    final static File FILE_COMPONENT_SHEET = new File(DIR, "componentsheet-1.properties");
    final static File FILE_DEFAULT_COMPONENT_SHEET = new File(DIR, "defaultcomponentsheet-1.properties");

    /**
     * Prepare.
     *
     * @throws Exception the exception
     */
    public static void prepare() throws Exception {
        FileUtils.deleteDirectory(DIR);
        DIR.mkdirs();
        FileUtils.writeStringToFile(FILE_TEMPLATE_WITH_ONE, MockFactory.getTemplateWithOne());
        FileUtils.writeStringToFile(FILE_OTHER_TEMPLATE_WITH_ONE, MockFactory
                .getOtherTemplateWithOne());
        FileUtils.writeStringToFile(FILE_TEMPLATE_WITH_ONE_INNER, MockFactory
                .getTemplateWithInner());
        FileUtils.writeStringToFile(FILE_DEFECTEND_TEMPLATE_WITH_ONE, MockFactory
                .getDefectEndTemplateWithOne());
        FileUtils.writeStringToFile(FILE_DEFECTEND_TEMPLATE_WITH_ONE, MockFactory
                .getDefectEndTemplateWithOne());
        FileUtils.writeStringToFile(FILE_DEFECTEND_TEMPLATE_WITH_ONE_INNER, MockFactory
                .getDefectEndTemplateWithOneInner());
        FileUtils.writeStringToFile(FILE_DEFECTCONTENT_TEMPLATE_WITH_ONE_INNER, MockFactory
                .getDefectContentTemplateWithOneInner());
        // default
        Properties properties = new Properties();
        properties.put("key-with-value", "a");
        properties.put("key-with-empty-value", "");
        properties.put("one-more", "b");
        OutputStream os = new FileOutputStream(FILE_DEFAULT_COMPONENT_SHEET);
        properties.store(os, "TEST");
        os.close();
        // comp 1
        properties.clear();
        properties.put("key-with-value", "c");
        properties.put("key-with-empty-value", "");
        os = new FileOutputStream(FILE_COMPONENT_SHEET);
        properties.store(os, "TEST");
        os.close();
    }

    /**
     * Gets the template with one.
     *
     * @return the template with one
     */
    public static String getTemplateWithOne() {
        return "<!-- @Main -->abc<!-- /@Main -->";
    }

    /**
     * Gets the defect content template with one.
     *
     * @return the defect content template with one
     */
    public static String getDefectContentTemplateWithOne() {
        return "<!-- @Main -->abc<!-- /@George -->";
    }

    /**
     * Gets the defect end template with one.
     *
     * @return the defect end template with one
     */
    public static String getDefectEndTemplateWithOne() {
        return "<!-- @Main -->abc<!-- /@Main";
    }

    /**
     * Gets the other template with one.
     *
     * @return the other template with one
     */
    public static String getOtherTemplateWithOne() {
        return "<!-- @Other -->xyz<!-- /@Other -->";
    }

    /**
     * Gets the template with inner.
     *
     * @return the template with inner
     */
    public static String getTemplateWithInner() {
        return "<!-- @Main -->abc<!-- @Inner --><!-- /@Inner --><!-- /@Main -->";
    }

    /**
     * Gets the defect end template with one inner.
     *
     * @return the defect end template with one inner
     */
    public static String getDefectEndTemplateWithOneInner() {
        return "<!-- @Main -->abc<!-- @Inner --><!-- /@Inner <!-- /@Main -->";
    }

    /**
     * Gets the defect content template with one inner.
     *
     * @return the defect content template with one inner
     */
    public static String getDefectContentTemplateWithOneInner() {
        return "<!-- @Main -->abc<!-- @Inner --><!-- /@Outer --><!-- /@Main -->";
    }

}
