
@login
Feature: Login Functionality

  Scenario: Successful Login
    Given User navigates to "https://qavn.asia:444" using Firefox
    When User enters "admin" in the username field "UserName"
    And User enters "1" in the password field "Password"
    And User clicks the login button "ses-submit-btn"
    Then User should be redirected to "https://qavn.asia:444/Folder/ViewFolder" within 5 seconds

  Scenario: Unsuccessful Login
    Given User navigates to "https://qavn.asia:444" using Firefox
    When User enters an invalid username in the field "UserName"
    And User enters an invalid password in the field "Password"
    And User clicks the login button "ses-submit-btn"
    Then User should see an error message containing "Login was unsuccessful"


