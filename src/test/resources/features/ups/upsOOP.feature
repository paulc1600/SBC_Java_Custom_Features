@upsBrowserTestPOM
Feature: UPS Test Suite for POM
  # ------------------------------------------------------
  #  Day 13 Scenarios
  # ------------------------------------------------------
  @quoteScenario13-UPS1
  Scenario: UPS end to end first POM
    Given I open "ups" page POM
    And I open Shipping menu POM
    And I go to Create a Shipment POM
    When I fill out origin shipment fields POM
    And I submit the shipment form POM
    Then I verify origin shipment fields submitted POM
    And I cancel the shipment form POM
    Then I verify shipment form is reset POM