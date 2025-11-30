package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LogoutTest extends BaseClass{

    @Test(dataProvider="ValidLoginData")
    public void Logout(String username,String password){
        Login(username,password);

        menu_Btn().click();
        logout_Btn().click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/",
                "User: "+username+" The URL should redirect to the login page after logout.");
    }
}
