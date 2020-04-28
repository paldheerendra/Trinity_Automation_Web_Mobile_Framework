package com.generic;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.generic.interfaces._MobileActions;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;

/**
 * wrapper functions for performing actions on mobile element 
 * @author Harshvardhan Yadav(Expleo)
 */
public class MobileActions implements _MobileActions 
{
	private static final Exception Exception = null;
	private int DEFAULT_SLEEP_TIMEOUT = 2;
	private int FLUENTWAIT_WAIT_MID_TIMEOUT = 20;
	private int FLUENTWAIT_WAIT_MIN_TIMEOUT = 10;

	private Pojo objPojo;

	public MobileActions(Pojo pojo) {
		this.objPojo = pojo;
	} 

	public boolean click(By locator){
		return this.invokeActionOnMobileLocator(locator, "click");
	}

	public boolean setText(By locator, String textToSet){
		return this.invokeActionOnMobileLocator(locator, "setText", textToSet);
	}

	public boolean checkElementDisplayed(By locator){ 
		return this.invokeActionOnMobileLocator(locator, "checkElementDisplayed");
	}

	public boolean checkElementNotDisplayed(By locator){
		return this.invokeActionOnMobileLocator(locator, "checkElementNotDisplayed");
	}

	public boolean selectFromCustomDropDown(By locator, String optionToSelect){
		return this.invokeActionOnMobileLocator(locator, "selectFromCustomDropDown", optionToSelect);
	}

	public boolean selectFromDropDown(By locator, String... option){
		/*StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		for(StackTraceElement stackTraceElement : stackTraceElements)
			System.out.println("------------->" + stackTraceElement.getMethodName());
		 */
		return this.invokeActionOnMobileLocator(locator, "selectDropDown", option);
	} 

	public boolean selectCheckbox(By locator, Boolean status){
		return this.invokeActionOnMobileLocator(locator, "selectCheckbox", status);
	}

	public boolean selectRadioButton(By locator, Boolean status){
		return this.invokeActionOnMobileLocator(locator, "selectRadioButton", status);
	}

