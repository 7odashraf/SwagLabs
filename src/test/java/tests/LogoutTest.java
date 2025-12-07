package tests;

import Pages.LoginPage;
import Pages.Logout;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogoutTest extends BaseClass{

    @Test(dataProvider="ValidLoginData")
    public void Logout(String username,String password){
        LoginPage login = new LoginPage(driver);
        Logout logout = new Logout(driver);

        login.Login(username, password);

        logout.clickMenu();
        logout.clickLogout();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/",
                "User: "+username+" The URL should redirect to the login page after logout.");
    }
}
