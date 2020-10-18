package PomEnvironment.Careers.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

import static definitions.CommonStepDefs.getPosition;
import static definitions.GuiTestEnvironment.getActions;
import static support.TestContext.getDriver;

public class CareersNewPosition extends CareersRecruiterHome {
    @FindBy(xpath="//div[@class='row']//input[contains(@placeholder,'Title')]")
    private WebElement textBoxTitle;

    @FindBy(xpath="//div[@class='row']//textarea[contains(@placeholder,'Description')]")
    private WebElement textBoxDescription;

    @FindBy(xpath="//div[contains(@class,'card-body')]//label[contains(@for,'Address')]/../input")
    private WebElement textBoxAddress;

    @FindBy(xpath="//div[contains(@class,'card-body')]//label[contains(@for,'City')]/../input")
    private WebElement textBoxCity;

    @FindBy(xpath="//div[contains(@class,'card-body')]//label[contains(@for,'State')]/../Select")
    private WebElement selectorState;

    @FindBy(xpath="//div[contains(@class,'card-body')]//label[contains(@for,'Zip')]/../input")
    private WebElement textBoxZip;

    @FindBy(xpath="//input[@id='positionDateOpen']")
    private WebElement inputDateOpen;
    @FindBy(xpath="//div[contains(@class,'today-button')]")
    private WebElement buttonToday;

    // dynamic elements
//    private WebElement positionCard(String title) {
//        return getByXpath("//h4[text()='" + title + "']/ancestor::div[contains(@class,'card-body')]");
//    }
    public void displayNewPosition(Map<String, String> position) {
        System.out.println("---------------------------------------");
        System.out.println("New Position Created ");
        System.out.println("Title:   " + position.get("title"));
        System.out.println("Descr:   " + position.get("description"));
        System.out.println("Address: " + position.get("address"));
        System.out.println("City:    " + position.get("city"));
        System.out.println("Sate:    " + position.get("state"));
        System.out.println("Zip:     " + position.get("zip"));
        System.out.println("---------------------------------------");
    }

    public Map<String, String> createNewPosition(String positionFile) {
        String xpathSelector = "//div[contains(@class,'card-body')]//label[contains(@for,'State')]/../Select";
        String xpathSubmit = "//button[@id='positionSubmit']";
        WebElement mySelector = getByXpath(xpathSelector);
        WebElement buttonSubmit = getByXpath(xpathSubmit);
        Map<String, String> position = getPosition(positionFile);
        displayNewPosition(position);

        textBoxTitle.sendKeys(position.get("title"));
        textBoxDescription.sendKeys(position.get("description"));
        textBoxAddress.sendKeys(position.get("address"));
        textBoxCity.sendKeys(position.get("city"));

        click(mySelector);
        mySelector.sendKeys(position.get("state"));

        textBoxZip.sendKeys(position.get("zip"));

        // dateOpen
        WebElement openDateDialog = getDriver().findElement(By.xpath("//input[@id='positionDateOpen']"));
        getActions()
                .moveToElement(openDateDialog)
                .click(openDateDialog)
                .perform();
        WebElement dialogTodayButton = getDriver().findElement(By.xpath("//div[contains(@class,'today-button')]"));
        getActions()
                .moveToElement(dialogTodayButton)
                .click(dialogTodayButton)
                .perform();

        // Submit the form
        click(buttonSubmit);
        return position;
    }
}
