import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Login extends BaseClass{


    @Test
    public void standerUser(){
        username().sendKeys("standard_user");
        password().sendKeys("secret_sauce");
        LoginBtn().click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
    }


    @Test(dependsOnMethods = "standerUser")
    public void addToCart(){
        item_1_addToCart_Btn().click();
        item_2_addToCart_Btn().click();
        item_5_addToCart_Btn().click();
        item_6_addToCart_Btn().click();


        Assert.assertTrue(item_1_Remove_Btn().isDisplayed());


    }

    @Test(dependsOnMethods = "addToCart")
    public void checkOut(){
        shopping_Cart_Btn().click();

        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/cart.html");

        checkOut_Btn().click();
        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-step-one.html");



    }

    @Test(dependsOnMethods = "checkOut")
    public void checkOut_Info(){
        firstname_Input().sendKeys("Mahmoud");
        lastname_Input().sendKeys("Ashraf");
        zip_Code_Input().sendKeys("123456");

        continue_CheckOut_Btn().click();
        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-step-two.html");

    }

    @Test(dependsOnMethods = "checkOut_Info")
    public void finishProcess(){
        finish_Btn().click();

        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-complete.html");
        assertEquals(complete_Header().getText(),"Thank you for your order!");

        back_Home_Btn().click();

        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");

    }

    @Test(dependsOnMethods = "finishProcess")
    public void Logout(){
        menu_Btn().click();
        logout_Btn().click();

        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/");

    }


}