	/**
	 * Perform specific action on mobile element
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public boolean invokeActionOnMobileLocator(By locator, String action){
		int intAttempts = 1;
		int maxTries = Integer.parseInt(objPojo.getConfiguration().getConfig("maxTriesForElement"));
		boolean blnResult = false;
		while(intAttempts <= maxTries){
			System.out.println("\n **** Attempt - " + intAttempts + " to perform " + action +" on element, MaxAttempts" + maxTries);
			try
			{
				MobileElement objMobileElement;
				action = action.toLowerCase();
				switch(action)
				{
				case "click":
					objMobileElement = this.processMobileElement(locator);
					objMobileElement.click();
					blnResult =  true;
					System.out.println("Performed click action on element");
					break;

				case "checkelementdisplayed":
					objMobileElement = this.processMobileElement(locator);
					if(objMobileElement == null){
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
						blnResult =  false;
						throw Exception;
					}
					else {
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
						blnResult =  true;
					}
					break;

				case "checkelementnotdisplayed":
					objMobileElement = this.processMobileElement(locator);
					if(objMobileElement == null) {
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
						blnResult = true;
					}
					else{
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
						blnResult =  false;
						throw Exception;
					}
					break;
				}
				break;
			}catch(StaleElementReferenceException exceptionStaleElement){
				System.out.println("----->>>StaleElementReferenceException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionStaleElement.printStackTrace();
					return false;
				}
			}
			catch(NoSuchElementException exceptionNoSuchElementException){
				System.out.println("----->>>NoSuchElementException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionNoSuchElementException.printStackTrace();
					return false;
				}
			}
			catch(WebDriverException exceptionWebDriverException){
				System.out.println("----->>>WebDriverException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					if(exceptionWebDriverException.getMessage().contains("Other element would receive the click:")){
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					}
					else if(exceptionWebDriverException.getMessage().contains("Element is not clickable")) {
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					}
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionWebDriverException.printStackTrace();
					return false;
				}
			}
			catch(Exception exception){
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
					return false;
				}
			}
		}  
		return blnResult;
	}



	/**
	 * Perform specific action on mobile element
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public boolean invokeActionOnMobileLocator(By locator, String action, String... values){
		int intAttempts = 1;
		int maxTries = Integer.parseInt(objPojo.getConfiguration().getConfig("maxTriesForElement"));
		boolean blnResult = false;
		while(intAttempts <= maxTries){
			System.out.println("\n **** Attempt - " + intAttempts + " to perform " + action +" on element, MaxAttempts" + maxTries);
			try
			{
				MobileElement objMobileElement;
				action = action.toLowerCase();
				switch(action)
				{
				case "settext":
					objMobileElement = this.processMobileElement(locator);
					objMobileElement.sendKeys(new CharSequence[]{values[0]});
					blnResult =  true;
					System.out.println("Performed set text on element");
					break;

				case "selectfromcustomdropdown":
					objMobileElement = this.processMobileElement(locator);
					By sub_locator = By.xpath(".//li/a[contains(text(),'" + values[0] + "')]");
					WebDriverWait wait = new WebDriverWait(objPojo.getAppiumDriverProvider().getAppiumDriver(), (long) objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitTimeout"));
					wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(objMobileElement, sub_locator));
					List<MobileElement> objCustomOptions = objMobileElement.findElements(sub_locator); 
					for(WebElement weOption : objCustomOptions){  
						if (weOption.getText().trim().equals(values[0])){
							weOption.click();
							objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
							System.out.println("Selected dropdown option for element");
							blnResult =  true;
							break;
						}
					} 		
					break;	

				case "selectdropdown":
					objMobileElement = this.processMobileElement(locator);
					Select sltDropDown = new Select(objMobileElement);

					if(values.length > 1 && !values[1].equals(""))
					{
						if(values[1].equalsIgnoreCase("Value"))
							sltDropDown.selectByValue(values[0]);
						else if(values[1].equalsIgnoreCase("Text"))
							sltDropDown.selectByVisibleText(values[0]);
						else if(values[1].equalsIgnoreCase("Index"))
							sltDropDown.selectByIndex(Integer.parseInt(values[0]));

						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
						System.out.println("Selected dropdown options for element");
						blnResult =  true;
					}
					else
					{
						// Web elements from dropdown list 
						List<WebElement> objOptions = sltDropDown.getOptions();
						boolean bOptionAvailable = false;
						int iIndex = 0;
						for(WebElement weOptions : objOptions)  
						{  
							if (weOptions.getText().trim().equalsIgnoreCase(values[0]))
							{
								sltDropDown.selectByIndex(iIndex);
								objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
								bOptionAvailable = true;
								System.out.println("Selected dropdown options for element");
								break;
							}
							else
								iIndex++;
						}
						return bOptionAvailable;
					}
					break;
				}
				break;
			}catch(StaleElementReferenceException exceptionStaleElement){
				System.out.println("----->>>StaleElementReferenceException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionStaleElement.printStackTrace();
					return false;
				}
			}
			catch(NoSuchElementException exceptionNoSuchElementException){
				System.out.println("----->>>NoSuchElementException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionNoSuchElementException.printStackTrace();
					return false;
				}
			}
			catch(WebDriverException exceptionWebDriverException){
				System.out.println("----->>>WebDriverException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					if(exceptionWebDriverException.getMessage().contains("Other element would receive the click:")){
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					}
					else if(exceptionWebDriverException.getMessage().contains("Element is not clickable")) {
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					}
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionWebDriverException.printStackTrace();
					return false;
				}
			}
			catch(Exception exception){
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
					return false;
				}
			}
		}  
		return blnResult;
	}

	/**
	 * Perform specific action on mobile element
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public boolean invokeActionOnMobileLocator(By locator, String action, boolean... values){
		int intAttempts = 1;
		int maxTries = Integer.parseInt(objPojo.getConfiguration().getConfig("maxTriesForElement"));
		boolean blnResult = false;
		while(intAttempts <= maxTries){
			System.out.println("\n **** Attempt - " + intAttempts + " to perform " + action +" on element, MaxAttempts" + maxTries);
			try
			{
				MobileElement objMobileElement;
				action = action.toLowerCase();
				switch(action)
				{
				case "selectcheckbox":
					objMobileElement = this.processMobileElement(locator);
					if (objMobileElement.getAttribute("type").equals("checkbox")) {
						if ((objMobileElement.isSelected() && !values[0]) || (!objMobileElement.isSelected() && values[0]))
							objMobileElement.click();
						blnResult = true;
					} else
						blnResult = false;
					System.out.println("Performed click action on checkbox element");
					break;

				case "selectradiobutton":
					objMobileElement = this.processMobileElement(locator);
					if (objMobileElement.getAttribute("type").equals("radio")) {
						if ((objMobileElement.isSelected() && !values[0]) || (!objMobileElement.isSelected() && values[0]))
							objMobileElement.click();
						blnResult = true;
					} else
						blnResult = false;
					System.out.println("Performed click action on radio element");
					break;
				}
				break;
			}catch(StaleElementReferenceException exceptionStaleElement){
				System.out.println("----->>>StaleElementReferenceException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionStaleElement.printStackTrace();
					return false;
				}
			}
			catch(NoSuchElementException exceptionNoSuchElementException){
				System.out.println("----->>>NoSuchElementException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionNoSuchElementException.printStackTrace();
					return false;
				}
			}
			catch(WebDriverException exceptionWebDriverException){
				System.out.println("----->>>WebDriverException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					if(exceptionWebDriverException.getMessage().contains("Other element would receive the click:")){
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					}
					else if(exceptionWebDriverException.getMessage().contains("Element is not clickable")) {
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					}
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionWebDriverException.printStackTrace();
					return false;
				}
			}
			catch(Exception exception){
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
					return false;
				}
			}
		}  
		return blnResult;
	}

	public String getAttribute(By locator, String strategy){
		return this.getFromMobileLocator(locator, "getAttribute", strategy);
	}

	public String getText(By locator){
		return this.getFromMobileLocator(locator, "getText");
	}

	/**
	 * Perform specific action on mobile element
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public String getFromMobileLocator(By locator, String action, String... strategy){
		int intAttempts = 1;
		int maxTries = Integer.parseInt(objPojo.getConfiguration().getConfig("maxTriesForElement"));
		String returnVal = "";
		while(intAttempts <= maxTries){
			System.out.println("Attempt - " + intAttempts + " to " + action +" on element, MaxAttempts" + maxTries);
			try{
				MobileElement objMobileElement;
				action = action.toLowerCase();
				switch(action)
				{
				case "getattribute":
					objMobileElement = this.processMobileElement(locator);
					returnVal = objMobileElement.getAttribute(strategy[0]);
					System.out.println("Performed get attribute action on element");
					break;

				case "gettext":
					objMobileElement = this.processMobileElement(locator);
					returnVal = objMobileElement.getText();
					System.out.println("Performed get text action on element");
					break;
				}
				break;
			}catch(StaleElementReferenceException exceptionStaleElement){
				System.out.println("----->>>StaleElementReferenceException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionStaleElement.printStackTrace();
					return null;
				}
			}
			catch(NoSuchElementException exceptionNoSuchElementException){
				System.out.println("----->>>NoSuchElementException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionNoSuchElementException.printStackTrace();
					return null;
				}
			}
			catch(WebDriverException exceptionWebDriverException){
				System.out.println("----->>>WebDriverException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					if(exceptionWebDriverException.getMessage().contains("Other element would receive the click:")){
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					}
					else if(exceptionWebDriverException.getMessage().contains("Element is not clickable")) {
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					}
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionWebDriverException.printStackTrace();
					return null;
				}
			}
			catch(Exception exception){
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
					return null;
				}
			}
		}  
		return returnVal;
	}

	/**
	 * Process mobile element with all defined synchronization 
	 * @return processed mobile element
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public MobileElement processMobileElement(final By locator)
	{
		System.out.println("Appium processing mobile element");
		try{
			Wait<WebDriver> wait = new FluentWait<WebDriver>(objPojo.getAppiumDriverProvider().getAppiumDriver())
					.withTimeout(Duration.ofSeconds(objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitTimeout")))
					.pollingEvery(Duration.ofSeconds(objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitPullingTimeout")))
					.ignoring(NoSuchElementException.class)
					.ignoring(InvalidElementStateException.class)
					.ignoring(StaleElementReferenceException.class)
					.ignoring(WebDriverException.class);

			MobileElement mobileElement = wait.until(new Function<WebDriver, MobileElement>(){
				public MobileElement apply(WebDriver driver){
					return (MobileElement) objPojo.getAppiumDriverProvider().getAppiumDriver().findElement(locator);
				}
			}); 

			WebDriverWait webDriverWait = new WebDriverWait(objPojo.getAppiumDriverProvider().getAppiumDriver(), objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitTimeout"));
			webDriverWait.until(ExpectedConditions.visibilityOf(mobileElement));

			return mobileElement;

		}catch(Exception exception){
			System.out.println("---> Process processMobileElement --->>Exception -- appium driver unable to find element");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return null;
		} 
	}

	/**
	 * Process mobile element with all defined synchronization 
	 * @return processed mobile element list
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public List<MobileElement> processMobileElements(final By locator)
	{
		System.out.println("Appium processing mobile element");
		try{
			Wait<WebDriver> wait = new FluentWait<WebDriver>(objPojo.getAppiumDriverProvider().getAppiumDriver())
					.withTimeout(Duration.ofSeconds(objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitTimeout")))
					.pollingEvery(Duration.ofSeconds(objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitPullingTimeout")))
					.ignoring(NoSuchElementException.class)
					.ignoring(InvalidElementStateException.class)
					.ignoring(StaleElementReferenceException.class);

			List<MobileElement> mobileElements = wait.until(new Function<WebDriver,  List<MobileElement>>(){
				@SuppressWarnings("unchecked")
				public List<MobileElement> apply(WebDriver driver){
					return (List<MobileElement>) objPojo.getAppiumDriverProvider().getAppiumDriver().findElements(locator);
				}
			}); 

			WebDriverWait webDriverWait = new WebDriverWait(objPojo.getAppiumDriverProvider().getAppiumDriver(), objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitTimeout"));
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			return mobileElements;
		}catch(Exception exception){
			System.out.println("---> Process Elelement --->>Exception");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return null;
		} 
	}

	public boolean checkElementDisplayedWithMinWait(By locator){
		try{
			WebDriverWait wait = new WebDriverWait(objPojo.getAppiumDriverProvider().getAppiumDriver(), (long)FLUENTWAIT_WAIT_MIN_TIMEOUT);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			objPojo.getAppiumDriverProvider().getAppiumDriver().findElement(locator);
			return true;
		}catch(Exception exception){
			System.out.println("---> checkMobileElementDisplayedWithMinWait --->>Exception");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		}
	}

	public boolean checkElementDisplayedWithMidWait(By locator){
		try{
			WebDriverWait wait = new WebDriverWait(objPojo.getAppiumDriverProvider().getAppiumDriver(), (long)FLUENTWAIT_WAIT_MID_TIMEOUT);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			objPojo.getAppiumDriverProvider().getAppiumDriver().findElement(locator);
			return true;
		}catch(Exception exception){
			System.out.println("---> checkMobileElementDisplayedWithMidWait --->>Exception");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		}
	}

	/** android scroll to required text using UIAutomator2 
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public boolean androidScrollToRequiredText(String textToSelect){
		try
		{
			objPojo.getWaitMethods().sleep(objPojo.getConfiguration().getConfigIntegerValue("minwait"));
			objPojo.getAppiumDriverProvider().getAppiumDriver().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView("
					+ "new UiSelector().text(\""+textToSelect+"\").instance(0));")); 
			objPojo.getWaitMethods().sleep(objPojo.getConfiguration().getConfigIntegerValue("minwait"));
			return true;
		}catch(Exception exception){
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		} 
	}

	/** android scroll to child element using UIAutomator2 
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public boolean androidScrollToGetChildByText(String scrollableContainerResourceID, String childIdentifier, String actualTextToSelect){
		try
		{ 	
			objPojo.getWaitMethods().sleep(objPojo.getConfiguration().getConfigIntegerValue("minwait"));
			objPojo.getAppiumDriverProvider().getAppiumDriver().findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().resourceId(\""+scrollableContainerResourceID+"\")).getChildByText("
							+ "new UiSelector().className(\""+childIdentifier+"\"), \""+actualTextToSelect+"\")"));
			objPojo.getWaitMethods().sleep(objPojo.getConfiguration().getConfigIntegerValue("minwait"));
			return true;
		}catch(Exception exception){
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		} 
	}

	/** android scroll to required text contains using UIAutomator2 
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public boolean androidScrollToRequiredTextContains(String textToSelect){
		try
		{
			objPojo.getWaitMethods().sleep(objPojo.getConfiguration().getConfigIntegerValue("minwait"));
			objPojo.getAppiumDriverProvider().getAppiumDriver().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView("
					+ "new UiSelector().textContains(\""+textToSelect+"\").instance(0));")); 
			objPojo.getWaitMethods().sleep(objPojo.getConfiguration().getConfigIntegerValue("minwait"));
			return true;
		}catch(Exception exception){
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		} 
	}

	/** android scroll using source mobile element to destination mobile element UIAutomator2 
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public boolean scrollUsingTouchActions_ByElements(MobileElement startElement, MobileElement endElement) {
		try
		{
			@SuppressWarnings("rawtypes")
			TouchAction actions = new TouchAction(objPojo.getAppiumDriverProvider().getAppiumDriver());
			//actions.press(startElement).waitAction(Duration.ofSeconds(2)).moveTo(endElement).release().perform();
			actions.longPress(new LongPressOptions().withElement(ElementOption.element(startElement))).moveTo(ElementOption.element(endElement)).release().perform();
			return true;
		}catch(Exception exception){
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		} 
	}

	public boolean selectFromFixedRadioSpinner(By spinner, String spinnerElementTextToSelect) {
		try
		{
			this.click(spinner);
			this.click(By.xpath("//android.widget.ListView/android.widget.CheckedTextView[@text='" + spinnerElementTextToSelect + "']"));
			return true;
		}catch(Exception exception) {
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		}
	}

	public boolean selectFromScrollableRadioSpinner(By spinner, String spinnerElementTextToSelect) {
		try
		{
			this.click(spinner);
			objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
			boolean requiredRadioButton = false;
			do{
				if(this.processMobileElement(By.xpath(".//android.widget.ListView")) != null) {
					List<MobileElement> listView = this.processMobileElements(By.xpath(".//android.widget.ListView/android.widget.CheckedTextView"));
					for(MobileElement mobileElement : listView) {
						if(mobileElement.getText().equals(spinnerElementTextToSelect)) {
							mobileElement.click();
							requiredRadioButton = true;
							break;
						}
					}
					if(!requiredRadioButton) {
						this.scrollUsingTouchActions_ByElements(listView.get(listView.size()-1), listView.get(0));
					}
				}
			}while(!requiredRadioButton);
			return true;
		}catch(Exception exception) {
			System.out.println("Unable to select element from scrollable spinner");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		}
	}

	public boolean webPageRefresh() {
		try
		{
			objPojo.getAppiumDriverProvider().getAppiumDriver().navigate().refresh();
			return true;
		}catch(Exception exception) {
			System.out.println("Unable to select element from scrollable spinner");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		}
	}

	/**
	 *  switch to the Frame by Frame name
	 *  @param : locator - The most common one. You locate your iframe like other
	 *        		elements, then pass it into the method
	 *       		eg.driver.switchTo().frame(driver.findElement(By.xpath(".//iframe[@title='Test']")))
	 *  @author Harshvardhan Yadav (Expleo)
	 */
	public void switchToFrameUsingIframe_Element(By locator) {
		try {
			objPojo.getAppiumDriverProvider().getAppiumDriver().switchTo().defaultContent();
			objPojo.getAppiumDriverProvider().getAppiumDriver().switchTo().frame(this.processMobileElement(locator));
			System.out.println("Switched to frame");
		} catch (Exception exception) {
			System.out.println("unable to switch to frame");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
		}
	}

