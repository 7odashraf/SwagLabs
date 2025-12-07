package tests;

import Pages.LoggerClass;
import Pages.LoginPage;
import Pages.Logout;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogoutTest extends BaseClass{

    private static final Logger log = LoggerClass.getLogger();

    @Test(dataProvider="ValidLoginData")
    public void Logout(String username,String password){
        log.info("Starting Logout Test for user: " + username);

        LoginPage login = new LoginPage(driver);
        Logout logout = new Logout(driver);

        log.info("Attempting to login with username: " + username);
        login.Login(username, password);

        log.info("Clicking the menu to log out.");
        logout.clickMenu();

        log.info("Clicking logout.");
        logout.clickLogout();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/",
                "User: "+username+" The URL should redirect to the login page after logout.");

        log.info("Logout test completed for user: " + username);
    }
}
