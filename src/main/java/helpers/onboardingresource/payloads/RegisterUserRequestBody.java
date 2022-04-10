package helpers.onboardingresource.payloads;

import org.json.JSONObject;

public class RegisterUserRequestBody {

    public static final String EMAIL = "email";

    public final JSONObject bodyBuilder(String email) {
        final JSONObject requestBody = new JSONObject();
        requestBody.put(EMAIL, email);
        return requestBody;
    }

}
