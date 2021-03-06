package definitions;

import cucumber.api.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static support.TestContext.*;

/*
 -------------------------------------------------------------------------------------------------------------
    definitions.GuiTestEnvironment  input                 returns               description

    getActions                    --                Actions               new Action constructor
    getExecutor                   --                Javascript Executor   new JS executor returned
    getWait                   integer               WebDriverWait         explicit wait in seconds provided
    getWait                       --                WebDriverWait         explicit wait of 5 sec



 -------------------------------------------------------------------------------------------------------------
*/

public class GuiTestEnvironment {
    // =============================================
    //  Create test environment variables
    // =============================================
    public static String tePortMode = "default";
    public static Integer teBrowserWidth = 1440;
    public static Integer teBrowserHeight = 900;
    public static String tePageURL = "";
    public static String tePageTitle = "";
    public static String teDataSource = "";
    public static String trPageURL = "";
    public static String trPageTitle = "";
    public static String trWindowHandle = "";
    public static String trPageSource = "";
    public static String tdFileName = "";
    public static Map<String, String> careersData = null;
    public static Map<String, String> quoteData = null;
    public static Map<String, Object> upsData = null;
    public static Map<String, String> stateData = null;

    public static int EXPTIMEOUT = 5;

    // ***************************************************************************
    //   getWait()  ===>  new wait constructor = default 5 sec
    // ***************************************************************************
    public static WebDriverWait getWait() {
        return getWait(5);
    }

    // ***************************************************************************
    //   getWait(int sec)  ===>  new wait constructor
    // ***************************************************************************
    public static WebDriverWait getWait(int timeout) {
        return new WebDriverWait(getDriver(), timeout);
    }

    // ***************************************************************************
    //   getActions()  ===>  new Action constructor
    // ***************************************************************************
    public static Actions getActions() {
        return new Actions(getDriver());
    }

    // ***************************************************************************
    //   getExecutor()  ===>  Javascript Executor
    // ***************************************************************************
    public static JavascriptExecutor getExecutor() {
        return (JavascriptExecutor) getDriver();
    }

