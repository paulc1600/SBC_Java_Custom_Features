package definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import API.RestClient;

import static definitions.ATestToolBox.*;
import static definitions.ATestToolBox.tePageURL;
import static support.TestContext.getTestDataInteger;
import static support.TestContext.setTestData;

public class RestStepDefs {
    RestClient RC = new RestClient();

    @Given("I open rest environment for {string}")
    public void iOpenRestEnvironmentFor(String url) {
        String title = "";
        switch (url) {
            case "careers":
                tePageURL = "https://skryabin-careers.herokuapp.com/";
                tePageTitle = "Careers Portal";
                break;
            case "google":
                tePageURL = "https://www.google.com/";
                tePageTitle = "Google";
                break;
            default:
                tePageURL = url;
        }
        url = tePageURL;
        title = tePageTitle;
        System.out.println("\n   Navigate: Open URL");
        System.out.println("================================================");
        System.out.println(" URL:   " + tePageURL);
        System.out.println(" Title: " + tePageTitle);
        System.out.println("================================================");
    }

    // --------------------------------------------------------------
    //  POST https://skryabin.com/recruit/api/v1/login
    // --------------------------------------------------------------
    @Given("I login via REST as {string}")
    public void iLoginViaRESTAs(String role) {
        RC.login(getStrData(role));
    }

    // --------------------------------------------------------------
    //  POST https://skryabin.com/recruit/api/v1/positions
    // --------------------------------------------------------------
    @When("I create via REST {string} position")
    public void iCreateViaRESTPosition(String type) {
        RC.createPosition(getStrData(type));
    }

    // --------------------------------------------------------------
    //  GET https://skryabin.com/recruit/api/v1/positions
    // --------------------------------------------------------------
    @Then("I verify via REST new position is in the list")
    public void iVerifyViaRESTNewPositionIsInTheList() {
        int posID = getTestDataInteger("newPositionID");
        RC.getPositionList(posID, true);
    }

    // --------------------------------------------------------------
    @When("I update via REST {string} position")
    public void iUpdateViaRESTPosition(String providedPosition) {
        RC.updatePosition(getStrData(providedPosition));
    }

    // --------------------------------------------------------------
    @Then("I verify via REST new position is updated")
    public void iVerifyViaRESTNewPositionIsUpdated() {
        // DEPENDENCY: must be set into TestData object by RestClient.createPosition()
        int posID = getTestDataInteger("newPositionID");
        RC.verifyPositionUpdates(posID);
    }

    // --------------------------------------------------------------
    @When("I delete via REST new position")
    public void iDeleteViaRESTNewPosition() {
        // DEPENDENCY: must be set into TestData object by RestClient.createPosition()
        int posID = getTestDataInteger("newPositionID");
        RC.deletePositionRecord(posID);
    }

    // --------------------------------------------------------------
    @Then("I verity via REST new position is deleted")
    public void iVerityViaRESTNewPositionIsDeleted() {
        int posID = getTestDataInteger("newPositionID");
        RC.getPositionList(posID, false);
        // Clean up for next time
        setTestData("newPositionID", "");
    }
}
