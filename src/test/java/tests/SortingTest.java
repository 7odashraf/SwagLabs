package tests;

import Pages.LoginPage;
import Pages.SortingPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Collections;
import java.util.List;

public class SortingTest extends BaseClass {


    @Test(dataProvider = "ValidLoginData")
    public void sortingByNameAtoZ(String username, String password) {
        LoginPage login = new LoginPage(driver);
        SortingPage sorting = new SortingPage(driver);

        login.Login(username, password);

        // Get the list of item names before sorting
        List<String> Items_names_Before_Sort = sorting.getItemNames();
        sorting.clickSortAtoZ();

        // Get the list of item names after sorting
        List<String> Items_names_After_Sort = sorting.getItemNames();

        // Sort the list of item names alphabetically for comparison
        Collections.sort(Items_names_Before_Sort);

        // Assert that the "Sort A to Z" button text is correct
        Assert.assertEquals(sorting.getSortAtoZText(), "Name (A to Z)", "User: '"+username+"', Button text should be 'Name (A to Z)'");
        // Assert that the list of items is sorted correctly
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort, "User: '"+username+"', Item names should be sorted in ascending alphabetical order.");
    }

    @Test(dataProvider = "ValidLoginData")
    public void sortingByNameZtoA(String username, String password) {
        LoginPage login = new LoginPage(driver);
        SortingPage sorting = new SortingPage(driver);

        login.Login(username, password);

        // Get the list of item names before sorting
        List<String> Items_names_Before_Sort = sorting.getItemNames();
        sorting.clickSortZtoA();

        // Get the list of item names after sorting
        List<String> Items_names_After_Sort = sorting.getItemNames();

        // Sort the list of item names in reverse order for comparison
        Collections.sort(Items_names_Before_Sort, Collections.reverseOrder());

        // Assert that the "Sort Z to A" button text is correct
        Assert.assertEquals(sorting.getSortZtoAText(), "Name (Z to A)", "User: '"+username+"', Button text should be 'Name (Z to A)'");
        // Assert that the "Sort Z to A" button text is correct
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort, "User: '"+username+"', Item names should be sorted in descending alphabetical order.");
    }

    @Test(dataProvider = "ValidLoginData")
    public void sortingByPriceLowtoHigh(String username, String password) {
        LoginPage login = new LoginPage(driver);
        SortingPage sorting = new SortingPage(driver);

        login.Login(username, password);

        // Get the list of item prices before sorting
        List<Double> Items_prices_Before_Sort = sorting.getItemPrices();
        sorting.clickSortLowToHigh();

        // Get the list of item prices after sorting
        List<Double> Items_prices_After_Sort = sorting.getItemPrices();

        // Sort the list of item prices in ascending order for comparison
        Collections.sort(Items_prices_Before_Sort);

        // Assert that the "Sort Low to High" button text is correct
        Assert.assertEquals(sorting.getSortLowToHighText(), "Price (low to high)", "User: '"+username+"', Button text should be 'Price (low to high)'");
        // Assert that the "Sort Low to High" button text is correct
        Assert.assertEquals(Items_prices_Before_Sort, Items_prices_After_Sort, "User: '"+username+"', Item prices should be sorted in ascending order.");

    }

    @Test(dataProvider = "ValidLoginData")
    public void sortingByPriceHightoLow(String username, String password) {
        LoginPage login = new LoginPage(driver);
        SortingPage sorting = new SortingPage(driver);

        login.Login(username, password);

        // Get the list of item prices before sorting
        List<Double> Items_prices_Before_Sort = sorting.getItemPrices();
        sorting.clickSortHighToLow();

        // Get the list of item prices after sorting
        List<Double> Items_prices_After_Sort = sorting.getItemPrices();

        // Sort the list of item prices in descending order for comparison
        Collections.sort(Items_prices_Before_Sort, Collections.reverseOrder());

        // Assert that the "Sort High to Low" button text is correct
        Assert.assertEquals(sorting.getSortHighToLowText(), "Price (high to low)", "User: '"+username+"', Button text should be 'Price (high to low)'");
        // Assert that the list of item prices is sorted correctly
        Assert.assertEquals(Items_prices_Before_Sort, Items_prices_After_Sort, "User: '"+username+"', Item prices should be sorted in descending order.");

    }
}