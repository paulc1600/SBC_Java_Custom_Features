package definitions.GuiEnvironment.Calculator;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static definitions.GuiTestEnvironment.getWait;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static support.TestContext.getDriver;

public class CalculatorNetSteps {
    // -----------------------------------------------------------------
    @When("I navigate to {string}")
    public void iNavigateTo(String homeLink) {
        getDriver().findElement(By.xpath("//a[contains(text(),'Auto Loan Calculator')]")).click();
    }

    // -----------------------------------------------------------------
    @And("I clear all calculator fields")
    public void iClearAllCalculatorFields() {
        getDriver().findElement(By.xpath("//input[@id='cloanamount']")).clear();
        getDriver().findElement(By.xpath("//input[@id='cloanterm']")).clear();
        getDriver().findElement(By.xpath("//input[@id='cinterestrate']")).clear();
        getDriver().findElement(By.xpath("//input[@id='cdownpayment']")).clear();
        getDriver().findElement(By.xpath("//input[@id='ctradeinvalue']")).clear();
        getDriver().findElement(By.xpath("//input[@id='csaletax']")).clear();
        getDriver().findElement(By.xpath("//input[@id='ctitlereg']")).clear();
    }

    // -----------------------------------------------------------------
    @And("I calculate")
    public void iCalculate() throws InterruptedException {
        getDriver().findElement(By.xpath("//table//input[@type='image'][@value='Calculate']")).click();
    }

    // -----------------------------------------------------------------
    @Then("I verify {string} calculator error")
    public void iVerifyCalculatorError(String errorMessage) {
        Assert.assertTrue(getDriver().findElement(By.xpath("//tr//div/font[contains(text(),errorMessage)]")).isDisplayed());
    }

    // -----------------------------------------------------------------
    //  Fill Out Auto Loan Form directly from Gherkin Parms
    // -----------------------------------------------------------------
    @And("I enter {string} price, {string} months, {string} interest, {string} downpayment, {string} trade-in, {string} state, {string} percent tax, {string} fees")
    public void iEnterPriceMonthsInterestDownpaymentTradeInStatePercentTaxFees(String price, String nMont, String interest, String downPay, String tradeIn, String state, String salesTax, String salesfees) {
        getDriver().findElement(By.xpath("//input[@id='cloanamount']")).sendKeys(price);
        getDriver().findElement(By.xpath("//input[@id='cloanterm']")).sendKeys(nMont);
        getDriver().findElement(By.xpath("//input[@id='cinterestrate']")).sendKeys(interest);
        getDriver().findElement(By.xpath("//input[@id='cdownpayment']")).sendKeys(downPay);
        getDriver().findElement(By.xpath("//input[@id='ctradeinvalue']")).sendKeys(tradeIn);
        // Pick the State
        Select stateSelect = new Select(getDriver().findElement(By.xpath("//select[@name='cstate']")));
        stateSelect.selectByVisibleText(state);      // selectByValue(state)  DOES NOT WORK != CA;
        // Fill in Rest of form
        getDriver().findElement(By.xpath("//input[@id='csaletax']")).sendKeys(salesTax);
        getDriver().findElement(By.xpath("//input[@id='ctitlereg']")).sendKeys(salesfees);
    }