	/**
	 * switch to the Frame by Frame name or Id
	 * @param : frameName - Name/Id of frame you want to switch
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public void switchToFrameUsingNameOrId(String frameNameOrID) {
		try {
			objPojo.getAppiumDriverProvider().getAppiumDriver().switchTo().defaultContent();
			objPojo.getAppiumDriverProvider().getAppiumDriver().switchTo().frame(frameNameOrID);
			System.out.println("Switched to frame");
		} catch (Exception exception) {
			System.out.println("unable to switch to frame");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
		}
	}

	/**
	 * switch to the Frame by index
	 * @param : index - Index on page
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public void switchToFrameUsingIndex(int index) {
		try {
			objPojo.getAppiumDriverProvider().getAppiumDriver().switchTo().defaultContent();
			objPojo.getAppiumDriverProvider().getAppiumDriver().switchTo().frame(index);
			System.out.println("Switched to frame");
		} catch (Exception exception) {
			System.out.println("unable to switch to frame");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
		}
	}

	/**
	 *	switch to the default content
	 * 	@author Harshvardhan Yadav (Expleo)
	 */
	public void switchToDefaultContent() {
		try {
			objPojo.getAppiumDriverProvider().getAppiumDriver().switchTo().defaultContent();
			System.out.println("Switched to default content");
		} catch (Exception exception) {
			System.out.println("unable to switch to default content");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
		}
	}

