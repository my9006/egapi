package helpers.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.testng.Assert.*;

public class ConfirmEmailPage extends BasePage<ConfirmEmailPage> {
    private final String PAGE_URL = "https://user.staging.estateguru.co/user/auth/register/verify-email";

    @FindBy(css = "[class*='MuiBox-root']>[class*='MuiTypography-h4']")
    private WebElement emailElement;

    public void checkConfirmationEmail(String email) {
        checkText(emailElement, email);
    }

    @Override
    public String getPageUrl() {
        return PAGE_URL;
    }

    @Override
    protected void load() {
        driver.get(getPageUrl());
    }

    @Override
    public void isLoaded() throws Error {
        assertEquals(driver.getCurrentUrl(), getPageUrl());
    }
}
