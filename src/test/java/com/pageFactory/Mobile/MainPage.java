package com.pageFactory.Mobile;

import org.openqa.selenium.By;

import com.generic.Pojo;

public class MainPage {

	// Local variables
	private Pojo objPojo;

	// Input

	// Button 
	private By btnSignIn = By.xpath("//div[@class='header-top-wrapper']//button[@class='btn toggle-item-button']/following-sibling::p[contains(text(),'Sign In')]");
	private By btnJoin = By.xpath("//div[@class='header-top-wrapper']//p[contains(text(),'Join')]");
	private By btnContinueForCookie = By.xpath("//button[contains(text(),'Continue') and contains(@class,'js-cookie')]");
	private By btnLearnMorePlayPoints = By.xpath("//div[contains(text(),'You have') and contains(text(),'playpoints')]/following-sibling::a[contains(text(),'Learn More')]");
	 
	// logo
	private By lgMeccaLogo = By.xpath("//div[@class='header-top-wrapper']//div[@class='header-logo-wrapper']/div[@class='header-logo']");

	// Link
	private By lnkLogout = By.xpath("//a[contains(text(),'Logout')]");

	// Div
	private By dvSignedInUserName = By.xpath("//div[@class='user-greeting-text-container']/p[@class='user-greeting-text']");
	private By dvBalance = By.xpath("//div[@class='balance-amount-container']/span[contains(text(),'Balance')]");
	private By dvUserPlayPoints = By.xpath("//div[contains(text(),'You have') and contains(text(),'playpoints')]");

	// Section
	private By secBingo = By.xpath("//div[@class='js-home-menu-title title']//a[contains(text(),'Bingo')]");
	private By secSlotAndGames = By.xpath("//div[@class='js-home-menu-title title']//a[contains(text(),'Slots & Games')]");
	private By secClub = By.xpath("//div[@class='js-home-menu-title title']//div[contains(text(),'Clubs')]");

	// img
	private By imgSliders = By.xpath("//img[@class='layout-content-image']"); 

	public MainPage(Pojo pojo){
		this.objPojo = pojo;
	}
	
	public void verifyMeccaLogoDisplayed() {
		objPojo.getLogReporter().mobileLog("Verify Mecca logo displayed",  
				objPojo.getMobileActions().checkElementDisplayed(lgMeccaLogo));
		this.clickContinueForCookie();
	}
	
	public void clickMeccaLogo() {
		objPojo.getLogReporter().mobileLog("Click on Mecca logo",  
				objPojo.getMobileActions().click(lgMeccaLogo));
		this.clickContinueForCookie();
	}

	public void clickSignIn() {
		objPojo.getLogReporter().mobileLog("Click Sign In",  
				objPojo.getMobileActions().click(btnSignIn));
	}

	public void clickJoin() {
		objPojo.getLogReporter().mobileLog("Click Join",  
				objPojo.getMobileActions().click(btnJoin));
	} 

	public void verifySignInButtonDisplayed() {
		objPojo.getLogReporter().mobileLog("Verify Sign In button displayed",  
				objPojo.getMobileActions().checkElementDisplayed(btnSignIn));
	}

	public void verifyLogoutLinkDisplayed() {
		objPojo.getLogReporter().mobileLog("Verify logout link displayed",  
				objPojo.getMobileActions().checkElementDisplayed(lnkLogout));
	}

	public void clickLogout() {
		objPojo.getLogReporter().mobileLog("Click Logout",  
				objPojo.getMobileActions().click(lnkLogout));
	} 

	public void clickContinueForCookie() {
		if(objPojo.getMobileActions().checkElementDisplayedWithMinWait(btnContinueForCookie))
			objPojo.getLogReporter().mobileLog("Click Continue For Cookie",  
					objPojo.getMobileActions().click(btnContinueForCookie));
	}

	public void selectHeaderMenu(String menu) {
		By locator = By.xpath("//div[@class='header-controls ']//span[contains(text(),'" + menu + "')]");
		objPojo.getLogReporter().mobileLog("Select header menu", menu,
				objPojo.getMobileActions().click(locator));
	}

	public void verifySignedInUserName(String userName) {
		objPojo.getLogReporter().mobileLog("Verify signed in user name", userName, 
				objPojo.getMobileActions().getText(dvSignedInUserName).equalsIgnoreCase(userName));
	}

	public void verifyBalanceFieldDisplayed() {
		objPojo.getLogReporter().mobileLog("Verify user balanced field displayed",  
				objPojo.getMobileActions().checkElementDisplayed(dvBalance));
	}

