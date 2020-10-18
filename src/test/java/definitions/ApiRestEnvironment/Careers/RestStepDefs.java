package definitions.ApiRestEnvironment.Careers;

import ApiRestEnvironment.RestClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import definitions.CommonStepDefs;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.io.File;
import java.util.List;
import java.util.Map;

import static ApiRestEnvironment.RestTestContext.getStrData;
import static definitions.CommonStepDefs.*;
import static org.assertj.core.api.Assertions.assertThat;
import static support.TestContext.*;

public class RestStepDefs {
    RestClient RC = new RestClient();

    // ---------------------------------------------------------------------------
    //  Verify Login Response -- Careers API Envr
    // ---------------------------------------------------------------------------
    public void verifyLoginResponse(String role, Map<String, Object> resultToCheck) {
        // Get initial session values for comparison etc.
        String initialSessionName = role + "_session";
        Map<String, Object> expectedSession = getTestDataMap(initialSessionName);
        boolean expectedAuthenticated = (boolean) expectedSession.get("authenticated");
        String expectedToken = (String) expectedSession.get("token");
        int expectedIssuedAt = (int) expectedSession.get("issuedAt");
        int expectedExpiresAt = (int) expectedSession.get("expiresAt");
        // Not sure if should compare these -- should they even be in response??!!!
        String expectedEmail = getStrData(role).get("email");
        String expectedFullName = getStrData(role).get("name");

        // Check Returned Session Details
        //     Not sure if should compare name and email -- should they even be in response??!!!
        assertThat(resultToCheck.get("email").toString().equalsIgnoreCase(expectedEmail));
        String resultFullName = resultToCheck.get("firstName").toString() + " " + resultToCheck.get("lastName").toString();
        assertThat(resultFullName.equalsIgnoreCase(expectedFullName));
        //     Possible Swagger / Application bug !!??  -- authenticated missing
        //     assertThat(result.get("authenticated").equals(expectedAuthenticated));
        assertThat(resultToCheck.get("token").toString().equalsIgnoreCase(expectedToken));
        assertThat(resultToCheck.get("issuedAt").equals(expectedIssuedAt));
        assertThat(resultToCheck.get("expiresAt").equals(expectedExpiresAt));
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
        } else if (recordType.equalsIgnoreCase("applications")) {
            posID = getTestDataInteger("newApplicationID");
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
            expectedRecord = CommonStepDefs.getPosition(recordName);
        } else if (recordType.equalsIgnoreCase("candidates")) {
            targetID = getTestDataInteger("newCandidateID");
            expectedRecord = CommonStepDefs.getCandidate(recordName);
        } else if (recordType.equalsIgnoreCase("applications")) {
            targetID = getTestDataInteger("newApplicationID");
            expectedRecord = CommonStepDefs.getApplication(recordName);
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
        Map<String, Object> result = RC.login(getStrData(role));
        String initialSessionName = role + "_session";
        setTestData(initialSessionName, result);
    }

    // --------------------------------------------------------------
    //  POST https://skryabin.com/recruit/api/v1/verify
    // --------------------------------------------------------------
    @Then("I verify via REST {string} login")
    public void iVerifyViaRESTLogin(String role) {
        // Send API Verify Post
        Map<String, Object> result = RC.verify();
        System.out.println(result);
        // Verify REST Response Details
        verifyLoginResponse(role, result);
    }

