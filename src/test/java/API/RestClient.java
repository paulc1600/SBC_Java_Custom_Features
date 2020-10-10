package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static API.RestTestEnvironment.*;

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
    public void login(Map<String, String> user) {
        baseUrl = teBaseUrl;            // RestTestEnvironment
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
    // --------------------------------------------------------------
    public Map<String, Object> createPosition(Map<String, String> position) {
        Map<String, Object> result = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)                   // prepare
                .basePath("positions")
                .header(CONTENT_TYPE, JSON)
                .header(AUTH, loginToken)
                .body(position)
                .when()                            // execute
                .post()
                .then()                            // process response
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
    public List<Map<String, Object>> getPositionList() {
        List<Map<String, Object>> result = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath("positions")
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
    public Map<String, Object> updatePosition(Map<String, String> position) {
        String basePath = "positions/" +  getTestDataInteger("newPositionID").toString();

        Map<String, Object> result = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath(basePath)
                .header(CONTENT_TYPE, JSON)
                .header(AUTH, loginToken)
                .body(position)
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
    public Map<String, Object> verifyPositionUpdates(int posID) {
        String basePath = "positions/" + posID;

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
    //   returns
    //     204 = Successfully deleted
    // --------------------------------------------------------------
    public void deletePositionRecord(int posID) {
        String basePath = "positions/" + posID;

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
