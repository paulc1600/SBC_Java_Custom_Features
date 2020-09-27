package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CareersHome extends Pages.Page {
    // Get page factory and common project routines from Pages.page

    @FindBy(xpath="//input[@id='positionsQuickSearchInput']")
    private WebElement searchTitle;

    @FindBy(xpath="//button[@id='positionsQuickSearchButton']")
    private WebElement buttonSearch;
}
