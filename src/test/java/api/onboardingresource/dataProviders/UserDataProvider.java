package api.onboardingresource.dataProviders;

import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "invalidEmail", parallel = true)
    public static Object[][] invalidEmails(){
        return new Object[][]{
                {"invalid.com"},
                {"invalid@.com"},
                {"@invalid.com"},
                {"invalid@com"},
        };
    }

}
