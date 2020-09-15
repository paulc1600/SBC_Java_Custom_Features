@quoteOOPSuite1
Feature: Quote OOP Test Suite 1

  @quoteScenario13-0
  Scenario: Required fields for quote oop
    Given I open "quote" page
    Given Tool to get "quote" test data from source "user" "file"
    When I fill out required fields for "user" oop
    And I submit the form oop
    Then I verify required fields for "user" oop

  @quoteScenario13-1
  Scenario: All fields for quote oop
    Given I open "quote" page
    Given Tool to get "quote" test data from source "user" "file"
    When I fill out required fields for "user" oop
    When I fill out optional fields for "user" oop
    And I submit the form oop
    Then I verify required fields for "user" oop
    Then I verify optional fields for "user" oop

  @quoteScenario13-3
  Scenario: Required fields test oop
    Given I open "quote" page
    Given Tool to get "quote" test data from source "user" "file"
    And I submit the form oop
    Then I see "username" error message "This field is required."
    And I see "email" error message "This field is required."
    And I see "password" error message "This field is required."
    And I see "name" error message "This field is required."
    And I see "agreedToPrivacyPolicy" error message "- Must check!"

  @quoteScenario13-4
  Scenario: Market username field limits test
    Given I open "quote" page
    Given Tool to get "quote" test data from source "user" "file"
    When I fill out "username" field with "a"
    And I submit the form oop
    Then I see "username" error message "Please enter at least 2 characters."
    When I fill out "username" field with "ab"
    Then I don't see "username" error message

  @quoteScenario13-5
  Scenario: Market email test oop
    Given I open "quote" page
    Given Tool to get "quote" test data from source "user" "file"
    When I fill out "email" field with "john"
    And I submit the form oop
    Then I see "email" error message "Please enter a valid email address."
    When I fill out "email" field with "john@example.com"
    Then I don't see "email" error message

  @quoteScenario13-6
  Scenario: Market password field tests
    Given I open "quote" page
    Given Tool to get "quote" test data from source "user" "file"
    When I fill out "password" field with "1234"
    And I submit the form oop
    Then I see "password" error message "Please enter at least 5 characters."
    When I fill out "password" field with "12345"
    Then I don't see "password" error message
    When I fill out "confirmPassword" field with "1234"
    And I submit the form oop
    Then I see "confirmPassword" error message "Please enter at least 5 characters."
    When I fill out "confirmPassword" field with "54321"
    Then I see "confirmPassword" error message "Passwords do not match!"
    When I fill out "confirmPassword" field with "12345"
    Then I don't see "confirmPassword" error message

  @quoteScenario13-7
  Scenario: Market name test oop
    Given I open "quote" page
    Given Tool to get "quote" test data from source "user" "file"
    When I fill out name field with first name "John" and last name "Doe"
    Then I verify "name" field value "John Doe"
    When I fill out name field with first name "John", middle name "Richard", last name "Doe"
    Then I verify "name" field value "John Richard Doe"
