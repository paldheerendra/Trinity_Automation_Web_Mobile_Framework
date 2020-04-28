package com.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.TestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.generic.appiumDriver.AppiumDriverProvider;
import com.generic.appiumDriver.AppiumManager;
import com.generic.customAnnotation.TestDataSource;
import com.generic.logger.Log4JLogger;
import com.generic.utils.Configuration;
import com.generic.utils.CustomReporter;
import com.generic.utils.DataPool;
import com.generic.utils.GenericUtils;
import com.generic.utils.NetworkMonitor;
import com.generic.utils.RunTimeDataHolder;
import com.generic.utils.WaitMethods;
import com.generic.utils.WebDomains;
import com.generic.webDriver.WebDriverProvider;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.annotations.Title;

/**
 *  Class will load all test data, load all objects, initialize web driver, start reports. 
 *  Contains generic functionalities like open browser 
 * 	@author Harshvardhan Yadav(Expleo)
 */
public class BaseTest extends Pojo 
{
	/**
	 * This method webdriver for web application 
	 * @author Harshvardhan Yadav (Expleo)
	 */
	@Step("Initialize Web Environment")
	public void initializeWebEnvironment(Hashtable<String, String> dataSet) 
	{
		try
		{
			if(getConfiguration() == null) {
				Configuration objConfiguration = new Configuration();
				objConfiguration.loadConfigProperties();
				super.setConfiguration(objConfiguration);
			}

			loadTestData(dataSet);

			NetworkMonitor networkMonitor = new NetworkMonitor();
			super.setNetworkMonitor(networkMonitor);
			if (getConfiguration().getConfig("browsermob.proxy").equalsIgnoreCase("true"))	
				getNetworkMonitor().startNetworkMonotorProxyServer();

			WebDriverProvider objWebDriverProvider = new WebDriverProvider();
			objWebDriverProvider.initialize(getConfiguration(), getNetworkMonitor());
			super.setWebDriverProvider(objWebDriverProvider);

			this.loadGetterSetter();

			super.getLogReporter().webLog("Web environment initialize successful", true);
			super.getWebDomains().getURLForWeb();
		}
		catch(Exception exception){
			if(getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			Assert.assertTrue(false);
			super.getLogReporter().webLog("Web environment initialize successful", false);
		}
	}

	/**
	 * @Description	: quit webdriver  
	 * @author Harshvardhan Yadav(Expleo)
	 */
	public void tearDownWebEnvironment()
	{
		try
		{
			super.getWebDriverProvider().tearDown();
			super.getLogReporter().webLog("Web environment teardown successful", true);
		}
		catch(Exception exception){
			if(getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			super.getLogReporter().webLog("Web environment teardown successful", false);

		}
		finally {
			if (getConfiguration().getConfig("browsermob.proxy").equalsIgnoreCase("true"))	
				getNetworkMonitor().stopNetworkMonotorProxyServer();
			if (getConfiguration().getConfig("test.custom.reporter").equalsIgnoreCase("true")) 
				super.getCustomReporter().endReport();
		}
	}

	/**
	 * This method appium driver for mobile application 
	 * @author Harshvardhan Yadav(Expleo)
	 */
	public void initializeMobileEnvironment(Hashtable<String, String> dataSet)
	{
		try
		{ 
			if(getConfiguration() == null) {
				Configuration objConfiguration = new Configuration();
				objConfiguration.loadConfigProperties();
				super.setConfiguration(objConfiguration);
			}

			loadTestData(dataSet);

			AppiumDriverProvider objAppiumDriverProvider = new AppiumDriverProvider();
			if(System.getProperty("os.name").trim().toLowerCase().contains("windows"))
			{
				if(getConfiguration().getConfigBooleanValue("desktop.appium")) {
					objAppiumDriverProvider.initializeDesktopAppium("android", getConfiguration());
					this.setAppiumDriverProvider(objAppiumDriverProvider);
				}
				else {
					AppiumManager objAppiumManager = new AppiumManager();
					this.setAppiumManager(objAppiumManager);
					objAppiumManager.startAppium();

					objAppiumDriverProvider.initializeConsoleBaseAppium("android", getConfiguration(), objAppiumManager.getCurrentAppiumIpAddress(), objAppiumManager.getCurrentAppiumPort());
					this.setAppiumDriverProvider(objAppiumDriverProvider);
				}
			}
			else if(System.getProperty("os.name").trim().toLowerCase().contains("mac"))
			{
				objAppiumDriverProvider.initializeDesktopAppium("ios", getConfiguration());
				this.setAppiumDriverProvider(objAppiumDriverProvider);
			}

			this.loadGetterSetter();

			if(getConfiguration().getConfig("mobile.test").equalsIgnoreCase("web"))
				super.getWebDomains().getURLForMobile();

			super.getLogReporter().mobileLog("Mobile environment initialize successful", true);
		}
		catch(Exception exception){
			if(getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			Assert.assertTrue(false);
			super.getLogReporter().mobileLog("Mobile environment initialize successful", false);
		}
	}

	private void loadGetterSetter() 
	{
		Log4JLogger objLog4JLogger = new Log4JLogger();
		super.setLog4JLogger(objLog4JLogger);
		super.getLog4JLogger().initializeLogger();
		
		WaitMethods objWaitMethods = new WaitMethods();
		super.setWaitMethods(objWaitMethods);

		LogReporter objLogReporter = new LogReporter(this);
		super.setLogReporter(objLogReporter);

		WebActions objWebActions = new WebActions(this);
		super.setWebActions(objWebActions);

		MobileActions objMobileActions = new MobileActions(this);
		super.setMobileActions(objMobileActions);

		RunTimeDataHolder objRunTimeDataHolder = new RunTimeDataHolder();
		super.setRunTimeDataHolder(objRunTimeDataHolder);

		WebDomains objWebDomains = new WebDomains(this);
		super.setWebDomains(objWebDomains);

		if(getCustomReporter() == null) {
			if (getConfiguration().getConfig("test.custom.reporter").equalsIgnoreCase("true")){
				CustomReporter objCustomReporter = new CustomReporter();
				objCustomReporter.startReport(super.getTestCaseID(), getSuiteName());
				super.setCustomReporter(objCustomReporter);
			}
		}
	}

	/**
	 * @Description	: quit webdriver  
	 * @author Harshvardhan Yadav(Expleo)
	 */
	public void tearDownMobileEnvironment()
	{
		try
		{
			getAppiumDriverProvider().tearDown();
			super.getLogReporter().mobileLog("Mobile environment teardown successful", true);
		}
		catch(Exception exception){
			if(getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			super.getLogReporter().mobileLog("Mobile environment teardown successful", false);

		}
		finally {
			if(getConfiguration().getConfig("appium.log").equalsIgnoreCase("true"))
				GenericUtils.writeAppiumLogs(getAppiumDriverProvider().getAppumLogEntries());
			if(System.getProperty("os.name").trim().toLowerCase().contains("windows")){
				if(!getConfiguration().getConfigBooleanValue("desktop.appium"))
					getAppiumManager().stopServer();
			}
		}
	}

	/** 
	 * return runtime object of page or view
	 * @author Harshvardhan Yadav(Expleo)
	 * 
	 */
	public <T> T PageObjectManager(Class<T> cls) {
		try {
			Constructor<T> constructor = cls.getConstructor(new Class[] { Pojo.class });
			return (T) constructor.newInstance(new Object[] { this });
		} catch (  InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException
				| SecurityException | NoSuchMethodException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	/** 
	 * testng data provider
	 * @author Harshvardhan Yadav(Expleo)
	 * 
	 */
	@DataProvider(name = "TestDataProvider")
	public Object[][] getDataProvider(Method method, ITestContext context) 
	{
		Object[][] testData = null;
		String testCaseID = ((TestDataSource) method.getAnnotation(TestDataSource.class)).testCaseID();
		super.setTestCaseID(testCaseID);

		String dataSource = ((TestDataSource) method.getAnnotation(TestDataSource.class)).dataSource();
		String testDataFilePath = System.getProperty("user.dir") + "/src/test/resources/testData/" +
				((dataSource.substring(0,1).equals("/") || dataSource.substring(0,1).equals("\\")) 
						? dataSource.substring(1) : dataSource) 
				+ ".xlsx";
		super.setTestDataFilePath(testDataFilePath);

		String testCaseTitle = ((Title) method.getAnnotation(Title.class)).value();
		super.setTestCaseTitle(testCaseTitle);
		String testCaseDescription = ((Description) method.getAnnotation(Description.class)).value();
		super.setTestCaseDescription(testCaseDescription);

		System.out.println("********* Executing test case - " + testCaseID + " - " + testCaseTitle + " - " + testCaseDescription );
		System.out.println("********* Loading test data for test case");
		if (!testDataFilePath.equals("") && !testCaseID.equals("")) 
			testData = new DataPool().loadTestData(testCaseID, testDataFilePath);

		if(testData == null) {
			System.out.println("********* Unable to load test data for test case please check missing filed \n following may be chances \n - TestCase id mismatch to excel test data row \n - forgot to add test data row");
			return null;
		}
		else
			return testData;
	}

	/**
	 * @Method : loadTestData
	 * @param : runID - test case run id
	 * @param : dataSet - test data hash table
	 * @Description : Load data from excel for the running testCase and return
	 *              as Object array
	 * @author : Harshvardhan Yadav (Expleo)
	 */
	public void loadTestData(Hashtable<String, String> dataSet) {
		//super.setRunID(runID);
		super.setDataPoolHashTable(dataSet);
	}

	/**
	 * @Method 	: loadDataProvider
	 * @param 	: testCaseID - test case id
	 * @param 	: testDataFile - test data file
	 * @Description : Load Data from Excel for the running testCase and return
	 *              as Object array
	 * @author Harshvardhan Yadav(Expleo)
	 */
	public Object[][] loadDataProvider(String testCaseID, String testDataFilePath) {
		Object[][] dataPool = null;

		if (!testDataFilePath.equals("") && !testCaseID.equals("")) {
			DataPool objDataPool = new DataPool();
			dataPool = objDataPool.loadTestData(testCaseID, testDataFilePath);
		}
		return dataPool;
	}

	@BeforeSuite(alwaysRun = true)
	public void setup(ITestContext ctx) {
		System.out.println("*** Setting test output directory for testNG");
		TestRunner runner = (TestRunner) ctx;
		runner.setOutputDirectory(System.getProperty("user.dir") + "/target/test-output");
	}

	@BeforeMethod(alwaysRun = true)
	public void setsuitename(ITestContext ctx) {
		super.setSuiteName(ctx.getSuite().getName());
	}
}