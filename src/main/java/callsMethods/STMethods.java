package callsMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import static callsMethods.Methods.agentAcceptCall;
import static callsMethods.Methods.log;
import static utils.Flags.isLocal;

/**
 * Created by SChubuk on 15.11.2017.
 */
public class STMethods {

    static boolean fast = false;
    static String group;

    static {
        try {
            if (isLocal()) {
                group = "Automation Transfer Functionality Local";
            } else {
                group = "Automation Transfer Functionality Jenkins";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static WebDriver loginInitiator(WebDriver driver, String username) throws Exception {
        return loginInitiator(driver, username, false);
    }

    public static WebDriver loginInitiator(WebDriver driver, String username, Boolean remote) throws Exception {
        driver = Methods.openWebphoneLoginPage(driver, "http://172.21.7.239/gbwebphone/", remote);
        Methods.login(driver, "usual", username, group);
        Methods.checkStatus(driver, "Available", 30);
        log("Login as transfer initiator to " + group + "/" + username, "INFO");
        return driver;
    }

    public static WebDriver loginReceiver(WebDriver driver, String username, Boolean remote) throws InterruptedException, FindFailed, IOException {
        driver = Methods.openWebphoneLoginPage(driver, "http://172.21.7.239/gbwebphone/", remote);
        Methods.login(driver, "usual", username, group);
        Methods.checkStatus(driver, "Available", 60);
        log("Login as transfer receiver to " + group + "/" + username, "INFO");
        return driver;
    }

    public static WebDriver login(WebDriver driver, String username, String group, Boolean remote) throws InterruptedException, FindFailed, IOException {
        driver = Methods.openWebphoneLoginPage(driver, "http://172.21.7.239/gbwebphone/", remote);
        Methods.login(driver, "usual", username, group);
        Methods.checkStatus(driver, "Available", 60);
        if(remote){
            log("Login as agent. " + group + "/" + username, "CONSOLE");
        } else {
            log("Login as supervisor. " + group + "/" + username, "CONSOLE");
        }
        return driver;
    }

    public static void call(WebDriver driver, String number) throws InterruptedException, FindFailed, UnknownHostException {
        Methods.call(driver, 1, number);
        Methods.cxAnswer();
    }

    public static void makeTransfer(WebDriver driver, String transferType, String number) throws InterruptedException {
        WebElement button_transfer = driver.findElement(By.cssSelector("#btn_transfer"));
        button_transfer.click();
        if(transferType.equals("attended")){
            WebElement selectTransfer = driver.findElement(By.cssSelector("#transfer_type > div.ui-selectonemenu-trigger.ui-state-default.ui-corner-right"));
            selectTransfer.click();
            if (!fast)
                Thread.sleep(1000);
            WebElement attendedTransfer = driver.findElement(By.cssSelector("#transfer_type_panel > div > ul > li:nth-child(2)"));
            attendedTransfer.click();
        }

        WebElement transferNumber = driver.findElement(By.cssSelector("#transfer_number"));
        transferNumber.click();
        transferNumber.sendKeys(number);
        if (!fast)
            Thread.sleep(1000);
        WebElement button_form_transfer = driver.findElement(By.cssSelector("#btn_trnsfr"));
        button_form_transfer.click();
        log("Make " + transferType + " transfer to " + number + ".", "INFO");
    }

    public static void acceptTransfer(WebDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        By byIdAccept = By.cssSelector("[id = 'btn_preview_accept'], [id = 'btn_accept']");
        WebElement button_Accept = driver.findElement(byIdAccept);
        Thread.sleep(500);
        agentAcceptCall(driver, 5, false);

    }


    public static void switchWindow(){
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ALT);
            r.keyPress(KeyEvent.VK_TAB);
            r.delay(100);
            r.keyRelease(KeyEvent.VK_ALT);
            r.keyRelease(KeyEvent.VK_TAB);
        } catch (AWTException e) {
            // handle
        }
    }

    public static void setResultCodeAndCheckAvailableStatus(WebDriver driver) throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.setWebphoneResultCode(driver);
        //Methods.checkStatus(driver, "Wrapup", 3);
        try{
        Methods.checkStatus(driver, "Available", 7);
        } catch (Exception e){
            e.printStackTrace();
            Methods.checkStatus(driver, "Meeting", 7);
        }

    }

    public static void clickUsername(WebDriver driver, String username){
        WebDriverWait waitForUsername = new WebDriverWait(driver, 10);
        By usernameSelector = By.xpath("//*[text()='" + username +"']");
        waitForUsername.until(ExpectedConditions.visibilityOfElementLocated(usernameSelector));
        WebElement userName = driver.findElement(usernameSelector);
        userName.click();
        log("Select agent " + username + " on Supervisor tab.", "CONSOLE");
    }



}

