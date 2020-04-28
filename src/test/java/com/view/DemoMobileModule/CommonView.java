package com.view.DemoMobileModule;

import com.generic.BaseView;
import com.generic.Pojo;
import com.pageFactory.Mobile.MainPage;

import ru.yandex.qatools.allure.annotations.Step;

public class CommonView extends BaseView
{
 	public CommonView(Pojo pojo){
		super(pojo);
	 }
  
 	@Step("User Login")
	public void login(String userName, String password) 
	{
		PageObjectManager(MainPage.class).clickSignIn();
		/*	PageObjectManager(LoginPage.class).setUserName(userName);
		PageObjectManager(LoginPage.class).setPassword(password);
		PageObjectManager(LoginPage.class).clickSignIn();
		PageObjectManager(LoginPage.class).clickImportantAccountInformationOK(); */
		PageObjectManager(MainPage.class).verifyLogoutLinkDisplayed();
	} 
	
	@Step("User Logout")
	public void logout() 
	{
		PageObjectManager(MainPage.class).clickLogout(); 
		PageObjectManager(MainPage.class).verifySignInButtonDisplayed();
	} 
}