@ApiRestEnvironment @rest
Feature: REST API automation for Careers project

  @rest1
  Scenario: REST API Position CRUD
    Given I login via REST as "recruiter"
    When I create via REST "automation" as new "positions"
    Then I verify via REST new "automation" is "in" the "positions" list
    When I update via REST "automation" record in "positions"
    Then I verify via REST "automation" record in "positions" is updated
    When I delete via REST new "automation" from the "positions" list
    Then I verify via REST new "automation" is "not in" the "positions" list
    Then I clean up REST test environment for "positions"

  @rest2
  Scenario: REST API Candidates CRUD
    Given I login via REST as "recruiter"
    When I create via REST "sdet" as new "candidates"
    Then I verify via REST new "sdet" is "in" the "candidates" list
    When I add via REST "resumeGeneric" "pdf" to "sdet" in "candidates" list
    Then I verify via REST that "resumeGeneric" "pdf" is added to "sdet" in "candidates" list
    When I update via REST "sdet" record in "candidates"
    Then I verify via REST "sdet" record in "candidates" is updated
    When I delete via REST new "sdet" from the "candidates" list
    Then I verify via REST new "sdet" is "not in" the "candidates" list
    Then I clean up REST test environment for "candidates"

  @careers3
  Scenario: REST API Recruiter creates position
    Given I login via REST as "recruiter"
    Then I verify via REST "recruiter" login
    When I create via REST "junior" as new "positions"
    Then I verify via REST new "junior" is "in" the "positions" list
    When I delete via REST new "junior" from the "positions" list
    Then I verify via REST new "junior" is "not in" the "positions" list
    Then I clean up REST test environment for "positions"