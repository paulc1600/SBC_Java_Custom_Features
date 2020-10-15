package API.definitions;

import API.RestClient;
import API.RestTestContext;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;
import java.util.Map;

import static API.RestTestContext.*;
import static org.assertj.core.api.Assertions.assertThat;
import static API.RestTestContext.getTimestamp;

public class RestStepDefs {
    RestClient RC = new RestClient();

    @Given("I open rest environment for {string}")
    public void iOpenRestEnvironmentFor(String environmentName) {
        RestTestContext.EnvironmentSetUp(environmentName);
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

    // ---------------------------------------------------------------------------
    //  Get Test Data Record ID for Newly created record -- Careers API Envr
    // ---------------------------------------------------------------------------
    public int getNewRecordID(String recordType) {
        int posID = 0;
        // DEPENDENCY: must be set into TestData object by RestClient.createNewRecord()
        if (recordType.equalsIgnoreCase("positions")) {
            posID = getTestDataInteger("newPositionID");
        } else if (recordType.equalsIgnoreCase("candidates")) {
            posID = getTestDataInteger("newCandidateID");
        } else {
            throw new IllegalStateException("Error: recordType " + recordType + " invalid in this API!");
        }
        return posID;
    }

    // --------------------------------------------------------------
    //  Find Records in Appropriate List -- and Compare All Fields
    // --------------------------------------------------------------
    public void findInListAndCompare(String recordType, String recordName, List<Map<String, Object>> recordList, boolean shouldBeInList) {
        int targetID = 0;
        Map<String, String> expectedRecord = null;

        if (recordType.equalsIgnoreCase("positions")) {
            targetID = getTestDataInteger("newPositionID");
            expectedRecord = getPosition(recordName);
        } else if (recordType.equalsIgnoreCase("candidates")) {
            targetID = getTestDataInteger("newCandidateID");
            expectedRecord = getCandidate(recordName);
        } else {
            throw new IllegalStateException("Error: recordType "+ recordType + " invalid in this API!");
        }

        // Check all positions in Response Body List -- for position just created
        boolean isInList = false;
        String listedPosID = "";

        for (Map<String, Object> positionOBJ : recordList) {
            listedPosID = String.valueOf(positionOBJ.get("id"));
            System.out.println("List Position = " + listedPosID + " compare " + targetID);
            if (listedPosID.equals("" + targetID)) {
                isInList = true;

                // New code to check out all the other fields
                System.out.println("============================================");
                System.out.println(" List of " +  recordType);
                System.out.println(" Expected Test Data Record Values: " + recordName);
                System.out.println("--------------------------------------------");
                for (String key : expectedRecord.keySet()) {

                    // Solve later .....
                    if (!key.equalsIgnoreCase("dateOpen") && !key.equalsIgnoreCase("password")) {
                        System.out.println(" Key = " + key + "    API Result: " + positionOBJ.get(key) + " <-->   Exp Value: " + expectedRecord.get(key));
                        assertThat(positionOBJ.get(key)).isEqualTo(expectedRecord.get(key));
                    }
                }
                System.out.println("--------------------------------------------");
                break;
            }
        }

        System.out.println("=========================================");
        System.out.println(" Target Record ID: " + targetID);
        if (shouldBeInList) {
            System.out.println(" Found Record ID:  " + listedPosID);
        }
        System.out.println(" Was in " + recordType + " list? " + isInList);
        System.out.println("=========================================");

        if (shouldBeInList) {
            assertThat(isInList).isTrue();
        } else {
            assertThat(isInList).isFalse();
        }
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
    //  POST https://skryabin.com/recruit/api/v1/candidates
    // --------------------------------------------------------------
    @When("I create via REST {string} as new {string}")
    public void iCreateViaApiNewRecord(String recordName, String recordType) {
        if (recordType.equalsIgnoreCase("positions")) {
            Map<String, Object> result = RC.createNewRecord(recordType, getPosition(recordName));
            setTestData("newPosition", result);
            setTestData("newPositionID", result.get("id"));
        } else if (recordType.equalsIgnoreCase("candidates")) {
            Map<String, Object> result = RC.createNewRecord(recordType, getCandidate(recordName));
            setTestData("newCandidate", result);
            setTestData("newCandidateID", result.get("id"));
        } else {
            throw new IllegalStateException("Error: recordType "+ recordType + " invalid in this API!");
        }
    }

    // --------------------------------------------------------------
    //  GET https://skryabin.com/recruit/api/v1/positions
    //  GET https://skryabin.com/recruit/api/v1/candidates
    // --------------------------------------------------------------
    @Then("I verify via REST new {string} is {string} the {string} list")
    public void iVerifyNewRecordIsInTheList(String recordName, String inOrNot, String recordType) {
        Boolean shouldBeInList = true;
        if (inOrNot.equalsIgnoreCase("not in")) { shouldBeInList = false; }
        List<Map<String, Object>> result = RC.getRecordsList(recordType);
        findInListAndCompare(recordType, recordName, result, shouldBeInList);
    }

    // --------------------------------------------------------------
    //  PUT https://skryabin.com/recruit/api/v1/positions/2887
    //  PUT https://skryabin.com/recruit/api/v1/candidates/2887
    // --------------------------------------------------------------
    @When("I update via REST {string} record in {string}")
    public void iUpdateViaRESTRecordIn(String providedRecord, String recordType) {
        int newRecID = 0;

        // Create and Store Update Fields
        String expectedAddress = "258 Very Modified St";
        String expectedCity = "Modified Village";
        // Needed for compare to actual record get request
        setTestData("updatedAddress", expectedAddress);
        setTestData("updatedCity", expectedCity);

        Map<String, String> record = getStrData(providedRecord);
        record.put("address", expectedAddress);
        record.put("city", expectedCity);

        // For previously created record (position or candidate) -- return ID
        newRecID = getNewRecordID(recordType);

        // Get Result of Update Action
        Map<String, Object> result = RC.updateRecord(newRecID, recordType, record);
        String actualAddress = result.get("address").toString();
        String actualCity = result.get("city").toString();

        // Verify Result of Update Action
        assertThat(actualAddress.equalsIgnoreCase(expectedAddress));
        assertThat(actualCity.equalsIgnoreCase(expectedCity));
    }

    // --------------------------------------------------------------
    @Then("I verify via REST {string} record in {string} is updated")
    public void iVerifyViaRESTNewRecordIsUpdated(String providedRecord, String recordType) {
        // For previously created record (position or candidate) -- return ID
        int posID = getNewRecordID(recordType);

        Map<String, Object> result = RC.verifyRecordUpdates(posID, recordType);
        String actualAddress = result.get("address").toString();
        String actualCity = result.get("city").toString();

        // Can verify that response itself has updated fields
        assertThat(actualAddress.equalsIgnoreCase(getTestDataString("updatedAddress")));
        assertThat(actualCity.equalsIgnoreCase(getTestDataString("updatedCity")));
    }

    // --------------------------------------------------------------
    @When("I delete via REST new {string} from the {string} list")
    public void iDeleteNewRecordFromList(String recordName, String recordType) {
        int posID = 0;
        // DEPENDENCY: must be set into TestData object by RestClient.createNewRecord()
        // For previously created record (position or candidate) -- return ID
        posID = getNewRecordID(recordType);
        RC.deleteListRecord(recordType, posID);
    }

    @Then("I clean up test environment for {string}")
    public void iCleanUpTestEnvironment(String recordType) {
        // Clean up for next time
        if (recordType.equalsIgnoreCase("positions")) {
            setTestData("newPositionID", "");
        } else if (recordType.equalsIgnoreCase("candidates")) {
            setTestData("newCandidateID", "");
        }
    }
}
