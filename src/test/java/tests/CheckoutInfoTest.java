package tests;

import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.LoginPage;
import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class CheckoutInfoTest extends BaseClass {

    @Test(dataProvider = "ValidLoginData")
    public void testCheckoutPage(String username, String password) throws IOException {
        LoginPage login = new LoginPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);
        CartPage cart = new CartPage(driver);

        login.Login(username, password);

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
            cart.clickShoppingCart();

            // Assert that the name of the item in the cart matches the expected name
            String itemName = cart.getLastItemFromCart().findElement(By.cssSelector(".inventory_item_name")).getText();
            softAssert.assertEquals(itemName, expectedName, "User : '"+username+"', Product: '"+expectedName+"', name does not match the expected name.");

            cart.clickCheckout();

            // Read checkout data from a JSON file (such as user info)
            JSONArray info = readCheckoutDataJson();
            for (int infoNum=0;infoNum< info.length();infoNum++){
                String expectedFirstName = info.getJSONObject(infoNum).getString("firstName");
                String expectedLastName= info.getJSONObject(infoNum).getString("lastName");
                String expectedZipCode= info.getJSONObject(infoNum).getString("zipCode");

                // Fill in the user details in the checkout form
                checkout.firstnameInput().sendKeys(expectedFirstName);
                checkout.lastnameInput().sendKeys(expectedLastName);
                checkout.zipCodeInput().sendKeys(expectedZipCode);

                checkout.clickContinue();

                // Verify that the error message matches the expected error for the current data
                softAssert.assertEquals(checkout.getErrorMessage(),info.getJSONObject(infoNum).getString("errorMessage"),"User : '"+username+"', The error message is incorrect for the provided user details."+" First Name : "+expectedFirstName+" Last Name : "+expectedLastName+" zip Code: "+expectedZipCode);

                driver.get("https://www.saucedemo.com/checkout-step-one.html");

            }
            // Return to the inventory page after finishing the checkout flow
            driver.get("https://www.saucedemo.com/inventory.html");

        }
        softAssert.assertAll();
    }
}
