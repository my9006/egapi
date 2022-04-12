package helpers.pages;


import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.testng.Assert.*;

public class PersonalInformationPage extends BasePage<PersonalInformationPage> {
    private final String PAGE_URL = "https://user.staging.estateguru.co/user/onboarding/info";
    private final String DOMAIN = ".estateguru.co";

    @FindBy(css = "[id='mui-1']")
    private WebElement firstNameElement;
    @FindBy(css = "[id='mui-2']")
    private WebElement lastNameElement;
    @FindBy(css = "[id='mui-4']")
    private WebElement citizenshipElement;
    @FindBy(css = "[name='country']")
    private WebElement countryElement;
    @FindBy(css = "[id='mui-8']")
    private WebElement fullAddressElement;
    @FindBy(css = "[type='checkbox']")
    private WebElement differentContactAddressCheckbox;
    @FindBy(css = "[name='contactCountry']")
    private WebElement contactCountryElement;
    @FindBy(css = "[name='contactAddress']")
    private WebElement contactAddressElement;
    @FindBy(css = "[[id='mui-10']]")
    private WebElement referralCodeElement;
    @FindBy(css = "[type='submit']")
    private WebElement nextButton;

    public void fillUserDetails(final JSONObject userDetails) {
        type(firstNameElement, userDetails.getString("firstName"));
        type(lastNameElement, userDetails.getString("lastName"));
        typeWithSearch(citizenshipElement, "Albania");
        type(fullAddressElement, userDetails.getString("fullAddress"));
        click(nextButton);
        wait.until(ExpectedConditions.urlContains("phone"));
    }


    @Override
    public String getPageUrl() {
        return PAGE_URL;
    }

    @Override
    protected void load() {
        final String[] tokenArray = BasePage.token.get().split("[.]");

        final Cookie cookieJclaims = new Cookie.Builder("JCLAIMS", tokenArray[1]).domain(DOMAIN).build();
        final Cookie cookieJhead = new Cookie.Builder("JHEAD", tokenArray[0]).domain(DOMAIN).build();
        final Cookie cookieJsig = new Cookie.Builder("JSIG", tokenArray[2]).domain(DOMAIN).build();

//        Normally this is handled in other way
        driver.get("https://user.staging.estateguru.co/user/auth/register");

        driver.manage().addCookie(cookieJclaims);
        driver.manage().addCookie(cookieJhead);
        driver.manage().addCookie(cookieJsig);

        driver.get(getPageUrl());
    }

    @Override
    protected void isLoaded() throws Error {
        assertEquals(driver.getCurrentUrl(), getPageUrl());
    }
}
