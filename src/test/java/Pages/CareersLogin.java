package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CareersLogin extends Pages.Page {
    // Get page factory and common project routines from Pages.page

    @FindBy(xpath="//button[contains(text(),'Careers')]")
    private WebElement buttonCareers;

    @FindBy(xpath="//button[contains(text(),'Apply')]")
    private WebElement buttonMyApply;

    @FindBy(xpath="//input[@placeholder='Please enter an Email']")
    private WebElement username;

    @FindBy(xpath="//input[@placeholder='Please enter a Password']")
    private WebElement password;

    @FindBy(xpath="//button[@id='loginButton']")
    private WebElement buttonSubmit;

    public void fillInLoginForm(String unameProvided, String passwordProvided) {
        username.sendKeys(unameProvided);
        password.sendKeys(passwordProvided);
    }

    public void submitLoginForm() {
        // use bullet-proof click
        click(buttonSubmit);
    }
}
