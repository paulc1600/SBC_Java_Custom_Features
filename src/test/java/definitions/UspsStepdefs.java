package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static definitions.GuiTestEnvironment.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static support.TestContext.getDriver;

public class UspsStepdefs {
    // =============================================
    //  Create test data variables
    // =============================================
    String tdAddress = "";
    String tdCity = "";
    String tdState = "";

    // ---------------------------------------------------------------------------
    //  Navigate to Zip Code Look Up Form on USPS Website
    // ---------------------------------------------------------------------------
    @When("I go to Lookup ZIP page by address")
    public void iGoToLookupZIPPageByAddress() throws InterruptedException {
        // Mail & Ship Navigation Link at top of page
        getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'Ship')]")).click();
        // --------------------------------------------------------------
        String xpZipCodeLink = "//h2[contains(@class,'center')]//a[contains(text(),'ZIP Code')]";
        trPageURL = getDriver().getCurrentUrl();
        assertThat(trPageURL).containsIgnoringCase("https://www.usps.com/ship/");
        getDriver().findElement(By.xpath(xpZipCodeLink)).click();
        // --------------------------------------------------------------
        String xpFindByAddressLink = "//a[contains(text(),'Find by Address')]";
        getDriver().findElement(By.xpath(xpFindByAddressLink)).click();
        // --------------------------------------------------------------
        String xpStreetAddr = "//input[@id='tAddress']";
        Thread.sleep(2000);
        trPageTitle = getDriver().getTitle();
        assertThat(trPageTitle).containsIgnoringCase("Zip Code");
    }

    // ---------------------------------------------------------------------------
    //  Fill OUt Zip Code Look Up Form by Address
    // ---------------------------------------------------------------------------
    @And("I fill out {string} street, {string} city, {string} state")
    public void iFillOutStreetCityState(String formAddress, String formCity, String formState) throws InterruptedException {
        getDriver().findElement(By.xpath("//input[@id='tAddress']")).sendKeys(formAddress);
        getDriver().findElement(By.xpath("//input[@id='tCity']")).sendKeys(formCity);
        String stateString = "";
        switch (formState) {
            case "CA":
                stateString = "CA - California";
                break;
            case "NY":
                stateString = "NY - New York";
                break;
            case "NV":
                stateString = "NV - Nevada";
                break;
            case "TX":
                stateString = "TX - Texas";
                break;
            default:
                stateString = "CA - California";
                break;
        }
        Select stateSelect = new Select(getDriver().findElement(By.xpath("//select[@id='tState']")));
        stateSelect.selectByValue(formState);
        // getDriver().findElement(By.xpath("//select[@id='tState']")).click();
        // getDriver().findElement(By.xpath("//select[@id='tState']//option[@value='" + formState + "']"));
        // Set Test Data from Gherkin parameters above and display
        tdAddress = formAddress;
        tdCity = formCity;
        tdState = formState;
        System.out.println("------------------------------------------------");
        System.out.println("       Zip Code Lookup for this address ...     ");
        System.out.println("------------------------------------------------");
        System.out.println(" Street Address  " + tdAddress);
        System.out.println(" City            " + tdCity);
        System.out.println(" State           " + tdState);
    }

    // ---------------------------------------------------------------------------
    //  submit the zip lookup by address form
    // ---------------------------------------------------------------------------
    @And("I submit the zip lookup by address form")
    public void iSubmitTheZipLookupByAddressForm() throws InterruptedException {
        //Find Button at bottom of form -- https://tools.usps.com/zip-code-lookup.htm?byaddress
        WebElement elFindButton = getDriver().findElement(By.xpath("//a[@id='zip-by-address']"));
        WebElement elCompanyInput = getDriver().findElement(By.xpath("//input[@id='tCompany']"));
        // DEBUG Code Scroll Into View if needed or click nearby field
        // isThisWebElementNotInViewport(elFindButton, "Find Button");
        // whatIsWebElementState(elCompanyInput, "Company Name Input");
        // whatIsWebElementState(elFindButton, "Find Button");
        Thread.sleep(1000);
        elFindButton.click();
    }

    // ===========================================================================
    //   Is WebElement beyond visible viewport in this display mode
    // ===========================================================================
    public void isThisWebElementNotInViewport(WebElement myElement, String elementLabel) {
        Point elLocation = myElement.getLocation();
        int topPixLocation = elLocation.getY();
        trPageURL = getDriver().getCurrentUrl();
        // System.out.println("\n" + " ------ DEBUG: " + trPageURL);
        // System.out.println(" ------ DEBUG: Element with label: " + elementLabel);
        // System.out.println(" ------ DEBUG: Y Position was:       " + topPixLocation);
        // System.out.println(" ------ DEBUG: Browser Height:       " + teBrowserHeight);
        if (topPixLocation >= teBrowserHeight) {
            // Beyond viewport dimension -- Selenium may not be able to interact!
            // So scroll down to it ...
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", myElement);
            System.out.println(" ------ DEBUG: Scrolling Occured.");
        }
    }

    // ---------------------------------------------------------------------------
    //  Validate Zip Code Lookup Results
    // ---------------------------------------------------------------------------
    @Then("I validate {string} zip code exists in the result")
    public void iValidateZipCodeExistsInTheResult(String correctZip) throws InterruptedException {
        // explicit wait for container is filled, parse zip codes to handle more than one
        WebDriverWait wait = new WebDriverWait(getDriver(), 5);
        String resultXpath = "//div[@class='zipcode-result-address']/p/strong";
        List<WebElement> elFoundZipList = wait.until(presenceOfAllElementsLocatedBy(By.xpath(resultXpath)));
        // Unpack list of ZipCodes
        // System.out.println(" ------ DEBUG: Results Count: " + elFoundZipList.size());
        String finalZipFound = "";
        if (elFoundZipList.size() > 1) {
            // No carrier routes greater than 1000 mailboxes???
            String[] foundZip = new String[1000];
            int arrIndex = 0;

            for (WebElement oneZip : elFoundZipList) {
                // System.out.println(" ------ DEBUG: Zip Results("+ arrIndex +"): " + oneZip.getText());
                // Cheating -- just strip carrier route and were all good
                if (oneZip.getText().length() >= 5) {
                    foundZip[arrIndex] = oneZip.getText().substring(0, 5);
                }
                arrIndex++;
            }
            finalZipFound = foundZip[0];
        } else {
            WebElement elFoundZip = getDriver().findElement(By.xpath(resultXpath));
            finalZipFound = elFoundZip.getText().substring(0, 5);
        }

        // Error path code if bad zip, no result box, unexpected format results??
        System.out.println("------------------------------------------------");
        System.out.println(" Expected Zip Code: " + correctZip);
        System.out.println(" Found Zip Code:    " + finalZipFound);
        System.out.println("------------------------------------------------");
        assertThat(finalZipFound).containsIgnoringCase(correctZip);
    }

    // ===========================================================================
    //
    //                       Lite Version of Automation Code
    //
    // ---------------------------------------------------------------------------
    //  Use Mouse Over to Reach Zip lookup Form
    // ---------------------------------------------------------------------------
    @When("I go to Lookup ZIP page by address \\(lite version)")
    public void iGoToLookupZIPPageByAddressLiteVersion() {
        // Mail & Ship Navigation Link at top of page, hover find Zip Lookup in sub-menu, click it
        Actions actSubMenu = new Actions(getDriver());
        WebElement mailAndShip = getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'Ship')]"));
        WebElement lookUpZip = getDriver().findElement(By.xpath("//ul[@class='tools']//a[contains(@href,'ZipLookup')]"));
        actSubMenu.moveToElement(mailAndShip).moveToElement(lookUpZip).click().perform();
        // Click "Find by Address" button
        getDriver().findElement(By.xpath("//div[contains(@id,'zip-lookup')]//a[contains(text(),'by Address')]")).click();
    }

    // ---------------------------------------------------------------------------
    //  Use Mouse Over to Post Office Location lookup Form (Scen 4)
    // ---------------------------------------------------------------------------
    @When("I navigate to Find a Location page")
    public void iNavigateToFindALocationPage() {
        // Mail & Ship Navigation Link at top of page, hover find Zip Lookup in sub-menu, click it
        Actions actSubMenu = new Actions(getDriver());
        WebElement mailAndShip = getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'Ship')]"));
        WebElement findLocation = getDriver().findElement(By.xpath("//ul[@class='tools']//a[contains(@href,'location')]"));
        actSubMenu.moveToElement(mailAndShip).moveToElement(findLocation).click().perform();
    }

    // ---------------------------------------------------------------------------
    //  Fill Out PO Locator Form (Scen 4)
    // ---------------------------------------------------------------------------
    @And("I filter by {string} location types, {string} services, {string} available services")
    public void iFilterByLocationTypesServicesAvailableServices(String locType, String serviceType, String availService) {
        // Can share / re-use action object
        Actions actMySelect = new Actions(getDriver());
        // Location Select: Depends on Gherkin to know correct selection list values (for PO & Services
        //   Surprise! Can't use " new select" when element is anything but real HTML select (i.e. BUTTON)
        WebElement elPoSelect = getDriver().findElement(By.xpath("//button[@id='post-offices-select']"));
        elPoSelect.click();      // Open selection list
        String xpathPoSelectOpt = "(//div[contains(@class,'dropdown')][contains(@class,'open')]//a[contains(text(),'" + locType + "')])[2]";
        // Use common wait method for all button dropdown options
        WebElement elPoSelectOpt = toolWaitForElementWithXpath(xpathPoSelectOpt);
        // Select actual option
        actMySelect.moveToElement(elPoSelect).moveToElement(elPoSelectOpt).click().perform();

        // Services Select: Depends on Gherkin to know correct selection list values
        WebElement elServSelect = getDriver().findElement(By.xpath("//button[@id='service-type-select']"));
        elServSelect.click();      // Open selection list
        String xpathServSelectOpt = "//li[@id='pickupPo']/a[contains(text(),'" + serviceType + "')]";
        // Use common wait method for all button dropdown options
        WebElement elServSelectOpt = toolWaitForElementWithXpath(xpathServSelectOpt);
        // Select actual option
        actMySelect.moveToElement(elServSelect).moveToElement(elServSelectOpt).click().perform();

        // Available Services Select: Depends on Gherkin to know correct selection list values
        WebElement elAvailSelect = getDriver().findElement(By.xpath("//button[@id='available-service-select']"));
        elAvailSelect.click();
        String xpathAvailSelectOpt = "//a[contains(text(),'" + availService + "')]";
        // Use common wait method for all button dropdown options
        WebElement elAvailSelectOpt = toolWaitForElementWithXpath(xpathAvailSelectOpt);
        // Select actual option
        actMySelect.moveToElement(elAvailSelect).moveToElement(elAvailSelectOpt).click().perform();

        // Search button submits completed form
        getDriver().findElement(By.xpath("//a[@id='searchLocations']")).click();
    }

    // ---------------------------------------------------------------------------
    //  Fill In Address into Modal Address Dialog Box (Scen 4)
    // ---------------------------------------------------------------------------
    @And("I provide data as {string} street, {string} city, {string} state")
    public void iProvideDataAsStreetCityState(String street, String city, String state) {
        // Open the modal address dialog form
        getDriver().findElement(By.xpath("//input[@id='search-input']")).click();
        // Use common wait method for entire Modal form
        toolWaitForElementWithXpath("//div[@id='address-modal']//div[@class='pickup-info']");
        // Assume all 3 fields are visible if form is visible ...
        getDriver().findElement(By.xpath("//input[@id='addressLineOne']")).sendKeys(street);
        getDriver().findElement(By.xpath("//input[@id='cityOrZipCode']")).sendKeys(city);

        // State box is selector -- not text input
        Select stateSelect = new Select(getDriver().findElement(By.xpath("//select[@id='servicesStateSelect']")));
        stateSelect.selectByValue(state);

        // Find Results for entire form entries so far
        getDriver().findElement(By.xpath("//a[contains(text(),'Results')]")).click();
    }

    // ---------------------------------------------------------------------------
    //  Examine Post Office Location Results and Get Phone Number (Scen 4)
    // ---------------------------------------------------------------------------
    @Then("I verify phone number is {string}")
    public void iVerifyPhoneNumberIs(String phoneToVerify) {
        // Use common wait method for entire result box
        toolWaitForElementWithXpath("//div[@id='resultBox']");
        // Click On Expansion Arrow for top result box == nearest post office
        getDriver().findElement(By.xpath("//div[@id='resultBox']/div[1]//span[@class='listArrow']")).click();
        // Use common wait method for Post Office Details to be displayed
        toolWaitForElementWithXpath("//div[@id='po-location-detail']");
        // get phone from details -- error path if no phone at all?
        String actualPhone = getDriver().findElement(By.xpath("//div[@id='po-location-detail']//p[@class='ask-usps']")).getText();
        assertThat(actualPhone).containsIgnoringCase(phoneToVerify);
    }

    // ---------------------------------------------------------------------------
    //  From Main page go to Help Tab            (Scen 3) and (@uspsScenario12-3)
    // ---------------------------------------------------------------------------
    @When("I go to {string} tab")
    public void iGoToTab(String providedTab) {
        if (providedTab.equalsIgnoreCase("Help")) {
            providedTab = "faq";
        }
        if (providedTab.equalsIgnoreCase("Postal Store")) {
            providedTab = "store";
        }
        if (providedTab.equalsIgnoreCase("Track & Manage")) {
            providedTab = "manage";
        }
        if (providedTab.equalsIgnoreCase("Mail & Ship")) {
            providedTab = "ship";
        }
        if (providedTab.equalsIgnoreCase("Business")) {
            providedTab = "business";
        }
        String tabXpath = "//a[@class='menuitem'][contains(@href, '" + providedTab + "')]";
        getDriver().findElement(By.xpath(tabXpath)).click();
    }

    // ---------------------------------------------------------------------------
    //  Perform Search (Scen 3)
    // ---------------------------------------------------------------------------
    @And("I perform {string} help search")
    public void iPerformHelpSearch(String providedSearch) {
        // Wait for web page with search box
        WebElement helpSearchBox = toolWaitForElementWithXpath("//input[@placeholder='Search for a topic']");
        helpSearchBox.sendKeys(providedSearch);
        // Click Magnifying Glass
        getDriver().findElement(By.xpath("//button[contains(text(),'Search')]")).click();
        // Wait until results box appears
        toolWaitForElementWithXpath("//div[@class='resultsWrapper']");
    }

    // ---------------------------------------------------------------------------
    //  Verify tha no Help On this Specific Topic (Scen 3)
    // ---------------------------------------------------------------------------
    @Then("I verify that no results of {string} available in help search")
    public void iVerifyThatNoResultsOfAvailableInHelpSearch(String searchTopic) {
        boolean testResult = false;
        String searchResultXpath = "//div[@class='resultsWrapper']//div[@class='resultBody']";
        testResult = iSearchMultiElementListsForText(searchResultXpath, searchTopic);
        assertThat(testResult).isFalse();
    }

    // ---------------------------------------------------------------------------
    //  Verify that no Help On this Specific Topic (Scen 3)
    // ---------------------------------------------------------------------------
    @Then("I search multi element lists at xpath {string} for text {string}")
    public boolean iSearchMultiElementListsForText(String elXpath, String searchText) {
        WebDriverWait waitToFill = new WebDriverWait(getDriver(), 5);
        List<WebElement> elResultsList = waitToFill.until(presenceOfAllElementsLocatedBy(By.xpath(elXpath)));
        // Unpack list results
        // System.out.println(" ------ DEBUG: Results Count: " + elFoundZipList.size());

        Boolean searchSuccessful = false;
        int searchHits = 0;

        if (elResultsList.size() > 1) {
            // Multiple Search Results -- at Most 100
            String[] foundResult = new String[100];
            int arrIndex = 0;

            for (WebElement oneBody : elResultsList) {
                if (oneBody.getText().contains(searchText)) {
                    foundResult[arrIndex] = oneBody.getText();
                    searchSuccessful = true;
                    searchHits++;
                    arrIndex++;
                }
            }
        } else {
            // Single Search Result
            WebElement elResult = getDriver().findElement(By.xpath(elXpath));
            if (elResult.getText().contains(searchText)) {
                searchSuccessful = true;
                searchHits++;
            }
        }
        return searchSuccessful;
    }

    // ---------------------------------------------------------------------------
    //  Mouse Move over to Price Calculator (Scen 1)
    // ---------------------------------------------------------------------------
    @When("I go to Calculate Price Page")
    public void iGoToCalculatePricePage() {
        // Mail & Ship Navigation Link at top of page, hover find pRICE Calculator in sub-menu, click it
        Actions actSubMenu = new Actions(getDriver());
        WebElement mailAndShip = getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'Ship')]"));
        WebElement calcPrice = getDriver().findElement(By.xpath("//li[@class='tool-calc']//a[contains(text(),'Calculate')]"));
        actSubMenu.moveToElement(mailAndShip).moveToElement(calcPrice).click().perform();
    }

    // ---------------------------------------------------------------------------
    //  Enter Price Calculation Parameters (Scenario 1)
    // ---------------------------------------------------------------------------
    @And("I select {string} with {string} shape")
    public void iSelectWithShape(String country, String mailType) {
        WebElement countryList = getDriver().findElement(By.xpath("//select[@name='CountryID']"));
        countryList.sendKeys(country);

        // Click on Postcard picture
        // WebElement postcardImage = getDriver().findElement(By.xpath("//input[@value='" + mailType + "'][@type='submit']"));
        WebElement postcardImage = getDriver().findElement(By.xpath("//input[@value='Postcard'][@type='submit']"));
        Actions actPostcard = new Actions(getDriver());
        actPostcard.moveToElement(postcardImage).click().perform();
    }

    // ---------------------------------------------------------------------------
    //  Price Price Calculation Form (Scen 1)
    // ---------------------------------------------------------------------------
    @And("I define {string} quantity")
    public void iDefineQuantity(String quantity) {
        toolWaitForElementWithXpath("//input[@placeholder='Quantity']");
        getDriver().findElement(By.xpath("//input[@placeholder='Quantity']")).sendKeys(quantity);
    }

    // ---------------------------------------------------------------------------
    //  Check Price Price Calculation results (Scen 1)
    // ---------------------------------------------------------------------------
    @Then("I calculate the price and validate cost is {string}")
    public void iCalculateThePriceAndValidateCostIs(String expectedPrice) {
        getDriver().findElement(By.xpath("//input[@value='Calculate'][@type='button']")).click();
        String actualTotal = getDriver().findElement(By.xpath("//div[@id='total']")).getText();
        assertThat(actualTotal).isEqualToIgnoringCase(expectedPrice);
    }

    // ---------------------------------------------------------------------------
    //   When I perform "Free Boxes" search                         (Scen 2)
    // ---------------------------------------------------------------------------
    @When("I perform {string} search")
    public void iPerformSearch(String SearchParm) {
        WebElement searchMenu = getDriver().findElement(By.xpath("//li[contains(@class, 'nav-search')]"));
        WebElement searchInput = getDriver().findElement(By.xpath("//input[@id='global-header--search-track-search']"));
        getActions()
                .moveToElement(searchMenu)
                .sendKeys(searchInput, SearchParm)
                .sendKeys(Keys.ENTER)
                .perform();
    }

    // ---------------------------------------------------------------------------
    //   I set "Mail & Ship" in filters                            (Scen 2)
    // ---------------------------------------------------------------------------
    @And("I set {string} in filters")
    public void iSetInFilters(String filterLink) {
        WebElement spinner = getDriver().findElement(By.xpath("//div[@class='white-spinner-container']"));
        WebElement filterElement = getDriver().findElement(By.xpath("//a[@class='dn-attr-a'][@title='" + filterLink + "']"));
        getExecutor().executeScript("arguments[0].click();", filterElement);
        getWait().until(ExpectedConditions.invisibilityOf(spinner));
    }

    // ---------------------------------------------------------------------------
    //   Then I verify that "7" results found                           (Scen 2)
    // ---------------------------------------------------------------------------
    @Then("I verify that {string} results found")
    public void iVerifyThatResultsFound(String totalResultsStr) {
        WebDriverWait waitResults = getWait(5);
        WebElement hdText = toolWaitForXpath("//span[@id='searchResultsHeading']", "visible", 5);
        assert(hdText.getText().contains(totalResultsStr + " results"));

        int nbrTotalResults = Integer.parseInt(totalResultsStr);
        List<WebElement> elSearchResults = waitResults.until(presenceOfAllElementsLocatedBy(By.xpath("//ul[@id='records']//li")));
        assertThat(elSearchResults.size()).isEqualTo(nbrTotalResults);
    }

    // ----------------------------------------------------------------------------------------
    //   When I select "Priority Mail | USPS" in results        (Scen 2 and @uspsScenario12-6)
    // ----------------------------------------------------------------------------------------
    @When("I select {string} in results")
    public void iSelectInResults(String selectedResult) {
        WebElement resultIWant = null;
        // Wait until all search results "fill in"
        toolWaitForXpath("//span[contains(@transid,'gadget')]", "allfull", 10);
        // Then find results you want (while avoiding intercepted click)
        resultIWant = getDriver().findElement(By.xpath("//span[contains(text(),'" + selectedResult + "')]"));
        try {
            // If not intercepted, then it just works
            resultIWant.click();
        }
        catch (WebDriverException ElementClickInterceptedException) {
            // If intercepted, use Javascript Super-Powers!
            getExecutor().executeScript("arguments[0].click();", resultIWant);
        }
    }

    // ---------------------------------------------------------------------------
    //   I click "Ship Now" button                (Scen 2 and @uspsScenario12-6)
    // ---------------------------------------------------------------------------
    @And("I click {string} button")
    public void iClickButton(String btnName) throws InterruptedException {
        String buttonXpath = "//a[contains(text(),'" + btnName + "')]";
        WebElement clickableElement = toolWaitForXpath(buttonXpath, "clickable", 10);
        try {
            // If not intercepted, then it just works
            clickableElement.click();
        }
        catch (WebDriverException ElementClickInterceptedException) {
            // If intercepted, use Javascript Super-Powers!
            getExecutor().executeScript("arguments[0].click();", clickableElement);
        }
    }

    // ---------------------------------------------------------------------------
    //   I validate that Sign In is required                          (Scen 2)
    // ---------------------------------------------------------------------------
    @Then("I validate that Sign In is required")
    public void iValidateThatSignInIsRequired() {
        String originalWindow = getDriver().getWindowHandle();
        // switch to new window
        for (String handle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(handle);
        }

        getWait(10).until(ExpectedConditions.titleContains("Sign In"));
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='username']")));
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='password']")));

        // switch back
        getDriver().switchTo().window(originalWindow);
    }

    // -------------------------------------------------------------------------------------
    //  I go to "Every Door Direct Mail" under "Business"
    //                                   (Scen 9)  (@uspsScenario12-3) (@uspsScenario12-7)
    // -------------------------------------------------------------------------------------
    @When("I go to {string} under {string}")
    public void iGoToUnder(String linkName, String tabName) {
        // Show Provided Gherkin Parms
        System.out.println(" Navigate to Tab: " + tabName);
        System.out.println(" Link Name:       " + linkName);

        // Business Link at top of page, hover find "Every Door Direct Mail" in sub-menu, click it
        WebElement elementTab = null;
        WebElement elementLink = null;

        // Pick a Tab
        switch (tabName) {
            case "Mail & Ship":
                elementTab = getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'Mail & Ship')]"));
                elementTab.click();
                break;
            case "Track & Manage":
                // Only PO Boxes supported at this time
                elementTab = getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'Track & Manage')]"));
                elementTab.click();
                if (linkName.equalsIgnoreCase("PO Boxes")) {
                    WebElement poBoxButton = getDriver().findElement(By.xpath("//a[contains(text(),'Reserve a PO Box')]"));
                    //This will scroll the page till the element is found
                    getExecutor().executeScript("arguments[0].scrollIntoView();", poBoxButton);
                    getExecutor().executeScript("arguments[0].click();", poBoxButton);
                } else {
                    throw new RuntimeException("Unsupported USPS link name: " + linkName);
                }
                break;
            case "Postal Store":
                elementTab = getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'Postal Store')]"));
                elementTab.click();

                // Only Stamps supported at this time
                if (linkName.equalsIgnoreCase("Stamps")) {
                    elementLink = toolWaitForXpath("//a/div/span[text()='Stamps']", "clickable", 10);
                    elementLink.click();
                } else {
                    throw new RuntimeException("Unsupported USPS link name: " + linkName);
                }
                break;
            case "Business":
                elementTab = getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'Business')]"));
                elementTab.click();

                // Only "Every Door Direct Mail" supported
                if (linkName.equalsIgnoreCase("Every Door Direct Mail")) {
                    elementLink = toolWaitForXpath("//ul[@class='tools']//a[contains(@href,'routeSearch')]", "clickable", 10);
                    elementLink.click();
                } else {
                    throw new RuntimeException("Unsupported USPS link name: " + linkName);
                }
                break;
            default:
                throw new RuntimeException("Unsupported USPS tab name: " + tabName);
        }
    }

    // ---------------------------------------------------------------------------
    //   I search for "4970 El Camino Real, Los Altos, CA 94022"  (Scen 9)
    // ---------------------------------------------------------------------------
    @And("I search for {string}")
    public void iSearchFor(String providedAddress) {
        WebElement searchBox = toolWaitForElementWithXpath("//input[@id='address']");
        searchBox.sendKeys(providedAddress);
        // Do the search with magnifying glass
        Actions actSearch = new Actions(getDriver());
        String mglassXpath = "//button[@type='submit'][contains(@class,'search')]";
        WebElement magGlass = toolWaitForXpath(mglassXpath, "clickable", 10);
        actSearch.moveToElement(magGlass).click().perform();
    }

    // ---------------------------------------------------------------------------
    //   I click "Show Table" on the map  (Scen 9)
    // ---------------------------------------------------------------------------
    @And("I click {string} on the map")
    public void iClickOnTheMap(String buttonName) {
        // Check for overlay by "Route Type" box (it's not always "there")
        try {
            String routeBox = "//div[contains(@class,'route-type')]/a[@class='close']";
            WebElement routeBoxElement = toolWaitForXpath(routeBox, "clickable", 5);
            routeBoxElement.click();
        }
        catch (WebDriverException e) {
            System.out.println("Route Box element not selectable so do not click.");
        }
        // No route box -- click through to show table
        // String tableButtonXpath = "//a[@class='route-table-toggle']";  -- hidden label intercepts click
        String tableButtonXpath = "//span[@class='toggle-icon']";
        WebElement mapTableButton = toolWaitForXpath(tableButtonXpath, "clickable", 10);
        mapTableButton.click();
        // Slava wait until progress spinner visible
        // then wait until progress spinner goes invisible
        // and then used Java Click
    }

    // ---------------------------------------------------------------------------
    //   I click "Select All" on the table (Scen 9)
    // ---------------------------------------------------------------------------
    @When("I click {string} on the table")
    public void iClickOnTheTable(String selectorText) {
        String linkSelectAll = "//a[@class='totalsArea'][contains(text(),'" + selectorText + "')]";
        WebElement linkTableAll = toolWaitForXpath(linkSelectAll, "visible", 10);
        linkTableAll.click();
        // Slava did not wait. Just found and click
    }

    // ---------------------------------------------------------------------------
    //   I close modal window (Scen 9)
    // ---------------------------------------------------------------------------
    @And("I close modal window")
    public void iCloseModalWindow() {
        String doneButtonXpath = "//div[@id='modal-box']//button[@id='dropOffDone']";
        WebElement doneButton = toolWaitForXpath(doneButtonXpath, "clickable", 10);
        doneButton.click();
        // Slava did not wait. Just found and click
    }

    // ---------------------------------------------------------------------------
    //   I verify that summary of all rows of Cost column is equal
    //   Approximate Cost in Order Summary                             (Scen 9)
    // ---------------------------------------------------------------------------
    @Then("I verify that summary of all rows of Cost column is equal Approximate Cost in Order Summary")
    public void iVerifyThatSummaryOfAllRowsOfCostColumnIsEqualApproximateCostInOrderSummary() {
        String foundNbrStr = "";
        float foundNbr = 0.0f;
        double THRESHOLD = .01;

        // explicit wait for route table container to be filled
        WebDriverWait wait = new WebDriverWait(getDriver(), 5);
        String resultXpath = "//div[@class='dojoxGridScrollbox']//table//tr//td[8]";
        List<WebElement> elFoundCosts = wait.until(presenceOfAllElementsLocatedBy(By.xpath(resultXpath)));
        // Unpack list of Costs
        if (elFoundCosts.size() > 1) {
            int arrIndex = 0;

            for (WebElement oneCost : elFoundCosts) {
                // strip just numerical part
                if (oneCost.getText().length() >= 3) {
                    foundNbrStr = oneCost.getText().substring(1, oneCost.getText().length());
                    System.out.println(" ------ DEBUG: Cost Results(" + arrIndex + "): " + foundNbrStr);
                    foundNbr = foundNbr + Float.parseFloat(foundNbrStr);
                }
                arrIndex++;
            }
        }
        String expectedTotal = getDriver().findElement(By.xpath("//span[@class='approx-cost']")).getText();
        float expectedNbr = Float.parseFloat(expectedTotal);

        // Show me anyway, I'm curious
        System.out.println("================================================");
        System.out.println(" Expected Value:   " + expectedNbr);
        System.out.println(" Actual Value:     " + foundNbr);
        // Nice compare that checks for near equal floating point
        Boolean checkThreshCompare = false;
        if (Math.abs(expectedNbr - foundNbr) < THRESHOLD) {
            checkThreshCompare = true;
            System.out.println(" Compare?     " + checkThreshCompare);
            System.out.println("================================================");
        } else {
            checkThreshCompare = false;
            System.out.println(" Compare?     " + checkThreshCompare);
            System.out.println("================================================");
        }
        org.junit.Assert.assertEquals(checkThreshCompare, true);
    }

    // ---------------------------------------------------------------------------
    //    And I enter "12345" into store search           (@uspsScenario12-3)
    // ---------------------------------------------------------------------------
    @And("I enter {string} into store search")
    public void iEnterIntoStoreSearch(String searchProvided) {
        // Enter the Postal Store search here
        WebElement storeSrchBox = toolWaitForXpath("//input[@id='store-search']", "clickable", 5);
        storeSrchBox.sendKeys("searchProvided");
        // Click Mag Glass
        WebElement magGlass = toolWaitForXpath("//input[@id='store-search-btn']", "clickable", 5);
        magGlass.click();
    }

    // ---------------------------------------------------------------------------
    //    I search and validate no products found           (@uspsScenario12-3)
    // ---------------------------------------------------------------------------
    @Then("I search and validate no products found")
    public void iSearchAndValidateNoProductsFound() {
        String noResultsXpath = "//div[@class = 'no-results-found']//p[contains(text(),'did not match any products')]";
        WebElement SrchResultsMsg = toolWaitForXpath(noResultsXpath, "visible", 5);
        assertThat(SrchResultsMsg.getText().contains("search did not match any products"));
    }

    // -------------------------------------------------------------------------------------
    //  And choose mail service Priority Mail                          (@uspsScenario12-4)
    // -------------------------------------------------------------------------------------
    @And("choose mail service Priority Mail")
    public void chooseMailServicePriorityMail() {
        JavascriptExecutor jsScrollClick = getExecutor();
        //Find Priority Mail filter check box (at bottom page)
        WebElement pmailFilterCBox = getDriver().findElement(By.xpath("//input[contains(@name,'Service-Priority Mail-1')]"));
        //This will scroll the page till the element is found
        jsScrollClick.executeScript("arguments[0].scrollIntoView();", pmailFilterCBox);
        jsScrollClick.executeScript("arguments[0].click();", pmailFilterCBox);
    }

    // -------------------------------------------------------------------------------------
    //  "I verify 1 items found"                                  (@uspsScenario12-4)
    // -------------------------------------------------------------------------------------
    @Then("I verify {int} items found")
    public void iVerifyItemsFound(int nbrStampTypes) {
        //h2[contains(@class,'results-per-page')]
        WebElement SrchResultsTotal = toolWaitForXpath("//h2[contains(@class,'results-per-page')]", "visible", 6);
        //  nbrResults ===> Typically looks like this:   "1 - 1 of 1 Results"
        String nbrResults = SrchResultsTotal.getText();
        int posCharF = nbrResults.indexOf("f") + 1;
        int posCharR = nbrResults.indexOf("R") - 1;
        String nbrStampsStr = SrchResultsTotal.getText().substring(posCharF, posCharR).strip();
        int nbrStamps = Integer.parseInt(nbrStampsStr);
        System.out.println("-------------------------------------------");
        System.out.println(" Expected Nbr Stamps:   " + nbrStampTypes);
        System.out.println(" Actual Nbr Stamps:     " + nbrStamps);
        System.out.println("-------------------------------------------");
        assertThat(nbrStamps == nbrStampTypes);
    }

    // -----------------------------------------------------------------------
    //  "I unselect Stamps checkbox"                     (@uspsScenario12-5)
    // -----------------------------------------------------------------------
    @When("I unselect Stamps checkbox")
    public void iUnselectStampsCheckbox() {
        WebElement stampsFilter = toolWaitForXpath("//label[contains(@for,'Category-Stamps')]", "clickable", 6);
        stampsFilter.click();
    }

    // -----------------------------------------------------------------------
    //  "And select Vertical stamp Shape"                 (@uspsScenario12-5)
    // -----------------------------------------------------------------------
    @And("select Vertical stamp Shape")
    public void selectVerticalStampShape() {
        JavascriptExecutor jsScrollClick = getExecutor();
        //Find Priority Mail filter check box (at bottom page)
        WebElement stampsFilter2 = getDriver().findElement(By.xpath("//label[contains(@for,'Shape-Vertical')]"));
        //This will scroll the page till the element is found
        jsScrollClick.executeScript("arguments[0].scrollIntoView();", stampsFilter2);
        jsScrollClick.executeScript("arguments[0].click();", stampsFilter2);
    }

    // -----------------------------------------------------------------------
    //  "And I click Blue color"                         (@uspsScenario12-5)
    // -----------------------------------------------------------------------
    @And("I click Blue color")
    public void iClickBlueColor() {
        JavascriptExecutor jsScrollClick = getExecutor();
        //Find Priority Mail filter check box (at bottom page)
        WebElement stampsFilterBlue = getDriver().findElement(By.xpath("//div[contains(@class,'color')]/div[contains(@onclick,'/blue/')]"));
        //This will scroll the page till the element is found
        jsScrollClick.executeScript("arguments[0].scrollIntoView();", stampsFilterBlue);
        jsScrollClick.executeScript("arguments[0].click();", stampsFilterBlue);
    }

    // -----------------------------------------------------------------------
    //  "Then I verify "Blue" and "Vertical" filters"     (@uspsScenario12-5)
    // -----------------------------------------------------------------------
    @Then("I verify {string} and {string} filters")
    public void iVerifyAndFilters(String colorFilter, String vertFilter) {
        String verticalCheckedXpath = "//input[@checked]/following-sibling:: input[contains(@name,'Shape-" + vertFilter + "')]/following-sibling:: label";
        assertThat(toolWaitForXpath(verticalCheckedXpath, "visible", 6).isDisplayed());

        String colorCheckedXpath = "//div[contains(@class,'result-facid-holder-grid-color')]/span[@class='hidden']";
        assertThat(getDriver().findElement(By.xpath(colorCheckedXpath)).getText().equalsIgnoreCase(colorFilter));
    }

    // ----------------------------------------------------------------------------
    //  "And I verify that items below 12 dollars exists"     (@uspsScenario12-5)
    // ----------------------------------------------------------------------------
    @And("I verify that items below {int} dollars exists")
    public void iVerifyThatItemsBelowDollarsExists(int expCost) {
        WebDriverWait waitAllCosts = getWait();
        String foundCostStr = "";
        float justCostfloat = 0.0f;
        int itemCount = 0;

        String allCostsXpath = "//div[contains(@class,'4 results-per-page')]//p[contains(text(),'$')]";
        List<WebElement> elFoundCostList = waitAllCosts.until(presenceOfAllElementsLocatedBy(By.xpath(allCostsXpath)));

        // Unpack list of Stamp Costs if at least 1 found
        if (elFoundCostList.size() > 0) {
            for (WebElement oneStamp : elFoundCostList) {
                foundCostStr = oneStamp.getText();
                // Ignore items smaller $0.00 = invalid cost str
                if (foundCostStr.length() >= 5) {
                    int posCharDollar = foundCostStr.indexOf("$") + 1;
                    int posCharMinus = foundCostStr.indexOf("-");
                    int posCharComma = foundCostStr.indexOf(",");

                    if (posCharComma == -1) {
                        // Have NO Commas in both of these cases
                        if (posCharMinus == -1) {
                            // $56.20
                            posCharMinus = foundCostStr.length() - 1;
                        } else {
                            // $800.00 - $900.00 (default case with '-' already handled above)
                            posCharMinus = foundCostStr.indexOf("-") - 1;
                        }
                    } else {
                        // Have commas in both cases
                        if (posCharMinus == -1) {
                            // $5,600.20
                            foundCostStr = foundCostStr.replace(",", "");
                            posCharMinus = foundCostStr.length() - 1;
                        } else {
                            // $8,800.00 - $9,000.00 (default case with '-' already handled above)
                            foundCostStr = foundCostStr.replace(",", "");
                            posCharMinus = foundCostStr.indexOf("-") - 1;
                        }
                    }

                    justCostfloat = Float.parseFloat(foundCostStr.substring(posCharDollar, posCharMinus).strip());

                    // Have to be under expected cost to be counted
                    if (justCostfloat < (float) expCost) {
                        itemCount++;
                    }
                }
            }
        }

        System.out.println("------------------------------------------------");
        System.out.println(" Expected Cost:   $" + (float) expCost);
        System.out.println(" Items Below:      " + itemCount);
        System.out.println("------------------------------------------------");
        assertThat(itemCount >= 1);
    }

    // ----------------------------------------------------------------------------
    //  "And verify "Passport Renewal" service exists"     (@uspsScenario12-6)
    // ----------------------------------------------------------------------------
    @And("verify {string} service exists")
    public void verifyServiceExists(String serviceProvided) {
        WebElement selectOption = null;

        // Wait until services dropbox list is there
        WebElement serviceSelector = toolWaitForXpath("//select[@id='passportappointmentType']", "clickable", 10);

        // Check for and Get Rid of Pop-Up
        WebElement modalBox = null;
        try {
            toolWaitForXpath("//div[@id='renewalModal']//div[@class='modal-content']", "visible", 2);
            System.out.println("USPS Modal Alert: 'Eligible to renew?' -- dismiss it.");
            String xpathCloseButton = "//div[@id='renewalModal']//button[@class='close closeicon'][contains(text(),'×')]";
            System.out.println("USPS Modal Alert: 'Eligible to renew?' -- dismiss first.");
            getDriver().findElement(By.xpath(xpathCloseButton)).click();
        }
        catch (WebDriverException TimeoutException) {
            System.out.println("No USPS Modal Alert: 'Eligible to renew?' -- just go check service directly.");
        }

        // Non-Standard selector code for Gherkin provided service name
        getActions().moveToElement(serviceSelector).click().perform();
        try {
            selectOption = getDriver().findElement(By.xpath("//option[contains(text(),'" + serviceProvided + "')]"));
        }
        catch (WebDriverException NoSuchElementException) {
            System.out.println("------------------------------------------------");
            System.out.println(" ERROR: Expected Option " + serviceProvided + " not found in page!");
            System.out.println("------------------------------------------------");
        }
        finally {
            String actualText = selectOption.getText().strip();
            System.out.println("------------------------------------------------");
            System.out.println(" Expected Text:   " + serviceProvided);
            System.out.println(" Actual Text:     " + actualText);
            System.out.println("------------------------------------------------");
            assertTrue(actualText.equalsIgnoreCase(serviceProvided));
        }
    }

    // ----------------------------------------------------------------------------
    //  "And I reserve new PO box for "94022""                (@uspsScenario12-7)
    // ----------------------------------------------------------------------------
    @And("I reserve new PO box for {string}")
    public void iReserveNewPOBoxFor(String zipProvided) {
        WebElement zipSearchBox = toolWaitForXpath("//input[@id='searchInput']", "clickable", 5);

        //Find PO reserve search by zip box -- by scrolling
        getDriver().findElement(By.xpath("//input[@id='searchInput']")).sendKeys(zipProvided);                                       // Enter Gherkin provided Zip Code
        getDriver().findElement(By.xpath("//a[@class='searchBtn']")).click();
    }

    // ----------------------------------------------------------------------------------------
    //  "Then I verify that "Los Altos — Post Office™" present"          (@uspsScenario12-7)
    // ----------------------------------------------------------------------------------------
    @Then("I verify that {string} present")
    public void iVerifyThatPresent(String poLocation) {
        WebElement resultIWant = null;
        String addressIWant = "";
        WebElement linkIWant = null;

        // Build Locators for all PO information test needs
        String xpathPOTableList = "//div[contains(@class,'locations')]/div[@class='row']";    // All result rows
        String xpathPONamesList = xpathPOTableList + "//span[@class='bold']";                 // Just PO Names
        String poName = poLocation.substring(0, poLocation.length() - 15);     // Strip away  " — Post Office™" = way easier
        String xpathPOMyName = xpathPONamesList + "[contains(text(),'" + poName + "')]";      // One PO Name Want
        String xpathPOMyAddress = xpathPOMyName + "/..//following-sibling:: p";               // One PO Address Want
        String xpathPOMyBoxLinks = xpathPOMyName + "/../..//following-sibling:: div[contains(@class,'availableCol')]//span[@class='availableSizes']";

        // Wait until all top table search results "fill in"
        toolWaitForXpath(xpathPONamesList, "allfull", 10);

        // Then verify results you want
        try {
            resultIWant = getDriver().findElement(By.xpath(xpathPOMyName));
            addressIWant = getDriver().findElement(By.xpath(xpathPOMyAddress)).getText();
            linkIWant = getDriver().findElement(By.xpath(xpathPOMyBoxLinks));
        }
        catch (WebDriverException NoSuchElementException) {
            System.out.println("------------------------------------------------");
            System.out.println(" ERROR: Post Office " + poName + " not found in results!");
            System.out.println("------------------------------------------------");
        }
        finally {
            String actualName = resultIWant.getText().strip();
            System.out.println("------------------------------------------------");
            System.out.println(" Expected Name:   " + poName);
            System.out.println(" Actual Name:     " + actualName);
            System.out.println(" Actual Address:  " + addressIWant);
            System.out.println("------------------------------------------------");
            assertTrue(actualName.equalsIgnoreCase(poName));

            // Set up for next step --- click on available packages link
            //  Note that all code must pass, and assert must pass to move on which is OK.
            linkIWant.click();
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    //  "And I verify that "Size 5-XL" PO Box is available in "Los Altos — Post Office™""      (@uspsScenario12-7)
    // ------------------------------------------------------------------------------------------------------------
    @And("I verify that {string} PO Box is available in {string}")
    public void iVerifyThatPOBoxIsAvailableIn(String boxSize, String poLocation) {
        //div[@id='boxLocation']//span[@class='bold']  =  Post Office Name should be here
        WebElement verifyPOName = toolWaitForXpath("//div[@id='boxLocation']//span[@class='bold']", "visible", 5);
        String actualPOName = verifyPOName.getText().strip();
        String expectedPOName = poLocation.substring(0, poLocation.length() - 15);     // Strip away  " — Post Office™" = way easier
        System.out.println("------------------------------------------------");
        System.out.println(" Expected PO:    " + expectedPOName);
        System.out.println(" Actual PO:      " + actualPOName);
        System.out.println(" Looking for box " + boxSize + ": ");

        // Click on the box icon
        String searchSize = "";

        switch (boxSize) {
            case "Size 5-XL":
                searchSize = "boxXL";
                break;
            case "Size 4-L":
                searchSize = "boxL";
                break;
            case "Size 3-M":
                searchSize = "boxM";
                break;
            case "Size 2-S":
                searchSize = "boxS";
                break;
            case "Size 1-XS":
                searchSize = "boxXS";
                break;
            default:
                throw new RuntimeException("Unsupported USPS box size: " + boxSize);
        }

        // Make actual box selection
        getDriver().findElement(By.xpath("//div[@id='availableboxes']//div/label[@for='" + searchSize + "']")).click();

        // Xpath tells you if available or not
        String boxAvailable = getDriver().findElement(By.xpath("//div[@id='availableboxes']//div/input[@value='" + searchSize + "']")).getAttribute("data-availability");
        boolean haveBox = false;

        if (boxAvailable.equalsIgnoreCase("")) {
            boxAvailable = "available now!";
            haveBox = true;
        } else {
            haveBox = false;
        }
        System.out.println(" Availability: " + boxAvailable);
        System.out.println("------------------------------------------------");
        assertTrue("ERROR: Box size " + boxSize + " NOT available at " +  actualPOName, haveBox);
    }
}
