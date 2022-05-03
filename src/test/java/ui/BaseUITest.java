package ui;

import configurations.Config;
import configurations.StackHelper;
import helpers.BaseUIHelper;
import org.testng.annotations.*;


public class BaseUITest {

    @AfterMethod(alwaysRun = true)
    protected void tearDown() {
        BaseUIHelper.quitDriver();
    }

    @BeforeSuite
    public void startInstanceRun() {
        if (Config.REMOTE.equals("true")){
//            StackHelper.startInstance();
        }
    }

    @AfterSuite
    public void stopInstanceRun() {
        if (Config.REMOTE.equals("true")){
            StackHelper.stopInstance();
        }
    }

}
