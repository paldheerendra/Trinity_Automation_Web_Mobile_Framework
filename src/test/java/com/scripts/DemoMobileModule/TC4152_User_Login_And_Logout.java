package com.scripts.DemoMobileModule;

import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.generic.BaseTest;
import com.generic.customAnnotation.TestDataSource;
import com.pageFactory.Mobile.MainPage;
import com.view.DemoMobileModule.CommonView;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

/**	
 *  Login and Logout 
 * 	@author : Harshvardhan Yadav (Expleo)
 */
public class TC4152_User_Login_And_Logout extends BaseTest 
{
	@Title("Test #4152")
	@Description("User Login and Logout ")
	@TestDataSource(testCaseID = "TC4152", dataSource = "/Regression/Regression")
	@Test(dataProvider = "TestDataProvider", groups = {"Mobile", "Regression"})
	public void tc4152_userLoginAndLogout(Hashtable<String, String> dataSet)
	{
		initializeMobileEnvironment(dataSet);
		
		PageObjectManager(MainPage.class).verifyMeccaLogoDisplayed();
		PageObjectManager(CommonView.class).login(dataPool("UserName"), dataPool("Password"));
		PageObjectManager(CommonView.class).logout();
	}

	@AfterMethod(groups = {"Mobile"})
	private void tearDown(){
		tearDownMobileEnvironment(); 
	}
}