package ui;

import helpers.flows.OnboardingFlows;
import helpers.onboardingresource.payloads.FillUserInfoRequestBody;
import helpers.pages.AddPhonePage;
import helpers.pages.BasePage;
import helpers.pages.PersonalInformationPage;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestUtils;

import static helpers.onboardingresource.payloads.FillUserInfoRequestBody.RequestCreationCombination.ALL_FIELDS;

public class PersonalInformationPageTest extends BaseUITest {

    private OnboardingFlows onboardingFlows;

    @BeforeClass
    public void setUp() {
        onboardingFlows = new OnboardingFlows();
    }

    @Test
    public void fillInfo() {
        final String email = TestUtils.getRandomEmail();
        BasePage.token.set(onboardingFlows.registerAndEnableUser(email));
        final JSONObject userDetails = new FillUserInfoRequestBody().bodyBuilder(ALL_FIELDS, email);
        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        personalInformationPage.openPage();
        personalInformationPage.fillUserDetails(userDetails);
        final AddPhonePage addPhonePage = new AddPhonePage();
        addPhonePage.isLoaded();
    }
}
