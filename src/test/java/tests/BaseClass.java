package tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseClass {

    WebDriver driver;

    @BeforeTest
    public void setup() {
        driver = new FirefoxDriver(); // FirefoxDriver
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterTest
    public void teardown(){
        driver.quit();
    }


    // DataProviders
    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() {
        return new Object[][]{
                // Standard user
                {new User("standard_user", "secret_sauce", null, "John", "Doe", "12345")},

                // Invalid user
                {new User("invalid_user", "invalid_user", "Epic sadface: Username and password do not match any user in this service", "Jane", "Smith", "54321")},

                // Empty username
                {new User("", "secret_sauce", "Epic sadface: Username is required", "Robert", "Johnson", "11223")},

                // Empty password
                {new User("standard_user", "", "Epic sadface: Password is required", "Emily", "Davis", "22334") },

                // User4 with empty password (edge case)
                { new User("locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out.", "Emily", "Davis", "22334") },

                // Problem user
                { new User("problem_user", "secret_sauce", null, "Chris", "Lee", "33445") },

                // Performance glitch user
                { new User("performance_glitch_user", "secret_sauce", null, "Natalie", "Wilson", "00000") },

                // Error user
                { new User("error_user", "secret_sauce", null, "Michael", "Taylor", "44556") },

                // Visual user
                {new User("visual_user", "secret_sauce", null, "Emily", "Davis", "22334") }
        };
    }
    @DataProvider(name = "loginSortingData")
    public Object[][] sortingDataProvider() {
        return new Object[][]{
                { "standard_user", "secret_sauce" },  // Standard user
                { "problem_user", "secret_sauce" },  // Problem user
                { "performance_glitch_user", "secret_sauce" },  // Performance glitch user
                { "error_user", "secret_sauce" },  // Error user
                { "visual_user", "secret_sauce" },  // Visual user
        };
    }


    // Locators for Login Page
    WebElement username() { return driver.findElement(By.id("user-name")); }
    WebElement password(){
        return driver.findElement(By.id("password"));
    }
    WebElement LoginBtn(){
        return driver.findElement(By.id("login-button"));
    }

    String error_Message_Test() {
        try {
            return driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public JSONArray readProductListJson() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("./src/test/java/tests/ProductList.json")));
        JSONObject obj = new JSONObject(json);
        return obj.getJSONArray("inventory_list");
    }

    // Locators for Sorting Page
    WebElement sortAtoZ() { return driver.findElement(By.cssSelector("option[value='az']"));}
    WebElement sortZtoA() { return driver.findElement(By.cssSelector("option[value='za']"));}
    WebElement sortHightoLow() { return driver.findElement(By.cssSelector("option[value='hilo']"));}
    WebElement sortLowtoHigh() { return driver.findElement(By.cssSelector("option[value='lohi']"));}
    public List<String> getItemNames() {
        List<WebElement> Item_Names = driver.findElements(By.cssSelector("[data-test='inventory-item-name']"));
        return new ArrayList<>(
                Item_Names.stream().map(WebElement::getText)
                        .collect(Collectors.toList()));
    }
    public List<Double> getItemPrices() {
        List<WebElement> itemPrices = driver.findElements(By.cssSelector("[data-test='inventory-item-price']"));
        return itemPrices.stream()
                .map(price -> {
                    String priceText = price.getText().replace("$", "");  // Remove the dollar sign
                    return Double.parseDouble(priceText);  // Convert the string to double
                })
                .collect(Collectors.toList());
    }


    // Locators for Cart Page
    WebElement shopping_Cart_Btn(){ return driver.findElement(By.cssSelector("a.shopping_cart_link"));}
    WebElement checkOut_Btn(){ return driver.findElement(By.id("checkout"));}

    // Locators for Checkout Page
    WebElement firstname_Input(){return driver.findElement(By.id("first-name"));}
    WebElement lastname_Input(){return driver.findElement(By.id("last-name"));}
    WebElement zip_Code_Input(){return driver.findElement(By.id("postal-code"));}
    WebElement continue_CheckOut_Btn(){ return driver.findElement(By.id("continue"));}

    // Locators for Overview Page
    WebElement finish_Btn(){ return driver.findElement(By.id("finish"));}
    WebElement complete_Header(){ return driver.findElement(By.cssSelector("h2.complete-header"));}
    WebElement back_Home_Btn(){ return driver.findElement(By.id("back-to-products"));}

    // Locators for Logout
    WebElement logout_Btn(){ return driver.findElement(By.id("logout_sidebar_link"));}
    WebElement menu_Btn(){ return driver.findElement(By.id("react-burger-menu-btn"));}


    public void Login(String username, String password){
        username().sendKeys(username);
        password().sendKeys(password);
        LoginBtn().click();
    }


}
