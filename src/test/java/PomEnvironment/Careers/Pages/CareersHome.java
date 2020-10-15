package PomEnvironment.Careers.Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CareersHome extends PomEnvironment.Page {
    // Get page factory and common project routines from Pages.page

    @FindBy(xpath="//input[@id='positionsQuickSearchInput']")
    private WebElement searchTitle;

    @FindBy(xpath="//button[@id='positionsQuickSearchButton']")
    private WebElement buttonSearch;

    @FindBy(xpath="//button[contains(text(),'List')]")
    private WebElement buttonList;

    @FindBy(xpath="//button[contains(text(),'Grid')]")
    private WebElement buttonGrid;

    @FindBy(xpath="//button[contains(text(),'Ascending')]")
    private WebElement buttonAscending;

    @FindBy(xpath="//button[contains(text(),'Shuffle')]")
    private WebElement buttonShuffle;

    @FindBy(xpath="//button[contains(text(),'Refresh')]")
    private WebElement buttonRefresh;

    @FindBy(xpath="//li//span/a[contains(@href,'positions')]//h4")
    private List<WebElement> cardsJobList;
}
