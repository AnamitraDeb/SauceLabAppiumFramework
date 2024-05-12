package com.saucelabs.tests;

import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.saucelabs.pages.ProductPage;
import com.saucelabs.pages.SettingsPage;
import com.saucelabs.testutils.BaseTest;
import com.saucelabs.utilities.DataLoader;

public class LoginTests extends BaseTest {
	
	ProductPage productPage;
	SettingsPage settingsPage;
	
	@Test(description = "User tries to login with invalid username", dataProvider = "getData", 
		  retryAnalyzer = com.saucelabs.listeners.RetryListener.class)
	public void loginWithInvalidUserName(HashMap<String, String> input) 
	{		
		logger.info("Logging in with invalid username and password");
		
		loginPage.loginWithCreds(input.get("invalidUsername"), input.get("validPassword"));
		
		logger.info("Retrieving error message");
		
		String errorPopupText = loginPage.getErrorMessage();

		Assert.assertEquals(errorPopupText, "Username and password do not match any user in this service.",
				"Login error message not retrieved or content not matching");
	}

	@Test(description = "User tries to login with invalid password", dataProvider = "getData",
		  retryAnalyzer = com.saucelabs.listeners.RetryListener.class)
	public void loginWithInvalidPassword(HashMap<String, String> input)
	{
		logger.info("Logging in with username and invalid password");

		loginPage.loginWithCreds(input.get("validUsername"), input.get("invalidPassowrd"));
		
		logger.info("Retrieving error message");
		
		String errorPopupText = loginPage.getErrorMessage();

		Assert.assertEquals(errorPopupText, "Username and password do not match any user in this service.",
				"Login error message not retrieved or content not matching");

	}
	
	@Test(description = "User tries to login with locked out credentials", dataProvider = "getData",
			  retryAnalyzer = com.saucelabs.listeners.RetryListener.class)
	public void loginWithLockedOutCreds(HashMap<String, String> input)
	{

		logger.info("Logging in with with locked out credentials");
		
		loginPage.loginWithCreds(input.get("lockedOutUsername"), input.get("validPassword"));
		
		logger.info("Retrieving error message");
		
		String errorPopupText = loginPage.getErrorMessage();

		Assert.assertEquals(errorPopupText, "Sorry, this user has been locked out.",
				"Login error message not retrieved or content not matching");

	}
	
	@Test(description = "User tries to login with valid credentials", dataProvider = "getData",
		  retryAnalyzer = com.saucelabs.listeners.RetryListener.class)
	public void loginWithValidCreds(HashMap<String, String> input)
	{ 
		 logger.info("Logging in with valid credentials");
		
		 ProductPage productPage = loginPage.loginWithCreds(input.get("validUsername"), input.get("validPassword"));
		 
		 logger.info("Checking for successful login");
		 
		 Assert.assertTrue(productPage.productDashboardDisplay(), "Login Failed");
		
	}
	
	@Test(description = "User tries to login with valid credentials and then log out", dataProvider = "getData",
			  retryAnalyzer = com.saucelabs.listeners.RetryListener.class)
		public void loginWithValidCredsAndThenLogout(HashMap<String, String> input)
		{
		    logger.info("Logging in with valid credentials");
			
		    productPage = loginPage.loginWithCreds(input.get("validUsername"), input.get("validPassword"));
		    
		    logger.info("Checking for successful login");
			
			Assert.assertTrue(productPage.productDashboardDisplay(), "Login Failed");
			
			logger.info("Logging out");
			
			settingsPage = productPage.goToSettingsPage();
			loginPage = settingsPage.logOut();
			
			Assert.assertTrue(loginPage.isUserTextFieldDisplayed(), "Logout Failed");
		}
	
	
	@DataProvider(name = "getData")
	public Object[][] getData()
	{
		List<HashMap<String, String>> dataList = DataLoader.readJsonFiles("loginUsers.json");
		return DataLoader.getMappedData(dataList);
		
	}
}