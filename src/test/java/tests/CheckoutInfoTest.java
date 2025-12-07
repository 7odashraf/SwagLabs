package tests;

import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.LoggerClass;
import Pages.LoginPage;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class CheckoutInfoTest extends BaseClass {

    private static final Logger log = LoggerClass.getLogger();

    @Test(dataProvider = "ValidLoginData")
    public void testCheckoutPage(String username, String password) throws IOException {
        log.info("===== Starting Checkout Info Test for user: " + username + " =====");

        LoginPage login = new LoginPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);
        CartPage cart = new CartPage(driver);

        log.info("Logging in with username: " + username);
        login.Login(username, password);

        // Read the list of items from a JSON file
        log.info("Reading product list JSON");
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Iterate through the list of items
        for (int i = 0; i < items.length(); i++) {
            // Get the product details from the JSON file
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");
            String expectedName = items.getJSONObject(i).getString("name");

            // Find and click the 'Add to Cart' button for the product
            log.info("Adding product to cart → " + expectedName);
            WebElement addBtn = driver.findElement(By.id(addToCartId));
            addBtn.click();

            // Navigate to the shopping cart page
            log.info("Navigating to shopping cart");
            cart.clickShoppingCart();

            // Assert that the name of the item in the cart matches the expected name
            String itemName = cart.getLastItemFromCart().findElement(By.cssSelector(".inventory_item_name")).getText();
            softAssert.assertEquals(itemName, expectedName, "User : '"+username+"', Product: '"+expectedName+"', name does not match the expected name.");

            log.info("Opening checkout page");
            cart.clickCheckout();

            // Read checkout data from a JSON file (such as user info)
            log.info("Reading checkout input data JSON");
            JSONArray info = readCheckoutDataJson();
            for (int infoNum=0;infoNum< info.length();infoNum++){
                String expectedFirstName = info.getJSONObject(infoNum).getString("firstName");
                String expectedLastName= info.getJSONObject(infoNum).getString("lastName");
                String expectedZipCode= info.getJSONObject(infoNum).getString("zipCode");

                // Fill in the user details in the checkout form
                log.info("Filling checkout data → Firstname: " + expectedFirstName + ", Lastname: " + expectedLastName + ", Zip: " + expectedZipCode);
                checkout.firstnameInput().sendKeys(expectedFirstName);
                checkout.lastnameInput().sendKeys(expectedLastName);
                checkout.zipCodeInput().sendKeys(expectedZipCode);

                log.info("Clicking Continue to trigger validation");
                checkout.clickContinue();

                // Verify that the error message matches the expected error for the current data
                softAssert.assertEquals(checkout.getErrorMessage(),info.getJSONObject(infoNum).getString("errorMessage"),"User : '"+username+"', The error message is incorrect for the provided user details."+" First Name : "+expectedFirstName+" Last Name : "+expectedLastName+" zip Code: "+expectedZipCode);

                log.info("Reloading checkout step one page for next input test");
                driver.get("https://www.saucedemo.com/checkout-step-one.html");

            }
            // Return to the inventory page after finishing the checkout flow
            log.info("Returning to inventory page to continue testing");
            driver.get("https://www.saucedemo.com/inventory.html");

        }
        softAssert.assertAll();
        log.info("===== Checkout Info Test Completed for user: " + username + " =====");
    }
}
