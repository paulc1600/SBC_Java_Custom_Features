package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static support.TestContext.getDriver;

public class UpsHomePage {
    // ---------------------------------------------------
    //  constructor
    // ---------------------------------------------------
    public UpsHomePage() {
        // Makes the lazy association annotation possible
        PageFactory.initElements(getDriver(), this);
        url = "https://www.ups.com/us/en/Home.page";
        title = "Global Shipping & Logistics Services | UPS - United States";
    }

    // ---------------------------------------------------
    //  fields inputs
    // ---------------------------------------------------
    private String url;
    private String title;

    @FindBy(xpath = "//a[@id='ups-menuLinks2']")
    private WebElement shippingLink;

    @FindBy(xpath = "//a[@id='ups-menuLinks1']")
    private WebElement trackingLink;

    // ---------------------------------------------------
    //                 shipping options
    // ---------------------------------------------------
    @FindBy(xpath = "//a[contains(@href,'ups.com/ship?')][contains(text(),'Create')]")
    private WebElement createAShipment;
    @FindBy(xpath = "//a[contains(text(),'Find a Shipping Service')]")
    private WebElement findShippingService;
    @FindBy(xpath = "//a[contains(text(),'Calculate Time & Cost')]")
    private WebElement calculateTimeAndCost;
    @FindBy(xpath = "//ul[@class='ups-menu_links']//a[contains(text(),'Schedule a Pickup')]")
    private WebElement ScheduleAPickup;
    @FindBy(xpath = "//a[contains(text(),'Manage Online Orders:')]")
    private WebElement manageOnlineOrders;
    @FindBy(xpath = "//a[contains(text(),'Create a Return')]")
    private WebElement createAReturn;
    @FindBy(xpath = "//a[contains(text(),'Explore All Shipping')]")
    private WebElement exploreAllShipping;

    // ---------------------------------------------------
    //  methods
    // ---------------------------------------------------
    public void open() {
        getDriver().get(url);
    }

    // ---------------------------------------------------
    //                Work with Links
    // ---------------------------------------------------
    public void upsTracking() {
        trackingLink.click();
    }

    public void upsShipping() {
        shippingLink.click();
    }

    // ---------------------------------------------------
    //                 shipping links
    // ---------------------------------------------------
    public void goCreateAShipment() {
        createAShipment.click();
    }
}
