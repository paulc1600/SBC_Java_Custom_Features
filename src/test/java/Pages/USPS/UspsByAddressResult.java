package Pages.USPS;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class UspsByAddressResult extends UspsHeader {

    @FindBy(id="zipByAddressDiv")
    private WebElement searchResult;

    @FindBy(xpath = "//*[@class='zipcode-result-address']")
    private List<WebElement> results;

    public String getSearchResult() {
        waitUntilContainsText(searchResult);
        return searchResult.getText();
    }

    public boolean areAllResultsContainZip(String zip) {
        for (WebElement result : results) {
            if (!result.getText().contains(zip)) {
                return false;
            }
        }
        return true;
    }

}
