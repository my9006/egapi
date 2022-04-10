package helpers.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.testng.Assert.*;

public class RegisterPage extends BasePage<RegisterPage> {
    private final String PAGE_URL = "https://user.staging.estateguru.co/user/auth/register";

    @FindBy(css = "[name='email']")
    private WebElement emailElement;
    @FindBy(css = "[type='submit']")
    private WebElement continueButton;
    @FindBy(css = "[id*='helper-text']")
    private WebElement fieldErrorElement;

    public void registerUser(String email) {
        type(emailElement, email);
        click(continueButton);
        wait.until(ExpectedConditions.urlContains("/verify-email"));
    }

    public void checkRequiredField() {
        clearText(emailElement);
        click(continueButton);
        assertTrue(fieldErrorElement.isDisplayed());
        final String requiredFieldErrorMessage = "Please enter your email address";
        checkText(fieldErrorElement, requiredFieldErrorMessage);
    }

    public void checkInvalidEmailError(String invalidEmail) {
        clearText(emailElement);
        type(emailElement, invalidEmail);
        click(continueButton);
        final String invalidEmailErrorText = "Please enter a valid email";
        checkText(fieldErrorElement, invalidEmailErrorText);
    }

    public void checkNonUniqueEmail(String nonUniqueEmail) {
        clearText(emailElement);
        type(emailElement, nonUniqueEmail);
        click(continueButton);
        final String nonUniqueEmailErrorText = "Email is not unique";
        checkText(fieldErrorElement, nonUniqueEmailErrorText);
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
    protected void isLoaded() throws Error {
        assertEquals(driver.getCurrentUrl(), getPageUrl());
    }
}
