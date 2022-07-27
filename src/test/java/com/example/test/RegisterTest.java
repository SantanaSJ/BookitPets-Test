package com.example.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegisterTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\drivers\\chromedriver.exe");
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
        this.driver.get("https://bookitpets.herokuapp.com/");
    }

    @AfterEach
    public void tearDown() {
        this.driver.quit();
    }


    @Test
    public void register_user_with_valid_data() {
        WebElement registerButton = this.driver.findElement(By.cssSelector(".nav-item:nth-child(3) span"));
        registerButton.click();

//        make sure we are on the right page
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://bookitpets.herokuapp.com/users/register", currentUrl);

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
    public void register_user_with_valid_firstName_and_invalid_lastName_phoneNumber_email_petName_petKg_password() {
        WebElement registerButton = this.driver.findElement(By.cssSelector(".nav-item:nth-child(3) span"));
        registerButton.click();
        //        make sure we are on the right page
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://bookitpets.herokuapp.com/users/register", currentUrl);

        WebElement firstName = this.driver.findElement(By.id("first-name"));

//        valid
        String newUser = getNewUserName();
        firstName.sendKeys(newUser);

//        invalid
        this.driver.findElement(By.id("last-name")).sendKeys("Te");
        this.driver.findElement(By.id("email_address")).sendKeys(" testuseremail.com");
        this.driver.findElement(By.id("pet-name")).sendKeys("RexRexRexRex");
        this.driver.findElement(By.id("pet-kg")).sendKeys("-12");
        this.driver.findElement(By.id("phone")).sendKeys("08855443337");
        this.driver.findElement(By.id("password")).sendKeys("a123456789");
        this.driver.findElement(By.id("confirm-password")).sendKeys("a123456789");


        WebElement submitButton = driver.findElement(By.id("submit"));

        JavascriptExecutor executor = (JavascriptExecutor) this.driver;
        executor.executeScript("arguments[0].click()", submitButton);

        String lastNameError = this.driver.findElement(By.id("last-nameError")).getText();
        String emailAddressError = this.driver.findElement(By.id("email_addressError")).getText();
        String petNameError = this.driver.findElement(By.id("pet-nameError")).getText();
        String petKgError = this.driver.findElement(By.id("pet-kgError")).getText();
        String phoneError = this.driver.findElement(By.id("phoneError")).getText();
        String passwordError = this.driver.findElement(By.id("passwordError")).getText();
        String confirmPasswordError = this.driver.findElement(By.id("confirm-passwordError")).getText();

        assertEquals("Last name must be between 3 and 20 characters.", lastNameError);
        assertEquals("Please enter a valid email!", emailAddressError);
        assertEquals("Pet name must be between 3 and 10 characters.", petNameError);
        assertEquals("Please provide valid kilograms!", petKgError);
        assertEquals("Phone number should be in the format: 0884444333", phoneError);
        assertEquals("Password must contain minimum 8 characters: At least 1 upper case letter, " +
                "At least 1 lower case letter, At least 1 digit, At least 1 special character", passwordError);
        assertEquals("Password must contain: At least 1 upper case letter, At least 1 lower case letter, " +
                "At least 1 digit, At least 1 special character, Minimum length - 8 characters.", confirmPasswordError);

    }

    @Test
    public void register_user_user_with_invalid_firstName_lastName_email_petKg_and_valid_phoneNumber_petName_password() {

        WebElement registerButton = this.driver.findElement(By.cssSelector(".nav-item:nth-child(3) span"));
        registerButton.click();
        //        make sure we are on the right page
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://bookitpets.herokuapp.com/users/register", currentUrl);

        //        invalid
        this.driver.findElement(By.id("first-name")).sendKeys("Te");
        this.driver.findElement(By.id("last-name")).sendKeys("Te");
        this.driver.findElement(By.id("email_address")).sendKeys(" testuseremail.com");
        this.driver.findElement(By.id("pet-kg")).sendKeys("-12");


//        valid
        WebElement phone = this.driver.findElement(By.id("phone"));
        WebElement petName = this.driver.findElement(By.id("pet-name"));
        WebElement password = this.driver.findElement(By.id("password"));
        WebElement confirmPassword = this.driver.findElement(By.id("confirm-password"));

        petName.sendKeys("Rex");
        phone.sendKeys("0885544333");
        password.sendKeys("a123456789A#");
        confirmPassword.sendKeys("a123456789A#");

        WebElement submitButton = this.driver.findElement(By.id("submit"));

        JavascriptExecutor executor = (JavascriptExecutor) this.driver;
        executor.executeScript("arguments[0].click()", submitButton);

//        Assert

        String firstNameError = this.driver.findElement(By.id("first-nameError")).getText();
        String lastNameError = this.driver.findElement(By.id("last-nameError")).getText();
        String emailAddressError = this.driver.findElement(By.id("email_addressError")).getText();
        String petKgError = this.driver.findElement(By.id("pet-kgError")).getText();

        assertEquals("First name must be between 3 and 20 characters.", firstNameError);
        assertEquals("Last name must be between 3 and 20 characters.", lastNameError);
        assertEquals("Please enter a valid email!", emailAddressError);
        assertEquals("Please provide valid kilograms!", petKgError);


        assertNotNull(phone);
        assertNotNull(petName);
        assertNotNull(password);
        assertNotNull(confirmPassword);
    }

    private void logout() {
        this.driver.findElement(By.id("navbarDarkProfileMenuLink")).click();
        this.driver.findElement(By.linkText("Logout")).click();
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
