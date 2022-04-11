package helpers.flows;

import helpers.onboardingresource.OnboardingHelper;
import helpers.onboardingresource.payloads.EnableUserRequestBody;
import helpers.onboardingresource.payloads.RegisterUserRequestBody;
import io.restassured.response.Response;
import org.json.JSONObject;
import utils.DescribedMatchers;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class OnboardingFlows {

    private final RegisterUserRequestBody registerUserRequestBody = new RegisterUserRequestBody();
    private final EnableUserRequestBody enableUserRequestBody = new EnableUserRequestBody();

    public String registerUser(final String email) {
        final JSONObject requestBody = registerUserRequestBody.bodyBuilder(email);
        final Response registrationResponse = OnboardingHelper.userRegister(requestBody);
        registrationResponse
                .then()
                .statusCode(DescribedMatchers.matchDescribedBy("Register User",
                        requestBody, registrationResponse, SC_OK));
        return getGuestUserToken(email);
    }

    public String registerAndEnableUser(final String email) {
        registerUser(email);
        return enableUser(email);
    }

    public String getGuestUserToken(final String email) {
        final Response getGuestTokenResponse = OnboardingHelper.getGuestToken(email);
        getGuestTokenResponse
                .then()
                .statusCode(DescribedMatchers
                        .matchDescribedBy("Get guest user token", "email",
                                email, getGuestTokenResponse, SC_OK));
        return getGuestTokenResponse.jsonPath().getString("token");
    }

    public String enableUser(String email) {
        final String token = getGuestUserToken(email);
        final JSONObject requestBody = enableUserRequestBody.bodyBuilder(token);
        final Response enableUserResponse = OnboardingHelper.enableUser(requestBody);
        final int numberOfCookieTokenSubparts = 3;
        enableUserResponse
                .then()
                .statusCode(DescribedMatchers
                        .matchDescribedBy("Enable user", requestBody, enableUserResponse, SC_OK))
                .body("token.split(\"[.]\").length", is(numberOfCookieTokenSubparts));
        return enableUserResponse.jsonPath().getString("token");
    }
}
