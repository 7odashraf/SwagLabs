import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Login {
    WebDriver driver;


    //some web elements
    WebElement username(){
        return driver.findElement(By.id("user-name"));
    }
    WebElement password(){
        return driver.findElement(By.id("password"));
    }
    WebElement LoginBtn(){
        return driver.findElement(By.id("login-button"));
    }



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








    @BeforeTest
    public void prepare() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterTest
    public void teardown(){
        driver.quit();
    }

    @Test
    public void standerUser(){
        username().sendKeys("standard_user");
        password().sendKeys("secret_sauce");
        LoginBtn().click();

//        System.out.println(driver.getCurrentUrl().equals("www."));
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
//        Assert.assertTrue(driver.getTitle().equals("https://www.saucedemo.com/inventory.html"),"");
//        Assert.assertTrue(driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html"),"");
    }


    @Test(dependsOnMethods = "standerUser")
    public void addToCart(){
        item_1_addToCart_Btn().click();
        item_2_addToCart_Btn().click();
        item_5_addToCart_Btn().click();
        item_6_addToCart_Btn().click();


        Assert.assertTrue(item_1_Remove_Btn().isDisplayed());


    }

    @Test(dependsOnMethods = "addToCart")
    public void checkOut(){
        shopping_Cart_Btn().click();

        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/cart.html");

        checkOut_Btn().click();
        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-step-one.html");



    }

    @Test(dependsOnMethods = "checkOut")
    public void checkOut_Info(){
        firstname_Input().sendKeys("Mahmoud");
        lastname_Input().sendKeys("Ashraf");
        zip_Code_Input().sendKeys("123456");

        continue_CheckOut_Btn().click();
        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-step-two.html");

    }

    @Test(dependsOnMethods = "checkOut_Info")
    public void finishProcess(){
        finish_Btn().click();

        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-complete.html");
        assertEquals(complete_Header().getText(),"Thank you for your order!");

        back_Home_Btn().click();

        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");

    }

    @Test(dependsOnMethods = "finishProcess")
    public void Logout(){
        menu_Btn().click();
        logout_Btn().click();

        assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/");

    }


}
