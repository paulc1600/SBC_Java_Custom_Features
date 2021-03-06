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
    Given Tool to get "usps" test data from source "No File" "default"
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
    Given Tool to get "usps" test data from source "No File" "default"
    When I go to Lookup ZIP page by address
    And I fill out "4970 El Camino Real" street, "Los Altos" city, "CA" state
    And I submit the zip lookup by address form
    Then I validate "94022" zip code exists in the result

  @uspsSmoke
  Scenario: Validate ZIP code for Federal District Courthouse
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "usps" test data from source "No File" "default"
    When I go to Lookup ZIP page by address
    And I fill out "450 Golden Gate Avenue" street, "San Francisco" city, "CA" state
    And I submit the zip lookup by address form
    Then I validate "94102" zip code exists in the result

  @uspsSmoke
  Scenario: Validate ZIP code for Federal District Courthouse (Lite Version)
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "usps" test data from source "No File" "default"
    When I go to Lookup ZIP page by address (lite version)
    And I fill out "450 Golden Gate Avenue" street, "San Francisco" city, "CA" state
    And I submit the zip lookup by address form
    Then I validate "94102" zip code exists in the result

  # Code courtesy of Viacheslav (Slava) Skryabin 04/01/2011
  @uspsScenario2
  Scenario: Verify location
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    Given Tool to get "usps" test data from source "No File" "default"
    # ------------------------------------------------------
    When I perform "Free Boxes" search
    And I set "Mail & Ship" in filters
    Then I verify that "7" results found
    When I select "Priority Mail | USPS" in results
    And I click "Ship Now" button
    Then I validate that Sign In is required

  @uspsScenario4
  Scenario: Phone number of the nearest Mail Pickup (Scen 4)
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    Given Tool to get "usps" test data from source "No File" "default"
    When I navigate to Find a Location page
    And I filter by "Post Offices" location types, "Pickup Services" services, "Accountable Mail" available services
    And I provide data as "4970 El Camino Real 110" street, "Los Altos" city, "CA" state
    Then I verify phone number is "800-275-8777"

  @uspsScenario5
  Scenario: Quadcopters delivery
    Given Tool that goes to the "usps" page
    When I go to "Help" tab
    And I perform "Quadcopters delivery" help search
    Then I verify that no results of "Quadcopters delivery" available in help search

  @uspsScenario6
  Scenario: Calculate price
    Given Tool that goes to the "usps" page
    When I go to Calculate Price Page
    And I select "Canada" with "Postcard" shape
    And I define "2" quantity
    Then I calculate the price and validate cost is "$2.40"

  @uspsScenario9
    Scenario: Every Door Direct Mail
      Given Tool that goes to the "usps" page
      When Tool to change resolution to "default"
      And Tool to print all page details "without" source
      Given Tool to get "usps" test data from source "No File" "default"
      # ------------------------------------------------------
      When I go to "Every Door Direct Mail" under "Business"
      And I search for "4970 El Camino Real, Los Altos, CA 94022"
      And I click "Show Table" on the map
      When I click "Select All" on the table
      And I close modal window
      Then I verify that summary of all rows of Cost column is equal Approximate Cost in Order Summary

  @uspsScenario12-3
  Scenario: Wrong store id does not match
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "usps" test data from source "No File" "default"
    # ------------------------------------------------------
    When I go to "Postal Store" tab
    And I enter "12345" into store search
    Then I search and validate no products found

  @uspsScenario12-4
  Scenario: One item found
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "usps" test data from source "No File" "default"
    # ------------------------------------------------------
    When I go to "Stamps" under "Postal Store"
    And choose mail service Priority Mail
    Then I verify 1 items found

  @uspsScenario12-5
  Scenario: Verify color
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "usps" test data from source "No File" "default"
    # ------------------------------------------------------
    When I go to "Stamps" under "Postal Store"
    When I unselect Stamps checkbox
    And select Vertical stamp Shape
    And I click Blue color
    Then I verify "Blue" and "Vertical" filters
    Then I verify 12 items found
    And I verify that items below 12 dollars exists

  @uspsScenario12-6
  Scenario: Verify Passport Service Exists
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "usps" test data from source "No File" "default"
    # ------------------------------------------------------
    When I perform "Passports" search
    And I select "Passport Application" in results
    And I click "Schedule an Appointment" button
    And verify "Passport Renewal" service exists

  @uspsScenario12-7
  Scenario: PO Box Search and Verify Small Box
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "usps" test data from source "No File" "default"
    # ------------------------------------------------------
    When I go to "PO Boxes" under "Track & Manage"
    And I reserve new PO box for "94022"
    Then I verify that "Los Altos — Post Office™" present
    And I verify that "Size 2-S" PO Box is available in "Los Altos — Post Office™"

  @uspsScenario12-8
  Scenario: PO Box Search and Verify Jumbo Box
    Given Tool that goes to the "usps" page
    When Tool to change resolution to "default"
    And Tool to print all page details "without" source
    Given Tool to get "usps" test data from source "No File" "default"
    # ------------------------------------------------------
    When I go to "PO Boxes" under "Track & Manage"
    And I reserve new PO box for "94022"
    Then I verify that "Los Altos — Post Office™" present
    And I verify that "Size 5-XL" PO Box is available in "Los Altos — Post Office™"