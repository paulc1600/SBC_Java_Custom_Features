@PomEnvironment @careersTest
Feature: Careers Portal Test Suites for POM

  @careers1
  Scenario: Recruiter removes position
    Given I open "careers" page oop
    And I login as "recruiter"
    Then I verify "recruiter" login
    When I remove "Principal Automation Engineer" position
    And I verify "Principal Automation Engineer" position is removed

  @careers2
  Scenario: Candy withdraws from Director Job
    Given I open "careers" page oop
    Given POM Tool to get "careers" test data from source "candy" "file"
    And I login as "candy"
    Then I verify "candy" login
    When I display my jobs list
    Then I verify job "Director, Product Development" is there
    When I withdraw my application for "Director, Product Development"
    And I display my jobs list
    Then I verify job "Director, Product Development" is not there

  @careers3
  Scenario: Recruiter creates position
    Given I open "careers" page oop
    And I login as "recruiter"
    Then I verify "recruiter" login
    When I create new "junior" position
    Then I verify new "junior" position is "created"
    When I remove new "junior" position
    And I verify new "junior" position is "removed"