package definitions.PomEnvironment.Usps;

import PomEnvironment.*;
import PomEnvironment.USPS.*;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static definitions.GuiTestEnvironment.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UspsOOPStepdefs {
    Page myPage = new Page();
    UspsHeader uspsHeader = new UspsHeader();
    UspsHome uspsHome = new UspsHome();
    UspsLookupByZip uspsLookupByZip = new UspsLookupByZip();
    UspsByAddressForm uspsByAddressForm = new UspsByAddressForm();
    UspsByAddressResult uspsByAddressResult = new UspsByAddressResult();
    UspsCalculator uspsCalculator = new UspsCalculator();

    // ----------------------------------------------------------------------
    // @usps6                      Scenario: Validate ZIP code oop
    // @usps7                      Scenario: USPS Calculate price with POM
    // ----------------------------------------------------------------------
    @Given("I open {string} page oop")
    public void iOpenPageOop(String url) {
        switch (url) {
            case "careers":
                tePageURL = "https://skryabin-careers.herokuapp.com/";
                tePageTitle = "Careers Portal";
                break;
            case "quote":
                tePageURL = "https://skryabin.com/market/quote.html";
                tePageTitle = "Quote";
                tdFileName = "user";
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
                tePageURL = url;
        }
        myPage.open();
    }

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
