package com.generic;

import java.util.Hashtable;

import com.generic.appiumDriver.AppiumDriverProvider;
import com.generic.appiumDriver.AppiumManager;
import com.generic.logger.Log4JLogger;
import com.generic.utils.Configuration;
import com.generic.utils.CustomReporter;
import com.generic.utils.DataPool;
import com.generic.utils.NetworkMonitor;
import com.generic.utils.RunTimeDataHolder;
import com.generic.utils.WaitMethods;
import com.generic.utils.WebDomains;
import com.generic.webDriver.WebDriverProvider;

/**
 * Getter setter for all object 
 * @author Dheerendra Singh
 */
public class Pojo 
{	
	// Local variables
	private Configuration objConfiguration;
	private WaitMethods objWaitMethods;
	private WebDriverProvider objWebDriverProvider;
	private WebDomains objWebDomains;
	private WebActions objWebActions;
	private MobileActions objMobileActions;
	private CustomReporter objCustomReporter;
 	private LogReporter objLogReporter;
	private AppiumDriverProvider objAppiumDriverProvider;
	private AppiumManager objAppiumManager;
	private NetworkMonitor objNetworkMonitor;
	private RunTimeDataHolder objRunTimeDataHolder;
	private Log4JLogger objLog4JLogger;

	private String strTestDataFilePath = "";
	private String testCaseID = "";
	private Hashtable<String, String> dataPoolHashTable;
	private String runID = "";
	private String testCaseTitle = "";
	private String testCaseDescription = "";
	private String suiteName = "";

	// Getter Setter for Config object instance
	public void setConfiguration(Configuration configuration){
		this.objConfiguration = configuration;
	}
	public Configuration getConfiguration(){
		return objConfiguration;
	}

	// Getter Setter for Wrapper function object instance
	public void setWaitMethods(WaitMethods waitMethods){
		this.objWaitMethods = waitMethods;
	}
	public WaitMethods getWaitMethods(){
		return objWaitMethods;
	}

	// Getter Setter for WebDriver object instance
	public void setWebDriverProvider(WebDriverProvider webDriverProvider){
		this.objWebDriverProvider = webDriverProvider;
	}
	public WebDriverProvider getWebDriverProvider(){
		return objWebDriverProvider;
	}

	// Getter Setter for WebDriver object instance
	public void setNetworkMonitor(NetworkMonitor networkMonitor){
		this.objNetworkMonitor = networkMonitor;
	}
	public NetworkMonitor getNetworkMonitor(){
		return objNetworkMonitor;
	}

	// Getter Setter for WebDomains object instance
	public void setWebDomains(WebDomains webDomains){
		this.objWebDomains = webDomains;
	}
	public WebDomains getWebDomains(){
		return objWebDomains;
	} 

	// Getter Setter for Utilities object instance
	public void setLogReporter(LogReporter objLogReporter) {
		this.objLogReporter = objLogReporter;
	}
	public LogReporter getLogReporter() {
		return objLogReporter;
	}

	// Getter Setter for WrapperFunctions object instance
	public void setWebActions(WebActions webActions) {
		this.objWebActions = webActions;
	}
	public WebActions getWebActions() {
		return objWebActions;
	}

	// Getter Setter for WrapperFunctions object instance
	public void setMobileActions(MobileActions mobileActions) {
		this.objMobileActions = mobileActions;
	}
	public MobileActions getMobileActions() {
		return objMobileActions;
	}

	// Getter Setter for test case id
	public void setTestCaseID(String testCaseID) {
		this.testCaseID = testCaseID;
	}
	public String getTestCaseID() {
		return testCaseID;
	}

	// Getter Setter for test data file path
	public void setTestDataFilePath(String strTestDataFilePath) {
		this.strTestDataFilePath = strTestDataFilePath;
	}
	public String getTestDataFilePath() {
		return strTestDataFilePath;
	}

	// Getter Setter for run id
	public void setRunID(String runID) {
		this.runID = runID;
	}
	public String getRunID() {
		return runID;
	}

	// Getter Setter for Data Pool HashTable 
	public void setDataPoolHashTable(Hashtable<String, String> dataPoolHashTable) {
		this.dataPoolHashTable = dataPoolHashTable;
	}
	public Hashtable<String, String> getDataPoolHashTable() {
		return dataPoolHashTable;
	}

	// Getter Setter for Data Pool HashTable 
	public void setCustomReporter(CustomReporter customReporter) {
		this.objCustomReporter = customReporter;
	}
	public CustomReporter getCustomReporter() {
		return objCustomReporter;
	}
 
	// Getter Setter for test case id
	public void setTestCaseTitle(String testCaseTitle) {
		this.testCaseTitle = testCaseTitle;
	}
	public String getTestCaseTitle() {
		return testCaseTitle;
	}

	// Getter Setter for test case id
	public void setTestCaseDescription(String testCaseDescription) {
		this.testCaseDescription = testCaseDescription;
	}
	public String getTestCaseDescription() {
		return testCaseDescription;
	}

	// Getter Setter for WebDriver object instance
	public void setAppiumDriverProvider(AppiumDriverProvider appiumDriverProvider){
		this.objAppiumDriverProvider = appiumDriverProvider;
	}
	public AppiumDriverProvider getAppiumDriverProvider(){
		return objAppiumDriverProvider;
	}

	// Getter Setter for AppiumManager object instance
	public void setAppiumManager(AppiumManager appiumManager){
		this.objAppiumManager = appiumManager;
	}
	public AppiumManager getAppiumManager(){
		return objAppiumManager;
	} 

	// Getter Setter for AppiumManager object instance
	public void setSuiteName(String suiteName){
		this.suiteName = suiteName;
	}
	public String getSuiteName(){
		return suiteName;
	} 

	// Getter Setter for RunTimeDataHolder object instance
	public void setRunTimeDataHolder(RunTimeDataHolder runTimeDataHolder){
		this.objRunTimeDataHolder = runTimeDataHolder;
	}
	public RunTimeDataHolder getRunTimeDataHolder(){
		return objRunTimeDataHolder;
	} 

	// Getter Setter for Log4JLogger object instance
	public void setLog4JLogger(Log4JLogger log4JLogger){
		this.objLog4JLogger = log4JLogger;
	}
	public Log4JLogger getLog4JLogger(){
		return objLog4JLogger;
	}

	/**
	 * this method returns data from the the previously loaded datapool
	 * @param	: columnHeader - excel file header column name
	 * @return	: String value for corresponding header
	 */
	public String dataPool(String columnHeader) {
		try {
			if (dataPoolHashTable.get(columnHeader.toLowerCase()) == null) {
				System.out.println("Excepted test data header - '" + columnHeader + "' is not available in test data file" );
				return "";
			}
			else {
				return dataPoolHashTable.get(columnHeader.toLowerCase());
			}
		} catch (Exception exception) {
			if(getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return "";
		}
	}

	@SuppressWarnings("serial")
	public void updateTestData(String header, String value) {
		new DataPool().updateTestDataSheet(this.getTestDataFilePath(), this.getTestCaseID(), new Hashtable<String, String>(){{put(header, value);}});
	}
}