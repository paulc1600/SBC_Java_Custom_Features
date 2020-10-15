package PomEnvironment.Careers.Pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CareersRecruiterHome extends CareersHome {

    @FindBy(xpath="//a[contains(@href,'candidates')]")
    private WebElement userLink;

    @FindBy(xpath="//button[contains(text(),'Logout')]")
    private WebElement buttonLogout;

    @FindBy(xpath="//button[contains(text(),'My Jobs')]")
    private WebElement buttonMyJobs;

    @FindBy(xpath="//span[contains(@class,'position-center')]")
    private WebElement pageNavBannerText;

    @FindBy(xpath="//button[contains(text(),'Recruit')]")
    private WebElement buttonRecruit;

    @FindBy(xpath="//button[contains(text(),'Careers')]")
    private WebElement buttonCareers;

    @FindBy(xpath="//h4[contains(text(),'New Position')]")
    private WebElement buttonNewPosition;

    public String verifyLoginName() {
        return userLink.getText();
    }

    public void goToRecruitPage() {
        // use bullet-proof click
        click(buttonRecruit);
    }

    public void returnToHomePage() {
        // use bullet-proof click
        click(buttonCareers);
    }

    public void startNewPosition() {
        // use bullet-proof click
        click(buttonNewPosition);
    }

    // When go from Careers list to Recruiter list, page label changes
    public String returnPageLabel(String targetPageLabel) {
        String actPageBanner = "";
        try {
            waitForTextIn(pageNavBannerText, targetPageLabel);
            actPageBanner = pageNavBannerText.getText();
            System.out.println("--- found Page Title: " +  actPageBanner);
            return actPageBanner;
        }
        catch (TimeoutException e) {
            actPageBanner = pageNavBannerText.getText();
            System.out.println("--- Page Banner NOT FOUND! ");
            System.out.println("--- target Page Title: " +  targetPageLabel);
            System.out.println("--- actual Page Title: " +  actPageBanner);
            return actPageBanner;
        }
    }
}
