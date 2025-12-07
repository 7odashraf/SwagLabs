package tests;

import Pages.*;
import Pages.LoginPage;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends BaseClass {

    private static final Logger log = LoggerClass.getLogger();

    @Test(dataProvider = "loginData")
    public void testLogin(String username,String password,String errorMessage) {
        log.info("===== Starting Login Test for user: {} =====", username);
        LoginPage loginPage = new LoginPage(driver);

        log.info("Entering username: {}", username);
        loginPage.enterUsername(username);

        log.info("Entering password: {}", password);
        loginPage.enterPassword(password);

        log.info("Clicking login button");
        loginPage.clickLogin();

        // Get the error message displayed on the page, if any
        String error_Message= loginPage.error_Message_Test();

        // Check if no error message is returned (successful login)
        if (error_Message == null){
            Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html",
                    "User: '"+username+"', The URL should redirect to the inventory page after a successful login.");
            log.info("Login SUCCESS for user: {}", username);
        } else {

        // Assert that the error message matches the expected error message (in case of login failure)
        Assert.assertEquals(errorMessage, error_Message,
                "User: '"+username+"', The error message displayed does not match the expected error message.");

            log.info("Correct error message displayed for invalid login attempt of user: {}", username);
        }

        log.info("===== Finished Login Test for user: {} =====", username);    }

}
