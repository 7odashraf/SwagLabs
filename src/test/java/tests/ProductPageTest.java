package tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class ProductPageTest extends BaseClass{

    @Test(dataProvider = "ValidLoginData")
    public void validateProduct_Name_Price_Description(String username, String password) throws IOException {
        Login(username, password);

        JSONArray items = readProductListJson();


        List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));


        for (int i = 0; i < inventoryItems.size(); i++) {
            JSONObject product = items.getJSONObject(i);

            String productId = product.getString("id");
            String productName = product.getString("name");
            String productDescription = product.getString("description");
            String productPrice = product.getString("price");

            String productIdNumber = productId.substring(productId.lastIndexOf('_') + 1); // Extracts the number after "item_"

            // Open the product page directly using the product ID
            driver.get("https://www.saucedemo.com/inventory-item.html?id=" + productIdNumber);


            // Get the product name, description, and price from the product page
            WebElement productPageName = driver.findElement(By.cssSelector(".inventory_details_name"));
            WebElement productPageDescription = driver.findElement(By.cssSelector(".inventory_details_desc"));
            WebElement productPagePrice = driver.findElement(By.cssSelector(".inventory_details_price"));

            // Validate the product name
            Assert.assertEquals(productPageName.getText(), productName, "Product name mismatch");

            // Validate the product description
            Assert.assertEquals(productPageDescription.getText(), productDescription, "Product description mismatch");

            // Validate the product price
            Assert.assertEquals(productPagePrice.getText(), productPrice, "Product price mismatch");

            // Go back to the inventory page after validation
            WebElement backButton = driver.findElement(By.id("back-to-products"));
            backButton.click();
        }
    }

    @Test(dataProvider = "ValidLoginData")
    public void validateProductImages(String username, String password) throws IOException {
        Login(username, password);

        JSONArray items = readProductListJson();

        List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));

        // Loop through each product in the JSON and validate its image
        for (int i = 0; i < inventoryItems.size(); i++) {
            JSONObject product = items.getJSONObject(i);

            String productId = product.getString("id");
            String productImage = product.getString("image"); // Get image URL from JSON

            // Extract the last part of the productId, assuming the format is "item_X"
            String productIdNumber = productId.substring(productId.lastIndexOf('_') + 1); // Extracts the number after "item_"

            // Open the product page directly using the product ID
            driver.get("https://www.saucedemo.com/inventory-item.html?id=" + productIdNumber); // Use the extracted product number

            // Wait for the product page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".inventory_details_container")));

            // Get the image URL from the product page
            WebElement productImageElement = driver.findElement(By.cssSelector(".inventory_details_img_container img"));
            String productImageSrc = productImageElement.getAttribute("src"); // Get the src attribute of the image
            String ActualProductImageSrc = productImageSrc.replace("https://www.saucedemo.com", "");

            // Validate the product image
            Assert.assertEquals(ActualProductImageSrc, productImage, "Product image mismatch for product ID: " + productId);

        }
    }
    @Test(dataProvider = "ValidLoginData")
    public void test_Add_Remove_Item(String username, String password) throws IOException {
        Login(username, password);

        JSONArray items = readProductListJson();


        List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));


        for (int i = 0; i < inventoryItems.size(); i++) {
            JSONObject product = items.getJSONObject(i);

            String productId = product.getString("id");

            String productIdNumber = productId.substring(productId.lastIndexOf('_') + 1); // Extracts the number after "item_"

            // Open the product page directly using the product ID
            driver.get("https://www.saucedemo.com/inventory-item.html?id=" + productIdNumber);

            // Test Add Product to Cart Functionality
            WebElement add = driver.findElement(By.id("add-to-cart"));
            add.click();
            WebElement remove = driver.findElement(By.id("remove"));
            WebElement badge = driver.findElement(By.className("shopping_cart_badge"));
            Assert.assertTrue(remove.isDisplayed() && badge.getText().equals("1"), "Test failed: Product not added to cart.");

            // Test Remove Product from Cart Functionality
            remove.click();// Reset
            List<WebElement> badgeList = driver.findElements(By.className("shopping_cart_badge"));
            Assert.assertTrue(badgeList.isEmpty(), "Test failed: Cart badge still visible.");

        }
    }

}
