/*
 * ExcelTransformatorTest.java
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


/**
 * @author Michael Lieshoff
 */
public class ExcelTransformatorTest {
    private ExcelTransformator tranformator = ExcelTransformator.create();

    @Test
    public void shouldTransform() {
        HSSFWorkbook workbook = this.tranformator.transform(MockFactory.createTextTable());
        MockFactory.assertWorkbook(workbook);
    }

    @Test
    public void shouldTransformEmptyTable() {
        HSSFWorkbook workbook = this.tranformator.transform(new TextTable());
        MockFactory.assertEmptyWorkbook(workbook);
    }

}
