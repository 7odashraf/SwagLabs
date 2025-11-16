import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Sorting extends LoginTest {
    @Test(priority = 5)
    public void sortingByNameAtoZ() {
        testValidLogin();
        List<String> Items_names_Before_Sort = getItemNames();
        sortAtoZ().click();
        List<String> Items_names_After_Sort = getItemNames();

        Collections.sort(Items_names_Before_Sort);
        Assert.assertEquals(sortAtoZ().getText(), "Name (A to Z)");
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort);
    }

    @Test(priority = 6)
    public void sortingByNameZtoA() {
        testValidLogin();
        List<String> Items_names_Before_Sort = getItemNames();
        sortZtoA().click();
        List<String> Items_names_After_Sort = getItemNames();

        Collections.sort(Items_names_Before_Sort, Collections.reverseOrder());
        Assert.assertEquals(sortZtoA().getText(), "Name (Z to A)");
        Assert.assertEquals(Items_names_Before_Sort, Items_names_After_Sort);
    }

    @Test(priority = 7)
    public void sortingByPriceLowtoHigh() {
        testValidLogin();
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
        testValidLogin();
        List<Double> Items_prices_Before_Sort = getItemPrices();
        Items_prices_Before_Sort.forEach(e -> System.out.println("Before: " + e));
        sortHightoLow().click();
        List<Double> Items_prices_After_Sort = getItemPrices();

        Collections.sort(Items_prices_Before_Sort, Collections.reverseOrder());
        Items_prices_After_Sort.forEach(e -> System.out.println("After: " + e));
        Assert.assertEquals(sortHightoLow().getText(), "Price (high to low)");
        Assert.assertEquals(Items_prices_Before_Sort, Items_prices_After_Sort);

    }
}