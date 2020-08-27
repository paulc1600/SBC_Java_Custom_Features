package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

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
}
