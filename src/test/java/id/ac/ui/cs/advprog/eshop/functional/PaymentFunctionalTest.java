package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class PaymentFunctionalTest {

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
    void payOrderVoucherSuccess(ChromeDriver driver) throws Exception {
        // Create product and order first
        driver.get(baseUrl + "/product/create");
        driver.findElement(By.id("nameInput")).sendKeys("Sampo Cap Bambang");
        driver.findElement(By.id("quantityInput")).sendKeys("10");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        driver.get(baseUrl + "/order/create");
        driver.findElement(By.name("author")).sendKeys("Safira Sudrajat");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(1000);
        
        // Find the "Pay Now" button in history
        driver.findElement(By.linkText("Pay Now")).click();
        
        Thread.sleep(1000);

        // Fill payment form
        Select methodSelect = new Select(driver.findElement(By.id("method")));
        methodSelect.selectByValue("VOUCHER_CODE");
        
        driver.findElement(By.id("voucherCode")).sendKeys("ESHOP1234ABC5678");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(1000);

        assertTrue(driver.getPageSource().contains("SUCCESS"));
    }

    @Test
    void payOrderBankTransferSuccess(ChromeDriver driver) throws Exception {
        // Create product and order first
        driver.get(baseUrl + "/product/create");
        driver.findElement(By.id("nameInput")).sendKeys("Sampo Cap Bambang");
        driver.findElement(By.id("quantityInput")).sendKeys("10");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        driver.get(baseUrl + "/order/create");
        driver.findElement(By.name("author")).sendKeys("Bambang Sudrajat");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(1000);
        
        driver.findElement(By.linkText("Pay Now")).click();
        
        Thread.sleep(1000);

        Select methodSelect = new Select(driver.findElement(By.id("method")));
        methodSelect.selectByValue("BANK_TRANSFER");
        
        driver.findElement(By.id("bankName")).sendKeys("BCA");
        driver.findElement(By.id("referenceCode")).sendKeys("12345678");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(1000);

        assertTrue(driver.getPageSource().contains("SUCCESS"));
    }
}
