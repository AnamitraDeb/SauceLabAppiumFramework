package com.saucelabs.stepdefinitions;

import java.util.List;

import org.testng.Assert;

import com.saucelabs.pages.CartPage;
import com.saucelabs.pages.ConfirmationPage;
import com.saucelabs.pages.ProductDetailsPage;
import com.saucelabs.pages.ProductPage;
import com.saucelabs.pages.ReviewPage;
import com.saucelabs.pages.UserInformationPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SubmitOrderStepDefintions {
	
	public ProductPage productPage;
	public ProductDetailsPage productDetailsPage;
	public CartPage cartPage;
	public UserInformationPage userInformationPage;
	public ReviewPage reviewPage;
	public ConfirmationPage confirmationPage;

	public static String detailsRetrievedFromProductCatalogue;
	public static String detailsRetrievedFromProductDetailsPage;
	public static String detailsRetrievedFromCartPage;
	public static String detailsRetrievedFromReviewPage;
	
	List<String> confirmationMessage;
	
	public SubmitOrderStepDefintions()
	{
		productDetailsPage = Hooks.pageObjectManager.getProductDetailsPage();
	}

	@When("user adds the product  to cart")
	public void user_adds_the_product_to_cart() {
		productDetailsPage.addToCart();
		cartPage = productDetailsPage.goToCartsPage();
	}

	@Then("user checks details of product {string} from cart page")
	public void user_checks_details_of_product_from_cart_page(String productname) throws InterruptedException {
		detailsRetrievedFromCartPage = cartPage.getCartItemDetails(productname);
		Assert.assertEquals(detailsRetrievedFromCartPage, ProductStepDefintions.detailsRetrievedFromProductDetailsPage,
				"Product Details not matching");
	}

	@When("user checkouts cart items")
	public void user_checkouts_cart_items() {
		userInformationPage = cartPage.checkOutCartItems();
	}

	@When("user enters user information as {string} {string} and {string}")
	public void user_enters_user_information_as_and(String firstname, String lastname, String zipcode) {
		userInformationPage.enterDetails(firstname, lastname, zipcode);

	}

	@When("user navigates to review page")
	public void user_navigates_to_review_page() {
		reviewPage = userInformationPage.goToReviewPage();
	}

	@Then("user checks details of product {string} from review page")
	public void user_checks_details_of_product_from_review_page(String productname) throws InterruptedException {
		detailsRetrievedFromReviewPage = reviewPage.getProductDetailsFromReviewPage(productname);
	    Assert.assertEquals(detailsRetrievedFromReviewPage,detailsRetrievedFromCartPage , "Product Details not matching");
	}

	@When("user finish submitting order")
	public void user_finish_submitting_order() {
		confirmationPage = reviewPage.finishOrder();
	    confirmationMessage = confirmationPage.getConfirmationMessage();

	}

	@Then("user sees confirmation message")
	public void user_sees_confirmation_message() {
		confirmationMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.contains("THANK YOU FOR YOU ORDER"), "Order not submitted");
	}

	@Then("navigates to product page")
	public void navigates_to_product_page() throws InterruptedException {
		productPage = confirmationPage.goToProductsPage();
		Thread.sleep(1000);
	}
	
}
