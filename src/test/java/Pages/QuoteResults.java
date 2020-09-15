package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static support.TestContext.getDriver;

public class QuoteResults {
    // ---------------------------------------------------
    //  constructor
    // ---------------------------------------------------
    public QuoteResults() {
        PageFactory.initElements(getDriver(), this);
    }

    // ---------------------------------------------------
    //  fields
    // ---------------------------------------------------
    @FindBy(xpath = "//b[@name='username']")
    private WebElement qrUsername;

    @FindBy(xpath = "//b[@name='email']")
    private WebElement qrEmail;

    @FindBy(xpath = "//b[@name='firstName']")
    private WebElement qrFirstName;

    @FindBy(xpath = "//b[@name='middleName']")
    private WebElement qrMiddleName;

    @FindBy(xpath = "//b[@name='lastName']")
    private WebElement qrLastName;

    @FindBy(xpath = "//b[@name='name']")
    private WebElement qrFullName;

    @FindBy(xpath = "//b[@name='phone']")
    private WebElement qrPhone;

    @FindBy(xpath = "//b[@name='dateOfBirth']")
    private WebElement qrDOB;

    @FindBy(xpath = "//b[@name='currentTime']")
    private WebElement qrTime;

    @FindBy(xpath = "//b[@name='address']")
    private WebElement qrAddress;

    @FindBy(xpath = "//b[@name='countryOfOrigin']")
    private WebElement qrCountry;

    @FindBy(xpath = "//b[@name='currentDate']")
    private WebElement qrDate;

    // ---------------------------------------------------
    //  methods
    // ---------------------------------------------------
    public String readQrUsername() {
        return qrUsername.getText();
    }

    public String readQrEmail() {
        return qrEmail.getText();
    }

    public String readQrFirstName() {
        return qrFirstName.getText();
    }

    public String readQrMiddleName() {
        return qrMiddleName.getText();
    }

    public String readQrLastName() {
        return qrLastName.getText();
    }

    public String readQrFullName() {
        return qrFullName.getText();
    }

    public String readQrPhone() {
        return qrPhone.getText();
    }

    public String readQrDOB() {
        return qrDOB.getText();
    }

    public String readQrTime() {
        return qrTime.getText();
    }

    public String readQrAddress() {
        return qrAddress.getText();
    }

    public String readQrCountry() {
        return qrCountry.getText();
    }

    public String readQrDate() {
        return qrDate.getText();
    }
}

