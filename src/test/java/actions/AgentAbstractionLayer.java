package actions;

import callsMethods.Methods;
import data.Data;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import static actions.client.Client.cxAnswer;
import static actions.client.Client.openCXphone;
import static actions.login.Login.openWebphoneLoginPage;
import static actions.webphonePanel.WebphonePanel.checkStatus;
import static actions.webphonePanel.WebphonePanel.setWebphoneResultCode;
import static data.Data.createData;
import static utils.Flags.isLocal;

/**
 * Created by SChubuk on 11.04.2018.
 */
public class AgentAbstractionLayer {
    private static final Logger log = Logger.getLogger("callsMethods.Methods");

    public static Data data;
    public static WebDriver driver;

    public  Data getData() {
        return data;
    }

    public static WebDriver getDriver() {
        return driver;
    }


    public static void createTestData() throws UnknownHostException {
        data = createData();
        if (isLocal()) {
            data.group = "Automation Outbound Calls Local";
        } else {
            data.group = "Automation Outbound Calls Jenkins";
        }

    }


    public  void Login() throws InterruptedException, IOException, FindFailed {
        driver = openWebphoneLoginPage(driver, data.browser, data.webphoneUrl);
        actions.login.Login.login(driver, data.method, data.username, data.group);
        checkStatus(driver, "Available", 30);
    }


    public  WebDriver callOnFirstLine() throws FindFailed, InterruptedException, IOException {
        openCXphone(5000);
        actions.webphonePanel.WebphonePanel.call(driver, 1, data.number1);
        cxAnswer();
        return driver;
    }


    public  WebDriver callOnSecondLine() throws FindFailed, InterruptedException, UnknownHostException {
        actions.webphonePanel.WebphonePanel.call(driver, 2, data.number2);
        cxAnswer();
        return driver;
    }



    public  void call() throws InterruptedException, IOException, FindFailed {
        createTestData();
        Login();
        callOnFirstLine();
    }

    public  void login() throws InterruptedException, FindFailed, IOException {
        createTestData();
        Login();
    }

    public  void login(String group) throws InterruptedException, FindFailed, IOException {
        createTestData();
        data.group = group;
        Login();
    }


    public  void setResultCodeAndCheckAvailableStatus() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        setWebphoneResultCode(driver);
        checkStatus(driver, "Available", 3);

    }
}
