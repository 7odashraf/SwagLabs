import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseClass {

    WebDriver driver;


    //some web elements
    WebElement username() { return driver.findElement(By.id("user-name")); }
    WebElement password(){
        return driver.findElement(By.id("password"));
    }
    WebElement LoginBtn(){
        return driver.findElement(By.id("login-button"));
    }

    String error_Message_Test() { return driver.findElement(By.cssSelector("h3[data-test='error']")).getText(); }



    WebElement item_1_addToCart_Btn() { return driver.findElement(By.id("add-to-cart-sauce-labs-backpack")); }
    WebElement item_2_addToCart_Btn(){
        return driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
    }
    WebElement item_3_addToCart_Btn(){
        return driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"));
    }
    WebElement item_4_addToCart_Btn(){
        return driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));
    }
    WebElement item_5_addToCart_Btn(){
        return driver.findElement(By.id("add-to-cart-sauce-labs-onesie"));
    }
    WebElement item_6_addToCart_Btn() { return driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")); }


    WebElement item_1_Remove_Btn() { return driver.findElement(By.id("remove-sauce-labs-backpack")); }
    WebElement item_2_Remove_Btn() { return driver.findElement(By.id("remove-sauce-labs-bike-light")); }
    WebElement item_3_Remove_Btn() { return driver.findElement(By.id("remove-sauce-labs-bolt-t-shirt")); }
    WebElement item_4_Remove_Btn() { return driver.findElement(By.id("remove-sauce-labs-fleece-jacket")); }
    WebElement item_5_Remove_Btn() { return driver.findElement(By.id("remove-sauce-labs-onesie")); }
    WebElement item_6_Remove_Btn() { return driver.findElement(By.id("remove-test.allthethings()-t-shirt-(red)")); }

    WebElement sortAtoZ() { return driver.findElement(By.cssSelector("option[value='az']"));}
    WebElement sortZtoA() { return driver.findElement(By.cssSelector("option[value='za']"));}
    WebElement sortHightoLow() { return driver.findElement(By.cssSelector("option[value='hilo']"));}
    WebElement sortLowtoHigh() { return driver.findElement(By.cssSelector("option[value='lohi']"));}

    WebElement shopping_Cart_Btn(){ return driver.findElement(By.cssSelector("a.shopping_cart_link"));}
    WebElement checkOut_Btn(){ return driver.findElement(By.id("checkout"));}

    WebElement firstname_Input(){return driver.findElement(By.id("first-name"));}
    WebElement lastname_Input(){return driver.findElement(By.id("last-name"));}
    WebElement zip_Code_Input(){return driver.findElement(By.id("postal-code"));}

    WebElement continue_CheckOut_Btn(){ return driver.findElement(By.id("continue"));}

    WebElement finish_Btn(){ return driver.findElement(By.id("finish"));}
    WebElement complete_Header(){ return driver.findElement(By.cssSelector("h2.complete-header"));}
    WebElement back_Home_Btn(){ return driver.findElement(By.id("back-to-products"));}

    WebElement logout_Btn(){ return driver.findElement(By.id("logout_sidebar_link"));}

    WebElement menu_Btn(){ return driver.findElement(By.id("react-burger-menu-btn"));}

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



    @BeforeTest
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterTest
    public void teardown(){
        driver.quit();
    }

}