    // -----------------------------------------------------------------
    @Then("I verify monthly pay is {string}")
    public void iVerifyMonthlyPayIs(String expMonPay) {
        String ndExpPayStr = expMonPay.replace("$", "");
        String actPayNbrPart = "";
        float expResult = Float.parseFloat(ndExpPayStr);
        float actPayFloat = 0.0f;
        double THRESHOLD = .01;

        // Looking for actual result element
        String actPayStr = getDriver().findElement(By.xpath("//h2[@class='h2result'][contains(text(),'Monthly Pay')]")).getText();

        if (actPayStr.length() != 0) {
            //     Element is  ==>  <h2 class="h2result" xpath="1" style="">Monthly Pay: &nbsp; $144.48</h2>
            //      Text will be something like this .... "Monthly Pay: $144.48"
            int locDollar = actPayStr.indexOf("$");
            actPayNbrPart = actPayStr.substring(locDollar + 1);
            actPayFloat = Float.parseFloat(actPayNbrPart);
        } else {
            actPayNbrPart = "## Error ##";
            actPayFloat = 0.0f;
        }

        // Show me anyway, I'm curious
        System.out.println("================================================");
        System.out.println(" Expected Value:   " + ndExpPayStr);
        System.out.println(" Actual Value:     " + actPayNbrPart);
        // Nice compare that checks for near equal floating point
        Boolean checkThreshCompare = false;
        if (Math.abs(expResult - actPayFloat) < THRESHOLD) {
            checkThreshCompare = true;
            System.out.println(" Compare?     " + checkThreshCompare);
            System.out.println("================================================");
        } else {
            checkThreshCompare = false;
            System.out.println(" Compare?     " + checkThreshCompare);
            System.out.println("================================================");
        }
        org.junit.Assert.assertEquals(checkThreshCompare, true);
    }

    // -----------------------------------------------------------------
    //  @calculatorOutlineTests -- I start their scientific calculator
    // -----------------------------------------------------------------
    @When("I start their scientific calculator")
    public void iStartTheirScientificCalculator() {
        getDriver().findElement(By.xpath("//a[contains(text(),'Scientific Calculator')]")).click();
    }

    // -----------------------------------------------------------------
    //  @calculatorOutlineTests -- Enter 1st number
    // -----------------------------------------------------------------
    @And("I enter first number {string}")
    public void iEnterFirstNumber(String nbr1) {
        WebDriverWait calcAppears = getWait();
        calcAppears.until(elementToBeClickable(By.xpath("//span[contains(text(),'AC')]")));
        WebElement K0 = getDriver().findElement(By.xpath("//span[@class='scinm'][contains(text(),'0')]"));
        WebElement K1 = getDriver().findElement(By.xpath("//span[@class='scinm'][contains(text(),'1')]"));
        WebElement K2 = getDriver().findElement(By.xpath("//span[contains(text(),'2')]"));
        WebElement K3 = getDriver().findElement(By.xpath("//span[contains(text(),'3')]"));
        WebElement K4 = getDriver().findElement(By.xpath("//span[contains(text(),'4')]"));
        WebElement K5 = getDriver().findElement(By.xpath("//span[contains(text(),'5')]"));
        WebElement K6 = getDriver().findElement(By.xpath("//span[contains(text(),'6')]"));
        WebElement K7 = getDriver().findElement(By.xpath("//span[contains(text(),'7')]"));
        WebElement K8 = getDriver().findElement(By.xpath("//span[contains(text(),'8')]"));
        WebElement K9 = getDriver().findElement(By.xpath("//span[contains(text(),'9')]"));
        WebElement Kdp = getDriver().findElement(By.xpath("//span[contains(text(),'.')]"));
        WebElement Kac = getDriver().findElement(By.xpath("//span[contains(text(),'AC')]"));

        Kac.click();     //reset calculator

        for (int i = 0; i < nbr1.length(); i++){
            char c = nbr1.charAt(i);
            String key = String.valueOf(c);

            switch (key) {
                case "0" -> K0.click();
                case "1" -> K1.click();
                case "2" -> K2.click();
                case "3" -> K3.click();
                case "4" -> K4.click();
                case "5" -> K5.click();
                case "6" -> K6.click();
                case "7" -> K7.click();
                case "8" -> K8.click();
                case "9" -> K9.click();
                case "." -> Kdp.click();
                case "C" -> Kac.click();
                default -> throw new IllegalStateException("Error: This key name is invalid: " + key);
            }
        }
    }

