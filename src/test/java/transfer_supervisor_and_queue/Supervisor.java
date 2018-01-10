package transfer_supervisor_and_queue;

import callsMethods.CallOnTwoLines;
import callsMethods.Methods;
import callsMethods.STMethods;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Flags;
import utils.RetryAnalyzer;

import java.io.IOException;

import static callsMethods.STMethods.loginReceiver;
import static transfer_supervisor_and_queue.Transfer.transferInitiator;
import static utils.TestSetup.setup;


/**
 * Created by SChubuk on 10.11.2017.
 */

//@Listeners(VideoListener.class)
public class Supervisor {
    static Data data;
    static WebDriver driver;
    static WebDriver driver2;
    static boolean fast = false;
    static int delay = 2;
    static String ieUser = "81058";
    static String chromeUser = "81059";
    static WebDriver dummiDriver;


    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void listen() throws Exception {
        //OPEN IE
        try {

            setup(dummiDriver);
            driver2 = loginReceiver(driver2, "81058");

            //OPEN CHROME
            CallOnTwoLines.call();
            driver = CallOnTwoLines.driver;
            data = CallOnTwoLines.data;
            Thread.sleep(1000);

            //LISTEN FROM IE CALL IN CHROME
            STMethods.switchWindow();
            WebElement userName = driver2.findElement(By.xpath("//*[text()='81016']"));
            userName.click();
            JavascriptExecutor js = (JavascriptExecutor) driver2;
            js.executeScript("runSupervisorAction('silent');PrimeFaces.ab({source:'tabView:supervisorListen',update:'growl'});");


            //END LISTENING IN IE
            Methods.checkStatus(driver2, "Listening", 5);
            Methods.agentHangup(driver2, 1);
            Methods.checkStatus(driver2, "Available", 5);

            //END CALL IN CHROME
            STMethods.switchWindow();
            Methods.agentHangup(driver, 1);
            CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
            utils.TestTeardown.teardown(transferInitiator, "blindTransferToNumber");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void talkToUser() throws Exception {
        try {
            setup(dummiDriver);

            //OPEN IE
            driver2 =  loginReceiver(driver2, "81058");


            //OPEN CHROME
            CallOnTwoLines.call();
            driver = CallOnTwoLines.driver;
            data = CallOnTwoLines.data;
            Thread.sleep(1000);

            //LISTEN FROM IE CALL IN CHROME
            STMethods.switchWindow();
            WebElement userName = driver2.findElement(By.xpath("//*[text()='81016']"));
            userName.click();
            JavascriptExecutor js = (JavascriptExecutor) driver2;
            js.executeScript("runSupervisorAction('whisper');PrimeFaces.ab({source:'tabView:supervisorTalk',update:'growl'});");
            System.out.println("JavaScript has been executed.");
            //listen.click();
            //Methods.clickIEelement(driver2, listen);

            //END LISTENING IN IE
            Methods.checkStatus(driver2, "Whispering", 5);
            Methods.agentHangup(driver2, 1);
            Methods.checkStatus(driver2, "Available", 5);

            //END CALL IN CHROME
            STMethods.switchWindow();
            Methods.agentHangup(driver, 1);
            CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
            utils.TestTeardown.teardown(transferInitiator, "blindTransferToNumber");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void bargeIn() throws Exception {
        try {
            setup(dummiDriver);
            //OPEN IE
            driver2 =  loginReceiver(driver2, "81058");


            //OPEN CHROME
            CallOnTwoLines.call();
            driver = CallOnTwoLines.driver;
            data = CallOnTwoLines.data;
            Thread.sleep(1000);

            //LISTEN FROM IE CALL IN CHROME
            STMethods.switchWindow();
            WebElement userName = driver2.findElement(By.xpath("//*[text()='81016']"));
            userName.click();
            JavascriptExecutor js = (JavascriptExecutor) driver2;
            js.executeScript("runSupervisorAction('intrude');PrimeFaces.ab({source:'tabView:supervisorBargein',update:'growl'});");
            System.out.println("JavaScript has been executed.");
            //listen.click();
            //Methods.clickIEelement(driver2, listen);

            //END LISTENING IN IE
            Methods.checkStatus(driver2, "Barged", 5);
            Methods.agentHangup(driver2, 1);
            Methods.checkStatus(driver2, "Available", 5);

            //END CALL IN CHROME
            STMethods.switchWindow();
            Methods.agentHangup(driver, 1);
            CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
            utils.TestTeardown.teardown(transferInitiator, "blindTransferToNumber");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void logUserOut() throws InterruptedException, IOException, FindFailed {

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void sendNotification() throws Exception {
        try {
            setup(dummiDriver);
            //OPEN IE
            driver2 = loginReceiver(driver2, "81058");


            //OPEN CHROME
            driver = Methods.openWebphoneLoginPage(driver, "chrome", "http://172.21.7.239/gbwebphone/");
            Methods.login(driver, "usual", "81016", "\\!test_group5_5220");
            Methods.checkStatus(driver, "Available", 60);
            Thread.sleep(1000);


            STMethods.switchWindow();
            WebDriverWait waitForUsername = new WebDriverWait(driver2, 10);
            waitForUsername.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='81016']")));
            WebElement userName = driver2.findElement(By.xpath("//*[text()='81016']"));
            userName.click();
            //Thread.sleep(1000);

            WebDriverWait waitForButton_Notify = new WebDriverWait(driver2, 10);
            waitForButton_Notify.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tabView\\:supervisorNotify")));
            WebElement button_Notify = driver2.findElement(By.cssSelector("#tabView\\:supervisorNotify"));
            button_Notify.click();
        /*JavascriptExecutor js = (JavascriptExecutor) driver2;
        js.executeScript("PrimeFaces.ab({source:'tabView:supervisorNotify',update:'growl'});");*/
            System.out.println("JavaScript has been executed.");
            //listen.click();
            //Methods.clickIEelement(driver2, listen);
            Thread.sleep(2000);

            //ENTER MESSAGE IN NOTIFICATION BOX
            WebElement notificationBox = driver2.findElement(By.cssSelector("#tabView\\:notificationMessage"));
            notificationBox.sendKeys("This is notification.");

            WebElement button_Send = driver2.findElement(By.cssSelector("#tabView\\:supervisorNotifyButton"));
            button_Send.click();

            System.out.println("Notification has been sent.");

            WebDriverWait waitForGrowl_container = new WebDriverWait(driver2, 5);
            waitForGrowl_container.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#growl_container")));
            WebElement growl_container = driver2.findElement(By.cssSelector("#growl_container"));
            Assert.assertTrue(growl_container.isDisplayed());
            Assert.assertTrue(growl_container.getText().contains("Sending notification(s)"));

            //Check that message received in chrome.
            STMethods.switchWindow();
            WebDriverWait waitForGrowlSocketMessage_container = new WebDriverWait(driver, 10);
            waitForGrowlSocketMessage_container.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#growlSocketMessage_container")));
            WebElement growlSocketMessage_container = driver.findElement(By.cssSelector("#growlSocketMessage_container"));
            Assert.assertTrue(growlSocketMessage_container.isDisplayed());
            Assert.assertTrue(growlSocketMessage_container.getText().contains("This is notification."));

            System.out.println("Notification arrived.");
            utils.TestTeardown.teardown(transferInitiator, "blindTransferToNumber");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void assist() throws Exception {
        try {
            setup(dummiDriver);
            //OPEN IE
            driver2 = loginReceiver(driver2, "81058");


            //OPEN CHROME
            driver = Methods.openWebphoneLoginPage(driver, "chrome", "http://172.21.7.239/gbwebphone/");
            Methods.login(driver, "usual", "81016", "\\!test_group5_5220");
            Methods.checkStatus(driver, "Available", 60);
            Thread.sleep(1000);

            //SELECT USER
            STMethods.switchWindow();
            WebDriverWait waitForUsername = new WebDriverWait(driver2, 10);
            waitForUsername.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='81016']")));
            WebElement userName = driver2.findElement(By.xpath("//*[text()='81016']"));
            userName.click();
            //Thread.sleep(1000);

            WebDriverWait waitForButton_Assist = new WebDriverWait(driver2, 10);
            waitForButton_Assist.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tabView\\:supervisorAssist")));
            WebElement button_Assist = driver2.findElement(By.cssSelector("#tabView\\:supervisorAssist"));
            button_Assist.click();
        /*JavascriptExecutor js = (JavascriptExecutor) driver2;
        js.executeScript("PrimeFaces.ab({source:'tabView:supervisorNotify',update:'growl'});");*/
            System.out.println("JavaScript has been executed.");
            //listen.click();
            //Methods.clickIEelement(driver2, listen);
            Thread.sleep(2000);

            //ENTER MESSAGE IN ASSIST BOX
            WebElement assistanceBox = driver2.findElement(By.xpath("//*[contains(@id,'message_assistanceDialog_81016_81049')]"));
            assistanceBox.sendKeys("This is assistance message.");
//message_assistanceDialog_81016_81049_1510668367831205
            WebElement button_Send = driver2.findElement(By.xpath("//*[contains(@id,'send_assistanceDialog_81016_81049')]"));
            button_Send.click();

            System.out.println("Assist message.");
//#assistLog_assistanceDialog_81058_81059_15106705893561180 > div:nth-child(8) > span.assistMessage

            WebDriverWait waitForAssistOnMyScreen = new WebDriverWait(driver2, 10);
            waitForAssistOnMyScreen.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='This is assistance message.']")));

            //CHECK THAT ASSISTANCE BOX IS EMPTY
            String assistanceBoxText = assistanceBox.getText();
            Assert.assertTrue(assistanceBoxText.equals(""));

            //Check that message received in chrome.
       /* Methods.switchFocus();
        WebDriverWait waitForGrowlSocketMessage_container = new WebDriverWait(driver, 10);
        waitForGrowlSocketMessage_container.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#growlSocketMessage_container")));
        WebElement growlSocketMessage_container = driver.findElement(By.cssSelector("#growlSocketMessage_container"));
        Assert.assertTrue(growlSocketMessage_container.isDisplayed());
        Assert.assertTrue(growlSocketMessage_container.getText().contains("This is notification."));

        System.out.println("Notification arrived.");*/
            utils.TestTeardown.teardown(transferInitiator, "blindTransferToNumber");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void changeStatus() throws InterruptedException, IOException, FindFailed {

    }


    @BeforeMethod
    public void open() throws InterruptedException, FindFailed, IOException {
        Methods.openCXphone(100);
        //before groups to launch ie browser
    }

    //alailability schedule for transfer point - not really needed
    @AfterMethod
    public void teardown() throws IOException {
        try {
            boolean isIE = Flags.isIE(driver);
            driver.quit();
            driver2.quit();

            if (isIE) {
                Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
    }

}
