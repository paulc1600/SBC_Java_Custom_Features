package definitions;

import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static support.TestContext.getDriver;

public class quoteSmokeDefs {
    @Then("I select option {string} from element with xpath {string}")
    public void iSelectOptionFromElementWithXpath(String option, String xpath) {
        new Select(getDriver().findElement(By.xpath(xpath))).selectByVisibleText(option);
    }
}
