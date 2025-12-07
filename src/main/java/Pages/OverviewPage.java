package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OverviewPage {
    WebDriver driver;

    public OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement finishButton() {
        return driver.findElement(By.id("finish"));
    }

    public WebElement completeHeader() {
        return driver.findElement(By.cssSelector("h2.complete-header"));
    }

    public WebElement backHomeButton() {
        return driver.findElement(By.id("back-to-products"));
    }

    public double getTotalPrice() {
        String totalText = driver.findElement(By.cssSelector(".summary_total_label[data-test='total-label']")).getText();
        return Double.parseDouble(totalText.replace("Total: $", "").trim());
    }

    public double getTax() {
        String taxText = driver.findElement(By.cssSelector(".summary_tax_label[data-test='tax-label']")).getText();
        return Double.parseDouble(taxText.replace("Tax: $", "").trim());
    }

    public List<WebElement> getCartItems() {
        return driver.findElements(By.cssSelector(".cart_list .cart_item"));
    }

    public WebElement getItemName(WebElement item) {
        return item.findElement(By.cssSelector(".inventory_item_name"));
    }

    public WebElement getItemPrice(WebElement item) {
        return item.findElement(By.cssSelector(".inventory_item_price"));
    }

    public WebElement getItemDescription(WebElement item) {
        return item.findElement(By.cssSelector(".inventory_item_desc"));
    }
}
