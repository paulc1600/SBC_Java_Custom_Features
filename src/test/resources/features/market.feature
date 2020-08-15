@marketBrowserTest
Feature: Market Quote Test Suite

  @marketIntro
  Scenario: webDriver Intro
    Given I go to "quote" page
    And I print page details
    And I go to "google" page
    And I print page details
    And I go back and forward, then refresh

  @marketRequired
  Scenario: Enter Required Fields and Verify Quote Report
    Given I go to "quote" page
    And I print page details
    And I fill out required fields
    And I submit the form
    Then I verify required fields