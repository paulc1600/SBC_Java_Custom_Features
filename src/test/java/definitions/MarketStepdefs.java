package definitions;

import cucumber.api.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import static definitions.ATestToolBox.*;
import static org.assertj.core.api.Assertions.assertThat;
import static support.TestContext.getDriver;

public class MarketStepdefs  {
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
    // =============================================
    //  Create default test data
    // =============================================
    String tdUsername = "jgoober";
    String tdName = "John P Goober";
    String tdPassword = "foobah123";
    String tdConfirmPassword = "foobah123";
    String tdEmail = "jgoober@technolyth.com";
    String tdAgreeToPrivacy = "True";         // yes I know I don't want boolean!
    String tdAddress = "560 American Way, Good City, CA";
    String tdCountry = "United States of America";
    String tdDateOfBirth = "05/19/1980";
    String tdGender = "male";

    // ===========================================================================
    //  Display Test Environment and Test Data Used to Console
    // ===========================================================================
    @Then("I display test environment set up")
    public void iDisplayTestEnvironmentSetUp() {
        ATestToolBox.toolToDisplayTestEnvironmentSetUp();
        System.out.println("       Test Environment Variables               ");
        System.out.println("================================================");
        iDisplayTestDataVariables1();
    }

    // ===========================================================================
    @And("I display test data variables 1")
    public void iDisplayTestDataVariables1() {
        // Only implemented for quote
        if (!tdUsername.equals("")) System.out.println("tdUsername =        " + tdUsername);             // jgoober
        if (!tdName.equals("")) System.out.println("tdName =            " + tdName);                 // John P Goober
        if (!tdPassword.equals("")) System.out.println("tdPassword =        " + tdPassword);             // foobah123
        if (!tdConfirmPassword.equals("")) System.out.println("tdConfirmPassword = " + tdConfirmPassword);      // foobah123
        if (!tdEmail.equals("")) System.out.println("tdEmail =           " + tdEmail);                // jgoober@technolyth.com
        if (!tdAgreeToPrivacy.equals("")) System.out.println("tdAgreeToPrivacy =  " + tdAgreeToPrivacy);       // True
    }

    // ===========================================================================
    @And("I display test data variables 2")
    public void iDisplayTestDataVariables2() {
        // Only implemented for quote
        System.out.println("tdAddress =         " + tdAddress);              // 560 American Way, Good City, CA
        System.out.println("tdCountry =         " + tdCountry);              // United States of America
        System.out.println("tdDateOfBirth =     " + tdDateOfBirth);          // 05/19/1980
        System.out.println("tdGender =          " + tdGender);               // male
    }

    // ===========================================================================
    @And("I go back and forward, then refresh")
    public void iGoBackAndForwardThenRefresh() {
        System.out.println("\n   Navigate: Back");
        getDriver().navigate().back();
        // Test Results of switching to this page
        trPageTitle = getDriver().getTitle();
        trPageURL = getDriver().getCurrentUrl();
        toolBoxPageDetails();

        System.out.println("\n   Navigate: Forward");
        getDriver().navigate().forward();
        // Test Results of switching to this page
        trPageTitle = getDriver().getTitle();
        trPageURL = getDriver().getCurrentUrl();
        toolBoxPageDetails();

        System.out.println("\n   Navigate: Refresh");
        getDriver().navigate().refresh();
        // Test Results of switching to this page
        trPageTitle = getDriver().getTitle();
        trPageURL = getDriver().getCurrentUrl();
        toolBoxPageDetails();
    }

