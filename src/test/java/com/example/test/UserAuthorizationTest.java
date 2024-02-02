package com.example.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("http://localhost:8080/", currentUrl);

        String title = this.driver.findElement(By.xpath("//h1[contains(.,'Welcome to BookIt Pets')]")).getText();
        assertEquals("WELCOME TO BOOKIT PETS", title);

        WebElement homeLink = this.driver.findElement(By.xpath("//*[@id=\"navbarNavDarkDropdown\"]/ul/li[1]/a"));
        homeLink.click();
        String loginUrl = this.driver.getCurrentUrl();
        String login = this.driver.findElement(By.className("card-header")).getText();

        assertEquals("http://localhost:8080/users/login", loginUrl);
        assertEquals("Log in", login);
    }

    @Test
    public void verify_registered_user_can_access_hotel_search() {
        String currentUrl = this.driver.getCurrentUrl();
        assertEquals("http://localhost:8080/", currentUrl);

        clickLoginButton();
        this.driver.findElement(By.id("email_address")).sendKeys("admin@admin.bg");
        this.driver.findElement(By.id("password")).sendKeys("a123456789A#");
        this.driver.findElement(By.id("submit")).click();

        WebElement searchField = this.driver.findElement(By.id("Search_Property"));
        WebElement usernameLink = this.driver.findElement(By.xpath("//*[@id=\"navbarDarkProfileMenuLink\"]/span"));
        String username = usernameLink.getText();

        assertNotNull(searchField, "Search field is expected to be present.");
        assertNotNull(usernameLink);
        assertEquals("Adminnew", username);

        searchField.sendKeys("R");

        WebDriverWait wait = new WebDriverWait(this.driver, 10);
        By hotelNameLocator = By.xpath("//*[@id=\"output\"]/div/div[1]/div[2]/div[1]/div");

        // Wait for the element to be present in the DOM
        WebElement hotelNameElement = wait.until(ExpectedConditions.presenceOfElementLocated(hotelNameLocator));

        String hotelName = hotelNameElement.getText();
        WebElement hotelImage = this.driver.findElement(By.cssSelector("#output > div > div.row.justify-content-center > div.col-lg-3.px-4 > img"));
        WebElement hotelDescription = this.driver.findElement(By.xpath("//*[@id=\"output\"]/div/div[1]/div[3]/small"));
        String text = hotelDescription.getText();

        assertEquals("Ramada by Wyndham Sofia City Center", hotelName);
        assertNotNull(hotelImage);
        assertTrue(hotelImage.isDisplayed());
        assertNotNull(text);

    }

    private void clickLoginButton() {
        WebElement loginButton = this.driver.findElement(By.linkText("Log in"));
        loginButton.click();
    }
}