package tests;

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
    public void fillValidInfo() {
        firstname_Input().sendKeys("John");
        lastname_Input().sendKeys("Doe");
        zip_Code_Input().sendKeys("12345");
        continue_CheckOut_Btn().click();
        Assert.assertEquals(getErrorMessage(), "", "checkout failed " + getErrorMessage());
    }

    @Test(dataProvider = "ValidLoginData")
    public void testOverviewPage(String username, String password) throws IOException {
        Login(username, password);

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

            shopping_Cart_Btn().click();

            // Verify that the item added to the cart matches the expected name
            String itemName = getLastItemFromCart().findElement(By.cssSelector(".inventory_item_name")).getText();
            softAssert.assertEquals(itemName, expectedName, "The product: " + itemName + " name does not match the expected name.");

            checkOut_Btn().click();
            fillValidInfo();  // Fill valid user info and proceed

            // Assert that there is no error message during checkout
            softAssert.assertEquals(getErrorMessage(),"","checkout failed "+getErrorMessage());

            // Get the list of items in the cart
            List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_list .cart_item"));

            // Verify that each item in the cart has the correct name, price, and description
            for (WebElement cartItem : cartItems) {
                WebElement itemNameElement = cartItem.findElement(By.cssSelector(".inventory_item_name"));
                String actualName = itemNameElement.getText();
                WebElement itemPriceElement = cartItem.findElement(By.cssSelector(".inventory_item_price"));
                String actualPrice = itemPriceElement.getText();
                WebElement itemDescElement = cartItem.findElement(By.cssSelector(".inventory_item_desc"));
                String actualDescription = itemDescElement.getText();

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
        Login(username, password);

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
            shopping_Cart_Btn().click();

            checkOut_Btn().click();
            fillValidInfo();  // Fill valid user info and proceed

            // Get the total price and tax from the summary
            String totalText = driver.findElement(By.cssSelector(".summary_total_label[data-test='total-label']")).getText();
            String totalValueText = totalText.replace("Total: $", "").trim();

            double actualTotal = Double.parseDouble(totalValueText);

            String taxText = driver.findElement(By.cssSelector(".summary_tax_label[data-test='tax-label']")).getText();
            String taxValue = taxText.replace("Tax: $", "").trim();
            double tax = Double.parseDouble(taxValue);

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
        Login(username, password);

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
            shopping_Cart_Btn().click();

            checkOut_Btn().click();
            fillValidInfo(); // Fill valid user info and proceed

            // Finish the checkout process
            finish_Btn().click();
            Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-complete.html");
            softAssert.assertEquals(complete_Header().getText(),"Thank you for your order!");

            // Go back to the inventory page after completing the purchase
            back_Home_Btn().click();
            softAssert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");

        }
        softAssert.assertAll();
    }
}
