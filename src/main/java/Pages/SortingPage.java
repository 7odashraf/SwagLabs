package Pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class SortingPage {

    WebDriver driver;

    public SortingPage(WebDriver driver) {
        this.driver = driver;
    }

    // -------- Locators for sort dropdown/options --------
    private By sortAtoZBtn = By.cssSelector("option[value='az']");
    private By sortZtoABtn = By.cssSelector("option[value='za']");
    private By sortLowToHighBtn = By.cssSelector("option[value='lohi']");
    private By sortHighToLowBtn = By.cssSelector("option[value='hilo']");

    private By inventoryItemName = By.cssSelector(".inventory_item_name");
    private By inventoryItemPrice = By.cssSelector(".inventory_item_price");

    // -------- Click sorting buttons --------
    public void clickSortAtoZ() {
        driver.findElement(sortAtoZBtn).click();
        handleAlert();
    }

    public void clickSortZtoA() {
        driver.findElement(sortZtoABtn).click();
        handleAlert();
    }

    public void clickSortLowToHigh() {
        driver.findElement(sortLowToHighBtn).click();
        handleAlert();
    }

    public void clickSortHighToLow() {
        driver.findElement(sortHighToLowBtn).click();
        handleAlert();
    }

    // -------- Get button text (for assertions) --------
    public String getSortAtoZText() {
        return driver.findElement(sortAtoZBtn).getText();
    }

    public String getSortZtoAText() {
        return driver.findElement(sortZtoABtn).getText();
    }

    public String getSortLowToHighText() {
        return driver.findElement(sortLowToHighBtn).getText();
    }

    public String getSortHighToLowText() {
        return driver.findElement(sortHighToLowBtn).getText();
    }

    // -------- Get item names/prices --------
    public List<String> getItemNames() {
        List<WebElement> elements = driver.findElements(inventoryItemName);
        List<String> names = new ArrayList<>();
        for (WebElement e : elements) names.add(e.getText());
        return names;
    }

    public List<Double> getItemPrices() {
        List<WebElement> elements = driver.findElements(inventoryItemPrice);
        List<Double> prices = new ArrayList<>();
        for (WebElement e : elements) {
            String text = e.getText().replace("$", "").trim();
            prices.add(Double.parseDouble(text));
        }
        return prices;
    }

    // -------- Handle alerts --------
    private void handleAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored) {}
    }
}