package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebElement;
import Pages.QuoteForm;
import Pages.QuoteResults;

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
    public void iFillOutRequiredFieldsForOop(String role) throws InterruptedException {
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
        assertThat(resultsPage.readQrAgreedToPrivacyPolicy().equalsIgnoreCase("true"));
    }

    @When("I fill out optional fields for {string} oop")
    public void iFillOutOptionalFieldsForOop(String userProfile) {
        formPage.fillDateOfBirth(quoteData.get("dateOfBirth"));
        formPage.fillPhoneNumber(quoteData.get("phoneNbr"));
        formPage.selectCountryOfOrigin(quoteData.get("country"));
        formPage.fillAddress(quoteData.get("address"));
        formPage.selectCarMake(quoteData.get("carMake"));
        formPage.selectGender(quoteData.get("gender"));
        formPage.selectAllowedToContact();
    }

    @Then("I verify optional fields for {string} oop")
    public void iVerifyOptionalFieldsForOop(String userProfile) {
        assertThat(resultsPage.readQrPhone()).containsIgnoringCase(quoteData.get("phoneNbr"));
        assertThat(resultsPage.readQrDOB()).containsIgnoringCase(quoteData.get("dateOfBirth"));
        assertThat(resultsPage.readQrAddress()).containsIgnoringCase(quoteData.get("address"));
        assertThat(resultsPage.readQrCountry()).containsIgnoringCase(quoteData.get("country"));
        assertThat(resultsPage.readQrAllowedToContact().equalsIgnoreCase("true"));
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
            case "username" -> assertThat(formPage.readErrorFields("username", "visible")).isEqualTo(expectedErrorMsg);
            case "email" -> assertThat(formPage.readErrorFields("email", "visible")).isEqualTo(expectedErrorMsg);
            case "password" -> assertThat(formPage.readErrorFields("password", "visible")).isEqualTo(expectedErrorMsg);
            case "confirmPassword" -> assertThat(formPage.readErrorFields("confirmPassword", "visible")).isEqualTo(expectedErrorMsg);
            case "name" -> assertThat(formPage.readErrorFields("name", "visible")).isEqualTo(expectedErrorMsg);
            case "agreedToPrivacyPolicy" -> assertThat(formPage.readErrorFields("agreedToPrivacyPolicy", "visible")).isEqualTo(expectedErrorMsg);
            default -> throw new IllegalStateException("Error: This form error condition is invalid: " + errorCondition);
        }
    }

    @When("I fill out {string} field with {string}")
    public void iFillOutFieldWith(String fieldName, String fieldValue) {
        switch (fieldName) {
            case "username" -> formPage.fillUsername(fieldValue);
            case "email" -> formPage.fillEmail(fieldValue);
            case "password" -> formPage.fillPassword(fieldValue);
            case "confirmPassword" -> formPage.fillConfirmPassword(fieldValue);
            case "name" -> formPage.fillName(fieldValue, fieldValue);
            default -> throw new IllegalStateException("Error: This form field is invalid: " + fieldName);
        }
    }

    @Then("I don't see {string} error message")
    public void iDonTSeeErrorMessage(String fieldName) {
        switch (fieldName) {
            case "username" -> assertThat(formPage.readErrorFields("username", "invisible")).isEqualTo("");
            case "email" -> assertThat(formPage.readErrorFields("email", "invisible")).isEqualTo("");
            case "password" -> assertThat(formPage.readErrorFields("password", "invisible")).isEqualTo("");
            case "confirmPassword" -> assertThat(formPage.readErrorFields("confirmPassword", "invisible")).isEqualTo("");
            case "name" -> assertThat(formPage.readErrorFields("name", "invisible")).isEqualTo("");
            case "agreedToPrivacyPolicy" -> assertThat(formPage.readErrorFields("agreedToPrivacyPolicy", "invisible")).isEqualTo("");
            default -> throw new IllegalStateException("Error: This form field name is invalid: " + fieldName);
        }
    }

    @When("I fill out name field with first name {string} and last name {string}")
    public void iFillOutNameFieldWithFirstNameAndLastName(String firstNameValue, String lastNameValue) {
        formPage.fillName(firstNameValue, lastNameValue);
    }

    @Then("I verify {string} field value {string}")
    public void iVerifyFieldValue(String fieldName, String expectedValue) {
        WebElement elementToVerify = formPage.returnThisElement(fieldName);
        if (fieldName.equalsIgnoreCase("username") ||
            fieldName.equalsIgnoreCase("name") ||
            fieldName.equalsIgnoreCase("email")) {
            String uiFieldValue = "";
            uiFieldValue = elementToVerify.getAttribute("value");
//            System.out.println("------------------------------");
//            System.out.println(" iVerifyFieldValue() uiFieldValue =  " + uiFieldValue);
//            System.out.println(" iVerifyFieldValue() expectedValue = " + expectedValue);
//            System.out.println("------------------------------");
            uiFieldValue.equalsIgnoreCase(expectedValue);
        } else {
            throw new IllegalStateException("Error: This field name is not implemented: " + fieldName);
        }
    }

    @When("I fill out name field with first name {string}, middle name {string}, last name {string}")
    public void iFillOutNameFieldWithFirstNameMiddleNameLastName(String fName, String mName, String lName) {
        formPage.fillName(fName, mName, lName);
    }
}
