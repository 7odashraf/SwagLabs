package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {

    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // -------- Cart Badge Number --------
    public int getCartBadgeNumber() {
        List<WebElement> badgeElements = driver.findElements(By.className("shopping_cart_badge"));
        if (badgeElements.isEmpty()) return 0; // Cart is empty
        return Integer.parseInt(badgeElements.get(0).getText());
    }

    // -------- Get Last Item in Cart --------
    public WebElement getLastItemFromCart() {
        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_item"));
        if (cartItems.isEmpty()) {
            throw new NoSuchElementException("The cart is empty, no items to retrieve.");
        }
        return cartItems.get(cartItems.size() - 1);
    }

    // -------- Buttons --------
    public void clickShoppingCart() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.shopping_cart_link"))).click();
    }

    public void clickContinueShopping() {
        driver.findElement(By.id("continue-shopping")).click();
    }

    public void clickCheckout() {
        driver.findElement(By.id("checkout")).click();
    }

    // -------- Remove item by button ID --------
    public void removeItemById(String removeBtnId) {
        try {
            driver.findElement(By.id(removeBtnId)).click();

        }catch (NoSuchElementException e){

        }
    }

    // -------- Check if item exists in cart by name --------
    public boolean isItemInCart(String itemName) {
        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_item"));
        for (WebElement cartItem : cartItems) {
            if (cartItem.findElement(By.cssSelector(".inventory_item_name")).getText().equals(itemName)) {
                return true;
            }
        }
        return false;
    }
}
