@predefined
Feature: Smoke steps

  @predefined1
  Scenario: Predefined steps for Google
    Given I open url "https://google.com"
    Then I should see page title as "Google"
    Then element with xpath "//input[@name='q']" should be present
    When I type "Behavior Driven Development" into element with xpath "//input[@name='q']"
    Then I click on element using JavaScript with xpath "(//input[@name='btnK'])[1]"
    Then I wait for element with xpath "//*[@id='res']" to be present
    Then element with xpath "//*[@id='res']" should contain text "Cucumber"

  @predefined2
  Scenario: Predefined steps for Yahoo
    Given I open url "https://search.yahoo.com/"
    Then I should see page title as "Yahoo Search - Web Search"
    Then element with xpath "//input[@id='yschsp']" should be present
    When I type "Behavior Driven Development" into element with xpath "//input[@id='yschsp']"
    Then I click on element with xpath "//form[@id='sf']/button/span"
    Then I wait for element with xpath "//ol[contains(@class, 'searchCenterMiddle')]" to be present
    Then element with xpath "//ol[contains(@class, 'searchCenterMiddle')]" should contain text "Cucumber"

  @predefined3
  Scenario: Predefined steps for Bing
    Given I open url "https://www.bing.com/"
    Then I should see page title as "Bing"
    Then element with xpath "//input[@id='sb_form_q']" should be present
    When I type "Behavior Driven Development with Cucumber" into element with xpath "//input[@id='sb_form_q']"
    Then I click on element with xpath "//label[@*[contains(., 'search')]]"
    Then I wait for element with xpath "//ol[@id='b_results']" to be present
    Then element with xpath "//ol[@id='b_results']" should contain text "Cucumber"

  @predefined4
  Scenario: Predefined steps Gibiru
    Given I open url "http://gibiru.com"
    Then I should see page title as "Gibiru – Protecting your privacy since 2009"
    Then element with xpath "//input[@name='q']" should be present
    When I type "Behavior Driven Development" into element with xpath "//input[@name='q']"
    Then I click on element with xpath "//button[@type='submit']"
    Then I wait for element with xpath "//div[contains(@class, 'results-wrapper')]" to be present
    Then element with xpath "//div[contains(@class, 'results-wrapper')]" should contain text "Cucumber"

  @predefined5
  Scenario: Predefined steps DuckDuckGo
    Given I open url "https://duckduckgo.com"
    Then I should see page title as "DuckDuckGo — Privacy, simplified."
    Then element with xpath "//input[@placeholder='Search the web without being tracked']" should be present
    When I type "Behavior Driven Development" into element with xpath "//input[@placeholder='Search the web without being tracked']"
    Then I click on element with xpath "//input[@id='search_button_homepage']"
    Then I wait for element with xpath "//div[@class='results--main']" to be present
    Then element with xpath "//div[@class='results--main']" should contain text "Cucumber"

  @predefined6
  Scenario: Predefined steps Swiss Cows
    Given I open url "https://swisscows.com"
    Then I should see page title contains "Swisscows"
    Then element with xpath "//input[@name='query']" should be present
    When I type "Behavior Driven Development" into element with xpath "//input[@name='query']"
    Then I click on element with xpath "//button[@class='search-submit']"
    Then I wait for element with xpath "//div[@class='web-results']" to be present
    Then element with xpath "//div[@class='web-results']" should contain text "Cucumber"

  @predefined7
  Scenario: Predefined steps Search Encrypt
    Given I open url "https://www.searchencrypt.com"
    Then I should see page title contains "Search Encrypt"
    Then element with xpath "//input[@placeholder='Search...']" should be present
    When I type "Behavior Driven Development cucumber" into element with xpath "//input[@placeholder='Search...']"
    Then I click on element with xpath "//button[@class='search-bar__submit']"
    Then I wait for element with xpath "//section[@class='serp__results container']" to be present
    Then I wait for 5 sec
    Then element with xpath "//section[@class='serp__results container']" should contain text "Cucumber"

  @predefined8
  Scenario: Predefined steps Start Page
    Given I open url "https://www.startpage.com/"
    Then I should see page title contains "Startpage.com"
    Then element with xpath "//input[@id='q']" should be present
    When I type "Behavior Driven Development Cucumber" into element with xpath "//input[@id='q']"
    Then I click on element with xpath "//button[@aria-label='Startpage Search']"
    Then I wait for element with xpath "//section[@class='w-gl w-gl--default']/child::div[3]" to be present
    Then I wait for 5 sec
    Then element with xpath "//section[@class='w-gl w-gl--default']/child::div[3]" should contain text "Cucumber"

  @predefined9
  Scenario: Predefined steps Yandex
    Given I open url "https://www.yandex.com"
    Then I should see page title contains "Yandex"
    Then element with xpath "//input[@id='text']" should be present
    When I type "Apollo 11" into element with xpath "//input[@id='text']"
    Then I click on element with xpath "//div[contains(@class, 'search')]/button[@type='submit']"
    # Gets challenged by Captcha (switch to Firefox)
    Then I wait for 10 sec
    Then I wait for element with xpath "//ul[@id='search-result']" to be present
    Then element with xpath "//ul[@id='search-result']" should contain text "moon"

  @predefined10
  Scenario: Predefined steps Boardreader
    Given I open url "https://boardreader.com/"
    Then I should see page title contains "Boardreader"
    Then element with xpath "//input[@id='title-query']" should be present
    When I type "Behavior Driven Development" into element with xpath "//input[@id='title-query']"
    Then I click on element with xpath "//button[@id='title-submit']"
    Then I wait for element with xpath "//ul[@class='mdl-list']" to be present
    Then I wait for 5 sec
    Then element with xpath "//ul[@class='mdl-list']" should contain text "BDD"

  @predefined10
  Scenario: Predefined steps Ecosia
    Given I open url "https://www.ecosia.org"
    Then I should see page title contains "Ecosia"
    Then element with xpath "//div/input[@type='search']" should be present
    When I type "Behavior Driven Development" into element with xpath "//div/input[@type='search']"
    Then I click on element with xpath "//button[contains(@class,'button-submit')][@type='submit']"
    Then I wait for element with xpath "//div[@class='results-page']" to be present
    Then I wait for 5 sec
    Then element with xpath "//div[@class='results-page']" should contain text "BDD"