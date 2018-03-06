package callsMethods;

import data.Data;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;
import utils.LoaderThread;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import static utils.Flags.isIE;
import static utils.Flags.isLocal;

import static utils.Logs.setChromeLogs;


/**
 * Created by SChubuk on 04.10.2017.
 */

public class Methods {
    public static String browser;
    public static boolean onJenkins;
    static boolean killProcess = true;
    static boolean debug = true;

    static boolean fast;


    public static WebDriver openWebphoneLoginPage(WebDriver driver, String browser, String webphoneUrl) throws InterruptedException, IOException, FindFailed {
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
            driver = new ChromeDriver(chromeOptions);
            driver.get(webphoneUrl);
            WebDriverWait waitForTitle = new WebDriverWait(driver, 10);
            waitForTitle.until(ExpectedConditions.titleIs("gbwebphone"));
            Assert.assertEquals(driver.getTitle(), "gbwebphone");
        } else {

            System.setProperty("webdriver.ie.driver", "C:/iedriver32/IEDriverServer.exe");
            System.setProperty("webdriver.ie.driver.loglevel", "INFO");

            DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
            ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                    true);

            /**********PLAY WITH CAPABILITIES*********************/
            ieCapabilities.setCapability("initialBrowserUrl", webphoneUrl);
            ieCapabilities.setCapability("nativeEvents", false);
            ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
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
                        System.out.println("openWebphoneLoginPage.updateJavaLater");
                        screen = new Screen();

                        org.sikuli.script.Pattern checkbox_doNotAskAgain = new org.sikuli.script.Pattern("C:\\SikuliImages\\checkbox_doNotAskAgain.png");
                        screen.wait(checkbox_doNotAskAgain, 2);
                        screen.click(checkbox_doNotAskAgain);

