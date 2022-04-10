package utils;

import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.core.DescribedAs;
import org.json.JSONObject;
import org.openqa.selenium.interactions.Interaction;

import static org.hamcrest.Matchers.*;

public class DescribedMatchers {

    public static Matcher<Integer> matchDescribedBy(String actionDescription, JSONObject request,
                                                    Response response, int expectedStatusCode) {
        return DescribedAs.describedAs("\n***" + actionDescription + "***\n"
                        + request + "\n***RESPONSE***\n"
                        + response.asPrettyString(),
                is(expectedStatusCode));
    }

}
