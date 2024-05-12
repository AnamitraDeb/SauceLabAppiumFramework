package com.saucelabs.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.saucelabs.gestures.AndroidGestures;

import io.appium.java_client.AppiumDriver;

public class CartPage extends HeaderPage {
	
	AppiumDriver driver;

	public CartPage(AppiumDriver driver) 
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//*[@content-desc='test-Item']")
	private List<WebElement> cartItems;
	
	@FindBy(xpath ="//*[@content-desc='test-Description']/android.widget.TextView[1]")
	private List<WebElement> cartProductNames;
	
	@FindBy(xpath ="//*[@content-desc='test-Description']/android.widget.TextView[2]")
	private List<WebElement> cartProductDescriptions;
	
	@FindBy(xpath ="//*[@content-desc='test-Price']/android.widget.TextView")
	private List<WebElement> cartProductPrices;
	
	@FindBy(xpath ="//*[@content-desc='test-REMOVE']")
	private List<WebElement> cartProductRemoveButtons;
	
	@FindBy(xpath = "//*[@content-desc='test-CONTINUE SHOPPING']")
	private WebElement continueShoppingButton;
	
	@FindBy(xpath = "//*[@content-desc='test-CHECKOUT']")
	private WebElement checkoutButton;
	
	public int getNumberofItemsInCart()
	{
		return cartItems.size();
	}
	public List<String> getNameOfProductsInCart()
	{
		return cartProductNames.stream().map(cartProductName -> cartProductName.getText()).collect(Collectors.toList());
	}

	public String getCartItemDetails(String productName) throws InterruptedException
	{
		String itemName="", itemPrice="";
		List<String> cartItemsList = getNameOfProductsInCart();
		while(true)
		{
			cartItemsList = getNameOfProductsInCart();
			if(cartItemsList.contains(productName))
			{
				break;
			}
			else
			{
				AndroidGestures.scrollToText(driver, productName);
				Thread.sleep(3000);
			}
		}
		WebElement item = cartProductNames.stream().filter(cartProductName -> cartProductName.getText().equalsIgnoreCase(productName)).
                findFirst().orElse(null);
		
		itemName = item.getText();
		//System.out.println(itemName);
		try
		{
		itemPrice = cartProductPrices.get(cartItemsList.indexOf(itemName)).getText();
		}
		catch(Exception e)
		{
			 AndroidGestures.scroll(driver);
			 Thread.sleep(3000);
			 cartItemsList = getNameOfProductsInCart();
             itemPrice = cartProductPrices.get(cartItemsList.indexOf(itemName)).getText();
		}
		return (itemName + " " + itemPrice);
	}
	
	public UserInformationPage checkOutCartItems()
	{
		AndroidGestures.scrollToText(driver, "CHECKOUT");
		checkoutButton.click();
		UserInformationPage userInformationPage = new UserInformationPage(driver);
		return userInformationPage;
	}
	
	

}
