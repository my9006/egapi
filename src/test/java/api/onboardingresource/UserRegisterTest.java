package api.onboardingresource;

import api.onboardingresource.dataProviders.UserDataProvider;
import helpers.onboardingresource.OnboardingHelper;
import helpers.onboardingresource.payloads.RegisterUserRequestBody;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static utils.TestUtils.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class UserRegisterTest {

    private RegisterUserRequestBody registerUserRequestBody;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        registerUserRequestBody = new RegisterUserRequestBody();
    }

    @Test(testName = "Register User")
    public void registerUserWithValidEmail() {
        final String email = FAKER.name().username() + "@eg.com";
        final JSONObject requestBody = registerUserRequestBody.bodyBuilder(email);
        OnboardingHelper.userRegister(requestBody)
                .then()
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/userRegisterSchema.json"))
                .body("status", is(SC_OK));
    }

    @Test(testName = "Register existing user")
    public void registerExistingUser() {
        final String email = FAKER.name().username() + "@eg.com";
        final JSONObject requestBody = registerUserRequestBody.bodyBuilder(email);
        OnboardingHelper.userRegister(requestBody)
                .then()
                .statusCode(SC_OK);
//It should be SC_CONFLICT
        OnboardingHelper.userRegister(requestBody)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("status", is(SC_BAD_REQUEST))
                .body("errors[0].code", is("email.not.unique"))
                .body("errors[0].field", is("email"));
    }

    @Test(testName = "Register user with no body")
    public void registerUserWithNoBorder() {
        OnboardingHelper.userRegister(new JSONObject())
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("status", is(SC_BAD_REQUEST))
                .body("errors.code", hasItem("not.null"))
                .body("errors.field", hasItem("email"));
    }

    @Test(testName = "Register with invalid email", dataProviderClass = UserDataProvider.class, dataProvider = "invalidEmail")
    public void registerUserInvalidEmail(String email) {
        final JSONObject requestBody = registerUserRequestBody.bodyBuilder(email);
        OnboardingHelper.userRegister(requestBody)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("status", is(SC_BAD_REQUEST))
                .body("errors[0].code", is("email.format.invalid"))
                .body("errors[0].field", is("email"));
    }

}
