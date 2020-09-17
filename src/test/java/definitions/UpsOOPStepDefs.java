package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.UpsHomePage;
import pages.UpsShipForm;
import cucumber.api.java.en.Given;

public class UpsOOPStepDefs {
    UpsHomePage upsHomePage = new UpsHomePage();
    UpsShipForm upsShipForm = new UpsShipForm();

    @Given("I open {string} page POM")
    public void iOpenPagePOM(String arg0) {
        upsHomePage.open();
    }

    @And("I open Shipping menu POM")
    public void iOpenShippingMenuPOM() {

    }

    @And("I go to Create a Shipment POM")
    public void iGoToCreateAShipmentPOM() {
    }

    @When("I fill out origin shipment fields POM")
    public void iFillOutOriginShipmentFieldsPOM() {
    }

    @And("I submit the shipment form POM")
    public void iSubmitTheShipmentFormPOM() {
    }

    @Then("I verify origin shipment fields submitted POM")
    public void iVerifyOriginShipmentFieldsSubmittedPOM() {
    }

    @And("I cancel the shipment form POM")
    public void iCancelTheShipmentFormPOM() {
    }

    @Then("I verify shipment form is reset POM")
    public void iVerifyShipmentFormIsResetPOM() {
    }
}
