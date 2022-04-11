package helpers.onboardingresource.payloads;

import org.json.JSONObject;

public class EnableUserRequestBody {

    public static final String TOKEN = "token";
    public static final String PASSWORD = "password";

    public JSONObject bodyBuilder(String token) {
        final JSONObject requestBody = new JSONObject();
        requestBody.put(TOKEN, token);
        requestBody.put(PASSWORD, "Qw!123456");
        return requestBody;
    }

}
