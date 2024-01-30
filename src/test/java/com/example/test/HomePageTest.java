package com.example.test;


import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromeDriver\\chromedriver.exe");
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
        this.driver.get("http://localhost:8080/");
    }

    @Test
    public void verify_user_is_on_homepage() {

        WebElement loginLink = this.driver.findElement(By.linkText("Log in"));
        loginLink.click();

        String login = this.driver.findElement(By.className("card-header")).getText();
        String currentUrl = this.driver.getCurrentUrl();
        assertEquals("Log in", login);
        assertEquals("http://localhost:8080/users/login", currentUrl);

        this.driver.findElement(By.id("email_address")).sendKeys("admin@admin.bg");
        this.driver.findElement(By.id("password")).sendKeys("a123456789A#");
        this.driver.findElement(By.id("submit")).click();

        WebElement searchField = this.driver.findElement(By.id("Search_Property"));
        WebElement usernameLink = this.driver.findElement(By.xpath("//*[@id=\"navbarDarkProfileMenuLink\"]/span"));
        String username = usernameLink.getText();

        assertNotNull(searchField, "Search field is expected to be present.");
        assertNotNull(usernameLink);
        assertEquals("Adminnew", username);

    }
}
