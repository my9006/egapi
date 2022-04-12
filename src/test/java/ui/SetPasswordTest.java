package ui;

import helpers.flows.OnboardingFlows;
import helpers.pages.BasePage;
import helpers.pages.SetPasswordPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static utils.TestUtils.*;

public class SetPasswordTest extends BaseUITest {

    private OnboardingFlows onboardingFlows;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        onboardingFlows = new OnboardingFlows();
    }

    @BeforeMethod(alwaysRun = true)
    public void init() {
        final String email = getRandomEmail();
        BasePage.token.set(onboardingFlows.registerUser(email));
    }

    @Test(testName = "Set password")
    public void checkSetValidPassword() {
        final SetPasswordPage setPasswordPage = new SetPasswordPage();
        setPasswordPage.openPage();
        final String validPassword = "Qw!123456";
        setPasswordPage.fillPassword(validPassword);
    }

    @Test(testName = "Password required field")
    public void checkPasswordRequired() {
        final SetPasswordPage setPasswordPage = new SetPasswordPage();
        setPasswordPage.openPage();
        setPasswordPage.checkNoPasswordErrorMessage();
    }

    @Test(testName = "Invalid password")
    public void checkInvalidPassword() {
        final SetPasswordPage setPasswordPage = new SetPasswordPage();
        setPasswordPage.openPage();
        setPasswordPage.checkInvalidPasswordErrorMessage();
    }
}
