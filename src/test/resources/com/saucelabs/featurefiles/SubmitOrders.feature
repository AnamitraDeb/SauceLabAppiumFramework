Feature: Submit Order Scenarios

  Scenario Outline: User logs in and submits order for a given product
    Given user logs in with username as "<username>" and password as "<password>"
    Then verify if login is successful
    When user checks product "<productname>" from product catalogue
    And user check for the product "<productname>" details form product details page
    When user adds the product  to cart
    Then user checks details of product "<productname>" from cart page
    When user checkouts cart items
    And user enters user information as "<firstname>" "<lastname>" and "<zipcode>"
    When user navigates to review page
    Then user checks details of product "<productname>" from review page
    When user finish submitting order
    And navigates to product page
    And user logs out
    Then verify if logout is successful

    Examples: 
      | username      | password     | productname       | firstname | lastname | zipcode |
      | standard_user | secret_sauce | Sauce Labs Onesie | Anamitra  | Deb      |  700055 |
