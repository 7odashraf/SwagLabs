package tests;

import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Objects;

public class CheckoutInfoTest extends BaseClass {

    @Test(dataProvider = "ValidLoginData")
    public void testCheckoutPage(String username, String password) throws IOException {
        Login(username, password);

        // Read the list of items from a JSON file
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Iterate through the list of items
        for (int i = 0; i < items.length(); i++) {
            // Get the product details from the JSON file
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");

            // Find and click the 'Add to Cart' button for the product
            WebElement addBtn = driver.findElement(By.id(addToCartId));
            addBtn.click();

            // Get the expected product name from the JSON
            String expectedName = items.getJSONObject(i).getString("name");

            // Navigate to the shopping cart page
            shopping_Cart_Btn().click();

            // Assert that the name of the item in the cart matches the expected name
            String itemName = getLastItemFromCart().findElement(By.cssSelector(".inventory_item_name")).getText();
            softAssert.assertEquals(itemName, expectedName, "User : '"+username+"', Product: '"+expectedName+"', name does not match the expected name.");

            checkOut_Btn().click();


            // Read checkout data from a JSON file (such as user info)
            JSONArray info = readCheckoutDataJson();
            for (int infoNum=0;infoNum< info.length();infoNum++){

                // Fill in the user details in the checkout form
                firstname_Input().sendKeys(info.getJSONObject(infoNum).getString("firstName"));
                lastname_Input().sendKeys(info.getJSONObject(infoNum).getString("lastName"));
                zip_Code_Input().sendKeys(info.getJSONObject(infoNum).getString("zipCode"));

                continue_CheckOut_Btn().click();

                // Verify that the error message matches the expected error for the current data
                softAssert.assertEquals(getErrorMessage(),info.getJSONObject(infoNum).getString("errorMessage"),"User : '"+username+"', The error message is incorrect for the provided user details.");
                if (Objects.equals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html")){
                    driver.get("https://www.saucedemo.com/checkout-step-one.html");
                }

            }
            // Return to the inventory page after finishing the checkout flow
            driver.get("https://www.saucedemo.com/inventory.html");

        }
        softAssert.assertAll();
    }
}
