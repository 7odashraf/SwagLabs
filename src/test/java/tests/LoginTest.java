package tests;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends BaseClass {

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


    @Test(dataProvider = "loginData")
    public void testLogin(User user) {
        username().sendKeys(user.username);
        password().sendKeys(user.password);
        LoginBtn().click();

//        System.out.println("Username: " + user.username + " Password: " + user.password);

        String error_Message= error_Message_Test();
        if (error_Message == null){
            Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
        }
        Assert.assertEquals(user.errorMessage, error_Message);
    }
}
