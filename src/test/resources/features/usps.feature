@uspsBrowserTest
Feature: USPS Test Suite

  @uspsSmoke
  Scenario: Validate ZIP code for Portnov Computer School
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "desktop"
    And Tool to print all page details "without" source
    # ------------------------------------------------------
    #  Test Environment Set Up
    #     Pages: quote, google (TBD), yahoo (TBD)
    #     Test Data: default, <Windows .CSV file name> (TBD), random generated (TBD)
    Given Tool to get "usps" test data from source "default"
    # ------------------------------------------------------
    When I go to Lookup ZIP page by address
    And I fill out "4970 El Camino Real, Suite 110" street, "Los Altos" city, "CA" state
    And I submit the zip lookup by address form
    Then I validate "94022" zip code exists in the result

  @uspsSmoke
  Scenario: Validate ZIP code for Portnov Computer School Block
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "usps" test data from source "default"
    When I go to Lookup ZIP page by address
    And I fill out "4970 El Camino Real" street, "Los Altos" city, "CA" state
    And I submit the zip lookup by address form
    Then I validate "94022" zip code exists in the result

  @uspsSmoke
  Scenario: Validate ZIP code for Federal District Courthouse
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "usps" test data from source "default"
    When I go to Lookup ZIP page by address
    And I fill out "450 Golden Gate Avenue" street, "San Francisco" city, "CA" state
    And I submit the zip lookup by address form
    Then I validate "94102" zip code exists in the result

  @uspsSmoke
  Scenario: Validate ZIP code for Federal District Courthouse (Lite Version)
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "usps" test data from source "default"
    When I go to Lookup ZIP page by address (lite version)
    And I fill out "450 Golden Gate Avenue" street, "San Francisco" city, "CA" state
    And I submit the zip lookup by address form
    Then I validate "94102" zip code exists in the result

  @uspsScenario4
  Scenario: Phone number of the nearest Mail Pickup (Scen 4)
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    Given Tool to get "usps" test data from source "default"
    When I navigate to Find a Location page
    And I filter by "Post Offices" location types, "Pickup Services" services, "Accountable Mail" available services
    And I provide data as "4970 El Camino Real 110" street, "Los Altos" city, "CA" state
    Then I verify phone number is "800-275-8777"

  @uspsScenario3
  Scenario: Quadcopters delivery
    Given Tool that goes to the "usps" page
    When I go to "Help" tab
    And I perform "Quadcopters delivery" help search
    Then I verify that no results of "Quadcopters delivery" available in help search

  @uspsScenario1
  Scenario: Calculate price
    Given Tool that goes to the "usps" page
    When I go to Calculate Price Page
    And I select "Canada" with "Postcard" shape
    And I define "2" quantity
    Then I calculate the price and validate cost is "$2.40"
