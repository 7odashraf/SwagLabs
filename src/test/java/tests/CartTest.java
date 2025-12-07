package tests;

import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoggerClass;
import Pages.LoginPage;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;

public class CartTest extends BaseClass {
    private static final Logger log = LoggerClass.getLogger();

// ========================== Test: Add and Remove Item from Cart ==============================

// 1. Verify cart badge increases by 1 after adding an item to the cart
// 2. Add item to cart and verify the item is in the cart (by checking name, price, and description)
// 3. Verify the cart badge decreases by 1 after removing the item from the cart
// 4. Verify the item is no longer in the cart (ensure item is removed by checking its absence)
//------------------------------------------------------------------------------------------



    @Test(dataProvider = "ValidLoginData")
    public void verifyAddRemoveItemFromCart(String username, String password) throws IOException {
        log.info("===== Starting Cart Test for user: " + username + " =====");

        LoginPage login = new LoginPage(driver);
        InventoryPage inventory = new InventoryPage(driver);
        CartPage cart = new CartPage(driver);

        log.info("Logging in with username: " + username);
        login.Login(username, password);

        // Read the list of products from the JSON file
        log.info("Reading product list from JSON file");
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Find all inventory items on the inventory page
        log.info("Fetching inventory items from UI");
        List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));

        // Loop through each item in the inventory
        for (int i = 0; i < inventoryItems.size(); i++) {
            // Get the product details from the JSON file
            JSONObject product = items.getJSONObject(i);

            String expectedName = product.getString("name");
            String expectedDescription = product.getString("description");
            String expectedPrice = product.getString("price");
            String addToCartId = product.getString("add_to_cart_id");

            log.info("Adding product to cart → " + expectedName);

            int oldBadge = cart.getCartBadgeNumber();
            log.info("Old cart badge count: " + oldBadge);

            inventory.clickAddToCart(addToCartId);

            int newBadge = cart.getCartBadgeNumber();
            log.info("New cart badge count: " + newBadge);

            softAssert.assertTrue(newBadge > oldBadge, "User : '"+username+"', The product: " + expectedName + ", Add to Cart test failed: badge count did not decrease");
            cart.clickShoppingCart();
            log.info("Opened cart to validate added item: " + expectedName);

            // Get the product name, description, and price from the cart page
            String itemName = cart.getLastItemFromCart().findElement(By.cssSelector(".inventory_item_name")).getText();
            String itemDescription = cart.getLastItemFromCart().findElement(By.cssSelector(".inventory_item_desc")).getText();
            String itemPrice = cart.getLastItemFromCart().findElement(By.cssSelector(".inventory_item_price")).getText();
            log.info("Validating name, description, and price for product: " + expectedName);

            // Validate the product name,description,price
            softAssert.assertEquals(itemName, expectedName, "User : '"+username+"', The product: " + expectedName + " name does not match the expected name.");
            softAssert.assertEquals(itemDescription, expectedDescription, "User : '"+username+"', The product: " + expectedName + " description does not match the expected description.");
            softAssert.assertEquals(itemPrice, expectedPrice, "User : '"+username+"',The product: " + expectedName + " price does not match the expected price.");

            // back to inventory page
            log.info("Returning to inventory page");
            cart.clickContinueShopping();

        }
        for (int i = 0; i < inventoryItems.size(); i++) {

            // Read the list of products from the JSON file
            JSONObject product = items.getJSONObject(i);

            String expectedName = product.getString("name");
            String RemoveFromCartId = product.getString("remove_from_cart_id");
            log.info("Removing product from cart → " + expectedName);

            int oldBadge = cart.getCartBadgeNumber();
            log.info("Old cart badge count: " + oldBadge);

            try {
                cart.removeItemById(RemoveFromCartId);
                log.info("Clicked remove button for: " + expectedName);
            }catch (NoSuchElementException e){
                log.error("Remove button NOT found for: " + expectedName);
                softAssert.fail("User : '"+username+"', The product: " + expectedName + ",  Remove from cart button not found or not clickable");
            }

            int newBadge = cart.getCartBadgeNumber();
            log.info("New cart badge count: " + newBadge);

            softAssert.assertTrue(newBadge < oldBadge, "User : '"+username+"', The product: " + expectedName + ", Add to Cart test failed: badge count did not decrease");
            cart.clickShoppingCart();
            log.info("Checking cart to confirm removal: " + expectedName);


            softAssert.assertFalse(cart.isItemInCart(expectedName), "User : '"+username+"', The product " + expectedName + " was not removed from the cart.");


            // back to inventory page
            log.info("Returning to inventory page");
            cart.clickContinueShopping();
        }
        softAssert.assertAll();

        log.info("===== Cart Test Completed Successfully for user: " + username + " =====");
    }

}
