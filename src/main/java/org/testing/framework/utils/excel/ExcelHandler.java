package org.testing.framework.utils.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExcelHandler {

    static Logger logger = LoggerFactory.getLogger(ExcelHandler.class.getName());

    private Workbook workbook;
    private String fileLocation;
    private Sheet sheet;
    private Sheet currentSheet;
    private Row currentRow;
    private Cell currentCell;
    private Row row;
    private Cell cell;
    private DataFormatter dataFormatter;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;


    /**
     * Sets up a excel handles with the give file
     * @param file
     * @throws IOException
     */
    public ExcelHandler(File file) throws IOException {
        init(file);
    }

    /**
     * Sets up a excel handler with the given file location
     * If file do not exist it will create a new excel file
     * @param fileLocation Full file location as string
     * @throws Exception
     */
    public ExcelHandler(String fileLocation) throws Exception {
        File file = null;
        this.fileLocation = fileLocation;
        try {
            new FileInputStream(fileLocation);
        }catch (FileNotFoundException e){

            logger.info("Creating new file at location : "+fileLocation);
            logger.warn("No such file");

            file = new File(fileLocation);
            file.createNewFile();
            workbook = new XSSFWorkbook();
            workbook.createSheet();

            writeAndSave();

        }finally {
            init(new File(fileLocation));
        }
    }

    /**
     * Utility to initialize global variables
     * @param file
     * @throws IOException
     */
    private void init(File file) throws IOException {
        this.fileLocation = file.getAbsolutePath();
        fileInputStream = new FileInputStream(fileLocation);
        try{
            workbook = new XSSFWorkbook(fileInputStream);
            currentSheet = workbook.getSheetAt(0);
            currentRow = currentSheet.getRow(0);
            currentCell = currentRow.getCell(0);
        }catch (NullPointerException e){
            /* do nothing */
        }finally{

            try {
                fileInputStream.close();
            }catch (IOException e){
                logger.warn(e.getMessage());
            }
        }

    }


    /**
<<<<<<< Updated upstream
     * This will select the sheet in the document by its name
     *
     * @param sheetName The sheet number as an int
=======
     * Sets name of the current sheet
     * @param sheetName
>>>>>>> Stashed changes
     */
    public void setCurrentSheet(String sheetName){
        currentSheet = workbook.getSheet(sheetName);
    }

    /**
     * Sets name of the sheet using sheet index
     * @param sheetNumber Sheet at index
     */
    public void setCurrentSheet(int sheetNumber){
        currentSheet = workbook.getSheetAt(sheetNumber);
    }

    /**
     *  Gets current sheet name
     * @return Current Sheet name
     */
    public String getCurrentSheetName(){
        return currentSheet.getSheetName();
    }

    /**
     * Gets cell value . If the cell / row is invalid it returns null
     * @param row as integer
     * @param col as integer
     * @return value in the cell
     * @exception if invalid or empty row or cell
     */
    public String getCellData(int row , int col) throws Exception {
        String data = null;
        try{
            cell = currentSheet.getRow(row).getCell(col);
            data = getCellData(cell);
        }catch(NullPointerException e){
            throw new Exception("Invalid / empty row or cell");
        }
        return data;
    }

    /**
     * Gets cell value for a given Sheet index . If the cell / row is invalid it returns null
     * @param sheetNumber as integer
     * @param row as integer
     * @param col as integer
     * @return Cell value as String
     * @exception if cell / row not valid or sheet number not valid
     */
    public String getCellData(int sheetNumber , int row , int col) throws Exception {
        String data = null;
        try {
            cell = workbook.getSheetAt(sheetNumber).getRow(row).getCell(col);
            data = getCellData(cell);
        }catch (NullPointerException e){
            throw new Exception("Invalid / empty row or cell");
        }catch(IllegalArgumentException e){
            throw new Exception("Sheet number not valid");
        }
        return data;
    }

    /**
     * Gets value for a given Sheet name .
     * @param sheetName as string
     * @param row as integer
     * @param col as integer
     * @return Cell value as String
     * @exception if cell / row is invalid or sheet name is not valid
     */
    public String getCellData(String sheetName , int row , int col) throws Exception {
        String data = null;
        try{
            cell = workbook.getSheet(sheetName).getRow(row).getCell(col);
            data = getCellData(cell);
        }catch (NullPointerException e){
            throw new Exception("Invalid / emprty row or cell");
        }catch(IllegalArgumentException e){
            throw new Exception("Sheet name not valid");
        }
        return data;
    }

    /**
     * Utility get fetch value of a cell as string
     * @param cell
     * @return Cell value as String
     */
    private String getCellData(Cell cell) throws Exception {
        String data = null;
        try{
            dataFormatter = new DataFormatter();
            data = dataFormatter.formatCellValue(cell);
        }catch (NullPointerException e){
            throw new Exception("Empty cell");
        }
        return data;
    }

    /**
     * Gets the list of values in the given column of a given sheet name
     * @param sheetName Sheet name as string
     * @param columnNumber column number as integer
     * @return List of values as strings
     */
    public ArrayList<String> getColumnDataList(String sheetName,int columnNumber) throws Exception {
        ArrayList<String> columnData = new ArrayList<String>();
        try {
            Sheet sheet = workbook.getSheet(sheetName);
            for (Row row : sheet) {
                columnData.add(getCellData(row.getCell(columnNumber)));
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet name not valid");
        }catch (NullPointerException e){
            throw new Exception("Column not valid");
        }
        return columnData;
    }

    /**
     * Gets the list of values in the given column of a given sheet index
     * @param sheetNumber
     * @param columnNumber
     * @return List of values as string
     * @throws Exception
     */
    public ArrayList<String> getColumnDataList(int sheetNumber,int columnNumber) throws Exception {
        ArrayList<String> columnData = new ArrayList<String>();
        try {
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            for (Row row : sheet) {
                columnData.add(getCellData(row.getCell(columnNumber)));
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet number not valid");
        }catch (NullPointerException e){
            throw new Exception("Column not valid");
        }
        return columnData;
    }

    /**
     * Gets the list of values in the given column of current sheet
     * @param columnNumber
     * @return List of values as String
     */
    public ArrayList<String> getColumnDataList(int columnNumber)throws Exception{
        Sheet sheet = currentSheet;
        ArrayList<String> columnData = new ArrayList<String>();
        try {
            for (Row row : sheet) {
                columnData.add(getCellData(row.getCell(columnNumber)));
            }
        }catch (NullPointerException e){
            throw new Exception("Column not valid");
        }
        return columnData;
    }

    /**
     * Gets the list of values in the given Row of given sheet name
     * @param sheetName
     * @param rowNumber
     * @return List of values as String
     * @exception
     */
    public ArrayList<String> getRowDataList(String sheetName,int rowNumber) throws Exception {
        ArrayList<String> rowData = new ArrayList<String>();
        try{
            Sheet sheet = workbook.getSheet(sheetName);
            for(Cell cell : sheet.getRow(rowNumber)){
                rowData.add(getCellData(cell));
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet name not valid");
        }catch(NullPointerException e){
            throw new Exception("Row number not valid");
        }
        return rowData;
    }

    /**
     * Gets the list of values in the given Row of given sheet number
     * @param sheetNumber
     * @param rowNumber
     * @return List of values as String
     */
    public ArrayList<String> getRowDataList(int sheetNumber,int rowNumber) throws Exception {
        ArrayList<String> rowData = new ArrayList<String>();
        try{
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            for(Cell cell : sheet.getRow(rowNumber)){
                rowData.add(getCellData(cell));
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet number not valid");
        }catch (NullPointerException e){
            throw new Exception("Row number not valid");
        }
        return rowData;
    }

    /**
     * Gets the list of values in the given Row of current sheet
     * @param rowNumber
     * @return List of values as String
     * @throws Exception
     */
    public ArrayList<String> getRowDataList(int rowNumber) throws Exception{
        Sheet sheet = currentSheet;
        ArrayList<String> rowData = new ArrayList<String>();
        try {
            for (Cell cell : sheet.getRow(rowNumber)) {
                rowData.add(getCellData(cell));
            }
        }catch (NullPointerException e){
            throw new Exception("Row number not valid");
        }
        return rowData;
    }


    /**
     * Utility to create HasMap of sheet name and values in the sheet
     * @return HasMap Sheet and values of the sheet  {Sheet name : [[values1],[values2],...]}
     * @exception if the cell is not valid
     */
//    public HashMap<String, ArrayList<ArrayList<String>>> readSheet() throws Exception {
//        HashMap<String,ArrayList<ArrayList<String>>> fileData = new HashMap<String,ArrayList<ArrayList<String>>>();
//        ArrayList<ArrayList<String>> sheetData ;
//        ArrayList<String> rowData ;
//
//        for(Sheet sheet : workbook) {
//            sheetData = new ArrayList<ArrayList<String>>();
//            for (Row row : sheet) {
//                rowData = new ArrayList<String>();
//                for (Cell cell : row) {
//                    rowData.add(getCellData(cell));
//                }
//                sheetData.add(rowData);
//            }
//            fileData.put(sheet.getSheetName(), sheetData);
//        }
//        return fileData;
//    }

    /**
     * Gets the values Column wise assuming first cell as header for all sheets in the file
     * @return HasMap of the Sheet , column and values {Sheet name : {Column header : [values] } }
     * @exception if cell not valid
     */
//    public HashMap<String,HashMap<String,ArrayList<String>>> readAllSheetUsingColumnAsHeader() throws Exception {
//        HashMap<String,HashMap<String,ArrayList<String>>> fileData = new HashMap<String,HashMap<String,ArrayList<String>>>();
//        HashMap<String,ArrayList<String>> column;
//        ArrayList<String> columnData;
//        boolean check = true;
//        String key = null;
//        int index = 0;
//        for(Sheet sheet : workbook){
//            index = 0;
//            column = new HashMap<String,ArrayList<String>>();
//            try {
//                for (Cell cell : sheet.getRow(0)) {
//                    key = getCellData(cell);
//                    columnData = new ArrayList<String>();
//                    check = true;
//
//                    for (Row row : sheet) {
//                        if (check) {
//                            check = false;
//                        } else {
//                            columnData.add(getCellData(row.getCell(index)));
//                        }
//                    }
//                    column.put(key, columnData);
//                    index++;
//                }
//
//            }catch (NullPointerException e){
//                /* do nothing */
//            }
//            fileData.put(sheet.getSheetName(), column);
//        }
//        return fileData;
//    }

    /**
     * Gets the values column wise assuming first cell of column as header for current sheet
     * @return HasMap of Column and values   {Column header : [values]}
     * @exception throw exception when the cell is not valid
     */
    public HashMap<String,ArrayList<String>> readSheetUsingColumnAsHeader()throws Exception{
        HashMap<String,ArrayList<String>> column;
        ArrayList<String> columnData;
        boolean check;
        Sheet sheet = currentSheet;
        String key;
        int index = 0;
        column = new HashMap<String,ArrayList<String>>();
        for(Cell cell : sheet.getRow(0)){
            key = getCellData(cell);
            columnData = new ArrayList<String>();
            check = true;

            for(Row row : sheet){
                if(check){
                    check = false;
                }else{
                    columnData.add(getCellData(row.getCell(index)));
                }
            }
            column.put(key,columnData);
            index++;
        }
        return column;
    }

    /**
     * Gets the values column wise assuming first cell of column as header for given sheet name
     * @param sheetName
     * @return HasMap of Column and values   {Column header : [values]}
     * @exception throw exception when the cell is not valid of sheet name is not valid
     */
    public HashMap<String,ArrayList<String>> readSheetUsingColumnAsHeader(String sheetName)throws Exception{
        HashMap<String,ArrayList<String>> column = null;
        ArrayList<String> columnData;
        boolean check;
        String key;
        int index = 0;
        try{
            Sheet sheet = workbook.getSheet(sheetName);
            column = new HashMap<String,ArrayList<String>>();
            for(Cell cell : sheet.getRow(0)){
                key = getCellData(cell);
                columnData = new ArrayList<String>();
                check = true;

                for(Row row : sheet){
                    if(check){
                        check = false;
                    }else{
                        columnData.add(getCellData(row.getCell(index)));
                    }
                }
                column.put(key,columnData);
                index++;
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet name not valid");
        }
        return column;
    }

    /**
     * Gets the values column wise assuming first cell of column as header for given sheet number
     * @param sheetNumber
     * @return HasMap of Column and values   {Column header : [values]}
     * @exception throw exception when the cell is not valid of sheet name is not valid
     */
    public HashMap<String,ArrayList<String>> readSheetUsingColumnAsHeader(int sheetNumber) throws Exception {
        HashMap<String,ArrayList<String>> column = null;
        ArrayList<String> columnData;
        boolean check;
        String key;
        int index = 0;
        try {
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            column = new HashMap<String, ArrayList<String>>();
            for (Cell cell : sheet.getRow(0)) {
                key = getCellData(cell);
                columnData = new ArrayList<String>();
                check = true;

                for (Row row : sheet) {
                    if (check) {
                        check = false;
                    } else {
                        columnData.add(getCellData(row.getCell(index)));
                    }
                }
                column.put(key, columnData);
                index++;
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet number not valid");
        }
        return column;
    }

    /**
     * Gets the values column wise for a particular column name and sheet name
     * @param columnName
     * @param sheetName
     * @return list of values for that column header
     * @exception throw exception when the cell is not valid of sheet name is not valid
     */
    public ArrayList<String> readSheetUsingColumnAsHeader(String sheetName, String columnName) throws Exception {
        ArrayList<String> columnData = new ArrayList<String>();
        boolean check = true;
        int index = 0;
        try{
            Sheet sheet = workbook.getSheet(sheetName);
            for(Cell cell : sheet.getRow(0)){
                if(getCellData(cell).equals(columnName)){
                    break;
                }
                index++;
            }
            for(Row row : sheet){
                if(check){
                    check = false;
                }else{
                    columnData.add(getCellData(row.getCell(index)));
                }
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet name not valid");
        }
        return columnData;
    }

    /**
     * Gets the values column wise for a particular column name for a particular sheet number
     * @param columnName
     * @param sheetNumber
     * @return list of values for that column header
     * @exception throw exception when the cell is not valid of sheet name is not valid
     */
    public ArrayList<String> readSheetUsingColumnAsHeader(int sheetNumber,String columnName) throws Exception {
        ArrayList<String> columnData = new ArrayList<String>();
        boolean check = true;
        int index = 0;
        try {
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            for (Cell cell : sheet.getRow(0)) {
                if (getCellData(cell).equals(columnName)) {
                    break;
                }
                index++;
            }
            for (Row row : sheet) {
                if (check) {
                    check = false;
                } else {
                    columnData.add(getCellData(row.getCell(index)));
                }
            }
        }catch (IllegalArgumentException e)
        {
            throw new Exception("Sheet number not valid");
        }
        return columnData;
    }

    /**
     * Gets the values column wise for a particular column number and sheet name
     * @param sheetName
     * @param columnNumber
     * @return list of values for that column header
     * @exception throw exception when the cell is not valid of sheet name is not valid
     */
    public ArrayList<String> readSheetUsingColumnAsHeader(String sheetName, int columnNumber) throws Exception {
        ArrayList<String> columnData = new ArrayList<String>();
        boolean check = true;
        int index = 0;
        try {
            Sheet sheet = workbook.getSheet(sheetName);
            for (Row row : sheet) {
                if (check) {
                    check = false;
                } else {
                    columnData.add(getCellData(row.getCell(columnNumber)));
                }
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet name not valid");
        }catch (NullPointerException e){
            throw new Exception("Column not valid");
        }
        return columnData;
    }

    /**
     * Gets the values column wise for a particular column number and sheet name
     * @param sheetNumber
     * @param columnNumber
     * @return list of values for that column header
     * @exception throw exception when the cell is not valid of sheet name is not valid
     */
    public ArrayList<String> readSheetUsingColumnAsHeader(int sheetNumber, int columnNumber) throws Exception {
        ArrayList<String> columnData = new ArrayList<String>();
        boolean check = true;
        int index = 0;
        try{
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            for(Row row : sheet){
                if(check) {
                    check = false;
                }else{
                    columnData.add(getCellData(row.getCell(columnNumber)));
                }
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet number not valid");
        }catch (NullPointerException e){
            throw new Exception("Column not valid");
        }
        return columnData;
    }

    /**
     * Gets the values of row wise for all sheets in the file
     * @return HasMap of sheet name , row header and values {Sheet name : {Row header : [values] }}
     * @exception if cell is not valid
     */
//    public HashMap<String,HashMap<String,ArrayList<String>>> readAllSheetUsingRowAsHeader()throws Exception{
//        HashMap<String,HashMap<String,ArrayList<String>>> fileData = new HashMap<String,HashMap<String,ArrayList<String>>>();
//        HashMap<String,ArrayList<String>> rowMap ;
//        ArrayList<String> rowData;
//        boolean check = true;
//        String key = null;
//        for(Sheet sheet : workbook){
//            rowMap = new HashMap<String,ArrayList<String>>();
//            for(Row row: sheet){
//                rowData = new ArrayList<String>();
//                check = true;
//                for(Cell cell : row){
//                    if(check){
//                        check = false;
//                    }else{
//                        rowData.add(getCellData(cell));
//                    }
//                }
//                rowMap.put(getCellData(row.getCell(0)),rowData);
//            }
//            fileData.put(sheet.getSheetName(),rowMap);
//        }
//        return fileData;
//    }
    /**
     * Gets the values of row wise for the current sheet
     * @return HasMap of row header and list of values {Row header : [values] }
     * @exception if cell is not valid
     */
    public HashMap<String,ArrayList<String>> readSheetUsingRowAsHeader()throws Exception{
        HashMap<String,ArrayList<String>> rowMap = new HashMap<String,ArrayList<String>>();
        ArrayList<String> rowData;
        Sheet sheet = currentSheet;
        boolean check = true;
        for(Row row: sheet){
            rowData = new ArrayList<String>();
            check = true;
            for(Cell cell : row){
                if(check){
                    check = false;
                }else{
                    rowData.add(getCellData(cell));
                }
            }
            rowMap.put(getCellData(row.getCell(0)),rowData);
        }
        return rowMap;
    }

    /**
     * Gets the values of row wise for the given sheet name
     * @param sheetName as String
     * @return HasMap of row as header with list of values of that row {row header : [values] }
     * @exception if the cell or sheet name is not valid
     */
    public HashMap<String,ArrayList<String>> readSheetUsingRowAsHeader(String sheetName) throws Exception {
        HashMap<String,ArrayList<String>> rowMap = new HashMap<String,ArrayList<String>>();
        ArrayList<String> rowData;
        try {
            Sheet sheet = workbook.getSheet(sheetName);
            boolean check = true;
            for (Row row : sheet) {
                rowData = new ArrayList<String>();
                check = true;
                for (Cell cell : row) {
                    if (check) {
                        check = false;
                    } else {
                        rowData.add(getCellData(cell));
                    }
                }
                rowMap.put(getCellData(row.getCell(0)), rowData);
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet name is not valid");
        }
        return rowMap;
    }


    /**
     * Gets the values of row wise for the given sheet number
     * @param sheetNumber
     * @return HasMap of row as header with list of values of that row {row header : [values] }
     * @exception if the cell or sheet number is not valid
     */
    public HashMap<String,ArrayList<String>> readSheetUsingRowAsHeader(int sheetNumber) throws Exception {
        HashMap<String,ArrayList<String>> rowMap = new HashMap<String,ArrayList<String>>();
        ArrayList<String> rowData;
        try {
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            boolean check = true;
            for (Row row : sheet) {
                rowData = new ArrayList<String>();
                check = true;
                for (Cell cell : row) {
                    if (check) {
                        check = false;
                    } else {
                        rowData.add(getCellData(cell));
                    }
                }
                rowMap.put(getCellData(row.getCell(0)), rowData);
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet number not valid");
        }
        return rowMap;
    }

    /**
     * Gets the values of row wise for the given sheet number and row header
     * @param sheetNumber as Integer
     * @param rowName as String
     * @return list of values of that row
     * @exception if the cell or sheet number is not valid
     */
    public ArrayList<String> readSheetUsingRowAsHeader(int sheetNumber ,String rowName) throws Exception{
        ArrayList<String> rowData = new ArrayList<String>();
        try {
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            boolean check = true;
            for (Row row : sheet) {
                if (getCellData(row.getCell(0)).equals(rowName)) {
                    for (Cell cell : row) {
                        if (check) {
                            check = false;
                        } else {
                            rowData.add(getCellData(cell));
                        }
                    }
                    break;
                }
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet number not valid");
        }
        return rowData;
    }

    /**
     * Gets the values of row wise for the given sheet name and rowName
     * @param sheetName as String
     * @param rowName as String
     * @return list of values of that row
     * @exception if the cell or sheet name is not valid
     */
    public ArrayList<String> readSheetUsingRowAsHeader(String sheetName ,String rowName) throws Exception {
        ArrayList<String> rowData = new ArrayList<String>();
        try {
            Sheet sheet = workbook.getSheet(sheetName);
            boolean check = true;
            for (Row row : sheet) {
                if (getCellData(row.getCell(0)).equals(rowName)) {
                    for (Cell cell : row) {
                        if (check) {
                            check = false;
                        } else {
                            rowData.add(getCellData(cell));
                        }
                    }
                    break;
                }
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet name not valid");
        }
        return rowData;
    }



    /**
     * Gets the values of row wise for the given sheet name and rowNumber
     * @param sheetName as String
     * @param rowNumber as integer
     * @return list of values of that row
     * @exception if the cell or sheet name is not valid
     */
    public ArrayList<String> readSheetUsingRowAsHeader(String sheetName ,int rowNumber) throws Exception {
        ArrayList<String> rowData = new ArrayList<String>();
        try {
            Sheet sheet = workbook.getSheet(sheetName);
            boolean check = true;
            for (Cell cell : sheet.getRow(rowNumber)) {
                if (check) {
                    check = false;
                } else {
                    rowData.add(getCellData(cell));
                }
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet name not valid");
        }catch (NullPointerException e){
            throw new Exception("Row number not valid");
        }
        return rowData;
    }

    /**
     * Gets the values of row wise for the given sheet number and row number
     * @param sheetNumber as Integer
     * @param rowNumber as integer
     * @return list of values of that row
     * @exception if the cell or sheet name is not valid
     */
    public ArrayList<String> readSheetUsingRowAsHeader(int sheetNumber ,int rowNumber) throws Exception {
        ArrayList<String> rowData = new ArrayList<String>();
        try {
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            boolean check = true;
            for (Cell cell : sheet.getRow(rowNumber)) {
                if (check) {
                    check = false;
                } else {
                    rowData.add(getCellData(cell));
                }
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet number is not valid");
        }catch (NullPointerException e){
            throw new Exception("Row number not valid");
        }
        return rowData;
    }

    /**
     * Verify if the row number is valid or not
     * @param activeSheet sheet
     * @param rowNumber as integer
     * @return true if valid and vice versa
     */
    private boolean validateRow(Sheet activeSheet,int rowNumber){
        try{
            activeSheet.getRow(rowNumber);
        }catch(NullPointerException e){
            return false;
        }
        return true;
    }

    /**
     * verify if the cell is valid or not
     * @param activeSheet sheet
     * @param rowNumber as integer
     * @param columnNumber as integer
     * @return true if valid and vice versa
     */
    private boolean validateCell(Sheet activeSheet,int rowNumber , int columnNumber){
        try{
            activeSheet.getRow(rowNumber).getCell(columnNumber);
        }catch(NullPointerException e){
            return false;
        }
        return true;
    }

    /**
     * Utility to append and save the file
     * @throws IOException
     */
    private  void writeAndSave() throws IOException {
        fileOutputStream = new FileOutputStream(fileLocation);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    /**
     * Sets the cell value for current sheet
     * @param rowNumber as integer
     * @param columnNumber as integer
     * @param value as string
     * @throws IOException
     */
    public void setCellData(int rowNumber,int columnNumber , String value) throws IOException {
        Sheet sheet = currentSheet;
        if(!validateCell(sheet,rowNumber,columnNumber)) {
            sheet.createRow(rowNumber).createCell(columnNumber).setCellValue(value);
            writeAndSave();
        }else{
            sheet.getRow(rowNumber).createCell(columnNumber).setCellValue(value);
            writeAndSave();
        }
    }

    /**
     * Sets the value of the given cell for given sheet name
     * @param sheetName as string
     * @param rowNumber as integer
     * @param columnNumber as integer
     * @param value as string
     * @throws Exception
     */
    public void setCellData(String sheetName, int rowNumber, int columnNumber , String value) throws Exception {
        try {
            Sheet sheet = workbook.getSheet(sheetName);
            if (!validateCell(sheet, rowNumber, columnNumber)) {
                sheet.createRow(rowNumber).createCell(columnNumber).setCellValue(value);
                writeAndSave();
            } else {
                sheet.getRow(rowNumber).createCell(columnNumber).setCellValue(value);
                writeAndSave();
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet name not valid");
        }
    }


    /**
     * Sets the value of the given cell for a given sheet number
     * @param sheetNumber as sheet number
     * @param rowNumber as integer
     * @param columnNumber as integer
     * @param value as string
     * @throws IOException
     */
    public void setCellData(int sheetNumber, int rowNumber, int columnNumber , String value) throws Exception {
        try {
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            if (!validateCell(sheet, rowNumber, columnNumber)) {
                sheet.createRow(rowNumber).createCell(columnNumber).setCellValue(value);
                writeAndSave();
            } else {
                sheet.getRow(rowNumber).createCell(columnNumber).setCellValue(value);
                writeAndSave();
            }
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet number not valid");
        }
    }

    /**
     * Set the sheet name for a given sheet number
     * @param sheetNumber as integer
     * @param sheetName as string
     * @throws Exception
     */
    public void setSheetName(int sheetNumber , String sheetName) throws Exception {
        try {
            workbook.setSheetName(sheetNumber, sheetName);
            writeAndSave();
        }catch (IllegalArgumentException e){
            throw new Exception("Sheet number not valid");
        }
    }

    /**
     * Add a new sheet to the file with sheet name
     * @param sheetName as String
     * @throws Exception for same sheet name or failed io exception
     */
    public void addSheet(String sheetName) throws Exception {
        try{
            workbook.createSheet(sheetName);
            writeAndSave();
        }catch (IllegalArgumentException e) {
            throw new Exception("Sheet name already exist.");
        }
    }


    /**
     * Add a new sheet to the file
     * @throws Exception if read write fail
     */
    public void addSheet() throws Exception {
        try {
            workbook.createSheet();
            writeAndSave();
        }catch(IllegalArgumentException e){
            throw new Exception("Unable to create sheet.");
        }
    }

}
