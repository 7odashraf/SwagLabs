package tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public int getCartBadgeNumber() {
        List<WebElement> badgeElements = driver.findElements(By.className("shopping_cart_badge"));
        if (badgeElements.isEmpty()) { return 0;}   // shopping cart Empty

        return Integer.parseInt(badgeElements.get(0).getText());
    }
    public WebElement getLastItemFromCart() {
        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_item"));

        if (cartItems.isEmpty()) {
            throw new NoSuchElementException("The cart is empty, no items to retrieve.");
        }
        return cartItems.get(cartItems.size() - 1);
    }
    WebElement shopping_Cart_Btn(){
        // adding JavascriptExecutor to handle unclickable element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.shopping_cart_link")));}
    WebElement continue_Shopping_Btn(){ return driver.findElement(By.id("continue-shopping"));}
    WebElement checkOut_Btn(){ return driver.findElement(By.id("checkout"));}

    // Locators for Checkout Page
    WebElement firstname_Input(){return driver.findElement(By.id("first-name"));}
    WebElement lastname_Input(){return driver.findElement(By.id("last-name"));}
    WebElement zip_Code_Input(){return driver.findElement(By.id("postal-code"));}
    WebElement continue_CheckOut_Btn(){ return driver.findElement(By.id("continue"));}
    String getErrorMessage() {
        if (driver.findElements(By.cssSelector("div.error-message-container.error h3[data-test='error']")).size() > 0) {
            return driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        } else {
            return "";
        }
    }

    public JSONArray readCheckoutDataJson() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("./src/test/java/tests/CheckoutData.json")));
        JSONObject obj = new JSONObject(json);
        return obj.getJSONArray("testCases");
    }


    // Locators for Overview Page
    WebElement finish_Btn(){ return driver.findElement(By.id("finish"));}
    WebElement complete_Header(){ return driver.findElement(By.cssSelector("h2.complete-header"));}
    WebElement back_Home_Btn(){ return driver.findElement(By.id("back-to-products"));}

    // Locators for Logout
    WebElement logout_Btn(){ WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));}
    WebElement menu_Btn(){ WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-burger-menu-btn")));}


    public void Login(String username, String password){
        username().sendKeys(username);
        password().sendKeys(password);
        LoginBtn().click();
    }


}
