package definitions.GuiEnvironment.UnitConverter;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

import static definitions.GuiTestEnvironment.toolWaitForElementWithXpath;
import static support.TestContext.getDriver;

public class UnitConverterDefs {
    // =============================================
    //  Create page specific test data variables
    // =============================================
    String tdSrcUnit = "";
    String tdtrgUnit = "";
    float tdSrcValue = 0.0f;
    String tdCnvType = "";

    // ===========================================================================
    // Select conversion tab for Weight, Length or Temperature
    // ===========================================================================
    @When("I select the conversion tab for {string}")
    public void iSelectTheConversionTabFor(String tabName) {
        String tabXpath = "";

        switch (tabName) {
            case "Length":
                tabXpath = "//div[@id='menu']//a[contains(@href,'Length')]";
                break;
            case "Temperature":
                tabXpath = "//div[@id='menu']//a[contains(@href,'Temperature')]";
                break;
            case "Weight":
                tabXpath = "//div[@id='menu']//a[contains(@href,'Weight')]";
                break;
            case "Time":
                tabXpath = "//div[@id='menu']//a[contains(@href,'Time')]";
                break;
            default:
                throw new IllegalStateException("Error: This tab name is invalid: " + tabName);
        }
        getDriver().findElement(By.xpath(tabXpath)).click();
        tdCnvType = tabName;
    }

    // ===========================================================================
    // TEMPERATURE
    //select[@id='calFrom']//option[contains(text(),'Fahrenheit')]
    //input[@name='fromVal']
    //select[@id="calTo"]/option[contains(text(),'Celsius')]
    //input[@name='toVal']
    // ===========================================================================
    // LENGTH
    //select[@name='calFrom']/option[contains(text(),'Mile')
    //input[@name='fromVal']
    //select[@id="calTo"]/option[contains(text(),'Foot')]
    //input[@name='toVal']
    // ===========================================================================
    @When("I select the source unit as {string} with value {float} and target unit {string}")
    public void iSelectTheSourceUnitAsWithCountAndTargetUnit(String srcUnit, float srcCount, String trgUnit) {
        // Clear From Value Field and reset answer
        WebElement fromInputBox = toolWaitForElementWithXpath("//input[@name='fromVal']");
        fromInputBox.clear();
        // From Unit Selector -- choose srcUnit
        getDriver().findElement(By.xpath("//select[@id='calFrom']/option[contains(text(),'" + srcUnit + "')]")).click();
        // From Value -- choose trgUnit
        String keyStr = "" + srcCount + "";
        getDriver().findElement(By.xpath("//input[@name='fromVal']")).sendKeys(keyStr);
        // To Unit Selector
        getDriver().findElement(By.xpath("//select[@name='calTo']/option[contains(text(),'" + trgUnit + "')]")).click();
        // ------ Set Gherkin Units
        tdSrcUnit = srcUnit;
        tdSrcValue = srcCount;
        tdtrgUnit = trgUnit;
    }

    // ===========================================================================
    @Then("I expect the resulting amount to be {float} {string}")
    public void iExpectTheResultingAmountToBe(float toValue, String manAuto) {
        String mapKey = tdSrcUnit + "To" + tdtrgUnit;
        float expValue = 0.0f;
        double THRESHOLD = .01;

        Map<String, Float> unitConRatios = new HashMap<>();
        unitConRatios.put("MileToFoot", 5280.0f);
        unitConRatios.put("MileToKilometer", 1.609f);
        unitConRatios.put("MileToYard", 17609.0f);
        unitConRatios.put("MeterToYard", 1.0936f);
        unitConRatios.put("MeterToInch", 39.37f);
        unitConRatios.put("PoundToOunce", 16.0f);
        unitConRatios.put("PoundToKilogram", 0.4536f);
        unitConRatios.put("KilogramToPound", 2.2046f);

        // Figure what what expected value should be for specific test
        //     Temperature is different. Need to calculate expected value
        if (tdCnvType.equalsIgnoreCase("Temperature")) {
            THRESHOLD = .1;
            if (tdSrcUnit.equalsIgnoreCase("Fahrenheit") &&
                tdtrgUnit.equalsIgnoreCase("Celsius")) {
                mapKey = "FahrenheitToCelsius";
                if (manAuto.equalsIgnoreCase("as provided")) {
                    // Input value from Gherkin itself
                    expValue = toValue;
                } else {
                    // Calculate Internally
                    expValue = convertFtoCTemperature(tdSrcValue);
                }
            } else if (tdSrcUnit.equalsIgnoreCase("Celsius") &&
                       tdtrgUnit.equalsIgnoreCase("Fahrenheit")) {
                mapKey = "CelsiusToFahrenheit";
                if (manAuto.equalsIgnoreCase("as provided")) {
                    // Input value from Gherkin itself
                    expValue = toValue;
                } else {
                    // Calculate Internally
                    expValue = convertCtoFTemperature(tdSrcValue);
                }
            } else {
                mapKey = tdSrcUnit + "To" + tdtrgUnit;
                throw new IllegalStateException("Error: This conversion is not supported: " + mapKey);
            }
        } else {
            // Use units Map above for all other expected values (it's just a ratio for these)
            THRESHOLD = .1;
            if (manAuto.equalsIgnoreCase("as provided")) {
                // Input value from Gherkin itself
                expValue = toValue;
            } else {
                // Calculate Internally
                expValue = unitConRatios.get(mapKey) * tdSrcValue;
            }
        }

        // Get actual answer Value from the Converter web page
        String strActValue = getDriver().findElement(By.xpath("//input[@name='toVal']")).getAttribute("value");
        float actValue = Float.parseFloat(strActValue);

        // Show me anyway, I'm curious
        System.out.println("================================================");
        System.out.println(" Conversion Type:  " + mapKey);
        System.out.println("                   " + tdSrcValue + " " + tdSrcUnit + "(s)");
        System.out.println(" Expected Value:   " + expValue  + " " + tdtrgUnit + "(s)");
        System.out.println(" Actual Value:     " + actValue);
        // System.out.println(" Compares? = 0:    " + Float.compare(expValue, actValue)); DOES NOT WORK
        // Nice compare that checks for near equal floating point
        Boolean checkThreshCompare = false;
        if (Math.abs(expValue - actValue) < THRESHOLD) {
            checkThreshCompare = true;
            System.out.println(" Compare?     " + checkThreshCompare);
            System.out.println("================================================");
        } else {
            checkThreshCompare = false;
            System.out.println(" Compare?     " + checkThreshCompare);
            System.out.println("================================================");
        }
        Assert.assertEquals(checkThreshCompare, true);
    }

    // ===========================================================================
    //    Fahrenheit to Celsius
    // ===========================================================================
    public static float convertFtoCTemperature(float providedF)
    {
        float f, c;
        f = providedF;
        c = (5/9)*(f-32);
        return c;
    }

    // ===========================================================================
    //    Celsius to Fahrenheit
    // ===========================================================================
    public static float convertCtoFTemperature(float providedC)
    {
        float f, c;
        c = providedC;
        f = c*(9/5)+32;
        return f;
    }
}
