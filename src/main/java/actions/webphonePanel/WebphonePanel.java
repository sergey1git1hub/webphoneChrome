package actions.webphonePanel;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.testng.Assert;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import static callsMethods.Methods.*;
import static utils.Flags.isChrome;
import static utils.Flags.isIE;
import static utils.Flags.isLocal;

/**
 * Created by SChubuk on 11.04.2018.
 */
public class WebphonePanel {
    public static WebDriver checkStatus(WebDriver driver, String status, int waitTime) throws UnknownHostException, UnsupportedEncodingException, InterruptedException {

        if ((!isLocal()) && (status.equals("Тренинг"))) {
            Thread.sleep(10000);
        } else {
            WebDriverWait waitForStatus = new WebDriverWait(driver, waitTime);
            waitForStatus.until(ExpectedConditions.textMatches(By.cssSelector(
                    "#statusButton > span.ui-button-text.ui-c"), Pattern.compile(".*\\b" + status + "\\b.*")));
            // System.out.println("Wait for status.");

            WebElement currentStatus = driver.findElement(By.cssSelector(
                    "#statusButton > span.ui-button-text.ui-c"));

            // System.out.println("Before assert status is: " + currentStatus.getText() + ".");
            Assert.assertTrue(currentStatus.getText().contains(status));
            //System.out.println("Asserting that contains: " + status + ".");
            log("Check that status is " + status + ".", "INFO");

        }
        return driver;
    }

    public static WebDriver changeStatusNewDontWork(WebDriver driver, String status) throws UnknownHostException, FindFailed, InterruptedException, UnsupportedEncodingException {

        if (status.equalsIgnoreCase("Available")) {
            sikuliClickElement("currentStatus");
            Thread.sleep(1000);
            sikuliClickElement("availableStatus");

        }
        if (status.equalsIgnoreCase("AUX")) {
            sikuliClickElement("currentStatus");
            Thread.sleep(1000);
            sikuliClickElement("auxStatus");
            Thread.sleep(1000);
        }

        checkStatus(driver, status, 2);
        log("Change status to " + status + ".", "INFO");
        return driver;
    }


    public static WebDriver changeStatus(WebDriver driver, String status) throws UnknownHostException, FindFailed, InterruptedException, UnsupportedEncodingException {
        //System.out.println("Changing status to " + status + ".");
        log("Change status to " + status + ".", "INFO");
        String hostName = InetAddress.getLocalHost().getHostName();
        if (!isLocal() && isIE(driver)) {
            changeStatusNewDontWork(driver, status);
            checkStatus(driver, status, 2);
            //System.out.println("Host is: kv1-it-pc-jtest and browser is not Chrome.");
        } else if (isChrome(driver)) {
            WebElement currentStatus = driver.findElement(By.cssSelector(
                    "#statusButton > span.ui-button-text.ui-c"));
            currentStatus.click();
            WebElement desirableStatus;
            if (!status.equals("AUX")) {
                desirableStatus = driver.findElement(By.xpath(
                        "/*//*[contains(text(),'" + status + "')]"));
            } else {
                desirableStatus = driver.findElement(By.xpath(
                        "//*[contains(text(),'AUX') and not(contains(text(),'Доступен'))]"));
            }
            desirableStatus.click();
            checkStatus(driver, status, 2);
            // System.out.println("Browser is Chrome.");
        } else {
            WebElement currentStatus = driver.findElement(By.cssSelector(
                    "#statusButton > span.ui-button-text.ui-c"));
            executeJavaScriptOrClick(driver, currentStatus);
            WebElement desirableStatus;
            if (!status.equals("AUX")) {
                desirableStatus = driver.findElement(By.xpath(
                        "/*//*[contains(text(),'" + status + "')]"));
            } else {
                desirableStatus = driver.findElement(By.xpath(
                        "//*[contains(text(),'AUX') and not(contains(text(),'Доступен'))]"));
            }
            executeJavaScriptOrClick(driver, desirableStatus);
            checkStatus(driver, status, 2);
        }
        return driver;
    }

    public static void hold(WebDriver driver) throws InterruptedException, UnsupportedEncodingException, UnknownHostException {

        WebElement button_Hold = driver.findElement(By.cssSelector("#btn_hold"));
        executeJavaScriptOrClick(driver, button_Hold, "wp_common.wp_HoldOrVoicemail();log(event);" +
                "PrimeFaces.ab({source:'btn_hold'});return false;");
        log("Press button Onhold.", "INFO");
        checkStatus(driver, "Onhold", 6);

    }

    public static void unhold(WebDriver driver) throws InterruptedException, UnsupportedEncodingException, UnknownHostException {
        WebElement button_Hold = driver.findElement(By.cssSelector("#btn_hold"));
        executeJavaScriptOrClick(driver, button_Hold, "wp_common.wp_HoldOrVoicemail();log(event);" +
                "PrimeFaces.ab({source:'btn_hold'});return false;");
        log("Unhold the call.", "INFO");
        checkStatus(driver, "Incall", 6);
    }

    public static void mute(WebDriver driver) throws InterruptedException, UnsupportedEncodingException, UnknownHostException {
        WebElement button_Mute = driver.findElement(By.cssSelector("#btn_mute"));
        executeJavaScriptOrClick(driver, button_Mute, "wp_common.wp_HoldOrVoicemail();log(event);" +
                "PrimeFaces.ab({source:'btn_hold'});return false;");
        log("Press button Mute.", "INFO");
        checkStatus(driver, "Muted", 6);
    }

