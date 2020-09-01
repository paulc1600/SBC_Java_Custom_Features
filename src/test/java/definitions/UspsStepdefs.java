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

import static definitions.ATestToolBox.*;
import static org.assertj.core.api.Assertions.assertThat;
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
    //  From Main page go to Help Tab (Scen 3)
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
        WebElement hdText = toolWaitForElementWithXpathAfterSecs("//span[@id='searchResultsHeading']", "visible", 5);
        assert(hdText.getText().contains(totalResultsStr + " results"));

        int nbrTotalResults = Integer.parseInt(totalResultsStr);
        List<WebElement> elSearchResults = waitResults.until(presenceOfAllElementsLocatedBy(By.xpath("//ul[@id='records']//li")));
        assertThat(elSearchResults.size()).isEqualTo(nbrTotalResults);
    }

    // ---------------------------------------------------------------------------
    //   When I select "Priority Mail | USPS" in results               (Scen 2)
    // ---------------------------------------------------------------------------
    @When("I select {string} in results")
    public void iSelectInResults(String selectedResult) {
        getDriver().findElement(By.xpath("//span[text()='" + selectedResult + "']")).click();
    }

    // ---------------------------------------------------------------------------
    //   I click "Ship Now" button                                    (Scen 2)
    // ---------------------------------------------------------------------------
    @And("I click {string} button")
    public void iClickButton(String btnName) throws InterruptedException {
        int numOfWin = getDriver().getWindowHandles().size();
        while (getDriver().getWindowHandles().size() < numOfWin + 1) {
            getDriver().findElement(By.xpath("//a[contains(text(),'" + btnName + "')]")).click();
            Thread.sleep(100);
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

    // ---------------------------------------------------------------------------
    //  I go to "Every Door Direct Mail" under "Business" (Scen 9)
    // ---------------------------------------------------------------------------
    @When("I go to {string} under {string}")
    public void iGoToUnder(String tabName, String linkName) {
        // Business Link at top of page, hover find "Every Door Direct Mail" in sub-menu, click it
        Actions actSubMenu = new Actions(getDriver());
        WebElement businessTab = getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'Business')]"));
        WebElement everyDoor = getDriver().findElement(By.xpath("//ul[@class='tools']//a[contains(@href,'routeSearch')]"));
        actSubMenu.moveToElement(businessTab).moveToElement(everyDoor).click().perform();
        System.out.println(" Navigate to Tab: " + tabName);
        System.out.println(" Link Name:       " + linkName);
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
        WebElement magGlass = toolWaitForElementWithXpathAfterSecs(mglassXpath, "clickable", 10);
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
            WebElement routeBoxElement = toolWaitForElementWithXpathAfterSecs(routeBox, "clickable", 5);
            routeBoxElement.click();
        }
        catch (WebDriverException e) {
            System.out.println("Route Box element not selectable so do not click.");
        }
        // No route box -- click through to show table
        // String tableButtonXpath = "//a[@class='route-table-toggle']";  -- hidden label intercepts click
        String tableButtonXpath = "//span[@class='toggle-icon']";
        WebElement mapTableButton = toolWaitForElementWithXpathAfterSecs(tableButtonXpath, "clickable", 10);
        mapTableButton.click();
    }

    // ---------------------------------------------------------------------------
    //   I click "Select All" on the table (Scen 9)
    // ---------------------------------------------------------------------------
    @When("I click {string} on the table")
    public void iClickOnTheTable(String selectorText) {
        String linkSelectAll = "//a[@class='totalsArea'][contains(text(),'" + selectorText + "')]";
        WebElement linkTableAll = toolWaitForElementWithXpathAfterSecs(linkSelectAll, "visible", 10);
        linkTableAll.click();
    }

    // ---------------------------------------------------------------------------
    //   I close modal window (Scen 9)
    // ---------------------------------------------------------------------------
    @And("I close modal window")
    public void iCloseModalWindow() {
        String doneButtonXpath = "//div[@id='modal-box']//button[@id='dropOffDone']";
        WebElement doneButton = toolWaitForElementWithXpathAfterSecs(doneButtonXpath, "clickable", 10);
        doneButton.click();
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
}
