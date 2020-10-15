package Pages.Careers;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CareersNewPosition extends CareersRecruiterHome {
    @FindBy(xpath="//div[@class='row']//input[contains(@placeholder,'Title')]")
    private WebElement textBoxTitle;

    @FindBy(xpath="//div[@class='row']//textarea[contains(@placeholder,'Description')]")
    private WebElement textBoxDescription;

    @FindBy(xpath="//div[@class='row']//input/../preceding-sibling::label[@text()='Address']")
    private WebElement badXpathtextBoxAddress;

    // dynamic elements
    private WebElement positionCard(String title) {
        return getByXpath("//h4[text()='" + title + "']/ancestor::div[contains(@class,'card-body')]");
    }

    public void createNewPosition() {
        textBoxTitle.sendKeys("Junior QA Tester");
        textBoxDescription.sendKeys("Junior QA does highly focused and highly supervised inconsequential work that can't seriously hurt project");

    }

    public boolean isPositionVisible(String title) {
        try {
            return positionCard(title).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
