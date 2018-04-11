package actions.login;

import data.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;
import utils.LoaderThread;

import java.io.IOException;
import java.net.URL;

import static callsMethods.Methods.*;
import static utils.Flags.isLocal;
import static utils.Logs.setChromeLogs;
import static utils.NativeServiceUpdate.waitForServiceUpdate;

/**
 * Created by SChubuk on 11.04.2018.
 */
public class Login {

    public static WebDriver openWebphoneLoginPage(WebDriver driver, String browser, String webphoneUrl) throws InterruptedException, IOException, FindFailed {
        return openWebphoneLoginPage(driver, browser, webphoneUrl, false);
    }


    public static WebDriver openWebphoneLoginPage(WebDriver driver, String browser, String webphoneUrl, Boolean remote) throws InterruptedException, IOException, FindFailed {
        if (System.getProperty("webphoneUrl") != null) {
            webphoneUrl = System.getProperty("webphoneUrl");
        }
        if (browser.equalsIgnoreCase("chrome")) {

            System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
            ChromeOptions chromeOptions = setChromeLogs();
            if (isLocal()) {
            } else {
                //         chromeOptions.addArguments("user-data-dir=C:/Users/sergey/AppData/Local/Google/Chrome/User Data");
            }
            if (Boolean.getBoolean("autoOpenDevtoolsForTabs")) {
                chromeOptions.addArguments("--auto-open-devtools-for-tabs");
            }
            chromeOptions.addArguments("--preserve-log");
            if (remote) {
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
            } else {
                driver = new ChromeDriver(chromeOptions);
            }
            driver.get(webphoneUrl);
            WebDriverWait waitForTitle = new WebDriverWait(driver, 10);
            waitForTitle.until(ExpectedConditions.titleIs("gbwebphone"));
            Assert.assertEquals(driver.getTitle(), "gbwebphone");
        } else {

            System.setProperty("webdriver.ie.driver", "C:/iedriver32/IEDriverServer.exe");
            DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
            ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                    true);

            /**********PLAY WITH CAPABILITIES*********************/
            ieCapabilities.setCapability("initialBrowserUrl", webphoneUrl);
            ieCapabilities.setCapability("nativeEvents", false);
            //ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
            ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
            ieCapabilities.setCapability("disable-popup-blocking", true);
            ieCapabilities.setCapability("enablePersistentHover", true);
            ieCapabilities.setCapability("ignoreZoomSetting", true);
            /***************************************************/

            driver = new InternetExplorerDriver(ieCapabilities);
            driver.manage().window().maximize();
            Thread thread1 = new LoaderThread(driver, webphoneUrl);
            Thread thread2 = new Thread() {
                public void run() {
                    Screen screen;
                    try {
                        log("Waiting for update java later dialog.", "DEBUG");
                        screen = new Screen();
                        org.sikuli.script.Pattern checkbox_doNotAskAgain = new org.sikuli.script.Pattern("C:\\SikuliImages\\checkbox_doNotAskAgain.png");
                        screen.wait(checkbox_doNotAskAgain, 2);
                        screen.click(checkbox_doNotAskAgain);

                        org.sikuli.script.Pattern option_updateJavaLater = new org.sikuli.script.Pattern("C:\\SikuliImages\\option_updateJavaLater.png");
                        screen.wait(option_updateJavaLater, 2);
                        screen.click(option_updateJavaLater);
                    } catch (FindFailed findFailed) {
                        log("There is no update java later window!", "DEBUG");
                    }
                    try {

                        screen = new Screen();
                        org.sikuli.script.Pattern checkbox_acceptTheRisk = new org.sikuli.script.Pattern("C:\\SikuliImages\\checkbox_acceptTheRisk.png");
                        screen.wait(checkbox_acceptTheRisk, 2);
                        screen.click(checkbox_acceptTheRisk);
                        org.sikuli.script.Pattern button_Run = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_Run.png");
                        screen.wait(button_Run, 2);
                        screen.click(button_Run);
                    } catch (FindFailed findFailed) {
                        log("There is no do you want to run this application window!", "DEBUG");
                    }
                }
            };


// Start the downloads.
            thread1.start();
            thread2.start();
// Wait for them both to finish
            thread1.join();
            thread2.join();

            WebDriverWait waitForTitle = new WebDriverWait(driver, 10);
            waitForTitle.until(ExpectedConditions.titleIs("gbwebphone"));
            Assert.assertEquals(driver.getTitle(), "gbwebphone");
            WebElement language = driver.findElement(By.cssSelector("#lang_input_label"));
            language.click();
            WebElement language_en = driver.findElement(By.xpath("//li[text() = 'English']"));
            language_en.click();

        }
        return driver;
    }


    public static WebDriver login(WebDriver driver, String method, String username, String group) throws InterruptedException, IOException {
        if (method == "sso") {
            WebElement button_SSO = driver.findElement(By.cssSelector("#ssoButton > span"));
            String winHandleBefore = driver.getWindowHandle();
            button_SSO.click();
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
            WebElement ssoUsername = driver.findElement(By.cssSelector("#username"));
            ssoUsername.sendKeys(username);
            sleep(1000);
            WebElement ssoPassword = driver.findElement(By.cssSelector("#password"));
            ssoPassword.sendKeys(Data.password);
            sleep(1000);
            WebElement ssoRememberMe = driver.findElement(By.cssSelector("#remember-me"));
            ssoRememberMe.click();
            sleep(1000);
            WebElement button_sso_Login = driver.findElement(By.cssSelector("#login_button"));
            button_sso_Login.click();
            driver.switchTo().window(winHandleBefore);
            Thread.sleep(1000);
            driver = handleLogoutWindow(driver);
        } else {
            By byNameU = By.cssSelector("[name=username_input]");
            WebDriverWait waitForUsername = new WebDriverWait(driver, 5);
            waitForUsername.until(ExpectedConditions.presenceOfElementLocated(byNameU));
            WebElement userName = driver.findElement(byNameU);
            userName.clear();
            userName.sendKeys(username);

            By byNameP = By.cssSelector("[name=password_input]");
            WebElement password = driver.findElement(byNameP);
            password.clear();
            password.sendKeys("1");


            WebElement button_Connect = driver.findElement(By.cssSelector("[name='btn_connect']"));
            button_Connect.click();
            driver = handleLogoutWindow(driver);
        }

      /*  String openDevTools = Keys.chord(Keys.ALT, Keys.CONTROL, "i");
        driver.findElement(By.cssSelector("#btn_continue > span.ui-button-text.ui-c")).sendKeys(openDevTools);*/

        WebDriverWait waitForButtonContinue = new WebDriverWait(driver, 10);
        waitForButtonContinue.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#btn_continue > span.ui-button-text.ui-c")));
        WebDriverWait waitForGroupList = new WebDriverWait(driver, 10);
        waitForGroupList.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#group_input_label")));
        WebElement groupList = driver.findElement(By.cssSelector("#group_input_label"));
        groupList.click();

        By groupSelector = By.cssSelector("[data-label='" + group + "']");
        log(groupSelector.toString(), "DEBUG");
        WebElement element = driver.findElement(groupSelector);
        //do not remove JavascriptExecutor here
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
        WebElement groupInDropdown = driver.findElement(By.cssSelector("[data-label='" + group + "']"));
        groupInDropdown.click();
        sleep(2000);

        log("Delay before button Continue.", "DEBUG");
        WebElement btnContinue = driver.findElement(By.cssSelector("#btn_continue > span.ui-button-text.ui-c"));
        btnContinue.click();
        log("Login to webphone as " + username + "/" + group + ".", "INFO");
        waitForServiceUpdate(driver);
        return driver;
    }
    public static WebDriver handleLogoutWindow(WebDriver driver) {
        try {
            WebDriverWait waitForLogoutWindow = new WebDriverWait(driver, 6);
            waitForLogoutWindow.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("#userLogoutForm\\3a btn_userlogout_yes > span.ui-button-text.ui-c")));
            WebElement button_Yes = driver.findElement(By.cssSelector("#userLogoutForm\\3a btn_userlogout_yes > span.ui-button-text.ui-c"));
            executeJavaScriptOrClick(driver, button_Yes);

        } catch (Exception e) {
            log("Logout window not found.", "DEBUG");
            //e.printStackTrace();
        }

        log("Handle logout window.", "DEBUG");
        return driver;
    }
}
