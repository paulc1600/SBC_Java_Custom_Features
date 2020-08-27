@marketBrowserTest
Feature: Market Quote Test Suite

  @marketIntro
  Scenario: Test Browser Navigation
    Given Tool that goes to the "quote" page
    And Tool to print page details
    And Tool that goes to the "google" page
    And Tool to print page details
    And I go back and forward, then refresh

  @marketSmoke
  Scenario: Desktop Enter Required Fields and Verify Quote Report
    # ------------------------------------------------------
    #  Test Environment Set Up
    #     Pages: quote, google (TBD), yahoo (TBD)
    #     Test Data: default, <Windows .CSV file name> (TBD), random generated (TBD)
    Given Tool that goes to the "quote" page
    When Tool to change resolution to "desktop"
    Given Tool to get "quote" test data from source "default"
    Then I display test environment set up
    # ------------------------------------------------------
    When I fill out required fields
    And I fill out the optional fields
    And I submit the form
    Then I verify required fields

  @marketSmoke
  Scenario: Phone Enter Required Fields and Verify Quote Report
    # ------------------------------------------------------
    #  Test Environment Set Up
    #     Pages: quote, google (TBD), yahoo (TBD)
    #     Test Data: default, <Windows .CSV file name> (TBD), random generated (TBD)
    Given Tool that goes to the "quote" page
    When Tool to change resolution to "phone"
    Given Tool to get "quote" test data from source "default"
    Then I display test environment set up
    # ------------------------------------------------------
    When I fill out required fields
    And I fill out the optional fields
    And I submit the form
    Then I verify required fields

  @marketEmailChecker
  Scenario: Perform Email Field Required Checking
    # ------------------------------------------------------
    #  Test Environment Set Up
    #     Pages: quote, google (TBD), yahoo (TBD)
    #     Test Data: default, <Windows .CSV file name> (TBD), random generated (TBD)
    Given Tool that goes to the "quote" page
    Given Tool to get "quote" test data from source "default"
    Then I display test environment set up
    # ------------------------------------------------------
    But I clear "quote" page field "email"
    And I fill out required fields
    And I submit the form
    Then I check for "quote" page field "email" error message "required"

  @marketEmailChecker
  Scenario: Perform Email Field Valid Checking
    # ------------------------------------------------------
    #  Test Environment Set Up
    Given Tool that goes to the "quote" page
    Given Tool to get "quote" test data from source "default"
    Then I display test environment set up
    # ------------------------------------------------------
    But I clear "quote" page field "email"
    And I custom set "quote" page field "email" to "goober.nonet.com"
    And I fill out required fields
    And I submit the form
    Then I check for "quote" page field "email" error message "invalid"
    # Can send any control key but MUST use Java defined values
    #      look up valid control keys: https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/Keys.html
    When I type "BACK_SPACE" 1 time into "email" field
    And I clear "quote" page field "email"
    And I fill out required fields
    And I custom set "quote" page field "email" to "goober@nonet.com"
    And I fill out required fields
    And I submit the form
    Then I verify required fields
    And I set all test data variables to their defaults

  @market3rdPartyAgreement
  Scenario: Verify Appearance of 3rd Party Alert
    # ------------------------------------------------------
    #  Test Environment Set Up
    Given Tool that goes to the "quote" page
    Given Tool to get "quote" test data from source "default"
    Then I display test environment set up
    # ------------------------------------------------------
    When I fill out required fields
    And Tool to handle 3rd party alert at "//button[@id='thirdPartyButton']" with a "accept"
    And I submit the form
    Then I verify required fields

  @market3rdPartyAgreement
  Scenario: Verify Dismiss of 3rd Party Alert
    # ------------------------------------------------------
    #  Test Environment Set Up
    Given Tool that goes to the "quote" page
    Given Tool to get "quote" test data from source "default"
    Then I display test environment set up
    # ------------------------------------------------------
    When I fill out required fields
    And Tool to handle 3rd party alert at "//button[@id='thirdPartyButton']" with a "dismiss"
    And I submit the form
    Then I verify required fields