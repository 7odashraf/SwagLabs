package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPage {

    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    // -------- Inputs --------
    public WebElement firstnameInput() {
        return driver.findElement(By.id("first-name"));
    }

    public WebElement lastnameInput() {
        return driver.findElement(By.id("last-name"));
    }

    public WebElement zipCodeInput() {
        return driver.findElement(By.id("postal-code"));
    }

    // -------- Buttons --------
    public void clickContinue() {
        driver.findElement(By.id("continue")).click();
    }

    public void clickFinish() {
        driver.findElement(By.id("finish")).click();
    }

    // -------- Error Message --------
    public String getErrorMessage() {
        try {
            return driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        } catch(NoSuchElementException e) {
            return "";
        }
    }
}
