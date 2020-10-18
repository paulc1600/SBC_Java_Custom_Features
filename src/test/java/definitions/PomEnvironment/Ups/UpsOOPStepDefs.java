package definitions.PomEnvironment.Ups;

import PomEnvironment.Page;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import PomEnvironment.Ups.UpsHomePage;
import PomEnvironment.Ups.UpsShipForm;

import static org.junit.Assert.assertTrue;

public class UpsOOPStepDefs {
    Page myPage = new Page();
    UpsHomePage upsHomePage = new UpsHomePage();
    UpsShipForm upsShipForm = new UpsShipForm();

    @Given("I open {string} page POM")
    public void iOpenPagePOM(String arg0) {
        myPage.open();
    }

    @And("I open Shipping menu POM")
    public void iOpenShippingMenuPOM() {
        upsHomePage.upsShipping();
    }

    @And("I go to Create a Shipment POM")
    public void iGoToCreateAShipmentPOM() {
        upsHomePage.goCreateAShipment();
    }

    @When("I fill out origin shipment fields POM")
    public void iFillOutOriginShipmentFieldsPOM() {
        upsShipForm.fillOriginName("John Goober");
        upsShipForm.fillOriginContactName("Administrator");
        upsShipForm.fillOriginAddress1("2345 Happy Ave");
        upsShipForm.fillOriginPostalCode("95148");
        upsShipForm.fillOriginCity("San Jose");
        upsShipForm.selectOriginState("CA");
        upsShipForm.fillOriginEmail("johngoober@nonet.net");
        upsShipForm.fillOriginPhone("4087771212");
    }

    @And("I submit the shipment form POM")
    public void iSubmitTheShipmentFormPOM() {
        upsShipForm.handleCookiePolicy();
        upsShipForm.submitOriginForm();
    }

    @Then("I verify origin shipment fields submitted POM")
    public void iVerifyOriginShipmentFieldsSubmittedPOM() {
        assertTrue(upsShipForm.getOriginName().equalsIgnoreCase("John Goober"));
        assertTrue(upsShipForm.getOriginContactName().equalsIgnoreCase("Administrator"));
        assertTrue(upsShipForm.getOriginAddressLine1().equalsIgnoreCase("2345 Happy Ave"));
        assertTrue(upsShipForm.getOriginCity().equalsIgnoreCase("San Jose"));
        assertTrue(upsShipForm.getOriginState().equalsIgnoreCase("CA"));
        assertTrue(upsShipForm.getOriginPostalCode().equalsIgnoreCase("95148"));
        assertTrue(upsShipForm.getOriginEmail().equalsIgnoreCase("johngoober@nonet.net"));
        assertTrue(upsShipForm.getOriginPhone().equalsIgnoreCase("4087771212"));
    }

    @And("I cancel the shipment form POM")
    public void iCancelTheShipmentFormPOM() {
        upsShipForm.handleCookiePolicy();
        upsShipForm.cancelOriginForm();
    }

    @Then("I verify shipment form is reset POM")
    public void iVerifyShipmentFormIsResetPOM() {
        // Handle Modal Warning -- from Cancel
        upsShipForm.handleModalWarning();
        // Check all empty again
        assertTrue(upsShipForm.chkOriginName().equalsIgnoreCase(""));
        assertTrue(upsShipForm.chkOriginContactName().equalsIgnoreCase(""));
        assertTrue(upsShipForm.chkOriginAddress1().equalsIgnoreCase(""));
        assertTrue(upsShipForm.chkOriginPostalCode().equalsIgnoreCase(""));
        assertTrue(upsShipForm.chkOriginCity().equalsIgnoreCase(""));
        assertTrue(upsShipForm.chkOriginEmail().equalsIgnoreCase(""));
        assertTrue(upsShipForm.chkOriginPhone().equalsIgnoreCase(""));
    }
}
