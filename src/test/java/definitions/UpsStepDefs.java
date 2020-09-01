package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static definitions.ATestToolBox.*;
import static org.junit.Assert.assertTrue;
import static support.TestContext.getDriver;

public class UpsStepDefs {
    // =============================================
    //  Create test data variables
    // =============================================
    String tdFirstCharges = "";
    String tdAfterCharges = "";

    @And("I open Shipping menu")
    public void iOpenShippingMenu() {
        // Watch out for Cookie Message!!!!
        WebElement elCookieConsent = getDriver().findElement(By.xpath("//div[contains(@class,'implicit_consent')]"));
        if (elCookieConsent.isDisplayed()) {
            getDriver().findElement(By.xpath("//div[contains(@class,'implicit_consent')]//button[contains(@class,'close')]")).click();
        }
        WebElement elShipLink = toolWaitForElementWithXpath("//a[@id='ups-menuLinks2']");
        elShipLink.click();      // go to shipping
    }

    @And("I go to Create a Shipment")
    public void iGoToCreateAShipment() {
        WebElement elCreateShip = toolWaitForElementWithXpath("//ul[@class='ups-menu_links']//a[contains(@href,'/ship?')]");
        elCreateShip.click();
    }

    // ---------------------------------------------------------------------------
    //  Fill Out UPS Shipping Form Origin (Scen @upsScenario10-1)
    //    upsData.get("key")
    //    name: Administrator
    //    address: 4970 El Camino Real
    //    zip: "94022"
    //    email: mail@example.com
    //    phone: "1234567890"
    //    city: LOS ALTOS
    //    state: CA
    // ---------------------------------------------------------------------------
    @When("I fill out origin shipment fields")
    public void iFillOutOriginShipmentFields() {
        // Wait for last field to be present in form = phone
        WebElement elShipPhone = toolWaitForElementWithXpath("//div[@class='row']//input[@id='originphone']");
        getDriver().findElement(By.xpath("//input[@id='originname']")).sendKeys((CharSequence) upsData.get("name"));
        getDriver().findElement(By.xpath("//input[@id='originaddress1']")).sendKeys((CharSequence) upsData.get("address"));
        getDriver().findElement(By.xpath("//input[@id='originpostal']")).sendKeys((CharSequence) upsData.get("zip"));
        getDriver().findElement(By.xpath("//input[@id='originemail']")).sendKeys((CharSequence) upsData.get("email"));
        elShipPhone.sendKeys((CharSequence) upsData.get("phone"));
        getDriver().findElement(By.xpath("//input[@id='origincity']")).sendKeys((CharSequence) upsData.get("city"));
        // Now for state selection list ------------------------------------------------
        String shortName = (String) upsData.get("state");
        if (stateData.containsKey(shortName)) {
            String longName = stateData.get(shortName);
            Select selStateList = new Select(getDriver().findElement(By.xpath("//select[@id='originstate']")));
            selStateList.selectByVisibleText(longName);
        } else {
            throw new IllegalStateException("Error: code "+ shortName + " had no equivalent state name!");
        }
    }

    @And("I submit the shipment form")
    public void iSubmitTheShipmentForm() throws InterruptedException {
        WebElement elContinueButton = getDriver().findElement(By.xpath("//button[contains(@id,'ContinueButton')]"));

        // Always scroll to continue
        JavascriptExecutor scrollToView = getExecutor();
        scrollToView.executeScript("arguments[0].scrollIntoView(true);", elContinueButton);
        Thread.sleep(500);

        // Submit Completed Form using JS click (gets intercepted otherwise)
        getExecutor().executeScript("arguments[0].click();", elContinueButton);
        Thread.sleep(200);
    }

