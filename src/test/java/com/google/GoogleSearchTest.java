package com.google;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by inna on 07/07/2017.
 */
public class GoogleSearchTest {

    WebDriver driver = new FirefoxDriver();
    driver.get("http://www.google.com");
}
