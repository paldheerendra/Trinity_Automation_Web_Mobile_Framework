package com.generic.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Loading test data for test case
 * @author Dheerendra Singh
 */
public class DataPool {
	private XSSFSheet sheet;
	private XSSFWorkbook workbook;
	private FileInputStream excelFileIS;
	private FormulaEvaluator evaluator;

	/**
	 * Load Data from Excel for the running testCase and return as Object array
	 * @author Dheerendra Singh
	 */
	public Object[][] loadTestData(String testCaseID, String testDataFilePath, String... sheetName) 
	{
		ArrayList<Hashtable<String, String>> hashTableList = new ArrayList<Hashtable<String, String>>();
		Object[][] objDataProvider = null;

		try 
		{
			excelFileIS = new FileInputStream(testDataFilePath);
			workbook = new XSSFWorkbook(excelFileIS);
			evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			if(sheetName != null && sheetName.length > 0)
				sheet = workbook.getSheet(sheetName[0]);
			else
				sheet = workbook.getSheetAt(0);

			// Fetch number of test data rows
			ArrayList<Integer> testDataRows = new ArrayList<Integer>();
			for (Row row : sheet) {
				String cellData = this.getCellValue(row, 0);
				if (cellData.equalsIgnoreCase(testCaseID)) {
					testDataRows.add(row.getRowNum());
				}
			}

			// Construct hash table with header and data values   
			for (int testDataRowNo : testDataRows) {
				Hashtable<String, String> dataValueSet = new Hashtable<String, String>();
				Row testDataRow = sheet.getRow(testDataRowNo);
				Row headerRow = null;
				int headerCounter = 1;
				boolean flagHederRow =  false;
				do {
					headerRow = sheet.getRow(testDataRowNo - headerCounter);
					if(this.getCellValue(headerRow, 0).equalsIgnoreCase("TestCaseId"))
						flagHederRow = true;
					headerCounter++;
				}while(!flagHederRow);

				int cellCount = 0;
				for (@SuppressWarnings("unused") Cell cell : headerRow) {
					String header = this.getCellValue(headerRow, cellCount).toLowerCase().trim();
					//System.out.println("header--->" + header);
					String testData = this.getCellValue(testDataRow, cellCount).trim();
					//System.out.println("testData--->" + testData);
					if (!header.equals(""))
						dataValueSet.put(header, testData);
					cellCount++;
				}
				hashTableList.add(dataValueSet);
			}

			objDataProvider = new Object[hashTableList.size()][1];
			int rowCount = 0;
			for (Hashtable<String, String> hashTable : hashTableList) {
				objDataProvider[rowCount][0] = hashTable;
				rowCount++;
			}

			workbook.close();
			excelFileIS.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			hashTableList = null;
			try {
				workbook.close();
				excelFileIS.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return objDataProvider;
	}

	/**
	 * @Method : updateTestDataSheet(String testDataFile, String testCaseID,
	 *         String runID,Hashtable<String, String> testDataToUpdate)
	 * @Description : Load Data from Excel for the running testCase and return
	 *              as Object array
	 * @author Dheerendra Singh
	 */
	public void updateTestDataSheet(String testDataFilePath, String testCaseID,
			Hashtable<String, String> testDataToUpdate, String... sheetName) {
		try {
			//System.out.println("testDataFilePath--------->" + testDataFilePath);
			//System.out.println("testCaseID--------->" + testCaseID);
			//System.out.println("testDataToUpdate--------->" + testDataToUpdate.get("UserName"));

			testDataToUpdate = this.deepCopy(testDataToUpdate);
			Set<String> keys = testDataToUpdate.keySet();
			for(String key: keys){
				System.out.println("Value of "+key+" is: "+testDataToUpdate.get(key));
			}

			excelFileIS = new FileInputStream(testDataFilePath);
			workbook = new XSSFWorkbook(excelFileIS);
			//evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			if(sheetName != null && sheetName.length > 0)
				sheet = workbook.getSheet(sheetName[0]);
			else
				sheet = workbook.getSheetAt(0);

			// Fetch number of test data rows
			ArrayList<Integer> testDataRows = new ArrayList<Integer>();
			for (Row row : sheet) {
				String cellData = this.getCellValueWithoutEvaluator(row, 0);
				if (cellData.equalsIgnoreCase(testCaseID)) {
					testDataRows.add(row.getRowNum());
				}
			}

			// Construct hash table with header and data values   
			for (int testDataRowNo : testDataRows) {
				Row testDataRow = sheet.getRow(testDataRowNo);
				Row headerRow = null;
				int headerCounter = 1;
				boolean flagHederRow =  false;
				do {
					headerRow = sheet.getRow(testDataRowNo - headerCounter);
					if(this.getCellValueWithoutEvaluator(headerRow, 0).equalsIgnoreCase("TestCaseId"))
						flagHederRow = true;
					headerCounter++;
				}while(!flagHederRow);

				int cellCount = 0;
				for (@SuppressWarnings("unused") Cell cell : headerRow) {
					String header = this.getCellValueWithoutEvaluator(headerRow, cellCount).toLowerCase().trim();
					//System.out.println("--->" + header);
					if (testDataToUpdate.containsKey(header.toLowerCase()))
						this.setCellValue(testDataRow, cellCount, testDataToUpdate.get(header));
					cellCount++;
				}
			}

			OutputStream fileOut = new FileOutputStream(testDataFilePath);  
			workbook.write(fileOut);

			workbook.close();
			excelFileIS.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				workbook.close();
				excelFileIS.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get Cell value for given cell (Used in loadDataProvider)
	 * @author Dheerendra Singh
	 */
	private String getCellValue(Row testDataRow, int columnNumber) {
		//System.out.println("columnNumber-------->" + columnNumber);
		if (testDataRow == null)
			return "";
		else {
			CellType cellType = testDataRow.getCell(columnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellTypeEnum();
			switch (cellType) {
			case FORMULA:
				evaluator.evaluateFormulaCellEnum(testDataRow.getCell(columnNumber, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL));
			default:
				break;
			}
			testDataRow.getCell(columnNumber).setCellType(CellType.STRING);
			Cell testDataCell = testDataRow.getCell(columnNumber, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (testDataCell == null)
				return "";
			else
				return testDataCell.getStringCellValue();
		}
	}

	/**
	 * Get Cell value for given cell (Used in loadDataProvider)
	 * @author Dheerendra Singh
	 */
	private String getCellValueWithoutEvaluator(Row testDataRow, int columnNumber) {
		if (testDataRow == null)
			return "";
		else {
			testDataRow.getCell(columnNumber).setCellType(CellType.STRING);
			Cell testDataCell = testDataRow.getCell(columnNumber,  Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (testDataCell == null)
				return "";
			else
				return testDataCell.getStringCellValue();
		}
	}

	/**
	 * Set Cell value for given cell (Used in loadDataProvider)
	 * @author Dheerendra Singh
	 */
	private boolean setCellValue(Row testDataRow, int columnNumber, String testData) {
		if (testDataRow != null) {
			Cell testDataCell = testDataRow.getCell(columnNumber,  Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (testDataCell != null) {
				testDataCell.setCellValue(testData);
				return true;
			}
		}
		return false;
	}

	private Hashtable<String, String> deepCopy(Hashtable<String, String> original) {
		Hashtable<String, String> copy = new Hashtable<String, String>(original.size());
		Set<String> keys = original.keySet();
		for(String key: keys){
			copy.put(key.toLowerCase(), original.get(key));
		}
		return copy;
	}
}