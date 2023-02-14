package com.generic;

import java.time.Duration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List; 
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.generic.interfaces._WebActions;
import com.generic.utils.CommandPrompt;

/**
 * wrapper functions for performing actions on web element 
 * @author Dheerendra Singh
 */
public class WebActions implements _WebActions 
{
	private static final Exception Exception = null;
	private int DEFAULT_SLEEP_TIMEOUT = 2;
	private int FLUENTWAIT_WAIT_MID_TIMEOUT = 20;
	private int FLUENTWAIT_WAIT_MIN_TIMEOUT = 10;

	private Pojo objPojo;

	public WebActions(Pojo pojo) {
		this.objPojo = pojo;
	} 

	public boolean click(By locator){
		return this.invokeOnLocator(locator, "click");
	}

	public boolean checkElementDisplayed(By locator){
		return this.invokeOnLocator(locator, "checkElementDisplayed");
	}
	public boolean checkElementNotDisplayed(By locator){
		return this.invokeOnLocator(locator, "checkElementNotDisplayed");
	}

	public boolean setText(By locator, String textToSet){
		return this.invokeOnLocator(locator, "setText", textToSet);
	}

	public boolean isCheckBoxSelected(By locator){
		return this.invokeOnLocator(locator, "isCheckBoxSelected");
	}

	public boolean isRadioButtonSelected(By locator){
		return this.invokeOnLocator(locator, "isRadioButtonSelected");
	}

	public boolean performMouseHover(By locator){
		return this.invokeOnLocator(locator, "performMouseHover");
	}

	public boolean bootStrapSetText(By locator, String textToSet){
		return this.invokeOnLocator(locator, "bootStrapSetText", textToSet);
	} 

	public boolean setTextWithClear(By locator, String textToSet){
		return this.invokeOnLocator(locator, "setTextWithClear", textToSet);
	}

	public boolean selectFromCustomDropDown(By locator, String optionToSelect){
		return this.invokeOnLocator(locator, "selectFromCustomDropDown", optionToSelect);
	}

	public boolean selectFromDropDown(By locator, String... option){
		return this.invokeOnLocator(locator, "selectDropDown", option);
	}

	public String getText(By locator){
		return this.getFromLocator(locator, "getText");
	}

	public String getAttribute(By locator, String strategy){
		return this.getFromLocator(locator, "getAttribute", strategy);
	}

	public boolean selectCheckbox(By locator, Boolean status){
		return this.invokeOnLocator(locator, "selectCheckbox", status);
	}

	public boolean selectRadioButton(By locator, Boolean status){
		return this.invokeOnLocator(locator, "selectRadioButton", status);
	}

	public String getSelectedValueFromDropDown(By locator){
		return this.getFromLocator(locator, "getSelectedValueFromDropDown");
	}