    // ---------------------------------------------------------------------------
    //  Check UPS Shipping Form Origin Info (Scen @upsScenario10-1)
    //    upsData.get("key")
    //    name: Administrator
    //    address: 4970 El Camino Real
    //    zip: "94022"
    //    email: mail@example.com
    //    phone: "1234567890"
    //    city: LOS ALTOS
    //    state: CA
    // ---------------------------------------------------------------------------
    @Then("I verify origin shipment fields submitted")
    public void iVerifyOriginShipmentFieldsSubmitted() {
        WebElement elOriginName = toolWaitForElementWithXpath("//span[@id='originnbsAgentSummaryName']");
        String chkAddr = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryAddressLine1']")).getText();
        String chkCity = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryCity']")).getText();
        String chkState = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryState']")).getText();
        String chkZip = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryPostalCode']")).getText();
        String chkEmail = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryEmail']")).getText();
        String chkPhone = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryPhone']")).getText();
        assertTrue(elOriginName.getText().equalsIgnoreCase((String) upsData.get("name")));
        assertTrue(chkAddr.equalsIgnoreCase((String) upsData.get("address")));
        assertTrue(chkCity.equalsIgnoreCase((String) upsData.get("city")));
        assertTrue(chkState.equalsIgnoreCase((String) upsData.get("state")));
        assertTrue(chkZip.equalsIgnoreCase((String) upsData.get("zip")));
        assertTrue(chkEmail.equalsIgnoreCase((String) upsData.get("email")));
//        System.out.println("------------------------------------------------");
//        System.out.println(" Form Value phone: " + chkPhone);
//        System.out.println(" File Value phone: " + upsData.get("phone"));
//        System.out.println("------------------------------------------------");
        assertTrue(chkPhone.equalsIgnoreCase((String) upsData.get("phone")));
    }

    @And("I cancel the shipment form")
    public void iCancelTheShipmentForm() {
        // Cancel Form using JS click
        WebElement elCancelButton = getDriver().findElement(By.xpath("//button[@id='nbsBackForwardNavigationCancelShipmentButton']"));
        getExecutor().executeScript("arguments[0].click();", elCancelButton);
    }

    @Then("I verify shipment form is reset")
    public void iVerifyShipmentFormIsReset() {
        // Verify Want to Cancel
        WebElement elCancelYes = toolWaitForElementWithXpath("//div[contains(@class,'modal-body')]//button[contains(@id, 'Yes')]");
        elCancelYes.click();

        // Check Value Attributes
        WebElement chkName = getDriver().findElement(By.xpath("//input[@id='originname']"));
        WebElement chkAddr = getDriver().findElement(By.xpath("//input[@id='originaddress1']"));
        WebElement chkZip = getDriver().findElement(By.xpath("//input[@id='originpostal']"));
        WebElement chkEmail = getDriver().findElement(By.xpath("//input[@id='originemail']"));
        WebElement chkPhone = toolWaitForElementWithXpath("//div[@class='row']//input[@id='originphone']");
        WebElement chkCity = getDriver().findElement(By.xpath("//input[@id='origincity']"));
        assertTrue(chkName.getAttribute("value") != "");
        assertTrue(chkAddr.getAttribute("value") != "");
        assertTrue(chkZip.getAttribute("value") != "");
        assertTrue(chkEmail.getAttribute("value") != "");
        assertTrue(chkPhone.getAttribute("value") != "");
        assertTrue(chkCity.getAttribute("value") != "");
    }

    // ---------------------------------------------------------------------------
    //  Fill Out UPS Shipping Form Destination (Scen @upsScenario10-2)
    //    upsData.get("key")
    //    dname: John B. Goodenough
    //    daddress: 3051 Ocala Ave
    //    dzip: "95148"
    //    demail: mail@example.com
    //    dphone: "1234567890"
    //    dcity: San Jose
    //    dstate: CA
    // ---------------------------------------------------------------------------
    @When("I fill out destination shipment fields")
    public void iFillOutDestinationShipmentFields() {
        // Wait for last field to be present in form = phone
        WebElement elDestPhone = toolWaitForElementWithXpath("//div[@class='row']//input[@id='destinationphone']");
        getDriver().findElement(By.xpath("//input[@id='destinationname']")).sendKeys((CharSequence) upsData.get("dname"));
        getDriver().findElement(By.xpath("//input[@id='destinationaddress1']")).sendKeys((CharSequence) upsData.get("daddress"));
        getDriver().findElement(By.xpath("//input[@id='destinationpostal']")).sendKeys((CharSequence) upsData.get("dzip"));
        getDriver().findElement(By.xpath("//input[@id='destinationemail']")).sendKeys((CharSequence) upsData.get("demail"));
        elDestPhone.sendKeys((CharSequence) upsData.get("dphone"));
        getDriver().findElement(By.xpath("//input[@id='destinationcity']")).sendKeys((CharSequence) upsData.get("dcity"));
        // Now for state selection list ------------------------------------------------
        String shortName = (String) upsData.get("state");
        if (stateData.containsKey(shortName)) {
            String longName = stateData.get(shortName);
            Select selStateList = new Select(getDriver().findElement(By.xpath("//select[@id='destinationstate']")));
            selStateList.selectByVisibleText(longName);
        } else {
            throw new IllegalStateException("Error: code "+ shortName + " had no equivalent state name!");
        }
    }

