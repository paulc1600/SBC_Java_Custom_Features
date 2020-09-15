package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.QuoteForm;
import pages.QuoteResults;

import static definitions.ATestToolBox.quoteData;
import static org.assertj.core.api.Assertions.assertThat;

public class QuoteOOPSteps {
    QuoteForm formPage = new QuoteForm();
    QuoteResults resultsPage = new QuoteResults();

    @Given("I open {string} page")
    public void iOpenPage(String page) throws InterruptedException {
        formPage.open();
    }

    @When("I fill out required fields for {string} oop")
    public void iFillOutRequiredFieldsForOop(String role) {
        formPage.fillUsername(quoteData.get("username"));
        formPage.fillName(quoteData.get("firstName"), quoteData.get("lastName"));
        formPage.fillBothPasswords(quoteData.get("password"));
        formPage.fillEmail(quoteData.get("email"));
        formPage.agreeWithPrivacyPolicy();
    }

    @And("I submit the form oop")
    public void iSubmitTheFormOop() {
        formPage.submit();
    }

    @Then("I verify required fields for {string} oop")
    public void iVerifyRequiredFieldsForOop(String userProfile) {
        assertThat(resultsPage.readQrUsername()).containsIgnoringCase(quoteData.get("username"));
        assertThat(resultsPage.readQrEmail()).containsIgnoringCase(quoteData.get("email"));
        assertThat(resultsPage.readQrFirstName()).containsIgnoringCase(quoteData.get("firstName"));
        assertThat(resultsPage.readQrLastName()).containsIgnoringCase(quoteData.get("lastName"));
    }

    @When("I fill out optional fields for {string} oop")
    public void iFillOutOptionalFieldsForOop(String userProfile) {
        formPage.fillDateOfBirth(quoteData.get("dateOfBirth"));
        formPage.fillPhoneNumber(quoteData.get("phoneNbr"));
        formPage.selectCountryOfOrigin(quoteData.get("country"));
        formPage.fillAddress(quoteData.get("address"));
        formPage.selectCarMake(quoteData.get("carMake"));
        formPage.selectGender(quoteData.get("gender"));
    }

    @Then("I verify optional fields for {string} oop")
    public void iVerifyOptionalFieldsForOop(String userProfile) {
        assertThat(resultsPage.readQrPhone()).containsIgnoringCase(quoteData.get("phoneNbr"));
        assertThat(resultsPage.readQrDOB()).containsIgnoringCase(quoteData.get("dateOfBirth"));
        assertThat(resultsPage.readQrAddress()).containsIgnoringCase(quoteData.get("address"));
        assertThat(resultsPage.readQrCountry()).containsIgnoringCase(quoteData.get("country"));
    }

    // -------------------------------------------------------------------------------
    //    Then I see "username" error message "This field is required."
    //    And I see "email" error message "This field is required."
    //    And I see "password" error message "This field is required."
    //    And I see "name" error message "This field is required."
    //    And I see "agreedToPrivacyPolicy" error message "- Must check!"
    // -------------------------------------------------------------------------------
    @Then("I see {string} error message {string}")
    public void iSeeErrorMessage(String errorCondition, String expectedErrorMsg) {
        switch (errorCondition) {
            case "username" -> assertThat(formPage.readErrorUserName()).containsIgnoringCase(expectedErrorMsg);
            case "email" -> assertThat(formPage.readErrorEmail()).containsIgnoringCase(expectedErrorMsg);
            case "password" -> assertThat(formPage.readErrorPassword()).containsIgnoringCase(expectedErrorMsg);
            case "name" -> assertThat(formPage.readErrorName()).containsIgnoringCase(expectedErrorMsg);
            case "agreedToPrivacyPolicy" -> assertThat(formPage.readErrorAgreedToPrivacyPolicy()).containsIgnoringCase(expectedErrorMsg);
            default -> throw new IllegalStateException("Error: This form error condition is invalid: " + errorCondition);
        }
    }

    @When("I fill out {string} field with {string}")
    public void iFillOutFieldWith(String fieldName, String fieldValue) {
        formPage.refresh();
        switch (fieldName) {
            case "username" -> formPage.fillUsername(fieldValue);
            case "email" -> formPage.fillEmail(fieldValue);
            case "password" -> formPage.fillBothPasswords(fieldValue);
            case "name" -> formPage.fillName(fieldValue, fieldValue);
            default -> throw new IllegalStateException("Error: This form field is invalid: " + fieldName);
        }
    }

    @Then("I don't see {string} error message")
    public void iDonTSeeErrorMessage(String fieldName) {
        switch (fieldName) {
            case "username" -> assertThat(formPage.readErrorUserName()).isEqualTo("");
            case "email" -> assertThat(formPage.readErrorEmail()).isEqualTo("");
            case "password" -> assertThat(formPage.readErrorPassword()).isEqualTo("");
            case "name" -> assertThat(formPage.readErrorName()).isEqualTo("");
            case "agreedToPrivacyPolicy" -> assertThat(formPage.readErrorAgreedToPrivacyPolicy()).isEqualTo("");
            default -> throw new IllegalStateException("Error: This form field name is invalid: " + fieldName);
        }
    }
}
