@java1
Feature: Java Feature

  @java1
  Scenario Outline: The World Is Stringy
    Given I say "Java string fun!" test name <testName>
    And I perform "display" actions with <first> and <second>
    And I perform "upper" actions with <first> and <second>
    And I perform "measure" actions with <first> and <second>
    And I perform "compare" actions with <first> and <second>
    And I perform "compareIC" actions with <first> and <second>
    And I perform "contains" actions with <first> and <second>
    Examples:
      | testName                    | first                       | second           |
      | "Display unmodified"        | "apple"                     | "orange"         |
      | "Verify capitals compare"   | "apple"                     | "APPLE"          |
      | "Impact white space"        | "apple  "                   | "  APPLE"        |
      | "Test contains at start"    | "really big string thing"   | "really big"     |
      | "Test contains middle"      | "really big string thing"   | " big string "   |

  @java2
  Scenario: The Arrays are Here
    And I work with Arrays