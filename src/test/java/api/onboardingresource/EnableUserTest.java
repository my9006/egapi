package api.onboardingresource;

import helpers.flows.OnboardingFlows;
import helpers.onboardingresource.OnboardingHelper;
import helpers.onboardingresource.payloads.EnableUserRequestBody;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.*;

import static utils.TestUtils.*;
import static org.apache.http.HttpStatus.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

public class EnableUserTest {

    private OnboardingFlows onboardingFlows;
    private EnableUserRequestBody enableUserRequestBody;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        onboardingFlows = new OnboardingFlows();
        enableUserRequestBody = new EnableUserRequestBody();
    }

    @Test(testName = "enable user")
    public void enableUser() {
        final String email = getRandomEmail();
        final String token = onboardingFlows.registerUser(email);

        final JSONObject enableBody = enableUserRequestBody.bodyBuilder(token);

        OnboardingHelper.enableUser(enableBody)
                .then()
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/userEnable.json"))
                .body("status", is(SC_OK))
                .body("token.split(\"[.]\").length", is(3));
    }

    @Test(testName = "Enable enabled user")
    public void enableEnabledUser() {
        final String email = getRandomEmail();
        final String token = onboardingFlows.registerUser(email);
        onboardingFlows.enableUser(email);

        final JSONObject enabledUserBody = enableUserRequestBody.bodyBuilder(token);

        OnboardingHelper.enableUser(enabledUserBody)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("details", is("Not found"));
    }

    @Test(testName = "Enable with bad password")
    public void enableWithBadPassword() {
        final String email = getRandomEmail();
        final String token = onboardingFlows.registerUser(email);
        final JSONObject enabledUserBody = enableUserRequestBody.bodyBuilder(token);
        final String badPassword = "1";
        enabledUserBody.put(EnableUserRequestBody.PASSWORD, badPassword);
//No password validation???
        OnboardingHelper.enableUser(enabledUserBody)
                .then()
                .statusCode(SC_OK);
    }

}
