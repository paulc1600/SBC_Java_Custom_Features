@javaArrays
Feature: Java Arrays Feature

  @javaArray1
  Scenario: The Arrays are Here
    And I work with Arrays

  @javaArray2
  Scenario: List Maker 2
    # Common Code to create lists
    Given I create shopping list "Safeway" with 9 items
    When I add item "Heirloom Carrots Bag" to list "Safeway"
    Given I create shopping list "CVS" with 3 items
    Given I create shopping list "Ace Hardware" with 2 items
    When I add item "Black and Decker Portable Drill" to list "Ace Hardware"
    When I add item "Starbucks Morning Blend" to list "Safeway"
    And I add item "Pringles Ranch" to list "Safeway"
    And I add item "6pk Diet Cherry Coke" to list "Safeway"
    And I add item "Stress Relief Tabs" to list "CVS"
    And I add item "10pk Washable Face Masks" to list "CVS"
    And I add item "Hand Sanitizer" to list "CVS"
    And I add item "Post Sugar Frosted Bombs" to list "Safeway"
    And I add item "John Deer 36inch Gas Riding Mower" to list "Ace Hardware"
    # Display all lists
    Then I check the items in list "CVS"
    Then I check the items in list "Ace Hardware"
    Then I check the items in list "Safeway"

  @javaArray3
  Scenario: List Maker 3
    # Common Code to create lists
    Given I create shopping list "Safeway" with 9 items
    When I add item "Heirloom Carrots Bag" to list "Safeway"
    Given I create shopping list "CVS" with 3 items
    Given I create shopping list "Ace Hardware" with 2 items
    When I add item "Black and Decker Portable Drill" to list "Ace Hardware"
    When I add item "Starbucks Morning Blend" to list "Safeway"
    And I add item "Pringles Ranch" to list "Safeway"
    And I add item "Stress Relief Tabs" to list "CVS"
    And I add item "John Deer 36inch Gas Riding Mower" to list "Ace Hardware"
    # Display selected list items
    Then I check the items in list "Ace Hardware"
    Then I check the 1 item in list "Ace Hardware"
    Then I check the items in list "Safeway"
    Then I check the 3 item in list "Safeway"

  @javaDynamicList
  Scenario: Dynamic List Maker
    Given I display dynamic list

  @javaDynamicList
  Scenario: Dynamic List Item Picker
    Given I get item 4 from the dynamic list
    When I add new item 99 to the dynamic list
    Then I display dynamic list
    Then I get item 7 from the dynamic list

  @javaMap
  Scenario: Work with Map
    Given I work with maps
