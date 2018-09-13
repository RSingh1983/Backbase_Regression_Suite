package org.testing.framework.utils.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class KVXLSXWriter {
    static org.slf4j.Logger LOG = LoggerFactory.getLogger(KVXLSXWriter.class.getName());
    private XLSXUtility xlsxUtility;

    public KVXLSXWriter(File file) {
        xlsxUtility = new XLSXUtility();
        xlsxUtility.setFile(file);
    }

    public void setSheet(String sheetName, int index) {


        try {
            if (sheetName != null)
                xlsxUtility.setSheet(sheetName);
            else
                xlsxUtility.setSheet(index);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public synchronized void write(String sheetName, Integer rowIndex, Map<String, String> dataMap) {
        try {
            xlsxUtility.setSheet(sheetName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeInSheet(rowIndex, dataMap);
    }

    public synchronized void write(Integer sheetIndex, Integer rowIndex, Map<String, String> dataMap) {
        try {
            xlsxUtility.setSheet(sheetIndex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeInSheet(rowIndex, dataMap);
    }


    public synchronized void write(String sheetName, Integer rowIndex, List<Map<String, String>> dataMaps) {
        try {
            xlsxUtility.setSheet(sheetName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeInSheet(rowIndex, dataMaps);
    }

    public synchronized void write(Integer sheetIndex, Integer rowIndex, List<Map<String, String>> dataMaps) {
        try {
            xlsxUtility.setSheet(sheetIndex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeInSheet(rowIndex, dataMaps);
    }

    public synchronized void write(String sheetName, Integer sheetIndex, Integer rowIndex, List<Map<String, String>> dataMaps) {

        setSheet(sheetName, sheetIndex);
        writeInSheet(rowIndex, dataMaps);
    }

    public synchronized void write(String sheetName, Integer sheetIndex, Integer rowIndex, Map<String, String> dataMap) {

        setSheet(sheetName, sheetIndex);
        writeInSheet(rowIndex, dataMap);
    }

    public synchronized void writeInSheet(Integer rowIndex, Map<String, String> dataMap) {
        LOG.info(new StringBuilder("Writing ").append(dataMap).append(" to row ").append(rowIndex).toString());
        XSSFRow row = xlsxUtility.getSheet().getRow(rowIndex);
        if (row == null) row = xlsxUtility.getSheet().createRow(rowIndex);
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            Cell cell = row.getCell(xlsxUtility.getHeader().indexOf(entry.getKey()), XSSFRow.CREATE_NULL_AS_BLANK);
            cell.setCellValue(entry.getValue());
        }
        xlsxUtility.write();
    }

    public synchronized void writeInSheet(Integer rowIndex, List<Map<String, String>> dataMaps) {
        if (null != dataMaps && !dataMaps.isEmpty()) {
            for (Map<String, String> dataMap : dataMaps) {
                writeInSheet(rowIndex, dataMap);
                rowIndex++;
            }
        }
    }

}
