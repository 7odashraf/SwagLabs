import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Inventory extends BaseClass {

//    @BeforeMethod
//    public void setup() {
//        driver = new FirefoxDriver();
//        driver.manage().window().maximize();
//        driver.get("https://www.saucedemo.com/");
//    }
//
//    @AfterMethod
//    public void teardown(){
//        driver.quit();
//    }


    public void Login() {
        username().sendKeys("standard_user");
        password().sendKeys("secret_sauce");
        LoginBtn().click();
    }

    @Test
    public void testAddRemoveItem1(){
        Login();

        item_1_addToCart_Btn().click();
        Assert.assertTrue(item_1_Remove_Btn().isDisplayed());

        item_1_Remove_Btn().click();
        Assert.assertTrue(item_1_addToCart_Btn().isDisplayed());
    }
    @Test
    public void testAddRemoveItem2(){
        item_2_addToCart_Btn().click();
        Assert.assertTrue(item_2_Remove_Btn().isDisplayed());

        item_2_Remove_Btn().click();
        Assert.assertTrue(item_2_addToCart_Btn().isDisplayed());
    }
    @Test
    public void testAddRemoveItem3(){
        item_3_addToCart_Btn().click();
        Assert.assertTrue(item_3_Remove_Btn().isDisplayed());

        item_3_Remove_Btn().click();
        Assert.assertTrue(item_3_addToCart_Btn().isDisplayed());
    }
    @Test
    public void testAddRemoveItem4(){
        item_4_addToCart_Btn().click();
        Assert.assertTrue(item_4_Remove_Btn().isDisplayed());

        item_4_Remove_Btn().click();
        Assert.assertTrue(item_4_addToCart_Btn().isDisplayed());
    }
    @Test
    public void testAddRemoveItem5(){
        item_5_addToCart_Btn().click();
        Assert.assertTrue(item_5_Remove_Btn().isDisplayed());

        item_5_Remove_Btn().click();
        Assert.assertTrue(item_5_addToCart_Btn().isDisplayed());
    }
    @Test
    public void testAddRemoveItem6(){
        item_6_addToCart_Btn().click();
        Assert.assertTrue(item_6_Remove_Btn().isDisplayed());

        item_6_Remove_Btn().click();
        Assert.assertTrue(item_6_addToCart_Btn().isDisplayed());
    }

}
