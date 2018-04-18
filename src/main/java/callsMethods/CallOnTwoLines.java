package callsMethods;

import data.Data;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import static data.Data.createData;
import static utils.Flags.isLocal;

/**
 * Created by SChubuk on 05.10.2017.
 */

public class CallOnTwoLines {
    private static final Logger log = Logger.getLogger("callsMethods.Methods");
    /***********CHANGED TO RUN CHROME BROWSER******************/
    public static Data data;
    /***********************************************************/
    public static WebDriver driver;

    public static void createTestData() throws UnknownHostException {
        data = createData();
        if (isLocal()) {
            data.group = "Automation Outbound Calls Local";
        } else {
            data.group = "Automation Outbound Calls Jenkins";
        }

        /*****************IMPROVEMENT******************/
        Methods.browser = data.browser;
        /**********************************************/
        Methods.onJenkins = false;

    }


    public static void Login() throws InterruptedException, IOException, FindFailed {
        driver = Methods.openWebphoneLoginPage(driver, data.webphoneUrl);
        Methods.login(driver, data.method, data.username, data.group);
        Methods.checkStatus(driver, "Available", 30);
    }


    public static WebDriver callOnFirstLine() throws FindFailed, InterruptedException, IOException {
        Methods.openCXphone(5000);
        Methods.call(driver, 1, data.number1);
        Methods.cxAnswer();
        return driver;
    }


    public static WebDriver callOnSecondLine() throws FindFailed, InterruptedException, UnknownHostException {
        Methods.call(driver, 2, data.number2);
        Methods.cxAnswer();
        return driver;
    }

    public static void callOnTwoLines() throws InterruptedException, IOException, FindFailed {
        createTestData();
        Login();
        callOnFirstLine();
        callOnSecondLine();
    }

    public static void call() throws InterruptedException, IOException, FindFailed {
        createTestData();
        Login();
        callOnFirstLine();
    }

    public static void login() throws InterruptedException, FindFailed, IOException {
        createTestData();
        Login();
    }

    public static void login(String group) throws InterruptedException, FindFailed, IOException {
        createTestData();
        data.group = group;
        Login();
    }


    public static void setResultCodeAndCheckAvailableStatus() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.setWebphoneResultCode(driver);
        Methods.checkStatus(driver, "Available", 3);

    }

}
