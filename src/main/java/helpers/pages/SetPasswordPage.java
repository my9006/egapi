package helpers.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SetPasswordPage extends BasePage<SetPasswordPage> {
    private final String PAGE_URL = "https://user.staging.estateguru.co/user/set-password";

    @FindBy(css = "[type='password']")
    private WebElement passwordInputElement;
    @FindBy(css = "[type='submit']")
    private WebElement nextButton;
    @FindBy(css = "[id='mui-1-helper-text']")
    private WebElement passwordError;

    public void fillPassword(final String password) {
        type(passwordInputElement, password);
        click(nextButton);
    }

    public void checkNoPasswordErrorMessage() {
        fillPassword("");
        final String noPasswordErrorMessage = "Please enter a password";
        checkText(passwordError, noPasswordErrorMessage);
    }

    public void checkInvalidPasswordErrorMessage() {
        final String invalidPassword = "123";
        fillPassword(invalidPassword);
        final String invalidPasswordErrorMessage = "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special case character";
        checkText(passwordError, invalidPasswordErrorMessage);
    }

    @Override
    public String getPageUrl() {
        return PAGE_URL + "?token=" + token.get();
    }

    @Override
    protected void load() {
        driver.get(getPageUrl());
    }

    @Override
    protected void isLoaded() throws Error {
        assertEquals(driver.getCurrentUrl(), getPageUrl());
    }
}
