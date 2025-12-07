package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InventoryPage {

    WebDriver driver;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }
    // Locators
    private By inventoryItem = By.cssSelector(".inventory_item");
    private By cartBadge = By.className("shopping_cart_badge");

    // Get all inventory items
    public List<WebElement> getInventoryItems() {
        return driver.findElements(inventoryItem);
    }

    public String getItemName(WebElement item) {
        return item.findElement(By.cssSelector(".inventory_item_name")).getText();
    }

    public String getItemDescription(WebElement item) {
        return item.findElement(By.cssSelector(".inventory_item_desc")).getText();
    }

    public String getItemPrice(WebElement item) {
        return item.findElement(By.cssSelector(".inventory_item_price")).getText();
    }

    // Add/Remove buttons
    public void clickAddToCart(String addToCartId) {
        driver.findElement(By.id(addToCartId)).click();
    }

    public void clickRemoveFromCart(String removeFromCartId) {
        driver.findElement(By.id(removeFromCartId)).click();
    }
    public void openInventory() {
        driver.get("https://www.saucedemo.com/inventory.html");
    }

    public String getCartBadge() {
        return driver.findElement(cartBadge).getText();
    }
}
