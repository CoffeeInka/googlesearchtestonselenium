package com.google;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;

/**
 * Created by inna on 07/07/2017.
 */
public class GoogleSearchTest {

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("marionette", false);
        driver = new FirefoxDriver(capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    WebDriver driver;

    @Test
    public void testSearchThenFollowLink() throws Exception {
        driver.get("http://google.com");
        search("Selenium automates browsers");
        assertResultsAmount(10);
    }

    private void search(String query) {
        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys(query + Keys.ENTER);
    }

    private void assertResultsAmount(int resultsAmount) {
        new WebDriverWait(driver, 6).until(numberOfElementsToBe(By.cssSelector(".srg>.g"),resultsAmount));
    }


}
