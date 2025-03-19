```gherkin
Feature: User Login

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters valid username "testuser"
    And the user enters valid password "password123"
    And the user clicks the login button
    Then the user should be redirected to the home page
    And the user should see a welcome message

  Scenario Outline: Unsuccessful login with invalid credentials
    Given the user is on the login page
    When the user enters username "<username>"
    And the user enters password "<password>"
    And the user clicks the login button
    Then the user should see an error message "<error_message>"

    Examples:
      | username | password    | error_message                        |
      |          | password123 | Please enter your username.          |
      | testuser |            | Please enter your password.          |
      | wronguser| wrongpass  | Invalid username or password.       |
      | testuser | wrongpass  | Invalid username or password.       |


```
