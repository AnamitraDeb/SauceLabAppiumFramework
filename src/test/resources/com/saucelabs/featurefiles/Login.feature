Feature: Login Scenarios

  Scenario Outline: Login with invalid username, invalid password and locked out user credentials
    Given user logs in with username as "<username>" and password as "<password>"
    Then verify error message as "<errormessage>"

    Examples: 
      | username        | password        | errormessage                                                 |
      | invalidUserName | secret_sauce    | Username and password do not match any user in this service. |
      | standard_user   | invalidPassword | Username and password do not match any user in this service. |
      | locked_out_user | secret_sauce    | Sorry, this user has been locked out.                        |

  Scenario Outline: Login with valid username and password
    Given user logs in with username as "<username>" and password as "<password>"
    Then verify if login is successful

    Examples: 
      | username      | password     |
      | standard_user | secret_sauce |

  Scenario Outline: Login with valid username and password and then logout
    Given user logs in with username as "<username>" and password as "<password>"
    Then verify if login is successful
    And user logs out
    Then verify if logout is successful

    Examples: 
      | username      | password     |
      | standard_user | secret_sauce |
