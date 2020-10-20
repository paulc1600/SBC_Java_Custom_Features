package PomEnvironment.USPS.definitions;

import PomEnvironment.Page;
import PomEnvironment.USPS.Pages.*;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class UspsOOPStepdefs {
    Page myPage = new Page();
    UspsHeader uspsHeader = new UspsHeader();
    UspsHome uspsHome = new UspsHome();
    UspsLookupByZip uspsLookupByZip = new UspsLookupByZip();
    UspsByAddressForm uspsByAddressForm = new UspsByAddressForm();
    UspsByAddressResult uspsByAddressResult = new UspsByAddressResult();
    UspsCalculator uspsCalculator = new UspsCalculator();

    // ------------------------------------------------------------
    // @usps6                      Scenario: Validate ZIP code oop
    // ------------------------------------------------------------
    @When("I go to Lookup ZIP page by address oop")
    public void iGoToLookupZIPPageByAddressOop() {
        uspsHome.goToLookupByZip();
        uspsLookupByZip.clickFindByAddress();
    }

    // ------------------------------------------------------------
    // @usps6                      Scenario: Validate ZIP code oop
    // ------------------------------------------------------------
    @And("I fill out {string} street, {string} city, {string} state oop")
    public void iFillOutStreetCityStateOop(String street, String city, String state) {
        uspsByAddressForm.fillStreet(street);
        uspsByAddressForm.fillCity(city);
        uspsByAddressForm.selectState(state);
        uspsByAddressForm.clickFind();
    }

    // ------------------------------------------------------------
    // @usps6                      Scenario: Validate ZIP code oop
    // ------------------------------------------------------------
    @Then("I validate {string} zip code exists in the result oop")
    public void iValidateZipCodeExistsInTheResultOop(String zip) {
        String actualTotalResult = uspsByAddressResult.getSearchResult();
        assertThat(actualTotalResult).contains(zip);

        boolean areAllItemsContainZip = uspsByAddressResult.areAllResultsContainZip(zip);
        assertThat(areAllItemsContainZip).isTrue();
    }

    // ----------------------------------------------------------------------
    // @usps7                      Scenario: USPS Calculate price with POM
    // ----------------------------------------------------------------------
    @When("I go to Calculate Price Page oop")
    public void iGoToCalculatePricePageOop() {
        uspsHeader.goToCalculatePrice();
    }

    // ----------------------------------------------------------------------
    // @usps7                      Scenario: USPS Calculate price with POM
    // ----------------------------------------------------------------------
    @And("I select {string} with {string} shape oop")
    public void iSelectWithShapeOop(String providedCountry, String providedType) {
        uspsCalculator.selectCountry(providedCountry);
        uspsCalculator.selectMailType(providedType);
    }

    // ----------------------------------------------------------------------
    // @usps7                      Scenario: USPS Calculate price with POM
    // ----------------------------------------------------------------------
    @And("I define {string} quantity oop")
    public void iDefineQuantityOop(String providedQuantity) {
        uspsCalculator.inputQuantity(providedQuantity);
        uspsCalculator.goCalculatePostage();
    }

    // ----------------------------------------------------------------------
    // @usps7                      Scenario: USPS Calculate price with POM
    // ----------------------------------------------------------------------
    @Then("I calculate the price and validate cost is {string} oop")
    public void iCalculateThePriceAndValidateCostIsOop(String providedTotal) {
        uspsCalculator.checkPostageTotal(providedTotal);
    }
}
