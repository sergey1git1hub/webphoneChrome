package legacy;

import callsMethods.Methods;
import callsMethods.STMethods;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.UnknownHostException;

import static actions.client.Client.cxAnswer;
import static org.testng.Assert.assertEquals;

/**
 * Created by SChubuk on 03.01.2018.
 */
public class Legacy {
public static String webchatServerUrl;

    //call to queue 33333
    @Test(retryAnalyzer = RetryAnalyzer.class)
    //@Video
    public static void blindTransferToQueue() throws InterruptedException, IOException, FindFailed {
      /*  transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
        STMethods.call(transferInitiator, callToNumber);
        STMethods.makeTransfer(transferInitiator, "blind", transferToQueue);
        STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
        Thread.sleep(10000);
        transferReceiver = transferInitiator;
        STMethods.acceptTransfer(transferReceiver);
        Thread.sleep(5000);
        Methods.clientHangup(transferReceiver, 1);
        STMethods.setResultCodeAndCheckAvailableStatus(transferReceiver);*/
    } 

    //ATTENDED TRANSFER TO QUEUE SHOULD BE THE SAME AS TRANSFER TO AGENT EXCEPT 33333
    @Test(retryAnalyzer = RetryAnalyzer.class)
    //@Video
    public static void attendedTransferToQueue() throws InterruptedException, IOException, FindFailed {
      /*  STMethods.login(transferReceiver);
        STMethods.login(transferInitiator);
        STMethods.call(transferInitiator, callToNumber);
        STMethods.makeTransfer(transferInitiator, "attended", transferToAgent);

        STMethods.acceptTransfer(transferReceiver);
        Thread.sleep(5000);
        Methods.agentHangup(transferInitiator,1);

        Thread.sleep(5000);
        Methods.agentHangup(transferReceiver,1);
        STMethods.setResultCodeAndCheckAvailableStatus(transferReceiver);*/
    }


    public static void answerCallOnClientSide() throws FindFailed, InterruptedException, UnknownHostException {
      /*  try {*/
        cxAnswer();
     /*   } catch (Exception e) {
            e.printStackTrace();
            WebDriver driverTemp = Methods.loginToPD();
            if (isLocal()) {
                Methods.runPDCampaign(driverTemp, 257);
            } else {
                Methods.runPDCampaign(driverTemp, 281);
            }
            Methods.cxAnswer();
        }*/
    }

    public static WebDriver login(WebDriver driver) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(webchatServerUrl);
        assertEquals(driver.getTitle(), "gbwebchat");

        WebElement name = driver.findElement(By.cssSelector("[name=username]"));
        WebElement password = driver.findElement(By.cssSelector("[name=password]"));

        WebElement button_login = driver.findElement(By.cssSelector("body > app-root > " +
                "md-sidenav-container > div.mat-sidenav-content > md-card > app-login-detail > " +
                "div > form > div:nth-child(3) > button"));


        name.sendKeys("81016");
        password.sendKeys("1");
        button_login.click();
        Thread.sleep(1000);
        return driver;
    }

    public static WebDriver handleSecurityWarning(WebDriver driver) {
        String parentHandle = driver.getWindowHandle(); // get the current window handle

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        String openDevTools = Keys.chord(Keys.ENTER);
        driver.findElement(new By.ByTagName("body")).sendKeys(openDevTools);


        driver.close(); // close newly opened window when done with it
        driver.switchTo().window(parentHandle); // switch back to the original window
        return driver;
    }

    public void clickEditButton(WebDriver adminDriver) {
        WebElement button_Edit = adminDriver.findElement(By.xpath("body > app-root > md-sidenav-container > div.mat-sidenav-content > md-card > app-workgroup-list > div > div:nth-child(1) > div:nth-child(2) > div > div:nth-child(2) > button:nth-child(1) >" +
                " div.mat-button-ripple.mat-ripple"));
        button_Edit.click();
    }

    public static File lastFileModified(String dir) {
        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        return choice;
    }

        public static void deleteDirectories() throws IOException {

        File sourceDirectory = new File("video");
        FileUtils.deleteDirectory(sourceDirectory);
        if (Boolean.getBoolean("videoAndLogs.deleteIfLocal")) {
            sourceDirectory = new File("videoAndLogs");
            FileUtils.deleteDirectory(sourceDirectory);
        }
    }

     /*Thread t1 = new Thread(new Runnable() {
                public void run() {
                    try {
                        FileUtils.copyDirectory(sourceDirectory, destDirectory);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t1.start();
            t1.join();*/
    //Thread.sleep(1000);

       /* public static void setup(WebDriver driver, ITestContext ctx) throws InterruptedException, FindFailed, IOException {
        String testName = ctx.getCurrentXmlTest().getName();
          *//* FileWriter writer = new FileWriter(driverLog);
        return writer;*//*

        manualLogFile = createLogFile(testName + " ");
        manualLogFile.write(testName.toUpperCase() + "\n");
        System.out.println(testName.toUpperCase());


        if (Boolean.getBoolean("closeBrowser")) {
            Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
            Thread.sleep(2000); //might fix phone not opened problem
            log("3CXPhone killed from setup method.", "DEBUG");
            String hostName = InetAddress.getLocalHost().getHostName();

            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            if (!isLocal()) {
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            }
            openCXphone(60);
            log("OpenCXphone method called from setup method.", "DEBUG");
        }
    }*/

         /*       By button_listen_selector = By.cssSelector("[id = 'tabView:supervisorListen']");
            WebDriverWait waitForButtonListen = new WebDriverWait(supervisor, 5);
            waitForButtonListen.until(ExpectedConditions.elementToBeClickable(button_listen_selector));

            WebElement button_listen = supervisor.findElement(button_listen_selector);*/
            /*executeJavaScriptOrClick(supervisor, button_listen, "runSupervisorAction('silent');" +
                    "PrimeFaces.ab({source:'tabView:supervisorListen',update:'growl'});");
*/
    //can't do it on remote PC!
           /* JavascriptExecutor js = (JavascriptExecutor) supervisor;
            js.executeScript("runSupervisorAction('silent');PrimeFaces.ab({source:'tabView:supervisorListen',update:'growl'});");*/

   /*WebDriverWait waitForButton_Notify = new WebDriverWait(supervisor, 10);
            waitForButton_Notify.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tabView\\:supervisorNotify")));
            WebElement button_Notify = supervisor.findElement(By.cssSelector("#tabView\\:supervisorNotify"));
            button_Notify.click();
            WebElement supervisorNotify = supervisor.findElement(By.cssSelector("#tabView\\3a supervisorNotify"));
            executeJavaScriptOrClick(supervisor, supervisorNotify, "PrimeFaces.ab({source:'tabView:supervisorNotify',update:'growl'});");
            System.out.println("JavaScript has been executed.");*/
    //listen.click();
    //Methods.clickIEelement(supervisor, listen);


}


