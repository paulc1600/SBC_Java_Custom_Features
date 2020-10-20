package PomEnvironment.USPS.Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static definitions.GuiTestEnvironment.getExecutor;
import static org.assertj.core.api.Assertions.assertThat;

public class UspsCalculator extends UspsHeader {

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
    @FindBy(xpath = "//div[@id='total']")
    private WebElement totalPostage;
    @FindBy(xpath = "//input[@value='Calculate'][@type='button']")
    private WebElement calculateButton;

    public void selectCountry(String countryValue) {
        waitForClickable(destCountry);
        // WebElement destCountry = getDriver().findElement(By.xpath("//select[@name='CountryID']"));
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

        // selectedIcon = getDriver().findElement(By.xpath(selectedXpath));
        waitForVisible(selectedIcon);
        //This will scroll the page till the element is found
        getExecutor().executeScript("arguments[0].scrollIntoView();", selectedIcon);
        // use bullet-proof click
        click(selectedIcon);
    }

    public void inputQuantity(String providedQuantity) {
        waitForClickable(quantity);
        // WebElement quantity = getDriver().findElement(By.xpath("//input[@id='quantity-0']"));
        quantity.sendKeys(providedQuantity);
    }

    public void goCalculatePostage() {
        // WebElement calculateButton = getDriver().findElement(By.xpath("//input[@value='Calculate'][@type='button']"));
        click(calculateButton);
    }

    // ------------------------------------------------------
    //  Check totalPostage against Testcase value
    // ------------------------------------------------------
    public void checkPostageTotal(String providedTotal) {
        String uspsCalcOutput = totalPostage.getText();
        System.out.println("================================================");
        System.out.println(" Expected Total:  " + providedTotal);
        System.out.println(" Actual Total:    " + uspsCalcOutput);
        System.out.println("================================================");
        // Move to step def !!  Wrong place for it. ...
        assertThat(uspsCalcOutput).isEqualToIgnoringCase(providedTotal);
    }
}
