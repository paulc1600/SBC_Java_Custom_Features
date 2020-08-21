package definitions;

import cucumber.api.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static support.TestContext.getDriver;

public class uspsStepdefs {
    // =============================================
    //  Create test environment variables
    // =============================================
    String tePortMode = "default";
    Integer teBrowserWidth = 1440;
    Integer teBrowserHeight = 900;
    String  tePageURL = "";
    String  tePageTitle = "";
    String  trPageURL = "";
    String  trPageTitle = "";
    String  trWindowHandle = "";
    String  trPageSource = "";
    // =============================================
    //  Create test data variables
    // =============================================
    String  tdAddress = "";
    String  tdCity = "";
    String  tdState = "";

    // ===========================================================================
    @Given("I go to my {string} page")
    public void iGoToMyPage(String page) {
        switch (page) {
            case "quote":
                tePageURL = "https://skryabin.com/market/quote.html";
                tePageTitle = "Quote";
                break;
            case "google":
                tePageURL = "https://www.google.com/";
                tePageTitle = "Google";
                break;
            case "yahoo":
                tePageURL = "https://www.yahoo.com/";
                tePageTitle = "Yahoo";
                break;
            case "usps":
                tePageURL = "https://www.usps.com/";
                tePageTitle = "USPS";
                break;
            default:
                tePageURL = page;
        }

        System.out.println("\n   Navigate: Open URL");
        getDriver().get(tePageURL);
        // Test Results of switching to this page
        trPageTitle = getDriver().getTitle();
        trPageURL = getDriver().getCurrentUrl();
        trWindowHandle = getDriver().getWindowHandle();
        trPageSource = getDriver().getPageSource();
    }

    // ===========================================================================
    //   Determine (default) or Set the Browser Viewport Size
    // ===========================================================================
    @When("I change my resolution to {string}")
    public void iChangeMyResolutionTo(String browserSize) {
        switch (browserSize) {
            case "phone":
                teBrowserWidth = 400;
                teBrowserHeight = 768;
                tePortMode = browserSize;
                break;
            case "desktop":
                teBrowserWidth = 1024;
                teBrowserHeight = 768;
                tePortMode = browserSize;
                break;
            default:
                // Default means leave it alone = whatever size the environment sets = typically MAX
                Dimension browserActualSize = getDriver().manage().window().getSize();
                teBrowserHeight = browserActualSize.getHeight();
                teBrowserWidth = browserActualSize.getWidth();
                tePortMode = "default";
                break;
        }
        if (browserSize.equalsIgnoreCase("phone") ||
                browserSize.equalsIgnoreCase("desktop")) {
            // Gherkin wants to change size, so browser changes to the set dimension
            Dimension newBrowserSize = new Dimension(teBrowserWidth,teBrowserHeight);
            getDriver().manage().window().setPosition(new Point(0,0));
            getDriver().manage().window().setSize(newBrowserSize);
        }
    }

    // ===========================================================================
    @Given("I get my {string} test data from source {string}")
    public void iGetTestDataFromSource(String page, String tdSource) {
        if (page.equalsIgnoreCase("quote")) {
            // Not implemented in this step file
        } else if (page.equalsIgnoreCase("google")) {
            // Not implemented yet
        } else if (page.equalsIgnoreCase("yahoo")) {
            // Not implemented yet
        } else if (page.equalsIgnoreCase("usps")) {
            switch (tdSource) {
                case "default":
                    // Variables are already set to defaults when step file called
                    break;
                case "file":
                    // Override defaults from test data file (not implemented)
                    break;
                default:
                    throw new IllegalStateException("Error: This test data source is invalid: " + tdSource);
            }
        } else {
            throw new RuntimeException("Unsupported page: " + page);
        }
    }

    // ---------------------------------------------------------------------------
    @And("I print all page details {string} source")
    public void iPrintAllPageDetailsWithSource(String srcFlag) {
        // trPageTitle = getDriver().getTitle();
        // trPageURL = getDriver().getCurrentUrl();
        System.out.println("================================================");
        System.out.println(" URL:   " + trPageURL);
        System.out.println(" Title: " + trPageTitle);
        System.out.println(" Port Mode: " + tePortMode);
        System.out.println(" Browser Size: " + teBrowserWidth + " x " + teBrowserHeight);
        System.out.println(" Handle: " + trWindowHandle);
        if (srcFlag.equalsIgnoreCase("with")) {
            System.out.println("================================================");
            System.out.println("                 Page Source                    ");
            System.out.println("------------------------------------------------");
            System.out.println(trPageSource);
            System.out.println("================================================");
            System.out.println("               End Page Source                  ");
        }
        System.out.println("------------------------------------------------\n");
        if (!tePageTitle.equals("")) {
            assertThat(trPageTitle).containsIgnoringCase(tePageTitle);
        }
    }

