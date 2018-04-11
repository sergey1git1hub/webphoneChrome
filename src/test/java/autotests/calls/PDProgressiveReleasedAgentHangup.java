package autotests.calls;

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

import static actions.client.Client.cxAnswer;
import static actions.client.Client.openCXphone;
import static actions.database.Powerdialer.runSqlQuery;
import static actions.login.Login.login;
import static actions.login.Login.openWebphoneLoginPage;
import static actions.webphonePanel.WebphonePanel.changeStatus;
import static actions.webphonePanel.WebphonePanel.checkStatus;
import static actions.webphonePanel.WebphonePanel.setWebphoneResultCode;
import static callsMethods.Methods.log;
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
    public static String testName = "Agent receives progressive released call from Powerdialer";

    public static void IELogin() throws InterruptedException, IOException, FindFailed {
        data = createData();
        if (isLocal()) {
            data.group = "Automation Progressive Released Local";
        } else {
            data.group = "Automation Progressive Released Jenkins";
        }
        Methods.browser = data.browser;
        driver = openWebphoneLoginPage(driver, data.browser, data.webphoneUrl);
        login(driver, data.method, data.username, data.group);
        checkStatus(driver, "Available", 10);
    }

    public static void changeStatusToAUX() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        changeStatus(driver, "AUX");
    }


    public static void runSQLQuery() throws SQLException, ClassNotFoundException, InterruptedException, FindFailed, IOException {
        if (isLocal()) {
            runSqlQuery("pd_automation_progressive_released_local", "94949");
        } else {
            runSqlQuery("pd_automation_progressive_released_jenkins", "94944");
        }

        openCXphone(2000);
    }


    public static void noIncomingCallToClient() throws InterruptedException {
        if (debug == true)
            Thread.sleep(5000);
        else Thread.sleep(20000);
        log("Check that there is no incoming call to client", "DEBUG");
    }

    public static void changeStatusToAvailable() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        changeStatus(driver, "Available");
    }


    public static void answerCallOnClientSide() throws FindFailed, InterruptedException, UnknownHostException, UnsupportedEncodingException {
        cxAnswer();
        checkStatus(driver, "Incall", 5);
    }

    public static void answerCallOnClientSide(int waitTime) throws FindFailed, InterruptedException, UnknownHostException, UnsupportedEncodingException {
       cxAnswer(waitTime);
       checkStatus(driver, "Incall", 5);
    }

    public static void agentHangup() throws InterruptedException, FindFailed, UnknownHostException {
        Thread.sleep(2000);
        actions.webphonePanel.WebphonePanel.agentHangup(driver, 1);
    }


    public static void setResultCodeAndCheckAvailableStatus() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        setWebphoneResultCode(driver);
        checkStatus(driver, "Available", 3);

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void pDProgressiveReleasedAgentHangup() throws Exception {
        try {
            setup(testName);
            IELogin();
            changeStatusToAUX();
            runSQLQuery();
            noIncomingCallToClient();
            changeStatusToAvailable();
            answerCallOnClientSide(40);
            agentHangup();
            setResultCodeAndCheckAvailableStatus();
            teardown(driver, testName);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