    // -----------------------------------------------------------------
    //  @calculatorOutlineTests -- Enter Math Function
    // -----------------------------------------------------------------
    @And("I enter the function {string}")
    public void iEnterTheFunction(String func) throws InterruptedException {
        WebElement Kadd = getDriver().findElement(By.xpath("//span[contains(@onclick,\"r('+')\")]"));
        WebElement Ksub = getDriver().findElement(By.xpath("//span[contains(@onclick,\"r('-')\")]"));
        WebElement Kac = getDriver().findElement(By.xpath("//span[contains(text(),'AC')]"));

        switch (func) {
            case "+" -> Kadd.click();
            case "-" -> Ksub.click();
            case "C" -> Kac.click();
            default -> throw new IllegalStateException("Error: This key name is invalid: " + func);
        }
    }

    // -----------------------------------------------------------------
    //  @calculatorOutlineTests -- Enter 1st number
    // -----------------------------------------------------------------
    @And("I enter the second number {string}")
    public void iEnterTheSecondNumber(String nbr2) {
        WebElement K0 = getDriver().findElement(By.xpath("//span[@class='scinm'][contains(text(),'0')]"));
        WebElement K1 = getDriver().findElement(By.xpath("//span[@class='scinm'][contains(text(),'1')]"));
        WebElement K2 = getDriver().findElement(By.xpath("//span[contains(text(),'2')]"));
        WebElement K3 = getDriver().findElement(By.xpath("//span[contains(text(),'3')]"));
        WebElement K4 = getDriver().findElement(By.xpath("//span[contains(text(),'4')]"));
        WebElement K5 = getDriver().findElement(By.xpath("//span[contains(text(),'5')]"));
        WebElement K6 = getDriver().findElement(By.xpath("//span[contains(text(),'6')]"));
        WebElement K7 = getDriver().findElement(By.xpath("//span[contains(text(),'7')]"));
        WebElement K8 = getDriver().findElement(By.xpath("//span[contains(text(),'8')]"));
        WebElement K9 = getDriver().findElement(By.xpath("//span[contains(text(),'9')]"));
        WebElement Kdp = getDriver().findElement(By.xpath("//span[contains(text(),'.')]"));

        for (int i = 0; i < nbr2.length(); i++){
            char c = nbr2.charAt(i);
            String key = String.valueOf(c);

            switch (key) {
                case "0" -> K0.click();
                case "1" -> K1.click();
                case "2" -> K2.click();
                case "3" -> K3.click();
                case "4" -> K4.click();
                case "5" -> K5.click();
                case "6" -> K6.click();
                case "7" -> K7.click();
                case "8" -> K8.click();
                case "9" -> K9.click();
                case "." -> Kdp.click();
                default -> throw new IllegalStateException("Error: This key name is invalid: " + key);
            }
        }
    }

    // -----------------------------------------------------------------
    @And("I press the {string} key")
    public void iPressTheKey(String keyt) {
        WebElement Kadd = getDriver().findElement(By.xpath("//span[contains(@onclick,\"r('+')\")]"));
        WebElement Ksub = getDriver().findElement(By.xpath("//span[contains(@onclick,\"r('-')\")]"));
        WebElement Kac = getDriver().findElement(By.xpath("//span[contains(text(),'AC')]"));
        WebElement Keq = getDriver().findElement(By.xpath("//span[contains(text(),'=')]"));

        switch (keyt) {
            case "+" -> Kadd.click();
            case "-" -> Ksub.click();
            case "C" -> Kac.click();
            case "=" -> Keq.click();
            default -> throw new IllegalStateException("Error: This key name is invalid: " + keyt);
        }
    }

    // -----------------------------------------------------------------
    //  @calculatorOutlineTests -- verify calculator results
    // -----------------------------------------------------------------
    @Then("I verify the calculation as {string}")
    public void iVerifyTheCalculationAs(String result) {
        WebElement Kresult = getDriver().findElement(By.xpath("//div[@id='sciOutPut']"));
        String expValue = result.strip();
        String actValue = Kresult.getText().strip();
        System.out.println("================================================");
        System.out.println(" Expected Value:   " + expValue);
        System.out.println(" Actual Value:     " + actValue);
        System.out.println("------------------------------------------------");
        assertTrue(expValue.equalsIgnoreCase(actValue));
    }
}
