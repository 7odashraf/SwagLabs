package tests;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTest extends BaseClass{

    @Test(dataProvider="ValidLoginData")
    public void Logout(String username,String password){
        Login(username,password);

        menu_Btn().click();
        logout_Btn().click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/");
    }
}
