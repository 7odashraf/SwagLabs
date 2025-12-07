package tests;

import Pages.*;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class OverviewTest extends BaseClass{
    private static final Logger log = LoggerClass.getLogger();

    // Helper method to fill valid user information and assert no error message during checkout
    public void fillValidInfo(CheckoutPage checkout) {
        log.info("Entering valid checkout information...");
        checkout.firstnameInput().sendKeys("John");
        checkout.lastnameInput().sendKeys("Doe");
        checkout.zipCodeInput().sendKeys("12345");
        checkout.clickContinue();
        log.info("Clicked Continue on checkout page.");

        Assert.assertEquals(checkout.getErrorMessage(), "", "checkout failed " + checkout.getErrorMessage());
    }

    @Test(dataProvider = "ValidLoginData")
    public void testOverviewPage(String username, String password) throws IOException {
        log.info("===== Starting Overview Page Test for user: " + username + " =====");

        LoginPage login = new LoginPage(driver);
        CartPage cart = new CartPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);
        OverviewPage overview = new OverviewPage(driver);

        login.Login(username, password);
        log.info("Logged in successfully.");

        // Read the list of products from a JSON file
        JSONArray items = readProductListJson();
        log.info("Loaded product list JSON.");

        SoftAssert softAssert = new SoftAssert();

        // Iterate through each item in the product list
        for (int i = 0; i < items.length(); i++) {
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");

            WebElement addBtn = driver.findElement(By.id(addToCartId));
            addBtn.click();
            log.info("Clicked Add to Cart button for product index: " + i);

            // Get the product details from the JSON (name, description, price)
            JSONObject product = items.getJSONObject(i);

            String expectedName = product.getString("name");
            String expectedDescription = product.getString("description");
            String expectedPrice = product.getString("price");

            cart.clickShoppingCart();
            log.info("Navigated to shopping cart");

            // Verify that the item added to the cart matches the expected name
            String itemName = cart.getLastItemFromCart().findElement(By.cssSelector(".inventory_item_name")).getText();
            log.info("Validating item name → expected: " + expectedName + ", actual: " + itemName);
            softAssert.assertEquals(itemName, expectedName, "The product: " + itemName + " name does not match the expected name.");

            cart.clickCheckout();
            fillValidInfo(checkout);  // Fill valid user info and proceed

            // Assert that there is no error message during checkout
            softAssert.assertEquals(checkout.getErrorMessage(),"","checkout failed "+checkout.getErrorMessage());

            // Get the list of items in the cart
            log.info("Validating Overview page items...");
            List<WebElement> cartItems = overview.getCartItems();

            // Verify that each item in the cart has the correct name, price, and description
            for (WebElement cartItem : cartItems) {
                String actualName = overview.getItemName(cartItem).getText();
                String actualPrice = overview.getItemPrice(cartItem).getText();
                String actualDescription = overview.getItemDescription(cartItem).getText();

                // Assert that the product details match for the current item
                if (actualName.equals(expectedName)) {

                    log.info("Matched item → " + expectedName + " | Validating details...");
                    softAssert.assertEquals(actualName, expectedName, "Expected product name: '" + expectedName + "' but found: '" + actualName + "' in the cart.");
                    softAssert.assertEquals(actualPrice, expectedPrice,
                            "Product '" + expectedName + "' price not matches. Expected: '" + expectedPrice + "' but found: '" + actualPrice + "' in overview page!");

                    softAssert.assertEquals(actualDescription, expectedDescription,
                            "Product '" + expectedName + "' description not matches. Expected: '" + expectedDescription + "' but found: '" + actualDescription + "' in overview page!");

                    break;  // Exit the loop if the product matches
                }
            }


            // If we are on the second checkout page, go back to the inventory page
            if (Objects.equals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html")){
                log.info("Returning to Inventory page...");
                driver.get("https://www.saucedemo.com/inventory.html");
            }
        }
        softAssert.assertAll();
        log.info("===== Overview Page Test Completed for user: " + username + " =====");
    }

    @Test(dataProvider = "ValidLoginData")
    public void testTotalPrice(String username, String password) throws IOException {
        log.info("===== Starting Total Price Test for user: " + username + " =====");

        LoginPage login = new LoginPage(driver);
        CartPage cart = new CartPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);
        OverviewPage overview = new OverviewPage(driver);

        login.Login(username, password);
        log.info("Logged in successfully.");

        // Read the list of products from the JSON file
        JSONArray items = readProductListJson();
        log.info("Loaded " + items.length() + " products from product list JSON.");

        SoftAssert softAssert = new SoftAssert();

        double expectedPrice=0;

        // Iterate through the list of products and calculate the expected total price
        for (int i = 0; i < items.length(); i++) {
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");

            log.info("Adding product #" + (i + 1) + " to cart → ID: " + addToCartId);
            WebElement addBtn = driver.findElement(By.id(addToCartId));
            addBtn.click();

            // Get the price of the current product and add it to the total price
            JSONObject product = items.getJSONObject(i);

            String expectedPriceTest = product.getString("price");
            double price = Double.parseDouble(expectedPriceTest.replace("$", ""));
            expectedPrice += price;
            log.info("Running expected item total (before tax): $" + expectedPrice);

            // Navigate to the shopping cart
            cart.clickShoppingCart();
            log.info("Navigated to Shopping Cart page.");

            cart.clickCheckout();
            log.info("Navigated to Checkout Information page.");

            fillValidInfo(checkout);  // Fill valid user info and proceed

            // Get the total price and tax from the summary
            double actualTotal = overview.getTotalPrice();
            double tax = overview.getTax();

            log.info("Overview Page → Tax: $" + tax + ", Total: $" + actualTotal);

            // Calculate the expected total price (price + tax)
            double expectedTotalprice = tax+ expectedPrice;
            log.info("Expected total price (items + tax): $" + expectedTotalprice);

            // Assert that the total price is calculated correctly
            softAssert.assertEquals(actualTotal, expectedTotalprice, 0.1, "The total price calculation is incorrect!");

            // Go back to the inventory page after the test
            driver.get("https://www.saucedemo.com/inventory.html");
            log.info("Returned to Inventory page for next iteration.\n");
        }
        softAssert.assertAll();
        log.info("===== Total Price Test Completed for user: " + username + " =====");
    }

    @Test(dataProvider = "ValidLoginData")
    public void testFinishProcess(String username, String password) throws IOException {
        log.info("===== Starting Finish Process Test for user: " + username + " =====");

        LoginPage login = new LoginPage(driver);
        CartPage cart = new CartPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);
        OverviewPage overview = new OverviewPage(driver);

        login.Login(username, password);
        log.info("Logged in successfully.");

        // Read the list of products from the JSON file
        JSONArray items = readProductListJson();
        log.info("Loaded " + items.length() + " products from product list JSON.");

        SoftAssert softAssert = new SoftAssert();

        // Iterate through the product list, adding each product to the cart
        for (int i = 0; i < items.length(); i++) {
            // Add each product to the cart
            for (int j = 0; j <= i; j++) {
                log.info("----- Iteration " + (i + 1) + ": Adding " + (j + 1) + " products to the cart -----");
                String addToCartId = items.getJSONObject(j).getString("add_to_cart_id");
                WebElement addToCartBtn = driver.findElement(By.id(addToCartId));
                addToCartBtn.click();
            }

            // Navigate to the shopping cart
            cart.clickShoppingCart();
            log.info("Navigated to Shopping Cart.");

            cart.clickCheckout();
            log.info("Navigated to Checkout Information page.");

            fillValidInfo(checkout); // Fill valid user info and proceed
            log.info("Entered valid checkout information and continued.");

            // Finish the checkout process
            overview.finishButton().click();
            log.info("Clicked Finish Button.");

            Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-complete.html");
            softAssert.assertEquals(overview.completeHeader().getText(),"Thank you for your order!");

            // Go back to the inventory page after completing the purchase
            overview.backHomeButton().click();
            log.info("Clicked Back Home.");

            softAssert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");

            log.info("----- Completed Iteration " + (i + 1) + " -----\n");
        }
        softAssert.assertAll();
        log.info("===== Finish Process Test Completed for user: " + username + " =====");
    }
}
