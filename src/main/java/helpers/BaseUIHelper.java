package helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseUIHelper {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driverThread.get() == null) {
            WebDriverManager.chromedriver().setup();
            driverThread.set(new ChromeDriver());
            driverThread.get().manage().window().maximize();
        }
        return driverThread.get();
    }

    public static void quitDriver() {
        driverThread.get().quit();
        driverThread.remove();
    }

}
