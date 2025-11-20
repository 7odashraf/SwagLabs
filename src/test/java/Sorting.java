
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Collections;
import java.util.List;



public class Sorting extends BaseClass {
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


    public void Login() {
        username().sendKeys("standard_user");
        password().sendKeys("secret_sauce");
        LoginBtn().click();
    }



    @Test(priority = 5)
    public void sortingByNameAtoZ() {
        Login();
        List<String> Items_names_Before_Sort = getItemNames();
        sortAtoZ().click();
        List<String> Items_names_After_Sort = getItemNames();

        Collections.sort(Items_names_Before_Sort);
        Assert.assertEquals(sortAtoZ().getText(), "Name (A to Z)");
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort);
    }

    @Test(priority = 6)
    public void sortingByNameZtoA() {
        Login();
        List<String> Items_names_Before_Sort = getItemNames();
        sortZtoA().click();
        List<String> Items_names_After_Sort = getItemNames();

        Collections.sort(Items_names_Before_Sort, Collections.reverseOrder());
        Assert.assertEquals(sortZtoA().getText(), "Name (Z to A)");
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort);
    }

    @Test(priority = 7)
    public void sortingByPriceLowtoHigh() {
        Login();
        List<Double> Items_prices_Before_Sort = getItemPrices();
        Items_prices_Before_Sort.forEach(e -> System.out.println("Before: " + e));
        sortLowtoHigh().click();
        List<Double> Items_prices_After_Sort = getItemPrices();

        Collections.sort(Items_prices_Before_Sort);
        Items_prices_Before_Sort.forEach(e -> System.out.println("Before2: " + e));
        Items_prices_After_Sort.forEach(e -> System.out.println("After: " + e));
        Assert.assertEquals(sortLowtoHigh().getText(), "Price (low to high)");
        Assert.assertEquals(Items_prices_Before_Sort, Items_prices_After_Sort);

    }

    @Test(priority = 8)
    public void sortingByPriceHightoLow() {
        Login();
        List<Double> Items_prices_Before_Sort = getItemPrices();
        sortHightoLow().click();
        List<Double> Items_prices_After_Sort = getItemPrices();

        Collections.sort(Items_prices_Before_Sort, Collections.reverseOrder());
        Assert.assertEquals(sortHightoLow().getText(), "Price (high to low)");
        Assert.assertEquals(Items_prices_Before_Sort, Items_prices_After_Sort);

    }
}