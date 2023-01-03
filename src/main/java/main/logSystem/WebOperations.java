package main.logSystem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebOperations {
    WebDriver driver;

    public void logOn(){
        if (driver == null){
            System.setProperty("webdriver.chrome.driver", "/Users/mac/Desktop/chrometest/chromedriver");
            driver = new ChromeDriver();
        }
        driver.get("https://www.marketalertum.com/Alerts/Login");
        WebElement searchField = driver.findElement(By.id("UserId"));
        searchField.sendKeys("afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4");
        WebElement searchButton = driver.findElement(By.tagName("input"));
        searchButton.submit();
    }

    public void logOut(){
        if (driver == null){
            System.setProperty("webdriver.chrome.driver", "/Users/mac/Desktop/chrometest/chromedriver");
            driver = new ChromeDriver();
        }
        driver.get("https://www.marketalertum.com/Home/Logout");
    }

    public void viewAlerts(){
        if (driver == null){
            System.setProperty("webdriver.chrome.driver", "/Users/mac/Desktop/chrometest/chromedriver");
            driver = new ChromeDriver();
        }
        driver.get("https://www.marketalertum.com/Alerts/List");
    }

    //public void closeSession(){
    //    if (driver != null){
    //        driver.close();
    //    }
    //}
}
