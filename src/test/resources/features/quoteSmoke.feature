@quoteSuite1
Feature: Quote Test Suite 1

  @TestsUI @T1TestResponsiveDesign
  Scenario: Test Responsive Design
    Given I open url "https://skryabin.com/market/quote.html"
    And I resize window to 1280 and 1024
    Then element with xpath "//b[@id='location']" should be displayed
    Then element with xpath "//b[@id='currentDate']" should be displayed
    Then element with xpath "//b[@id='currentTime']" should be displayed
    And I resize window to 800 and 1024
    Then element with xpath "//b[@id='location']" should be displayed
    Then element with xpath "//b[@id='currentDate']" should be displayed
    Then element with xpath "//b[@id='currentTime']" should be displayed
    And I resize window to 400 and 1024
    Then element with xpath "//b[@id='location']" should not be displayed
    Then element with xpath "//b[@id='currentDate']" should not be displayed
    Then element with xpath "//b[@id='currentTime']" should not be displayed

  @TestsUI @T2TestUsername
  Scenario: Validate Username field
    Given I open url "https://skryabin.com/market/quote.html"
    Then I wait for element with xpath "//input[@name='username']" to be present
    When I type "btester" into element with xpath "//input[@name='username']"
    Then element with xpath "//input[@name='username']" should have attribute "value" as "btester"
    # Set Up for minimum length test: fill in required fields
    When I clear element with xpath "//input[@name='username']"
    And I type "bobtester@nonet.com" into element with xpath "//input[@name='email']"
    And I type "12345" into element with xpath "//input[@id='password']"
    And I type "12345" into element with xpath "//input[@id='confirmPassword']"
    And I type "Bob Tester" into element with xpath "//input[@name='name']"
    And I click on element with xpath "//input[@name='agreedToPrivacyPolicy']"
    And I type "bt" into element with xpath "//input[@name='username']"
    And I click on element with xpath "//button[@id='formSubmit']"
    Then I wait for element with xpath "//b[@name='username']" to be present
    Then element with xpath "//b[@name='username']" should contain text "bt"

  @TestsUI @T3TestEmail
  Scenario: Validate Email field
    Given I open url "https://skryabin.com/market/quote.html"
    Then I wait for element with xpath "//input[@name='email']" to be present
    When I type "bobtester@nonet.com" into element with xpath "//input[@name='email']"
    Then element with xpath "//input[@name='email']" should have attribute "value" as "bobtester@nonet.com"
    # email test: minimum length error -----------------------------------
    When I clear element with xpath "//input[@name='email']"
    And I type "b@" into element with xpath "//input[@name='email']"
    And I click on element with xpath "//input[@name='username']"
    Then element with xpath "//label[@id='email-error']" should contain text "Please enter a valid email"
    # email test: minimum length accepted ---------------------------------
    When I clear element with xpath "//input[@name='email']"
    And I type "1@n" into element with xpath "//input[@name='email']"
    And I click on element with xpath "//input[@name='username']"
    Then element with xpath "//label[@id='email-error']" should not be displayed
    # email test: invalid format ------------------------------------------
    When I clear element with xpath "//input[@name='email']"
    And I type "bobtesterATnonet.com" into element with xpath "//input[@name='email']"
    And I click on element with xpath "//input[@name='username']"
    Then element with xpath "//label[@id='email-error']" should contain text "Please enter a valid email"
    # email test: maximum length accepted ----------------------------------
    When I clear element with xpath "//input[@name='email']"
    And I type "BobMax78901234567890123456789012345678901234567890@ComMax7890123456789012345678901234567890123456789" into element with xpath "//input[@name='email']"
    And I click on element with xpath "//input[@name='username']"
    Then element with xpath "//label[@id='email-error']" should not be displayed
    # email test: maximum length error -------------------------------------
    When I clear element with xpath "//input[@name='email']"
    And I type "BobErr78901234567890123456789012345678901234567890@ComErr78901234567890123456789012345678901234567890" into element with xpath "//input[@name='email']"
    And I click on element with xpath "//input[@name='username']"
    # Possible Bug? Does not detect the error.
    Then element with xpath "//label[@id='email-error']" should contain text "Please enter a valid email"

  @TestsUI @T4TestPassword
  Scenario: Validate Password fields
    Given I open url "https://skryabin.com/market/quote.html"
    Then I wait for element with xpath "//input[@id='password']" to be present
    When I type "12345" into element with xpath "//input[@id='password']"
    And I type "12345" into element with xpath "//input[@id='confirmPassword']"
    Then element with xpath "//label[@id='password-error']" should not be present
    # password test: minimum length error -----------------------------------
    When I clear element with xpath "//input[@id='password']"
    And I type "1234" into element with xpath "//input[@id='password']"
    And I click on element with xpath "//input[@name='username']"
    Then element with xpath "//label[@id='password-error']" should contain text "enter at least 5"
    # password test: confirm password disabled when no password  ------------
    When I clear element with xpath "//input[@id='password']"
    Then element with xpath "//input[@id='confirmPassword']" should be disabled

  @TestsUI @T5TestNameFunctions
  Scenario: Validate Name Field and Modal Dialog Box
    Given I open url "https://skryabin.com/market/quote.html"
    Then I wait for element with xpath "//input[@name='name']" to be present
    When I type "Bob A Tester" into element with xpath "//input[@name='name']"
    Then element with xpath "//input[@name='name']" should have attribute "value" as "Bob A Tester"
    # Test modal name dialog pop-up ------------------------------------------
    When I clear element with xpath "//input[@name='name']"
    And I click on element with xpath "//input[@name='name']"
    Then I wait for element with xpath "//div[contains(@class,'ui-dialog')][@role='dialog']" to be present
    # Test dialog box name concatenation -------------------------------------
    When I type "Chuck" into element with xpath "//input[@id='firstName']"
    And I type "A" into element with xpath "//input[@id='middleName']"
    And I type "Tester" into element with xpath "//input[@id='lastName']"
    And I click on element with xpath "//span[contains(text(),'Save')]/parent::button"
    Then element with xpath "//input[@name='name']" should have attribute "value" as "Chuck A Tester"

  @TestsUI @T6TestPrivacyPolicyRequired
  Scenario: Verify Privacy Policy is Required
    Given I open url "https://skryabin.com/market/quote.html"
    Then I wait for element with xpath "//input[@name='name']" to be present
    When I type "Dan A Tester" into element with xpath "//input[@name='name']"
    And I type "dtester" into element with xpath "//input[@name='username']"
    And I type "dantester@nonet.com" into element with xpath "//input[@name='email']"
    And I type "123456" into element with xpath "//input[@id='password']"
    And I type "123456" into element with xpath "//input[@id='confirmPassword']"
    And I click on element with xpath "//button[@id='formSubmit']"
    Then element with xpath "//label[@id='agreedToPrivacyPolicy-error']" should contain text "Must check"

  @TestsUI @T7OptFields
  Scenario: Verify Additional Form Fields
    Given I open url "https://skryabin.com/market/quote.html"
    Then I wait for element with xpath "//input[@name='name']" to be present
    # When I type "Frank A Tester" into element with xpath "//input[@name='name']"
    # Use modal name dialog pop-up to enter name -----------------------------
    When I clear element with xpath "//input[@name='name']"
    And I click on element with xpath "//input[@name='name']"
    Then I wait for element with xpath "//div[contains(@class,'ui-dialog')][@role='dialog']" to be present
    When I type "Frank" into element with xpath "//input[@id='firstName']"
    And I type "A" into element with xpath "//input[@id='middleName']"
    And I type "Tester" into element with xpath "//input[@id='lastName']"
    And I click on element with xpath "//span[contains(text(),'Save')]/parent::button"
    # END modal name dialog pop-up -------------------------------------------
    And I type "ftester" into element with xpath "//input[@name='username']"
    And I type "franktester@nonet.com" into element with xpath "//input[@name='email']"
    And I type "23456" into element with xpath "//input[@id='password']"
    And I type "23456" into element with xpath "//input[@id='confirmPassword']"
    And I click on element with xpath "//input[@name='agreedToPrivacyPolicy']"
    # Additional Form Fields
    When I type "(408) 555-1212" into element with xpath "//input[@name='phone']"
    And I select option "United States of America" from element with xpath "//select[@name='countryOfOrigin']"
    And I click on element with xpath "//input[@name='gender'][@value='male']"
    And I click on element with xpath "//input[@name='allowedToContact']"
    And I type "2258 Nobug Lane, Quality Town, CA" into element with xpath "//textarea[@id='address']"
    And I click on element with xpath "//option[contains(text(),'Ford')]"
    And I click on element with xpath "//button[@id='thirdPartyButton']"
    And I accept alert
    And I type "01/01/1990" into element with xpath "//input[@id='dateOfBirth']"
    And I click on element with xpath "//button[@id='formSubmit']"
    # Validate Results Page
    Then I wait for element with xpath "//b[@name='username']" to be present
    Then element with xpath "//b[@name='username']" should contain text "ftester"
    Then element with xpath "//b[@name='password']" should not contain text "23456"
    Then element with xpath "//b[@name='email']" should contain text "franktester@nonet.com"
    Then element with xpath "//b[@name='firstName']" should contain text "Frank"
    Then element with xpath "//b[@name='middleName']" should contain text "A"
    Then element with xpath "//b[@name='lastName']" should contain text "Tester"
    Then element with xpath "//b[@name='name']" should contain text "Frank A Tester"
    # Possible Bug? Phone not there
    # Then element with xpath "//b[@name='phone']" should contain text "(408) 555-1212"
    Then element with xpath "//b[@name='dateOfBirth']" should contain text "01/01/1990"
    Then element with xpath "//b[@name='address']" should contain text "2258 Nobug Lane, Quality Town, CA"
    # Likely a bug. Code changes selection United States of America to "USA" = Inconsistent
    Then element with xpath "//b[@name='countryOfOrigin']" should contain text "United States of America"