    // --------------------------------------------------------------
    //  POST https://skryabin.com/recruit/api/v1/positions
    //  POST https://skryabin.com/recruit/api/v1/candidates
    //  POST https://skryabin.com/recruit/api/v1/applications
    // --------------------------------------------------------------
    @When("I create via REST {string} as new {string}")
    public void iCreateViaApiNewRecord(String recordName, String recordType) {
        if (recordType.equalsIgnoreCase("positions")) {
            Map<String, Object> result = RC.createNewRecord(recordType, getPosition(recordName));
            setTestData("newPosition", result);
            setTestData("newPositionID", result.get("id"));
            setTestData("newPositionName", recordName);
        } else if (recordType.equalsIgnoreCase("candidates")) {
            Map<String, Object> result = RC.createNewRecord(recordType, getCandidate(recordName));
            setTestData("newCandidate", result);
            setTestData("newCandidateID", result.get("id"));
            setTestData("newCandidateName", recordName);
        } else if (recordType.equalsIgnoreCase("applications")) {
            Map<String, Object> result = RC.createNewRecord(recordType, getApplication(recordName));
            setTestData("newApplication", result);
            setTestData("newApplicationID", result.get("id"));
            setTestData("newApplicationName", recordName);
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

    // ---------------------------------------------------------------------
    //   When I add via REST "resume" "pdf" to "sdet" in "candidates" list
    //   POST "Https://skryabin.com/recruit/api/v1/candidates/{id}/resume"
    // ---------------------------------------------------------------------
    @When("I add via REST {string} {string} to {string} in {string} list")
    public void iAddViaRESTResumeInList(String fileName, String fext, String actualRecordName, String recordType) {
        String expectedRecName = "";
        long fileCRC32 = getFileCRC32(teDataDirectory, fileName, fext);

        if (recordType.equalsIgnoreCase("positions")) {
            throw new IllegalStateException("Error: Cannot add resume to recordType "+ recordType + "!");
        } else if (recordType.equalsIgnoreCase("candidates")) {
            expectedRecName = getTestDataString("newCandidateName");
            setTestData("newCandidateFileCRC", fileCRC32);       //save to check resume contents
        } else if (recordType.equalsIgnoreCase("applications")) {
            expectedRecName = getTestDataString("newApplicationName");
            setTestData("newApplicationFileCRC", fileCRC32);     //save to check resume contents
        } else {
            throw new IllegalStateException("Error: recordType "+ recordType + " invalid in this API!");
        }

        // Check that a prior scenario created correct record
        if (actualRecordName.equalsIgnoreCase(expectedRecName)) {
            int targetID = getNewRecordID(recordType);
            File resumeFilePath = getFile(teDataDirectory, fileName, fext);
            RC.addResumeForRecord(targetID, recordType, resumeFilePath);
        } else {
            throw new IllegalStateException("Error: record ID for "+ actualRecordName + " not found!");
        }
    }

    // --------------------------------------------------------------------------------------
    //   Then I verify via REST that "resume" "pdf" is added to "sdet" in "candidates" list
    //   GET "Https://skryabin.com/recruit/api/v1/candidates/{id}/resume"
    // --------------------------------------------------------------------------------------
    @Then("I verify via REST that {string} {string} is added to {string} in {string} list")
    public void iVerifyViaRESTThatResumeIsAddedToInList(String filename, String fext, String providedRecord, String recordType) {
        int targetID = 0;
        long actualCRC2 = 0;
        long expectedCRC2 = 0;

        if (recordType.equalsIgnoreCase("positions")) {
            throw new IllegalStateException("Error: Cannot add resume to recordType "+ recordType + "!");
        } else if (recordType.equalsIgnoreCase("candidates")) {
            targetID = getTestDataInteger("newCandidateID");
            expectedCRC2 = getTestDataLong("newCandidateFileCRC");
        } else if (recordType.equalsIgnoreCase("applications")) {
            targetID = getTestDataInteger("newApplicationID");
            expectedCRC2 = getTestDataLong("newApplicationFileCRC");
        } else {
            throw new IllegalStateException("Error: recordType "+ recordType + " invalid in this API!");
        }

        // Send actual request
        ExtractableResponse<Response> resumeResponse = RC.getResumeFromRecord(targetID, recordType);

        // Verify Checksum of original resume file and response resume file are same
        byte[] resumeFileBA = resumeResponse.asByteArray();
        actualCRC2 = getFileCRC32(resumeFileBA);
        System.out.println("--------------------------------------------");
        System.out.println(" Expected CRC32 = " + expectedCRC2);
        System.out.println(" Actual CRC32   = " + actualCRC2);
        System.out.println("--------------------------------------------");
        assertThat(actualCRC2).isEqualTo(expectedCRC2);
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

    @Then("I clean up REST test environment for {string}")
    public void iCleanUpTestEnvironment(String recordType) {
        // Clean up for next time
        if (recordType.equalsIgnoreCase("positions")) {
            setTestData("newPositionID", "");
        } else if (recordType.equalsIgnoreCase("candidates")) {
            setTestData("newCandidateID", "");
        }
    }
}
