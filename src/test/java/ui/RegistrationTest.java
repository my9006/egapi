package ui;

import api.onboardingresource.dataProviders.UserDataProvider;
import helpers.flows.OnboardingFlows;
import helpers.pages.ConfirmEmailPage;
import helpers.pages.RegisterPage;
import org.testng.annotations.Test;
import utils.TestUtils;

public class RegistrationTest extends BaseUITest {

    @Test(testName = "Register user with valid Email")
    public void registerUser() {
        final RegisterPage registerPage = new RegisterPage();
        registerPage.openPage();
        final String validEmail = TestUtils.getRandomEmail();
        registerPage.registerUser(validEmail);
        final ConfirmEmailPage confirmEmailPage = new ConfirmEmailPage();
        confirmEmailPage.isLoaded();
        confirmEmailPage.checkConfirmationEmail(validEmail);
    }

    @Test(testName = "check required field on registration")
    public void registerWithNoEmail() {
        final RegisterPage registerPage = new RegisterPage();
        registerPage.openPage();
        registerPage.checkRequiredField();
    }

    @Test(testName = "check invalid email")
    public void registerInvalidEmail() {
        final RegisterPage registerPage = new RegisterPage();
        registerPage.openPage();
        final String invalidEmail = TestUtils.getRandomItemFromDataProvider(UserDataProvider.invalidEmails());
        registerPage.checkInvalidEmailError(invalidEmail);
    }

    @Test(testName = "check non unique email")
    public void registerWithNonUniqueEmail() {
        final RegisterPage registerPage = new RegisterPage();
        registerPage.openPage();
        final String nonUniqueEmail = TestUtils.getRandomEmail();
        final OnboardingFlows onboardingFlows = new OnboardingFlows();
        onboardingFlows.registerUser(nonUniqueEmail);
        registerPage.checkNonUniqueEmail(nonUniqueEmail);
    }

}
