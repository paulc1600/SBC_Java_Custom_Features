package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class UspsCalculator {
    @FindBy(xpath = "//*[@class='zipcode-result-address']")
    private List<WebElement> results;

    public String getSearchResult() {
        return "";
    }
}
