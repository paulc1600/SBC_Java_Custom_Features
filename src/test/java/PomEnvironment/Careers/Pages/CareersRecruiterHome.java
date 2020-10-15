package Pages.Careers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CareersRecruiterHome extends CareersHome {

    @FindBy(xpath="//a[contains(@href,'candidates')]")
    private WebElement userLink;

    @FindBy(xpath="//button[contains(text(),'Logout')]")
    private WebElement buttonLogout;

    @FindBy(xpath="//button[contains(text(),'My Jobs')]")
    private WebElement buttonMyJobs;

    @FindBy(xpath="//button[contains(text(),'Recruit')]")
    private WebElement buttonRecruit;

    @FindBy(xpath="//h4[contains(text(),'New Position')]")
    private WebElement buttonNewPosition;

    public String verifyLoginName() {
        return userLink.getText();
    }

    public void goToRecruitPage() {
        // use bullet-proof click
        click(buttonRecruit);
    }

    public void startNewPosition() {
        // use bullet-proof click
        click(buttonNewPosition);
    }
}
