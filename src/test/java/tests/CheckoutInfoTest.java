package tests;

import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Objects;

public class CheckoutInfoTest extends BaseClass {

    @Test(dataProvider = "ValidLoginData")
    public void testCheckoutPage(String username, String password) throws IOException {
        Login(username, password);

        JSONArray items = readProductListJson();

        for (int i = 0; i < items.length(); i++) {
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");

            WebElement addBtn = driver.findElement(By.id(addToCartId));
            addBtn.click();

            String expectedName = items.getJSONObject(i).getString("name");

            shopping_Cart_Btn().click();

            String itemName = getLastItemFromCart().findElement(By.cssSelector(".inventory_item_name")).getText();
            Assert.assertEquals(itemName, expectedName, "The product: " + itemName + " name does not match the expected name.");

            checkOut_Btn().click();


            JSONArray info = readCheckoutDataJson();
            for (int infoNum=0;infoNum< info.length();infoNum++){

                firstname_Input().sendKeys(info.getJSONObject(infoNum).getString("firstName"));
                lastname_Input().sendKeys(info.getJSONObject(infoNum).getString("lastName"));
                zip_Code_Input().sendKeys(info.getJSONObject(infoNum).getString("zipCode"));

                continue_CheckOut_Btn().click();

                Assert.assertEquals(getErrorMessage(),info.getJSONObject(infoNum).getString("errorMessage"),"");
                if (Objects.equals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html")){
                    driver.get("https://www.saucedemo.com/checkout-step-one.html");
                }

            }
            driver.get("https://www.saucedemo.com/inventory.html");

        }
    }
}
