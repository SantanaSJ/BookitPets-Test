package com.example.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;

import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class RegisterAndLogin {

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
    public void register_user_with_valid_data() {
        WebElement registerButton = this.driver.findElement(By.cssSelector(".nav-item:nth-child(3) span"));
        registerButton.click();
        WebElement firstName = this.driver.findElement(By.id("first-name"));

        String newUser = getNewUserName();

        firstName.sendKeys(newUser);

        String expectedFirstName = firstName.getAttribute("value");
        this.driver.findElement(By.id("last-name")).sendKeys("TestUser");
        this.driver.findElement(By.id("pet-name")).sendKeys("Bren");
        this.driver.findElement(By.id("pet-kg")).sendKeys("12");
        this.driver.findElement(By.id("phone")).sendKeys("0884433654");

        String newEmail = getNewEmail();

        this.driver.findElement(By.id("email_address")).sendKeys(newEmail);

        this.driver.findElement(By.id("password")).sendKeys("a123456789A#");
        this.driver.findElement(By.id("confirm-password")).sendKeys("a123456789A#");

        WebElement submitButton = driver.findElement(By.id("submit"));

        JavascriptExecutor executor = (JavascriptExecutor) this.driver;
        executor.executeScript("arguments[0].click();", submitButton);

//        Point p = submitButton.getLocation();
//        int x = p.x;
//        int y = p.y;
//        Actions actions = new Actions(driver);
//        actions.moveToElement(submitButton).moveByOffset(x, y);
//        actions.click();
//        actions.perform();

        String actual = this.driver.findElement(By.cssSelector("#navbarDarkProfileMenuLink > span")).getText();

        assertEquals(actual, expectedFirstName);

        logout();
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

    private void logout() {
        this.driver.findElement(By.id("navbarDarkProfileMenuLink")).click();
        this.driver.findElement(By.linkText("Logout")).click();
    }

    private void assertLoginErrorMessage() {
        String alert = this.driver.findElement(By.className("alert")).getText();
        assertEquals("This username and password combination does not exist.", alert);
    }

    private void clickLoginButton() {
        WebElement loginButton = this.driver.findElement(By.linkText("Log in"));
        loginButton.click();
    }

    private String getNewEmail() {
        int n = (int) Math.floor(Math.random() * 100);
        String newEmail = "newTestUser" + n + "@email.com";
        return newEmail;
    }

    private String getNewUserName() {
        int n = (int) Math.floor(Math.random() * 100);
        String newUser = "newTestUser" + n;
        return newUser;
    }

}
