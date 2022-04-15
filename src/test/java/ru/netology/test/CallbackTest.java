package ru.netology.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CallbackTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void souldSendForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Вася Теркин");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+99999999999");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedText, actualText);
        driver.close();
    }

    @Test
    public void souldSetInvalidName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Vasia Terkin");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+99999999999");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'name'] .input__sub")).getText().trim();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedText, actualText);
        driver.close();
    }

    @Test
    public void souldSetEmptyName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'name'] .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText, actualText);
        driver.close();
    }

    @Test
    public void souldSetEmptyTel() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Вася Теркин");
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'phone'] .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText, actualText);
        driver.close();
    }

    @Test
    public void souldSetTelSub11() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Вася Теркин");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+99999");
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'phone'] .input__sub")).getText().trim();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedText, actualText);
        driver.close();
    }

    @Test
    public void souldSendWithOutСheck() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Вася Теркин");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+99999999999");
        driver.findElement(By.tagName("button")).click();
        String actualText = actualText = driver.findElement(By.cssSelector("[data-test-id = 'phone'] .input__sub")).getText().trim();
        String expectedText = "На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно.";
        assertEquals(expectedText, actualText);
        driver.close();
    }
}
