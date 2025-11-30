package tests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Collections;
import java.util.List;

public class SortingTest extends BaseClass {


    @Test(dataProvider = "ValidLoginData")
    public void sortingByNameAtoZ(String username, String password) {
        Login(username, password);

        // Get the list of item names before sorting
        List<String> Items_names_Before_Sort = getItemNames();
        sortAtoZ().click();

        // Handle any alerts that may appear after sorting
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Accept the alert if it appears
        } catch (NoAlertPresentException e) {
            // no alert, nothing to do
        }

        // Get the list of item names after sorting
        List<String> Items_names_After_Sort = getItemNames();

        // Sort the list of item names alphabetically for comparison
        Collections.sort(Items_names_Before_Sort);


        // Assert that the "Sort A to Z" button text is correct
        Assert.assertEquals(sortAtoZ().getText(), "Name (A to Z)", "User: '"+username+"', Button text should be 'Name (A to Z)'");
        // Assert that the list of items is sorted correctly
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort, "User: '"+username+"', Item names should be sorted in ascending alphabetical order.");
    }

    @Test(dataProvider = "ValidLoginData")
    public void sortingByNameZtoA(String username, String password) {
        Login(username,password);

        // Get the list of item names before sorting
        List<String> Items_names_Before_Sort = getItemNames();
        sortZtoA().click();

        // Handle any alerts that may appear after sorting
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Accept the alert if it appears
        } catch (NoAlertPresentException e) {
            // no alert, nothing to do
        }
        // Get the list of item names after sorting
        List<String> Items_names_After_Sort = getItemNames();

        // Sort the list of item names in reverse order for comparison
        Collections.sort(Items_names_Before_Sort, Collections.reverseOrder());

        // Assert that the "Sort Z to A" button text is correct
        Assert.assertEquals(sortZtoA().getText(), "Name (Z to A)", "User: '"+username+"', Button text should be 'Name (Z to A)'");
        // Assert that the "Sort Z to A" button text is correct
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort, "User: '"+username+"', Item names should be sorted in descending alphabetical order.");
    }

    @Test(dataProvider = "ValidLoginData")
    public void sortingByPriceLowtoHigh(String username, String password) {
        Login(username,password);

        // Get the list of item prices before sorting
        List<Double> Items_prices_Before_Sort = getItemPrices();
        sortLowtoHigh().click();

        // Handle any alerts that may appear after sorting
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Accept the alert if it appears
        } catch (NoAlertPresentException e) {
            // no alert, nothing to do
        }

        // Get the list of item prices after sorting
        List<Double> Items_prices_After_Sort = getItemPrices();

        // Sort the list of item prices in ascending order for comparison
        Collections.sort(Items_prices_Before_Sort);

        // Assert that the "Sort Low to High" button text is correct
        Assert.assertEquals(sortLowtoHigh().getText(), "Price (low to high)", "User: '"+username+"', Button text should be 'Price (low to high)'");
        // Assert that the "Sort Low to High" button text is correct
        Assert.assertEquals(Items_prices_Before_Sort, Items_prices_After_Sort, "User: '"+username+"', Item prices should be sorted in ascending order.");

    }

    @Test(dataProvider = "ValidLoginData")
    public void sortingByPriceHightoLow(String username, String password) {
        Login(username,password);

        // Get the list of item prices before sorting
        List<Double> Items_prices_Before_Sort = getItemPrices();
        sortHightoLow().click();

        // Handle any alerts that may appear after sorting
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Accept the alert if it appears
        } catch (NoAlertPresentException e) {
            // no alert, nothing to do
        }

        // Get the list of item prices after sorting
        List<Double> Items_prices_After_Sort = getItemPrices();

        // Sort the list of item prices in descending order for comparison
        Collections.sort(Items_prices_Before_Sort, Collections.reverseOrder());

        // Assert that the "Sort High to Low" button text is correct
        Assert.assertEquals(sortHightoLow().getText(), "Price (high to low)", "User: '"+username+"', Button text should be 'Price (high to low)'");
        // Assert that the list of item prices is sorted correctly
        Assert.assertEquals(Items_prices_Before_Sort, Items_prices_After_Sort, "User: '"+username+"', Item prices should be sorted in descending order.");

    }
}