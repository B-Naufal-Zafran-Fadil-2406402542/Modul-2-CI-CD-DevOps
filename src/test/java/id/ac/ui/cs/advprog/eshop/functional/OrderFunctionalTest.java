package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class OrderFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createOrder_isSuccessful(ChromeDriver driver) throws Exception {
        // First create a product so order can be created
        driver.get(baseUrl + "/product/create");
        driver.findElement(By.id("nameInput")).sendKeys("Sampo Cap Bambang");
        driver.findElement(By.id("quantityInput")).sendKeys("10");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Now create order
        driver.get(baseUrl + "/order/create");
        String authorName = "Safira Sudrajat";
        driver.findElement(By.id("author")).sendKeys(authorName);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(1000);

        // Should be redirected to history search or success page
        // Based on my template, it redirects to history list for that author
        assertTrue(driver.getCurrentUrl().contains("/order/history"));
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains(authorName));
        assertTrue(pageSource.contains("WAITING_PAYMENT"));
    }

    @Test
    void viewOrderHistory_isSuccessful(ChromeDriver driver) throws Exception {
        // First create a product and an order
        driver.get(baseUrl + "/product/create");
        driver.findElement(By.id("nameInput")).sendKeys("Sampo Cap Bambang");
        driver.findElement(By.id("quantityInput")).sendKeys("10");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        driver.get(baseUrl + "/order/create");
        String authorName = "Bambang Sudrajat";
        driver.findElement(By.id("author")).sendKeys(authorName);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Go to history search
        driver.get(baseUrl + "/order/history");
        driver.findElement(By.id("author")).sendKeys(authorName);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(1000);

        assertTrue(driver.getPageSource().contains(authorName));
    }
}