    // ---------------------------------------------------------------------------
    //  Navigate to Zip Code Look Up Form on USPS Website
    // ---------------------------------------------------------------------------
    @When("I go to Lookup ZIP page by address")
    public void iGoToLookupZIPPageByAddress() throws InterruptedException {
        // Mail & Ship Navigation Link at top of page
        getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'Ship')]")).click();
        // --------------------------------------------------------------
        // Did he get to shipping page? -- wait for page to appear
        String xpZipCodeLink = "//h2[contains(@class,'center')]//a[contains(text(),'ZIP Code')]";
        Thread.sleep(2000);
        // new WebDriverWait(getDriver(), 10, 200).until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(By.xpath(xpZipCodeLink))));
        trPageURL = getDriver().getCurrentUrl();
        assertThat(trPageURL).containsIgnoringCase("https://www.usps.com/ship/");
        getDriver().findElement(By.xpath(xpZipCodeLink)).click();
        // --------------------------------------------------------------
        // Did he get to Zip Look Up page 1? -- wait until "find by address" link is displayed
        String xpFindByAddressLink = "//a[contains(text(),'Find by Address')]";
        Thread.sleep(2000);
        // new WebDriverWait(getDriver(), 10, 200).until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(By.xpath(xpFindByAddressLink))));
        getDriver().findElement(By.xpath(xpFindByAddressLink)).click();
        // --------------------------------------------------------------
        // Did he get to Zip Look Up page 2? -- wait until "street address" text field is displayed
        String xpStreetAddr = "//input[@id='tAddress']";
        Thread.sleep(2000);
        // new WebDriverWait(getDriver(), 10, 200).until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(By.xpath(xpStreetAddr))));
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
        new Select(getDriver().findElement(By.xpath("//select[@id='tState']"))).selectByVisibleText(stateString);
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

    // ===========================================================================
    //   What Is WebElement State
    // ===========================================================================
    public void whatIsWebElementState(WebElement myElement, String elementLabel) {
        boolean isVisible = myElement.isDisplayed();
        boolean isPresent = myElement.isEnabled();
        // System.out.println("\n" + " ------ DEBUG: " + trPageURL);
        // System.out.println(" ------ DEBUG: Element with label: " + elementLabel);
        // System.out.println(" ------ DEBUG: Enabled:       " + isPresent);
        // System.out.println(" ------ DEBUG: Displayed:       " + isVisible);
        // System.out.println(" ------ DEBUG: Has size:       " + myElement.getSize());

        // Element Not visible to WebDriver -- try to fix

        if (! isVisible) {
            switch (elementLabel) {
                case "Find Button":
                    // Page -- https://tools.usps.com/zip-code-lookup.htm?byaddress
                    // Click on Company above?
                    getDriver().findElement(By.xpath("//input[@id='tCompany']")).click();
                    break;
                default:
                    // when needed, it will appear here
                    break;
            }
        }
    }

    // ---------------------------------------------------------------------------
    //  Validate Zip Code Lookup Results
    // ---------------------------------------------------------------------------
    @Then("I validate {string} zip code exists in the result")
    public void iValidateZipCodeExistsInTheResult(String correctZip) throws InterruptedException {
        Thread.sleep(4000);
        String resultXpath = "//div[@class='zipcode-result-address']/p/strong";
        List<WebElement> elFoundZipList = getDriver().findElements(By.xpath(resultXpath));
        // Unpack list of ZipCodes
        // Need way more code to cross-reference addresses to carrier route and pick correct 9 digit value
        // System.out.println(" ------ DEBUG: Results Count: " + elFoundZipList.size());
        String finalZipFound = "";
        if (elFoundZipList.size() > 1) {
            // No carrier routes greater than 1000 mailboxes???
            String[] foundZip = new String[1000];
            int arrIndex = 0;

            for (WebElement oneZip : elFoundZipList) {
                // System.out.println(" ------ DEBUG: Zip Results("+ arrIndex +"): " + oneZip.getText());
                // Cheating -- just strip carrier route and were al good
                if (oneZip.getText().length() >= 5) {
                    foundZip[arrIndex] = oneZip.getText().substring(0,5);
                }
                arrIndex++;
            }
            finalZipFound = foundZip[0];
        } else {
            WebElement elFoundZip = getDriver().findElement(By.xpath(resultXpath));
            finalZipFound = elFoundZip.getText().substring(0,5);
        }

        // Error path code if bad zip, no result box, unexpected format results??
        System.out.println("------------------------------------------------");
        System.out.println(" Expected Zip Code: " + correctZip);
        System.out.println(" Found Zip Code:    " + finalZipFound);
        System.out.println("------------------------------------------------");
        assertThat(finalZipFound).containsIgnoringCase(correctZip);
    }
}


