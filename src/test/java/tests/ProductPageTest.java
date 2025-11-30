package tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class ProductPageTest extends BaseClass{

    @Test(dataProvider = "ValidLoginData")
    public void validateProduct_Name_Price_Description(String username, String password) throws IOException {
        Login(username, password);

        // Read the list of products from the JSON file
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Find all inventory items on the inventory page
        List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));

        // Loop through each item in the inventory
        for (int i = 0; i < inventoryItems.size(); i++) {
            // Get the product details from the JSON file
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

            // Validate the product name,description,price
            softAssert.assertEquals(productPageName.getText(), productName, "User : '"+username+"', Product: '"+productName+"', Product name mismatch");
            softAssert.assertEquals(productPageDescription.getText(), productDescription, "User : '"+username+"', Product: '"+productName+"', Product description mismatch");
            softAssert.assertEquals(productPagePrice.getText(), productPrice, "User : '"+username+"', Product: '"+productName+"', Product price mismatch");

            // Go back to the inventory page after validation
            WebElement backButton = driver.findElement(By.id("back-to-products"));
            backButton.click();
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "ValidLoginData")
    public void validateProductImages(String username, String password) throws IOException {
        Login(username, password);

        // Read the list of products from the JSON file
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Find all inventory items on the inventory page
        List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));

        // Loop through each product in the JSON and validate its image
        for (int i = 0; i < inventoryItems.size(); i++) {
            // Get the product details from the JSON file
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
            softAssert.assertEquals(ActualProductImageSrc, productImage, "User : '"+username+"', Product image mismatch for product ID: " + productId);

        }
        softAssert.assertAll();
    }
    @Test(dataProvider = "ValidLoginData")
    public void test_Add_Remove_Item(String username, String password) throws IOException {
        Login(username, password);

        // Read the list of products from the JSON file
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Find all inventory items on the inventory page
        List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));


        for (int i = 0; i < inventoryItems.size(); i++) {
            // Get the product details from the JSON file
            JSONObject product = items.getJSONObject(i);

            String productId = product.getString("id");

            String productIdNumber = productId.substring(productId.lastIndexOf('_') + 1); // Extracts the number after "item_"

            // Open the product page directly using the product ID
            driver.get("https://www.saucedemo.com/inventory-item.html?id=" + productIdNumber);

            // Test Add Product to Cart Functionality
            WebElement add = driver.findElement(By.id("add-to-cart"));
            add.click();
            try {
                WebElement remove = driver.findElement(By.id("remove"));
                WebElement badge = driver.findElement(By.className("shopping_cart_badge"));
                softAssert.assertTrue(remove.isDisplayed() && badge.getText().equals("1"));
            }catch (NoSuchElementException e){
                softAssert.fail("User : '"+username+"', Product ID: " + productId+"',Test failed: Product not added to cart.");
            }

            // Test Remove Product from Cart Functionality
            try {
                driver.findElement(By.id("remove")).click();// Reset
            }catch (NoSuchElementException e){
            softAssert.fail( "User : '"+username+"', Product ID: " + productId+"', Test failed: Cart badge still visible.");
            }

        }
        softAssert.assertAll();
    }

}
