package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CareersUserHome extends CareersHome {
    @FindBy(xpath="//button[contains(text(),'Login')]")
    private WebElement buttonLogin;

    @FindBy(xpath="//button[contains(text(),'Apply')]")
    private WebElement buttonApply;

    public void goToLoginPage() {
        // use bullet-proof click
        click(buttonLogin);
    }
}
