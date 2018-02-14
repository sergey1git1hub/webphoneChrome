package calls;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import callsMethods.Methods;
import utils.RetryAnalyzer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import static data.Data.createData;
import static utils.Flags.isLocal;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)

public class PDProgressiveReleasedAgentHangup {
    static Data data;
    static WebDriver driver;
    static boolean debug = true;

    public static void IELogin() throws InterruptedException, IOException, FindFailed {
        data = createData();
        if (isLocal()) {
            data.group = "Automation Progressive Released Local";
        } else {
            data.group = "Automation Progressive Released Jenkins";
        }
        Methods.browser = data.browser;
        driver = Methods.openWebphoneLoginPage(driver, data.browser, data.webphoneUrl);
        Methods.login(driver, data.method, data.username, data.group);
        Methods.checkStatus(driver, "Available", 10);
    }

    public static void changeStatusToAUX() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.changeStatus(driver, "AUX");
        Methods.checkStatus(driver, "AUX", 3);
    }


    public static void runSQLQuery() throws SQLException, ClassNotFoundException, InterruptedException, FindFailed, IOException {
        if (isLocal()) {
            Methods.runSqlQuery("pd_automation_progressive_released_local", "94949");
        } else {
            Methods.runSqlQuery("pd_automation_progressive_released_jenkins", "94944");
        }

        Methods.openCXphone(2000);
    }


    public static void noIncomingCallToClient() throws InterruptedException {
        if (debug == true)
            Thread.sleep(5000);
        else Thread.sleep(20000);
    }

    public static void changeStatusToAvailable() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.changeStatus(driver, "Available");
        Methods.checkStatus(driver, "Available", 3);
    }


    public static void waitForCallOnClientSide2() throws FindFailed, InterruptedException {
        try {
            Methods.cxAnswer();
        } catch (Exception e) {
            e.printStackTrace();
            WebDriver driverTemp = Methods.loginToPD();
            Methods.runPDCampaign(driverTemp, 257);
            Methods.cxAnswer();
        }
    }

    public static void agentHangup() throws InterruptedException, FindFailed, UnknownHostException {
        Thread.sleep(2000);
        Methods.agentHangup(driver, 1);
    }


    public static void setResultCodeAndCheckAvailableStatus() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.setWebphoneResultCode(driver);
        Methods.checkStatus(driver, "Available", 3);

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void pDProgressiveReleasedAgentHangup() throws Exception {
        try {
            setup(driver);
            IELogin();
            changeStatusToAUX();
            runSQLQuery();
            noIncomingCallToClient();
            changeStatusToAvailable();
            waitForCallOnClientSide2();
            agentHangup();
            setResultCodeAndCheckAvailableStatus();
            teardown(driver, "pDProgressiveReleasedAgentHangup");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
