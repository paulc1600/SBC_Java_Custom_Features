package definitions;

import Pages.*;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class CareersStepdefs {
    Page myPage = new Page();
    CareersHome home = new CareersHome();
    CareersList jobsList = new CareersList();
    CareersUserHome userHome = new CareersUserHome();
    CareersLogin userLogin = new CareersLogin();
    CareersRecruiterHome recruiterHome = new CareersRecruiterHome();
    CareersRecruit recruit = new CareersRecruit();

    @And("I login as {string}")
    public void iLoginAs(String userProvided) {
        String[] userCredentialsArray = userHome.getCareersUserData(userProvided);   // returns email, pwd, full name
        String unameValue = userCredentialsArray[0];
        String upassword = userCredentialsArray[1];

        userHome.goToLoginPage();
        userLogin.fillInLoginForm(unameValue, upassword);
        userLogin.submitLoginForm();
    }

    @Then("I verify {string} login")
    public void iVerifyLogin(String userType) {
        String[] userCredentialsArray = userHome.getCareersUserData(userType);  // returns email, pwd, full name
        String expectedName = userCredentialsArray[2];

        String actualName = recruiterHome.verifyLoginName();
        assertThat(actualName.equalsIgnoreCase(expectedName)).isTrue();
    }

    // -----------------------------------------------------------
    //     When   I display my jobs list            -- @careers2
    // -----------------------------------------------------------
    @When("I display my jobs list")
    public void iDisplayMyJobsList() {
        userHome.displayMyJobsList();
    }

    @Then("I verify job {string} is there")
    public void iVerifyJobIsThere(String providedTitle) {
        boolean isVisible = userHome.isJobPositionVisible(providedTitle);
        assertThat(isVisible).isTrue();
    }

    @When("I withdraw my application for {string}")
    public void iWithdrawMyApplicationFor(String providedTitle) {
        String actualTitle = userHome.accessMyJobDetails(providedTitle);
        assertThat(actualTitle.equalsIgnoreCase(providedTitle)).isTrue();
        userHome.withdrawApplication();
    }

    @Then("I verify job {string} is not there")
    public void iVerifyJobIsNotThere(String providedTitle) {
        boolean isGone = userHome.isJobPositionGone(providedTitle);
        assertThat(isGone).isTrue();
    }

    // -------------------------------------------------------------------------------
    //     When I remove "Principal Automation Engineer" position      -- @careers1
    // -------------------------------------------------------------------------------
    @When("I remove {string} position")
    public void iRemovePosition(String providedTitle) {
        recruiterHome.goToRecruitPage();
        recruit.removePosition(providedTitle);
    }

    // -----------------------------------------------------------------------------------
    //     And I verify "Principal Automation Engineer" position is removed  -- @careers1
    // -----------------------------------------------------------------------------------
    @And("I verify {string} position is removed")
    public void iVerifyPositionIsRemoved(String providedTitle) {
        recruit.refresh();
        boolean isVisible = recruit.isPositionVisible(providedTitle);
        boolean errorsPresent = recruit.areErrorsPresent();
        assertThat(errorsPresent).isFalse();
        assertThat(isVisible).isFalse();
    }
}
