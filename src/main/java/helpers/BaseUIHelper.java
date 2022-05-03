package helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static configurations.Config.*;

public class BaseUIHelper {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    @SneakyThrows
    public static WebDriver getDriver() {
        if (driverThread.get() == null) {
            if(REMOTE.equals(true)){
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("os", "Windows");
                caps.setCapability("os_version", "10");
                caps.setCapability("browser", "Chrome");
                caps.setCapability("browser_version", "100");
                caps.setCapability("browserstack.networkLogs", true);
//                caps.setCapability("resolution", "1280x800");
                driverThread.set(new RemoteWebDriver(new URL(HUB_URL), caps));
            }else {
                WebDriverManager.chromedriver().setup();
                driverThread.set(new ChromeDriver());
//                driverThread.get().manage().window().maximize();
            }
        }
        driverThread.get().manage().window().maximize();
        return driverThread.get();
    }

    public static void quitDriver() {
        driverThread.get().quit();
        driverThread.remove();
    }

}
