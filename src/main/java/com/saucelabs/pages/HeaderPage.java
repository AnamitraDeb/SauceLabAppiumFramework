package com.saucelabs.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;


public class HeaderPage {
	
	AppiumDriver driver;
	
	public HeaderPage(AppiumDriver driver)
	{
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//*[@content-desc='test-Menu']")
	public WebElement settingsButton;
	
	@FindBy(xpath = "//*[@content-desc='test-Cart']")
	public WebElement cartButton;
	
	@FindBy(xpath = "(//android.widget.ImageView)[3]")
	public WebElement swagLabsLogo;
	
	@FindBy(xpath = "//*[@content-desc='test-Cart']/android.view.ViewGroup/android.widget.TextView")
	public WebElement noOfCartItems;
	
	public int getNumberOfItemsAddedInCart()
	{
		return Integer.parseInt(noOfCartItems.getText());
	}
	
	public SettingsPage goToSettingsPage()
	{
		settingsButton.click();
		SettingsPage settingsPage = new SettingsPage(driver);
		return settingsPage;
	}
	
	public CartPage goToCartsPage()
	{
		cartButton.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	

}
