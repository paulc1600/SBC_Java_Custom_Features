@uspsOOPTest
Feature: USPS Test Suites for POM

  @usps6
  Scenario Outline: Validate ZIP code oop
    Given I open "usps" page oop
    When I go to Lookup ZIP page by address oop
    And I fill out "<street>" street, "<city>" city, "<state>" state oop
    Then I validate "<zip>" zip code exists in the result oop
    Examples:
      | street              | city      | state | zip   |
      | 4970 El Camino Real | Los Altos | CA    | 94022 |
      | 11 Wall st          | New York  | NY    | 10005 |
      | 111 S Michigan Ave  | Chicago   | IL    | 60603 |

  @usps7
  Scenario: USPS Calculate price with POM
    Given I open "usps" page oop
    When I go to Calculate Price Page oop
    And I select "Canada" with "Postcard" shape oop
    And I define "2" quantity oop
    Then I calculate the price and validate cost is "$2.40" oop
