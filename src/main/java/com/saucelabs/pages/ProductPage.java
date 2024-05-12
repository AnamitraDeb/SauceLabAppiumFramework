package com.saucelabs.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.saucelabs.gestures.AndroidGestures;

import io.appium.java_client.AppiumDriver;

public class ProductPage extends HeaderPage {
	
	 AppiumDriver driver;
		
		public ProductPage(AppiumDriver driver)
		{
			super(driver);
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}
		
		@FindBy(xpath = "//*[@text='PRODUCTS']")
		 private WebElement productPageDashboardProductTitle;
		
		@FindBy(xpath = "//*[@content-desc='test-Toggle']")
		private WebElement productListViewSwitcher;
		
		@FindBy(xpath = "//*[@content-desc='test-Modal Selector Button']")
		private WebElement filterButton;
		
		@FindBy(xpath = "//*[@content-desc='test-Item title']")
		private List<WebElement> productTitles;
		
		@FindBy(xpath = "//*[@content-desc='test-Price']")
		private List<WebElement> productPrices;
		
		@FindBy(xpath = "//*[@content-desc='test-ADD TO CART']")
		private List<WebElement> addToCartButon;
		
		
		
		public boolean productDashboardDisplay() 
		{
			return productPageDashboardProductTitle.isDisplayed();
		}
		
		public List<String> getProductNames() {
			return productTitles.stream().map(productTitle -> productTitle.getText()).collect(Collectors.toList());
		}
		
		
		public WebElement getProductWithName(String productName)
		{
			List<String> products;
			boolean canScrollMore = true;
			while(canScrollMore)
			{
				products = getProductNames();
				if(products.contains(productName))
				  canScrollMore = false;
				else
					AndroidGestures.scroll(driver);
			}
			WebElement product = productTitles.stream().filter(productTitle -> productTitle.getText().equalsIgnoreCase(productName)).
			              findFirst().orElse(null);
			return product;
		}
		
		public String getProductDetailsFromProductCataloguePage(String productName)
		{
			WebElement product = getProductWithName(productName);
			String productPrice = productPrices.get(productTitles.indexOf(product)).getText();
			String res = productName+ " " + productPrice;
			return res;		
		}
		
		public ProductDetailsPage goToProductDetailsPage(String productName)
		{
			WebElement product = getProductWithName(productName);
			product.click();
			ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
			return productDetailsPage;	
		}
		
		
		

}