	/**
	 * Return current window handle
	 * @return current window handle
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public String getCurrentWindowHandle() {
		try {
			return objPojo.getAppiumDriverProvider().getAppiumDriver().getWindowHandle();
		}catch(Exception exception){
			System.out.println("Unable to get main window handel");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return null;
		} 
	}

	/**
	 * switchTo child Window
	 * @author Harshvardhan Yadav (Expleo)
	 **/
	public void switchToChildWindow() 
	{	
		try
		{
			objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
			String parentHandle = this.getCurrentWindowHandle();
			Set<String> windowNames = objPojo.getAppiumDriverProvider().getAppiumDriver().getWindowHandles();

			@SuppressWarnings("rawtypes")
			Iterator ite = windowNames.iterator();

			while (ite.hasNext()) 
			{
				String popupHandle = ite.next().toString();
				if (!popupHandle.equals(parentHandle)) {
					objPojo.getAppiumDriverProvider().getAppiumDriver().switchTo().window(popupHandle);
					objPojo.getLogReporter().webLog("Switched to newly opned window", true);
					break;
				}
			}
		}catch(Exception exception){
			System.out.println("Unable to siwtch to child window");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
		} 
	}

	/**
	 * switchTo child Window using window handle
	 * @author Harshvardhan Yadav (Expleo)
	 **/
	public void switchToWindowUsingHandle(String windowHandle) 
	{	
		try
		{
			objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
			Set<String> windowNames = objPojo.getAppiumDriverProvider().getAppiumDriver().getWindowHandles();

			@SuppressWarnings("rawtypes")
			Iterator ite = windowNames.iterator();

			while (ite.hasNext()) 
			{
				String popupHandle = ite.next().toString();
				if (popupHandle.equals(windowHandle)) {
					objPojo.getAppiumDriverProvider().getAppiumDriver().switchTo().window(popupHandle);
					objPojo.getLogReporter().webLog("Switched to main window", true);
					break;
				}
			}
		}catch(Exception exception){
			System.out.println("Unable to siwtch to window using handle");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
		} 
	}

