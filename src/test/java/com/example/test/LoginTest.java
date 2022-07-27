package com.example.test;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;

import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class LoginTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\chrome driver\\chromedriver.exe");
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
        this.driver.get("https://bookitpets.herokuapp.com/");
    }

    @AfterEach
    public void tearDown() {
        this.driver.quit();
    }

    @Test
    public void openPage() {

//        this.driver.navigate().to("https://pacific-spire-77723.herokuapp.com/");
        String title = this.driver.getTitle();
        System.out.println(title);
    }

    @Test
    public void verifyPageTitle() {
        String title = this.driver.getTitle();
        assertEquals("BookIt", title);
    }


    @Test
    public void login_user_with_valid_credentials() {
//        (By.cssSelector(".nav-item:nth-child(4) span"))
        clickLoginButton();

        this.driver.findElement(By.id("email_address")).sendKeys("testuser@email.com");
        this.driver.findElement(By.id("password")).sendKeys("a123456789A#");
        this.driver.findElement(By.id("submit")).click();

        String text = this.driver.findElement(By.cssSelector(".nav-item:nth-child(1) a")).getText();
        assertThat(text, is("Home"));
    }

    @Test
    public void login_user_with_valid_email_and_invalid_password() {
        clickLoginButton();
        this.driver.findElement(By.id("email_address")).sendKeys("testuser@email.com");
        this.driver.findElement(By.id("password")).sendKeys("a123456789A");
        this.driver.findElement(By.id("submit")).click();

        assertLoginErrorMessage();
    }

    @Test
    public void login_user_with_invalid_email_and_valid_password() {
        clickLoginButton();
        this.driver.findElement(By.id("email_address")).sendKeys("testuseremail.com");
        this.driver.findElement(By.id("password")).sendKeys("a123456789A#");
        this.driver.findElement(By.id("submit")).click();

        assertLoginErrorMessage();
    }

    @Test
    public void login_user_with_invalid_email_and_invalid_password() {
        clickLoginButton();
        this.driver.findElement(By.id("email_address")).sendKeys("testuseremail.com");
        this.driver.findElement(By.id("password")).sendKeys("1234567");
        this.driver.findElement(By.id("submit")).click();

        assertLoginErrorMessage();
    }

    @Test
    public void login_user_with_valid_email_and_missing_password() {
        clickLoginButton();
        this.driver.findElement(By.id("email_address")).sendKeys("testuser@email.com");
        this.driver.findElement(By.id("password")).sendKeys("");
        this.driver.findElement(By.id("submit")).click();

        assertLoginErrorMessage();
    }


    private void assertLoginErrorMessage() {
        String alert = this.driver.findElement(By.className("alert")).getText();
        assertEquals("This username and password combination does not exist.", alert);
    }

    private void clickLoginButton() {
        WebElement loginButton = this.driver.findElement(By.linkText("Log in"));
        loginButton.click();
    }


}
