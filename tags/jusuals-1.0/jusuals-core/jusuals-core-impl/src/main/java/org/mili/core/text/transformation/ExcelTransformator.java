/*
 * ExcelTransformator.java
 *
 * 15.10.2010
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
package org.mili.core.text.transformation;

import org.apache.poi.hssf.usermodel.*;
import org.mili.core.text.*;


/**
 * This class is a EXCEL transformator.
 *
 * @author Michael Lieshoff
 *
 */
public class ExcelTransformator {

    /**
     * Instantiates a new excel transformator.
     */
    protected ExcelTransformator() {
        super();
    }

    /**
     * Creates the transformator.
     *
     * @return the excel transformator
     */
    public static ExcelTransformator create() {
        return new ExcelTransformator();
    }

    /**
     * Transforms.
     *
     * @param from from table
     * @param params the params
     * @return the HSSF workbook
     */
    public HSSFWorkbook transform(Table from, Object... params) {
        if (from.getRowSize() == 0) {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFDataFormat format = wb.createDataFormat();
            HSSFSheet sheet = wb.createSheet(getSheetName(null));
            HSSFRow r = sheet.createRow(0);
            HSSFCell c = r.createCell((short) (0));
            c.setCellValue("Keine Daten vorhanden !");
            return wb;
        }
        String fsInt = "#,###,##0";
        String fsFloat = "#,###,##0.000";
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFDataFormat format = wb.createDataFormat();
        HSSFSheet sheet = wb.createSheet(getSheetName(null));
        HSSFFont headFont = wb.createFont();
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle headStyle = wb.createCellStyle();
        headStyle.setFont(headFont);
        HSSFCellStyle numberStyle = wb.createCellStyle();
        HSSFRow headerRow = sheet.createRow(0);
        short z = 0;
        for (int i = 0, n = from.getColSize(); i < n; i++) {
            Col col = from.getCol(i);
            HSSFCell headerCell = headerRow.createCell((short) (z));
            headerCell.setCellStyle(headStyle);
            headerCell.setCellValue(col.getName());
            z++;
        }
        for (int i = 0, n = from.getRowSize(); i < n; i++) {
            Row row = from.getRow(i);
            HSSFRow contentRow = sheet.createRow(i + 1);
            short j = 0;
            for (int ii = 0, nn = from.getColSize(); ii < nn; ii++) {
                Col col = from.getCol(ii);
                Object o = row.getValue(ii);
                HSSFCell contentCell = contentRow.createCell((short) (j));
                String value = o == null ? "" : String.valueOf(o);
                if (o instanceof Number) {
                    if (o instanceof Integer) {
                        numberStyle.setDataFormat(format.getFormat(fsInt));
                        contentCell.setCellValue(Integer.parseInt(value));
                        contentCell.setCellStyle(numberStyle);
                    } else if (o instanceof Float) {
                        numberStyle.setDataFormat(format.getFormat(fsFloat));
                        contentCell.setCellValue(Float.parseFloat(value));
                        contentCell.setCellStyle(numberStyle);
                    }
                } else {
                    contentCell.setCellValue(value);
                }
                j++;
            }
        }
        return wb;
    }

    /*
     * name of sheet is lowercase and length is < 31 chars. It should NOT contained the chars:
     * <code>\/*?[]</code>
     *
     * @param name sheets name.
     * @return normalized sheet name.
     */
    String getSheetName(String name) {
        String s = name;
        if (s == null || s.length() == 0) {
            return "unknown";
        }
        s = s.replace("\\", "").replace("/", "").replace("?", "").replace("]", "")
                .replace("[", "");
        if (s.length() > 31) {
            s = s.substring(0, 31);
        }
        return s;
    }

}
