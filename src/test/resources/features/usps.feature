@uspsBrowserTest
Feature: USPS Test Suite

  @uspsSmoke
  Scenario: Validate ZIP code for Portnov Computer School
    Given I go to my "usps" page
    When I change my resolution to "default"
    And I print all page details "without" source
    # ------------------------------------------------------
    #  Test Environment Set Up
    #     Pages: quote, google (TBD), yahoo (TBD)
    #     Test Data: default, <Windows .CSV file name> (TBD), random generated (TBD)
    Given I get my "usps" test data from source "default"
    # ------------------------------------------------------
    When I go to Lookup ZIP page by address
    And I fill out "4970 El Camino Real, Suite 110" street, "Los Altos" city, "CA" state
    And I submit the zip lookup by address form
    Then I validate "94022" zip code exists in the result

  @uspsSmoke
  Scenario: Validate ZIP code for Portnov Computer School Block
    Given I go to my "usps" page
    When I change my resolution to "default"
    And I print all page details "without" source
    Given I get my "usps" test data from source "default"
    When I go to Lookup ZIP page by address
    And I fill out "4970 El Camino Real" street, "Los Altos" city, "CA" state
    And I submit the zip lookup by address form
    Then I validate "94022" zip code exists in the result

  @uspsSmoke
  Scenario: Validate ZIP code for Federal District Courthouse
    Given I go to my "usps" page
    When I change my resolution to "default"
    And I print all page details "without" source
    Given I get my "usps" test data from source "default"
    When I go to Lookup ZIP page by address
    And I fill out "450 Golden Gate Avenue" street, "San Francisco" city, "CA" state
    And I submit the zip lookup by address form
    Then I validate "94102" zip code exists in the result