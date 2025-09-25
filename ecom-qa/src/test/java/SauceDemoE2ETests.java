import com.example.pages.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SauceDemoE2ETests extends BaseTest {

//     login
    @Test
    public void testValidLogin() {
        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

//         assert inventory page landing
        Assertions.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "After valid login user should land on inventory page");
    }

    @Test
    public void testInvalidLogin_ShowsError() {
        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "wrong_pass");
        String err = login.getErrorMessage().toLowerCase();
        Assertions.assertTrue(err.contains("username") || err.contains("password") || err.contains("do not match"),
                "Invalid login should show an error message. Got: " + err);
    }

//    products
    @Test
    public void testProductsCountAndBasicSort() {
        LoginPage login = new LoginPage(driver);
        login.login("standard_user","secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        int count = products.productCount();
        Assertions.assertEquals(6, count, "Expected 6 products on SauceDemo (standard dataset)");

        // test sort - low to high by price - basic smoke check (ensures the select works)
        products.sortBy("Price (low to high)");
        List<Double> prices = products.productPrices();
        Assertions.assertTrue(isSortedAscending(prices), "Prices should be sorted ascending after 'Price (low to high)'.");
    }

//    cart
    @Test
    public void testAddAndRemoveCart() {
        LoginPage login = new LoginPage(driver);
        login.login("standard_user","secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        products.addToCartByName("Sauce Labs Backpack");
        Assertions.assertEquals(1, products.getCartBadgeCount(), "Cart badge should be 1 after adding one product");

        products.goToCart();
        CartPage cart = new CartPage(driver);
        List<String> items = cart.getCartItemNames();
        Assertions.assertTrue(items.contains("Sauce Labs Backpack"), "Backpack should be in cart");

        cart.removeItemByName("Sauce Labs Backpack");
        // after removal, cart should be empty
        Assertions.assertTrue(driver.findElements(org.openqa.selenium.By.className("shopping_cart_badge")).isEmpty(),
                "Cart badge should be gone after removing the only item");
    }

//    checkout
    @Test
    public void testCheckoutFlowAndConfirmation() {
        LoginPage login = new LoginPage(driver);
        login.login("standard_user","secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        products.addToCartByName("Sauce Labs Backpack");
        products.goToCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillInfo("John","Doe","12345");
        checkout.finishCheckout();

        String confirmation = checkout.getConfirmationText();
        Assertions.assertTrue(confirmation.toLowerCase().contains("thank"),
                "Confirmation header should contain 'thank' (case-insensitive). Actual: " + confirmation);
    }

//    logout
    @Test
    public void testLogout() {
        LoginPage login = new LoginPage(driver);
        login.login("standard_user","secret_sauce");

//         open burger menu and click logout
        org.openqa.selenium.By menuBtn = org.openqa.selenium.By.id("react-burger-menu-btn");
        org.openqa.selenium.By logoutLink = org.openqa.selenium.By.id("logout_sidebar_link");

        driver.findElement(menuBtn).click();

//        wait until link visible
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(logoutLink));
        driver.findElement(logoutLink).click();

//        after logout, login button should be visible again
        org.openqa.selenium.By loginBtn = org.openqa.selenium.By.id("login-button");
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(loginBtn));

        Assertions.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"), "Should be back at login page after logout");
    }

    //  helper fun
    private boolean isSortedAscending(List<Double> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < list.get(i - 1)) return false;
        }
        return true;
    }
}

