package definitions;

import cucumber.api.java.en.Given;

import java.util.Map;

import static PomEnvironment.PomTestEnvironment.*;
import static support.TestContext.getDriver;
import static support.TestContext.getTimestamp;

public class CommonPomStepDefs {
    // --------------------------------------------------------------
    //  Read and Prepare a Position Record -- Careers API Envr
    // --------------------------------------------------------------
    public static Map<String, String> getPosition(String filename) {
        Map<String, String> position = getPomStrData(filename);
        String title = position.get("title");
        position.put("title", title + getTimestamp());
        return position;
    }

    // --------------------------------------------------------------
    //  Read and Prepare a Candidate Record -- Careers API Envr
    // --------------------------------------------------------------
    public static Map<String, String> getCandidate(String filename) {
        Map<String, String> candidate = getPomStrData(filename);
        String email = candidate.get("email");
        String[] emailComp = email.split("@");
        candidate.put("email", emailComp[0] + getTimestamp() + "@" + emailComp[1]);
        return candidate;
    }

    // --------------------------------------------------------------
    //  Read and Prepare a Application Record -- Careers API Envr
    // --------------------------------------------------------------
    public static Map<String, String> getApplication(String filename) {
        Map<String, String> application = getPomStrData(filename);
        // String email = application.get("email");
        // String[] emailComp = email.split("@");
        // application.put("email", emailComp[0] + getTimestamp() + "@" + emailComp[1]);
        return application;
    }

    // ----------------------------------------------------------------------
    // @usps6                      Scenario: Validate ZIP code oop
    // @usps7                      Scenario: USPS Calculate price with POM
    // ----------------------------------------------------------------------
    @Given("I open {string} page oop")
    public void iOpenPageOop(String url) {
        switch (url) {
            case "careers":
                tePageURL = "https://skryabin-careers.herokuapp.com/";
                tePageTitle = "Careers Portal";
                break;
            case "quote":
                tePageURL = "https://skryabin.com/market/quote.html";
                tePageTitle = "Quote";
                tdFileName = "user";
                break;
            case "google":
                tePageURL = "https://www.google.com/";
                tePageTitle = "Google";
                break;
            case "yahoo":
                tePageURL = "https://www.yahoo.com/";
                tePageTitle = "Yahoo";
                break;
            case "usps":
                tePageURL = "https://www.usps.com/";
                tePageTitle = "USPS";
                break;
            case "ups":
                tePageURL = " https://www.ups.com/us/en/Home.page";
                tePageTitle = "Global Shipping & Logistics Services | UPS - United States";
                tdFileName = "dataUPS";
                break;
            case "unit converter":
                tePageURL = "https://www.unitconverters.net/";
                tePageTitle = "Unit Converter";
                break;
            case "calculator":
                tePageURL = "https://www.calculator.net/";
                tePageTitle = "Calculator.net: Free Online Calculators - Math, Fitness, Finance, Science";
                break;
            default:
                tePageURL = url;
        }
        System.out.println("\n   Navigate: Open URL");
        System.out.println("================================================");
        System.out.println(" URL:   " + tePageURL);
        System.out.println(" Title: " + tePageTitle);
        System.out.println("================================================");
        getDriver().get(tePageURL);
    }

    // ===========================================================================
    //   Test Environment -- Page Selector
    //    (can be called from Gherkin)
    // ===========================================================================
    @Given("POM Tool that goes to the {string} OOP page")
    public void iGoToThePage(String page) {
        switch (page) {
            case "careers":
                tePageURL = "https://skryabin-careers.herokuapp.com/";
                tePageTitle = "Careers Portal";
                break;
            case "quote":
                tePageURL = "https://skryabin.com/market/quote.html";
                tePageTitle = "Quote";
                tdFileName = "user";
                break;
            case "usps":
                tePageURL = "https://www.usps.com/";
                tePageTitle = "USPS";
                break;
            case "ups":
                tePageURL = " https://www.ups.com/us/en/Home.page";
                tePageTitle = "Global Shipping & Logistics Services | UPS - United States";
                tdFileName = "dataUPS";
                break;
            case "unit converter":
                tePageURL = "https://www.unitconverters.net/";
                tePageTitle = "Unit Converter";
                break;
            case "calculator":
                tePageURL = "https://www.calculator.net/";
                tePageTitle = "Calculator.net: Free Online Calculators - Math, Fitness, Finance, Science";
                break;
            default:
                tePageURL = page;
        }

        System.out.println("\n   Navigate: Open URL");
        getDriver().get(tePageURL);
        // Test Results of switching to this page
        trPageTitle = getDriver().getTitle();
        trPageURL = getDriver().getCurrentUrl();
        trWindowHandle = getDriver().getWindowHandle();
        trPageSource = getDriver().getPageSource();
    }
}
