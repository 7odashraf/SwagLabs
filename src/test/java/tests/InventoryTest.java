package tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;




public class InventoryTest extends BaseClass {

    @Test(dataProvider = "ValidLoginData")
    public void test_Add_Remove_Item(String username, String password) throws IOException {
        Login(username, password);

        // Read the list of items from a JSON file
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Iterate through the list of items
        for (int i = 0; i < items.length(); i++) {
            // Get the "add to cart" button ID and "remove from cart" button ID for each item
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");
            String RemoveFromCartId = items.getJSONObject(i).getString("remove_from_cart_id");

            // Try to click on the "Add to Cart" button
            try {
                WebElement addBtn =driver.findElement(By.id(addToCartId));
                addBtn.click();
            }catch (NoSuchElementException e ){
                softAssert.fail( "User : '"+username+"', "+ items.getJSONObject(i).getString("name")+": add to cart button unclickable.");
            }

            // Try to click on the "Remove from Cart" button
            try {
                WebElement removeBtn = driver.findElement(By.id(RemoveFromCartId));
                removeBtn.click();
            }catch (NoSuchElementException e ){
                softAssert.fail("User : '"+username+"', "+ items.getJSONObject(i).getString("name")+": remove form cart button unclickable.");
            }
        }
        softAssert.assertAll();
    }
    @Test(dataProvider = "ValidLoginData")
    public void testInventoryItems(String username, String password) throws IOException {
        Login(username, password);

        // Read the list of items from a JSON file
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Find all inventory items on the page
        List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));

        // Iterate through each inventory item and compare its details with the expected values from the JSON
        for (int i = 0; i < inventoryItems.size(); i++) {
            WebElement item = inventoryItems.get(i);

            // Extract item details from the page
            String itemName = item.findElement(By.cssSelector(".inventory_item_name")).getText();
            String itemDescription = item.findElement(By.cssSelector(".inventory_item_desc")).getText();
            String itemPrice = item.findElement(By.cssSelector(".inventory_item_price")).getText();

            // Get the expected product details from the JSON file
            JSONObject product = items.getJSONObject(i);
            String expectedName = product.getString("name");
            String expectedDescription = product.getString("description");
            String expectedPrice = product.getString("price");

            // Assert that the item name, description, and price match the expected values
            softAssert.assertEquals(itemName, expectedName, "User : '"+username+"', The name of the product is incorrect for item " + (i + 1)+" : "+expectedName);
            softAssert.assertEquals(itemDescription, expectedDescription, "User : '"+username+"', The description of the product is incorrect for item " + (i + 1)+" : "+expectedName);
            softAssert.assertEquals(itemPrice, expectedPrice, "User : '"+username+"', The price of the product is incorrect for item " + (i + 1)+" : "+expectedName);
        }
        softAssert.assertAll();
    }

}
