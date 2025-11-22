package tests;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Collections;
import java.util.List;

public class SortingTest extends BaseClass {

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


    public void Login(String username, String password) {
        username().sendKeys(username);
        password().sendKeys(password);
        LoginBtn().click();
    }



    @Test(dataProvider = "loginSortingData")
    public void sortingByNameAtoZ(String username, String password) {
        Login(username, password);
        List<String> Items_names_Before_Sort = getItemNames();
        sortAtoZ().click();
        List<String> Items_names_After_Sort = getItemNames();

        Collections.sort(Items_names_Before_Sort);
        Assert.assertEquals(sortAtoZ().getText(), "Name (A to Z)");
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort);
    }

    @Test(dataProvider = "loginSortingData")
    public void sortingByNameZtoA(String username, String password) {
        Login(username,password);
        List<String> Items_names_Before_Sort = getItemNames();
        sortZtoA().click();
        List<String> Items_names_After_Sort = getItemNames();

        Collections.sort(Items_names_Before_Sort, Collections.reverseOrder());
        Assert.assertEquals(sortZtoA().getText(), "Name (Z to A)");
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort);
    }

    @Test(dataProvider = "loginSortingData")
    public void sortingByPriceLowtoHigh(String username, String password) {
        Login(username,password);
        List<Double> Items_prices_Before_Sort = getItemPrices();
        sortLowtoHigh().click();
        List<Double> Items_prices_After_Sort = getItemPrices();

        Collections.sort(Items_prices_Before_Sort);
        Assert.assertEquals(sortLowtoHigh().getText(), "Price (low to high)");
        Assert.assertEquals(Items_prices_Before_Sort, Items_prices_After_Sort);

    }

    @Test(dataProvider = "loginSortingData")
    public void sortingByPriceHightoLow(String username, String password) {
        Login(username,password);
        List<Double> Items_prices_Before_Sort = getItemPrices();
        sortHightoLow().click();
        List<Double> Items_prices_After_Sort = getItemPrices();

        Collections.sort(Items_prices_Before_Sort, Collections.reverseOrder());
        Assert.assertEquals(sortHightoLow().getText(), "Price (high to low)");
        Assert.assertEquals(Items_prices_Before_Sort, Items_prices_After_Sort);

    }
}