	public void selectMenu(String mainMenu, String... sunMenu) {
		By locator = By.xpath("//div[@class='header-controls']/button/p[contains(text(),'Menu')]");
		objPojo.getLogReporter().mobileLog("Click on menu button",  
				objPojo.getMobileActions().click(locator));

		locator = By.xpath("//div[@class='menubar']");
		objPojo.getLogReporter().mobileLog("Verify menubar displayed",  
				objPojo.getMobileActions().checkElementDisplayed(locator));

		if(sunMenu != null && sunMenu.length > 0) {
			locator = By.xpath("//ul[@class='menubar-list-centered js-menubar']/li/div/a[contains(text(),'" + mainMenu + "')]/following-sibling::a");
			objPojo.getLogReporter().mobileLog("Select main menu", mainMenu, 
					objPojo.getMobileActions().click(locator));

			locator = By.xpath("//div[@class='menubar-submenu collapse in']/ul/li/a[contains(text(),'" + sunMenu + "')]");
			objPojo.getLogReporter().mobileLog("Select sub menu", sunMenu[0], 
					objPojo.getMobileActions().click(locator));
		}
		else {
			locator = By.xpath("//ul[@class='menubar-list-centered js-menubar']/li/div/a[contains(text(),'" + mainMenu +"')]");
			objPojo.getLogReporter().mobileLog("Select main menu", mainMenu, 
					objPojo.getMobileActions().click(locator));
		}
	}

	public void verifyGameAvailableInFavouritesMenu(String gameTitle) {
		By locator = By.xpath("//strong[contains(text(),'" + gameTitle + "')]/ancestor::section/parent::div[contains(@class,'js-favourites-swiper-wrapper')]");
		objPojo.getLogReporter().mobileLog("Verify game is available in Favourites menu", gameTitle, 
				objPojo.getMobileActions().checkElementDisplayed(locator));
	}

	public void verifyGameNotAvailableInFavouritesMenu(String gameTitle) {
		By locator = By.xpath("//strong[contains(text(),'" + gameTitle + "')]/ancestor::section/parent::div[contains(@class,'js-favourites-swiper-wrapper')]");
		objPojo.getLogReporter().mobileLog("Verify game is not available in Favourites menu", gameTitle, 
				!objPojo.getMobileActions().checkElementDisplayedWithMidWait(locator));
	}

	public void verifyBingoSectionDisplayed() {
		objPojo.getLogReporter().mobileLog("Verify Bingo section displayed",  
				objPojo.getMobileActions().checkElementDisplayed(secBingo));
	}

	public void verifySlotsAndGamesSectionDisplayed() {
		objPojo.getLogReporter().mobileLog("Verify Slots & Games section displayed",  
				objPojo.getMobileActions().checkElementDisplayed(secSlotAndGames));
	}

	public void verifyClubSectionDisplayed() {
		objPojo.getLogReporter().mobileLog("Verify Club section displayed",  
				objPojo.getMobileActions().checkElementDisplayed(secClub));
	}

	public void verifyFooterSectionLinksDisplayed() {
		By locator = By.xpath("//img[@alt='Facebook']");
		objPojo.getLogReporter().mobileLog("Verify 'Facebook' link displayed",  
				objPojo.getMobileActions().checkElementDisplayed(locator));

		locator = By.xpath("//img[@alt='Twitter']");
		objPojo.getLogReporter().mobileLog("Verify 'Twitter' link displayed",  
				objPojo.getMobileActions().checkElementDisplayed(locator));

		locator = By.xpath("//a[contains(@href,'instagram.com')]");
		objPojo.getLogReporter().mobileLog("Verify 'Instagram' link displayed",  
				objPojo.getMobileActions().checkElementDisplayed(locator));

		locator = By.xpath("//img[@alt='YouTube']");
		objPojo.getLogReporter().mobileLog("Verify 'YouTube' link displayed",  
				objPojo.getMobileActions().checkElementDisplayed(locator));

		locator = By.xpath("//a[contains(text(),'Play bingo online')]");
		objPojo.getLogReporter().mobileLog("Verify 'Play bingo online' link displayed",  
				objPojo.getMobileActions().checkElementDisplayed(locator));

		locator = By.xpath("//span[contains(text(),'Responsible Gaming')]/parent::a");
		objPojo.getLogReporter().mobileLog("Verify 'Responsible Gaming' link displayed",  
				objPojo.getMobileActions().checkElementDisplayed(locator));

		locator = By.xpath("//span[contains(text(),'Help Centre')]/parent::a");
		objPojo.getLogReporter().mobileLog("Verify 'Help Centre' link displayed",  
				objPojo.getMobileActions().checkElementDisplayed(locator));

		locator = By.xpath("//span[contains(text(),'Contact Us')]/parent::a");
		objPojo.getLogReporter().mobileLog("Verify 'Contact Us' link displayed",  
				objPojo.getMobileActions().checkElementDisplayed(locator));
	}

	public void verifySliderDisplayed() {
		objPojo.getLogReporter().mobileLog("Verify sliders displayed",  
				objPojo.getMobileActions().checkElementDisplayed(imgSliders));
	}

	public void verifyUserPlayPointDisplayed() {
		objPojo.getLogReporter().mobileLog("Verify user playpoints displayed",  
				objPojo.getMobileActions().checkElementDisplayed(dvUserPlayPoints));
	}
	
	public void clickPlayPointLearnMoreDisplayed() {
		objPojo.getLogReporter().mobileLog("Click playpoints 'Learn More' ",  
				objPojo.getMobileActions().click(btnLearnMorePlayPoints));
	}
}