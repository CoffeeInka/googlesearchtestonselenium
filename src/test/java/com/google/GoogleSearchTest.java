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

    public WebDriver driver;
    public WebDriverWait wait;
    public String results = ".srg>.g";

    @Before
    public void setUp() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("marionette", false);
        driver = new FirefoxDriver(capabilities);
        wait = new WebDriverWait(driver, 6);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSearch() {
        driver.get("http://google.com");
        search("Selenium automates browsers");
        assertResultsAmount(10);
        assertResult(0, "for automating web applications for testing purposes");
    }

    @Test
    public void testFollowLink() {
        driver.get("http://google.com");
        search("Selenium automates browsers");
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
        wait.until(textToBePresentInElementLocated(By.cssSelector(String.format(results + ":nth-child(%d)", index + 1)), text));
    }

    public void followLink(int index) {
        wait.until(minimumSizeOf(By.cssSelector(results), index+1));
        driver.findElements(By.cssSelector(results)).get(index).findElement(By.cssSelector(".r>a")).click();
    }

}
