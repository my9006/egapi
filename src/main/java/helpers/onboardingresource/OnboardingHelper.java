package helpers.onboardingresource;

import helpers.BaseApiHelper;
import helpers.TokenFormatter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class OnboardingHelper extends BaseApiHelper {

    final static RequestSpecification requestSpecificationUser = setUpSpec("user/");
    final static RequestSpecification requestSpecificationTest = setUpSpec("test/");

    public static Response userRegister(final JSONObject body) {
        return given()
                .spec(requestSpecificationUser)
                .body(body.toString())
                .post("register");
    }

    public static Response getGuestToken(final String email) {
        return given()
                .spec(requestSpecificationTest)
                .queryParam("email", email)
                .get("/guest/token");
    }

    public static Response enableUser(final JSONObject body) {
        return given()
                .spec(requestSpecificationUser)
                .body(body.toString())
                .post("/enable");
    }

    public static Response fillUserInfo(final String token, final JSONObject body) {
        return given()
                .spec(requestSpecificationUser)
                .header("Cookie", TokenFormatter.cookieTokenFormatter(token))
                .body(body.toString())
                .post("/info");
    }

}
