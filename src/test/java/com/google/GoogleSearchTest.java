package com.google;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * Created by inna on 07/07/2017.
 */
public class GoogleSearchTest {

    public static WebDriver driver;
    public static WebDriverWait wait;

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
    public void testSearchThenFollowLink() throws Exception {
        driver.get("http://google.com");
        search("Selenium automates browsers");
        assertResultsAmount(10);
        assertResult(1, "for automating web applications for testing purposes");
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
        wait.until(sizeOf(By.cssSelector(".srg>.g"), resultsAmount));
    }

    public void assertResult(int index, String text) {
        wait.until(textToBePresentInElementLocated(By.cssSelector(String.format(".srg>.g:nth-child(%d)", index)), text));
    }

    public void followLink(int index) {
        wait.until(numberOfElementsToBeAtLeast(By.cssSelector(".srg>.g"), 1));
        driver.findElements(By.cssSelector(".srg>.g")).get(index).findElement(By.cssSelector(".r>a")).click();
    }

    public static ExpectedCondition<Boolean> numberOfElementsToBeAtLeast(final By elementsLocator, final int expectedSize) {
        return new ExpectedCondition<Boolean>() {
            private int listSize;
            private List<WebElement> elements;

            public Boolean apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);
                listSize = elements.size();
                return listSize >= expectedSize;
            }

            public String toString() {
                return String.format("\nsize of list: %s\n to be: %s\n while actual size is: %s\n", elements, expectedSize, listSize);
            }
        };
    }

    public static ExpectedCondition<Boolean> sizeOf(final By elementsLocator, final int expectedSize) {
        return new ExpectedCondition<Boolean>() {
            private int listSize;
            private List<WebElement> elements;

            public Boolean apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);
                listSize = elements.size();
                return listSize == expectedSize;
            }

            public String toString() {
                return String.format("\nsize of list: %s\n to be: %s\n while actual size is: %s\n", elements, expectedSize, listSize);
            }
        };
    }


}
