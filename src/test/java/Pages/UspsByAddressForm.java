package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class UspsByAddressForm extends Pages.UspsHeader {

    @FindBy(id="tAddress")
    private WebElement street;

    @FindBy(id="tCity")
    private WebElement city;

    @FindBy(id="tState")
    private WebElement state;

    @FindBy(id="zip-by-address")
    private WebElement find;


    public void fillStreet(String value) {
        street.sendKeys(value);
    }

    public void fillCity(String value) {
        city.sendKeys(value);
    }

    public void clickFind() {
        find.click();
    }

    public void selectState(String value) {
        new Select(state).selectByValue(value);
    }

}
