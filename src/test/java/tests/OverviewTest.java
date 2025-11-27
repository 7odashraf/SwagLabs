package tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class OverviewTest extends BaseClass{

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

        JSONArray items = readProductListJson();

        for (int i = 0; i < items.length(); i++) {
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");

            WebElement addBtn = driver.findElement(By.id(addToCartId));
            addBtn.click();

            JSONObject product = items.getJSONObject(i);

            String expectedName = product.getString("name");
            String expectedDescription = product.getString("description");
            String expectedPrice = product.getString("price");

            shopping_Cart_Btn().click();

            String itemName = getLastItemFromCart().findElement(By.cssSelector(".inventory_item_name")).getText();
            Assert.assertEquals(itemName, expectedName, "The product: " + itemName + " name does not match the expected name.");

            checkOut_Btn().click();
            fillValidInfo();

            Assert.assertEquals(getErrorMessage(),"","checkout failed "+getErrorMessage());

            List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_list .cart_item"));

            for (WebElement cartItem : cartItems) {
                WebElement itemNameElement = cartItem.findElement(By.cssSelector(".inventory_item_name"));
                String actualName = itemNameElement.getText();
                WebElement itemPriceElement = cartItem.findElement(By.cssSelector(".inventory_item_price"));
                String actualPrice = itemPriceElement.getText();
                WebElement itemDescElement = cartItem.findElement(By.cssSelector(".inventory_item_desc"));
                String actualDescription = itemDescElement.getText();

                 if (actualName.equals(expectedName)) {

                    Assert.assertEquals(actualName, expectedName, "Expected product name: '" + expectedName + "' but found: '" + actualName + "' in the cart.");
                    Assert.assertEquals(actualPrice, expectedPrice,
                            "Product '" + expectedName + "' price not matches. Expected: '" + expectedPrice + "' but found: '" + actualPrice + "' in overview page!");

                    Assert.assertEquals(actualDescription, expectedDescription,
                            "Product '" + expectedName + "' description not matches. Expected: '" + expectedDescription + "' but found: '" + actualDescription + "' in overview page!");

                    break;
                }
            }


            if (Objects.equals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html")){
                driver.get("https://www.saucedemo.com/inventory.html");
            }
        }
    }

    @Test(dataProvider = "ValidLoginData")
    public void testTotalPrice(String username, String password) throws IOException {
        Login(username, password);

        JSONArray items = readProductListJson();
        double expectedPrice=0;

        for (int i = 0; i < items.length(); i++) {
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");

            WebElement addBtn = driver.findElement(By.id(addToCartId));
            addBtn.click();

            JSONObject product = items.getJSONObject(i);

            String expectedPriceTest = product.getString("price");
            double price = Double.parseDouble(expectedPriceTest.replace("$", ""));
            expectedPrice += price;

            shopping_Cart_Btn().click();

            checkOut_Btn().click();
            fillValidInfo();

            String totalText = driver.findElement(By.cssSelector(".summary_total_label[data-test='total-label']")).getText();
            String totalValueText = totalText.replace("Total: $", "").trim();

            double actualTotal = Double.parseDouble(totalValueText);

            String taxText = driver.findElement(By.cssSelector(".summary_tax_label[data-test='tax-label']")).getText();
            String taxValue = taxText.replace("Tax: $", "").trim();
            double tax = Double.parseDouble(taxValue);

            double expectedTotalprice = tax+ expectedPrice;

            Assert.assertEquals(actualTotal, expectedTotalprice, 0.1, "The total price calculation is incorrect!");

            driver.get("https://www.saucedemo.com/inventory.html");
        }

    }

    @Test(dataProvider = "ValidLoginData")
    public void testFinishProcess(String username, String password) throws IOException, InterruptedException {
        Login(username, password);

        JSONArray items = readProductListJson();

        for (int i = 0; i < items.length(); i++) {
            for (int j = 0; j <= i; j++) {
                String addToCartId = items.getJSONObject(j).getString("add_to_cart_id");
                WebElement addToCartBtn = driver.findElement(By.id(addToCartId));
                addToCartBtn.click();
            }

            shopping_Cart_Btn().click();

            checkOut_Btn().click();
            fillValidInfo();
            finish_Btn().click();
            Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-complete.html");
            Assert.assertEquals(complete_Header().getText(),"Thank you for your order!");
            back_Home_Btn().click();
            Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");

        }

    }
}
