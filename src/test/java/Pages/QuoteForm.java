package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static definitions.ATestToolBox.getWait;
import static definitions.ATestToolBox.toolWaitForXpath;
import static org.assertj.core.api.Assertions.assertThat;
import static support.TestContext.getDriver;

public class QuoteForm {
    // ---------------------------------------------------
    //  constructor
    // ---------------------------------------------------
    public QuoteForm() {
        // Makes the lazy association annotation possible
        PageFactory.initElements(getDriver(), this);
        url = "https://skryabin.com/market/quote.html";
        title = "Get a Quote";
    }

    // ---------------------------------------------------
    //  fields inputs
    // ---------------------------------------------------
    private String url;
    private String title;

    // @FindBy(name = "username") -- fails on 2nd interaction with username with his error label
    @FindBy(xpath = "//input[@name='username']")
    private WebElement username;

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement password;

    @FindBy(xpath = "//input[@name='confirmPassword']")
    private WebElement confirmPassword;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement name;

    @FindBy(xpath = "//div[@role='dialog']")
    private WebElement nameDialogBox;

    @FindBy(xpath = "//input[@id='firstName']")
    private WebElement firstName;

    @FindBy(xpath = "//input[@id='middleName']")
    private WebElement middleName;

    @FindBy(xpath = "//input[@id='lastName']")
    private WebElement lastName;

    @FindBy(xpath = "//span[text()='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//input[@name='agreedToPrivacyPolicy']")
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
    @FindBy(xpath = "//label[@id='confirmPassword-error']")
    private WebElement errorConfirmPassword;
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
    // ----------------------------------------------------------------
    //   General Purpose Field Setter -- API Supports
    //      username      password            name
    //      email         confirmPassword     agreedToPrivacyPolicy
    //      firstName
    //      middleName
    //      lastName
    // ----------------------------------------------------------------
    private void setFieldValue(String fieldNameProvided, String fieldValueProvided) {
//        System.out.println("---------------------------------------");
//        System.out.println(" setFieldValue() field Name:  " + fieldNameProvided);
//        System.out.println(" setFieldValue() field Value: " + fieldValueProvided);
        WebElement fieldElement = returnThisElement(fieldNameProvided);
        if (fieldNameProvided.equalsIgnoreCase("password")) {
            fieldElement.clear();
            fieldElement.sendKeys(fieldValueProvided);
        } else if (fieldNameProvided.equalsIgnoreCase("confirmPassword")) {
            toolWaitForXpath("//input[@id='confirmPassword']", "clickable", 3);
            fieldElement.clear();
            fieldElement.sendKeys(fieldValueProvided);
        } else {
            fieldElement.sendKeys(fieldValueProvided);
        }
    }

    // ----------------------------------------------------------------
    //   Old Code -- to be refactored
    // ----------------------------------------------------------------
    public void fillUsername(String value) {
        setFieldValue("username", value);
    }
    public void fillEmail(String value) {
        setFieldValue("email", value);
    }
    public void fillBothPasswords(String value) {
        setFieldValue("password", value);
        setFieldValue("confirmPassword", value);
    }
    public void fillPassword(String value) {
        setFieldValue("password", value);
    }
    public void fillConfirmPassword(String value) {
        setFieldValue("confirmPassword", value);
    }
    public void fillName(String firstNameProvided, String lastNameProvided) {
        // Wait nameDialogBox to Disappear
        WebDriverWait waitDialog = getWait(3);
        waitDialog.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
        name.click();
        waitDialog.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
        setFieldValue("firstName", firstNameProvided);
        setFieldValue("lastName", lastNameProvided);
        saveButton.click();
    }
    public void checkName(String firstNameProvided, String lastNameProvided) {
        String nameValue = name.getAttribute("value");
        assertThat(nameValue).isEqualTo(firstNameProvided + " " + lastNameProvided);
    }
    public void fillName(String firstNameProvided, String middleNameProvided, String lastNameProvided) {
        // Wait nameDialogBox to Disappear
        WebDriverWait waitDialog = getWait(3);
        waitDialog.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
        name.click();
        waitDialog.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
        setFieldValue("firstName", firstNameProvided);
        setFieldValue("middleName", middleNameProvided);
        setFieldValue("lastName", lastNameProvided);
        saveButton.click();
    }
    public void checkName(String firstNameProvided, String middleNameProvided, String lastNameProvided) {
        String nameValue = name.getAttribute("value");
        assertThat(nameValue).isEqualTo(firstNameProvided + " " + lastNameProvided);
        assertThat(nameValue).isEqualTo(firstNameProvided + " " + middleNameProvided + " " + lastNameProvided);
    }

    public void agreeWithPrivacyPolicy() {
        if (!privacy.isSelected()) {
            privacy.click();
        }
    }