    // ===========================================================================
    //   Test Environment -- Page Selector
    //    (can be called from Gherkin)
    // ===========================================================================
    @Given("Tool that goes to the {string} page")
    public void iGoToThePage(String page) {
        switch (page) {
            case "careers":
                tePageURL = "https://skryabin-careers.herokuapp.com/";
                tePageTitle = "Careers Portal";
                break;
            case "quote":
                tePageURL = "https://skryabin.com/market/quote.html";
                tePageTitle = "Quote";
                tdFileName = "user";
                break;
            case "usps":
                tePageURL = "https://www.usps.com/";
                tePageTitle = "USPS";
                break;
            case "ups":
                tePageURL = " https://www.ups.com/us/en/Home.page";
                tePageTitle = "Global Shipping & Logistics Services | UPS - United States";
                tdFileName = "dataUPS";
                break;
            case "unit converter":
                tePageURL = "https://www.unitconverters.net/";
                tePageTitle = "Unit Converter";
                break;
            case "calculator":
                tePageURL = "https://www.calculator.net/";
                tePageTitle = "Calculator.net: Free Online Calculators - Math, Fitness, Finance, Science";
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
    //   Test Environment -- Test Data Source Provider
    //    (can be called from Gherkin)
    // ===========================================================================
    @Given("Tool to get {string} test data from source {string} {string}")
    public static void toolToManageTestData(String page, String fileName, String tdSource) {
        teDataSource = tdSource;
        if (page.equalsIgnoreCase("quote")) {
            switch (teDataSource) {
                case "default":
                    // Variables are already set to defaults when step file called
                    break;
                case "file":
                    if (fileName.equalsIgnoreCase("") ||
                        fileName.equalsIgnoreCase("No File")) {
                        // If not specified in Gherkin Scenario -- use this yml file
                        tdFileName = "user";
                    } else {
                        // Gherkin Scenario explicitly needs different yml file
                        tdFileName = fileName;
                    }
                    tdFileName = "user";
                    System.out.println("================================================");
                    System.out.println(" Test Data Source is: " + teDataSource);
                    System.out.println(" Active File: " + tdFileName + " on quoteData()");
                    System.out.println("------------------------------------------------");
                    quoteData = getGuiStrData(tdFileName);
                    break;
                default:
                    throw new IllegalStateException("Error: This test data source is invalid: " + teDataSource);
            }
        } else if (page.equalsIgnoreCase("careers")) {
            switch (teDataSource) {
                case "default":
                    // Variables are already set to defaults when step file called
                    break;
                case "file":
                    // Gherkin Scenario explicitly needs different yml file
                    tdFileName = fileName;
                    // Override defaults from test data file -- uses tdFileName = "recruiter", or  etc.
                    System.out.println("================================================");
                    System.out.println(" Test Data Source is: " + teDataSource);
                    System.out.println(" Base Directory:     " + teDataDirectory);
                    System.out.println(" Active File:        " + tdFileName + " on careersData()");
                    System.out.println("------------------------------------------------");
                    careersData = getGuiStrData(tdFileName);
                    break;
                default:
                    throw new IllegalStateException("Error: This test data source is invalid: " + tdSource);
            }
        } else if (page.equalsIgnoreCase("usps")) {
            switch (teDataSource) {
                case "default":
                    // Variables are already set to defaults when step file called
                    break;
                case "file":
                    // Override defaults from test data file (not implemented)
                    break;
                default:
                    throw new IllegalStateException("Error: This test data source is invalid: " + teDataSource);
            }
        } else if (page.equalsIgnoreCase("ups")) {
            switch (teDataSource) {
                case "default":
                    // Variables are already set to defaults when step file called
                    break;
                case "file":
                    // Override defaults from test data file -- uses tdFileName = "dataUPS";
                    upsData = getGuiData(tdFileName);
                    stateData = getGuiStrData("dataStateNames");
                    System.out.println("================================================");
                    System.out.println(" Test Data Source is: " + teDataSource);
                    System.out.println(" Active File: " + tdFileName + " on upsData()");
                    System.out.println(" Active File: " + "dataStateNames" + " on stateData()");
                    System.out.println("------------------------------------------------");
                    break;
                default:
                    throw new IllegalStateException("Error: This test data source is invalid: " + teDataSource);
            }
        } else {
            throw new RuntimeException("Unsupported page: " + page);
        }
    }

    // ===========================================================================
    //   Test Environment -- Change Browser Resolution
    //     Parameters: "phone", "desktop", "default" = Acer Desktop screen
    //    (can be called from Gherkin)
    // ===========================================================================
    @When("Tool to change resolution to {string}")
    public static void toolToChangeResolutionTo(String browserSize) {
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
                // Use default values
                tePortMode = "default";
                teBrowserWidth = 1440;
                teBrowserHeight = 900;
                break;
        }

        if (browserSize.equalsIgnoreCase("phone") ||
                browserSize.equalsIgnoreCase("desktop")) {
            // Gherkin wants to change size, so browser changes to the set dimension
            Dimension newBrowserSize = new Dimension(teBrowserWidth, teBrowserHeight);
            getDriver().manage().window().setPosition(new Point(0, 0));
            getDriver().manage().window().setSize(newBrowserSize);
        }
    }

    // ===========================================================================
    //   Test Environment -- monitor
    //     Sysout: trPageURL, trPageTitle
    //    (can be called from Gherkin)
    // ===========================================================================
    @And("Tool to print page details")
    public static void toolBoxPageDetails() {
        System.out.println("================================================");
        System.out.println(" URL:   " + trPageURL);
        System.out.println(" Title: " + trPageTitle);
        System.out.println("================================================");
    }

    // ===========================================================================
    //   Test Environment -- monitor
    //     Sysout: browser state and dimensions
    //    (can be called from Gherkin)
    // ===========================================================================
    @And("Tool to print browser size")
    public static void toolToPrintBrowserSize() {
        System.out.println(" Port Mode: " + tePortMode);
        System.out.println(" Browser Size: " + teBrowserWidth + " x " + teBrowserHeight);
        System.out.println("================================================");
    }

    // ===========================================================================
    //  Tool: Display Test Environment to Console
    // ===========================================================================
    @Then("Tool to display test environment set up")
    public static void toolToDisplayTestEnvironmentSetUp() {
        toolBoxPageDetails();
        toolToPrintBrowserSize();
    }

    // ===========================================================================
    //  Tool: Display Page Details to Console
    // ===========================================================================
    @And("Tool to print all page details {string} source")
    public void toolToPrintAllPageDetailsWithSource(String srcFlag) {
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

    // ===========================================================================
    //   Tool: Is WebElement beyond visible viewport in this display mode
    //      Parameters: webElement = detect position of it
    //      Output:     boolean = in or out of viewport
    //          (can be called from Gherkin)
    // ===========================================================================
    @But("Tool to check if web element beyond viewport window")
    public static boolean toolChecksWebElementNotInViewport(WebElement myElement) {
        boolean notInViewport = false;

        Point elLocation = myElement.getLocation();
        int topPixLocation = elLocation.getY();
        if (topPixLocation >= teBrowserHeight) {
            // Beyond viewport dimension in Phone mode -- Selenium may not be able to interact!
            notInViewport = true;
        }
        return notInViewport;
    }

    // ===========================================================================
    //   Tool: handle JS alert box
    //      Parameters: (1) Xpath to click and generate alert
    //                  (2) "dismiss" or "accept"
    //          (can be called from Gherkin)
    // ===========================================================================
    @And("Tool to handle 3rd party alert at {string} with a {string}")
    public static void toolHandles3RdPartyAlert(String alertXpath, String alertAction) {
        getDriver().findElement(xpath(alertXpath)).click();
        // Go away Mr. Alert
        if (alertAction.equalsIgnoreCase("accept")) {
            getDriver().switchTo().alert().accept();
        } else if (alertAction.equalsIgnoreCase("dismiss")) {
            getDriver().switchTo().alert().dismiss();
        }
        // Try going back to first window
        getDriver().switchTo().window(getDriver().getWindowHandles().iterator().next());
    }

    // ===========================================================================
    //   Tool: Gets WebElement state
    //      Parameters: webElement, a nice label
    //          (called from Gherkin)
    // ===========================================================================
    @And("Tool gets element state for {string} with label {string}")
    public static void ToolGetsWebElementState(WebElement myElement, String elementLabel) {
        boolean isVisible = myElement.isDisplayed();
        boolean isPresent = myElement.isEnabled();
        System.out.println("\n" + " ------ DEBUG: " + trPageURL);
        System.out.println(" ------ DEBUG: Element with label: " + elementLabel);
        System.out.println(" ------ DEBUG: Enabled:       " + myElement.isEnabled());
        System.out.println(" ------ DEBUG: Displayed:       " + myElement.isDisplayed());
        System.out.println(" ------ DEBUG: Has size:       " + myElement.getSize());
    }

    // -------------------------------------------------------------------------------------------
    //   Tool: Wait for WebElement After X seconds
    //      Parameters: (1) xPath of nice element you want
    //                  (2) waitType     = visible, clickable, selected, allfull
    //                  (3) waitProvided = 0 (no explicit wait / just find element using project
    //                                        implicit wait in Hooks)
    //                      waitProvided > 0 && <  300 (literal number seconds up to 5 minutes)
    //                      waitProvided >= 300 (use framework default = EXPTIMEOUT)
    //      Output:     WebElement after appears (for single element)
    //                  null                     (for type allfull -- NOT RETURN LIST ... yet)
    //          (called from Gherkin)
    // -------------------------------------------------------------------------------------------
    @And("Tool wait for element with Xpath {string} to appear after {int} secs")
    public static WebElement toolWaitForXpath(String thisXpathProvided, String waitType, int waitProvided) {
        WebElement elementWanted;
        if (waitProvided != 0) {
            // Use one of two possible explicit waits (Gherkin or Framework constant)
            if (waitProvided >= 300) { waitProvided = EXPTIMEOUT; }

            WebDriverWait waitToAppear = getWait(waitProvided);
            By byThisXpath = By.xpath(thisXpathProvided);

            switch (waitType) {
                case "visible":
                    elementWanted = waitToAppear.until(visibilityOfElementLocated(byThisXpath));
                    break;
                case "clickable":
                    elementWanted = waitToAppear.until(elementToBeClickable(byThisXpath));
                    break;
                case "selected":
                    waitToAppear.until(elementToBeSelected(byThisXpath));
                    elementWanted = null;
                    break;
                case "allfull":
                    waitToAppear.until(presenceOfAllElementsLocatedBy(byThisXpath));
                    elementWanted = null;
                    break;
                default:
                    elementWanted = waitToAppear.until(visibilityOfElementLocated(byThisXpath));
            }
            return elementWanted;
        } else {
            // Use framework implicit wait only
            elementWanted = getDriver().findElement(By.xpath(thisXpathProvided));
            return elementWanted;
        }
    }

    // ---------------------------------------------------------------------------
    //   Tool: Wait for WebElement
    //      Parameters: xPath of nice element you want
    //      Output:     WebElement after appears
    //          (called from Gherkin)
    // ---------------------------------------------------------------------------
    @And("Tool wait for element with Xpath {string} to appear")
    public static WebElement toolWaitForElementWithXpath(String thisXpathProvided) {
        WebDriverWait waitToAppear = getWait(10);
        By byThisXpath = By.xpath(thisXpathProvided);
        WebElement elementNowVisible = waitToAppear.until(visibilityOfElementLocated(byThisXpath));
        return elementNowVisible;
    }

    // ---------------------------------------------------------------------------
    //   Tool getGuiData: Get key value pairs from resources/data yml file
    //      Parameters: name of yml file = dataUPS.yml
    //      Output:     yml stream
    //       ====> use getGuiData()  when need to read strings or ints from yml
    //          (called from Gherkin)
    // ---------------------------------------------------------------------------
    public static Map<String, Object> getGuiData(String fileName) {
        String myDataDir = teDataDirectory;
        String myFilename = fileName;
        String myExtension = "yml";
        return new Yaml().load(getStream(myDataDir, myFilename, myExtension));
    }

    // ---------------------------------------------------------------------------
    //   Tool getGuiStrData: Get key value pairs from resources/data yml file
    //      Parameters: name of yml file = dataUPS.yml
    //      Output:     yml stream
    //       ====> use getGuiStrData()  when know you only read strings from yml
    //          (called from Gherkin)
    // ---------------------------------------------------------------------------
    public static Map<String, String> getGuiStrData(String fileName) {
        String myDataDir = teDataDirectory;
        String myFilename = fileName;
        String myExtension = "yml";
        return new Yaml().load(getStream(myDataDir, myFilename, myExtension));
    }
}
