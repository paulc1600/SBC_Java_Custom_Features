package definitions.Careers;

import API.RestClient;
import API.RestTestEnvironment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;
import java.util.Map;

import static API.RestTestEnvironment.*;
import static org.assertj.core.api.Assertions.assertThat;
import static support.TestContext.getTimestamp;

public class RestStepDefs {
    RestClient RC = new RestClient();

    @Given("I open rest environment for {string}")
    public void iOpenRestEnvironmentFor(String environmentName) {
        RestTestEnvironment.EnvironmentSetUp(environmentName);
    }

    // --------------------------------------------------------------
    //  Read and Prepare a Position Record -- Careers API Envr
    // --------------------------------------------------------------
    public Map<String, String> getPosition(String filename) {
        Map<String, String> position = getStrData(filename);
        String title = position.get("title");
        position.put("title", title + getTimestamp());
        return position;
    }

    // --------------------------------------------------------------
    //  Read and Prepare a Candidate Record -- Careers API Envr
    // --------------------------------------------------------------
    public Map<String, String> getCandidate(String filename) {
        Map<String, String> candidate = getStrData(filename);
        String email = candidate.get("email");
        String[] emailComp = email.split("@");
        candidate.put("email", emailComp[0] + getTimestamp() + "@" + emailComp[1]);
        return candidate;
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
        Map<String, Object> result = RC.createPosition(getPosition(type));
        setTestData("newPosition", result);
        setTestData("newPositionID", result.get("id"));
    }

    // --------------------------------------------------------------
    //  GET https://skryabin.com/recruit/api/v1/positions
    // --------------------------------------------------------------
    @Then("I verify via REST new position is in the list")
    public void iVerifyViaRESTNewPositionIsInTheList() {
        int posID = getTestDataInteger("newPositionID");
        Map <String, String> expectedPosition = getPosition("automation");
        List<Map<String, Object>> result = RC.getPositionList();

        // Check all positions in Response Body List -- for position just created
        boolean isInList = false;
        String listedPosID = "";
        for (Map<String, Object> positionOBJ : result) {
            listedPosID = String.valueOf(positionOBJ.get("id"));
            System.out.println("List Position = " + listedPosID + " compare " + posID);
            if (listedPosID.equals(""+posID)) {
                isInList = true;

                // New code to check out all the other fields
                for (String key : expectedPosition.keySet()) {
                    // Solve later .....
                    if (!key.equalsIgnoreCase("dateOpen")) {
                        assertThat(positionOBJ.get(key)).isEqualTo(expectedPosition.get(key));
                    }
                }

                break;
            }
        }

        System.out.println("=========================================");
        System.out.println(" Target Record ID: " + posID);
        System.out.println(" Found Record ID:  " + listedPosID);
        System.out.println(" Was in list?      " + isInList);
        System.out.println("=========================================");
        assertThat(isInList).isTrue();
    }

    // --------------------------------------------------------------
    @When("I update via REST {string} position")
    public void iUpdateViaRESTPosition(String providedPosition) {
        // Create and Store Update Fields
        String expectedAddress = "258 Very Modified St";
        String expectedCity = "Modified Village";
        // Needed for compare to actual record get request
        setTestData("updatedAddress", expectedAddress);
        setTestData("updatedCity", expectedCity);

        // Prepare Position Test Data with Modifications
        Map<String, String> position = getStrData(providedPosition);
        position.put("address", expectedAddress);
        position.put("city", expectedCity);

        // Get Result of Update Action
        Map<String, Object> result = RC.updatePosition(position);
        String actualAddress = result.get("address").toString();
        String actualCity = result.get("city").toString();

        // Verify Result of Update Action
        assertThat(actualAddress.equalsIgnoreCase(expectedAddress));
        assertThat(actualCity.equalsIgnoreCase(expectedCity));
    }

    // --------------------------------------------------------------
    @Then("I verify via REST new position is updated")
    public void iVerifyViaRESTNewPositionIsUpdated() {
        // DEPENDENCY: must be set into TestData object by RestClient.createPosition()
        int posID = getTestDataInteger("newPositionID");
        Map<String, Object> result = RC.verifyPositionUpdates(posID);
        String actualAddress = result.get("address").toString();
        String actualCity = result.get("city").toString();

        // Can verify that response itself has updated fields
        assertThat(actualAddress.equalsIgnoreCase(getTestDataString("updatedAddress")));
        assertThat(actualCity.equalsIgnoreCase(getTestDataString("updatedCity")));
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
        List<Map<String, Object>> result = RC.getPositionList();

        // Check all positions in Response Body List -- ensure position just deleted is gone
        boolean isInList = false;
        String listedPosID = "";

        for (Map<String, Object> positionOBJ : result) {
            listedPosID = String.valueOf(positionOBJ.get("id"));
            System.out.println("List Position = " + listedPosID + " compare " + posID);
            if (listedPosID.equals(""+posID)) {
                isInList = true;
                break;
            }
        }

        System.out.println("=========================================");
        System.out.println(" Target Record ID: " + posID);
        System.out.println(" Was in list?      " + isInList);
        System.out.println("=========================================");
        assertThat(isInList).isFalse();

        // Clean up for next time
        setTestData("newPositionID", "");
    }
}
