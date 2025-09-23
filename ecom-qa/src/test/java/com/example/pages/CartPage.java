package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        wait.until(ExpectedConditions.urlContains("cart"));
    }

    public List<String> getCartItemNames() {
        List<WebElement> nameEls = driver.findElements(By.className("inventory_item_name"));
        return nameEls.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void removeItemByName(String name) {
        List<WebElement> items = driver.findElements(By.className("cart_item"));
        for (WebElement item : items) {
            String n = item.findElement(By.className("inventory_item_name")).getText();
            if (n.equals(name)) {
                item.findElement(By.tagName("button")).click();
                wait.until(ExpectedConditions.invisibilityOf(item));
                return;
            }
        }
        throw new RuntimeException("Cart item not found: " + name);
    }

    public void clickCheckout() {
        driver.findElement(By.id("checkout")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
    }
}

