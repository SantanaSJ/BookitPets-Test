package com.example.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class RoomAvailability {
    private WebDriver driver;
    private JavascriptExecutor executor;

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

    //    invalid
    @Test
    public void check_availability_with_no_dates_selected() {
        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();

        JavascriptExecutor executor = (JavascriptExecutor) this.driver;
        executor.executeScript("scroll(0, 300);");

        WebElement bookNowButton = getBookNowButton();
        executor.executeScript("arguments[0].click();", bookNowButton);

        findCheckButton().click();

        String checkInDateErrorMessage = getCheckInDateErrorMessage();
        String checkIOutDateErrorMessage = getCheckOutDateErrorMessage();

        assertEquals("Check in date is required!", checkInDateErrorMessage);
        assertEquals("Check out date is required!", checkIOutDateErrorMessage);

//        this.driver.quit();

    }


    @Test
    public void check_availability_with_no_checkin_date_and_checkout_date_bigger_than_current_date() {
        logInUser();
        verifySuccessfulLogin();
        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();

        this.executor = (JavascriptExecutor) this.driver;
        this.executor.executeScript("scroll(0, 300);");

        WebElement bookNowButton = getBookNowButton();

        this.executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkOut")).sendKeys(convertDateToString(getValidCheckOutDateBiggerThanCurrentDate()));

        findCheckButton().click();

        String checkInDateErrorMessage = getCheckInDateErrorMessage();
        assertEquals("Check in date is required!", checkInDateErrorMessage);
    }

    @Test
    public void check_availability_with_checkin_date_equal_to_current_date_and_no_checkout_date() {

        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();
        waitUntilHotelsAreLoaded();

        this.executor = (JavascriptExecutor) this.driver;

        this.executor.executeScript("scroll(0, 300);");

        WebElement bookNowButton = getBookNowButton();
        this.executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkIn")).sendKeys(convertDateToString(getCurrentDate()));

        findCheckButton().click();

        String checkOutDateErrorMessage = getCheckOutDateErrorMessage();
        assertEquals("Check out date is required!", checkOutDateErrorMessage);
    }

    @Test
    public void check_availability_with_checkin_date_smaller_than_current_date_and_checkout_date_bigger_than_current_date() {
        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();

        this.executor = (JavascriptExecutor) this.driver;

        this.executor.executeScript("scroll(0, 300);");
        WebElement bookNowButton = getBookNowButton();

        this.executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkIn")).sendKeys(convertDateToString(getInvalidCheckInDateSmallerThanCurrentDate()));
        this.driver.findElement(By.id("checkOut")).sendKeys(convertDateToString(getValidCheckOutDateBiggerThanCurrentDate()));

        findCheckButton().click();

        String checkInDateErrorMessage = getCheckInDateErrorMessage();
        assertEquals("must be a date in the present or in the future", checkInDateErrorMessage);

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

        this.driver.findElement(By.id("checkIn")).sendKeys(convertDateToString(getCurrentDate()));
        this.driver.findElement(By.id("checkOut")).sendKeys(convertDateToString(getCurrentDate()));

        findCheckButton().click();

        String checkOutDateErrorMessage = getCheckOutDateErrorMessage();
        assertEquals("must be a future date", checkOutDateErrorMessage);
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

//        getValidCheckInDateBiggerThanCurrentDate();
        this.driver.findElement(By.id("checkIn")).sendKeys(convertDateToString(getValidCheckInDateBiggerThanCurrentDate()));
        this.driver.findElement(By.id("checkOut")).sendKeys(convertDateToString(getCurrentDate()));

        findCheckButton().click();

        String checkOutDateErrorMessage = getCheckOutDateErrorMessage();
        assertEquals("must be a future date", checkOutDateErrorMessage);
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

        this.driver.findElement(By.id("checkIn")).sendKeys(convertDateToString(getValidCheckInDateBiggerThanCurrentDate()));
        this.driver.findElement(By.id("checkOut")).sendKeys(convertDateToString(getInvalidCheckOutDateSmallerThanCurrentDate()));

        findCheckButton().click();

        String checkOutDateErrorMessage = getCheckOutDateErrorMessage();
        assertEquals("must be a future date", checkOutDateErrorMessage);
    }

    //    valid ??
    @Test
    public void check_availability_with_check_in_01_10_and_check_out_03_10_and_one_single_room() {
        logInUser();
        verifySuccessfulLogin();

        this.driver.findElement(By.id("showAll")).click();

        waitUntilHotelsAreLoaded();

        JavascriptExecutor executor = (JavascriptExecutor) this.driver;

        executor.executeScript("scroll(0, 200);");
        WebElement bookNowButton = getBookNowButton();

        executor.executeScript("arguments[0].click();", bookNowButton);

        this.driver.findElement(By.id("checkIn")).sendKeys(convertDateToString(getValidCheckInDateBiggerThanCurrentDate()));
        this.driver.findElement(By.id("checkOut")).sendKeys(convertDateToString(getValidCheckOutDateBiggerThanCurrentDate()));

//        find dropdown
        WebElement dropdown = this.driver.findElement(By.id("numberOfRooms"));

//        select number of rooms #1
        dropdown.findElement(By.xpath("//*[@id=\"numberOfRooms\"]/option[2]")).click();

        findCheckButton().click();

        String text = this.driver.findElement(By.cssSelector("div.alert-success > span")).getText();
        assertEquals("Selected rooms are available!", text);
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

        this.driver.findElement(By.id("checkIn")).sendKeys(convertDateToString(getValidCheckInDateBiggerThanCurrentDate()));
        this.driver.findElement(By.id("checkOut")).sendKeys(convertDateToString(getValidCheckOutDateBiggerThanCurrentDate()));

//        find dropdown for double room
        WebElement dropdown = this.driver.findElement(By.name("rooms[1].numberOfRooms"));

//        select number of rooms #2
//        dropdown.findElement(By.xpath("option[. = '1']")).click();
        Select select = new Select(dropdown);
        select.selectByVisibleText("1");


        findCheckButton().click();

        String text = this.driver.findElement(By.cssSelector("div.alert-success > span")).getText();
        assertEquals("Selected rooms are available!", text);
    }

    private WebElement findCheckButton() {
        By checkButtonByXpath = By.xpath("//*[@id=\"check\"]/button");
        WebElement element = this.driver.findElement(checkButtonByXpath);
        return element;
    }

    private LocalDate getValidCheckInDateBiggerThanCurrentDate() {
        LocalDate currentDate = getCurrentDate();
        return currentDate.plusDays(5);
    }

    private LocalDate getInvalidCheckOutDateSmallerThanCurrentDate() {
        LocalDate currentDate = getCurrentDate();
        return currentDate.minusDays(5L);
    }

    private LocalDate getValidCheckOutDateBiggerThanCurrentDate() {
        LocalDate currentDate = getCurrentDate();
        return currentDate.plusDays(8);
    }

    private LocalDate getInvalidCheckInDateSmallerThanCurrentDate() {
        LocalDate currentDate = getCurrentDate();
        return currentDate.minusDays(5);
    }

    private String convertDateToString(LocalDate currentDate) {
        return currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    private LocalDate getCurrentDate() {
        return LocalDate.now();
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

    private void
    waitUntilHotelsAreLoaded() {
        WebDriverWait wait = new WebDriverWait(this.driver, 3);
//        wait.until(d -> d.findElement(By.xpath("//*[@id=\"output\"]/div[1]/div[2]/button")));
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"output\"]/div[1]/div[2]/button")));

    }

    private void verifySuccessfulLogin() {
        String text = this.driver.findElement(By.cssSelector(".nav-item:nth-child(1) a")).getText();
        assertEquals("Home", text);
    }

}