                        org.sikuli.script.Pattern option_updateJavaLater = new org.sikuli.script.Pattern("C:\\SikuliImages\\option_updateJavaLater.png");
                        screen.wait(option_updateJavaLater, 2);
                        screen.click(option_updateJavaLater);
                    } catch (FindFailed findFailed) {
                        if (debug == true) {

                            findFailed.printStackTrace();
                        } else {
                            System.out.println("There is no update java later window!");
                        }
                    }
                    try {
                        System.out.println("openWebphoneLoginPage.DoYouWantToRunThisApplication");
                        screen = new Screen();
                        org.sikuli.script.Pattern checkbox_acceptTheRisk = new org.sikuli.script.Pattern("C:\\SikuliImages\\checkbox_acceptTheRisk.png");
                        screen.wait(checkbox_acceptTheRisk, 2);
                        screen.click(checkbox_acceptTheRisk);
                        org.sikuli.script.Pattern button_Run = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_Run.png");
                        screen.wait(button_Run, 2);
                        screen.click(button_Run);
                    } catch (FindFailed findFailed) {
                        if (debug == true) {
                            findFailed.printStackTrace();
                        } else {
                            System.out.println("There is no do you want to run this application window!");
                        }
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


    public static WebDriver login(WebDriver driver, String method, String username, String group) throws InterruptedException {
        System.out.println("login");
        if (method == "sso") {
            WebElement button_SSO = driver.findElement(By.cssSelector("#ssoButton > span"));
            String winHandleBefore = driver.getWindowHandle();
            button_SSO.click();
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
            WebElement ssoUsername = driver.findElement(By.cssSelector("#username"));
            ssoUsername.sendKeys(username);
            if (fast == false)
                Thread.sleep(1000);
            WebElement ssoPassword = driver.findElement(By.cssSelector("#password"));
            ssoPassword.sendKeys(Data.password);
            if (fast == false)
                Thread.sleep(1000);
            WebElement ssoRememberMe = driver.findElement(By.cssSelector("#remember-me"));
            ssoRememberMe.click();
            if (fast == false)
                Thread.sleep(1000);
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
        System.out.println(groupSelector);
        WebElement element = driver.findElement(groupSelector);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
        WebElement groupInDropdown = driver.findElement(By.cssSelector("[data-label='" + group + "']"));
        groupInDropdown.click();
        Thread.sleep(2000);
        System.out.println("Delay before button Continue.");
        WebElement btnContinue = driver.findElement(By.cssSelector("#btn_continue > span.ui-button-text.ui-c"));
        btnContinue.click();

        return driver;
    }

    public static WebDriver checkStatus(WebDriver driver, String status, int waitTime) throws UnknownHostException, UnsupportedEncodingException, InterruptedException {

        System.out.println("checkStatus");
        if ((!isLocal()) && (status.equals("Тренинг"))) {
            Thread.sleep(10000);
        } else {
            WebDriverWait waitForStatus = new WebDriverWait(driver, waitTime);
            waitForStatus.until(ExpectedConditions.textMatches(By.cssSelector(
                    "#statusButton > span.ui-button-text.ui-c"), Pattern.compile(".*\\b" + status + "\\b.*")));
            System.out.println("Wait for status.");

            WebElement currentStatus = driver.findElement(By.cssSelector(
                    "#statusButton > span.ui-button-text.ui-c"));
            System.out.println("Before assert status is: " + currentStatus.getText() + ".");
            System.out.println("Asserting that contains: " + status + ".");

            Assert.assertTrue(currentStatus.getText().contains(status));
            System.out.println("Check Status.");
        }
        return driver;
    }

    public static WebDriver changeStatusNewDontWork(WebDriver driver, String status) throws UnknownHostException, FindFailed, InterruptedException, UnsupportedEncodingException {
        System.out.println("changeStatus");
        if (status.equalsIgnoreCase("Available")) {
            Screen screen = new Screen();
            org.sikuli.script.Pattern currentStatus = new org.sikuli.script.Pattern("C:\\SikuliImages\\currentStatus.png");
            screen.wait(currentStatus, 10);
            Thread.sleep(1000);
            screen.click(currentStatus);
            Thread.sleep(1000);
            org.sikuli.script.Pattern availableStatus = new org.sikuli.script.Pattern("C:\\SikuliImages\\availableStatus.png");
            screen.wait(availableStatus, 10);
            screen.click(availableStatus);
        }
        if (status.equalsIgnoreCase("AUX")) {
            Screen screen = new Screen();
            org.sikuli.script.Pattern currentStatus = new org.sikuli.script.Pattern("C:\\SikuliImages\\currentStatus.png");
            screen.wait(currentStatus, 10);
            //Thread.sleep(2000);
            screen.click(currentStatus);
            Thread.sleep(1000);
            org.sikuli.script.Pattern auxStatus = new org.sikuli.script.Pattern("C:\\SikuliImages\\auxStatus.png");
            screen.wait(auxStatus, 10);
            screen.click(auxStatus);
        }

        checkStatus(driver, status, 2);
        return driver;
    }


    public static WebDriver changeStatus(WebDriver driver, String status) throws UnknownHostException, FindFailed, InterruptedException, UnsupportedEncodingException {
        System.out.println("changeStatus");
        String hostName = InetAddress.getLocalHost().getHostName();
        if (!isLocal() && !browser.equals("chrome")) {
            if (status.equalsIgnoreCase("Available")) {
                Screen screen = new Screen();
                org.sikuli.script.Pattern currentStatus = new org.sikuli.script.Pattern("C:\\SikuliImages\\currentStatus.png");
                screen.wait(currentStatus, 10);
                Thread.sleep(1000);
                screen.click(currentStatus);
                Thread.sleep(1000);
                org.sikuli.script.Pattern availableStatus = new org.sikuli.script.Pattern("C:\\SikuliImages\\availableStatus.png");
                screen.wait(availableStatus, 10);
                screen.click(availableStatus);
            }
            if (status.equalsIgnoreCase("AUX")) {
                Screen screen = new Screen();
                org.sikuli.script.Pattern currentStatus = new org.sikuli.script.Pattern("C:\\SikuliImages\\currentStatus.png");
                screen.wait(currentStatus, 10);
                //Thread.sleep(2000);
                screen.click(currentStatus);
                Thread.sleep(1000);
                org.sikuli.script.Pattern auxStatus = new org.sikuli.script.Pattern("C:\\SikuliImages\\auxStatus.png");
                screen.wait(auxStatus, 10);
                screen.click(auxStatus);
            }
            checkStatus(driver, status, 2);
            System.out.println("Host is: kv1-it-pc-jtest and browser is not Chrome.");
        } else if (browser.equals("chrome")) {
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
            System.out.println("Browser is Chrome.");
        } else {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            WebElement currentStatus = driver.findElement(By.cssSelector(
                    "#statusButton > span.ui-button-text.ui-c"));
            executor.executeScript("arguments[0].click();", currentStatus);
            WebElement desirableStatus;
            if (!status.equals("AUX")) {
                desirableStatus = driver.findElement(By.xpath(
                        "/*//*[contains(text(),'" + status + "')]"));
            } else {
                desirableStatus = driver.findElement(By.xpath(
                        "//*[contains(text(),'AUX') and not(contains(text(),'Доступен'))]"));
            }
            executor.executeScript("arguments[0].click();", desirableStatus);
            checkStatus(driver, status, 2);
            System.out.println("Else.");
        }
        return driver;
    }

    public static WebDriver switchLine(WebDriver driver, int line) throws FindFailed, InterruptedException, UnknownHostException {
        System.out.println("switchLine");
        /*String hostName = InetAddress.getLocalHost().getHostName();
        if (driver.equals("chrome") && isLocal()) {
            System.out.println("Browser is chrome.");*/
            WebDriverWait waitForLineElement = new WebDriverWait(driver, 2);
            waitForLineElement.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id = 'btn_line_" + line + "_span']")));
            WebElement lineElement = driver.findElement(By.cssSelector("[id = 'btn_line_" + line + "_span']"));
            Thread.sleep(1000);
            System.out.println("Slept 1000 ms.");
            lineElement.click();
            System.out.println("Line switched by webdriver.");
        /*} else {
            System.out.println("Browser is not chrome or running on Jenkns.");
            if (!isLocal()) {
                WebElement lineElement = driver.findElement(By.cssSelector("[id = 'btn_line_" + line + "_span']"));
                lineElement.sendKeys(Keys.ENTER);
            } else
                try {
                    if (driver instanceof JavascriptExecutor) {
                        ((JavascriptExecutor) driver)
                                .executeScript("wp_common.wp_ChangeLine(" + line + "); log(event);");
                        System.out.println("Line switched by javascript.");
                    }
                } catch (Exception e) {
                    if (debug == true)
                        e.printStackTrace();
                    else System.out.println("JavaScript execution error!");
                }
        }*/
        return driver;
    }


    public static WebDriver call(WebDriver driver, int line, String number) throws FindFailed, InterruptedException, UnknownHostException {
        System.out.println("call");
        String hostName = InetAddress.getLocalHost().getHostName();
        if (true/*hostName.equalsIgnoreCase("kv1-it-pc-jtest")*/) {
            switchLine(driver, line);
            System.out.println("Line switched.");
            Thread.sleep(1000);
            System.out.println("Sleep after Line switched.");
            Screen screen = new Screen();
            if (!(!isLocal() && browser.equals("chrome"))) {
                org.sikuli.script.Pattern phoneNumberField_Sikuli = new org.sikuli.script.Pattern("C:\\SikuliImages\\phoneNumberField_Sikuli.png");
                screen.wait(phoneNumberField_Sikuli, 10);
                screen.click(phoneNumberField_Sikuli);
            }
            System.out.println("Sikuli clkicked phone number filed.");
            WebElement phoneNumberField = driver.findElement(By.cssSelector("#PhoneNumber"));
            phoneNumberField.click();
            phoneNumberField.clear();
            phoneNumberField.sendKeys(number);
            System.out.println("Selenium send phone number key.");
            WebElement button_Call = driver.findElement(By.cssSelector("#btn_call"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", button_Call);
            System.out.println("Button call clicked with javascript.");
        } else {

        }
        return driver;

    }

    public static void openCXphone(int waitTime) throws FindFailed, InterruptedException, IOException {
        Screen screen = new Screen();
        App cxphone = App.open("C:\\Program Files (x86)\\3CXPhone\\3CXPhone.exe");
        org.sikuli.script.Pattern closePhoneWindow = new org.sikuli.script.Pattern("C:\\SikuliImages\\closePhoneWindow.png");
        screen.wait(closePhoneWindow, waitTime);
        screen.click(closePhoneWindow);

    }

    public static void cxAnswer() throws FindFailed, InterruptedException {
        Screen screen = new Screen();
        org.sikuli.script.Pattern button_3CXAcceptCall = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_3CXAcceptCall.png");
        screen.wait(button_3CXAcceptCall, 10);
        screen.click(button_3CXAcceptCall);
        if (fast = false)
            Thread.sleep(1000);
        org.sikuli.script.Pattern closePhoneWindow = new org.sikuli.script.Pattern("C:\\SikuliImages\\closePhoneWindow.png");
        screen.wait(closePhoneWindow, 10);
        screen.click(closePhoneWindow);

    }

    public static WebDriver agentHangup(WebDriver driver, int line) throws FindFailed, InterruptedException, UnknownHostException {

        System.out.println("agentHangup");
        switchLine(driver, line);
        Thread.sleep(500);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        WebElement button_Hangup = driver.findElement(By.cssSelector("#btn_hangup"));
        //if (browser == "chrome")
        button_Hangup.click();
        //else executor.executeScript("arguments[0].click();", button_Hangup);
        return driver;
    }

    public static void clientHangup(WebDriver driver, int line) throws FindFailed {
        App cxphone = App.open("C:\\Program Files (x86)\\3CXPhone\\3CXPhone.exe");
        Screen screen = new Screen();
        org.sikuli.script.Pattern line_3CXLine1 = new org.sikuli.script.Pattern("C:\\SikuliImages\\line_3CXLine1.png");
        org.sikuli.script.Pattern line_3CXLine2 = new org.sikuli.script.Pattern("C:\\SikuliImages\\line_3CXLine2.png");
        org.sikuli.script.Pattern button_3CXHangupCall = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_3CXHangupCall.png");
        org.sikuli.script.Pattern closePhoneWindow = new org.sikuli.script.Pattern("C:\\SikuliImages\\closePhoneWindow.png");
        if (line == 2) {
            screen.wait(line_3CXLine1, 10);
            screen.click(line_3CXLine1);
        }

        screen.click(button_3CXHangupCall);
        screen.wait(closePhoneWindow, 10);
        screen.click(closePhoneWindow);
    }

    public static WebDriver setWebphoneResultCode(WebDriver driver) throws InterruptedException, UnknownHostException, FindFailed {
        System.out.println("setWebphoneResultCode");
        String hostName = InetAddress.getLocalHost().getHostName();
        if (!isLocal()) {
            Screen screen = new Screen();
            org.sikuli.script.Pattern resultCodeUdachno = new org.sikuli.script.Pattern("C:\\SikuliImages\\resultCodeUdachno.png");
            screen.wait(resultCodeUdachno, 10);
            screen.click(resultCodeUdachno);
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
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", resultCode);
            Thread.sleep(1000);
            WebElement button_Save = driver.findElement(By.cssSelector("#btn_rslt > span.ui-button-text.ui-c"));
            executor.executeScript("arguments[0].click();", button_Save);
        }
        return driver;


    }

    public static WebDriver switchToAdTab(WebDriver driver) {
        System.out.println("switchToAdTab");
        WebElement adTab = driver.findElement(By.xpath("//a[@href = '#tabView:tab123']"));
        adTab.click();
        try {

            driver.switchTo().frame("TAB_123");
            WebElement username = driver.findElement(By.cssSelector("[name = 'j_username']"));
            username.sendKeys("81016");
            WebElement password = driver.findElement(By.cssSelector("[name = 'j_password']"));
            password.sendKeys("1");
            WebElement login = driver.findElement(By.cssSelector("[name = 'login']"));
            login.click();
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        System.out.println("getConnection");
        String userName = "GBWebPhoneTest";
        String password = "yt~k$tCW8%Gj";
        String url = "jdbc:sqlserver://172.21.65.14\\\\corporate;DatabaseName=GBWebPhoneTest;portNumber=1438";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection(url, userName, password);
        return conn;
    }

    public static void updateRecord(Connection con, String dbTable, String dbPhoneNumber) throws SQLException {
        System.out.println("updateRecord");
        String query;
        query = "INSERT INTO GBWebPhoneTest.dbo." + dbTable + "(phone_number_1)"
                + " VALUES ('" + dbPhoneNumber + "');";
        Statement stmt = con.createStatement();
        stmt.execute(query);
    }

    public static void runSqlQuery(String dbTable, String dbPhoneNumber) throws SQLException, ClassNotFoundException {
        System.out.println("runSqlQuery");
        updateRecord(getConnection(), dbTable, dbPhoneNumber);
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
        return driver;
    }

    public static WebDriver saveCRMCard(WebDriver driver) throws FindFailed, InterruptedException {
        System.out.println("saveCRMCard");
        WebDriverWait waitForIncallStatus = new WebDriverWait(driver, 5);
        waitForIncallStatus.until(ExpectedConditions.textMatches(By.cssSelector(
                "#statusButton > span.ui-button-text.ui-c"), Pattern.compile(".*\\bIncall\\b.*")));
        Screen screen = new Screen();

        org.sikuli.script.Pattern mltest;
        if (browser.equals("chrome")) {
            mltest = new org.sikuli.script.Pattern("C:\\SikuliImages\\mltestChrome.png");
        } else {
            mltest = new org.sikuli.script.Pattern("C:\\SikuliImages\\mltest.png");
        }
        screen.wait(mltest, 10);
        screen.click(mltest);

        org.sikuli.script.Pattern button_OK = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_OK.png");
        screen.wait(button_OK, 10);
        screen.click(button_OK);

        driver.switchTo().frame("TAB_123");
        try {
            WebElement visitDate = driver.findElement(By.cssSelector("[name = 'cardValues[0].value']"));
            if (visitDate.getText().equalsIgnoreCase("")) {
                visitDate.sendKeys("2018-03-20");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //visitDate.click();

        driver.switchTo().defaultContent();

        org.sikuli.script.Pattern button_nextForm = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_nextForm.png");
        screen.wait(button_nextForm, 10);
        screen.click(button_nextForm);

        org.sikuli.script.Pattern button_save = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_save.png");
        screen.wait(button_save, 10);
        screen.click(button_save);

        org.sikuli.script.Pattern selectResultCode = new org.sikuli.script.Pattern("C:\\SikuliImages\\selectResultCode.png");
        screen.wait(selectResultCode, 10);
        screen.click(selectResultCode);

        org.sikuli.script.Pattern callLater = new org.sikuli.script.Pattern("C:\\SikuliImages\\callLater.png");
        screen.wait(callLater, 10);
        screen.click(callLater);
        Thread.sleep(2000);
        screen.wait(button_save, 10);
        screen.click(button_save);
        return driver;
    }

    public static WebDriver loginToPD() throws InterruptedException {
        System.out.println("loginToPD");
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        WebDriver agentPD = new ChromeDriver();
        agentPD.manage().window().maximize();
        agentPD.get(Data.PDUrl);

        System.out.println(agentPD.getTitle());
        Thread.sleep(3000);
        Assert.assertEquals(agentPD.getTitle(), "gbpowerdialer");

        Thread.sleep(1000);
        WebElement username = agentPD.findElement(By.cssSelector("#username"));
        username.sendKeys("81016");
        WebElement password = agentPD.findElement(By.cssSelector("#password"));
        password.sendKeys("1");
        WebElement button_SignIn = agentPD.findElement(By.cssSelector("#loginModal > div > div > form > div.modal-footer > button"));
        button_SignIn.click();
        return agentPD;
    }

    public static void runPDCampaign(WebDriver agentPD, int campaignId) throws InterruptedException {
        System.out.println("runPDCampaign");
        Thread.sleep(2000);

        WebElement columns = agentPD.findElement(By.xpath("//*[@id=\"campaignGrid\"]/div/div[1]"));
        columns.click();
        Thread.sleep(1000);

        WebElement id = agentPD.findElement(By.xpath("//*[@id=\"menuitem-13\"]/button"));
        id.click();
        Thread.sleep(1000);
        WebElement running = agentPD.findElement(By.xpath("//*[text() = '" + campaignId + "']//parent::div//following-sibling::div[3]//div"));


        System.out.println(running.getText());
        if (!running.getText().equals("Running")) {
            WebElement currentCampaign = agentPD.findElement(By.xpath("//*[text() = '" + campaignId + "']"));
            currentCampaign.click();
            WebElement icon_Enable = agentPD.findElement(By.cssSelector("#navbarCompainList > div > div:nth-child(7) > button"));
            icon_Enable.click();
            WebElement button_Enable = agentPD.findElement(By.cssSelector("#navbarCompainList > div > div.btn-group.open > ul > li:nth-child(1) > a"));
            button_Enable.click();
            Thread.sleep(1000);
            WebElement button_Start = agentPD.findElement(By.cssSelector("#navbarCompainList > div > button.btn.btn-success.btn-sm.navbar-btn"));
            button_Start.click();
        }
        agentPD.quit();
    }


    public static void clickIEelement(WebDriver driver, WebElement element) {
        System.out.println("clickIEelement");
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }


    public static WebDriver handleLogoutWindow(WebDriver driver) {
        System.out.println("handleLogoutWindow");
        try {
            WebDriverWait waitForLogoutWindow = new WebDriverWait(driver, 3);
            waitForLogoutWindow.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("#userLogoutForm\\3a btn_userlogout_yes > span.ui-button-text.ui-c")));
            WebElement button_Yes = driver.findElement(By.cssSelector("#userLogoutForm\\3a btn_userlogout_yes > span.ui-button-text.ui-c"));
            Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
            String browserName = cap.getBrowserName().toLowerCase();
            System.out.println(browserName);
            if (browserName.equals("internet explorer")) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                WebElement currentStatus = driver.findElement(By.cssSelector(
                        "#statusButton > span.ui-button-text.ui-c"));
                executor.executeScript("arguments[0].click();", button_Yes);
            } else {
                button_Yes.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    public static void focusCXphone(int waitTime) throws FindFailed, InterruptedException, IOException {
        System.out.println("openCXphone");
        String hostName = InetAddress.getLocalHost().getHostName();
        Thread.sleep(1000);
        if (hostName.equalsIgnoreCase("kv1-it-pc-jtest")) {
            App cxphone = App.open("C:\\Program Files (x86)\\3CXPhone\\3CXPhone.exe");
            Thread.sleep(waitTime);
        } else {
            App cxphone = App.open("C:\\Program Files (x86)\\3CXPhone\\3CXPhone.exe");
            Thread.sleep(waitTime);
        }
    }


}
