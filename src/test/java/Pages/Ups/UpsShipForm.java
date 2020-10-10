package Pages.Ups;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import static definitions.GuiTestEnvironment.*;
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

    @FindBy(xpath = "//span[@id='originnbsAgentSummaryName']")
    private WebElement resultOriginName;
    @FindBy(xpath = "//span[@id='originnbsAgentSummaryContactName']")
    private WebElement resultOriginContactName;
    @FindBy(xpath = "//span[@id='originnbsAgentSummaryAddressLine1']")
    private WebElement resultOriginAddressLine1;
    @FindBy(xpath = "//span[@id='originnbsAgentSummaryCity']")
    private WebElement resultOriginCity;
    @FindBy(xpath = "//span[@id='originnbsAgentSummaryState']")
    private WebElement resultOriginState;
    @FindBy(xpath = "//span[@id='originnbsAgentSummaryPostalCode']")
    private WebElement resultOriginPostalCode;
    @FindBy(xpath = "//span[@id='originnbsAgentSummaryCountryCode']")
    private WebElement resultOriginCountry;
    @FindBy(xpath = "//span[@id='originnbsAgentSummaryEmail']")
    private WebElement resultOriginEmail;
    @FindBy(xpath = "//span[@id='originnbsAgentSummaryPhone']")
    private WebElement resultOriginPhone;

    // ---------------------------------------------------
    //  Origin Form -- continue / cancel buttons
    // ---------------------------------------------------
    @FindBy(xpath = "//button[@id='nbsBackForwardNavigationContinueButton']")
    private WebElement continueButton;
    @FindBy(xpath = "//button[@id='nbsBackForwardNavigationCancelShipmentButton']")
    private WebElement cancelButton;

    // ---------------------------------------------------
    //  Modal Cancel Warning Form
    // ---------------------------------------------------
    @FindBy(xpath = "//div[@class='modal-content']")
    private WebElement modalForm;
    @FindBy(xpath = "//div[@class='modal-content']//button[@id='nbsCancelShipmentWarningYes']")
    private WebElement modalYesButton;

    // ---------------------------------------------------
    //  Work with Origin Form
    // ---------------------------------------------------
    // Watch out for Cookie Message!!!!
    public void handleCookiePolicy() {
        WebElement elCookieConsent = getDriver().findElement(By.xpath("//div[contains(@class,'implicit_consent')]"));
        if (elCookieConsent.isDisplayed()) {
            System.out.println("Info: Cookie Policy pop-up was present, and had to be closed.");
            getDriver().findElement(By.xpath("//div[contains(@class,'implicit_consent')]//button[contains(@class,'close')]")).click();
        }
    }

    public void selectOriginCountry(String value) {
        new Select(originCountry).selectByVisibleText(value);
    }
    public void fillOriginName(String value) { originName.sendKeys(value); }
    public void fillOriginContactName(String value) { originContactName.sendKeys(value); }
    public void fillOriginAddress1(String value) { originAddress1.sendKeys(value); }
    public void fillOriginPostalCode(String value) { originPostalCode.sendKeys(value); }
    public void fillOriginCity(String value) { originCity.sendKeys(value); }

    public void selectOriginState(String state2letter) {
        stateData = getGuiStrData("dataStateNames");
        if (stateData.containsKey(state2letter)) {
            String longName = stateData.get(state2letter);
            new Select(originState).selectByVisibleText(longName);
        } else {
            throw new IllegalStateException("Error: code "+ state2letter + " had no equivalent state name!");
        }
    }

    public void fillOriginEmail(String value) { originEmail.sendKeys(value); }
    public void fillOriginPhone(String value) { originPhone.sendKeys(value); }

    // ---------------------------------------------------
    //  Origin Form Buttons (submit / cancel)
    // ---------------------------------------------------
    public void submitOriginForm() {
        // Always scroll to continue button.
        getExecutor().executeScript("arguments[0].scrollIntoView(true);", continueButton);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Error: interrupted exception while waiting for scroll to complete!");
        }
        // Submit Completed Form using JS click (gets intercepted otherwise)
        getExecutor().executeScript("arguments[0].click();", continueButton);
        continueButton.click();
    }
    public void cancelOriginForm() {
        // Always scroll to continue button.
        getExecutor().executeScript("arguments[0].scrollIntoView(true);", cancelButton);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Error: interrupted exception while waiting for scroll to complete!");
        }
        // Submit Completed Form using JS click (gets intercepted otherwise)
        getExecutor().executeScript("arguments[0].click();", cancelButton);
    }

    // ---------------------------------------------------
    //  Handle Modal Warning
    //    private WebElement modalForm;
    //    private WebElement modalYesButton;
    // ---------------------------------------------------
    public void handleModalWarning() {
        String xpathINeed = lookUpXpath(modalForm);
        toolWaitForXpath(xpathINeed, "visible", 5);
        modalYesButton.click();
    }

    // ---------------------------------------------------
    //  Read Resulting Origin Data
    // ---------------------------------------------------
    public String getOriginName() { return resultOriginName.getText();}
    public String getOriginContactName() { return resultOriginContactName.getText();}
    public String getOriginAddressLine1() { return resultOriginAddressLine1.getText();}
    public String getOriginCity() { return resultOriginCity.getText();}
    public String getOriginState() { return resultOriginState.getText();}
    public String getOriginPostalCode() { return resultOriginPostalCode.getText();}
    public String getOriginCountry() { return resultOriginCountry.getText();}
    public String getOriginEmail() { return resultOriginEmail.getText();}
    public String getOriginPhone() { return resultOriginPhone.getText();}

    // ---------------------------------------------------
    //  Get Contents of Origin Form Fields
    // ---------------------------------------------------
    public String chkOriginName() { return originName.getAttribute("value"); }
    public String chkOriginContactName() { return originContactName.getAttribute("value"); }
    public String chkOriginAddress1() { return originAddress1.getAttribute("value"); }
    public String chkOriginPostalCode() { return originPostalCode.getAttribute("value"); }
    public String chkOriginCity() { return originCity.getAttribute("value"); }
    public String chkOriginEmail() { return originEmail.getAttribute("value"); }
    public String chkOriginPhone() { return originPhone.getAttribute("value"); }

    // ---------------------------------------------------
    //   Return Xpath of Selected WebElement
    // ---------------------------------------------------
    public String lookUpXpath(WebElement thisElement) {
        String desiredXpath = "";
        if (thisElement.equals(continueButton)) { desiredXpath = "//button[@id='nbsBackForwardNavigationContinueButton']";}
        if (thisElement.equals(modalForm)) { desiredXpath = "//div[@class='modal-content']";}
        return desiredXpath;
    }
}
