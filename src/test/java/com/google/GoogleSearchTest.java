package com.google;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import static core.CustomConditions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * Created by inna on 07/07/2017.
 */
public class GoogleSearchTest {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static String results = ".srg>.g";

    @BeforeClass
    public static void setUp() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("marionette", false);
        driver = new FirefoxDriver(capabilities);
        wait = new WebDriverWait(driver, 6);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }


    @Test
    public void testSearchThenFollowLink() throws Exception {
        driver.get("http://google.com");
        search("Selenium automates browsers");
        assertResultsAmount(10);
        assertResult(0, "for automating web applications for testing purposes");

        followLink(0);
        wait.until(urlContains("http://www.seleniumhq.org/"));
    }

    public void search(String query) {
        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys(query + Keys.ENTER);
    }

    public void assertResultsAmount(int resultsAmount) {
        wait.until(sizeOf(By.cssSelector(results), resultsAmount));
    }

    public void assertResult(int index, String text) {
        wait.until(textToBePresentInElementLocated(By.cssSelector(String.format(results+"nth-child(%d)", index + 1)), text));
    }

    public void followLink(int index) {
        driver.findElements(By.cssSelector(results)).get(index).findElement(By.cssSelector(".r>a")).click();
    }

}
