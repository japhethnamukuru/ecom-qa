package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        wait.until(ExpectedConditions.urlContains("inventory"));
    }

    public int productCount() {
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        return items.size();
    }

    public List<String> productNames() {
        return driver.findElements(By.className("inventory_item_name"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Double> productPrices() {
        return driver.findElements(By.className("inventory_item_price"))
                .stream().map(e -> Double.parseDouble(e.getText().replace("$",""))).collect(Collectors.toList());
    }

    public void addToCartByName(String productName) {
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        for (WebElement item : items) {
            WebElement nameEl = item.findElement(By.className("inventory_item_name"));
            if (productName.equals(nameEl.getText())) {
                WebElement btn = item.findElement(By.tagName("button"));
                btn.click();
                return;
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public void removeFromCartByName(String productName) {
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        for (WebElement item : items) {
            WebElement nameEl = item.findElement(By.className("inventory_item_name"));
            if (productName.equals(nameEl.getText())) {
                WebElement btn = item.findElement(By.tagName("button"));
                btn.click();
                return;
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public int getCartBadgeCount() {
        List<WebElement> badge = driver.findElements(By.className("shopping_cart_badge"));
        if (badge.isEmpty()) return 0;
        return Integer.parseInt(badge.get(0).getText());
    }

    public void goToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("cart"));
    }

    public void sortBy(String visibleText) {
        WebElement sortEl = driver.findElement(By.className("product_sort_container"));
        Select select = new Select(sortEl);
        select.selectByVisibleText(visibleText);
    }
}