    // ---------------------------------------------------
    //                Set Optional Fields
    // ---------------------------------------------------
    // ----------------------------------------------------------------
    //   Also Old Code -- also needs to be refactored
    // ----------------------------------------------------------------
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
    private String xpathErrorUserName = "//label[@id='username-error']";
    private String xpathErrorEmail = "//label[@id='email-error']";
    private String xpathErrorPassword = "//label[@id='password-error']";
    private String xpathErrorConfirmPassword = "//label[@id='confirmPassword-error']";
    private String xpathErrorName =  "//label[@id='name-error']";
    private String xpathErrorAgreedPolicy = "//label[@id='agreedToPrivacyPolicy-error']";

    // ---------------------------------------------------------------------------------------
    //    Handles all errors without "state" problems
    // ---------------------------------------------------------------------------------------
    public String readErrorsCorrectly(WebElement errorElement, String xpathErrorElement, String changeTo) {
        String retValue = "";
        WebDriverWait changeWait = getWait(3);
        if (changeTo.equalsIgnoreCase("invisible")) {
            // Wait to Disappear
            changeWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathErrorElement)));
        } else {
            // Wait to appear
            changeWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorElement)));
        }

        Boolean isPresent = getDriver().findElements(By.xpath(xpathErrorElement)).size() > 0;

        // System.out.println("---------------------------------------");
        // System.out.println(" Error label xpath: " + xpathErrorElement);
        if (isPresent) {
            if (errorElement.isDisplayed()) {
                retValue = errorElement.getText();
            } else {
                retValue = "";
            }
            // System.out.println(" Error label visible? " + errorElement.isDisplayed());
        }
        // System.out.println(" Error label is present? " + isPresent);
        // System.out.println(" Error Message? " + retValue);
        // System.out.println("---------------------------------------");
        return retValue;
    }

    // -------------------------------------------------------------------------------------
    //         case "username" -> assertThat(formPage.readErrorUserName()).isEqualTo("");
    //         case "email" -> assertThat(formPage.readErrorEmail()).isEqualTo("");
    //         case "password" -> assertThat(formPage.readErrorPassword()).isEqualTo("");
    //         case "confirmPassword" -> assertThat(formPage.readErrorConfirmPassword()).isEqualTo("");
    //         case "name" -> assertThat(formPage.readErrorName()).isEqualTo("");
    //         case "agreedToPrivacyPolicy" -> assertThat(formPage.readErrorAgreedToPrivacyPolicy()).isEqualTo("");
    // -------------------------------------------------------------------------------------
    public String readErrorFields(String fieldName, String changeTo) {
        String neededValue = "";

        switch (fieldName) {
            case "username":
                neededValue = readErrorsCorrectly(errorUserName, xpathErrorUserName, changeTo);
                break;
            case "email":
                neededValue = readErrorsCorrectly(errorEmail, xpathErrorEmail, changeTo);
                break;
            case "password":
                neededValue = readErrorsCorrectly(errorPassword, xpathErrorPassword, changeTo);
                break;
            case "confirmPassword":
                neededValue = readErrorsCorrectly(errorConfirmPassword, xpathErrorConfirmPassword, changeTo);
                break;
            case "name":
                neededValue = readErrorsCorrectly(errorName, xpathErrorName, changeTo);
                break;
            case "agreedToPrivacyPolicy":
                neededValue = readErrorsCorrectly(errorAgreedToPrivacyPolicy, xpathErrorAgreedPolicy, changeTo);
                break;
            default:
                throw new IllegalStateException("Error: This form field name is invalid: " + fieldName);
        }
        return neededValue;
    }

    // -------------------------------------------------------------------------------------
    //    Convert Field Name to WebElement
    public WebElement returnThisElement(String fieldName) {
        WebElement desiredElement = null;
        switch (fieldName) {
            case "username":
                desiredElement = username;
                break;
            case "email":
                desiredElement = email;
                break;
            case "password":
                desiredElement = password;
                break;
            case "confirmPassword":
                desiredElement = confirmPassword;
                break;
            case "name":
                desiredElement = name;
                break;
            case "firstName":
                desiredElement = firstName;
                break;
            case "middleName":
                desiredElement = middleName;
                break;
            case "lastName":
                desiredElement = lastName;
                break;
            case "agreedToPrivacyPolicy":
                desiredElement = privacy;
                break;
            default:
                throw new IllegalStateException("Error: This form field name is invalid: " + fieldName);
        }
        return desiredElement;
    }


    // ---------------------------------------------------
    //                Work with Buttons
    // ---------------------------------------------------
    public void submit() {
        // toolWaitForXpath("//input[@name='agreedToPrivacyPolicy']", "selected", 3);
        submit.click();
    }

    public void refresh() {
        formRefresh.click();
    }
}