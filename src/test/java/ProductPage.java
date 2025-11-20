import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ProductPage {

    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        // Perform login before all tests
        login(driver);
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }

    // Login helper method
    public void login(WebDriver driver) {
        driver.get("https://www.saucedemo.com/");
        WebElement user = driver.findElement(By.id("user-name"));
        WebElement pwd = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("login-button"));

        user.sendKeys("standard_user");
        pwd.sendKeys("secret_sauce");
        loginBtn.click();

        System.out.println("Logged in successfully");
    }

    // TC_002: Product Image Load Verification
    @Test
    public void TC_001() {
        driver.get("https://www.saucedemo.com/inventory-item.html?id=4");

        WebElement productImage = driver.findElement(By.className("inventory_details_img"));
        Assert.assertTrue(productImage.isDisplayed(), "TC_002 FAILED - Product image not displayed");

        String imageSource = productImage.getAttribute("src");
        Assert.assertNotNull(imageSource, "TC_002 FAILED - Image source is empty");
        Assert.assertFalse(imageSource.isEmpty(), "TC_002 FAILED - Image source is empty");
        System.out.println("TC_002 PASSED - Product image loaded: " + imageSource);
    }

    // TC_003: Product Title Verification
    @Test
    public void TC_002() {
        driver.get("https://www.saucedemo.com/inventory.html");

        String productName = "Sauce Labs Backpack";
        String inventoryTitle = driver.findElement(By.xpath("//div[@data-test='inventory-item-name' and text()='" + productName + "']")).getText();
        driver.findElement(By.xpath("//div[@data-test='inventory-item-name' and text()='" + productName + "']")).click();

        String detailTitle = driver.findElement(By.className("inventory_details_name")).getText();
        Assert.assertEquals(inventoryTitle, detailTitle, "TC_003 FAILED - Titles don't match");
        System.out.println("TC_003 PASSED - Titles match");
    }

    // TC_004: Product Description Verification
    @Test
    public void TC_003() {
        driver.get("https://www.saucedemo.com/inventory.html");

        String productName = "Sauce Labs Backpack";
        String inventoryDesc = getProductDescription(driver, productName);
        driver.findElement(By.xpath("//div[@data-test='inventory-item-name' and text()='" + productName + "']")).click();

        String detailDesc = driver.findElement(By.className("inventory_details_desc")).getText();
        Assert.assertEquals(inventoryDesc, detailDesc, "TC_004 FAILED - Descriptions mismatch");
        System.out.println("TC_004 PASSED - Descriptions match");
    }

    // Helper method to get product description
    public String getProductDescription(WebDriver driver, String productName) {
        WebElement container = getProductContainer(driver, productName);
        return container.findElement(By.cssSelector("[data-test='inventory-item-desc']")).getText();
    }

    public WebElement getProductContainer(WebDriver driver, String productName) {
        return driver.findElement(By.xpath("//div[@data-test='inventory-item-name' and text()='" + productName + "']"))
                .findElement(By.xpath("./ancestor::div[@data-test='inventory-item']"));
    }

    // TC_005: Product Price Format Verification
    @Test
    public void TC_004() {
        driver.get("https://www.saucedemo.com/inventory-item.html?id=4");

        String priceText = driver.findElement(By.className("inventory_details_price")).getText();
        Assert.assertTrue(priceText.startsWith("$"), "TC_005 FAILED - Price does not start with $");
        Assert.assertTrue(priceText.contains("."), "TC_005 FAILED - Price does not contain decimal");
        Assert.assertTrue(priceText.matches(".*\\.[0-9]{2}"), "TC_005 FAILED - Price does not have 2 decimals");
        System.out.println("TC_005 PASSED - Price format correct: " + priceText);
    }

    // TC_006: Product price Verification
    @Test
    public void TC_005() {
        driver.get("https://www.saucedemo.com/inventory.html");

        String productName = "Sauce Labs Backpack";
        String invPrice = getProductPrice(driver, productName);
        driver.findElement(By.xpath("//div[@data-test='inventory-item-name' and text()='" + productName + "']")).click();
        String detailPrice = driver.findElement(By.className("inventory_details_price")).getText();

        Assert.assertEquals(invPrice, detailPrice, "TC_006 FAILED - Price mismatch");
        System.out.println("TC_006 PASSED - Prices match");
    }

    public String getProductPrice(WebDriver driver, String productName) {
        WebElement container = getProductContainer(driver, productName);
        return container.findElement(By.cssSelector("[data-test='inventory-item-price']")).getText();
    }

    // TC_007: Add Product to Cart Functionality
    @Test
    public void TC_006() {
        driver.get("https://www.saucedemo.com/inventory-item.html?id=4");

        WebElement add = driver.findElement(By.id("add-to-cart"));
        add.click();

        WebElement remove = driver.findElement(By.id("remove"));
        WebElement badge = driver.findElement(By.className("shopping_cart_badge"));

        Assert.assertTrue(remove.isDisplayed(), "TC_007 FAILED - Add to cart malfunction");
        Assert.assertEquals(badge.getText(), "1", "TC_007 FAILED - Cart badge does not show 1");
        remove.click();
        System.out.println("TC_007 PASSED - Add to cart works");
    }

    // TC_008: Remove Product from Cart Functionality
    @Test
    public void TC_007() {
        driver.get("https://www.saucedemo.com/inventory-item.html?id=4");

        WebElement addBtn = driver.findElement(By.id("add-to-cart"));
        addBtn.click();

        WebElement removeBtn = driver.findElement(By.id("remove"));
        removeBtn.click();

        List<WebElement> badge = driver.findElements(By.className("shopping_cart_badge"));

        Assert.assertTrue(badge.isEmpty(), "TC_008 FAILED - Cart badge still visible");
        System.out.println("TC_008 PASSED - Product successfully removed from cart");
    }

    // TC_010: Add Same Product from Inventory Page Verification
    @Test
    public void TC_008() {
        driver.get("https://www.saucedemo.com/inventory-item.html?id=4");

        WebElement addBtn = driver.findElement(By.id("add-to-cart"));
        addBtn.click();
        driver.findElement(By.id("back-to-products")).click();

        WebElement button = driver.findElement(By.id("remove-sauce-labs-backpack"));
        Assert.assertEquals(button.getText(), "Remove", "TC_010 FAILED - Inventory page still shows Add to Cart");
        button.click();
        System.out.println("TC_010 PASSED - Cannot add same item again, inventory shows Remove");
    }

    // TC_011: Verification of Cart Persistence After Page Refresh
    @Test
    public void TC_009() {
        driver.get("https://www.saucedemo.com/inventory-item.html?id=4");

        WebElement addBtn = driver.findElement(By.id("add-to-cart"));
        addBtn.click();

        String beforeRefresh = driver.findElement(By.className("shopping_cart_badge")).getText();
        driver.navigate().refresh();
        String afterRefresh = driver.findElement(By.className("shopping_cart_badge")).getText();

        Assert.assertEquals(beforeRefresh, afterRefresh);
    }
}

