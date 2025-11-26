package tests;//package tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;




public class InventoryTest extends BaseClass {

    @BeforeMethod
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }

    @Test(dataProvider = "loginSortingData")
    public void test_Add_Remove_Item(String username, String password) throws IOException {
        Login(username, password);

        JSONArray items = readProductListJson();

        for (int i = 0; i < items.length(); i++) {
            String addToCartId = items.getJSONObject(i).getString("add_to_cart_id");
            String RemoveFromCartId = items.getJSONObject(i).getString("remove_from_cart_id");

            WebElement addBtn =driver.findElement(By.id(addToCartId));
            addBtn.click();

            List<WebElement> removeBtnList = driver.findElements(By.id(RemoveFromCartId));
            Assert.assertTrue(removeBtnList.size() > 0, items.getJSONObject(i).getString("name")+": add to cart bottun unclickable.");

            WebElement removeBtn =driver.findElement(By.id(RemoveFromCartId));
            removeBtn.click();

            List<WebElement> addBtnList = driver.findElements(By.id(addToCartId));
            Assert.assertTrue(addBtnList.size() > 0, items.getJSONObject(i).getString("name")+": remove form cart bottun unclickable.");

        }
    }
    @Test(dataProvider = "loginSortingData")
    public void testInventoryItems(String username, String password) throws IOException {
        Login(username, password);

        JSONArray items = readProductListJson();


        List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));

        for (int i = 0; i < inventoryItems.size(); i++) {
            WebElement item = inventoryItems.get(i);

            String itemName = item.findElement(By.cssSelector(".inventory_item_name")).getText();
            String itemDescription = item.findElement(By.cssSelector(".inventory_item_desc")).getText();
            String itemPrice = item.findElement(By.cssSelector(".inventory_item_price")).getText();

            JSONObject product = items.getJSONObject(i);
            String expectedName = product.getString("name");
            String expectedDescription = product.getString("description");
            String expectedPrice = product.getString("price");

            Assert.assertEquals(itemName, expectedName, "The name of the product is incorrect for item " + (i + 1));
            Assert.assertEquals(itemDescription, expectedDescription, "The description of the product is incorrect for item " + (i + 1));
            Assert.assertEquals(itemPrice, expectedPrice, "The price of the product is incorrect for item " + (i + 1));
        }
    }

}
