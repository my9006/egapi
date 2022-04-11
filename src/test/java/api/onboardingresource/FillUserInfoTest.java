package api.onboardingresource;

import helpers.flows.OnboardingFlows;
import helpers.onboardingresource.OnboardingHelper;
import helpers.onboardingresource.payloads.FillUserInfoRequestBody;
import org.json.JSONObject;
import org.testng.annotations.*;

import java.util.UUID;

import static utils.TestUtils.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

import static helpers.onboardingresource.payloads.FillUserInfoRequestBody.RequestCreationCombination.*;
import static org.apache.http.HttpStatus.*;

public class FillUserInfoTest {

    private FillUserInfoRequestBody fillUserInfoRequestBody;
    private OnboardingFlows onboardingFlows;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        fillUserInfoRequestBody = new FillUserInfoRequestBody();
        onboardingFlows = new OnboardingFlows();
    }

    @Test(testName = "Fill user with required fields")
    public void fillUserInfoRequired() {
        final String email = getRandomEmail();
        onboardingFlows.registerUser(email);
        final String token = onboardingFlows.enableUser(email);

        final JSONObject userInfoBody = fillUserInfoRequestBody.bodyBuilder(REQUIRED, email);

        OnboardingHelper.fillUserInfo(token, userInfoBody)
                .then()
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/userInfoSchema.json"))
                .body("username", is(email))
                .body("email", is(email))
                .body("firstName", is(userInfoBody.getString("firstName")))
                .body("lastName", is(userInfoBody.getString("lastName")))
                .body("country", is(userInfoBody.getString("country")))
                .body("fullAddress", is(userInfoBody.get("fullAddress")));
    }

    @Test(testName = "fill user info all fields")
    public void fillUserAllFields() {
        final String email = getRandomEmail();
        final String token = onboardingFlows.registerAndEnableUser(email);
        final JSONObject userInfoBody = fillUserInfoRequestBody.bodyBuilder(ALL_FIELDS, email);

        OnboardingHelper.fillUserInfo(token, userInfoBody)
                .then()
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/userInfoSchema.json"))
                .body("username", is(email))
                .body("email", is(email))
                .body("firstName", is(userInfoBody.getString("firstName")))
                .body("lastName", is(userInfoBody.getString("lastName")))
                .body("country", is(userInfoBody.getString("country")))
                .body("fullAddress", is(userInfoBody.get("fullAddress")))
                .body("city", is(userInfoBody.getString("city")))
                .body("zipCode", is(userInfoBody.getString("zipCode")))
                .body("citizenship", is(userInfoBody.getString("citizenship")))
                .body("contactCountry", is(userInfoBody.getString("contactCountry")))
                .body("contactAddress", is(userInfoBody.getString("contactAddress")));
    }

    @Test(testName = "User info with invalid token")
    public void fillWithInvalidToken() {
        final String email = getRandomEmail();
        onboardingFlows.registerAndEnableUser(email);
        final String token = "in.valid.token";

        final JSONObject userInfoBody = fillUserInfoRequestBody.bodyBuilder(REQUIRED, email);

        OnboardingHelper.fillUserInfo(token, userInfoBody)
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("message", is("Access is denied"));
    }

    @Test(testName = "User info with no username")
    public void fillWithoutUsername() {
        final String email = getRandomEmail();
        final String token = onboardingFlows.registerAndEnableUser(email);

        final JSONObject userInfoBody = fillUserInfoRequestBody.bodyBuilder(REQUIRED, null);

        OnboardingHelper.fillUserInfo(token, userInfoBody)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("validation.failed"))
                .body("errors[0].field", is("username"));
    }

    //This test is flaky and probably there's an issue
    @Test(testName = "Mismatching username and token")
    public void fillWithMismatchingCredentials() {
        final String email1 = getRandomEmail();
        final String email2 = getRandomEmail();

        final String token1 = onboardingFlows.registerAndEnableUser(email1);
        final String token2 = onboardingFlows.registerAndEnableUser(email2);

        final JSONObject user1InfoBody = fillUserInfoRequestBody.bodyBuilder(REQUIRED, email1);
//Is this ok?
        OnboardingHelper.fillUserInfo(token2, user1InfoBody)
                .then()
                .statusCode(SC_OK);
    }
}
