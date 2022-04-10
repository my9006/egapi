package helpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class BaseApiHelper {

    static final String BASE_URL = "https://user.staging.estateguru.co/api/";

    public static RequestSpecification setUpSpec(final String path) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(path)
                .setContentType(JSON)
                .build();
    }

}
