package tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BaseClass {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new FirefoxDriver();      // Edit this with your available Drive
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }


    // DataProviders
    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() {
        return new Object[][]{// Data provider for LoginTest with erorr messages
                {"standard_user", "secret_sauce", null}, // Standard user
                {"invalid_user", "invalid_user", "Epic sadface: Username and password do not match any user in this service"}, // Invalid user
                {"", "secret_sauce", "Epic sadface: Username is required"}, // Empty username
                {"standard_user", "", "Epic sadface: Password is required" }, // Empty password
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out." }, //  empty password
                {"problem_user", "secret_sauce", null }, // Problem user
                {"performance_glitch_user", "secret_sauce", null }, // Performance glitch user
                {"error_user", "secret_sauce", null }, // Error user
                {"visual_user", "secret_sauce", null } // Visual user
        };
    }
    @DataProvider(name = "ValidLoginData")
    public Object[][] validDataProvider() {
        return new Object[][]{
                { "standard_user", "secret_sauce" },  // Standard user
                { "problem_user", "secret_sauce" },  // Problem user
                { "performance_glitch_user", "secret_sauce" },  // Performance glitch user
                { "error_user", "secret_sauce" },  // Error user
                { "visual_user", "secret_sauce" },  // Visual user
        };
    }

    // Reads from a JSON files
    public JSONArray readProductListJson() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("./src/test/resources/testdata/ProductList.json")));
        JSONObject obj = new JSONObject(json);
        return obj.getJSONArray("inventory_list");
    }

    public JSONArray readCheckoutDataJson() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("./src/test/resources/testdata/CheckoutData.json")));
        JSONObject obj = new JSONObject(json);
        return obj.getJSONArray("testCases");
    }


}
