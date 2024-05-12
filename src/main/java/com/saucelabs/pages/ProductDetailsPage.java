package com.saucelabs.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.saucelabs.gestures.AndroidGestures;

import io.appium.java_client.AppiumDriver;

public class ProductDetailsPage extends HeaderPage {
	
	AppiumDriver driver;
	
	public ProductDetailsPage(AppiumDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "(//*[@content-desc='test-Description']/android.widget.TextView)[1]")
	private WebElement productName;
	
	@FindBy(xpath = "(//*[@content-desc='test-Description']/android.widget.TextView)[2]")
	private WebElement productDescription;
	
	@FindBy(xpath = "//*[@content-desc='test-Price']")
	private WebElement productPrice;
	
	@FindBy(xpath = "//*[@content-desc='test-ADD TO CART']")
	private WebElement addToCartButonDetailspage;
	
	@FindBy(xpath = "//*[@content-desc='test-BACK TO PRODUCTS']")
	private WebElement backToProductsPageButton;
	
	
	public String getProductTitle()
	{
		return productName.getText();		
	}
	public String getProductDescription()
	{
		return productDescription.getText();		
	}
	public String getProductPrice()
	{
		AndroidGestures.scroll(driver);
		return productPrice.getText();		
	}
	
	public String getProductDetailsFromProductDetailsPage()
	{
		System.out.println("Description of the product" + productDescription.getText());
		String res = productName.getText() + " " + productPrice.getText();
		return res;
		
	}
	
	public ProductPage goBackToProductsPage()
	{
		backToProductsPageButton.click();
		ProductPage ProductPage = new ProductPage(driver);
		return ProductPage;
	}
	
	public void addToCart()
	{
		addToCartButonDetailspage.click();
	}

}
