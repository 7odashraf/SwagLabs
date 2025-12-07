package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Logout {

    WebDriver driver;
    WebDriverWait wait;

    public Logout(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement menuBtn(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("react-burger-menu-btn")
        ));
    }

    public WebElement logoutBtn(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("logout_sidebar_link")
        ));
    }

    public void clickMenu(){
        menuBtn().click();
    }

    public void clickLogout(){
        logoutBtn().click();
    }
}
