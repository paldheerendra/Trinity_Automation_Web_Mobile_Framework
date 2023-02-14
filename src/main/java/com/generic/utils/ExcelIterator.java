package com.generic.utils;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * custom Iterator for excel file
 * @author Dheerendra Singh
 */
public class ExcelIterator 
{
	private String flFile;
	private int intSheetNumber;
	private int intIndex = 0;
	private int intNoOfRow;
	private int intNoOfColumn;
	private XSSFSheet sheet;
	private XSSFWorkbook workbook;
	private FileInputStream excelFileIS;

	public ExcelIterator(String strExcelFile, int intSheetno, 
			boolean ignoreHeaderRow) 
	{
		flFile = strExcelFile;
		intSheetNumber = intSheetno;
		try {
			excelFileIS = new FileInputStream(flFile);
			//workbook = new XSSFWorkbook(new File(flFile));
			workbook = new XSSFWorkbook(excelFileIS);
			excelFileIS.close();
			sheet = workbook.getSheetAt(intSheetNumber);
			intNoOfRow = sheet.getPhysicalNumberOfRows();
			if(ignoreHeaderRow)
				intIndex++;
		}catch(Exception exception) {
			exception.printStackTrace();
		} 
	}

	public boolean isDone() 
	{
		if(intIndex < intNoOfRow) 
			return true;
		else 
			return false;
	}

	public String getColumn(int clmNo)
	{
		String strCellvalue = "";
		Row row = null;
		Cell cell = null;
		row = sheet.getRow(intIndex);
		intNoOfColumn = row.getPhysicalNumberOfCells();
		try
		{
			cell = row.getCell(clmNo, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (cell == null)
				strCellvalue = "";
			else 
				strCellvalue = cell.toString().trim();
			strCellvalue = cell.toString();
			return strCellvalue;
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return strCellvalue;
	}

	/** method return cell string content based on header row(0)
	 *  but method did not work if ignoreHeaderRow = true 
	 */
	public String getColumn(String columnHeader)
	{
		Row headerRow = sheet.getRow(0);
		Row testDataRow = sheet.getRow(this.getCurrentRowNumber());
		int clmNo = 0;
		String cellValue = "";
		try
		{
			do
			{
				String currentHeader = "";
				Cell headerCell = headerRow.getCell(clmNo, MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (headerCell == null)
					currentHeader = "";
				else 
					currentHeader = headerCell.toString().trim();
				if(currentHeader.equalsIgnoreCase(columnHeader))
				{
					Cell testDataCell = testDataRow.getCell(clmNo, MissingCellPolicy.RETURN_BLANK_AS_NULL);

					if (testDataCell == null)
						cellValue = "";
					else 
						cellValue = testDataCell.toString().trim();
				}
				clmNo++;
			}
			while(clmNo < headerRow.getLastCellNum());
		}
		catch(Exception exception)
		{
		}
		return cellValue;
	}

	public void next()
	{
		intIndex++;
	}

	public int getCurrentRowNumber()
	{
		return intIndex;
	}

	public int getNumberOfRows()
	{
		return intNoOfRow; 
	}

	public int getNumberOfColumnForCurrentRow()
	{
		return intNoOfColumn;
	}

	public void test() {
		
	}


}

/********* Usage Example ********
 * 
 	ExcelIterator objExcelIterator = new ExcelIterator("file path", 0, true);
	while(objExcelIterator.isDone()) // iterator over all rows within excel file
	{
		System.out.println("if user want particular cell content from current row based on index use--" + 
					objExcelIterator.getColumn(0));
		
		// note method getColumn(String) works only if ignoreHeaderRow = true 
		System.out.println("if user want particular cell content from current row based on index header" + 
					objExcelIterator.getColumn("Header"));
	 	
		// if user want to print all cell contents within row use following
	 	int clmNo = 0;
		do{
	 		System.out.println(objExcelIterator.getColumn(clmNo));
			clmNo++;
		}while(clmNo < objExcelIterator.getNumberOfColumnForCurrentRow());
	
		objExcelIterator.next(); // control moves to next row
	}
 */
