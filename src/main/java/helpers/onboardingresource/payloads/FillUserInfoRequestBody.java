package helpers.onboardingresource.payloads;

import org.json.JSONObject;

import java.util.*;
import java.util.function.Function;

import static utils.TestUtils.*;

public class FillUserInfoRequestBody {

    private final Map<RequestCreationCombination, Function<String, JSONObject>> MAP = new HashMap<>();

    public static final String USER_NAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String COUNTRY = "country";
    public static final String CITY = "city";
    public static final String ZIP_CODE = "zipCode";
    public static final String CITIZENSHIP = "citizenship";
    public static final String FULL_ADDRESS = "fullAddress";
    public static final String REFERRAL_CODE = "referralCode";
    public static final String CONTACT_COUNTRY = "contactCountry";
    public static final String CONTACT_ADDRESS = "contactAddress";
    public static final String PARTNER_ID = "partnerId";
    public static final String PUBLISHER_ID = "publisherId";
    public static final String PUBLISHER_DATA = "publisherData";

    public enum RequestCreationCombination {
        REQUIRED,
        ALL_FIELDS
    }

    private final Function<String, JSONObject> required = (String email) -> {
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USER_NAME, email);
        requestBody.put(COUNTRY, FAKER.country().name());
        requestBody.put(FULL_ADDRESS, FAKER.address().fullAddress());
        requestBody.put(FIRST_NAME, FAKER.name().firstName());
        requestBody.put(LAST_NAME, FAKER.name().lastName());

        return requestBody;
    };

    private final Function<String, JSONObject> allFields = (String email) -> {
        final JSONObject requestBody = this.required.apply(email);
        requestBody.put(CITY, FAKER.country().capital());
        requestBody.put(ZIP_CODE, FAKER.address().zipCode());
        requestBody.put(CITIZENSHIP, FAKER.country().name());
//        Country below may fail sometime, when the country is like Macedonia, former Yugoslavia, just rerun :)
        requestBody.put(CONTACT_COUNTRY, FAKER.country().name());
        requestBody.put(CONTACT_ADDRESS, FAKER.address().fullAddress());
//        Empty due to absence of context
//        requestBody.put(REFERRAL_CODE, null);
        requestBody.put(PARTNER_ID, "");
        requestBody.put(PUBLISHER_ID, "");
        requestBody.put(PUBLISHER_DATA, "");
        return requestBody;
    };

    {
        MAP.put(RequestCreationCombination.REQUIRED, required);
        MAP.put(RequestCreationCombination.ALL_FIELDS, allFields);
    }

    public JSONObject bodyBuilder(RequestCreationCombination combination, String email) {
        return MAP.get(combination).apply(email);
    }

}
