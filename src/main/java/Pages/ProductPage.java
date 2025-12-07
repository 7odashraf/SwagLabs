package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage {

    WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators

    private By productName = By.cssSelector(".inventory_details_name");
    private By productDescription = By.cssSelector(".inventory_details_desc");
    private By productPrice = By.cssSelector(".inventory_details_price");
    private By productImage = By.cssSelector(".inventory_details_img_container img");
    private By backBtn = By.id("back-to-products");
    private By cartBadge = By.className("shopping_cart_badge");


    // Actions

    public String getProductName() {
        return driver.findElement(productName).getText();
    }

    public String getProductDescription() {
        return driver.findElement(productDescription).getText();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText();
    }

    public String getProductImageSrc() {
        String src = driver.findElement(productImage).getAttribute("src");
        return src.replace("https://www.saucedemo.com", "");
    }

    public void openProductById(String idNumber) {
        driver.get("https://www.saucedemo.com/inventory-item.html?id=" + idNumber);
    }

    public void clickBack() {
        driver.findElement(backBtn).click();
    }

    public void clickAddToCart() {
        driver.findElement(By.id("add-to-cart")).click();
    }

    public void clickRemoveFromCart() {
        driver.findElement(By.id("remove")).click();
    }

    public boolean isInCart(String addToCartId) {
        String removeId = addToCartId.replace("add-to-cart", "remove");
        return driver.findElement(By.id(removeId)).isDisplayed();
    }
    // Get cart badge count
    public int getCartBadgeCount() {
        try {
            return Integer.parseInt(driver.findElement(cartBadge).getText());
        } catch (Exception e) {
            return 0;
        }
    }
}