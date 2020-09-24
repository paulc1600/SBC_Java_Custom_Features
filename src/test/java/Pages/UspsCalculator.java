package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static definitions.ATestToolBox.getExecutor;
import static support.TestContext.getDriver;

public class UspsCalculator extends Pages.UspsHeader {
    Page myPage = new Page();

    // ------------- Country and Type Panel
    @FindBy(xpath = "//select[@id='CountryID']")
    private WebElement destCountry;

    @FindBy(xpath = "//input[@id='Hazmat']")
    private WebElement cboxHazardousMaterials;

    @FindBy(xpath = "//input[@id='GroundTransportation']")
    private WebElement cboxRequiresGround;

    @FindBy(xpath = "//input[@value='Postcard'][@type='submit']")
    private WebElement iconPostcard;

    @FindBy(xpath = "//input[@value='FlatRateEnvelopes'][@type='submit']")
    private WebElement iconFlatRateEnvelopes;

    @FindBy(xpath = "//input[@value='FlatRateBoxes'][@type='submit']")
    private WebElement iconFlatRateBoxes;

    @FindBy(xpath = "//input[@value='ShapeAndSize'][@type='submit']")
    private WebElement iconByShapeSize;

    // ------------- Quantity Panel
    @FindBy(xpath = "//input[@id='quantity-0']")
    private WebElement quantity;
    @FindBy(xpath = "//input[@value='Calculate'][@type='button']")
    private WebElement calculateButton;

    public void selectCountry(String countryValue) {
        // myPage.waitForClickable(destCountry);  -- does not work -- destCountry always null
        WebElement destCountry = getDriver().findElement(By.xpath("//select[@name='CountryID']"));
        Select selectCountry = new Select(destCountry);
        selectCountry.selectByVisibleText(countryValue);
    }

    public void selectMailType(String typeValue) {
        WebElement selectedIcon = null;
        String selectedXpath = "";     // Needed for backup -- lazy association failing
        switch (typeValue) {
            case "Postcard":
                selectedIcon = iconPostcard;
                selectedXpath = "//input[@value='Postcard'][@type='submit']";
                break;
            case "FlatRateEnvelopes":
                selectedIcon = iconFlatRateEnvelopes;
                selectedXpath = "//input[@value='FlatRateEnvelopes'][@type='submit']";
                break;
            case "FlatRateBoxes":
                selectedIcon = iconFlatRateBoxes;
                selectedXpath = "//input[@value='FlatRateBoxes'][@type='submit']";
                break;
            case "ShapeAndSize":
                selectedIcon = iconByShapeSize;
                selectedXpath = "//input[@value='ShapeAndSize'][@type='submit']";
                break;
            default:
                throw new RuntimeException("Unsupported USPS mail selector type: " + typeValue);
        }
        // myPage.waitForVisible(selectedIcon); -- not working, always null
        selectedIcon = getDriver().findElement(By.xpath(selectedXpath));
        //This will scroll the page till the element is found
        getExecutor().executeScript("arguments[0].scrollIntoView();", selectedIcon);
        // use bullet-proof click
        myPage.click(selectedIcon);
    }

    public void inputQuantity(String providedQuantity) {
        // myPage.waitForClickable(quantity);  -- not working, always null
        WebElement quantity = getDriver().findElement(By.xpath("//input[@id='quantity-0']"));
        quantity.sendKeys(providedQuantity);
    }

    public void goCalculatePostage() {
        // Without findElement always fails with null pointer
        WebElement calculateButton = getDriver().findElement(By.xpath("//input[@id='quantity-0']"));
        myPage.click(calculateButton);
    }

}
