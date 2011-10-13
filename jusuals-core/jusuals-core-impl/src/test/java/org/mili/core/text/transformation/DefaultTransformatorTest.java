/*
 * DefaultTransformatorTest.java
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
package org.mili.core.text.transformation;

import org.apache.poi.hssf.usermodel.*;
import org.junit.*;
import org.mili.core.text.*;

import static org.junit.Assert.*;


/**
 * @author Michael Lieshoff
 */
public class DefaultTransformatorTest {
    private DefaultTransformator transformator = DefaultTransformator.create();

    @Test
    public void shouldHaveTransformFromTextToCsv() {
        Table table = (Table) this.transformator.transform(MockFactory.createTextTable(),
                AvailableTransformations.CSV);
        assertEquals(MockFactory.getCsvString(), table.toString().toString()
                .replaceAll("[\r\n]", ""));
    }

    @Test
    public void shouldHaveTransformFromTextToXml() {
        Table table = (Table) this.transformator.transform(MockFactory.createTextTable(),
                AvailableTransformations.XML);
        assertEquals(MockFactory.getXmlString(), table.toString().toString()
                .replaceAll("[\r\n]", ""));
    }

    @Test
    public void shouldHaveTransformFromXmlToText() {
        Table table = (Table) this.transformator.transform(MockFactory.createXML(),
                AvailableTransformations.TABLE);
        assertEquals(MockFactory.getTextString(), table.toString().toString()
                .replaceAll("[\r\n]", ""));
    }

    @Test
    public void shouldHaveTransformFromTextToExcel() {
        HSSFWorkbook workbook = (HSSFWorkbook) this.transformator.transform(MockFactory
                .createTextTable(), AvailableTransformations.EXCEL);
        MockFactory.assertWorkbook(workbook);
    }

    @Test(expected=NullPointerException.class)
    public void failtTransformBeacuseNullType() {
        this.transformator.transform(MockFactory.createXML(), null);
    }

}
