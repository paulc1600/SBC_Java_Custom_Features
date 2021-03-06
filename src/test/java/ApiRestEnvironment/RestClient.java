package ApiRestEnvironment;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.List;
import java.util.Map;

import static ApiRestEnvironment.RestTestContext.*;
import static support.TestContext.teApiBaseUrl;

public class RestClient {

    private String baseUrl;
    private static String loginToken;

    // --------------------------------------------------------------
    //  POST https://skryabin.com/recruit/api/v1/login
    //     {
    //        "email": "xxxxxxxxxxxxxxxxxx",
    //        "password": "xxxxxxxxxxxx"
    //     }
    //   returns
    //     {
    //         "authenticated": true,
    //         "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im9
    //              YXQiOjE2MDI.....blah, blah,"
    //         "issuedAt": 1602100131,
    //         "expiresAt": 1602186531
    //    }
    // --------------------------------------------------------------
    public Map<String, Object> login(Map<String, String> user) {
        baseUrl = teApiBaseUrl;            // RestTestContext
        // prepare -- header tells server how to parse body -- lookup header detail in Postman Header
        RequestSpecification request = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath("login")
                .header(CONTENT_TYPE, JSON)
                .body(user);

        // execute -- need use post (look in swagger)
        Response response = request.when().post();

        // verify and extract data
        Map<String, Object> result = response.then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getMap("");

        loginToken = "Bearer " + result.get("token");
        System.out.println(loginToken);
        return result;
    }

    // --------------------------------------------------------------
    //  POST https://skryabin.com/recruit/api/v1/verify
    //   returns
    //     {
    //         "authenticated": true,
    //         "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im9
    //              YXQiOjE2MDI.....blah, blah,"
    //         "issuedAt": 1602100131,
    //         "expiresAt": 1602186531
    //    }
    // --------------------------------------------------------------
    public Map<String, Object> verify() {
        baseUrl = teApiBaseUrl;            // RestTestContext
        Map<String, Object> result = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath("verify")
                .header(CONTENT_TYPE, JSON)
                .header(AUTH, loginToken)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getMap("");
        return result;
    }

    // --------------------------------------------------------------
    //  POST https://skryabin.com/recruit/api/v1/positions
    //    {
    //            "title": "Senior Unknown Analyst",
    //            "address": "1234 Fog Way",
    //            "city": "San Jose",
    //            "state": "CA",
    //            "zip": "95123",
    //            "description": "Help our company to be even more confused than it already is.",
    //            "dateOpen": "2020-04-28",
    //            "company": "Unknown Working Environment"
    //    }
    //   returns
    //    {
    //            "title": "Senior Unknown Analyst",
    //            "address": "1234 Fog Way",
    //            "city": "San Jose",
    //            "state": "CA",
    //            "zip": "95123",
    //            "description": "Help our company to be even more confused than it already is.",
    //            "dateOpen": "2020-04-28",
    //            "company": "Unknown Working Environment",
    //            "id": 2827
    //    }
    //  POST https://skryabin.com/recruit/api/v1/candidates
    // --------------------------------------------------------------
    public Map<String, Object> createNewRecord(String recordType, Map<String, String> recordContent) {
        boolean authNeeded = false;
        Response response = null;
        if (recordType.equalsIgnoreCase("positions")) { authNeeded = true; }

        if (authNeeded) {
            response = RestAssured.given()
                    .log().all()
                    .baseUri(baseUrl)                   // prepare
                    .basePath(recordType)
                    .header(CONTENT_TYPE, JSON)
                    .header(AUTH, loginToken)
                    .body(recordContent)
                    .when()                            // execute
                    .post();
        } else {
            response = RestAssured.given()
                    .log().all()
                    .baseUri(baseUrl)                   // prepare
                    .basePath(recordType)
                    .header(CONTENT_TYPE, JSON)
                    .body(recordContent)
                    .when()                            // execute
                    .post();
        }

        // verify successful completion
        Map<String, Object> result = response.then()         // process response
                .log().all()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getMap("");

        return result;
    }

