package helpers.pages;

import helpers.BaseUIHelper;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.NoSuchElementException;

import static org.testng.Assert.*;

public abstract class BasePage<T extends LoadableComponent<T>> extends LoadableComponent<T> {
    @Getter
    private final static Duration TIMEOUT = Duration.ofSeconds(60);
    private final static Duration SLEEP = Duration.ofSeconds(3);
    protected WebDriver driver;
    protected Wait<WebDriver> wait;

    public BasePage() {
        this.driver = BaseUIHelper.getDriver();
        this.wait = new FluentWait<>(this.driver)
                .withTimeout(TIMEOUT)
                .pollingEvery(SLEEP)
                .ignoring(Exception.class);
        this.driver.manage().timeouts().pageLoadTimeout(TIMEOUT);
        PageFactory.initElements(this.driver, this);
    }

    public void openPage() {
        this.get();
    }

    protected WebElement waitForElement(WebElement element) {
        nullCheck(element);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    protected WebElement click(WebElement button) {
        nullCheck(button);
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        return button;
    }

    public void clearText(WebElement textField) {
        waitForElement(textField);
        textField.clear();
    }

    protected void type(WebElement element, String text) {
        waitForElement(element);
        element.sendKeys(text);
    }

    protected WebElement scrollIntoView(WebElement element) {
        waitForElement(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    protected void clickOut() {
        driver.findElement(By.xpath("//html")).click();
    }

    protected void checkText(WebElement element, String text) {
        waitForElement(element);
        assertEquals(element.getText(), text);
    }

    private void nullCheck(WebElement element) {
        if (element == null) {
            throw new NoSuchElementException("Provided element is null");
        }
    }

    public abstract String getPageUrl();

}
