package com.generic;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import io.appium.java_client.AppiumDriver;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

public class LogReporter {

	private Pojo objPojo;

	public LogReporter(Pojo pojo) {
		this.objPojo = pojo;
	}

	/**
	 * Web Reporter method
	 * @param : Step - Step description, resultLog - result log pass/fail
	 *        (true/false), includeMobile - result for mobile(true/false)
	 * @author Dheerendra Singh
	 */
	@Step("{0}")
	public void webLog(String step, boolean resultLog) {
		String strLog = step;
		this.addAssertTakeScreenShot(step, strLog, "", "", "", resultLog);
	}

	/**
	 * Web Reporter method
	 * @param : Step - Step description, inputValue - Input value, resultLog -
	 *        result log pass/fail (true/false), includeMobile - result for
	 *        mobile(true/false)
	 * @author Dheerendra Singh
	 */
	@Step("{0} - {1}")
	public void webLog(String step, String inputValue, boolean resultLog) {
		String strLog = step + "|| Input Value : " + inputValue;
		this.addAssertTakeScreenShot(step, strLog, inputValue, "", "", resultLog);
	}

	/**
	 * Web Reporter method
	 * @param : Step - Step description, expectedValue - verification point
	 *        expected value, actualValue - verification point actual value,
	 *        resultLog - result log pass/fail (true/false), includeMobile -
	 *        result for mobile(true/false)
	 * @author Dheerendra Singh
	 */
	@Step("{0} - {1} - {2}")
	public void webLog(String step, String expectedValue, String actualValue, boolean resultLog) {
		String strLog = step + " || Expected Result : " + expectedValue + " || Actual Result : " + actualValue;
		this.addAssertTakeScreenShot(step, strLog, "", expectedValue, actualValue, resultLog);
	}

	/**
	 * Mobile Reporter method
	 * @param : Step - Step description, resultLog - result log pass/fail
	 *        (true/false), includeMobile - result for mobile(true/false)
	 * @author Dheerendra Singh
	 */
	@Step("{0}")
	public void mobileLog(String step, boolean resultLog) {
		String strLog = step;
		this.addAssertTakeScreenShot(step, strLog, "", "", "", resultLog);
	}

	/**
	 * Mobile Reporter method
	 * @param : Step - Step description, inputValue - Input value, resultLog -
	 *        result log pass/fail (true/false), includeMobile - result for
	 *        mobile(true/false)
	 * @author Dheerendra Singh
	 */
	@Step("{0} - {1}")
	public void mobileLog(String step, String inputValue, boolean resultLog) {
		String strLog = step + "|| Input Value : " + inputValue;
		this.addAssertTakeScreenShot(step, strLog, inputValue, "", "", resultLog);
	}

	/**
	 * Mobile Reporter method
	 * @param : Step - Step description, expectedValue - verification point
	 *        expected value, actualValue - verification point actual value,
	 *        resultLog - result log pass/fail (true/false), includeMobile -
	 *        result for mobile(true/false)
	 * @author Dheerendra Singh
	 */
	@Step("{0} - {1} - {2}")
	public void mobileLog(String step, String expectedValue, String actualValue, boolean resultLog) {
		String strLog = step + " || Expected Result : " + expectedValue + " || Actual Result : " + actualValue;
		this.addAssertTakeScreenShot(step, strLog, "", expectedValue, actualValue, resultLog);
	}

	/**
	 * Add step assert along with reporting and screenshot 
	 * @author Dheerendra Singh
	 */
	private void addAssertTakeScreenShot(String step, String strLog, String inputValue, String expectedValue,
			String actualValue, boolean resultLog) 
	{
		try 
		{ 
	 		objPojo.getLog4JLogger().getLogger().info(step);
			
			if (objPojo.getConfiguration().getConfig("test.custom.reporter").equalsIgnoreCase("true")) 
			{
				if (resultLog)
					objPojo.getCustomReporter().pass(step, inputValue, expectedValue, actualValue);
				else
					objPojo.getCustomReporter().fail(step, inputValue, expectedValue, actualValue);
			}

			if (resultLog) 
			{
				Reporter.log("Step >> " + strLog);
				System.out.println("\n Step performed <<<<<<<<<< " + strLog + " <<<<<<<<<< PASS");
 		 		Assert.assertTrue(true);
			} else {
				String fileName = Long.toString(System.currentTimeMillis()) + ".png";
				String fileWithPath = System.getProperty("user.dir") +  "/target/test-output/ScreenShot/" + fileName;
				Reporter.log("Step >> " + strLog);
				System.out.println("\n Step performed <<<<<<<<<< " + strLog + " <<<<<<<<<< FAIL");
				
				StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
				boolean mobileScreenShot = false;
				for(StackTraceElement stackTraceElement : stackTraceElements) {
					if(stackTraceElement.getMethodName().equalsIgnoreCase("mobileLog"))
						mobileScreenShot = true;
			 	}
				
			 	if (mobileScreenShot)
					this.takeScreenShot(objPojo.getAppiumDriverProvider().getAppiumDriver(), fileWithPath);
				else
					this.takeScreenShot(objPojo.getWebDriverProvider().getDriver(), fileWithPath);
 	 
				Assert.assertTrue(false);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 *  Take Screen shot for current web driver.
	 * @author Dheerendra Singh
	 */
	public void takeScreenShot(WebDriver webDriver, String fileWithPath) {
	 	try {
			TakesScreenshot scrShot = ((TakesScreenshot) webDriver);
			File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File destFile = new File(fileWithPath);
		 	FileUtils.moveFile(srcFile, destFile);
			this.fileToByte(destFile);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Take Screen shot for using current appiumDriver instance.
	 * @author Dheerendra Singh
	 */
	public void takeScreenShot(AppiumDriver<?> appiumDriver, String fileWithPath) {
	 	try {
			TakesScreenshot scrShot = ((TakesScreenshot) appiumDriver);
		 	File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
		 	File destFile = new File(fileWithPath);
			FileUtils.moveFile(srcFile, destFile);
			this.fileToByte(destFile);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Converts image file to byte array for allure  report.
	 * @author Dheerendra Singh
	 * @throws : IOException
	 */
	@Attachment(value = "Screenshot", type = "image/png")
	private byte[] fileToByte(File file) throws Exception {
		if (file != null)
			return Files.readAllBytes(Paths.get(file.getPath()));
		else
			return new byte[0];
	}
}