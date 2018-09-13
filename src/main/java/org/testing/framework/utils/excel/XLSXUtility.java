package org.testing.framework.utils.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XLSXUtility {

    static org.slf4j.Logger LOG = LoggerFactory.getLogger(XLSXUtility.class.getName());

	private int numberOfRows = -1;
	private int numberOfColumns = -1;

	private XSSFWorkbook wb = null;
	private File file = null;
	private FileOutputStream output = null;

	private boolean sheetSetWithIndex;
	private Integer sheetIndex;
	private String sheetName;
	private XSSFSheet sheet = null;
	private List<String> header = null;

	public void setFile(File file) {
		this.file = file;
		try {
			wb = new XSSFWorkbook(new FileInputStream(file));
			LOG.info("Reading file " + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setSheet(int index) throws FileNotFoundException {
		sheetSetWithIndex = true;
		sheetIndex = index;
		sheet = wb.getSheetAt(index);
		if (sheet == null)
			throw new FileNotFoundException("Specified Sheet not found in Excel File");
		numberOfRows = getRowCount();
		numberOfColumns = getColumnCount();
		LOG.info(new StringBuilder("Reading Sheet at index").append(index).toString());
		header = getRowData(0);
	}

	public void setSheet(String sheetName) throws FileNotFoundException {
		sheetSetWithIndex = false;
		this.sheetName = sheetName;
		sheet = wb.getSheet(sheetName);
		if (sheet == null)
			throw new FileNotFoundException("Specified Sheet not found in Excel File");
		numberOfRows = getRowCount();
		numberOfColumns = getColumnCount();

		LOG.info(new StringBuilder("Reading Sheet ").append(sheetName).toString());
		header = getRowData(0);
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public List<String> getHeader() {
		return header;
	}

	public XSSFSheet getSheet() {
		return sheet;
	}

	/**
	 * This method will count date in first column to check for the cell which has null i.e,. end of the column date
	 *
	 * @return number of rows in the sheet
	 */
	private int getRowCount() {
		int numberOfRows = 0;
		XSSFRow row = null;
		boolean notNull = true;

		while (notNull) {
			row = sheet.getRow(numberOfRows);
			try {
				if (row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK)
					numberOfRows++;
			} catch (Exception e) {
				notNull = false;
			}
		}

		return numberOfRows;
	}

	private int getColumnCount() {
		int numberOfColumns = 0;
		boolean notNull = true;

		XSSFRow rowData = sheet.getRow(0);

		while (notNull) {
			try {
				if (rowData.getCell(numberOfColumns).getCellType() != Cell.CELL_TYPE_BLANK) {
					numberOfColumns++;
				} else {
					notNull = false;
				}
			} catch (Exception e) {
				notNull = false;
			}
		}

		return numberOfColumns;
	}


	/**
	 * This method is used to fetch date of a row whose index is passed as parameter
	 *
	 * @param rowIndex
	 * @return Data of entire row
	 * @throws
	 */
	public ArrayList<String> getRowData(int rowIndex) {
		XSSFRow rowData = sheet.getRow(rowIndex);
		XSSFCell cell = null;

		ArrayList<String> rowDataArray = new ArrayList<String>();

		for (int i = 0; i < numberOfColumns; i++) {
			cell = rowData.getCell(i, XSSFRow.CREATE_NULL_AS_BLANK);
			rowDataArray.add(getCellValue(cell).trim());
		}
		return rowDataArray;
	}


	/**
	 * This method is used to fetch date of a column whose index is passed as parameter
	 *
	 * @param coulumnIndex
	 * @return Data of entire column
	 * @throws
	 */
	public ArrayList<String> getColumnData(int coulumnIndex) {

		ArrayList<String> columnDataArray = new ArrayList<String>();
		XSSFRow row = null;
		XSSFCell cell = null;
		for (int r = 0; r < numberOfRows; r++) {
			row = sheet.getRow(r);
			cell = row.getCell(coulumnIndex);
			columnDataArray.add(getCellValue(cell).trim());
		}

		return columnDataArray;
	}

	public void write(Integer rowIndex, Integer columnIndex, String value) {
		XSSFRow rowData = sheet.getRow(rowIndex);

		Cell cell = rowData.getCell(columnIndex, XSSFRow.CREATE_NULL_AS_BLANK);
		cell.setCellValue(value);
		try {
			output = new FileOutputStream(file);
			wb.write(output);
			output.close();
			reloadAndSetSheet();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getCellValue(Cell cell) {
		String message = null;
		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
			message = String.valueOf(cell.getBooleanCellValue());
		else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			message = String.valueOf(cell.getNumericCellValue());
		else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA)
			message = (String.valueOf(cell.getCellFormula()));
		else if (cell.getCellType() == Cell.CELL_TYPE_STRING)
			message = cell.getStringCellValue();
		else if (cell.getCellType() == Cell.CELL_TYPE_ERROR)
			message = String.valueOf(cell.getErrorCellValue());
		else
			message = "";
		return message;
	}

	public void write() {
		try {
			output = new FileOutputStream(file);
			wb.write(output);
			output.close();
			reloadAndSetSheet();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void reloadAndSetSheet() {
		// Reload the workbook, workaround for bug 49940
		// https://issues.apache.org/bugzilla/show_bug.cgi?id=49940
		try {
			wb = new XSSFWorkbook(new FileInputStream(file.getAbsolutePath()));
			if (sheetSetWithIndex) setSheet(sheetIndex);
			else setSheet(sheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void removeRow(){
		int rowCount=getRowCount();
		for(int i=0;i<rowCount;i++){
			XSSFRow row=sheet.getRow(i);
			if(row==null){
				continue;
			}
			sheet.removeRow(row );
		}
	}

	public void removeColumnRow(){
		
		int rowCount=getRowCount();
		for(int i=0;i<rowCount;i++){
			XSSFRow row=sheet.getRow(i);
			if(row==null){
				continue;
			}
			
		}
	}
}

