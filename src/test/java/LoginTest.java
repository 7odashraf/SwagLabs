import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends BaseClass{

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


    @Test(priority = 0)
    public void testValidLogin() {
        username().sendKeys("standard_user");
        password().sendKeys("secret_sauce");
        LoginBtn().click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
    }

    @Test(priority = 1)
    public void testInvalidLogin() {
        username().sendKeys("invalid_user");
        password().sendKeys("invalid_password");
        LoginBtn().click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/");
        Assert.assertEquals("Epic sadface: Username and password do not match any user in this service", error_Message_Test());
    }

    @Test(priority = 2)
    public void testEmptyUsername() {
        username().sendKeys("");
        password().sendKeys("secret_sauce");
        LoginBtn().click();

        Assert.assertEquals("Epic sadface: Username is required", error_Message_Test());
    }

    @Test(priority = 3)
    public void testEmptyPassword() {
        username().sendKeys("standard_user");
        password().sendKeys("");
        LoginBtn().click();

        Assert.assertEquals("Epic sadface: Password is required", error_Message_Test());
    }

    @Test(priority = 4)
    public void testEmptyFields() {
        username().sendKeys("");
        password().sendKeys("");
        LoginBtn().click();

        Assert.assertEquals("Epic sadface: Username is required", error_Message_Test());
    }
}

