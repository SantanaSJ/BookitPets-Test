package com.example.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAuthorizationTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromeDriver\\chromedriver.exe");
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
        this.driver.get("http://localhost:8080/");
    }

    @AfterEach
    public void tearDown() {
        this.driver.close();
    }

    @Test
    public void verify_restriction_of_hotel_search_and_booking_room_access_form_for_non_registered_user() {

        String currentUrl = this.driver.getCurrentUrl();
        String title = this.driver.findElement(By.xpath("//h1[contains(.,'Welcome to BookIt Pets')]")).getText();

        WebElement homeLink = this.driver.findElement(By.linkText("/home"));
        homeLink.click();
        String loginUrl = this.driver.getCurrentUrl();
        String login = this.driver.findElement(By.id("login")).getText();

        assertEquals("http://localhost:8080/", currentUrl);
        assertEquals("Welcome to BookIt Pets", title);
        assertEquals("http://localhost:8080/users/login", loginUrl);
        assertEquals("Log in", login);
    }
}
