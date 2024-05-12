package com.saucelabs.utilities;

import com.saucelabs.pages.CartPage;
import com.saucelabs.pages.ConfirmationPage;
import com.saucelabs.pages.LoginPage;
import com.saucelabs.pages.ProductDetailsPage;
import com.saucelabs.pages.ProductPage;
import com.saucelabs.pages.ReviewPage;
import com.saucelabs.pages.SettingsPage;
import com.saucelabs.pages.UserInformationPage;

import io.appium.java_client.AppiumDriver;

public class PageObjectManager {
	
	AppiumDriver driver;
	
	public PageObjectManager(AppiumDriver driver)
	{
		this.driver = driver;
	}
	
	public CartPage getCartPage()
	{
		return new CartPage(driver);
	}
	
	public ConfirmationPage getConfirmationPage()
	{
		return new ConfirmationPage(driver);
	}
	
	public LoginPage getLoginPage()
	{
		return new LoginPage(driver);
	}
	
	public ProductDetailsPage getProductDetailsPage()
	{
		return new ProductDetailsPage(driver);
	}
	
	public ProductPage getProductPage()
	{
		return new ProductPage(driver);
	}
	
	public ReviewPage getReviewPage()
	{
		return new ReviewPage(driver);
	}
	
	public SettingsPage getSettingsPage()
	{
		return new SettingsPage(driver);
	}
	
	public UserInformationPage getUserInformationPage()
	{
		return new UserInformationPage(driver);
	}

}
