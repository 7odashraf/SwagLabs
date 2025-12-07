package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;
    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginBtn = By.id("login-button");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    // Actions
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }

    public String error_Message_Test() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    // Login Helper Function
    public void Login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}
