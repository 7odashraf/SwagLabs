package tests;

import Pages.InventoryPage;
import Pages.LoggerClass;
import Pages.LoginPage;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;




public class InventoryTest extends BaseClass {
    private static final Logger log = LoggerClass.getLogger();

    @Test(dataProvider = "ValidLoginData")
    public void test_Add_Remove_Item(String username, String password) throws IOException {
        log.info("===== Starting Add/Remove Inventory Test for user: " + username + " =====");

        LoginPage login = new LoginPage(driver);
        InventoryPage inventory = new InventoryPage(driver);

        log.info("Logging in with username: " + username);
        login.Login(username, password);

        // Read the list of items from a JSON file
        log.info("Reading product list JSON");
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Iterate through the list of items
        for (int i = 0; i < items.length(); i++) {
            // Get the "add to cart" button ID and "remove from cart" button ID for each item
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");
            String RemoveFromCartId = items.getJSONObject(i).getString("remove_from_cart_id");
            String productName =items.getJSONObject(i).getString("name");
            log.info("Testing Add/Remove for product: " + productName);

            // Try to click on the "Add to Cart" button
            try {
                log.info("Clicking Add to Cart → " + productName);
                inventory.clickAddToCart(addToCartId);
            }catch (NoSuchElementException e ){
                log.error("Add to Cart button NOT found for: " + productName);
                softAssert.fail( "User : '"+username+"', "+ items.getJSONObject(i).getString("name")+": add to cart button unclickable.");
            }

            // Try to click on the "Remove from Cart" button
            try {
                log.info("Clicking Remove from Cart → " + productName);
                inventory.clickRemoveFromCart(RemoveFromCartId);
            }catch (NoSuchElementException e ){
                log.error("Remove from Cart button NOT found for: " + productName);
                softAssert.fail("User : '"+username+"', "+ items.getJSONObject(i).getString("name")+": remove form cart button unclickable.");
            }
        }
        softAssert.assertAll();
        log.info("===== Add/Remove Inventory Test Completed for user: " + username + " =====");
    }
    @Test(dataProvider = "ValidLoginData")
    public void testInventoryItems(String username, String password) throws IOException {
        log.info("===== Starting Inventory Items Validation Test for user: " + username + " =====");

        LoginPage login = new LoginPage(driver);
        InventoryPage inventory = new InventoryPage(driver);

        log.info("Logging in with username: " + username);
        login.Login(username, password);

        // Read the list of items from a JSON file
        log.info("Reading product list JSON");
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Find all inventory items on the page
        log.info("Fetching inventory items from UI");
        List<WebElement> inventoryItems = inventory.getInventoryItems();

        // Iterate through each inventory item and compare its details with the expected values from the JSON
        for (int i = 0; i < inventoryItems.size(); i++) {
            WebElement item = inventoryItems.get(i);

            // Extract item details from the page
            String itemName = inventory.getItemName(item);
            String itemDescription = inventory.getItemDescription(item);
            String itemPrice = inventory.getItemPrice(item);

            // Get the expected product details from the JSON file
            JSONObject product = items.getJSONObject(i);
            String expectedName = product.getString("name");
            String expectedDescription = product.getString("description");
            String expectedPrice = product.getString("price");

            // Assert that the item name, description, and price match the expected values
            log.info("Comparing UI vs JSON → " + expectedName);
            softAssert.assertEquals(itemName, expectedName, "User : '"+username+"', The name of the product is incorrect for item " + (i + 1)+" : "+expectedName);
            softAssert.assertEquals(itemDescription, expectedDescription, "User : '"+username+"', The description of the product is incorrect for item " + (i + 1)+" : "+expectedName);
            softAssert.assertEquals(itemPrice, expectedPrice, "User : '"+username+"', The price of the product is incorrect for item " + (i + 1)+" : "+expectedName);
        }
        softAssert.assertAll();
        log.info("===== Inventory Items Test Completed for user: " + username + " =====");
    }

}
