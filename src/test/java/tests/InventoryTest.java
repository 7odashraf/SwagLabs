package tests;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
    @DataProvider(name = "loginSortingData")
    public Object[][] sortingDataProvider() {
        return new Object[][]{
                { "standard_user", "secret_sauce" },  // Standard user
                { "problem_user", "secret_sauce" },  // Problem user
                { "performance_glitch_user", "secret_sauce" },  // Performance glitch user
                { "error_user", "secret_sauce" },  // Error user
                { "visual_user", "secret_sauce" },  // Visual user
        };
    }
    public void Login(String username, String password) {
        username().sendKeys(username);
        password().sendKeys(password);
        LoginBtn().click();
    }

    @Test(dataProvider = "loginSortingData")
    public void testAddRemoveItem1(String username, String password) {
        Login(username, password);

        item_1_addToCart_Btn().click();
        Assert.assertTrue(item_1_Remove_Btn().isDisplayed());

        item_1_Remove_Btn().click();
        Assert.assertTrue(item_1_addToCart_Btn().isDisplayed());
    }
    @Test(dataProvider = "loginSortingData")
    public void testAddRemoveItem2(String username, String password) {
        Login(username, password);

        item_2_addToCart_Btn().click();
        Assert.assertTrue(item_2_Remove_Btn().isDisplayed());

        item_2_Remove_Btn().click();
        Assert.assertTrue(item_2_addToCart_Btn().isDisplayed());
    }
    @Test(dataProvider = "loginSortingData")
    public void testAddRemoveItem3(String username, String password) {
        Login(username, password);
        item_3_addToCart_Btn().click();
        Assert.assertTrue(item_3_Remove_Btn().isDisplayed());

        item_3_Remove_Btn().click();
        Assert.assertTrue(item_3_addToCart_Btn().isDisplayed());
    }
    @Test(dataProvider = "loginSortingData")
    public void testAddRemoveItem4(String username, String password) {
        Login(username, password);
        item_4_addToCart_Btn().click();
        Assert.assertTrue(item_4_Remove_Btn().isDisplayed());

        item_4_Remove_Btn().click();
        Assert.assertTrue(item_4_addToCart_Btn().isDisplayed());
    }
    @Test(dataProvider = "loginSortingData")
    public void testAddRemoveItem5(String username, String password) {
        Login(username, password);
        item_5_addToCart_Btn().click();
        Assert.assertTrue(item_5_Remove_Btn().isDisplayed());

        item_5_Remove_Btn().click();
        Assert.assertTrue(item_5_addToCart_Btn().isDisplayed());
    }
    @Test(dataProvider = "loginSortingData")
    public void testAddRemoveItem6(String username, String password) {
        Login(username, password);
        item_6_addToCart_Btn().click();
        Assert.assertTrue(item_6_Remove_Btn().isDisplayed());

        item_6_Remove_Btn().click();
        Assert.assertTrue(item_6_addToCart_Btn().isDisplayed());
    }

}
