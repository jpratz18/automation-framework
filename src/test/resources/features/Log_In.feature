@login @regresion

Feature: WebDriver University - Login Page

  Background:
    Given I access the webdriver university login page

  Scenario: Validate Successful login
    When I enter an user webdriver
    And I enter a password webdriver123
    And I click on the login button
    Then I should be presented with the successful login message

  Scenario: Validate Failed log in
    When I enter an user sasa
    And I enter a password webdriver123
    And I click on the login button
    Then I should be presented with the unsuccessful login message

    @smoke
  Scenario Outline: Validate - Successful & Unsuccessful login
    When I enter an user <username>
    And I enter a password <password>
    And I click on the login button
    Then I should be presented with the following login validation message <loginValidationMessage>

    Examples:
      | username  | password     | loginValidationMessage |
      | webdriver | webdriver123 | validation succeeded   |
      | webdriver | webdriver1   | validation failed      |
      | juan      | password1    | validation failed      |