    public static void unmute(WebDriver driver) throws InterruptedException, UnsupportedEncodingException, UnknownHostException {
        WebElement button_Mute = driver.findElement(By.cssSelector("#btn_mute"));
        executeJavaScriptOrClick(driver, button_Mute, "wp_common.wp_HoldOrVoicemail();log(event);" +
                "PrimeFaces.ab({source:'btn_hold'});return false;");
        log("Unmute the call.", "INFO");
        checkStatus(driver, "Incall", 6);

    }

    public static WebDriver switchLine(WebDriver driver, int line) throws FindFailed, InterruptedException, UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        if (driver.equals("chrome") && isLocal()) {
            System.out.println("Browser is chrome.");
            WebDriverWait waitForLineElement = new WebDriverWait(driver, 2);
            waitForLineElement.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id = 'btn_line_" + line + "_span']")));
            WebElement lineElement = driver.findElement(By.cssSelector("[id = 'btn_line_" + line + "_span']"));
            Thread.sleep(1000);
            System.out.println("Slept 1000 ms.");
            lineElement.click();
        } else {
            /*System.out.println("Browser is not chrome or running on Jenkns.");
            if (!isLocal()) {
                WebElement lineElement = driver.findElement(By.cssSelector("[id = 'btn_line_" + line + "_span']"));
                lineElement.sendKeys(Keys.ENTER);
            } else*/
            try {
                if (driver instanceof JavascriptExecutor) {
                    ((JavascriptExecutor) driver)
                            .executeScript("wp_common.wp_ChangeLine(" + line + "); log(event);");
                    System.out.println("Line switched by javascript.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Switch to the " + line + " line.");
        return driver;
    }


    public static WebDriver call(WebDriver driver, int line, String number) throws FindFailed, InterruptedException, UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        if (true/*hostName.equalsIgnoreCase("kv1-it-pc-jtest")*/) {
            switchLine(driver, line);
            Thread.sleep(1000);
            log("Sleep after Line switched.", "DEBUG");
            if (!(!isLocal() && isChrome(driver))) {
                sikuliClickElement("phoneNumberField_Sikuli");
            }
            log("Sikuli clkicked phone number filed.", "DEBUG");
            WebElement phoneNumberField = driver.findElement(By.cssSelector("#PhoneNumber"));
            phoneNumberField.click();
            phoneNumberField.clear();
            phoneNumberField.sendKeys(number);
            Thread.sleep(200);
            WebElement button_Call = driver.findElement(By.cssSelector("#btn_call"));
            button_Call.click();
           /* JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", button_Call);
            System.out.println("Button call clicked with javascript.");*/
        } else {

        }

        log("Call to " + number + " on the " + line + " line.", "INFO");
        return driver;
    }

    public static WebDriver agentHangup(WebDriver driver, int line) throws FindFailed, InterruptedException, UnknownHostException {

        switchLine(driver, line);
        Thread.sleep(500);
        WebElement button_Hangup = driver.findElement(By.cssSelector("#btn_hangup"));
        executeJavaScriptOrClick(driver, button_Hangup, "arguments[0].click();");
        log("Hangup the call on agent side on the " + line + " line.", "INFO");
        return driver;
    }

    public static WebDriver setWebphoneResultCode(WebDriver driver) throws InterruptedException, UnknownHostException, FindFailed {

        String hostName = InetAddress.getLocalHost().getHostName();
        if (!isLocal()) {
            sikuliClickElement("resultCodeUdachno");
            Thread.sleep(1000); //necessary
            WebElement button_Save = driver.findElement(By.cssSelector("#btn_rslt > span.ui-button-text.ui-c"));
            button_Save.click();
        } else if (isIE(driver) == false) {
            WebDriverWait waitForResultCode = new WebDriverWait(driver, 5);
            waitForResultCode.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Удачно']")));

            WebElement resultCode = driver.findElement(By.xpath("//td[text()='Удачно']"));
            resultCode.click();
            Thread.sleep(1000);
            WebElement button_Save = driver.findElement(By.cssSelector("#btn_rslt > span.ui-button-text.ui-c"));
            button_Save.click();
        } else {
            WebDriverWait waitForResultCode = new WebDriverWait(driver, 5);
            waitForResultCode.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Удачно']")));

            WebElement resultCode = driver.findElement(By.xpath("//td[text()='Удачно']"));
            executeJavaScriptOrClick(driver, resultCode);
            Thread.sleep(1000);
            WebElement button_Save = driver.findElement(By.cssSelector("#btn_rslt > span.ui-button-text.ui-c"));
            executeJavaScriptOrClick(driver, button_Save);
        }
        log("Select result code \"Udachno\".", "INFO");
        return driver;

    }

    public static WebDriver agentAcceptCall(WebDriver driver, int waitTime, boolean isPreview) throws InterruptedException {

        WebDriverWait waitForButtonAccept = new WebDriverWait(driver, waitTime);
        String idValue;
        if (isPreview) {
            idValue = "btn_preview_accept";
        } else {
            idValue = "btn_accept";
        }
        driver.switchTo().defaultContent();
        By byIdAccept = By.cssSelector("[id = '" + idValue + "']");
        waitForButtonAccept.until(ExpectedConditions.elementToBeClickable(byIdAccept));
        WebElement button_Accept = driver.findElement(byIdAccept);
        //if not wait, CRM card not opened
        Thread.sleep(500);
        if (isIE(driver) == true) {
            clickIEelement(driver, button_Accept);
        } else {
            button_Accept.click();
        }
        if (isPreview) {
            log("Accept preview call on agent side.", "INFO");
        } else {
            log("Accept call on agent side.", "INFO");
        }
        return driver;
    }

}
