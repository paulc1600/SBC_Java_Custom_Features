package PomEnvironment;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.logging.Level;

import static definitions.GuiTestEnvironment.*;
import static support.TestContext.getDriver;

/*
 -------------------------------------------------------------------------------------------------------------
    Pages.Page                input                 returns     description

    areErrorsPresent              --                boolean     Checks browser logs for ANY severe errors (T/F)
    click                     WebElement            void        Wait until can click. Click. Try JS if fails.
    clickWithJS               WebElement            void        Use JS to click element now.
    getAllByXpath             String          List WebElements  Find and return WebElement list at Xpath provided
    getByXpath                String                WebElement  Find and return WebElement at Xpath provided
    mouseOver                 WebElement            void        Moves to element
    refresh                       --                void        Refresh current browser page.
    sendKeys                  WebElement, String    void        Wait until visible. Send string to element
    waitForClickable          WebElement            void        Explicit wait element clickable state
    waitForDisappear          WebElement            void        Explicit wait until element goes invisible.
    waitForVisible            WebElement            void        Explicit wait element is visible
    waitToBeSelected          WebElement            void        Explicit wait element is selected
    waitUntilContainsText     WebElement            void        Explicit wait element fills at least one

 -------------------------------------------------------------------------------------------------------------
*/

public class Page {

    // fields
    protected String url;
    protected String title;

    // constructor
    public Page() {
        PageFactory.initElements(getDriver(), this);
    }

    public void open() {
        url = tePageURL;
        title = tePageTitle;
        System.out.println("\n   Navigate: Open URL");
        System.out.println("================================================");
        System.out.println(" URL:   " + tePageURL);
        System.out.println(" Title: " + tePageTitle);
        System.out.println("================================================");
        getDriver().get(tePageURL);
    }

    public boolean areErrorsPresent() {
        LogEntries entries = getDriver().manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : entries) {
            if (entry.getLevel().equals(Level.SEVERE)) {
                System.err.println(entry);
                return true;
            }
        }
        return false;
    }

    protected WebElement getByXpath(String xpath) {
        return getDriver().findElement(By.xpath(xpath));
    }

    protected List<WebElement> getAllByXpath(String xpath) {
        return getDriver().findElements(By.xpath(xpath));
    }

    protected void mouseOver(WebElement element) {
        getActions().moveToElement(element).perform();
    }

    public void refresh() {
        getDriver().navigate().refresh();
    }

    public void waitForVisible(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitUntilContainsText(WebElement element) {
        getWait().until(driver -> !element.getText().isEmpty());
    }

    protected void waitForTextIn(WebElement element, String targetText) {
        getWait().until(ExpectedConditions.textToBePresentInElement(element, targetText));
    }

    protected void waitForClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForDisappear(WebElement element) {
        getWait().until(ExpectedConditions.invisibilityOf(element));
    }

    protected void waitToBeSelected(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeSelected(element));
    }

    protected void click(WebElement element) {
        waitForClickable(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            System.err.println("Exception clicking on element! Clicking with JS...");
            clickWithJS(element);
        }
    }

    protected void sendKeys(WebElement element, String value) {
        waitForVisible(element);
        element.sendKeys(value);
    }

    protected void clickWithJS(WebElement element) {
        getExecutor().executeScript("arguments[0].click();", element);
    }

}
