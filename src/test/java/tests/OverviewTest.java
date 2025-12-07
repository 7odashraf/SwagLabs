package tests;

import Pages.*;
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

    // Helper method to fill valid user information and assert no error message during checkout
    public void fillValidInfo(CheckoutPage checkout) {
        checkout.firstnameInput().sendKeys("John");
        checkout.lastnameInput().sendKeys("Doe");
        checkout.zipCodeInput().sendKeys("12345");
        checkout.clickContinue();
        Assert.assertEquals(checkout.getErrorMessage(), "", "checkout failed " + checkout.getErrorMessage());
    }

    @Test(dataProvider = "ValidLoginData")
    public void testOverviewPage(String username, String password) throws IOException {
        LoginPage login = new LoginPage(driver);
        CartPage cart = new CartPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);
        OverviewPage overview = new OverviewPage(driver);

        login.Login(username, password);

        // Read the list of products from a JSON file
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Iterate through each item in the product list
        for (int i = 0; i < items.length(); i++) {
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");

            WebElement addBtn = driver.findElement(By.id(addToCartId));
            addBtn.click();

            // Get the product details from the JSON (name, description, price)
            JSONObject product = items.getJSONObject(i);

            String expectedName = product.getString("name");
            String expectedDescription = product.getString("description");
            String expectedPrice = product.getString("price");

            cart.clickShoppingCart();

            // Verify that the item added to the cart matches the expected name
            String itemName = cart.getLastItemFromCart().findElement(By.cssSelector(".inventory_item_name")).getText();
            softAssert.assertEquals(itemName, expectedName, "The product: " + itemName + " name does not match the expected name.");

            cart.clickCheckout();
            fillValidInfo(checkout);  // Fill valid user info and proceed

            // Assert that there is no error message during checkout
            softAssert.assertEquals(checkout.getErrorMessage(),"","checkout failed "+checkout.getErrorMessage());

            // Get the list of items in the cart
            List<WebElement> cartItems = overview.getCartItems();

            // Verify that each item in the cart has the correct name, price, and description
            for (WebElement cartItem : cartItems) {
                String actualName = overview.getItemName(cartItem).getText();
                String actualPrice = overview.getItemPrice(cartItem).getText();
                String actualDescription = overview.getItemDescription(cartItem).getText();

                // Assert that the product details match for the current item
                if (actualName.equals(expectedName)) {

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
                driver.get("https://www.saucedemo.com/inventory.html");
            }
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "ValidLoginData")
    public void testTotalPrice(String username, String password) throws IOException {
        LoginPage login = new LoginPage(driver);
        CartPage cart = new CartPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);
        OverviewPage overview = new OverviewPage(driver);

        login.Login(username, password);

        // Read the list of products from the JSON file
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        double expectedPrice=0;

        // Iterate through the list of products and calculate the expected total price
        for (int i = 0; i < items.length(); i++) {
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");

            WebElement addBtn = driver.findElement(By.id(addToCartId));
            addBtn.click();

            // Get the price of the current product and add it to the total price
            JSONObject product = items.getJSONObject(i);

            String expectedPriceTest = product.getString("price");
            double price = Double.parseDouble(expectedPriceTest.replace("$", ""));
            expectedPrice += price;

            // Navigate to the shopping cart
            cart.clickShoppingCart();

            cart.clickCheckout();

            fillValidInfo(checkout);  // Fill valid user info and proceed

            // Get the total price and tax from the summary
            double actualTotal = overview.getTotalPrice();
            double tax = overview.getTax();

            // Calculate the expected total price (price + tax)
            double expectedTotalprice = tax+ expectedPrice;

            // Assert that the total price is calculated correctly
            softAssert.assertEquals(actualTotal, expectedTotalprice, 0.1, "The total price calculation is incorrect!");

            // Go back to the inventory page after the test
            driver.get("https://www.saucedemo.com/inventory.html");
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "ValidLoginData")
    public void testFinishProcess(String username, String password) throws IOException {
        LoginPage login = new LoginPage(driver);
        CartPage cart = new CartPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);
        OverviewPage overview = new OverviewPage(driver);

        login.Login(username, password);

        // Read the list of products from the JSON file
        JSONArray items = readProductListJson();

        SoftAssert softAssert = new SoftAssert();

        // Iterate through the product list, adding each product to the cart
        for (int i = 0; i < items.length(); i++) {
            // Add each product to the cart
            for (int j = 0; j <= i; j++) {
                String addToCartId = items.getJSONObject(j).getString("add_to_cart_id");
                WebElement addToCartBtn = driver.findElement(By.id(addToCartId));
                addToCartBtn.click();
            }

            // Navigate to the shopping cart
            cart.clickShoppingCart();

            cart.clickCheckout();
            fillValidInfo(checkout); // Fill valid user info and proceed

            // Finish the checkout process
            overview.finishButton().click();
            Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-complete.html");
            softAssert.assertEquals(overview.completeHeader().getText(),"Thank you for your order!");

            // Go back to the inventory page after completing the purchase
            overview.backHomeButton().click();
            softAssert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");

        }
        softAssert.assertAll();
    }
}
