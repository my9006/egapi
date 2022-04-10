package helpers.flows;

import helpers.onboardingresource.OnboardingHelper;
import helpers.onboardingresource.payloads.RegisterUserRequestBody;
import io.restassured.response.Response;
import org.json.JSONObject;
import utils.DescribedMatchers;

import static org.apache.http.HttpStatus.*;

public class RegistrationFlows {

    private final RegisterUserRequestBody registerUserRequestBody = new RegisterUserRequestBody();

    public void registerUser(final String email){
        final JSONObject requestBody= registerUserRequestBody.bodyBuilder(email);
        final Response registrationResponse = OnboardingHelper.userRegister(requestBody);
        registrationResponse.then().statusCode(DescribedMatchers.matchDescribedBy("Register User",
                requestBody, registrationResponse, SC_OK));
    }

}
