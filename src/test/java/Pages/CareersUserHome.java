package Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static definitions.ATestToolBox.careersData;
import static definitions.ATestToolBox.teDataSource;

public class CareersUserHome extends CareersHome {
    @FindBy(xpath="//button[contains(text(),'Login')]")
    private WebElement buttonLogin;

    @FindBy(xpath="//button[contains(text(),'Logout')]")
    private WebElement buttonLogout;

    @FindBy(xpath="//button[contains(text(),'Apply')]")
    private WebElement buttonApply;

    @FindBy(xpath="//button[contains(text(),'My Jobs')]")
    private WebElement buttonMyJobs;

    @FindBy(xpath="//div[contains(@class,'card-body')]//label[contains(text(),'Title')]/../span")
    private WebElement jobTileTitle;

    @FindBy(xpath="//button[contains(text(),'Withdraw Application')]")
    private WebElement buttonJobWithdraw;

    // dynamic elements -- My Jobs List
    private WebElement jobPositionCard(String title) {
        return getByXpath("//li[contains(@class,'list-item')]//h4[text()='" + title + "']");
    }

    private List<WebElement> allJobPositionCards(String title) {
        return getAllByXpath("//li[contains(@class,'list-item')]//h4[text()='" + title + "']");
    }


    public String[] getCareersUserData(String userProvided) {
        String[] userCredentialsArray = new String[3];
        if (teDataSource.equalsIgnoreCase("file")) {
            userCredentialsArray[0] = careersData.get("email");
            userCredentialsArray[1] = careersData.get("password");
            userCredentialsArray[2] = careersData.get("name");
        } else {
            if (userProvided.equalsIgnoreCase("recruiter")) {
                userCredentialsArray[0] = "owen@example.com";
                userCredentialsArray[1] = "welcome";
                userCredentialsArray[2] = "Owen Kelley";
            } else {
                throw new RuntimeException("Unsupported user profile: " + userProvided);
            }
        }
        return userCredentialsArray;
    }

    public void goToLoginPage() {
        // use bullet-proof click
        click(buttonLogin);
    }

    public void displayMyJobsList() {
        // use bullet-proof click
        click(buttonMyJobs);
    }

    public boolean isJobPositionVisible(String title) {
        waitForVisible(jobPositionCard(title));
        try {
            return jobPositionCard(title).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isJobPositionGone(String title) {
        boolean notThere = false;
        try {
            notThere = !jobPositionCard(title).isDisplayed();
            return notThere;
        } catch (NoSuchElementException e) {
            notThere = true;
            return notThere;
        }
    }

    public String accessMyJobDetails(String title) {
        waitForVisible(jobPositionCard(title));
        WebElement myJob = jobPositionCard(title);
        click(myJob);   // use bullet-proof click
        return jobTileTitle.getText();
    }

    public void withdrawApplication() {
        click(buttonJobWithdraw);
    }

    public void goLogOut() {
        // use bullet-proof click
        click(buttonLogout);
    }
}
