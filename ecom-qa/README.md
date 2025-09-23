# SauceDemo E2E Automated Tests (Java + Selenium + JUnit 5 + Maven)

This project contains end-to-end automated test cases for [saucedemo.com](https://www.saucedemo.com/).  
It uses **Java 17, Selenium WebDriver, JUnit 5, and Maven**.

## ✅ Test Cases Implemented
1. **Login**
    - `testValidLogin`: Verify valid login works.
    - `testInvalidLogin_ShowsError`: Verify invalid login shows error message.

2. **Products**
    - `testProductsCountAndBasicSort`: Verify products load and basic sorting works.

3. **Cart**
    - `testAddAndRemoveCart`: Verify adding and removing items from cart.

4. **Checkout**
    - `testCheckoutFlowAndConfirmation`: Verify checkout flow works through confirmation.

5. **Logout**
    - `testLogout`: Verify logout redirects to login page.

---

## 🛠️ How to Run Tests

```bash
# Cloning and running the tests
git clone https://github.com/japhethnamukuru/ecom-qa.git
cd ecom-qa
mvn clean verify site
```

```bash
# Clean, compile, run tests and generate reports
mvn clean verify site
```


