package com.saucelabs.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;

public class SettingsPage {
AppiumDriver driver;
	
	public SettingsPage(AppiumDriver driver)
	{
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//*[@content-desc='test-LOGOUT']")
	private WebElement logOutButton;
	
	@FindBy(xpath = "//*[@content-desc='test-ALL ITEMS']")
	private WebElement allItemsButton;
	
	@FindBy(xpath = "//*[@content-desc='test-Close']/android.widget.ImageView")
	private WebElement settingsPageCloseButton;
	
	
	
	public LoginPage logOut()
	{
		logOutButton.click();
		LoginPage loginPage = new LoginPage(driver);
		return loginPage;
	}
	
	public ProductPage goToProductsPage()
	{
		allItemsButton.click();
		ProductPage productPage = new ProductPage(driver);
		return productPage;
	}

}