    // ===========================================================================
    @And("I fill out required fields")
    public void iFillOutRequiredFields() {
        // -----------------------------------------------------------------------------------------
        // tdUsername = "jgoober"                  tdAgreeToPrivacy = "True"
        // tdName = "John P Goober"                tdAddress = "560 American Way, Good City, CA"
        // tdPassword = "foobah123"                tdCountry = "United States of America"
        // tdConfirmPassword = "foobah123"         tdDateOfBirth = "05/19/1980"
        // tdEmail = "jgoober@technolyth.com"      tdGender = "male"
        // -----------------------------------------------------------------------------------------
        if (tdUsername.equalsIgnoreCase("<clear>")) {
            getDriver().findElement(By.xpath("//input[@name='username']")).clear();
        } else if (!(tdUsername.equalsIgnoreCase(""))) {
            getDriver().findElement(By.xpath("//input[@name='username']")).sendKeys(tdUsername);
        }
        tdUsername = "";

        if (tdName.equalsIgnoreCase("<clear>")) {
            getDriver().findElement(By.xpath("//input[@name='name']")).clear();
        } else if (!(tdName.equalsIgnoreCase(""))) {
            getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(tdName);
        }
        tdName = "";

        if (tdPassword.equalsIgnoreCase("<clear>")) {
            getDriver().findElement(By.xpath("//input[@id='password']")).clear();
        } else if (!(tdPassword.equalsIgnoreCase(""))) {
            getDriver().findElement(By.xpath("//input[@name='password']")).sendKeys(tdPassword);
        }
        tdPassword = "";

        if (tdConfirmPassword.equalsIgnoreCase("<clear>")) {
            getDriver().findElement(By.xpath("//input[@id='confirmPassword']")).clear();
        } else if (!(tdConfirmPassword.equalsIgnoreCase(""))) {
            getDriver().findElement(By.xpath("//input[@id='confirmPassword']")).sendKeys(tdConfirmPassword);
        }
        tdConfirmPassword = "";

        // Create and populate email element
        if (tdEmail.equalsIgnoreCase("<clear>")) {
            getDriver().findElement(By.xpath("//input[@name='email']")).clear();
        } else if (!(tdEmail.equalsIgnoreCase(""))) {
            getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys(tdEmail);
        }
        tdEmail = "";

        if (tdAgreeToPrivacy.equalsIgnoreCase("True")) {
            getDriver().findElement(By.xpath("//input[@name='agreedToPrivacyPolicy']")).click();
        } else if (!(tdAgreeToPrivacy.equalsIgnoreCase(""))) {
            getDriver().findElement(By.xpath("//input[@name='agreedToPrivacyPolicy']")).clear();
        }
        tdAgreeToPrivacy = "";
    }

    // ===========================================================================
    @And("I fill out the optional fields")
    public void iFillOutTheOptionalFields() {
        // Show values I'm going to use
        iDisplayTestDataVariables2();
        // -----------------------------------------------------------------------------------------
        // tdAddress = "560 American Way, Good City, CA"
        // tdCountry = "United States of America"
        // tdDateOfBirth = "05/19/1980"
        // tdGender = "male"
        // -----------------------------------------------------------------------------------------
        // I'm cheating here, since I know order in which application displays fields in "phone mode"
        // Without this knowledge, need more fancy code to discover if element is visible or not without
        // scrolling and to discover if it is above current position in viewport or below current position.
        // -----------------------------------------------------------------------------------------
        // Date of Birth
        WebElement elDateOfBirth = getDriver().findElement(By.xpath("//input[@name='dateOfBirth']"));
        // In phone mode, element needs scrolling first
        if (tePortMode.equals("phone") && toolChecksWebElementNotInViewport(elDateOfBirth)) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", elDateOfBirth);
        }
        elDateOfBirth.sendKeys(tdDateOfBirth);

