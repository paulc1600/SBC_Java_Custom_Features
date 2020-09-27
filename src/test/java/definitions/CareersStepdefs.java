package definitions;

import Pages.*;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

public class CareersStepdefs {
    Page myPage = new Page();
    CareersHome home = new CareersHome();
    CareersList jobsList = new CareersList();
    CareersUserHome userHome = new CareersUserHome();
    CareersLogin userLogin = new CareersLogin();
    CareersRecruiterHome recruiterHome = new CareersRecruiterHome();

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
    public void iVerifyLogin() {
        String expectedName = "Owen Kelley";
        String actualName = recruiterHome.verifyLoginName();
        assertThat(actualName.equalsIgnoreCase(expectedName)).isTrue();
    }
}
