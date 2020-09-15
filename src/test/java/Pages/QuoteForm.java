package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import static support.TestContext.getDriver;

public class QuoteForm {
    // ---------------------------------------------------
    //  constructor
    // ---------------------------------------------------
    public QuoteForm() {
        PageFactory.initElements(getDriver(), this);
        url = "https://skryabin.com/market/quote.html";
        title = "Get a Quote";
    }

    // ---------------------------------------------------
    //  fields inputs
    // ---------------------------------------------------
    private String url;
    private String title;

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(name = "confirmPassword")
    private WebElement confirmPassword;

    @FindBy(name = "name")
    private WebElement name;

    @FindBy(name = "firstName")
    private WebElement firstName;

    @FindBy(name = "lastName")
    private WebElement lastName;

    @FindBy(xpath = "//span[text()='Save']")
    private WebElement saveButton;

    @FindBy(name = "agreedToPrivacyPolicy")
    private WebElement privacy;

    @FindBy(xpath = "//input[@id='dateOfBirth']")
    private WebElement dateOfBirth;

    @FindBy(xpath = "//input[@name='phone']")
    private WebElement phoneNumber;

    @FindBy(xpath = "//select[@name='countryOfOrigin']")
    private WebElement countryOfOrigin;

    @FindBy(xpath = "//textarea[@id='address']")
    private WebElement address;

    @FindBy(xpath = "//select[@name='carMake']")
    private WebElement carMake;

    // Gender Split Into two component radio buttons
    @FindBy(xpath = "//input[@name='gender'][@value='male']")
    private WebElement elMaleRadio;
    @FindBy(xpath = "//input[@name='gender'][@value='female']")
    private WebElement elFemaleRadio;

    // Allowed to contact iFrame
    @FindBy(xpath = "//input[@name='allowedToContact']")
    private WebElement allowedToContact;
    @FindBy(xpath = "//input[@id='contactPersonName']")
    private WebElement contactPersonName;
    @FindBy(xpath = "//input[@id='contactPersonPhone']")
    private WebElement contactPersonPhone;

    // The Other Buttons / Link
    @FindBy(xpath = "//button[@id='thirdPartyButton']")
    private WebElement thirdPartyButton;
    @FindBy(xpath = "//button[contains(text(), 'Related documents')]")
    private WebElement relatedDocuments;
    @FindBy(xpath = "//a[contains(text(), 'View documents')]")
    private WebElement viewDocuments;
    @FindBy(xpath = "//button[@id='formReset']")
    private WebElement formReset;
    @FindBy(xpath = "//button[@id='formRefresh']")
    private WebElement formRefresh;
    @FindBy(xpath = "//input[@id='attachment']")
    private WebElement chooseFile;
    @FindBy(xpath = "//a[@href='Documents.pdf']")
    private WebElement download;
    // The form submit button
    @FindBy(id = "formSubmit")
    private WebElement submit;

    // ---------------------------------------------------
    //  fields error messages
    // ---------------------------------------------------
    @FindBy(xpath = "//label[@id='username-error']")
    private WebElement errorUserName;
    @FindBy(xpath = "//label[@id='email-error']")
    private WebElement errorEmail;
    @FindBy(xpath = "//label[@id='password-error']")
    private WebElement errorPassword;
    @FindBy(xpath = "//label[@id='name-error']")
    private WebElement errorName;
    @FindBy(xpath = "//label[@id='agreedToPrivacyPolicy-error']")
    private WebElement errorAgreedToPrivacyPolicy;

    // ---------------------------------------------------
    //  methods
    // ---------------------------------------------------
    public void open() {
        getDriver().get(url);
    }

    // ---------------------------------------------------
    //                Set Required Fields
    // ---------------------------------------------------
    public void fillUsername(String value) {
        username.sendKeys(value);
    }

    public void fillEmail(String value) {
        email.sendKeys(value);
    }

    public void fillBothPasswords(String value) {
        password.sendKeys(value);
        confirmPassword.sendKeys(value);
    }

    public void fillName(String firstNameValue, String lastNameValue) {
        name.click();
        firstName.sendKeys(firstNameValue);
        lastName.sendKeys(lastNameValue);
        saveButton.click();
    }

    public void agreeWithPrivacyPolicy() {
        if (!privacy.isSelected()) {
            privacy.click();
        }
    }

    // ---------------------------------------------------
    //                Set Optional Fields
    // ---------------------------------------------------
    public void fillDateOfBirth(String value) {
        // Only sets main dob for testing results -- Not sufficient to test dob UI itself
        dateOfBirth.sendKeys(value);
    }

    public void fillPhoneNumber(String value) {
        phoneNumber.sendKeys(value);
    }

    public void selectCountryOfOrigin(String country) {
        new Select(countryOfOrigin).selectByVisibleText(country);
    }

    public void fillAddress(String value) {
        address.sendKeys(value);
    }

    public void selectCarMake(String make) {
        new Select(carMake).selectByVisibleText(make);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("male")) {
            elMaleRadio.click();
        } else {
            elFemaleRadio.click();
        }
    }

    public void selectAllowedToContact() {
        if (!allowedToContact.isSelected()) {
            allowedToContact.click();
        }
    }

    public void fillContactPersonName(String value) {
        getDriver().switchTo().frame("additionalInfo");
        contactPersonName.sendKeys(value);
        getDriver().switchTo().defaultContent();
    }

    public void fillContactPersonPhone(String value) {
        getDriver().switchTo().frame("additionalInfo");
        contactPersonPhone.sendKeys(value);
        getDriver().switchTo().defaultContent();
    }

    // ---------------------------------------------------
    //                Read Error Fields
    // ---------------------------------------------------
    public String readErrorUserName() {
        return errorUserName.getText();
    }
    public String readErrorEmail() {
        return errorEmail.getText();
    }
    public String readErrorPassword() {
        return errorPassword.getText();
    }
    public String readErrorName() {
        return errorName.getText();
    }
    public String readErrorAgreedToPrivacyPolicy () {
        return errorAgreedToPrivacyPolicy.getText();
    }

    // ---------------------------------------------------
    //                Work with Buttons
    // ---------------------------------------------------
    public void submit() {
        // toolWaitForElementWithXpathAfterSecs("//input[@name='agreedToPrivacyPolicy']", "selected", 3);
        submit.click();
    }

    public void refresh() {
        formRefresh.click();
    }
}