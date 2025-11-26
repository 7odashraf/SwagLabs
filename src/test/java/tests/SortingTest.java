package tests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Collections;
import java.util.List;

public class SortingTest extends BaseClass {


    @Test(dataProvider = "ValidLoginData")
    public void sortingByNameAtoZ(String username, String password) {
        Login(username, password);
        List<String> Items_names_Before_Sort = getItemNames();
        sortAtoZ().click();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // or dismiss()
        } catch (NoAlertPresentException e) {
            // no alert, nothing to do
        }
        List<String> Items_names_After_Sort = getItemNames();

        Collections.sort(Items_names_Before_Sort);
        Assert.assertEquals(sortAtoZ().getText(), "Name (A to Z)");
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort);
    }

    @Test(dataProvider = "ValidLoginData")
    public void sortingByNameZtoA(String username, String password) {
        Login(username,password);
        List<String> Items_names_Before_Sort = getItemNames();
        sortZtoA().click();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // or dismiss()
        } catch (NoAlertPresentException e) {
            // no alert, nothing to do
        }
        List<String> Items_names_After_Sort = getItemNames();

        Collections.sort(Items_names_Before_Sort, Collections.reverseOrder());
        Assert.assertEquals(sortZtoA().getText(), "Name (Z to A)");
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort);
    }

    @Test(dataProvider = "ValidLoginData")
    public void sortingByPriceLowtoHigh(String username, String password) {
        Login(username,password);
        List<Double> Items_prices_Before_Sort = getItemPrices();
        sortLowtoHigh().click();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // or dismiss()
        } catch (NoAlertPresentException e) {
            // no alert, nothing to do
        }
        List<Double> Items_prices_After_Sort = getItemPrices();

        Collections.sort(Items_prices_Before_Sort);
        Assert.assertEquals(sortLowtoHigh().getText(), "Price (low to high)");
        Assert.assertEquals(Items_prices_Before_Sort, Items_prices_After_Sort);

    }

    @Test(dataProvider = "ValidLoginData")
    public void sortingByPriceHightoLow(String username, String password) {
        Login(username,password);
        List<Double> Items_prices_Before_Sort = getItemPrices();
        sortHightoLow().click();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // or dismiss()
        } catch (NoAlertPresentException e) {
            // no alert, nothing to do
        }
        List<Double> Items_prices_After_Sort = getItemPrices();

        Collections.sort(Items_prices_Before_Sort, Collections.reverseOrder());
        Assert.assertEquals(sortHightoLow().getText(), "Price (high to low)");
        Assert.assertEquals(Items_prices_Before_Sort, Items_prices_After_Sort);

    }
}