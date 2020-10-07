package definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import support.RestClient;

import static definitions.ATestToolBox.*;
import static definitions.ATestToolBox.tePageURL;

public class RestStepDefs {
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
    //     {
    //        "email": "owen@example.com",
    //        "password": "welcome"
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
    @Given("I login via REST as {string}")
    public void iLoginViaRESTAs(String role) {
        new RestClient().login(getStrData(role));
    }

    @When("I create via REST {string} position")
    public void iCreateViaRESTPosition(String type) {
        new RestClient().createPosition(getStrData(type));
    }
}
