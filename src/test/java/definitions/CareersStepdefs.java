package definitions;

import PomEnvironment.Careers.Pages.*;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Map;

import static PomEnvironment.PomTestEnvironment.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CareersStepdefs {

    CareersHome home = new CareersHome();
    CareersUserHome userHome = new CareersUserHome();
    CareersLogin userLogin = new CareersLogin();
    CareersRecruiterHome recruiterHome = new CareersRecruiterHome();
    CareersRecruit recruit = new CareersRecruit();
    CareersNewPosition newPosition = new CareersNewPosition();

    Map<String, String> savedNewPosition;

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

    // -----------------------------------------------------------------------------------
    //    When   When I create new position                               -- @careers3
    // -----------------------------------------------------------------------------------
    @When("I create new {string} position")
    public void iCreateNewPosition(String filename) {
        recruiterHome.goToRecruitPage();
        recruiterHome.startNewPosition();
        savedNewPosition = newPosition.createNewPosition(filename);
    }

    // -----------------------------------------------------------------------------------
    //    Then   I verify new {string} position is created or removed      -- @careers3
    // -----------------------------------------------------------------------------------
    @Then("I verify new {string} position is {string}")
    public void iVerifyNewPositionIsCreated(String positionFile, String positionAction) {
        String posTitle = savedNewPosition.get("title");
        System.out.println("--- Looking for position: " + posTitle);
        // Force refresh of Job Card List
        recruiterHome.returnToHomePage();
        recruiterHome.returnPageLabel("Careers");
        recruiterHome.goToRecruitPage();
        recruiterHome.returnPageLabel("Recruit");
        boolean isVisible = recruit.isPositionVisible(posTitle);
        if (positionAction.equalsIgnoreCase("created")) {
            assertThat(isVisible).isTrue();
        } else if (positionAction.equalsIgnoreCase("removed")) {
            assertThat(isVisible).isFalse();
        }
    }

    // -----------------------------------------------------------------------------------
    //    When   I remove new {string} position                           -- @careers3
    // -----------------------------------------------------------------------------------
    @When("I remove new {string} position")
    public void iRemoveNewPosition(String positionFile) {
        String posTitle = savedNewPosition.get("title");
        recruit.removePosition(posTitle);
    }

    @Given("I get {string} test data from source {string} {string}")
    public void iGetTestDataFromSource(String page, String fileName, String tdSource) {
        teDataSource = tdSource;
        if (page.equalsIgnoreCase("quote")) {
            switch (teDataSource) {
                case "default":
                    // Variables are already set to defaults when step file called
                    break;
                case "file":
                    if (fileName.equalsIgnoreCase("") ||
                            fileName.equalsIgnoreCase("No File")) {
                        // If not specified in Gherkin Scenario -- use this yml file
                        tdFileName = "user";
                    } else {
                        // Gherkin Scenario explicitly needs different yml file
                        tdFileName = fileName;
                    }
                    tdFileName = "user";
                    System.out.println("================================================");
                    System.out.println(" Test Data Source is: " + teDataSource);
                    System.out.println(" Active File: " + tdFileName + " on quoteData()");
                    System.out.println("------------------------------------------------");
                    quoteData = getPomStrData(tdFileName);
                    break;
                default:
                    throw new IllegalStateException("Error: This test data source is invalid: " + teDataSource);
            }
        } else if (page.equalsIgnoreCase("careers")) {
            switch (teDataSource) {
                case "default":
                    // Variables are already set to defaults when step file called
                    break;
                case "file":
                    // Gherkin Scenario explicitly needs different yml file
                    tdFileName = fileName;
                    // Override defaults from test data file -- uses tdFileName = "recruiter", or  etc.
                    careersData = getPomStrData(tdFileName);
                    System.out.println("================================================");
                    System.out.println(" Test Data Source is: " + teDataSource);
                    System.out.println(" Active File: " + tdFileName + " on careersData()");
                    System.out.println("------------------------------------------------");
                    break;
                default:
                    throw new IllegalStateException("Error: This test data source is invalid: " + tdSource);
            }
        } else if (page.equalsIgnoreCase("usps")) {
            switch (teDataSource) {
                case "default":
                    // Variables are already set to defaults when step file called
                    break;
                case "file":
                    // Override defaults from test data file (not implemented)
                    break;
                default:
                    throw new IllegalStateException("Error: This test data source is invalid: " + teDataSource);
            }
        } else if (page.equalsIgnoreCase("ups")) {
            switch (teDataSource) {
                case "default":
                    // Variables are already set to defaults when step file called
                    break;
                case "file":
                    // Override defaults from test data file -- uses tdFileName = "dataUPS";
                    upsData = getPomData(tdFileName);
                    stateData = getPomStrData("dataStateNames");
                    System.out.println("================================================");
                    System.out.println(" Test Data Source is: " + teDataSource);
                    System.out.println(" Active File: " + tdFileName + " on upsData()");
                    System.out.println(" Active File: " + "dataStateNames" + " on stateData()");
                    System.out.println("------------------------------------------------");
                    break;
                default:
                    throw new IllegalStateException("Error: This test data source is invalid: " + teDataSource);
            }
        } else {
            throw new RuntimeException("Unsupported page: " + page);
        }
    }
}
