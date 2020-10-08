package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static support.TestContext.*;

public class RestClient {

    private String baseUrl = "https://skryabin.com/recruit/api/v1/";
    private static String loginToken;
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String JSON = "application/json";
    public static final String AUTH = "Authorization";

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
    public void createPosition(Map<String, String> position) {
        String title = position.get("title");
        position.put("title", title + getTimestamp());

        // prepare
        RequestSpecification request = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath("positions")
                .header(CONTENT_TYPE, JSON)
                .header(AUTH, loginToken)
                .body(position);

        // execute
        Response response = request.when()
                .post();

        // verify and extract
        Map<String, Object> result = response.then()
                .log().all()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getMap("");

        setTestData("newPosition", result);
        setTestData("newPositionID", result.get("id"));
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
    public List<Map<String, Object>> getPositionList(int posID, boolean shouldBeInList) {
        // prepare
        RequestSpecification request = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath("positions");

        // execute
        Response response = request.when()
                .get();

        List <Map<String, Object>> result = response.then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("");

        // Check all positions in Response Body List -- for position just created
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
        if (shouldBeInList) {
            System.out.println("=========================================");
            System.out.println(" Target Record ID: " + posID);
            System.out.println(" Found Record ID:  " + listedPosID);
            System.out.println(" Was in list?      " + isInList);
            System.out.println("=========================================");
            assertThat(isInList);
        } else {
            System.out.println("=========================================");
            System.out.println(" Target Record ID: " + posID);
            System.out.println(" Was in list?      " + isInList);
            System.out.println("=========================================");
            assertThat(isInList).isFalse();
        }
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
        String expectedAddress = "258 Very Modified St";
        String expectedCity = "Modified Village";

        // Needed for compare to actual record get request
        setTestData("updatedAddress", expectedAddress);
        setTestData("updatedCity", expectedCity);

        position.put("address", expectedAddress);
        position.put("city", expectedCity);

        // prepare
        RequestSpecification request = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath(basePath)
                .header(CONTENT_TYPE, JSON)
                .header(AUTH, loginToken)
                .body(position);

        // execute
        Response response = request.when()
                .put();

        // verify and extract
        Map<String, Object> result = response.then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getMap("");

        String actualAddress = result.get("address").toString();
        String actualCity = result.get("city").toString();

        // Can verify that response itself has updated fields
        assertThat(actualAddress.equalsIgnoreCase(expectedAddress));
        assertThat(actualCity.equalsIgnoreCase(expectedCity));
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
    public void verifyPositionUpdates(int posID) {
        String basePath = "positions/" + posID;

        // prepare
        RequestSpecification request = RestAssured.given()
                .log().all()
                .baseUri(baseUrl)
                .basePath(basePath);

        // execute
        Response response = request.when()
                .get();

        // verify and extract
        Map<String, Object> result = response.then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getMap("");

        String actualAddress = result.get("address").toString();
        String actualCity = result.get("city").toString();

        // Can verify that response itself has updated fields
        assertThat(actualAddress.equalsIgnoreCase(getTestDataString("updatedAddress")));
        assertThat(actualCity.equalsIgnoreCase(getTestDataString("updatedCity")));
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
