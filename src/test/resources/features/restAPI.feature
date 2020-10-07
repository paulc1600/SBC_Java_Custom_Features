@rest
Feature: REST API automation for Careers project

  @rest1
  Scenario: REST API Position CRUD
    Given I open rest environment for "careers"
    Given Tool to get "careers" test data from source "recruiter" "file"
      # POST /login to get token
    Given I login via REST as "recruiter"
      # POST /positions
    When I create via REST "automation" position
      # GET /positions
    Then I verify via REST new position is in the list
      # PUT /positions/{id}
    When I update via REST "automation" position
      # GET /positions/{id}
    Then I verify via REST new position is updated
      # DELETE /positions/{id}
    When I delete via REST new position
      # GET /positions
    Then I verity via REST new position is deleted