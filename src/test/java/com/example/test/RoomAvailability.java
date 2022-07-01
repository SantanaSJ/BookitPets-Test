package com.example.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class RoomAvailability {
    private WebDriver driver;
    private JavascriptExecutor executor;

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


    //    invalid
    @Test
    public void check_availability_with_no_dates_selected() {
        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();

        this.executor = (JavascriptExecutor) this.driver;
        this.executor.executeScript("scroll(0, 300);");
        WebElement bookNowButton = getBookNowButton();
        this.executor.executeScript("arguments[0].click();", bookNowButton);

        By checkButtonByXpath = By.xpath("//*[@id=\"check\"]/button");
        this.driver.findElement(checkButtonByXpath).click();

        String checkInDateErrorMessage = getCheckInDateErrorMessage();
        String checkIOutDateErrorMessage = getCheckOutDateErrorMessage();

        assertEquals("Check in date is required!", checkInDateErrorMessage);
        assertEquals("Check out date is required!", checkIOutDateErrorMessage);

//        this.driver.quit();

    }

    @Test
    public void check_availability_with_no_checkin_date_and_valid_checkout_date() {
        logInUser();
        verifySuccessfulLogin();
        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();
        this.executor = (JavascriptExecutor) this.driver;

        this.executor.executeScript("scroll(0, 300);");
        WebElement bookNowButton = getBookNowButton();

        this.executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkOut")).sendKeys("03-10-2022");

        By checkButtonByXpath = By.xpath("//*[@id=\"check\"]/button");
        this.driver.findElement(checkButtonByXpath).click();

        String checkInDateErrorMessage = getCheckInDateErrorMessage();
        assertEquals("Check in date is required!", checkInDateErrorMessage);
//        this.driver.quit();
    }

    @Test
    public void check_availability_with_valid_checkin_date_and_no_checkout_date() {
        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();
        waitUntilHotelsAreLoaded();

        this.executor = (JavascriptExecutor) this.driver;

        this.executor.executeScript("scroll(0, 300);");

        WebElement bookNowButton = getBookNowButton();
        this.executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkIn")).sendKeys("01-10-2022");

        By checkButtonByXpath = By.xpath("//*[@id=\"check\"]/button");
        this.driver.findElement(checkButtonByXpath).click();

        String checkOutDateErrorMessage = getCheckOutDateErrorMessage();
        assertEquals("Check out date is required!", checkOutDateErrorMessage);

//        this.driver.quit();
    }

    @Test
    public void check_availability_with_invalid_checkin_date_and_valid_checkout_date() {
        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();

        this.executor = (JavascriptExecutor) this.driver;

        this.executor.executeScript("scroll(0, 300);");
        WebElement bookNowButton = getBookNowButton();

        this.executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkIn")).sendKeys("29-06-2022");
        this.driver.findElement(By.id("checkOut")).sendKeys("02-07-2022");

        By checkButtonByXpath = By.xpath("//*[@id=\"check\"]/button");
        this.driver.findElement(checkButtonByXpath).click();

        String checkInDateErrorMessage = getCheckInDateErrorMessage();
        assertEquals("must be a date in the present or in the future", checkInDateErrorMessage);

//        this.driver.quit();

    }

    @Test
    public void check_availability_with_checkin_date_equal_to_current_date_and_checkout_date_equal_to_current_date() {
        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();
        this.executor = (JavascriptExecutor) this.driver;

        this.executor.executeScript("scroll(0, 300);");
        WebElement bookNowButton = getBookNowButton();

        this.executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkIn")).sendKeys("01-07-2022");
        this.driver.findElement(By.id("checkOut")).sendKeys("01-07-2022");

        By checkButtonByXpath = By.xpath("//*[@id=\"check\"]/button");
        this.driver.findElement(checkButtonByXpath).click();

        String checkOutDateErrorMessage = getCheckOutDateErrorMessage();
        assertEquals("must be a future date", checkOutDateErrorMessage);

//        this.driver.quit();
    }

    @Test
    public void check_availability_with_checkin_date_bigger_than_current_date_and_checkout_date_equal_to_current_date() {
        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();

        this.executor = (JavascriptExecutor) this.driver;

        this.executor.executeScript("scroll(0, 300);");
        WebElement bookNowButton = getBookNowButton();

        this.executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkIn")).sendKeys("02-07-2022");
        this.driver.findElement(By.id("checkOut")).sendKeys("01-07-2022");

        By checkButtonByXpath = By.xpath("//*[@id=\"check\"]/button");
        this.driver.findElement(checkButtonByXpath).click();

        String checkOutDateErrorMessage = getCheckOutDateErrorMessage();
        assertEquals("must be a future date", checkOutDateErrorMessage);

//        this.driver.quit();
    }

    @Test
    public void check_availability_with_checkin_date_bigger_than_current_date_and_checkout_date_smaller_than_current_date() {
        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();

        this.executor = (JavascriptExecutor) this.driver;

        this.executor.executeScript("scroll(0, 300);");
        WebElement bookNowButton = getBookNowButton();

        this.executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkIn")).sendKeys("03-07-2022");
        this.driver.findElement(By.id("checkOut")).sendKeys("30-06-2022");

        By checkButtonByXpath = By.xpath("//*[@id=\"check\"]/button");
        this.driver.findElement(checkButtonByXpath).click();

        String checkOutDateErrorMessage = getCheckOutDateErrorMessage();
        assertEquals("must be a future date", checkOutDateErrorMessage);

//        this.driver.quit();
    }

    //    valid ??
    @Test
    public void check_availability_with_check_in_01_10_and_check_out_03_10_and_one_single_room() {
        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();

        this.executor = (JavascriptExecutor) this.driver;

        this.executor.executeScript("scroll(0, 300);");
        WebElement bookNowButton = getBookNowButton();

        this.executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkIn")).sendKeys("01-11-2022");
        this.driver.findElement(By.id("checkOut")).sendKeys("03-11-2022");

//        TODO:

        this.driver.findElement(By.id("numberOfRooms")).sendKeys("1");

        By checkButtonByXpath = By.xpath("//*[@id=\"check\"]/button");
        this.driver.findElement(checkButtonByXpath).click();
        String text = this.driver.findElement(By.cssSelector("div.alert-success > span")).getText();
        assertEquals("Selected rooms are available!", text);

//        driver.quit();
    }

    @Test
    public void check_availability_with_check_in_01_10_and_check_out_03_10_and_one_double_room() {
        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();

        JavascriptExecutor executor = (JavascriptExecutor) this.driver;

        executor.executeScript("scroll(0, 300);");
        WebElement bookNowButton = getBookNowButton();

        executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkIn")).sendKeys("01-11-2022");
        this.driver.findElement(By.id("checkOut")).sendKeys("03-11-2022");

//        TODO:

        By checkButtonByXpath = By.xpath("//*[@id=\"check\"]/button");
        this.driver.findElement(checkButtonByXpath).click();
        String text = this.driver.findElement(By.cssSelector("div.alert-success > span")).getText();
        assertEquals("Selected rooms are available!", text);

//        driver.quit();
    }

    private String getCheckInDateErrorMessage() {
        return this.driver.findElement(By.id("checkIn-Error")).getText();
    }
    private String getCheckOutDateErrorMessage() {
        return this.driver.findElement(By.id("checkOut-Error")).getText();
    }

    private void logInUser() {
        this.driver.findElement(By.linkText("Log in")).click();
        this.driver.findElement(By.id("email_address")).sendKeys("pesho@abv.bg");
        this.driver.findElement(By.id("password")).sendKeys("test");
        this.driver.findElement(By.id("submit")).click();
    }

    private WebElement getBookNowButton() {
        By bookNowButtonByXpath = By.xpath("//*[@id=\"output\"]/div[1]/div[2]/button");
        return this.driver.findElement(bookNowButtonByXpath);
    }

    private void waitUntilHotelsAreLoaded() {
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(1L));
        wait.until(d -> d.findElement(By.xpath("//*[@id=\"output\"]/div[1]/div[2]/button")));
    }

    private void verifySuccessfulLogin() {
        String text = this.driver.findElement(By.cssSelector(".nav-item:nth-child(1) a")).getText();
        assertEquals("Home", text);
    }

}
