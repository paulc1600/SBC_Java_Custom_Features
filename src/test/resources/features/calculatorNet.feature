@calculatorNetTest
Feature: Unit Converter Test Suite

  @calculatorNetSmoke1
  Scenario: Auto Loan Calculator Tests
    Given Tool that goes to the "calculator" page
    # ------------------------------------------------------
    #  Test Environment Set Up
    When Tool to change resolution to "default"
    When Tool to get "calculator" test data from source "default"
    Then Tool to print all page details "without" source
    # ------------------------------------------------------
    When I navigate to "Auto Loan Calculator"
    And I clear all calculator fields
    And I calculate
    Then I verify "Please provide a positive auto price." calculator error
    Then I verify "Please provide a positive interest value." calculator error
    And I enter "25000" price, "60" months, "4.5" interest, "5000" downpayment, "0" trade-in, "California" state, "7" percent tax, "300" fees
    And I calculate
    Then I verify monthly pay is "$372.86"

  @calculatorOutlineTests
  Scenario Outline: Scientific Calculator Tests
    Given Tool that goes to the "calculator" page
    When Tool to change resolution to "default"
    When Tool to get "calculator" test data from source "default"
    When I start their scientific calculator
    And I enter first number <nbr1>
    And I enter the function <fnct1>
    And I enter the second number <nbr2>
    And I press the <keyt> key
    Then I verify the calculation as <result>
    Examples:
      | nbr1   | fnct1 | nbr2    | keyt | result   |
      | "1.0"  | "+"   | "2.0"   | "="  | "3"      |
      | "5.6"  | "+"   | "2.3"   | "="  | "7.9"    |
      | "5.69" | "-"   | "2.3"   | "="  | "3.39"   |
      | "5.69" | "-"   | "10.69" | "="  | "-5"     |
