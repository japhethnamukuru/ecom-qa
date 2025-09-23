package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    public void fillInfo(String firstName, String lastName, String postalCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(postalCode);
        driver.findElement(By.id("continue")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    public void finishCheckout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish"))).click();
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }

    public String getConfirmationText() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        return el.getText();
    }
}
