package PomEnvironment.USPS;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UspsLookupByZip extends UspsHeader {

    @FindBy(xpath="//a[contains(@href,'byaddress')][contains(@class,'code-address')]")
    private WebElement findByAddress;

    public void clickFindByAddress() {
        findByAddress.click();
    }
}
