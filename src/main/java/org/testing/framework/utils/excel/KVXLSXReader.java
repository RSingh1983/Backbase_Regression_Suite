package org.testing.framework.utils.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class KVXLSXReader {

	private XLSXUtility xlsxUtility;

	public KVXLSXReader(File file) {
		xlsxUtility = new XLSXUtility();
		xlsxUtility.setFile(file);
	}


	public void setSheet(String sheetName,int index){

			try {
				if(sheetName!=null)
					xlsxUtility.setSheet(sheetName);
				else
					xlsxUtility.setSheet(index);
			} catch (FileNotFoundException e) {
			
				e.printStackTrace();
			}
		
	}

	public List<String> getHeaders(String sheetName,int index) {
		setSheet(sheetName,index);
		return xlsxUtility.getHeader();
	}

	public  List<Map<String, String>> readSheetData(String sheetName){
		
		try{
			xlsxUtility.setSheet(sheetName);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return getSheetData();
		
	}
	public  List<Map<String, String>> readSheetData(int  sheetIndex){
		try{
		xlsxUtility.setSheet(sheetIndex);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return getSheetData();
	}
	public synchronized List<Map<String, String>> readSheetData(String sheetName, Integer sheetIndex) {
		setSheet(sheetName, sheetIndex);
		return getSheetData();
	}

	public List<Map<String, String>> getSheetData(){
		List<Map<String, String>> mapSheet = new ArrayList<Map<String, String>>();
		Map<String, String> mapRowData = new LinkedHashMap<String, String>();
		List<String> rowData = null;

		try {
			for (int i = 1; i < xlsxUtility.getNumberOfRows(); i++) {
				rowData = xlsxUtility.getRowData(i);
				Iterator<String> iterateHeader = xlsxUtility.getHeader().iterator();
				Iterator<String> iterateRowData = rowData.iterator();
				while (iterateHeader.hasNext() && iterateRowData.hasNext()){
					mapRowData.put(iterateHeader.next(), iterateRowData.next());
				}
				mapSheet.add(new LinkedHashMap<String, String>((LinkedHashMap<String, String>) mapRowData));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapSheet;

	}


}