        // -----------------------------------------------------------------------------------------
        // One of several ways to access Country of Origin selector option
        //        The ways not chosen V
        //        click on element with xpath "//select[@name='countryOfOrigin']"
        //        click on element with xpath "//option[contains(text(),'United States of America')]"
        WebElement elCountry = getDriver().findElement(By.xpath("//select[@name='countryOfOrigin']"));
        // In phone mode, element needs scrolling first
        if (tePortMode.equals("phone") && isWebElementNotInViewport(elCountry)) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", elCountry);
        }
        new Select(elCountry).selectByVisibleText(tdCountry);

        // -----------------------------------------------------------------------------------------
        // Gender
        WebElement elMaleRadio = getDriver().findElement(By.xpath("//input[@name='gender'][@value='male']"));
        WebElement elFemaleRadio = getDriver().findElement(By.xpath("//input[@name='gender'][@value='female']"));
        // Only check one radio button, since they are side by side and have same location from top
        // In phone mode, element needs scrolling first
        if (tePortMode.equals("phone") && isWebElementNotInViewport(elMaleRadio)) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", elMaleRadio);
        }
        // Done with optional scrolling, now click one you need to click on
        if (tdGender.equalsIgnoreCase("male")) {
            elMaleRadio.sendKeys(tdDateOfBirth);
        } else {
            elFemaleRadio.sendKeys(tdDateOfBirth);
        }

        // -----------------------------------------------------------------------------------------
        //  Address
        WebElement elPostalAddress = getDriver().findElement(By.xpath("//textarea[@id='address']"));
        // In phone mode, element needs scrolling first
        if (tePortMode.equals("phone") && isWebElementNotInViewport(elPostalAddress)) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", elPostalAddress);
        }
        elPostalAddress.sendKeys(tdAddress);
    }

    // ===========================================================================
    //   Is WebElement beyond visible viewport in this display mode
    // ===========================================================================
    @But("Is web element beyond viewport window")
    public boolean isWebElementNotInViewport(WebElement myElement) {
        boolean notInViewport = false;

        Point elLocation = myElement.getLocation();
        int topPixLocation = elLocation.getY();
        if (topPixLocation >= teBrowserHeight) {
            // Beyond viewport dimension in Phone mode -- Selenium may not be able to interact!
            notInViewport = true;
        }
        return notInViewport;
    }

    // Create and click submit button
    @And("I submit the form")
    public void iSubmitTheForm() {
        WebElement Submit=getDriver().findElement(By.xpath("//button[@id='formSubmit']"));
        Submit.submit();
    }

    // ===========================================================================
    @Then("I verify required fields")
    public void iVerifyRequiredFields() {
        // Verify now at Correct Place and Looks Good
        WebElement pageResult=getDriver().findElement(By.xpath("//div[@id='quotePageResult']"));
        assertThat(pageResult.isDisplayed()).isTrue();
        String actualText1 = getDriver().findElement(By.xpath("//legend[@class='applicationResult']")).getText();
        assertThat(actualText1).containsIgnoringCase("Submitted Application");
        // -----------------------------------------------------------------------------------------
        // tdUsername = "jgoober"                  tdAgreeToPrivacy = "True"
        // tdName = "John P Goober"                tdAddress = "560 American Way, Good City, CA"
        // tdPassword = "foobah123"                tdCountry = "United States of America"
        // tdConfirmPassword = "foobah123"         tdDateOfBirth = "05/19/1980"
        // tdEmail = "jgoober@technolyth.com"      tdGender = "male"
        // -----------------------------------------------------------------------------------------
        // Username
        String actualText2 = getDriver().findElement(By.xpath("//b[@name='username']")).getText();
        assertThat(actualText2).containsIgnoringCase(tdUsername);
        // Email
        String actualText3 = getDriver().findElement(By.xpath("//b[@name='email']")).getText();
        assertThat(actualText3).containsIgnoringCase(tdEmail);
        // Full Name
        String actualText4 = getDriver().findElement(By.xpath("//b[@name='name']")).getText();
        assertThat(actualText4).containsIgnoringCase(tdName);
        // Privacy policy checked -- false checking may not be possible???
        String agreedPrivacy=getDriver().findElement(By.xpath("//b[@name='agreedToPrivacyPolicy']")).getText();
        assertThat(agreedPrivacy).containsIgnoringCase(tdAgreeToPrivacy);
    }

    // ===========================================================================
    @But("I custom set {string} page field {string} to {string}")
    public void iCustomSetPageFieldTo(String page, String pgField, String tdFieldValue) {
        // Only implemented required fields, requires more code for optional fields
        if (page.equalsIgnoreCase("quote")) {
            switch (pgField) {
                case "username":
                    if (tdFieldValue.equalsIgnoreCase("<clear>")) {
                        getDriver().findElement(By.xpath("//input[@name='username']")).clear();
                    } else {
                        getDriver().findElement(By.xpath("//input[@name='username']")).sendKeys(tdFieldValue);
                    }
                    // Value has been processed. Need to reload to set field again
                    System.out.println("-----------------------------------------------");
                    System.out.println("tdUsername =        " + tdFieldValue);
                    tdUsername = "";
                    break;
                case "name":
                    if (tdFieldValue.equalsIgnoreCase("<clear>")) {
                        getDriver().findElement(By.xpath("//input[@name='name']")).clear();
                    } else {
                        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(tdFieldValue);
                    }
                    // Value has been processed. Need to reload to set field again
                    System.out.println("-----------------------------------------------");
                    System.out.println("tdName   =        " + tdFieldValue);
                    tdName = "";
                    break;
                case "password":
                    if (tdFieldValue.equalsIgnoreCase("<clear>")) {
                        getDriver().findElement(By.xpath("//input[@id='password']")).clear();
                    } else {
                        getDriver().findElement(By.xpath("//input[@name='password']")).sendKeys(tdFieldValue);
                    }
                    // Value has been processed. Need to reload to set field again
                    System.out.println("-----------------------------------------------");
                    System.out.println("tdPassword   =      " + tdFieldValue);
                    tdPassword = "";
                    break;
                case "confirm password":
                    if (tdFieldValue.equalsIgnoreCase("<clear>")) {
                        getDriver().findElement(By.xpath("//input[@id='confirmPassword']")).clear();
                    } else {
                        getDriver().findElement(By.xpath("//input[@id='confirmPassword']")).sendKeys(tdFieldValue);
                    }
                    // Value has been processed. Need to reload to set field again
                    System.out.println("-----------------------------------------------");
                    System.out.println("tdConfirmPassword   =  " + tdFieldValue);
                    tdConfirmPassword = "";
                    break;
                case "email":
                    WebElement email=getDriver().findElement(By.xpath("//input[@name='email']"));
                    if (tdFieldValue.equalsIgnoreCase("<clear>")) {
                        getDriver().findElement(By.xpath("//input[@name='email']")).clear();
                    } else {
                        email.sendKeys(tdFieldValue);
                    }
                    // Value has been processed. Need to reload to set field again
                    System.out.println("-----------------------------------------------");
                    System.out.println("tdEmail    =    " + tdFieldValue);
                    tdEmail = "";
                    break;
                case "agree to privacy":
                    if (tdFieldValue.equalsIgnoreCase("<clear>") || tdFieldValue.equalsIgnoreCase("False"))  {
                        getDriver().findElement(By.xpath("//input[@name='agreedToPrivacyPolicy']")).clear();
                    } else {
                        getDriver().findElement(By.xpath("//input[@name='agreedToPrivacyPolicy']")).click();
                    }
                    // Value has been processed. Need to reload to set field again
                    System.out.println("-----------------------------------------------");
                    System.out.println("tdAgreeToPrivacy    =    " + tdFieldValue);
                    tdAgreeToPrivacy = "";
                default:
                    throw new IllegalStateException("Error: field "+ pgField + " not in page " + page + "!");
            }
        } else if (page.equalsIgnoreCase("google")) {
            // Not implemented yet
        } else if (page.equalsIgnoreCase("yahoo")) {
            // Not implemented yet
        } else {
            throw new RuntimeException("Unsupported page: " + page);
        }
    }

    // ===========================================================================
    @But("I clear {string} page field {string}")
    public void iClearPageField(String page, String pgField) {
        if (page.equalsIgnoreCase("quote")) {
            // -----------------------------------------------------------------------------------------
            // tdUsername = "jgoober"                  tdAgreeToPrivacy = "True"
            // tdName = "John P Goober"                tdAddress = "560 American Way, Good City, CA"
            // tdPassword = "foobah123"                tdCountry = "United States of America"
            // tdConfirmPassword = "foobah123"         tdDateOfBirth = "05/19/1980"
            // tdEmail = "jgoober@technolyth.com"      tdGender = "male"
            // -----------------------------------------------------------------------------------------
            switch (pgField) {
                case "username":
                    tdUsername = "<clear>";
                    break;
                case "name":
                    tdName = "<clear>";
                    break;
                case "password":
                    tdPassword = "<clear>";
                    break;
                case "confirm password":
                    tdConfirmPassword = "<clear>";
                    break;
                case "email":
                    tdEmail = "<clear>";
                    break;
                case "agree to privacy":
                    tdAgreeToPrivacy = "False";
                    break;
                default:
                    throw new IllegalStateException("Error: field "+ pgField + " not in page " + page + "!");
            }
        } else if (page.equalsIgnoreCase("google")) {
            // Not implemented yet
        } else if (page.equalsIgnoreCase("yahoo")) {
            // Not implemented yet
        } else {
            throw new RuntimeException("Unsupported page: " + page);
        }
    }

    // ===========================================================================
    @Then("I check for {string} page field {string} error message {string}")
    public void iCheckForPageFieldErrorMessage(String page, String pgField, String errMessage) {
        if (page.equalsIgnoreCase("quote")) {
            switch (pgField) {
                case "username":
                    throw new IllegalStateException("Error: field " + pgField + " error checking not supported!");
                case "name":
                    throw new IllegalStateException("Error: field " + pgField + " error checking not supported!");
                case "password":
                    throw new IllegalStateException("Error: field " + pgField + " error checking not supported!");
                case "confirm password":
                    throw new IllegalStateException("Error: field " + pgField + " error checking not supported!");
                    // break;
                case "email":
                    // //label[@id='email-error'], "This field is required."
                    if (errMessage.equalsIgnoreCase("required")) {
                        errMessage = "field is required";
                        String emailError = getDriver().findElement(By.xpath("//label[@id='email-error']")).getText();
                        assertThat(emailError).containsIgnoringCase(errMessage);
                    } else if (errMessage.equalsIgnoreCase("invalid")) {
                        errMessage = "enter a valid email";
                        String emailError = getDriver().findElement(By.xpath("//label[@id='email-error']")).getText();
                        assertThat(emailError).containsIgnoringCase(errMessage);
                    }
                    break;
                case "agree to privacy":
                    throw new IllegalStateException("Error: field " + pgField + " error checking not supported!");
                    // break;
                default:
                    throw new IllegalStateException("Error: field " + pgField + " not in page " + page + "!");
            }
        } else if (page.equalsIgnoreCase("google")) {
            // Not implemented yet
        } else if (page.equalsIgnoreCase("yahoo")) {
            // Not implemented yet
        } else {
            throw new RuntimeException("Unsupported page: " + page);
        }
    }

    // ===========================================================================
    @When("I type {string} {int} time into {string} field")
    public void iTypeTimeIntoField(String keyValue, int keyCount, String pgField) {
        if (keyValue.equals("BACK_SPACE") || keyValue.equals("DELETE")) {
            if (keyCount > 0 ) {
                for (Integer nk = 1; nk <= keyCount; nk++) {
                    switch (pgField) {
                        case "username":
                            getDriver().findElement(By.xpath("//input[@name='username']")).sendKeys(keyValue);
                            break;
                        case "name":
                            getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(keyValue);
                            break;
                        case "password":
                            getDriver().findElement(By.xpath("//input[@name='password']")).sendKeys(keyValue);
                            break;
                        case "confirm password":
                            getDriver().findElement(By.xpath("//input[@id='confirmPassword']")).sendKeys(keyValue);
                            break;
                        case "email":
                            getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys(keyValue);
                            break;
                        default:
                            throw new IllegalStateException("Error: field "+ pgField + " not supported with custom keys!");
                    }
                }
            }
        } else {
            throw new IllegalStateException("Error: key value "+ keyValue + " not supported with custom keys!");
        }
    }

    // ===========================================================================
    @And("I create and handle 3rd party alert")
    public void iCreateAndHandleRdPartyAlert() {
        getDriver().findElement(By.xpath("//button[@id='thirdPartyButton']")).click();
        // Go away Mr. Alert
        getDriver().switchTo().alert().accept();
        // Try going back to first window
        getDriver().switchTo().window(getDriver().getWindowHandles().iterator().next());
    }

    // ===========================================================================
    @And("I set all test data variables to their defaults")
    public void iSetAllTestDataVariablesToTheirDefaults() {
        // ---- Test Environment Defaults
        tePortMode = "default";
        teBrowserWidth = 1440;
        teBrowserHeight = 900;
        // ---- Test Data Defaults
        tdUsername = "jgoober";
        tdName = "John P Goober";
        tdPassword = "foobah123";
        tdConfirmPassword = "foobah123";
        tdEmail = "jgoober@technolyth.com";
        tdAgreeToPrivacy = "True";         // yes I know I don't want boolean!
        tdAddress = "560 American Way, Good City, CA";
        tdCountry = "United States of America";
        tdDateOfBirth = "05/19/1980";
        tdGender = "male";
    }
}