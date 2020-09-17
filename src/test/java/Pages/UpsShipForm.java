package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import static support.TestContext.getDriver;

public class UpsShipForm {
    // ---------------------------------------------------
    //  constructor
    // ---------------------------------------------------
    public UpsShipForm() {
        // Makes the lazy association annotation possible
        PageFactory.initElements(getDriver(), this);
    }

    // ---------------------------------------------------
    //  Origin Form -- fields inputs
    // ---------------------------------------------------
    @FindBy(xpath = "//select[@id='origincountry']")
    private WebElement originCountry;
    @FindBy(xpath = "//input[@id='originname']")
    private WebElement originName;
    @FindBy(xpath = "//input[@id='origincontactName']")
    private WebElement originContactName;
    @FindBy(xpath = "//input[@id='originaddress1']")
    private WebElement originAddress1;
    @FindBy(xpath = "//input[@id='originpostal']")
    private WebElement originPostalCode;
    @FindBy(xpath = "//input[@id='origincity']")
    private WebElement originCity;
    @FindBy(xpath = "//select[@id='originstate']")
    private WebElement originState;
    @FindBy(xpath = "//input[@id='originemail']")
    private WebElement originEmail;
    @FindBy(xpath = "//input[@id='originphone']")
    private WebElement originPhone;

    // ---------------------------------------------------
    //  Origin Form -- continue
    // ---------------------------------------------------
    @FindBy(xpath = "//button[@id='nbsBackForwardNavigationContinueButton']")
    private WebElement continueButton;

    // ---------------------------------------------------
    //  Work with Origin Form
    // ---------------------------------------------------
    public void selectOriginCountry(String value) {
        new Select(originCountry).selectByVisibleText(value);
    }
    public void fillOriginName(String value) { originName.sendKeys(value); }
    public void fillOriginContactName(String value) { originContactName.sendKeys(value); }
    public void fillOriginAddress1(String value) { originAddress1.sendKeys(value); }
    public void fillOriginPostalCode(String value) { originPostalCode.sendKeys(value); }
    public void fillOriginCity(String value) { originCity.sendKeys(value); }
    public void selectOriginState(String value) {
        new Select(originState).selectByVisibleText(value);
    }
    public void fillOriginEmail(String value) { originEmail.sendKeys(value); }
    public void fillOriginPhone(String value) { originPhone.sendKeys(value); }

    // ---------------------------------------------------
    //  Submit Origin Form
    // ---------------------------------------------------
    public void submitOriginForm() {
        continueButton.click();
    }
}