	/**
	 * switch to window using the given title
	 * @param : locator - Window title
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public boolean switchToWindowUsingTitle(String windowTitle) {
		try {
			objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
			String mainWindowHandle = objPojo.getAppiumDriverProvider().getAppiumDriver().getWindowHandle();
			Set<String> openWindows = objPojo.getAppiumDriverProvider().getAppiumDriver().getWindowHandles();

			if (!openWindows.isEmpty()) {
				for (String windows : openWindows) {
					String window = objPojo.getAppiumDriverProvider().getAppiumDriver().switchTo().window(windows).getTitle();
					if (windowTitle.equals(window)) {
						objPojo.getLogReporter().webLog("Switched to window using title - ", windowTitle, true);
						return true;
					}
					else
						objPojo.getAppiumDriverProvider().getAppiumDriver().switchTo().window(mainWindowHandle);
				}
			}
			return false;
		} catch (Exception exception) {
			objPojo.getLogReporter().webLog("Unable to switch to window using title - ", windowTitle, true);
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		}
	}

	/**
	 * Execute javascript
	 * @author Harshvardhan Yadav (Expleo)
	 */
	public void executeJavascript(String javascript) {
		JavascriptExecutor js = (JavascriptExecutor) objPojo.getAppiumDriverProvider().getAppiumDriver();
		js.executeScript(javascript); 
	}
}