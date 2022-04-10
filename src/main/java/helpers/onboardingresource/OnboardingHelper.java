package helpers.onboardingresource;

import helpers.BaseApiHelper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class OnboardingHelper extends BaseApiHelper {

    final static RequestSpecification requestSpecification = setUpSpec("user/");

    public static Response userRegister(final JSONObject body) {
        return given()
                .spec(requestSpecification)
                .body(body.toString())
                .post("register");
    }

}
