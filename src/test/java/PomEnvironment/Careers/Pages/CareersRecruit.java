package PomEnvironment.Careers.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static PomEnvironment.PomTestEnvironment.*;
import static support.TestContext.getDriver;

public class CareersRecruit extends CareersRecruiterHome{
    @FindBy(xpath="//button[contains(text(),'Careers')]")
    private WebElement buttonCareers;

    // dynamic elements
    private WebElement positionCard(String title) {
        return getByXpath("//h4[text()='" + title + "']/ancestor::div[contains(@class,'card-body')]");
    }

    private List<WebElement> allPositionCards(String title) {
        return getAllByXpath("//h4[text()='" + title + "']/ancestor::div[contains(@class,'card-body')]");
    }

    private WebElement closeForPosition(String title) {
        return getByXpath("//h4[text()='" + title + "']/ancestor::div[contains(@class,'card')]//button");
    }

    // Page Buttons
    public void returnRecruiterHome() {
        // use bullet-proof click
        click(buttonCareers);
    }

    // Page Methods
    public void removePositionOld(String positionTitle) {
        // Move to Job Card with correct Title and Cause Close Button to become Visible
        String xpathJobTitle = "//div[contains(@class,'card-body')]/a[contains(@href,'positions')]/*[contains(text(),'" + positionTitle + "')]";
        WebElement elJobCard = getDriver().findElement(By.xpath(xpathJobTitle));
        getActions().moveToElement(elJobCard).perform();

        // Calculate xpath for link in same card body as the one above (with correct Title)
        // Typically links looks like = "/positions/4"
        String xpathJobBodyLink = "//div[contains(@class,'card-body')]/a[contains(@href,'positions')]/*[contains(text(),'" + positionTitle + "')]/..";
        String cardBodyLink = getDriver().findElement(By.xpath(xpathJobBodyLink)).getAttribute("href");
        System.out.println("Info: cardBodyLink = " + cardBodyLink);
        // The job header element uses similar link, so can find correct close button from there
        // Need to prune the link down from 38
        String finalLink = cardBodyLink.substring(38);
        String xpathJobButton = "//div[contains(@class,'card-header')]/a[contains(@href,'" + finalLink + "')]/../button";
        WebElement elJobCloseButton = getDriver().findElement(By.xpath(xpathJobButton));
        // Click on correct Job -- Delete Job Card
        click(elJobCloseButton);
    }

    public boolean isPositionVisible(String title) {
        try {
            waitForVisible(positionCard(title));
            return positionCard(title).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public CareersRecruit removePosition(String title) {
        WebElement card = positionCard(title);
        WebElement closeButton = closeForPosition(title);

        mouseOver(card);
        waitForClickable(closeButton);
        click(closeButton);
        waitForDisappear(card);

        return new CareersRecruit();
    }

}
