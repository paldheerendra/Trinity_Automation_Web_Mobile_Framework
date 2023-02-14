package com.generic.utils;

import java.util.Properties;

/**
 * Expleo custom reporter
 * @author Dheerendra Singh
 */
public class CustomReporter extends CustomReporterHelper {
	private Properties objCustomReporterConfig;
	private String printExcelReport = "";
	private String printPDFReport = "";
	private String printHTMLReport = "";

	/**
	 * Start report as per report configuration properties
 	 * @param scriptName - Running script name
	 */
	public void startReport(String scriptName,String suiteName) {
		try {
			objCustomReporterConfig = loadCustomConfigFile(scriptName.substring(scriptName.lastIndexOf(".") + 1));
			printExcelReport = objCustomReporterConfig.getProperty("custom.reports.excel").trim();
			printPDFReport = objCustomReporterConfig.getProperty("custom.reports.pdf").trim();
			printHTMLReport = objCustomReporterConfig.getProperty("custom.reports.html").trim();

			if (printExcelReport.equalsIgnoreCase("true"))
				startExcelReport(suiteName);
			if (printPDFReport.equalsIgnoreCase("true"))
				startPDFReport();
			if (printHTMLReport.equalsIgnoreCase("true"))
				startHTMLReport();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Add pass step
	 * @param stepDesc  - Step description
	 * @param inputValue  - Input value
	 * @param exceptedValue  - Expected value (for verification point)
	 * @param actualValue - Actual value (for verification point)
	 */
	public void pass(String stepDesc, String inputValue, String exceptedValue, String actualValue) {
		try {
			if (printExcelReport.equalsIgnoreCase("true"))
				addExcelPassStep(stepDesc, inputValue, exceptedValue, actualValue);
			if (printPDFReport.equalsIgnoreCase("true"))
				addPDFPassStep(stepDesc, inputValue, exceptedValue, actualValue);
			if (printHTMLReport.equalsIgnoreCase("true"))
				addHTMLPassStep(stepDesc, inputValue, exceptedValue, actualValue);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Add fail step 
	 * @param stepDesc - Step description
	 * @param inputValue - Input value
	 * @param exceptedValue - Expected value (for verification point)
	 * @param actualValue  - Actual value (for verification point)
	 */
	public void fail(String stepDesc, String inputValue, String exceptedValue, String actualValue) {
		try {
			if (printExcelReport.equalsIgnoreCase("true"))
				addExcelFailStep(stepDesc, inputValue, exceptedValue, actualValue);
			if (printPDFReport.equalsIgnoreCase("true"))
				addPDFFailStep(stepDesc, inputValue, exceptedValue, actualValue);
			if (printHTMLReport.equalsIgnoreCase("true"))
				addHTMLFailStep(stepDesc, inputValue, exceptedValue, actualValue);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Add info step
 	 * @param stepDesc - Step description
	 * @param infoMessage - Info message
	 */
	public void info(String stepDesc, String infoMessage) {
		try {
			if (printExcelReport.equalsIgnoreCase("true"))
				addExcelInfoStep(stepDesc, infoMessage);
			if (printPDFReport.equalsIgnoreCase("true"))
				addPDFInfoStep(stepDesc, infoMessage);
			if (printHTMLReport.equalsIgnoreCase("true"))
				addHTMLInfoStep(stepDesc, infoMessage);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * end report
	 */
	public void endReport() {
		try {
			if (printExcelReport.equalsIgnoreCase("true"))
				endExcelReport();
			if (printPDFReport.equalsIgnoreCase("true"))
				endPDFReport();
			if (printHTMLReport.equalsIgnoreCase("true"))
				endHTMLReport();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}