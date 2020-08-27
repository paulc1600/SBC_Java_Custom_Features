@unitConverterTest
Feature: Unit Converter Test Suite

  @unitConverterSmoke
  Scenario: Basic Conversion Tests
    Given Tool that goes to the "unit converter" page
    # ------------------------------------------------------
    #  Test Environment Set Up
    When Tool to change resolution to "default"
    When Tool to get "unit converter" test data from source "default"
    Then Tool to print all page details "without" source
    # ------------------------------------------------------
    When I select the conversion tab for "Temperature"
    And I select the source unit as "Fahrenheit" with value 54.0 and target unit "Celsius"
    Then I expect the resulting amount to be 12.2 "as provided"
    # ------------------------------------------------------
    When I select the conversion tab for "Weight"
    And I select the source unit as "Pound" with value 170.0 and target unit "Kilogram"
    Then I expect the resulting amount to be 77.0 "calculated"
    # ------------------------------------------------------
    When I select the conversion tab for "Length"
    And I select the source unit as "Mile" with value 3.0 and target unit "Foot"
    Then I expect the resulting amount to be 15840.0 "as provided"
    # ------------------------------------------------------