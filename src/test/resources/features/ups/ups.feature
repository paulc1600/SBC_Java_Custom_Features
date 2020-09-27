@upsBrowserTest
Feature: UPS Test Suite
  # ------------------------------------------------------
  #  Day 10 Scenarios
  # ------------------------------------------------------
  @upsScenario10-1
  Scenario: UPS end to end first
    Given Tool that goes to the "ups" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "ups" test data from source "No File" "file"
    # ------------------------------------------------------
    And I open Shipping menu
    And I go to Create a Shipment
    When I fill out origin shipment fields
    And I submit the shipment form
    Then I verify origin shipment fields submitted
    And I cancel the shipment form
    Then I verify shipment form is reset

  @upsScenario10-2
  Scenario: UPS end to end second
    Given Tool that goes to the "ups" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "ups" test data from source "No File" "file"
    # ------------------------------------------------------
    And I open Shipping menu
    And I go to Create a Shipment
    When I fill out origin shipment fields
    And I submit the shipment form
    Then I verify origin shipment fields submitted
    When I fill out destination shipment fields
    When I submit the shipment form
    And I set packaging type and weight
    When I submit the shipment form
    Then I verify total charges appear
    And I select cheapest delivery option
    And I submit the shipment form
    # ------------------------------------------------
    And I set description and check Saturday Delivery type
    Then I verify total charges changed
    When I submit the shipment form
    And I select Paypal payment type
    And I review the shipment form
    Then I review all recorded details on the review page
    And I cancel the shipment form
    Then I verify shipment form is reset
