package com.google;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * Created by inna on 07/07/2017.
 */
public class GoogleSearchTest {

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("marionette", false);
        driver = new FirefoxDriver(capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    WebDriver driver;

    @Test
    public void testSearchThenFollowLink() throws Exception {
        driver.get("http://google.com");
        search("Selenium automates browsers");
        assertResultsAmount(10);
        assertFirstResultHasText("for automating web applications for testing purposes");

        followFirstLink();
        new WebDriverWait(driver, 6).until(visibilityOfAllElementsLocatedBy(By.cssSelector("#mainContent>h2")));
        assertEquals(driver.getCurrentUrl(), "http://www.seleniumhq.org/");
    }

    private void search(String query) {
        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys(query + Keys.ENTER);
    }

    private void assertResultsAmount(int resultsAmount) {
        new WebDriverWait(driver, 6).until(numberOfElementsToBe(By.cssSelector(".srg>.g"), resultsAmount));
    }

    private void assertFirstResultHasText(String text) {
        new WebDriverWait(driver, 10).until(textToBePresentInElementLocated(By.cssSelector(".srg>.g:nth-child(1)"), text));
    }

    private void followFirstLink() {
        driver.findElement(By.cssSelector(".srg>.g:nth-child(1)")).findElement(By.cssSelector(".r>a")).click();
    }

}
