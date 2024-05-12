Feature: Product Scenarios

  Scenario Outline: User logs in and tries to check the detail of a given product
    Given user logs in with username as "<username>" and password as "<password>"
    Then verify if login is successful
    When user checks product "<productname>" from product catalogue
    And user check for the product "<productname>" details form product details page
    And user logs out
    Then verify if logout is successful

    Examples: 
      | username      | password     | productname       |
      | standard_user | secret_sauce | Sauce Labs Onesie |
