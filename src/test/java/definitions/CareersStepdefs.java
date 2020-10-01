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
        String unameValue = "";
        String upassword = "";
        if (userProvided.equalsIgnoreCase("recruiter")) {
            unameValue = "owen@example.com";
            upassword = "welcome";
            userHome.goToLoginPage();
            userLogin.fillInLoginForm(unameValue, upassword);
            userLogin.submitLoginForm();
        } else {
            throw new RuntimeException("Unsupported user profile: " + userProvided);
        }
    }

    @Then("I verify {string} login")
    public void iVerifyLogin(String userType) {
        String expectedName = "";
        if (userType.equalsIgnoreCase("recruiter")) {
            expectedName = "Owen Kelley";
        }
        String actualName = recruiterHome.verifyLoginName();
        assertThat(actualName.equalsIgnoreCase(expectedName)).isTrue();
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