	/**
	 * Perform specific action on web element
	 * @author Dheerendra Singh
	 */
	public boolean invokeOnLocator(By locator, String action){
		int intAttempts = 0;
		int maxTries = Integer.parseInt(objPojo.getConfiguration().getConfig("maxTriesForElement"));
		boolean blnResult = false;
		while(intAttempts <= maxTries){
			System.out.println("**** Attempt - " + intAttempts + " to perform " + action +" on element, MaxAttempts" + maxTries);
			try
			{
				WebElement objWebElement;
				action = action.toLowerCase();
				switch(action)
				{
				case "click":
					objWebElement = this.processElement(locator);
					objWebElement.click();
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					blnResult =  true;
					System.out.println("Performed click action on element");
					break;

				case "checkelementdisplayed":
					objWebElement = this.processElement(locator);
					if(objWebElement == null){
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
					objWebElement = this.processElement(locator);
					if(objWebElement == null) {
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
						blnResult = true;
					}
					else{
						objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
						blnResult =  false;
						throw Exception;
					}
					break;

				case "ischeckboxselected":
					objWebElement = this.processElement(locator);
					if (objWebElement.getAttribute("type").equals("checkbox"))
						blnResult = objWebElement.isSelected();
					System.out.println("Performed verify selected action on checkbox element");
					break;

				case "isradiobuttonselected":
					objWebElement = this.processElement(locator);
					if (objWebElement.getAttribute("type").equals("radio"))
						blnResult = objWebElement.isSelected();
					System.out.println("Performed verify selected action on radiobox element");
					break;

				case "performmousehover":
					objWebElement = this.processElement(locator);
					Actions actionBuilder = new Actions(objPojo.getWebDriverProvider().getDriver());
					actionBuilder.moveToElement(objWebElement).build().perform();
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					System.out.println("Performed mouse over action on element");
					break;
				}
				break;
			}catch(StaleElementReferenceException exceptionStaleElement){
				System.out.println("----->>>StaleElementReferenceException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionStaleElement.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionNoSuchElementException.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionWebDriverException.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
					return false;
				}
			}
		}  
		System.out.println("blnResult------->" + blnResult);
		return blnResult;
	}

	/**
	 * Perform specific action on web element
	 * @author Dheerendra Singh
	 */
	public boolean invokeOnLocator(By locator, String action, String... values){
		int intAttempts = 0;
		int maxTries = Integer.parseInt(objPojo.getConfiguration().getConfig("maxTriesForElement"));
		boolean blnResult = false;
		while(intAttempts <= maxTries){
			System.out.println("**** Attempt - " + intAttempts + " to perform " + action +" on element, MaxAttempts" + maxTries);
			try
			{
				WebElement objWebElement;
				action = action.toLowerCase();
				switch(action)
				{
				case "settext":
					objWebElement = this.processElement(locator);
					objWebElement.sendKeys(new CharSequence[]{values[0]});
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					blnResult =  true;
					System.out.println("Performed set text action on element");
					break;

				case "bootstrapsettext":
					objWebElement = this.processElement(locator);
					objWebElement.sendKeys(new CharSequence[]{values[0]});
					WebElement objWebElementAutoPopulated =  this.processElement(By.xpath("//ul[@class='dropdown-menu ng-isolate-scope']/li/a/strong[contains(text(),'" + values[0] + "')]"));
					objWebElementAutoPopulated.click();
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					blnResult =  true;
					System.out.println("Performed bootstrap set text action on element");
					break;

				case "settextwithclear":
					objWebElement = this.processElement(locator);
					objWebElement.clear();
					objWebElement.sendKeys(new CharSequence[]{values[0]});
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					blnResult =  true;
					System.out.println("Performed clear with set text action on element");
					break;

				case "selectfromcustomdropdown":
					objWebElement = this.processElement(locator);
					By sub_locator = By.xpath(".//li/div/span[text()='" + values[0] + "']");
					WebDriverWait wait = new WebDriverWait(objPojo.getWebDriverProvider().getDriver(), (long) objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitTimeout"));
					wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(objWebElement, sub_locator));
					List<WebElement> objCustomOptions = objWebElement.findElements(sub_locator); 
					for(WebElement weOption : objCustomOptions){  
						if (weOption.getText().trim().equals(values[0])){
							weOption.click();
							objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
							System.out.println("Selected dropdown option for element");
							blnResult =  true;
						}
					} 		 
					break;

				case "selectdropdown":
					objWebElement = this.processElement(locator);
					Select sltDropDown = new Select(objWebElement);

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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionStaleElement.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionNoSuchElementException.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionWebDriverException.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
					return false;
				}
			}
		}  
		System.out.println("blnResult------->" + blnResult);
		return blnResult;
	}

	/**
	 * Perform specific action on web element
	 * @author Dheerendra Singh
	 */
	public String getFromLocator(By locator, String action, String... strategy){
		int intAttempts = 0;
		int maxTries = Integer.parseInt(objPojo.getConfiguration().getConfig("maxTriesForElement"));
		String returnVal = "";
		while(intAttempts <= maxTries){
			System.out.println("Attempt - " + intAttempts + " to perform " + action +" on element, MaxAttempts" + maxTries);
			try{
				WebElement webElement;
				action = action.toLowerCase();

				switch(action) 
				{
				case"getattribute":
					webElement = this.processElement(locator);
					returnVal = webElement.getAttribute(strategy[0]);
					System.out.println("Performed get attribute action on web element");
					break;

				case "gettext":
					webElement = this.processElement(locator);
					returnVal = webElement.getText();
					System.out.println("Performed get text action on web element");
					break;

				case "getselectedvaluefromdropdown":
					webElement = this.processElement(locator);
					Select selectDorpDown = new Select(webElement);
					String selectedDorpDownValue = selectDorpDown.getFirstSelectedOption().getText();
					returnVal = selectedDorpDownValue;
					System.out.println("Performed get text selected action on dropdown element");
					break;	
				}
				break;
			}catch(StaleElementReferenceException exceptionStaleElement){
				System.out.println("----->>>StaleElementReferenceException");
				intAttempts ++;
				if(intAttempts <= maxTries){
					objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionStaleElement.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionNoSuchElementException.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionWebDriverException.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
					return null;
				}
			}
		}  
		System.out.println("returnVal------->" + returnVal);
		return returnVal;
	}

	/**
	 * Perform specific action on web element
	 * @author Dheerendra Singh
	 */
	public boolean invokeOnLocator(By locator, String action, boolean... status){
		int intAttempts = 0;
		int maxTries = Integer.parseInt(objPojo.getConfiguration().getConfig("maxTriesForElement"));
		boolean blnResult = false;
		while(intAttempts <= maxTries){
			System.out.println("**** Attempt - " + intAttempts + " to perform " + action +" on element, MaxAttempts" + maxTries);
			try
			{
				WebElement objWebElement;
				action = action.toLowerCase();
				switch(action)
				{
				case "selectcheckbox":
					objWebElement = this.processElement(locator);
					if (objWebElement.getAttribute("type").equals("checkbox")) {
						if ((objWebElement.isSelected() && !status[0]) || (!objWebElement.isSelected() && status[0]))
							objWebElement.click();
						blnResult = true;
					} else
						blnResult = false;
					System.out.println("Performed click action on checkbox element");
					break;

				case "selectradiobutton":
					objWebElement = this.processElement(locator);
					if (objWebElement.getAttribute("type").equals("radio")) {
						if ((objWebElement.isSelected() && !status[0]) || (!objWebElement.isSelected() && status[0]))
							objWebElement.click();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionStaleElement.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionNoSuchElementException.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exceptionWebDriverException.printStackTrace();
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
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
				}
				else{
					System.out.println("----->>>Exception");				
					if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
						exception.printStackTrace();
					return false;
				}
			}
		}  
		System.out.println("blnResult------->" + blnResult);
		return blnResult;
	}

	/**
	 * Wait for network traffic to stop
	 * @author Dheerendra Singh
	 */
	private boolean waitForNetworkTraffic() {
		try{
			if (objPojo.getConfiguration().getConfig("browsermob.proxy").equalsIgnoreCase("true")) {
				System.out.println("\n wait for network traffic to stop");
				return objPojo.getNetworkMonitor().getNetworkMonitorProxy().waitForQuiescence(objPojo.getConfiguration().getConfigIntegerValue("browsermob.proxy.quietPeriod"),
						objPojo.getConfiguration().getConfigIntegerValue("browsermob.proxy.timeout"), TimeUnit.SECONDS);
			}
		}catch(Exception exception){
			System.out.println("--->waitForNetworkTraffic --->>Exception -- wait for network traffic to stop");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		}  
		return false;

	}

	/**
	 * Process web element with all defined synchronization 
	 * @return processed web element
	 * @author Dheerendra Singh
	 */
	public WebElement processElement(final By locator)
	{
		this.waitForNetworkTraffic();
		System.out.println("\n webdriver processing web element");

		try{
			Wait<WebDriver> wait = new FluentWait<WebDriver>(objPojo.getWebDriverProvider().getDriver())
					.withTimeout(Duration.ofSeconds(objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitTimeout")))
					.pollingEvery(Duration.ofSeconds(objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitPullingTimeout")))
					.ignoring(NoSuchElementException.class)
					.ignoring(InvalidElementStateException.class)
					.ignoring(StaleElementReferenceException.class);

			WebElement webElement = wait.until(new Function<WebDriver, WebElement>(){
				public WebElement apply(WebDriver driver){
					return objPojo.getWebDriverProvider().getDriver().findElement(locator);
				}
			}); 

			WebDriverWait webDriverWait = new WebDriverWait(objPojo.getWebDriverProvider().getDriver(), objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitTimeout"));
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			return webElement;
		}catch(Exception exception){
			System.out.println("---> Process Elelement --->>Exception -- webdriver unable to find element");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return null;
		}  
	}

	/**
	 * Process web element with all defined synchronization 
	 * @return processed web element list
	 * @author Dheerendra Singh
	 */
	public List<WebElement> processElements(final By locator)
	{
		this.waitForNetworkTraffic();
		System.out.println("Attempt to process selenium web element");

		try{
			Wait<WebDriver> wait = new FluentWait<WebDriver>(objPojo.getWebDriverProvider().getDriver())
					.withTimeout(Duration.ofSeconds(objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitTimeout")))
					.pollingEvery(Duration.ofSeconds(objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitPullingTimeout")))
					.ignoring(NoSuchElementException.class)
					.ignoring(InvalidElementStateException.class)
					.ignoring(StaleElementReferenceException.class);

			List<WebElement> webElements = wait.until(new Function<WebDriver,  List<WebElement>>(){
				public  List<WebElement> apply(WebDriver driver){
					return objPojo.getWebDriverProvider().getDriver().findElements(locator);
				}
			}); 

			WebDriverWait webDriverWait = new WebDriverWait(objPojo.getWebDriverProvider().getDriver(), objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitTimeout"));
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			return webElements;
		}catch(Exception exception){
			System.out.println("---> Process Elelement --->>Exception");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return null;
		} 
	}

	/**
	 *  wait for completion of active ajax control
	 * @author Dheerendra Singh
	 */
	public void waitForAjaxControls(int timeoutInSeconds) {
		System.out.println("Querying active AJAX controls by calling jquery.active");
		try {
			if (objPojo.getWebDriverProvider().getDriver() instanceof JavascriptExecutor) 
			{
				JavascriptExecutor jsDriver = (JavascriptExecutor) objPojo.getWebDriverProvider().getDriver();
				for (int i = 0; i < timeoutInSeconds; i++) {
					Object numberOfAjaxConnections = jsDriver
							.executeScript("return jQuery.active");
					// return should be a number
					if (numberOfAjaxConnections instanceof Long) 
					{
						Long n = (Long) numberOfAjaxConnections;
						System.out.println("Number of active jquery AJAX controls: " + n);
						if (n.longValue() == 0L)
							break;
					}
					Thread.sleep(1000);
				}
			} else {
				System.out.println("Web driver: can't run javascript.");
			}
		} catch (InterruptedException interruptedException) {
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				interruptedException.printStackTrace();
		}
	}

	// Framework User can configure loading indicator over here 
	public void waitForPageLoadingIndicator() {
		WebElement waitElement = null;
		try 
		{
			//Sets FluentWait Setup
			Wait<WebDriver> fluentWait  = new FluentWait<WebDriver>(objPojo.getWebDriverProvider().getDriver())
					.withTimeout(Duration.ofSeconds(objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitTimeout")))
					.pollingEvery(Duration.ofSeconds(objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitPullingTimeout")))
					.ignoring(NoSuchElementException.class)
					.ignoring(TimeoutException.class);

			// First checking to see if the loading indicator is found
			// we catch and throw no exception here in case they aren't ignored

			waitElement = fluentWait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(By.xpath("// Loading  "));
				}
			});

			//checking if loading indicator was found and if so we wait for it to disappear
			if (waitElement != null) {
				WebDriverWait wait = new WebDriverWait(objPojo.getWebDriverProvider().getDriver(), 
						objPojo.getConfiguration().getConfigIntegerValue("driver.fluentWaitPullingTimeout"));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(
						By.xpath("//Loading  "))
						);
			}
		} catch (Exception exception) {
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
		}
	}

	public boolean checkElementElementDisplayedWithMinWait(By locator){
		try{
			WebDriverWait wait = new WebDriverWait(objPojo.getWebDriverProvider().getDriver(), (long)FLUENTWAIT_WAIT_MIN_TIMEOUT);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			objPojo.getWebDriverProvider().getDriver().findElement(locator);
			return true;
		}catch(Exception exception){
			System.out.println("---> checkMobileElementDisplayedWithMinWait --->>Exception");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		}
	}

	public boolean checkElementElementDisplayedWithMidWait(By locator){
		try{
			WebDriverWait wait = new WebDriverWait(objPojo.getWebDriverProvider().getDriver(), (long)FLUENTWAIT_WAIT_MID_TIMEOUT);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			objPojo.getWebDriverProvider().getDriver().findElement(locator);
			return true;
		}catch(Exception exception){
			System.out.println("---> checkMobileElementDisplayedWithMidWait --->>Exception");
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * Press keyboard keys
	 * @author Dheerendra Singh
	 */
	public boolean pressKeybordKeys(By locator, String key){
		try{
			WebElement objWebElement = this.processElement(locator);
			if(key.toLowerCase().equals("tab"))
				objWebElement.sendKeys(Keys.TAB);
			if(key.toLowerCase().equals("enter"))
				objWebElement.sendKeys(Keys.ENTER);
			return true;
		}catch(Exception exception){
			System.out.println("---> pressKeybordKeys --->>Exception");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Return current window handle
	 * @return current window handle
	 * @author Dheerendra Singh
	 */
	public String getCurrentWindowHandle() {
		try {
			return objPojo.getWebDriverProvider().getDriver().getWindowHandle();
		}catch(Exception exception){
			System.out.println("Unable to get main window handel");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return null;
		} 
	}

	/**
	 * switchTo child Window
	 * @author Dheerendra Singh
	 **/
	public void switchToChildWindow() 
	{	
		try
		{
			objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
			String parentHandle = this.getCurrentWindowHandle();
			Set<String> windowNames = objPojo.getWebDriverProvider().getDriver().getWindowHandles();

			@SuppressWarnings("rawtypes")
			Iterator ite = windowNames.iterator();

			while (ite.hasNext()) 
			{
				String popupHandle = ite.next().toString();
				if (!popupHandle.equals(parentHandle)) {
					objPojo.getWebDriverProvider().getDriver().switchTo().window(popupHandle);
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
	 * @author Dheerendra Singh
	 **/
	public void switchToWindowUsingHandle(String windowHandle) 
	{	
		try
		{
			objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
			Set<String> windowNames = objPojo.getWebDriverProvider().getDriver().getWindowHandles();

			@SuppressWarnings("rawtypes")
			Iterator ite = windowNames.iterator();

			while (ite.hasNext()) 
			{
				String popupHandle = ite.next().toString();
				if (popupHandle.equals(windowHandle)) {
					objPojo.getWebDriverProvider().getDriver().switchTo().window(popupHandle);
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
	 * @author Dheerendra Singh
	 */
	public boolean switchToWindowUsingTitle(String windowTitle) {
		try {
			objPojo.getWaitMethods().sleep(DEFAULT_SLEEP_TIMEOUT);
			String mainWindowHandle = objPojo.getWebDriverProvider().getDriver().getWindowHandle();
			Set<String> openWindows = objPojo.getWebDriverProvider().getDriver().getWindowHandles();

			if (!openWindows.isEmpty()) {
				for (String windows : openWindows) {
					String window = objPojo.getWebDriverProvider().getDriver().switchTo().window(windows).getTitle();
					if (windowTitle.equals(window)) {
						objPojo.getLogReporter().webLog("Switched to window using title - ", windowTitle, true);
						return true;
					}
					else
						objPojo.getWebDriverProvider().getDriver().switchTo().window(mainWindowHandle);
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
	 * execute autoit script
	 * @author Dheerendra Singh
	 **/
	public void executeAutoIIscriptForFileUpload(String filePath) {
		try {
			String commandToRun = System.getProperty("user.dir") + "/src/test/resources/uploads/FileUploadScript/FileUpload.exe" + " " + filePath;
			CommandPrompt.runCommand(commandToRun);
			objPojo.getWaitMethods().sleep(objPojo.getConfiguration().getConfigIntegerValue("midwait")); 
		}catch(Exception exception){
			System.out.println("Unable to execute autoit script");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
		} 
	}

	/**
	 *  switch to the Frame by Frame name
	 *  @param : locator - The most common one. You locate your iframe like other
	 *        		elements, then pass it into the method
	 *       		eg.driver.switchTo().frame(driver.findElement(By.xpath(".//iframe[@title='Test']")))
	 *  @author Dheerendra Singh
	 */
	public void switchToFrameUsingIframe_Element(By locator) {
		try {
			objPojo.getWebDriverProvider().getDriver().switchTo().defaultContent();
			objPojo.getWebDriverProvider().getDriver().switchTo().frame(this.processElement(locator));
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
	 * @author Dheerendra Singh
	 */
	public void switchToFrameUsingNameOrId(String frameNameOrID) {
		try {
			objPojo.getWebDriverProvider().getDriver().switchTo().defaultContent();
			objPojo.getWebDriverProvider().getDriver().switchTo().frame(frameNameOrID);
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
	 * @author Dheerendra Singh
	 */
	public void switchToFrameUsingIndex(int index) {
		try {
			objPojo.getWebDriverProvider().getDriver().switchTo().defaultContent();
			objPojo.getWebDriverProvider().getDriver().switchTo().frame(index);
			System.out.println("Switched to frame");
		} catch (Exception exception) {
			System.out.println("unable to switch to frame");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
		}
	}

	/**
	 *	switch to the default content
	 * 	@author Dheerendra Singh
	 */
	public void switchToDefaultContent() {
		try {
			objPojo.getWebDriverProvider().getDriver().switchTo().defaultContent();
			System.out.println("Switched to default content");
		} catch (Exception exception) {
			System.out.println("unable to switch to default content");
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
		}
	}

	/**
	 * Highlight element
	 * @author Dheerendra Singh
	 */
	public void elementHighlight(By locator) {
		WebElement element = this.processElement(locator);
		for (int i = 0; i < 10; i++) {
			JavascriptExecutor js = (JavascriptExecutor) objPojo.getWebDriverProvider().getDriver();
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: red; border: 3px solid red;");
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		}
	}

	/**
	 * Execute javascript
	 * @author Dheerendra Singh
	 */
	public void executeJavascript(String javascript) {
		JavascriptExecutor js = (JavascriptExecutor) objPojo.getWebDriverProvider().getDriver();
		js.executeScript(javascript); 
	}

	/*******************************************************
	 * 
	 * Following are common function examples update as per project need 
	 * 
	 *******************************************************/

	/**
	 * Check given content is available under whole table with respect to table header
	 * @param : locator - By identification of element (xpath up to table )
	 * @param : columnHeader - String column header
	 * @param : ContentToVerify - String Content to be verify 
	 * @author Dheerendra Singh
	 */
	public boolean verifyTableContent(By locator, String columnHeader, String ContentToVerify) {
		Hashtable<String, String> dataColumnHeader = new Hashtable<String, String>();
		int intColumnNumber = 1;
		boolean blnverify = false;
		try {
			WebElement weResultTable = this.processElement(locator);
			List<WebElement> weColumnsHeaders = weResultTable.findElements(By.xpath(".//thead/tr/th"));
			for (WebElement weColumnHeader : weColumnsHeaders) {
				String strHeader = weColumnHeader.getText().trim();
				if (!strHeader.equals(""))
					dataColumnHeader.put(strHeader, String.valueOf(intColumnNumber));
				intColumnNumber++;
			}

			List<WebElement> weRows = weResultTable.findElements(By.xpath(".//tbody/tr"));
			for (WebElement weRow : weRows) {
				WebElement weExceptedClm = weRow.findElement(By.xpath
						(".//td[" + dataColumnHeader.get(columnHeader) + "]"));
				if (weExceptedClm.getText().trim().equals(ContentToVerify)) {
					blnverify = true;
					return blnverify;
				}
			}
			return blnverify;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * Common function to verify column content
	 * @author Dheerendra Singh
	 **/
	public boolean verifyGraphContentAgainstToColumn(By locatorGraph, String XaxisHeader, String contentToVerifyFirst)
	{
		Hashtable<String, String> dataColumnHeader = new Hashtable<String, String>();
		int intColumnNumber = 1;
		boolean blnverify = false;

		try {
			WebElement weGraph = this.processElement(locatorGraph);
			List<WebElement> weGraphXAxis = weGraph.findElements(By.xpath(".//*[@class='highcharts-axis-labels highcharts-xaxis-labels']//*"));
			for (WebElement weXAxisHeader : weGraphXAxis)
			{
				String strHeader = weXAxisHeader.getText().trim();
				if (!strHeader.equals(""))
					dataColumnHeader.put(strHeader, String.valueOf(intColumnNumber));
				intColumnNumber++;
			}

			WebElement weExceptedYAxisHeader = weGraph.findElement(By.xpath(".//*[@class='highcharts-data-labels highcharts-series-0 highcharts-tracker']/*[" + dataColumnHeader.get(XaxisHeader) + "]/*/*"));
			System.out.println(" weExceptedClm : " + weExceptedYAxisHeader.getText());
			System.out.println(" contentToVerifyFirst : " + contentToVerifyFirst);
			if (weExceptedYAxisHeader.getText().trim().equalsIgnoreCase(contentToVerifyFirst))
			{
				blnverify = true;
				System.out.println("blnverify" + blnverify);
				return blnverify;
			}

			return blnverify;
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			if(objPojo.getConfiguration().getConfigBooleanValue("printStackTraceForActions"))
				exception.printStackTrace();
			return false;
		}
	}	 
}