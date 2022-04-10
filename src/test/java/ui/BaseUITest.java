package ui;

import helpers.BaseUIHelper;
import org.testng.annotations.AfterMethod;

public class BaseUITest {

    @AfterMethod(alwaysRun = true)
    protected void tearDown() {
        BaseUIHelper.quitDriver();
    }

}
