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

import static callsMethods.Methods.clickIEelement;
import static utils.Flags.isIE;

/**
 * Created by SChubuk on 15.11.2017.
 */
public class STMethods {

    static boolean fast = false;

    public static WebDriver loginInitiator(WebDriver driver, String username) throws InterruptedException, FindFailed, IOException {
       /* Methods.openCXphone(100);*/
        driver = Methods.openWebphoneLoginPage(driver, "chrome", "http://172.21.7.239/gbwebphone/");
        Methods.login(driver, "usual", username, "\\!test_group5_5220");
        Methods.checkStatus(driver, "Available", 30);
        return driver;
    }

    public static WebDriver loginReceiver(WebDriver driver, String username) throws InterruptedException, FindFailed, IOException {
        driver = Methods.openWebphoneLoginPage(driver, "ie", "http://172.21.24.109/gbwebphone/");
        Methods.login(driver, "usual", username, "\\!test_group5_5220");
        Methods.checkStatus(driver, "Available", 60);
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
        transferNumber.sendKeys(number);
        WebElement button_form_transfer = driver.findElement(By.cssSelector("#btn_trnsfr"));
        button_form_transfer.click();
    }

    public static void acceptTransfer(WebDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        By byIdAccept = By.cssSelector("[id = 'btn_preview_accept'], [id = 'btn_accept']");
        WebElement button_Accept = driver.findElement(byIdAccept);
        Thread.sleep(500);
      /*  if (isIE(driver) == true) {
            clickIEelement(driver, button_Accept);
        } else {*/
            button_Accept.click();
        /*}*/

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



}