    // --------------------------------------------------------------
    //  GET https://skryabin.com/recruit/api/v1/positions
    //   returns
    //   [
    //    {
    //        "id": 2834,
    //            "title": "Super Senior QA Automation",
    //            "address": "308 West Way",
    //            "city": "San Francisco",
    //            "state": "CA",
    //            "zip": "94099",
    //            "description": "Must have 7 years experience in Python, Java, C, Cobol",
    //            "dateOpen": "2020-12-05",
    //            "company": "Blue Corncobs",
    //            "candidatesCount": 0
    //    },
    // --------------------------------------------------------------
    public List<Map<String, Object>> getRecordsList(String recordType) {
        List<Map<String, Object>> result = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath(recordType)
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("");

        return result;
    }

    // --------------------------------------------------------------
    //  POST https://skryabin.com/recruit/api/v1/candidates/2887/resume
    //            .multiPart("file", new File("/path/to/file.json"))
    //            .multiPart("resume", resumeFilePath)
    //   returns 201
    // --------------------------------------------------------------
    public void addResumeForRecord(int newPosID, String recordType, File resumeFilePath) {
        String basePath = recordType + "/" +  newPosID + "/resume" ;

        RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath(basePath)
                .header(AUTH, loginToken)
                .multiPart("resume", resumeFilePath)
                .when()
                .post()
                .then()
                .statusCode(201);
    }

    // --------------------------------------------------------------
    //  POST https://skryabin.com/recruit/api/v1/candidates/2887/resume
    //            .multiPart("file", new File("/path/to/file.json"))
    //            .multiPart("resume", resumeFilePath)
    //   returns 201
    // --------------------------------------------------------------
    public ExtractableResponse<Response> getResumeFromRecord(int newPosID, String recordType) {
        String basePath = recordType + "/" +  newPosID + "/resume" ;

        ExtractableResponse<Response> response = null;

        response = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath(basePath)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract();

        return response;
    }

    // --------------------------------------------------------------
    //  PUT https://skryabin.com/recruit/api/v1/positions/2887
    //    {
    //            "title": "Senior Unknown Analyst",
    //            "address": "1234 Fog Way",
    //            "city": "San Jose",
    //            "state": "CA",
    //            "zip": "95123",
    //            "description": "Help our company to be even more confused than it already is.",
    //            "dateOpen": "2020-04-28",
    //            "company": "Unknown Working Environment"
    //    }
    //   returns
    // --------------------------------------------------------------
    public Map<String, Object> updateRecord(int newPosID, String recordType, Map<String, String> recordContent) {
        String basePath = recordType + "/" +  newPosID;

        Map<String, Object> result = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath(basePath)
                .header(CONTENT_TYPE, JSON)
                .header(AUTH, loginToken)
                .body(recordContent)
                .when()
                .put()
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getMap("");

        return result;
    }

    // --------------------------------------------------------------
    //  GET https://skryabin.com/recruit/api/v1/positions/2887
    //   returns
    //    {
    //            "title": "Senior Unknown Analyst",
    //            "address": "1234 Fog Way",
    //            "city": "San Jose",
    //            "state": "CA",
    //            "zip": "95123",
    //            "description": "Help our company to be even more confused than it already is.",
    //            "dateOpen": "2020-04-28",
    //            "company": "Unknown Working Environment"
    //    }
    // --------------------------------------------------------------
    public Map<String, Object> verifyRecordUpdates(int posID, String recordType) {
        String basePath = recordType + "/" + posID;

        // prepare
        Map<String, Object> result = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath(basePath)
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getMap("");

        return result;
    }

    // --------------------------------------------------------------
    //  DELETE https://skryabin.com/recruit/api/v1/positions/2887
    //  DELETE https://skryabin.com/recruit/api/v1/candidates/2887
    //   returns
    //     204 = Successfully deleted
    // --------------------------------------------------------------
    public void deleteListRecord(String recordType, int posID) {
        String basePath = recordType + "/" + posID;

        // prepare
        RequestSpecification request = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath(basePath)
                .header(AUTH, loginToken);

        // execute
        Response response = request.when()
                .delete();

        // verify successful completion
        String result = response.then()
                .log().all()
                .statusCode(204)
                .extract()
                .asString();
        System.out.println("Delete Result Body: " + result);
    }
}