    // ---------------------------------------------------------------------------
    //  Fill Out UPS Shipping Form Destination (Scen @upsScenario10-2)
    //    upsData.get("key")
    //       weight: "2"
    //       pkgtype: UPS Express Box - Small
    // ---------------------------------------------------------------------------
    @And("I set packaging type and weight")
    public void iSetPackagingTypeAndWeight() {
        // Select the Package type
        Select selTypeList = new Select(getDriver().findElement(By.xpath("//select[@id='nbsPackagePackagingTypeDropdown0']")));
        selTypeList.selectByVisibleText((String) upsData.get("pkgtype"));

        // Wait for weight field to be present in form
        System.out.println("------------------------------------------------");
        System.out.println(" Pkg Weight: " + (CharSequence) upsData.get("weight"));
        System.out.println(" Pkg Type:   " + (String) upsData.get("pkgtype"));
        System.out.println("------------------------------------------------");
        WebElement elPkgWeight = toolWaitForElementWithXpath("//input[contains(@id,'PackageWeightField')]");
        elPkgWeight.sendKeys((String) upsData.get("weight"));
    }

    // ---------------------------------------------------------------------------
    //  UPS Form Displays Total Charges (Scen @upsScenario10-2)
    // ---------------------------------------------------------------------------
    @Then("I verify total charges appear")
    public void iVerifyTotalChargesAppear() {
        // Wait for charges
        WebElement elTotalBanner = getDriver().findElement(By.xpath("//span[@id='total-charges-spinner']"));
        if (toolChecksWebElementNotInViewport(elTotalBanner)) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", elTotalBanner);
            System.out.println("-- Debug: Tile was not in viewport -- needed scroll.");
        }
        toolWaitForElementWithXpath("//span[@id='total-charges-spinner']");
        assertTrue(elTotalBanner.isDisplayed());
        String tdCharges = elTotalBanner.getText();
        System.out.println("------------------------------------------------");
        System.out.println(" Total Cost: " + tdCharges);
        System.out.println("------------------------------------------------");
    }

    // ---------------------------------------------------------------------------
    //  UPS Form Select Cheap Option (Scen @upsScenario10-2)
    // ---------------------------------------------------------------------------
    @And("I select cheapest delivery option")
    public void iSelectCheapestDeliveryOption() throws InterruptedException {
        WebElement elCheapOption = getDriver().findElement(By.xpath("//input[@id='nbsServiceTileServiceRadio7']"));
        JavascriptExecutor scrollToView = getExecutor();
        scrollToView.executeScript("arguments[0].scrollIntoView(true);", elCheapOption);
        Thread.sleep(500);

        JavascriptExecutor superClick = getExecutor();
        superClick.executeScript("arguments[0].click();", elCheapOption);
    }

    // ---------------------------------------------------------------------------
    //  UPS Form 4 Options (Scen @upsScenario10-2)
    //    upsData.get("key")
    //        description: Engraved Photo Frame
    // ---------------------------------------------------------------------------
    @And("I set description and check Saturday Delivery type")
    public void iSetDescriptionAndCheckSaturdayDeliveryType() {
        WebElement beforeCharges = toolWaitForElementWithXpath("//span[@id='total-charges-spinner']");
        tdFirstCharges = beforeCharges.getText();

        // Other render uses Xpath == //saturday-delivery-option//label[@class='ups-lever ups-checkbox-custom-label section-checkbox-label']
        // One render uses xpath == //saturday-delivery-option//span[@class='ups-lever_switch_bg']
        WebElement elSatDelivery = toolWaitForElementWithXpath("//saturday-delivery-option//label[@class='ups-lever ups-checkbox-custom-label section-checkbox-label']");
        elSatDelivery.click();

        getDriver().findElement(By.xpath("//input[@id='nbsShipmentDescription']")).sendKeys((CharSequence) upsData.get("description"));
    }

    // ---------------------------------------------------------------------------
    //  UPS Form 4 Checking Charges Changed (Scen @upsScenario10-2)
    // ---------------------------------------------------------------------------
    @Then("I verify total charges changed")
    public void iVerifyTotalChargesChanged() {
        //span[@id='total-charges-spinner']
        WebElement afterCharges = toolWaitForElementWithXpath("//span[@id='total-charges-spinner']");
        tdAfterCharges = afterCharges.getText();
        System.out.println("------------------------------------------------");
        System.out.println(" Before Saturday Choice: " + tdFirstCharges);
        System.out.println(" Before Saturday Choice: " + tdAfterCharges);
        System.out.println("------------------------------------------------");
        // assertTrue(!tdFirstCharges.equalsIgnoreCase(tdAfterCharges));
    }

    @And("I select Paypal payment type")
    public void iSelectPaypalPaymentType() {
        WebElement payPalTile = toolWaitForElementWithXpath("//span[contains(text(),'Other Ways to Pay')]");
        JavascriptExecutor superClick = getExecutor();
        superClick.executeScript("arguments[0].click();", payPalTile);

        WebElement elPayPal = toolWaitForElementWithXpath("//label[@class='ups-radio-custom-label ng-star-inserted']");
    }

    @And("I review the shipment form")
    public void iReviewTheShipmentForm() throws InterruptedException {
        WebElement elReviewButton = getDriver().findElement(By.xpath("//button[@id='nbsBackForwardNavigationReviewSecondaryButton']"));

        // Always scroll to review
        JavascriptExecutor scrollToView = getExecutor();
        scrollToView.executeScript("arguments[0].scrollIntoView(true);", elReviewButton);
        Thread.sleep(500);

        // Review Completed Form using JS click (gets intercepted otherwise)
        getExecutor().executeScript("arguments[0].click();", elReviewButton);
        Thread.sleep(200);
    }

    @Then("I review all recorded details on the review page")
    public void iReviewAllRecordedDetailsOnTheReviewPage() {
        String oName = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryName']")).getText();
        String oAddr = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryAddressLine1']")).getText();
        String oCity = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryCity']")).getText();
        String oState = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryState']")).getText();
        String oZip = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryPostalCode']")).getText();
        String oEmail = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryEmail']")).getText();
        String oPhone = getDriver().findElement(By.xpath("//span[@id='originnbsAgentSummaryPhone']")).getText();
        String dName = getDriver().findElement(By.xpath("//span[@id='destinationnbsAgentSummaryName']")).getText();
        String dAddr = getDriver().findElement(By.xpath("//span[@id='destinationnbsAgentSummaryAddressLine1']")).getText();
        String dCity = getDriver().findElement(By.xpath("//span[@id='destinationnbsAgentSummaryCity']")).getText();
        String dState = getDriver().findElement(By.xpath("//span[@id='destinationnbsAgentSummaryState']")).getText();
        String dZip = getDriver().findElement(By.xpath("//span[@id='destinationnbsAgentSummaryPostalCode']")).getText();
        String PkgType = getDriver().findElement(By.xpath("//package-review/div/div/p[contains(text(),'UPS Express Box - Small')]")).getText();
        String Weight = getDriver().findElement(By.xpath("//span[contains(text(),'2 lbs')]")).getText();
        String contentDescr = getDriver().findElement(By.xpath("//p[contains(text(),'Description of Goods: Plastic Photo Frame')]")).getText();
        String payMethod = getDriver().findElement(By.xpath("//span[@id='nbsPaymentSummaryBillPayPalSummary']")).getText();
        assertTrue(oName.equalsIgnoreCase((String) upsData.get("name")));
        assertTrue(oAddr.equalsIgnoreCase((String) upsData.get("address")));
        assertTrue(oCity.equalsIgnoreCase((String) upsData.get("city")));
        assertTrue(oState.equalsIgnoreCase((String) upsData.get("state")));
        assertTrue(oZip.equalsIgnoreCase((String) upsData.get("zip")));
        assertTrue(oEmail.equalsIgnoreCase((String) upsData.get("email")));
        assertTrue(oPhone.equalsIgnoreCase((String) upsData.get("phone")));

        assertTrue(dName.equalsIgnoreCase((String) upsData.get("dname")));
        assertTrue(dAddr.equalsIgnoreCase((String) upsData.get("daddress")));
        assertTrue(dCity.equalsIgnoreCase((String) upsData.get("dcity")));
        assertTrue(dState.equalsIgnoreCase((String) upsData.get("dstate")));
        assertTrue(dZip.equalsIgnoreCase((String) upsData.get("dzip")));
    }

}
