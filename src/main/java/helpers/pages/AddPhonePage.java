package helpers.pages;

import static org.testng.Assert.*;

public class AddPhonePage extends BasePage<AddPhonePage> {

    private String PAGE_URL = "https://user.staging.estateguru.co/user/onboarding/phone";

    @Override
    public String getPageUrl() {
        return PAGE_URL;
    }

    @Override
    protected void load() {

    }

    @Override
    public void isLoaded() throws Error {
        assertEquals(driver.getCurrentUrl(), getPageUrl());
    }
}
