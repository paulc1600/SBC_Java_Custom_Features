package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static support.TestContext.getDriver;

public class MarketStepdefs  {
    @Given("I go to {string} page")
    public void iGoToPage(String page) {
        if (page.equalsIgnoreCase("quote")) {
            getDriver().get("https://skryabin.com/market/quote.html");
        } else if (page.equalsIgnoreCase("google")) {
            getDriver().get("https://www.google.com/");
        } else if (page.equalsIgnoreCase("yahoo")) {
            getDriver().get("https://www.yahoo.com/");
        } else {
            throw new RuntimeException("Unsupported page: " + page);
        }
    }

    @And("I print page details")
    public void iPrintPageDetails() {
        String pageTitle = getDriver().getTitle();
        String pageURL = getDriver().getCurrentUrl();
        System.out.println("================================================");
        System.out.println(" URL:   " + pageURL);
        System.out.println(" Title: " + pageTitle);
        System.out.println("================================================\n");
    }

    @And("I go back and forward, then refresh")
    public void iGoBackAndForwardThenRefresh() {
        System.out.println(" ");
    }

    @And("I fill out required fields")
    public void iFillOutRequiredFields() {
        getDriver().findElement(By.xpath("//input[@name='username']")).sendKeys("jgoober");
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys("John P Goober");
        getDriver().findElement(By.xpath("//input[@id='password']")).sendKeys("foobah123");
        getDriver().findElement(By.xpath("//input[@id='confirmPassword']")).sendKeys("foobah123");
        // Create and populate email element
        WebElement email=getDriver().findElement(By.xpath("//input[@name='email']"));
        email.sendKeys("jgoober@technolyth.com");
        // OPT And I type "560 American Way, Good City, CA" into element with xpath "//textarea[@id='address']"
        // OPT And I click on element with xpath "//select[@name='countryOfOrigin']"
        // OPT And I click on element with xpath "//option[contains(text(),'United States of America')]"
        // OPT And I type "05/19/1980" into element with xpath "//input[@name='dateOfBirth']"
        // OPT And I click on element with xpath "//input[@name='gender'][@value='male']"
        getDriver().findElement(By.xpath("//input[@name='agreedToPrivacyPolicy']")).click();
    }

    // Create and click submit button
    @And("I submit the form")
    public void iSubmitTheForm() {
        WebElement Submit=getDriver().findElement(By.xpath("//button[@id='formSubmit']"));
        Submit.submit();
    }

    @Then("I verify required fields")
    public void iVerifyRequiredFields() {
        // Correct Place and Looks Good
        WebElement pageResult=getDriver().findElement(By.xpath("//div[@id='quotePageResult']"));
        assertThat(pageResult.isDisplayed()).isTrue();
        String actualText1 = getDriver().findElement(By.xpath("//legend[@class='applicationResult']")).getText();
        assertThat(actualText1).containsIgnoringCase("Submitted Application");
        // Username
        String actualText2 = getDriver().findElement(By.xpath("//b[@name='username']")).getText();
        assertThat(actualText2).containsIgnoringCase("jgoober");
        // Email
        String actualText3 = getDriver().findElement(By.xpath("//b[@name='email']")).getText();
        assertThat(actualText3).containsIgnoringCase("goober@technolyth.com");
        // Full Name
        String actualText4 = getDriver().findElement(By.xpath("//b[@name='name']")).getText();
        assertThat(actualText4).containsIgnoringCase("John P Goober");
        // Privacy policy checked
        String agreedPrivacy=getDriver().findElement(By.xpath("//b[@name='agreedToPrivacyPolicy']")).getText();
        assertThat(agreedPrivacy).containsIgnoringCase("true");
    }
}
